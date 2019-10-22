# server-test

## 配置文件说明
* apollo-config  
这个文件夹下面存放的是各个模块中需要的配置文件，命名格式为 "module-" + 扩展格式（目前支持 .properties, .yml）  
配置文件内部则必须以模块名开头，例如 module-common.properties 配置文件中的配置项必须以 common 作为前缀

* docker.properties  
这个文件中配置了 devops 中初始化的 Docker 资源，测试基础程序会根据这些配置信息自动初始化   
apollo-config 文件夹中的配置文件，自动初始化容器内的数据（目前初始化 MySQL 和 Redis）,  
所以如果 Docker 中的资源有修改，请务必更新该配置文件

## 使用技巧
* 新增配置  
在 apollo-config 文件夹中对应模块配置文件中添加配置项，即使是创建新的配置文件，程序也会自动扫描加载

* 变更配置  
在 apollo 控制台中变更配置，确认没问题之后，再手动同步到 apollo-config 中，下次直接加载最新值
