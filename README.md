[![Build Status](https://travis-ci.com/jojoreading-public/demo-server.svg?branch=master)](https://travis-ci.com/jojoreading-public/demo-server)
[![Coverage Status](https://coveralls.io/repos/github/jojoreading-public/demo-server/badge.svg?branch=master)](https://coveralls.io/github/jojoreading-public/demo-server?branch=master)
[![codecov](https://codecov.io/gh/jojoreading-public/demo-server/branch/master/graph/badge.svg)](https://codecov.io/gh/jojoreading-public/demo-server)

# demo-server

本项目是一个演示程序，主要是把我们团队在平常遇到的问题汇总并加以解决，给团队一个最佳实践，同时开源出来，接受大家更广泛的意见和建议。

## 我们遇到了哪些问题

* 项目开发的时候需要依赖 MySQL、Redis、ElasticSearch 等服务，之前大家都是用公司内某台服务器上提供的服务，但为了能够调试、跟踪程序的一些细微问题，需要自己独享这些服务来避免其他人运行程序时干扰自己的工作，难道需要让运维为每个人提供一组单独的服务吗？

  答：之前我们的团队也是没有一个很好的解决方案，虽然能够想到 vagrant 或者 Docker 这些虚拟化工具，但没有比较完整的解决方案，直到我拜读了这篇文章 [后端开发实践系列——开发者的第0个迭代](https://mp.weixin.qq.com/s/uMB0nYc_c_lA0CHSqy3q4w)，作者给了我很多启发。你会看到我们项目里面的 devops 文件夹主要有一个 docker-compose.yml 文件，里面编排了这个项目需要用到的服务，每个人在开发的时候都是独享这些服务，互不干扰。

  假设 A 同学在开发 feature1，B 同学在开发 feature2，他们都需要添加或者修改数据库模型，此时只需要在自己的服务里面修改，确认没问题之后通过 devops/export.sh 脚本按需导出即可，每个feature的数据库模型跟着自己的代码版本走，当需要合并到主干分支的时候直接合并导出的数据库文件即可，当主干上的功能需要发布时只需要对比上次发布和这次发布的数据库文件，即可汇总本次升级时需要执行哪些操作，然后提交给 DBA 审核并执行。
  
  配置文件同理，开发模式下配置文件都会统一存储在 server-test 模块 resources/apollo-config 下，项目首次启动时该文件夹里面的配置文件会自动加载进 Apollo，你可以在 Apollo 随时调整配置文件的内容以便于观察效果。当项目需要发布时，我们只需要对比上次发布和本次发布版本之间的配置差异，整理后通知相关人员增加 Apollo 配置，避免配置遗漏。

  

* 项目进行单元测试、集成测试的时候或多或少都会用到外部服务，而且有时候同一个测试还不能多次运行，往往会因为数据库里面的主键冲突而导致运行失败，这个问题怎么解决呢？

  答：正是因为我们项目遇到了这些问题，所以我们的测试一直推进不了，每次测试完都会向 MySQL 里面写入大量的测试数据，如果测试的数据涉及到主键唯一问题，还可能会运行失败。

  我们也了解到了某些同事在既往的工作过程中有通过各种各样的方案来解决这个问题，例如通过 H2 这种内存型数据库，每次运行都是干净的。但 H2 和 MySQL 肯定是有差异的，即使都是 MySQL 也会有版本特性问题，所以我们打算通过 Docker 来解决这些问题。

  我们的实现过程是，每运行一个单元测试前我们都通过脚本把 devops 下的资源重新初始化一遍，例如用数据库结构重新初始化 MySQL，直接擦除  Redis 的所有数据。这样我们的每次测试都是独立、互不干扰的，这部分的实现是在 server-test 模块的 UnitTestBase 中实现的。

  

* 我们的 server-test 模块式专门为测试提供一些基础设施的，不希望在正式打包的时候混杂了任何测试代码，这该怎么办呢？

  答：我们用到了 maven 的 profile 模式，当你在 IDE 或者 maven 命令行模式下启用了 test-suit profile 时，测试相关的模块、代码才会生效，这就解决了上述问题。

  这也是为什么我们会在除 server-test 模块之外的其他模块 pom.xml 中如此引用测试模块了

  ```
  <profiles>
     <profile>
        <id>test-suit</id>
        <dependencies>
           <dependency>
              <groupId>com.jojoreading.demo</groupId>
              <artifactId>server-test</artifactId>
              <version>${project.parent.version}</version>
           </dependency>
        </dependencies>
     </profile>
  </profiles>
  ```


* 我们在每个应用或者微服务中的项目结构是怎么样的呢，他们每层的职责是什么？

  答：我们也一直在探索这个问题，介绍以下我们自己的模块层级划分，有可以改进的地方还请大家在中指教。

  ![模块层级划分](https://raw.githubusercontent.com/odirus/jojoreading-public-community/master/demo-server/doc/images/module.png)

  你在项目的 pom.xml 中也能体会到以下的关系：

  * server-test 模块，为单元测试、集成测试提供公共的基础组件。

  * server-common 模块，提供公共的基础组件，例如邮件发送服务、数据库连接池、Redis连接池、消息队列等，只能依赖于 server-test 模块，而不能依赖上层的其他模块。
  * server-core 模块，专门处理数据相关的内容，包括但不限于 MySQL、Redis、ElasticSearch 等，只能依赖 common 模块。
  * server-facade 模块，防腐层，统一封装外部的调用，例如调用其他微服务、调用第三方服务等，只能依赖 common 模块。
  * server-biz 模块，业务模块，这里要实现 server-web 层和 server-api 对外提供的各种服务，只能依赖于 server-core、server-facade 模块，通过依赖传递，也可以使用 server-common 的一些统一工具函数。
  * server-web 模块，对外提供 web 服务，包括 restful 接口、网页等，通过调用 server-biz 模块对外提供服务，通过依赖传递，也可以使用 server-common 的一些统一工具函数。
  * server-api 模块，当我们需要对外提供 api 时，例如 dubbo 服务，就可以单独把这个模块打包出来提供给外部使用，尽量不要使用其他模块的代码，这样在打包的时候这个模块才会足够简洁。

  当然在每一层的测试代码中都要依赖 server-test 中的基础设施。

## 怎么运行这个项目
* 把项目代码导入 IDE 中，建议使用 idea 这个 IDE

* 安装最新版本的 Docker

* 启动该项目的依赖服务，执行命令 ./devops/start.sh 即可，等待所有服务启动完毕，相关的资源请查看 devops 文件夹下的 [README.md](./devops/README.md)

  * 如果希望运行 web 服务器（入口是 com.jojoreading.demo.server.web.WebServerApplication）还需要在 IDE 中配置下面的启动参数

  ```
  -Dapollo.configService=http://127.0.0.1:18080
  -Dapp.id=dev-app
  -Dapollo.bootstrap.enabled=true
  -Dapollo.bootstrap.namespaces=application,module-common
  ```

  * 如果是希望把 web 服务器打包成一个可执行 jar 包，不用配置上述参数，运行下面的命令

  ```
  mvn clean install -pl server-web -am -Dmaven.test.skip=true
  ```

  * 如果是希望在命令行下运行测试，不用配置上述参数，运行下面的命令

  ```
  mvn clean test -P test-suit
  ```


