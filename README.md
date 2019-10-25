[![Build Status](https://travis-ci.org/jojoreading-public/demo-server.svg?branch=master)](https://travis-ci.com/jojoreading-public/demo-server)
[![Coverage Status](https://coveralls.io/repos/github/jojoreading-public/demo-server/badge.svg?branch=master)](https://coveralls.io/github/jojoreading-public/demo-server?branch=master)
[![codecov](https://codecov.io/gh/jojoreading-public/demo-server/branch/master/graph/badge.svg)](https://codecov.io/gh/jojoreading-public/demo-server)
# demo-server
本项目是一个演示程序，以 SprintBoot 作为框架基础，结合了 Apollo 作为配置中心、Docker 作为外部服务容器，演示项目模块结构、如何进行单元测试、如何进行集成测试等

实现这个项目的过程中，也参考了以下的一些资料：
* [后端开发实践系列——开发者的第0个迭代](https://mp.weixin.qq.com/s/uMB0nYc_c_lA0CHSqy3q4w)

## 准备事项
* 安装最新版本的 Docker

## 目录结构
* devops  
该目录存放了项目相关的一些外部依赖，目前包括 MySQL, Redis, Apollo 等  
启动项目或者运行测试之前先启动 devops 环境，请阅读该目录下的 [README.md](./devops/README.md)

* server-biz  
应用层，实现业务服务处理，也包括对外发布的接口模块 server-api 的实现等。

* server-core  
核心领域层，主要是针对数据的处理，包括但不限于对 MySQL、Redis、ES 等数据源的封装和操作。

* server-common  
基础组件层，主要是针对基础组件的封装，例如 mq、redis、es等

* server-test  
各个模块公用的测试组件，具体细节请阅读该目录下的 [README.md](./server-test/README.md)

## 快捷命令
* 启动容器内的所有资源
```
cd devops && ./start.sh
```
* 运行模块下的所有测试，包括单元测试和集成测试  
```
mvn clean test -P test-suit
```
* 打包 server-web 模块
```
mvn clean install -pl server-web -am -Dmaven.test.skip=true
```
 ## 我们的团队博客
 [叫叫阅读研发团队博客](https://jojoreading-public.github.io)
