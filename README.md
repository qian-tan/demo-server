# demo-server
演示程序，演示项目结构、单元测试、集成测试等

## 准备事项
* 安装最新版本的 Docker

## 目录结构
* devops  
里面存放了项目相关的一些外部依赖，目前包括 MySQL, Redis, Apollo 等  
启动项目或者运行测试之前先启动 devops 环境，请阅读该目录下的 [README.md](./devops/README.md)

* server-common  
基础组件层，主要是针对基础组件的封装，例如 mq、redis、es等

* server-test  
各个模块公用的测试组件，具体细节请阅读该目录下的 [README.md](./server-test/README.md)