/*
 Navicat Premium Data Transfer

 Source Server         : local-13306
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:13306
 Source Schema         : ApolloConfigDB

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 22/10/2019 10:43:18
*/

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `ApolloConfigDB` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `ApolloConfigDB`;

-- ----------------------------
-- Table structure for App
-- ----------------------------
DROP TABLE IF EXISTS `App`;
CREATE TABLE `App` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `AppId` varchar(500) NOT NULL DEFAULT 'default' COMMENT 'AppID',
  `Name` varchar(500) NOT NULL DEFAULT 'default' COMMENT '应用名',
  `OrgId` varchar(32) NOT NULL DEFAULT 'default' COMMENT '部门Id',
  `OrgName` varchar(64) NOT NULL DEFAULT 'default' COMMENT '部门名字',
  `OwnerName` varchar(500) NOT NULL DEFAULT 'default' COMMENT 'ownerName',
  `OwnerEmail` varchar(500) NOT NULL DEFAULT 'default' COMMENT 'ownerEmail',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  KEY `AppId` (`AppId`(191)),
  KEY `DataChange_LastTime` (`DataChange_LastTime`),
  KEY `IX_Name` (`Name`(191))
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='应用表';

-- ----------------------------
-- Records of App
-- ----------------------------
BEGIN;
INSERT INTO `App` VALUES (1, 'SampleApp', 'Sample App', 'TEST1', '样例部门1', 'apollo', 'apollo@acme.com', b'1', 'default', '2019-10-21 22:29:49', 'apollo', '2019-10-22 10:36:21');
INSERT INTO `App` VALUES (2, 'dev-app', 'dev-app', 'SharedServices', '基础架构部门', 'apollo', 'apollo@acme.com', b'0', 'apollo', '2019-10-22 10:40:22', 'apollo', '2019-10-22 10:40:22');
COMMIT;

-- ----------------------------
-- Table structure for AppNamespace
-- ----------------------------
DROP TABLE IF EXISTS `AppNamespace`;
CREATE TABLE `AppNamespace` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `Name` varchar(32) NOT NULL DEFAULT '' COMMENT 'namespace名字，注意，需要全局唯一',
  `AppId` varchar(32) NOT NULL DEFAULT '' COMMENT 'app id',
  `Format` varchar(32) NOT NULL DEFAULT 'properties' COMMENT 'namespace的format类型',
  `IsPublic` bit(1) NOT NULL DEFAULT b'0' COMMENT 'namespace是否为公共',
  `Comment` varchar(64) NOT NULL DEFAULT '' COMMENT '注释',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  KEY `IX_AppId` (`AppId`),
  KEY `Name_AppId` (`Name`,`AppId`),
  KEY `DataChange_LastTime` (`DataChange_LastTime`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='应用namespace定义';

-- ----------------------------
-- Records of AppNamespace
-- ----------------------------
BEGIN;
INSERT INTO `AppNamespace` VALUES (1, 'application', 'SampleApp', 'properties', b'0', 'default app namespace', b'1', '', '2019-10-21 22:29:49', 'apollo', '2019-10-22 10:36:20');
INSERT INTO `AppNamespace` VALUES (2, 'application', 'dev-app', 'properties', b'0', 'default app namespace', b'0', 'apollo', '2019-10-22 10:40:22', 'apollo', '2019-10-22 10:40:22');
COMMIT;

-- ----------------------------
-- Table structure for Audit
-- ----------------------------
DROP TABLE IF EXISTS `Audit`;
CREATE TABLE `Audit` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `EntityName` varchar(50) NOT NULL DEFAULT 'default' COMMENT '表名',
  `EntityId` int(10) unsigned DEFAULT NULL COMMENT '记录ID',
  `OpName` varchar(50) NOT NULL DEFAULT 'default' COMMENT '操作类型',
  `Comment` varchar(500) DEFAULT NULL COMMENT '备注',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  KEY `DataChange_LastTime` (`DataChange_LastTime`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='日志审计表';

-- ----------------------------
-- Records of Audit
-- ----------------------------
BEGIN;
INSERT INTO `Audit` VALUES (1, 'Namespace', 1, 'DELETE', NULL, b'0', 'apollo', '2019-10-22 10:36:21', NULL, '2019-10-22 10:36:21');
INSERT INTO `Audit` VALUES (2, 'Cluster', 1, 'DELETE', NULL, b'0', 'apollo', '2019-10-22 10:36:21', NULL, '2019-10-22 10:36:21');
INSERT INTO `Audit` VALUES (3, 'App', 1, 'DELETE', NULL, b'0', 'apollo', '2019-10-22 10:36:21', NULL, '2019-10-22 10:36:21');
INSERT INTO `Audit` VALUES (4, 'App', 2, 'INSERT', NULL, b'0', 'apollo', '2019-10-22 10:40:22', NULL, '2019-10-22 10:40:22');
INSERT INTO `Audit` VALUES (5, 'AppNamespace', 2, 'INSERT', NULL, b'0', 'apollo', '2019-10-22 10:40:22', NULL, '2019-10-22 10:40:22');
INSERT INTO `Audit` VALUES (6, 'Cluster', 2, 'INSERT', NULL, b'0', 'apollo', '2019-10-22 10:40:22', NULL, '2019-10-22 10:40:22');
INSERT INTO `Audit` VALUES (7, 'Namespace', 2, 'INSERT', NULL, b'0', 'apollo', '2019-10-22 10:40:22', NULL, '2019-10-22 10:40:22');
COMMIT;

-- ----------------------------
-- Table structure for Cluster
-- ----------------------------
DROP TABLE IF EXISTS `Cluster`;
CREATE TABLE `Cluster` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `Name` varchar(32) NOT NULL DEFAULT '' COMMENT '集群名字',
  `AppId` varchar(32) NOT NULL DEFAULT '' COMMENT 'App id',
  `ParentClusterId` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '父cluster',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  KEY `IX_AppId_Name` (`AppId`,`Name`),
  KEY `IX_ParentClusterId` (`ParentClusterId`),
  KEY `DataChange_LastTime` (`DataChange_LastTime`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='集群';

-- ----------------------------
-- Records of Cluster
-- ----------------------------
BEGIN;
INSERT INTO `Cluster` VALUES (1, 'default', 'SampleApp', 0, b'1', '', '2019-10-21 22:29:49', 'apollo', '2019-10-22 10:36:21');
INSERT INTO `Cluster` VALUES (2, 'default', 'dev-app', 0, b'0', 'apollo', '2019-10-22 10:40:22', 'apollo', '2019-10-22 10:40:22');
COMMIT;

-- ----------------------------
-- Table structure for Commit
-- ----------------------------
DROP TABLE IF EXISTS `Commit`;
CREATE TABLE `Commit` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ChangeSets` longtext NOT NULL COMMENT '修改变更集',
  `AppId` varchar(500) NOT NULL DEFAULT 'default' COMMENT 'AppID',
  `ClusterName` varchar(500) NOT NULL DEFAULT 'default' COMMENT 'ClusterName',
  `NamespaceName` varchar(500) NOT NULL DEFAULT 'default' COMMENT 'namespaceName',
  `Comment` varchar(500) DEFAULT NULL COMMENT '备注',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  KEY `DataChange_LastTime` (`DataChange_LastTime`),
  KEY `AppId` (`AppId`(191)),
  KEY `ClusterName` (`ClusterName`(191)),
  KEY `NamespaceName` (`NamespaceName`(191))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='commit 历史表';

-- ----------------------------
-- Table structure for GrayReleaseRule
-- ----------------------------
DROP TABLE IF EXISTS `GrayReleaseRule`;
CREATE TABLE `GrayReleaseRule` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `AppId` varchar(32) NOT NULL DEFAULT 'default' COMMENT 'AppID',
  `ClusterName` varchar(32) NOT NULL DEFAULT 'default' COMMENT 'Cluster Name',
  `NamespaceName` varchar(32) NOT NULL DEFAULT 'default' COMMENT 'Namespace Name',
  `BranchName` varchar(32) NOT NULL DEFAULT 'default' COMMENT 'branch name',
  `Rules` varchar(16000) DEFAULT '[]' COMMENT '灰度规则',
  `ReleaseId` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '灰度对应的release',
  `BranchStatus` tinyint(2) DEFAULT '1' COMMENT '灰度分支状态: 0:删除分支,1:正在使用的规则 2：全量发布',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  KEY `DataChange_LastTime` (`DataChange_LastTime`),
  KEY `IX_Namespace` (`AppId`,`ClusterName`,`NamespaceName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='灰度规则表';

-- ----------------------------
-- Table structure for Instance
-- ----------------------------
DROP TABLE IF EXISTS `Instance`;
CREATE TABLE `Instance` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `AppId` varchar(32) NOT NULL DEFAULT 'default' COMMENT 'AppID',
  `ClusterName` varchar(32) NOT NULL DEFAULT 'default' COMMENT 'ClusterName',
  `DataCenter` varchar(64) NOT NULL DEFAULT 'default' COMMENT 'Data Center Name',
  `Ip` varchar(32) NOT NULL DEFAULT '' COMMENT 'instance ip',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `IX_UNIQUE_KEY` (`AppId`,`ClusterName`,`Ip`,`DataCenter`),
  KEY `IX_IP` (`Ip`),
  KEY `IX_DataChange_LastTime` (`DataChange_LastTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='使用配置的应用实例';

-- ----------------------------
-- Table structure for InstanceConfig
-- ----------------------------
DROP TABLE IF EXISTS `InstanceConfig`;
CREATE TABLE `InstanceConfig` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `InstanceId` int(11) unsigned DEFAULT NULL COMMENT 'Instance Id',
  `ConfigAppId` varchar(32) NOT NULL DEFAULT 'default' COMMENT 'Config App Id',
  `ConfigClusterName` varchar(32) NOT NULL DEFAULT 'default' COMMENT 'Config Cluster Name',
  `ConfigNamespaceName` varchar(32) NOT NULL DEFAULT 'default' COMMENT 'Config Namespace Name',
  `ReleaseKey` varchar(64) NOT NULL DEFAULT '' COMMENT '发布的Key',
  `ReleaseDeliveryTime` timestamp NULL DEFAULT NULL COMMENT '配置获取时间',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `IX_UNIQUE_KEY` (`InstanceId`,`ConfigAppId`,`ConfigNamespaceName`),
  KEY `IX_ReleaseKey` (`ReleaseKey`),
  KEY `IX_DataChange_LastTime` (`DataChange_LastTime`),
  KEY `IX_Valid_Namespace` (`ConfigAppId`,`ConfigClusterName`,`ConfigNamespaceName`,`DataChange_LastTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应用实例的配置信息';

-- ----------------------------
-- Table structure for Item
-- ----------------------------
DROP TABLE IF EXISTS `Item`;
CREATE TABLE `Item` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `NamespaceId` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '集群NamespaceId',
  `Key` varchar(128) NOT NULL DEFAULT 'default' COMMENT '配置项Key',
  `Value` longtext NOT NULL COMMENT '配置项值',
  `Comment` varchar(1024) DEFAULT '' COMMENT '注释',
  `LineNum` int(10) unsigned DEFAULT '0' COMMENT '行号',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  KEY `IX_GroupId` (`NamespaceId`),
  KEY `DataChange_LastTime` (`DataChange_LastTime`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='配置项目';

-- ----------------------------
-- Records of Item
-- ----------------------------
BEGIN;
INSERT INTO `Item` VALUES (1, 1, 'timeout', '100', 'sample timeout配置', 1, b'1', 'default', '2019-10-21 22:29:49', 'apollo', '2019-10-22 10:36:20');
COMMIT;

-- ----------------------------
-- Table structure for Namespace
-- ----------------------------
DROP TABLE IF EXISTS `Namespace`;
CREATE TABLE `Namespace` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `AppId` varchar(500) NOT NULL DEFAULT 'default' COMMENT 'AppID',
  `ClusterName` varchar(500) NOT NULL DEFAULT 'default' COMMENT 'Cluster Name',
  `NamespaceName` varchar(500) NOT NULL DEFAULT 'default' COMMENT 'Namespace Name',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  KEY `AppId_ClusterName_NamespaceName` (`AppId`(191),`ClusterName`(191),`NamespaceName`(191)),
  KEY `DataChange_LastTime` (`DataChange_LastTime`),
  KEY `IX_NamespaceName` (`NamespaceName`(191))
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='命名空间';

-- ----------------------------
-- Records of Namespace
-- ----------------------------
BEGIN;
INSERT INTO `Namespace` VALUES (1, 'SampleApp', 'default', 'application', b'1', 'default', '2019-10-21 22:29:49', 'apollo', '2019-10-22 10:36:21');
INSERT INTO `Namespace` VALUES (2, 'dev-app', 'default', 'application', b'0', 'apollo', '2019-10-22 10:40:22', 'apollo', '2019-10-22 10:40:22');
COMMIT;

-- ----------------------------
-- Table structure for NamespaceLock
-- ----------------------------
DROP TABLE IF EXISTS `NamespaceLock`;
CREATE TABLE `NamespaceLock` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `NamespaceId` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '集群NamespaceId',
  `DataChange_CreatedBy` varchar(32) NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) DEFAULT 'default' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `IsDeleted` bit(1) DEFAULT b'0' COMMENT '软删除',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `IX_NamespaceId` (`NamespaceId`),
  KEY `DataChange_LastTime` (`DataChange_LastTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='namespace的编辑锁';

-- ----------------------------
-- Table structure for Release
-- ----------------------------
DROP TABLE IF EXISTS `Release`;
CREATE TABLE `Release` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `ReleaseKey` varchar(64) NOT NULL DEFAULT '' COMMENT '发布的Key',
  `Name` varchar(64) NOT NULL DEFAULT 'default' COMMENT '发布名字',
  `Comment` varchar(256) DEFAULT NULL COMMENT '发布说明',
  `AppId` varchar(500) NOT NULL DEFAULT 'default' COMMENT 'AppID',
  `ClusterName` varchar(500) NOT NULL DEFAULT 'default' COMMENT 'ClusterName',
  `NamespaceName` varchar(500) NOT NULL DEFAULT 'default' COMMENT 'namespaceName',
  `Configurations` longtext NOT NULL COMMENT '发布配置',
  `IsAbandoned` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否废弃',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  KEY `AppId_ClusterName_GroupName` (`AppId`(191),`ClusterName`(191),`NamespaceName`(191)),
  KEY `DataChange_LastTime` (`DataChange_LastTime`),
  KEY `IX_ReleaseKey` (`ReleaseKey`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='发布';

-- ----------------------------
-- Records of Release
-- ----------------------------
BEGIN;
INSERT INTO `Release` VALUES (1, '20161009155425-d3a0749c6e20bc15', '20161009155424-release', 'Sample发布', 'SampleApp', 'default', 'application', '{\"timeout\":\"100\"}', b'0', b'1', 'default', '2019-10-21 22:29:49', 'apollo', '2019-10-22 10:36:20');
COMMIT;

-- ----------------------------
-- Table structure for ReleaseHistory
-- ----------------------------
DROP TABLE IF EXISTS `ReleaseHistory`;
CREATE TABLE `ReleaseHistory` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `AppId` varchar(32) NOT NULL DEFAULT 'default' COMMENT 'AppID',
  `ClusterName` varchar(32) NOT NULL DEFAULT 'default' COMMENT 'ClusterName',
  `NamespaceName` varchar(32) NOT NULL DEFAULT 'default' COMMENT 'namespaceName',
  `BranchName` varchar(32) NOT NULL DEFAULT 'default' COMMENT '发布分支名',
  `ReleaseId` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '关联的Release Id',
  `PreviousReleaseId` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '前一次发布的ReleaseId',
  `Operation` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '发布类型，0: 普通发布，1: 回滚，2: 灰度发布，3: 灰度规则更新，4: 灰度合并回主分支发布，5: 主分支发布灰度自动发布，6: 主分支回滚灰度自动发布，7: 放弃灰度',
  `OperationContext` longtext NOT NULL COMMENT '发布上下文信息',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  KEY `IX_Namespace` (`AppId`,`ClusterName`,`NamespaceName`,`BranchName`),
  KEY `IX_ReleaseId` (`ReleaseId`),
  KEY `IX_DataChange_LastTime` (`DataChange_LastTime`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='发布历史';

-- ----------------------------
-- Records of ReleaseHistory
-- ----------------------------
BEGIN;
INSERT INTO `ReleaseHistory` VALUES (1, 'SampleApp', 'default', 'application', 'default', 1, 0, 0, '{}', b'1', 'apollo', '2019-10-21 22:29:49', 'apollo', '2019-10-22 10:36:20');
COMMIT;

-- ----------------------------
-- Table structure for ReleaseMessage
-- ----------------------------
DROP TABLE IF EXISTS `ReleaseMessage`;
CREATE TABLE `ReleaseMessage` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `Message` varchar(1024) NOT NULL DEFAULT '' COMMENT '发布的消息内容',
  `DataChange_LastTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  KEY `DataChange_LastTime` (`DataChange_LastTime`),
  KEY `IX_Message` (`Message`(191))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='发布消息';

-- ----------------------------
-- Records of ReleaseMessage
-- ----------------------------
BEGIN;
INSERT INTO `ReleaseMessage` VALUES (1, 'SampleApp+default+application', '2019-10-22 10:36:21');
COMMIT;

-- ----------------------------
-- Table structure for ServerConfig
-- ----------------------------
DROP TABLE IF EXISTS `ServerConfig`;
CREATE TABLE `ServerConfig` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `Key` varchar(64) NOT NULL DEFAULT 'default' COMMENT '配置项Key',
  `Cluster` varchar(32) NOT NULL DEFAULT 'default' COMMENT '配置对应的集群，default为不针对特定的集群',
  `Value` varchar(2048) NOT NULL DEFAULT 'default' COMMENT '配置项值',
  `Comment` varchar(1024) DEFAULT '' COMMENT '注释',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  KEY `IX_Key` (`Key`),
  KEY `DataChange_LastTime` (`DataChange_LastTime`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='配置服务自身配置';

-- ----------------------------
-- Records of ServerConfig
-- ----------------------------
BEGIN;
INSERT INTO `ServerConfig` VALUES (1, 'eureka.service.url', 'default', 'http://localhost:8080/eureka/', 'Eureka服务Url，多个service以英文逗号分隔', b'0', 'default', '2019-10-21 22:29:49', '', '2019-10-21 22:29:49');
INSERT INTO `ServerConfig` VALUES (2, 'namespace.lock.switch', 'default', 'false', '一次发布只能有一个人修改开关', b'0', 'default', '2019-10-21 22:29:49', '', '2019-10-21 22:29:49');
INSERT INTO `ServerConfig` VALUES (3, 'item.value.length.limit', 'default', '20000', 'item value最大长度限制', b'0', 'default', '2019-10-21 22:29:49', '', '2019-10-21 22:29:49');
INSERT INTO `ServerConfig` VALUES (4, 'config-service.cache.enabled', 'default', 'false', 'ConfigService是否开启缓存，开启后能提高性能，但是会增大内存消耗！', b'0', 'default', '2019-10-21 22:29:49', '', '2019-10-21 22:29:49');
INSERT INTO `ServerConfig` VALUES (5, 'item.key.length.limit', 'default', '128', 'item key 最大长度限制', b'0', 'default', '2019-10-21 22:29:49', '', '2019-10-21 22:29:49');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
