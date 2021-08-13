-- MySQL dump 10.13  Distrib 8.0.15, for macos10.14 (x86_64)
--
-- Host: localhost    Database: company_loan
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `data_company_loan`
--

DROP TABLE IF EXISTS `data_company_loan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `data_company_loan` (
  `company_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '企业id',
  `mzt_user_id` varchar(32) NOT NULL COMMENT '闽政通用户ID',
  `company_name` varchar(128) NOT NULL COMMENT '企业名称',
  `company_credit_code` varchar(32) DEFAULT NULL COMMENT '统一社会信用代码',
  `company_address` varchar(256) DEFAULT '' COMMENT '企业地址',
  `company_province` varchar(30) DEFAULT '' COMMENT '省份',
  `company_city` varchar(30) DEFAULT '' COMMENT '市',
  `company_area` varchar(30) DEFAULT '' COMMENT '区或县',
  `company_type` varchar(128) DEFAULT '' COMMENT '企业划型',
  `company_industry` varchar(128) DEFAULT '' COMMENT '所在行业',
  `company_business` varchar(1024) DEFAULT '' COMMENT '主营业务',
  `loan_band` varchar(64) DEFAULT '' COMMENT '意向银行（逗号隔开）',
  `loan_amount` decimal(12,2) NOT NULL COMMENT '贷款金额',
  `loan_first` char(1) DEFAULT '0' COMMENT '是否首次贷款（0是 1不是）',
  `loan_purpose` varchar(64) DEFAULT '' COMMENT '贷款用途',
  `remark` varchar(512) DEFAULT '' COMMENT '备注说明',
  `contact_name` varchar(16) DEFAULT '' COMMENT '联系人姓名',
  `contact_phone` varchar(16) DEFAULT '' COMMENT '联系人手机',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='企业贷款信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_company_loan`
--

LOCK TABLES `data_company_loan` WRITE;
/*!40000 ALTER TABLE `data_company_loan` DISABLE KEYS */;
-- INSERT INTO `data_company_loan` VALUES (10,'123','树蛙大数据有限公司','91350111MA8RE8BT17','','','','有限责任公司(自然人投资或控股)','互联网生产服务平台','一般项目：大数据服务；软件开发；计算机软硬件及辅助设备批发；计算机软硬件及辅助设备零售；信息咨询服务（不含许可类信息咨询服务）；线下数据处理服务；互联网数据服务；人工智能公共数据平台；数据处理服务；数据处理和存储支持服务；区块链技术相关软件和服务；生物质能资源数据库信息系统平台；物联网技术研发；互联网销售（除销售需要许可的商品）；信息系统集成服务（除依法须经批准的项目外，凭营业执照依法自主开展经营活动）n许可项目：出版物批发；出版物零售；出版物互联网销售（依法须经批准的项目，经相关部门批准后方可开展经营活动，具体经营项目以相关部门批准文件或许可证件为准）','1,2',1000.00,'0','测试','','张三','13655988863','0','2021-07-22 11:06:15',NULL),(11,'123','晋江交发科技有限公司','91350582MA8RFY385R','','','','有限责任公司(自然人投资或控股)','工程和技术研究和试验发展','一般项目：工程和技术研究和试验发展；砼结构构件销售；建筑装饰材料销售；建筑材料销售；建筑砌块销售；水泥制品销售；建筑用钢筋产品销售；金属制品销售；信息咨询服务（不含许可类信息咨询服务）（除依法须经批准的项目外，凭营业执照依法自主开展经营活动）n许可项目：建设工程设计（依法须经批准的项目，经相关部门批准后方可开展经营活动，具体经营项目以相关部门批准文件或许可证件为准）','1,2',100.00,'0','100','','张三','13655988863','0','2021-07-22 11:12:01',NULL),(12,'123','厦门恒言楠贸易有限公司','91350212MA333TTA77','','','','有限责任公司(自然人独资)','贸易代理','贸易代理；纺织品、针织品及原料批发；服装批发；鞋帽批发；化妆品及卫生用品批发；厨房、卫生间用具及日用杂货批发；灯具、装饰物品批发；家用电器批发；其他家庭用品批发；文具用品批发；其他文化用品批发；五金产品批发；电气设备批发；计算机、软件及辅助设备批发；其他机械设备及电子产品批发；纺织品及针织品零售；服装零售；鞋帽零售；化妆品及卫生用品零售；钟表、眼镜零售；箱、包零售；厨房用具及日用杂品零售；其他日用品零售；文具用品零售；其他文化用品零售；家用视听设备零售；日用家电设备零售；计算机、软件及辅助设备零售；通信设备零售；其他电子产品零售；五金零售；灯具零售；涂料零售。','1,3',1000.00,'0','1000','','张三','18805955908','0','2021-07-22 11:28:15',NULL);
/*!40000 ALTER TABLE `data_company_loan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gen_table`
--

DROP TABLE IF EXISTS `gen_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `gen_table` (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='代码生成业务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_table`
--

LOCK TABLES `gen_table` WRITE;
/*!40000 ALTER TABLE `gen_table` DISABLE KEYS */;
INSERT INTO `gen_table` VALUES (1,'sys_dept','部门表',NULL,NULL,'SysDept','crud','com.ruoyi.system','system','dept','部门','ruoyi','0','/',NULL,'admin','2021-07-12 20:48:37','',NULL,NULL),(3,'data_company_loan','企业贷款信息表',NULL,NULL,'DataCompanyLoan','crud','com.ruoyi.system','system','loan','企业贷款信息','genius','0','/',NULL,'admin','2021-07-13 11:22:22','',NULL,NULL);
/*!40000 ALTER TABLE `gen_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gen_table_column`
--

DROP TABLE IF EXISTS `gen_table_column`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `gen_table_column` (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) DEFAULT '' COMMENT '字典类型',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8 COMMENT='代码生成业务表字段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_table_column`
--

LOCK TABLES `gen_table_column` WRITE;
/*!40000 ALTER TABLE `gen_table_column` DISABLE KEYS */;
INSERT INTO `gen_table_column` VALUES (1,'1','dept_id','部门id','bigint(20)','Long','deptId','1','1',NULL,'1',NULL,NULL,NULL,'EQ','input','',1,'admin','2021-07-12 20:48:37','',NULL),(2,'1','parent_id','父部门id','bigint(20)','Long','parentId','0','0',NULL,'1','1','1','1','EQ','input','',2,'admin','2021-07-12 20:48:37','',NULL),(3,'1','ancestors','祖级列表','varchar(50)','String','ancestors','0','0',NULL,'1','1','1','1','EQ','input','',3,'admin','2021-07-12 20:48:37','',NULL),(4,'1','dept_name','部门名称','varchar(30)','String','deptName','0','0',NULL,'1','1','1','1','LIKE','input','',4,'admin','2021-07-12 20:48:37','',NULL),(5,'1','order_num','显示顺序','int(4)','Integer','orderNum','0','0',NULL,'1','1','1','1','EQ','input','',5,'admin','2021-07-12 20:48:37','',NULL),(6,'1','leader','负责人','varchar(20)','String','leader','0','0',NULL,'1','1','1','1','EQ','input','',6,'admin','2021-07-12 20:48:37','',NULL),(7,'1','phone','联系电话','varchar(11)','String','phone','0','0',NULL,'1','1','1','1','EQ','input','',7,'admin','2021-07-12 20:48:37','',NULL),(8,'1','email','邮箱','varchar(50)','String','email','0','0',NULL,'1','1','1','1','EQ','input','',8,'admin','2021-07-12 20:48:37','',NULL),(9,'1','status','部门状态（0正常 1停用）','char(1)','String','status','0','0',NULL,'1','1','1','1','EQ','radio','',9,'admin','2021-07-12 20:48:37','',NULL),(10,'1','del_flag','删除标志（0代表存在 2代表删除）','char(1)','String','delFlag','0','0',NULL,'1',NULL,NULL,NULL,'EQ','input','',10,'admin','2021-07-12 20:48:37','',NULL),(11,'1','create_by','创建者','varchar(64)','String','createBy','0','0',NULL,'1',NULL,NULL,NULL,'EQ','input','',11,'admin','2021-07-12 20:48:37','',NULL),(12,'1','create_time','创建时间','datetime','Date','createTime','0','0',NULL,'1',NULL,NULL,NULL,'EQ','datetime','',12,'admin','2021-07-12 20:48:37','',NULL),(13,'1','update_by','更新者','varchar(64)','String','updateBy','0','0',NULL,'1','1',NULL,NULL,'EQ','input','',13,'admin','2021-07-12 20:48:37','',NULL),(14,'1','update_time','更新时间','datetime','Date','updateTime','0','0',NULL,'1','1',NULL,NULL,'EQ','datetime','',14,'admin','2021-07-12 20:48:37','',NULL),(35,'3','company_id','企业id','bigint(20)','Long','companyId','1','1',NULL,'1',NULL,NULL,NULL,'EQ','input','',1,'admin','2021-07-13 11:22:22','',NULL),(36,'3','mzt_user_id','闽政通用户ID','varchar(20)','String','mztUserId','0','0','1','1','1','1','1','EQ','input','',2,'admin','2021-07-13 11:22:22','',NULL),(37,'3','company_name','企业名称','varchar(64)','String','companyName','0','0','1','1','1','1','1','LIKE','input','',3,'admin','2021-07-13 11:22:22','',NULL),(38,'3','company_credit_code','统一社会信用代码','varchar(32)','String','companyCreditCode','0','0','1','1','1','1','1','EQ','input','',4,'admin','2021-07-13 11:22:22','',NULL),(39,'3','company_province','省份','varchar(30)','String','companyProvince','0','0',NULL,'1','1','1','1','EQ','input','',5,'admin','2021-07-13 11:22:22','',NULL),(40,'3','company_city','市','varchar(30)','String','companyCity','0','0',NULL,'1','1','1','1','EQ','input','',6,'admin','2021-07-13 11:22:22','',NULL),(41,'3','company_area','区或县','varchar(30)','String','companyArea','0','0',NULL,'1','1','1','1','EQ','input','',7,'admin','2021-07-13 11:22:22','',NULL),(42,'3','company_type','企业划型','varchar(32)','String','companyType','0','0',NULL,'1','1','1','1','EQ','select','',8,'admin','2021-07-13 11:22:22','',NULL),(43,'3','company_industry','所在行业','varchar(64)','String','companyIndustry','0','0',NULL,'1','1','1','1','EQ','input','',9,'admin','2021-07-13 11:22:22','',NULL),(44,'3','company_business','主营业务','varchar(128)','String','companyBusiness','0','0',NULL,'1','1','1','1','EQ','input','',10,'admin','2021-07-13 11:22:22','',NULL),(45,'3','loan_band','意向银行（逗号隔开）','varchar(64)','String','loanBand','0','0',NULL,'1','1','1','1','EQ','input','',11,'admin','2021-07-13 11:22:22','',NULL),(46,'3','loan_amount','贷款金额','decimal(18,2)','BigDecimal','loanAmount','0','0','1','1','1','1','1','EQ','input','',12,'admin','2021-07-13 11:22:22','',NULL),(47,'3','loan_first','是否首次贷款（0是 1不是）','char(1)','String','loanFirst','0','0',NULL,'1','1','1','1','EQ','input','',13,'admin','2021-07-13 11:22:22','',NULL),(48,'3','loan_purpose','贷款用途','varchar(64)','String','loanPurpose','0','0',NULL,'1','1','1','1','EQ','input','',14,'admin','2021-07-13 11:22:22','',NULL),(49,'3','remark','备注说明','varchar(128)','String','remark','0','0',NULL,'1','1','1',NULL,'EQ','input','',15,'admin','2021-07-13 11:22:22','',NULL),(50,'3','contact_name','联系人姓名','varchar(16)','String','contactName','0','0',NULL,'1','1','1','1','LIKE','input','',16,'admin','2021-07-13 11:22:22','',NULL),(51,'3','contact_phone','联系人手机','varchar(16)','String','contactPhone','0','0',NULL,'1','1','1','1','EQ','input','',17,'admin','2021-07-13 11:22:22','',NULL),(52,'3','del_flag','删除标志（0代表存在 2代表删除）','char(1)','String','delFlag','0','0',NULL,'1',NULL,NULL,NULL,'EQ','input','',18,'admin','2021-07-13 11:22:22','',NULL),(53,'3','create_time','创建时间','datetime','Date','createTime','0','0',NULL,'1',NULL,NULL,NULL,'EQ','datetime','',19,'admin','2021-07-13 11:22:22','',NULL),(54,'3','update_time','更新时间','datetime','Date','updateTime','0','0',NULL,'1','1',NULL,NULL,'EQ','datetime','',20,'admin','2021-07-13 11:22:22','',NULL);
/*!40000 ALTER TABLE `gen_table_column` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_BLOB_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `blob_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_BLOB_TRIGGERS`
--

LOCK TABLES `QRTZ_BLOB_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_BLOB_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_BLOB_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_CALENDARS`
--

DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `QRTZ_CALENDARS` (
  `sched_name` varchar(120) NOT NULL,
  `calendar_name` varchar(200) NOT NULL,
  `calendar` blob NOT NULL,
  PRIMARY KEY (`sched_name`,`calendar_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_CALENDARS`
--

LOCK TABLES `QRTZ_CALENDARS` WRITE;
/*!40000 ALTER TABLE `QRTZ_CALENDARS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_CALENDARS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_CRON_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `cron_expression` varchar(200) NOT NULL,
  `time_zone_id` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_CRON_TRIGGERS`
--

LOCK TABLES `QRTZ_CRON_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_CRON_TRIGGERS` DISABLE KEYS */;
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('RuoyiScheduler','TASK_CLASS_NAME1','DEFAULT','0/10 * * * * ?','Asia/Shanghai'),('RuoyiScheduler','TASK_CLASS_NAME2','DEFAULT','0/15 * * * * ?','Asia/Shanghai'),('RuoyiScheduler','TASK_CLASS_NAME3','DEFAULT','0/20 * * * * ?','Asia/Shanghai');
/*!40000 ALTER TABLE `QRTZ_CRON_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_FIRED_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL,
  `entry_id` varchar(95) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `instance_name` varchar(200) NOT NULL,
  `fired_time` bigint(13) NOT NULL,
  `sched_time` bigint(13) NOT NULL,
  `priority` int(11) NOT NULL,
  `state` varchar(16) NOT NULL,
  `job_name` varchar(200) DEFAULT NULL,
  `job_group` varchar(200) DEFAULT NULL,
  `is_nonconcurrent` varchar(1) DEFAULT NULL,
  `requests_recovery` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_FIRED_TRIGGERS`
--

LOCK TABLES `QRTZ_FIRED_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_FIRED_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_FIRED_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_JOB_DETAILS`
--

DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `sched_name` varchar(120) NOT NULL,
  `job_name` varchar(200) NOT NULL,
  `job_group` varchar(200) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `job_class_name` varchar(250) NOT NULL,
  `is_durable` varchar(1) NOT NULL,
  `is_nonconcurrent` varchar(1) NOT NULL,
  `is_update_data` varchar(1) NOT NULL,
  `requests_recovery` varchar(1) NOT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_JOB_DETAILS`
--

LOCK TABLES `QRTZ_JOB_DETAILS` WRITE;
/*!40000 ALTER TABLE `QRTZ_JOB_DETAILS` DISABLE KEYS */;
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('RuoyiScheduler','TASK_CLASS_NAME1','DEFAULT',NULL,'com.ruoyi.quartz.util.QuartzDisallowConcurrentExecution','0','1','0','0',_binary '�\�\0sr\0org.quartz.JobDataMap���迩�\�\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�\�\��\�](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\�.�(v\n\�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\��\�`\�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0TASK_PROPERTIESsr\0com.ruoyi.quartz.domain.SysJob\0\0\0\0\0\0\0\0L\0\nconcurrentt\0Ljava/lang/String;L\0cronExpressionq\0~\0	L\0invokeTargetq\0~\0	L\0jobGroupq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0jobNameq\0~\0	L\0\rmisfirePolicyq\0~\0	L\0statusq\0~\0	xr\0\'com.ruoyi.common.core.domain.BaseEntity\0\0\0\0\0\0\0\0L\0createByq\0~\0	L\0\ncreateTimet\0Ljava/util/Date;L\0paramsq\0~\0L\0remarkq\0~\0	L\0searchValueq\0~\0	L\0updateByq\0~\0	L\0\nupdateTimeq\0~\0xpt\0adminsr\0java.util.Datehj�KYt\0\0xpw\0\0z�J\�\�xpt\0\0pppt\01t\00/10 * * * * ?t\0ryTask.ryNoParamst\0DEFAULTsr\0java.lang.Long;�\�̏#\�\0J\0valuexr\0java.lang.Number����\��\0\0xp\0\0\0\0\0\0\0t\0系统默认（无参）t\03t\01x\0'),('RuoyiScheduler','TASK_CLASS_NAME2','DEFAULT',NULL,'com.ruoyi.quartz.util.QuartzDisallowConcurrentExecution','0','1','0','0',_binary '�\�\0sr\0org.quartz.JobDataMap���迩�\�\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�\�\��\�](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\�.�(v\n\�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\��\�`\�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0TASK_PROPERTIESsr\0com.ruoyi.quartz.domain.SysJob\0\0\0\0\0\0\0\0L\0\nconcurrentt\0Ljava/lang/String;L\0cronExpressionq\0~\0	L\0invokeTargetq\0~\0	L\0jobGroupq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0jobNameq\0~\0	L\0\rmisfirePolicyq\0~\0	L\0statusq\0~\0	xr\0\'com.ruoyi.common.core.domain.BaseEntity\0\0\0\0\0\0\0\0L\0createByq\0~\0	L\0\ncreateTimet\0Ljava/util/Date;L\0paramsq\0~\0L\0remarkq\0~\0	L\0searchValueq\0~\0	L\0updateByq\0~\0	L\0\nupdateTimeq\0~\0xpt\0adminsr\0java.util.Datehj�KYt\0\0xpw\0\0z�J\�\�xpt\0\0pppt\01t\00/15 * * * * ?t\0ryTask.ryParams(\'ry\')t\0DEFAULTsr\0java.lang.Long;�\�̏#\�\0J\0valuexr\0java.lang.Number����\��\0\0xp\0\0\0\0\0\0\0t\0系统默认（有参）t\03t\01x\0'),('RuoyiScheduler','TASK_CLASS_NAME3','DEFAULT',NULL,'com.ruoyi.quartz.util.QuartzDisallowConcurrentExecution','0','1','0','0',_binary '�\�\0sr\0org.quartz.JobDataMap���迩�\�\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�\�\��\�](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\�.�(v\n\�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\��\�`\�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0TASK_PROPERTIESsr\0com.ruoyi.quartz.domain.SysJob\0\0\0\0\0\0\0\0L\0\nconcurrentt\0Ljava/lang/String;L\0cronExpressionq\0~\0	L\0invokeTargetq\0~\0	L\0jobGroupq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0jobNameq\0~\0	L\0\rmisfirePolicyq\0~\0	L\0statusq\0~\0	xr\0\'com.ruoyi.common.core.domain.BaseEntity\0\0\0\0\0\0\0\0L\0createByq\0~\0	L\0\ncreateTimet\0Ljava/util/Date;L\0paramsq\0~\0L\0remarkq\0~\0	L\0searchValueq\0~\0	L\0updateByq\0~\0	L\0\nupdateTimeq\0~\0xpt\0adminsr\0java.util.Datehj�KYt\0\0xpw\0\0z�J\�\�xpt\0\0pppt\01t\00/20 * * * * ?t\08ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)t\0DEFAULTsr\0java.lang.Long;�\�̏#\�\0J\0valuexr\0java.lang.Number����\��\0\0xp\0\0\0\0\0\0\0t\0系统默认（多参）t\03t\01x\0');
/*!40000 ALTER TABLE `QRTZ_JOB_DETAILS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_LOCKS`
--

DROP TABLE IF EXISTS `QRTZ_LOCKS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `QRTZ_LOCKS` (
  `sched_name` varchar(120) NOT NULL,
  `lock_name` varchar(40) NOT NULL,
  PRIMARY KEY (`sched_name`,`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_LOCKS`
--

LOCK TABLES `QRTZ_LOCKS` WRITE;
/*!40000 ALTER TABLE `QRTZ_LOCKS` DISABLE KEYS */;
INSERT INTO `QRTZ_LOCKS` VALUES ('RuoyiScheduler','STATE_ACCESS'),('RuoyiScheduler','TRIGGER_ACCESS');
/*!40000 ALTER TABLE `QRTZ_LOCKS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_PAUSED_TRIGGER_GRPS`
--

DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_PAUSED_TRIGGER_GRPS`
--

LOCK TABLES `QRTZ_PAUSED_TRIGGER_GRPS` WRITE;
/*!40000 ALTER TABLE `QRTZ_PAUSED_TRIGGER_GRPS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_PAUSED_TRIGGER_GRPS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_SCHEDULER_STATE`
--

DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `sched_name` varchar(120) NOT NULL,
  `instance_name` varchar(200) NOT NULL,
  `last_checkin_time` bigint(13) NOT NULL,
  `checkin_interval` bigint(13) NOT NULL,
  PRIMARY KEY (`sched_name`,`instance_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SCHEDULER_STATE`
--

LOCK TABLES `QRTZ_SCHEDULER_STATE` WRITE;
/*!40000 ALTER TABLE `QRTZ_SCHEDULER_STATE` DISABLE KEYS */;
INSERT INTO `QRTZ_SCHEDULER_STATE` VALUES ('RuoyiScheduler','geniusmartdeMacBook-Pro.local1626941628667',1626941811767,15000);
/*!40000 ALTER TABLE `QRTZ_SCHEDULER_STATE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_SIMPLE_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `repeat_count` bigint(7) NOT NULL,
  `repeat_interval` bigint(12) NOT NULL,
  `times_triggered` bigint(10) NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SIMPLE_TRIGGERS`
--

LOCK TABLES `QRTZ_SIMPLE_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_SIMPLE_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_SIMPLE_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_SIMPROP_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `str_prop_1` varchar(512) DEFAULT NULL,
  `str_prop_2` varchar(512) DEFAULT NULL,
  `str_prop_3` varchar(512) DEFAULT NULL,
  `int_prop_1` int(11) DEFAULT NULL,
  `int_prop_2` int(11) DEFAULT NULL,
  `long_prop_1` bigint(20) DEFAULT NULL,
  `long_prop_2` bigint(20) DEFAULT NULL,
  `dec_prop_1` decimal(13,4) DEFAULT NULL,
  `dec_prop_2` decimal(13,4) DEFAULT NULL,
  `bool_prop_1` varchar(1) DEFAULT NULL,
  `bool_prop_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SIMPROP_TRIGGERS`
--

LOCK TABLES `QRTZ_SIMPROP_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_SIMPROP_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_SIMPROP_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `QRTZ_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `job_name` varchar(200) NOT NULL,
  `job_group` varchar(200) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `next_fire_time` bigint(13) DEFAULT NULL,
  `prev_fire_time` bigint(13) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `trigger_state` varchar(16) NOT NULL,
  `trigger_type` varchar(8) NOT NULL,
  `start_time` bigint(13) NOT NULL,
  `end_time` bigint(13) DEFAULT NULL,
  `calendar_name` varchar(200) DEFAULT NULL,
  `misfire_instr` smallint(2) DEFAULT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_TRIGGERS`
--

LOCK TABLES `QRTZ_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_TRIGGERS` DISABLE KEYS */;
INSERT INTO `QRTZ_TRIGGERS` VALUES ('RuoyiScheduler','TASK_CLASS_NAME1','DEFAULT','TASK_CLASS_NAME1','DEFAULT',NULL,1626941630000,-1,5,'PAUSED','CRON',1626941628000,0,NULL,2,''),('RuoyiScheduler','TASK_CLASS_NAME2','DEFAULT','TASK_CLASS_NAME2','DEFAULT',NULL,1626941640000,-1,5,'PAUSED','CRON',1626941628000,0,NULL,2,''),('RuoyiScheduler','TASK_CLASS_NAME3','DEFAULT','TASK_CLASS_NAME3','DEFAULT',NULL,1626941640000,-1,5,'PAUSED','CRON',1626941628000,0,NULL,2,'');
/*!40000 ALTER TABLE `QRTZ_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_config` (
  `config_id` int(5) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='参数配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_config`
--

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` VALUES (1,'主框架页-默认皮肤样式名称','sys.index.skinName','skin-blue','Y','admin','2021-07-12 18:37:11','',NULL,'蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow'),(2,'用户管理-账号初始密码','sys.user.initPassword','123456','Y','admin','2021-07-12 18:37:11','',NULL,'初始化密码 123456'),(3,'主框架页-侧边栏主题','sys.index.sideTheme','theme-dark','Y','admin','2021-07-12 18:37:11','',NULL,'深色主题theme-dark，浅色主题theme-light');
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` char(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8 COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` VALUES (100,0,'0','若依科技',0,'若依','15888888888','ry@qq.com','0','0','admin','2021-07-12 18:37:11','',NULL),(101,100,'0,100','深圳总公司',1,'若依','15888888888','ry@qq.com','0','0','admin','2021-07-12 18:37:11','',NULL),(102,100,'0,100','长沙分公司',2,'若依','15888888888','ry@qq.com','0','0','admin','2021-07-12 18:37:11','',NULL),(103,101,'0,100,101','研发部门',1,'若依','15888888888','ry@qq.com','0','0','admin','2021-07-12 18:37:11','',NULL),(104,101,'0,100,101','市场部门',2,'若依','15888888888','ry@qq.com','0','0','admin','2021-07-12 18:37:11','',NULL),(105,101,'0,100,101','测试部门',3,'若依','15888888888','ry@qq.com','0','0','admin','2021-07-12 18:37:11','',NULL),(106,101,'0,100,101','财务部门',4,'若依','15888888888','ry@qq.com','0','0','admin','2021-07-12 18:37:11','',NULL),(107,101,'0,100,101','运维部门',5,'若依','15888888888','ry@qq.com','0','0','admin','2021-07-12 18:37:11','',NULL),(108,102,'0,100,102','市场部门',1,'若依','15888888888','ry@qq.com','0','0','admin','2021-07-12 18:37:11','',NULL),(109,102,'0,100,102','财务部门',2,'若依','15888888888','ry@qq.com','0','0','admin','2021-07-12 18:37:11','',NULL);
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_data`
--

DROP TABLE IF EXISTS `sys_dict_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_dict_data` (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB AUTO_INCREMENT=173 DEFAULT CHARSET=utf8 COMMENT='字典数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_data`
--

LOCK TABLES `sys_dict_data` WRITE;
/*!40000 ALTER TABLE `sys_dict_data` DISABLE KEYS */;
INSERT INTO `sys_dict_data` VALUES (1,1,'男','0','sys_user_sex','','','Y','0','admin','2021-07-12 18:37:11','',NULL,'性别男'),(2,2,'女','1','sys_user_sex','','','N','0','admin','2021-07-12 18:37:11','',NULL,'性别女'),(3,3,'未知','2','sys_user_sex','','','N','0','admin','2021-07-12 18:37:11','',NULL,'性别未知'),(4,1,'显示','0','sys_show_hide','','primary','Y','0','admin','2021-07-12 18:37:11','',NULL,'显示菜单'),(5,2,'隐藏','1','sys_show_hide','','danger','N','0','admin','2021-07-12 18:37:11','',NULL,'隐藏菜单'),(6,1,'正常','0','sys_normal_disable','','primary','Y','0','admin','2021-07-12 18:37:11','',NULL,'正常状态'),(7,2,'停用','1','sys_normal_disable','','danger','N','0','admin','2021-07-12 18:37:11','',NULL,'停用状态'),(8,1,'正常','0','sys_job_status','','primary','Y','0','admin','2021-07-12 18:37:11','',NULL,'正常状态'),(9,2,'暂停','1','sys_job_status','','danger','N','0','admin','2021-07-12 18:37:11','',NULL,'停用状态'),(10,1,'默认','DEFAULT','sys_job_group','','','Y','0','admin','2021-07-12 18:37:11','',NULL,'默认分组'),(11,2,'系统','SYSTEM','sys_job_group','','','N','0','admin','2021-07-12 18:37:11','',NULL,'系统分组'),(12,1,'是','Y','sys_yes_no','','primary','Y','0','admin','2021-07-12 18:37:11','',NULL,'系统默认是'),(13,2,'否','N','sys_yes_no','','danger','N','0','admin','2021-07-12 18:37:11','',NULL,'系统默认否'),(14,1,'通知','1','sys_notice_type','','warning','Y','0','admin','2021-07-12 18:37:11','',NULL,'通知'),(15,2,'公告','2','sys_notice_type','','success','N','0','admin','2021-07-12 18:37:11','',NULL,'公告'),(16,1,'正常','0','sys_notice_status','','primary','Y','0','admin','2021-07-12 18:37:11','',NULL,'正常状态'),(17,2,'关闭','1','sys_notice_status','','danger','N','0','admin','2021-07-12 18:37:11','',NULL,'关闭状态'),(18,1,'新增','1','sys_oper_type','','info','N','0','admin','2021-07-12 18:37:11','',NULL,'新增操作'),(19,2,'修改','2','sys_oper_type','','info','N','0','admin','2021-07-12 18:37:11','',NULL,'修改操作'),(20,3,'删除','3','sys_oper_type','','danger','N','0','admin','2021-07-12 18:37:11','',NULL,'删除操作'),(21,4,'授权','4','sys_oper_type','','primary','N','0','admin','2021-07-12 18:37:11','',NULL,'授权操作'),(22,5,'导出','5','sys_oper_type','','warning','N','0','admin','2021-07-12 18:37:11','',NULL,'导出操作'),(23,6,'导入','6','sys_oper_type','','warning','N','0','admin','2021-07-12 18:37:11','',NULL,'导入操作'),(24,7,'强退','7','sys_oper_type','','danger','N','0','admin','2021-07-12 18:37:11','',NULL,'强退操作'),(25,8,'生成代码','8','sys_oper_type','','warning','N','0','admin','2021-07-12 18:37:11','',NULL,'生成操作'),(26,9,'清空数据','9','sys_oper_type','','danger','N','0','admin','2021-07-12 18:37:11','',NULL,'清空操作'),(27,1,'成功','0','sys_common_status','','primary','N','0','admin','2021-07-12 18:37:11','',NULL,'正常状态'),(28,2,'失败','1','sys_common_status','','danger','N','0','admin','2021-07-12 18:37:11','',NULL,'停用状态'),(135,1,'农业发展银行泉州分行','1','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(136,2,'工商银行泉州分行','2','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(137,3,'农业银行泉州分行','3','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(138,4,'中国银行泉州分行','4','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(139,5,'建设银行泉州分行','5','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(140,6,'兴业银行泉州分行','6','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(141,7,'中信银行泉州分行','7','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(142,8,'招商银行泉州分行','8','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(143,9,'民生银行泉州分行','9','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(144,10,'交通银行泉州分行','10','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(145,11,'邮储银行泉州分行','11','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(146,12,'平安银行泉州分行','12','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(147,13,'光大银行泉州分行','13','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(148,14,'华夏银行泉州分行','14','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(149,15,'海峡银行泉州分行','15','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(150,16,'浦发银行泉州分行','16','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(151,17,'厦门银行泉州分行','17','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(152,18,'首都银行泉州分行','18','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(153,19,'厦门国际银行泉州分行','19','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(154,20,'恒丰银行泉州分行','20','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(155,21,'广发银行泉州分行','21','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(156,22,'渤海银行泉州分行','22','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(157,23,'泉州银行','23','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(158,24,'泉州农商行','24','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(159,25,'晋江农商行','25','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(160,26,'石狮农商行','26','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(161,27,'南安农商行','27','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(162,28,'安溪农商行','28','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(163,29,'惠安农信社','29','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(164,30,'永春农信社','30','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(165,31,'德化农信社','31','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(166,32,'安溪民生村镇银行','32','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(167,33,'惠安中成村镇银行','33','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(168,34,'南安汇通村镇银行','34','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(169,35,'石狮渝农商村镇银行','35','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(170,36,'台商投资区晋农商村镇银行','36','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(171,37,'永春漳农商村镇银行','37','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL),(172,38,'德化成功村镇银行','38','sys_user_band',NULL,'default','N','0','','2021-07-22 16:13:52','',NULL,NULL);
/*!40000 ALTER TABLE `sys_dict_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_type`
--

DROP TABLE IF EXISTS `sys_dict_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_dict_type` (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8 COMMENT='字典类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_type`
--

LOCK TABLES `sys_dict_type` WRITE;
/*!40000 ALTER TABLE `sys_dict_type` DISABLE KEYS */;
INSERT INTO `sys_dict_type` VALUES (1,'用户性别','sys_user_sex','0','admin','2021-07-12 18:37:11','',NULL,'用户性别列表'),(2,'菜单状态','sys_show_hide','0','admin','2021-07-12 18:37:11','',NULL,'菜单状态列表'),(3,'系统开关','sys_normal_disable','0','admin','2021-07-12 18:37:11','',NULL,'系统开关列表'),(4,'任务状态','sys_job_status','0','admin','2021-07-12 18:37:11','',NULL,'任务状态列表'),(5,'任务分组','sys_job_group','0','admin','2021-07-12 18:37:11','',NULL,'任务分组列表'),(6,'系统是否','sys_yes_no','0','admin','2021-07-12 18:37:11','',NULL,'系统是否列表'),(7,'通知类型','sys_notice_type','0','admin','2021-07-12 18:37:11','',NULL,'通知类型列表'),(8,'通知状态','sys_notice_status','0','admin','2021-07-12 18:37:11','',NULL,'通知状态列表'),(9,'操作类型','sys_oper_type','0','admin','2021-07-12 18:37:11','',NULL,'操作类型列表'),(10,'系统状态','sys_common_status','0','admin','2021-07-12 18:37:11','',NULL,'登录状态列表'),(100,'贷款银行','sys_user_band','0','admin','2021-07-18 16:53:24','',NULL,NULL);
/*!40000 ALTER TABLE `sys_dict_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job`
--

DROP TABLE IF EXISTS `sys_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='定时任务调度表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job`
--

LOCK TABLES `sys_job` WRITE;
/*!40000 ALTER TABLE `sys_job` DISABLE KEYS */;
INSERT INTO `sys_job` VALUES (1,'系统默认（无参）','DEFAULT','ryTask.ryNoParams','0/10 * * * * ?','3','1','1','admin','2021-07-12 18:37:11','',NULL,''),(2,'系统默认（有参）','DEFAULT','ryTask.ryParams(\'ry\')','0/15 * * * * ?','3','1','1','admin','2021-07-12 18:37:11','',NULL,''),(3,'系统默认（多参）','DEFAULT','ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)','0/20 * * * * ?','3','1','1','admin','2021-07-12 18:37:11','',NULL,'');
/*!40000 ALTER TABLE `sys_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job_log`
--

DROP TABLE IF EXISTS `sys_job_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_job_log` (
  `job_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) DEFAULT NULL COMMENT '日志信息',
  `status` char(1) DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) DEFAULT '' COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务调度日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job_log`
--

LOCK TABLES `sys_job_log` WRITE;
/*!40000 ALTER TABLE `sys_job_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_job_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_logininfor`
--

DROP TABLE IF EXISTS `sys_logininfor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_logininfor` (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` char(1) DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8 COMMENT='系统访问记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_logininfor`
--

LOCK TABLES `sys_logininfor` WRITE;
/*!40000 ALTER TABLE `sys_logininfor` DISABLE KEYS */;
INSERT INTO `sys_logininfor` VALUES (100,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','登录成功','2021-07-12 20:30:35'),(101,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','退出成功','2021-07-12 20:30:50'),(102,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','1','验证码已失效','2021-07-12 20:36:34'),(103,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','1','验证码错误','2021-07-12 20:36:39'),(104,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','登录成功','2021-07-12 20:36:43'),(105,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','退出成功','2021-07-12 20:44:05'),(106,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','登录成功','2021-07-12 20:44:12'),(107,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','登录成功','2021-07-13 07:37:35'),(108,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','退出成功','2021-07-13 07:40:09'),(109,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','登录成功','2021-07-13 07:40:20'),(110,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','退出成功','2021-07-13 07:44:22'),(111,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','1','验证码已失效','2021-07-13 07:48:08'),(112,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','1','验证码已失效','2021-07-13 08:48:24'),(113,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','登录成功','2021-07-13 08:48:30'),(114,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','1','验证码错误','2021-07-13 11:02:08'),(115,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','登录成功','2021-07-13 11:02:15'),(116,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','退出成功','2021-07-13 11:42:32'),(117,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','1','验证码已失效','2021-07-13 11:55:05'),(118,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','1','验证码错误','2021-07-13 11:55:09'),(119,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','登录成功','2021-07-13 11:55:12'),(120,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','登录成功','2021-07-13 16:07:41'),(121,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','1','验证码错误','2021-07-13 17:00:43'),(122,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','1','验证码错误','2021-07-13 17:00:46'),(123,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','登录成功','2021-07-13 17:00:50'),(124,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','登录成功','2021-07-13 17:50:16'),(125,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','登录成功','2021-07-15 15:30:04'),(126,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','退出成功','2021-07-15 15:33:01'),(127,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','登录成功','2021-07-15 15:34:22'),(128,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','登录成功','2021-07-15 17:00:15'),(129,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','登录成功','2021-07-18 16:28:05'),(130,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','登录成功','2021-07-19 15:02:55'),(131,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','登录成功','2021-07-20 16:40:52'),(132,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','登录成功','2021-07-21 15:57:47'),(133,'admin','127.0.0.1','内网IP','Chrome 9','Mac OS X','0','登录成功','2021-07-22 15:55:44');
/*!40000 ALTER TABLE `sys_logininfor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `is_frame` int(1) DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `is_cache` int(1) DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2012 DEFAULT CHARSET=utf8 COMMENT='菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'系统管理',0,1,'system',NULL,1,0,'M','0','0','','system','admin','2021-07-12 18:37:11','',NULL,'系统管理目录'),(2,'系统监控',0,2,'monitor',NULL,1,0,'M','0','0','','monitor','admin','2021-07-12 18:37:11','',NULL,'系统监控目录'),(3,'系统工具',0,3,'tool',NULL,1,0,'M','0','0','','tool','admin','2021-07-12 18:37:11','',NULL,'系统工具目录'),(4,'若依官网',0,4,'http://ruoyi.vip',NULL,0,0,'M','0','0','','guide','admin','2021-07-12 18:37:11','',NULL,'若依官网地址'),(100,'用户管理',1,1,'user','system/user/index',1,0,'C','0','0','system:user:list','user','admin','2021-07-12 18:37:11','',NULL,'用户管理菜单'),(101,'角色管理',1,2,'role','system/role/index',1,0,'C','0','0','system:role:list','peoples','admin','2021-07-12 18:37:11','',NULL,'角色管理菜单'),(102,'菜单管理',1,3,'menu','system/menu/index',1,0,'C','0','0','system:menu:list','tree-table','admin','2021-07-12 18:37:11','',NULL,'菜单管理菜单'),(103,'部门管理',1,4,'dept','system/dept/index',1,0,'C','0','0','system:dept:list','tree','admin','2021-07-12 18:37:11','',NULL,'部门管理菜单'),(104,'岗位管理',1,5,'post','system/post/index',1,0,'C','0','0','system:post:list','post','admin','2021-07-12 18:37:11','',NULL,'岗位管理菜单'),(105,'字典管理',1,6,'dict','system/dict/index',1,0,'C','0','0','system:dict:list','dict','admin','2021-07-12 18:37:11','',NULL,'字典管理菜单'),(106,'参数设置',1,7,'config','system/config/index',1,0,'C','0','0','system:config:list','edit','admin','2021-07-12 18:37:11','',NULL,'参数设置菜单'),(107,'通知公告',1,8,'notice','system/notice/index',1,0,'C','0','0','system:notice:list','message','admin','2021-07-12 18:37:11','',NULL,'通知公告菜单'),(108,'日志管理',1,9,'log','',1,0,'M','0','0','','log','admin','2021-07-12 18:37:11','',NULL,'日志管理菜单'),(109,'在线用户',2,1,'online','monitor/online/index',1,0,'C','0','0','monitor:online:list','online','admin','2021-07-12 18:37:11','',NULL,'在线用户菜单'),(110,'定时任务',2,2,'job','monitor/job/index',1,0,'C','0','0','monitor:job:list','job','admin','2021-07-12 18:37:11','',NULL,'定时任务菜单'),(111,'数据监控',2,3,'druid','monitor/druid/index',1,0,'C','0','0','monitor:druid:list','druid','admin','2021-07-12 18:37:11','',NULL,'数据监控菜单'),(112,'服务监控',2,4,'server','monitor/server/index',1,0,'C','0','0','monitor:server:list','server','admin','2021-07-12 18:37:11','',NULL,'服务监控菜单'),(113,'缓存监控',2,5,'cache','monitor/cache/index',1,0,'C','0','0','monitor:cache:list','redis','admin','2021-07-12 18:37:11','',NULL,'缓存监控菜单'),(114,'表单构建',3,1,'build','tool/build/index',1,0,'C','0','0','tool:build:list','build','admin','2021-07-12 18:37:11','',NULL,'表单构建菜单'),(115,'代码生成',3,2,'gen','tool/gen/index',1,0,'C','0','0','tool:gen:list','code','admin','2021-07-12 18:37:11','',NULL,'代码生成菜单'),(116,'系统接口',3,3,'swagger','tool/swagger/index',1,0,'C','0','0','tool:swagger:list','swagger','admin','2021-07-12 18:37:11','',NULL,'系统接口菜单'),(500,'操作日志',108,1,'operlog','monitor/operlog/index',1,0,'C','0','0','monitor:operlog:list','form','admin','2021-07-12 18:37:11','',NULL,'操作日志菜单'),(501,'登录日志',108,2,'logininfor','monitor/logininfor/index',1,0,'C','0','0','monitor:logininfor:list','logininfor','admin','2021-07-12 18:37:11','',NULL,'登录日志菜单'),(1001,'用户查询',100,1,'','',1,0,'F','0','0','system:user:query','#','admin','2021-07-12 18:37:11','',NULL,''),(1002,'用户新增',100,2,'','',1,0,'F','0','0','system:user:add','#','admin','2021-07-12 18:37:11','',NULL,''),(1003,'用户修改',100,3,'','',1,0,'F','0','0','system:user:edit','#','admin','2021-07-12 18:37:11','',NULL,''),(1004,'用户删除',100,4,'','',1,0,'F','0','0','system:user:remove','#','admin','2021-07-12 18:37:11','',NULL,''),(1005,'用户导出',100,5,'','',1,0,'F','0','0','system:user:export','#','admin','2021-07-12 18:37:11','',NULL,''),(1006,'用户导入',100,6,'','',1,0,'F','0','0','system:user:import','#','admin','2021-07-12 18:37:11','',NULL,''),(1007,'重置密码',100,7,'','',1,0,'F','0','0','system:user:resetPwd','#','admin','2021-07-12 18:37:11','',NULL,''),(1008,'角色查询',101,1,'','',1,0,'F','0','0','system:role:query','#','admin','2021-07-12 18:37:11','',NULL,''),(1009,'角色新增',101,2,'','',1,0,'F','0','0','system:role:add','#','admin','2021-07-12 18:37:11','',NULL,''),(1010,'角色修改',101,3,'','',1,0,'F','0','0','system:role:edit','#','admin','2021-07-12 18:37:11','',NULL,''),(1011,'角色删除',101,4,'','',1,0,'F','0','0','system:role:remove','#','admin','2021-07-12 18:37:11','',NULL,''),(1012,'角色导出',101,5,'','',1,0,'F','0','0','system:role:export','#','admin','2021-07-12 18:37:11','',NULL,''),(1013,'菜单查询',102,1,'','',1,0,'F','0','0','system:menu:query','#','admin','2021-07-12 18:37:11','',NULL,''),(1014,'菜单新增',102,2,'','',1,0,'F','0','0','system:menu:add','#','admin','2021-07-12 18:37:11','',NULL,''),(1015,'菜单修改',102,3,'','',1,0,'F','0','0','system:menu:edit','#','admin','2021-07-12 18:37:11','',NULL,''),(1016,'菜单删除',102,4,'','',1,0,'F','0','0','system:menu:remove','#','admin','2021-07-12 18:37:11','',NULL,''),(1017,'部门查询',103,1,'','',1,0,'F','0','0','system:dept:query','#','admin','2021-07-12 18:37:11','',NULL,''),(1018,'部门新增',103,2,'','',1,0,'F','0','0','system:dept:add','#','admin','2021-07-12 18:37:11','',NULL,''),(1019,'部门修改',103,3,'','',1,0,'F','0','0','system:dept:edit','#','admin','2021-07-12 18:37:11','',NULL,''),(1020,'部门删除',103,4,'','',1,0,'F','0','0','system:dept:remove','#','admin','2021-07-12 18:37:11','',NULL,''),(1021,'岗位查询',104,1,'','',1,0,'F','0','0','system:post:query','#','admin','2021-07-12 18:37:11','',NULL,''),(1022,'岗位新增',104,2,'','',1,0,'F','0','0','system:post:add','#','admin','2021-07-12 18:37:11','',NULL,''),(1023,'岗位修改',104,3,'','',1,0,'F','0','0','system:post:edit','#','admin','2021-07-12 18:37:11','',NULL,''),(1024,'岗位删除',104,4,'','',1,0,'F','0','0','system:post:remove','#','admin','2021-07-12 18:37:11','',NULL,''),(1025,'岗位导出',104,5,'','',1,0,'F','0','0','system:post:export','#','admin','2021-07-12 18:37:11','',NULL,''),(1026,'字典查询',105,1,'#','',1,0,'F','0','0','system:dict:query','#','admin','2021-07-12 18:37:11','',NULL,''),(1027,'字典新增',105,2,'#','',1,0,'F','0','0','system:dict:add','#','admin','2021-07-12 18:37:11','',NULL,''),(1028,'字典修改',105,3,'#','',1,0,'F','0','0','system:dict:edit','#','admin','2021-07-12 18:37:11','',NULL,''),(1029,'字典删除',105,4,'#','',1,0,'F','0','0','system:dict:remove','#','admin','2021-07-12 18:37:11','',NULL,''),(1030,'字典导出',105,5,'#','',1,0,'F','0','0','system:dict:export','#','admin','2021-07-12 18:37:11','',NULL,''),(1031,'参数查询',106,1,'#','',1,0,'F','0','0','system:config:query','#','admin','2021-07-12 18:37:11','',NULL,''),(1032,'参数新增',106,2,'#','',1,0,'F','0','0','system:config:add','#','admin','2021-07-12 18:37:11','',NULL,''),(1033,'参数修改',106,3,'#','',1,0,'F','0','0','system:config:edit','#','admin','2021-07-12 18:37:11','',NULL,''),(1034,'参数删除',106,4,'#','',1,0,'F','0','0','system:config:remove','#','admin','2021-07-12 18:37:11','',NULL,''),(1035,'参数导出',106,5,'#','',1,0,'F','0','0','system:config:export','#','admin','2021-07-12 18:37:11','',NULL,''),(1036,'公告查询',107,1,'#','',1,0,'F','0','0','system:notice:query','#','admin','2021-07-12 18:37:11','',NULL,''),(1037,'公告新增',107,2,'#','',1,0,'F','0','0','system:notice:add','#','admin','2021-07-12 18:37:11','',NULL,''),(1038,'公告修改',107,3,'#','',1,0,'F','0','0','system:notice:edit','#','admin','2021-07-12 18:37:11','',NULL,''),(1039,'公告删除',107,4,'#','',1,0,'F','0','0','system:notice:remove','#','admin','2021-07-12 18:37:11','',NULL,''),(1040,'操作查询',500,1,'#','',1,0,'F','0','0','monitor:operlog:query','#','admin','2021-07-12 18:37:11','',NULL,''),(1041,'操作删除',500,2,'#','',1,0,'F','0','0','monitor:operlog:remove','#','admin','2021-07-12 18:37:11','',NULL,''),(1042,'日志导出',500,4,'#','',1,0,'F','0','0','monitor:operlog:export','#','admin','2021-07-12 18:37:11','',NULL,''),(1043,'登录查询',501,1,'#','',1,0,'F','0','0','monitor:logininfor:query','#','admin','2021-07-12 18:37:11','',NULL,''),(1044,'登录删除',501,2,'#','',1,0,'F','0','0','monitor:logininfor:remove','#','admin','2021-07-12 18:37:11','',NULL,''),(1045,'日志导出',501,3,'#','',1,0,'F','0','0','monitor:logininfor:export','#','admin','2021-07-12 18:37:11','',NULL,''),(1046,'在线查询',109,1,'#','',1,0,'F','0','0','monitor:online:query','#','admin','2021-07-12 18:37:11','',NULL,''),(1047,'批量强退',109,2,'#','',1,0,'F','0','0','monitor:online:batchLogout','#','admin','2021-07-12 18:37:11','',NULL,''),(1048,'单条强退',109,3,'#','',1,0,'F','0','0','monitor:online:forceLogout','#','admin','2021-07-12 18:37:11','',NULL,''),(1049,'任务查询',110,1,'#','',1,0,'F','0','0','monitor:job:query','#','admin','2021-07-12 18:37:11','',NULL,''),(1050,'任务新增',110,2,'#','',1,0,'F','0','0','monitor:job:add','#','admin','2021-07-12 18:37:11','',NULL,''),(1051,'任务修改',110,3,'#','',1,0,'F','0','0','monitor:job:edit','#','admin','2021-07-12 18:37:11','',NULL,''),(1052,'任务删除',110,4,'#','',1,0,'F','0','0','monitor:job:remove','#','admin','2021-07-12 18:37:11','',NULL,''),(1053,'状态修改',110,5,'#','',1,0,'F','0','0','monitor:job:changeStatus','#','admin','2021-07-12 18:37:11','',NULL,''),(1054,'任务导出',110,7,'#','',1,0,'F','0','0','monitor:job:export','#','admin','2021-07-12 18:37:11','',NULL,''),(1055,'生成查询',115,1,'#','',1,0,'F','0','0','tool:gen:query','#','admin','2021-07-12 18:37:11','',NULL,''),(1056,'生成修改',115,2,'#','',1,0,'F','0','0','tool:gen:edit','#','admin','2021-07-12 18:37:11','',NULL,''),(1057,'生成删除',115,3,'#','',1,0,'F','0','0','tool:gen:remove','#','admin','2021-07-12 18:37:11','',NULL,''),(1058,'导入代码',115,2,'#','',1,0,'F','0','0','tool:gen:import','#','admin','2021-07-12 18:37:11','',NULL,''),(1059,'预览代码',115,4,'#','',1,0,'F','0','0','tool:gen:preview','#','admin','2021-07-12 18:37:11','',NULL,''),(1060,'生成代码',115,5,'#','',1,0,'F','0','0','tool:gen:code','#','admin','2021-07-12 18:37:11','',NULL,''),(2006,'企业贷款信息',3,1,'loan','system/loan/index',1,0,'C','0','0','system:loan:list','#','admin','2021-07-13 11:29:52','',NULL,'企业贷款信息菜单'),(2007,'企业贷款信息查询',2006,1,'#','',1,0,'F','0','0','system:loan:query','#','admin','2021-07-13 11:29:52','',NULL,''),(2008,'企业贷款信息新增',2006,2,'#','',1,0,'F','0','0','system:loan:add','#','admin','2021-07-13 11:29:52','',NULL,''),(2009,'企业贷款信息修改',2006,3,'#','',1,0,'F','0','0','system:loan:edit','#','admin','2021-07-13 11:29:52','',NULL,''),(2010,'企业贷款信息删除',2006,4,'#','',1,0,'F','0','0','system:loan:remove','#','admin','2021-07-13 11:29:52','',NULL,''),(2011,'企业贷款信息导出',2006,5,'#','',1,0,'F','0','0','system:loan:export','#','admin','2021-07-13 11:29:52','',NULL,'');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_notice`
--

DROP TABLE IF EXISTS `sys_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_notice` (
  `notice_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) NOT NULL COMMENT '公告标题',
  `notice_type` char(1) NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob COMMENT '公告内容',
  `status` char(1) DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='通知公告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notice`
--

LOCK TABLES `sys_notice` WRITE;
/*!40000 ALTER TABLE `sys_notice` DISABLE KEYS */;
INSERT INTO `sys_notice` VALUES (1,'温馨提醒：2018-07-01 若依新版本发布啦','2',_binary '新版本内容','0','admin','2021-07-12 18:37:11','',NULL,'管理员'),(2,'维护通知：2018-07-01 若依系统凌晨维护','1',_binary '维护内容','0','admin','2021-07-12 18:37:11','',NULL,'管理员');
/*!40000 ALTER TABLE `sys_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_oper_log`
--

DROP TABLE IF EXISTS `sys_oper_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_oper_log` (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) DEFAULT '' COMMENT '模块标题',
  `business_type` int(2) DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) DEFAULT '' COMMENT '请求方式',
  `operator_type` int(1) DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) DEFAULT '' COMMENT '返回参数',
  `status` int(1) DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB AUTO_INCREMENT=184 DEFAULT CHARSET=utf8 COMMENT='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_oper_log`
--

LOCK TABLES `sys_oper_log` WRITE;
/*!40000 ALTER TABLE `sys_oper_log` DISABLE KEYS */;
INSERT INTO `sys_oper_log` VALUES (100,'代码生成',6,'com.ruoyi.generator.controller.GenController.importTableSave()','POST',1,'admin',NULL,'/tool/gen/importTable','127.0.0.1','内网IP','sys_dept','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-12 20:48:37'),(101,'代码生成',6,'com.ruoyi.generator.controller.GenController.importTableSave()','POST',1,'admin',NULL,'/tool/gen/importTable','127.0.0.1','内网IP','data_company_loan','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-13 08:48:49'),(102,'代码生成',8,'com.ruoyi.generator.controller.GenController.batchGenCode()','GET',1,'admin',NULL,'/tool/gen/batchGenCode','127.0.0.1','内网IP','{}','null',0,NULL,'2021-07-13 08:54:23'),(103,'企业贷款信息',1,'com.ruoyi.app.controller.DataCompanyLoanController.add()','POST',1,'admin',NULL,'/app/loan','127.0.0.1','内网IP','{\"companyName\":\"11\",\"params\":{},\"loanAmount\":1000,\"companyId\":1,\"createTime\":1626145367084,\"mztUserId\":\"1\",\"companyCreditCode\":\"111\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-13 11:02:47'),(104,'企业贷款信息',2,'com.ruoyi.app.controller.DataCompanyLoanController.edit()','PUT',1,'admin',NULL,'/app/loan','127.0.0.1','内网IP','{\"companyCity\":\"\",\"companyArea\":\"\",\"loanBand\":\"\",\"companyType\":\"\",\"contactName\":\"\",\"companyName\":\"11\",\"companyBusiness\":\"\",\"remark\":\"\",\"updateTime\":1626145381023,\"delFlag\":\"0\",\"params\":{},\"loanAmount\":1000.11,\"companyId\":1,\"createTime\":1626145367000,\"mztUserId\":\"1\",\"loanPurpose\":\"\",\"companyCreditCode\":\"111\",\"contactPhone\":\"\",\"companyProvince\":\"\",\"loanFirst\":\"0\",\"companyIndustry\":\"\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-13 11:03:01'),(105,'企业贷款信息',3,'com.ruoyi.app.controller.DataCompanyLoanController.remove()','DELETE',1,'admin',NULL,'/app/loan/1','127.0.0.1','内网IP','{companyIds=1}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-13 11:03:08'),(106,'代码生成',3,'com.ruoyi.generator.controller.GenController.remove()','DELETE',1,'admin',NULL,'/tool/gen/2','127.0.0.1','内网IP','{tableIds=2}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-13 11:22:17'),(107,'代码生成',6,'com.ruoyi.generator.controller.GenController.importTableSave()','POST',1,'admin',NULL,'/tool/gen/importTable','127.0.0.1','内网IP','data_company_loan','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-13 11:22:22'),(108,'代码生成',8,'com.ruoyi.generator.controller.GenController.batchGenCode()','GET',1,'admin',NULL,'/tool/gen/batchGenCode','127.0.0.1','内网IP','{}','null',0,NULL,'2021-07-13 11:23:06'),(109,'菜单管理',3,'com.ruoyi.web.controller.system.SysMenuController.remove()','DELETE',1,'admin',NULL,'/system/menu/2000','127.0.0.1','内网IP','{menuId=2000}','{\"msg\":\"存在子菜单,不允许删除\",\"code\":500}',0,NULL,'2021-07-13 11:28:23'),(110,'菜单管理',3,'com.ruoyi.web.controller.system.SysMenuController.remove()','DELETE',1,'admin',NULL,'/system/menu/2001','127.0.0.1','内网IP','{menuId=2001}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-13 11:28:36'),(111,'菜单管理',3,'com.ruoyi.web.controller.system.SysMenuController.remove()','DELETE',1,'admin',NULL,'/system/menu/2002','127.0.0.1','内网IP','{menuId=2002}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-13 11:28:39'),(112,'菜单管理',3,'com.ruoyi.web.controller.system.SysMenuController.remove()','DELETE',1,'admin',NULL,'/system/menu/2003','127.0.0.1','内网IP','{menuId=2003}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-13 11:28:40'),(113,'菜单管理',3,'com.ruoyi.web.controller.system.SysMenuController.remove()','DELETE',1,'admin',NULL,'/system/menu/2005','127.0.0.1','内网IP','{menuId=2005}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-13 11:28:45'),(114,'菜单管理',3,'com.ruoyi.web.controller.system.SysMenuController.remove()','DELETE',1,'admin',NULL,'/system/menu/2004','127.0.0.1','内网IP','{menuId=2004}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-13 11:28:47'),(115,'菜单管理',3,'com.ruoyi.web.controller.system.SysMenuController.remove()','DELETE',1,'admin',NULL,'/system/menu/2000','127.0.0.1','内网IP','{menuId=2000}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-13 11:28:50'),(116,'企业贷款信息',1,'com.ruoyi.web.controller.system.DataCompanyLoanController.add()','POST',1,'admin',NULL,'/system/loan','127.0.0.1','内网IP','{\"companyName\":\"11\",\"params\":{},\"loanAmount\":11,\"companyId\":2,\"createTime\":1626147215094,\"mztUserId\":\"11\",\"companyCreditCode\":\"22\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-13 11:33:35'),(117,'企业贷款信息',1,'com.ruoyi.web.controller.system.DataCompanyLoanController.add()','POST',1,'admin',NULL,'/system/loan','127.0.0.1','内网IP','{\"companyCity\":\"泉州\",\"companyArea\":\"丰泽\",\"loanBand\":\"意向银行\",\"contactName\":\"张展\",\"companyName\":\"企业名称\",\"companyBusiness\":\"主营业务\",\"remark\":\"备注\",\"params\":{},\"loanAmount\":1000,\"companyId\":3,\"createTime\":1626148627839,\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"companyCreditCode\":\"DASKFJAKSLFJ\",\"contactPhone\":\"18805955908\",\"companyProvince\":\"福建\",\"loanFirst\":\"0\",\"companyIndustry\":\"所在行业\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-13 11:57:07'),(118,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"companyCity\":\"泉州\",\"companyArea\":\"丰泽\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称\",\"companyBusiness\":\"主营业务\",\"remark\":\"备注\",\"params\":{},\"loanAmount\":1000,\"companyId\":4,\"createTime\":1626148705134,\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"companyCreditCode\":\"DASKFJAKSLFJ\",\"contactPhone\":\"18805955908\",\"companyProvince\":\"福建\",\"loanFirst\":\"0\",\"companyIndustry\":\"所在行业\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-13 11:58:25'),(119,'部门管理',1,'com.ruoyi.web.controller.system.SysDeptController.add()','POST',1,'admin',NULL,'/system/dept','127.0.0.1','内网IP','{\"deptName\":\"深圳总公司\",\"orderNum\":\"0\",\"params\":{},\"parentId\":100,\"children\":[],\"status\":\"0\"}','{\"msg\":\"新增部门\'深圳总公司\'失败，部门名称已存在\",\"code\":500}',0,NULL,'2021-07-13 16:08:48'),(120,'部门管理',1,'com.ruoyi.web.controller.system.SysDeptController.add()','POST',1,'admin',NULL,'/system/dept','127.0.0.1','内网IP','{\"deptName\":\"深圳总公司\",\"orderNum\":\"2\",\"params\":{},\"parentId\":100,\"children\":[],\"status\":\"0\"}','{\"msg\":\"新增部门\'深圳总公司\'失败，部门名称已存在\",\"code\":500}',0,NULL,'2021-07-13 16:09:13'),(121,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"companyCity\":\"泉州\",\"companyArea\":\"丰泽\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称\",\"companyBusiness\":\"主营业务\",\"remark\":\"备注\",\"params\":{},\"loanAmount\":1000,\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"companyCreditCode\":\"DASKFJAKSLFJ\",\"contactPhone\":\"18805955908\",\"companyProvince\":\"福建\",\"loanFirst\":\"0\",\"companyIndustry\":\"所在行业\"}','null',1,'nested exception is org.apache.ibatis.exceptions.TooManyResultsException: Expected one result (or null) to be returned by selectOne(), but found: 2','2021-07-13 16:25:30'),(122,'企业贷款信息',3,'com.ruoyi.web.controller.system.DataCompanyLoanController.remove()','DELETE',1,'admin',NULL,'/system/loan/3','127.0.0.1','内网IP','{companyIds=3}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-13 16:26:12'),(123,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"companyCity\":\"泉州\",\"companyArea\":\"丰泽\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称\",\"companyBusiness\":\"主营业务\",\"remark\":\"备注\",\"params\":{},\"loanAmount\":1000,\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"companyCreditCode\":\"DASKFJAKSLFJ\",\"contactPhone\":\"18805955908\",\"companyProvince\":\"福建\",\"loanFirst\":\"0\",\"companyIndustry\":\"所在行业\"}','{\"msg\":\"新增\'企业名称\'失败，名称已存在\",\"code\":500}',0,NULL,'2021-07-13 16:26:15'),(124,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"companyCity\":\"泉州\",\"companyArea\":\"丰泽\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称\",\"companyBusiness\":\"主营业务\",\"remark\":\"备注\",\"params\":{},\"loanAmount\":1000,\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"companyCreditCode\":\"DASKFJAKSLFJ\",\"contactPhone\":\"18805955908\",\"companyProvince\":\"福建\",\"loanFirst\":\"0\",\"companyIndustry\":\"所在行业\"}','{\"msg\":\"新增\'企业名称\'失败，名称已存在\",\"code\":500}',0,NULL,'2021-07-13 16:26:18'),(125,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"companyCity\":\"泉州\",\"companyArea\":\"丰泽\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称1\",\"companyBusiness\":\"主营业务\",\"remark\":\"备注\",\"params\":{},\"loanAmount\":1000,\"companyId\":5,\"createTime\":1626164797017,\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"companyCreditCode\":\"DASKFJAKSLFJ\",\"contactPhone\":\"18805955908\",\"companyProvince\":\"福建\",\"loanFirst\":\"0\",\"companyIndustry\":\"所在行业\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-13 16:26:37'),(126,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"companyCity\":\"泉州\",\"companyArea\":\"丰泽\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称1\",\"companyBusiness\":\"主营业务\",\"remark\":\"备注\",\"params\":{},\"loanAmount\":1000,\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"companyCreditCode\":\"DASKFJAKSLFJ\",\"contactPhone\":\"18805955908\",\"companyProvince\":\"福建\",\"loanFirst\":\"0\",\"companyIndustry\":\"所在行业\"}','{\"msg\":\"新增\'企业名称1\'失败，名称已存在\",\"code\":500}',0,NULL,'2021-07-13 16:52:01'),(127,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"companyName\":\"啊哈哈\",\"params\":{},\"createTime\":1626166742798}','null',1,'\n### Error updating database.  Cause: java.sql.SQLException: Field \'mzt_user_id\' doesn\'t have a default value\n### The error may exist in file [/Users/geniusmart/RuoYi-Vue/ruoyi-system/target/classes/mapper/system/DataCompanyLoanMapper.xml]\n### The error may involve com.ruoyi.system.mapper.DataCompanyLoanMapper.insertDataCompanyLoan-Inline\n### The error occurred while setting parameters\n### SQL: insert into data_company_loan          ( company_name,                                                                                                                                                                                                                create_time )           values ( ?,                                                                                                                                                                                                                ? )\n### Cause: java.sql.SQLException: Field \'mzt_user_id\' doesn\'t have a default value\n; Field \'mzt_user_id\' doesn\'t have a default value; nested exception is java.sql.SQLException: Field \'mzt_user_id\' doesn\'t have a default value','2021-07-13 16:59:02'),(128,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"companyName\":\"啊哈哈\",\"params\":{},\"loanAmount\":1000,\"companyId\":6,\"createTime\":1626166821141,\"mztUserId\":\"11111\",\"companyCreditCode\":\"2222\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-13 17:00:21'),(129,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"params\":{},\"createTime\":1626167063751}','null',1,'\n### Error updating database.  Cause: java.sql.SQLException: Field \'mzt_user_id\' doesn\'t have a default value\n### The error may exist in file [/Users/geniusmart/RuoYi-Vue/ruoyi-system/target/classes/mapper/system/DataCompanyLoanMapper.xml]\n### The error may involve com.ruoyi.system.mapper.DataCompanyLoanMapper.insertDataCompanyLoan-Inline\n### The error occurred while setting parameters\n### SQL: insert into data_company_loan          ( create_time )           values ( ? )\n### Cause: java.sql.SQLException: Field \'mzt_user_id\' doesn\'t have a default value\n; Field \'mzt_user_id\' doesn\'t have a default value; nested exception is java.sql.SQLException: Field \'mzt_user_id\' doesn\'t have a default value','2021-07-13 17:04:23'),(130,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"companyCity\":\"泉州\",\"companyArea\":\"丰泽\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称1\",\"companyBusiness\":\"主营业务\",\"remark\":\"备注\",\"params\":{},\"loanAmount\":1000,\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"companyCreditCode\":\"DASKFJAKSLFJ\",\"contactPhone\":\"18805955908\",\"companyProvince\":\"福建\",\"loanFirst\":\"0\",\"companyIndustry\":\"所在行业\"}','{\"msg\":\"新增\'企业名称1\'失败，该企业名称已存在\",\"code\":500}',0,NULL,'2021-07-13 18:01:41'),(131,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"companyCity\":\"泉州\",\"companyArea\":\"丰泽\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称1\",\"companyBusiness\":\"主营业务\",\"remark\":\"备注\",\"params\":{},\"loanAmount\":1000,\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"companyCreditCode\":\"DASKFJAKSLFJ\",\"contactPhone\":\"18805955908\",\"companyProvince\":\"福建\",\"loanFirst\":\"0\",\"companyIndustry\":\"所在行业\"}','{\"msg\":\"新增\'企业名称1\'失败，该企业名称已存在\",\"code\":500}',0,NULL,'2021-07-15 15:30:10'),(132,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"companyCity\":\"泉州\",\"companyArea\":\"丰泽\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称1\",\"companyBusiness\":\"主营业务\",\"remark\":\"备注\",\"params\":{},\"loanAmount\":1000,\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"companyCreditCode\":\"DASKFJAKSLFJ\",\"contactPhone\":\"18805955908\",\"companyProvince\":\"福建\",\"loanFirst\":\"0\",\"companyIndustry\":\"所在行业\"}','{\"msg\":\"新增\'企业名称1\'失败，该企业名称已存在\",\"code\":500}',0,NULL,'2021-07-15 15:36:11'),(133,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"companyCity\":\"泉州\",\"companyArea\":\"丰泽\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称1\",\"companyBusiness\":\"主营业务\",\"remark\":\"备注\",\"params\":{},\"loanAmount\":1000,\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"companyCreditCode\":\"DASKFJAKSLFJ\",\"contactPhone\":\"18805955908\",\"companyProvince\":\"福建\",\"loanFirst\":\"0\",\"companyIndustry\":\"所在行业\"}','{\"msg\":\"新增\'企业名称1\'失败，该企业名称已存在\",\"code\":500}',0,NULL,'2021-07-15 15:37:28'),(134,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"companyCity\":\"泉州\",\"companyArea\":\"丰泽\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称1\",\"companyBusiness\":\"主营业务\",\"remark\":\"备注\",\"params\":{},\"loanAmount\":1000,\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"companyCreditCode\":\"DASKFJAKSLFJ\",\"contactPhone\":\"18805955908\",\"companyProvince\":\"福建\",\"loanFirst\":\"0\",\"companyIndustry\":\"所在行业\"}','{\"msg\":\"新增\'企业名称1\'失败，该企业名称已存在\",\"code\":500}',0,NULL,'2021-07-15 15:38:09'),(135,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"code\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称1\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','{\"msg\":\"新增\'企业名称1\'失败，该企业名称已存在\",\"code\":500}',0,NULL,'2021-07-15 16:57:17'),(136,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"code\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称2\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','null',1,'\n### Error updating database.  Cause: java.sql.SQLException: Field \'company_credit_code\' doesn\'t have a default value\n### The error may exist in file [/Users/geniusmart/RuoYi-Vue/ruoyi-system/target/classes/mapper/system/DataCompanyLoanMapper.xml]\n### The error may involve com.ruoyi.system.mapper.DataCompanyLoanMapper.insertDataCompanyLoan-Inline\n### The error occurred while setting parameters\n### SQL: insert into data_company_loan          ( mzt_user_id,             company_name,                                                                                                        loan_band,             loan_amount,             loan_first,             loan_purpose,                          contact_name,             contact_phone,                          create_time )           values ( ?,             ?,                                                                                                        ?,             ?,             ?,             ?,                          ?,             ?,                          ? )\n### Cause: java.sql.SQLException: Field \'company_credit_code\' doesn\'t have a default value\n; Field \'company_credit_code\' doesn\'t have a default value; nested exception is java.sql.SQLException: Field \'company_credit_code\' doesn\'t have a default value','2021-07-15 16:57:28'),(137,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"code\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称2\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','null',1,'\n### Error updating database.  Cause: java.sql.SQLException: Field \'company_credit_code\' doesn\'t have a default value\n### The error may exist in file [/Users/geniusmart/RuoYi-Vue/ruoyi-system/target/classes/mapper/system/DataCompanyLoanMapper.xml]\n### The error may involve com.ruoyi.system.mapper.DataCompanyLoanMapper.insertDataCompanyLoan-Inline\n### The error occurred while setting parameters\n### SQL: insert into data_company_loan          ( mzt_user_id,             company_name,                                                                                                        loan_band,             loan_amount,             loan_first,             loan_purpose,                          contact_name,             contact_phone,                          create_time )           values ( ?,             ?,                                                                                                        ?,             ?,             ?,             ?,                          ?,             ?,                          ? )\n### Cause: java.sql.SQLException: Field \'company_credit_code\' doesn\'t have a default value\n; Field \'company_credit_code\' doesn\'t have a default value; nested exception is java.sql.SQLException: Field \'company_credit_code\' doesn\'t have a default value','2021-07-15 16:59:34'),(138,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"code\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称2\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','null',1,'\n### Error updating database.  Cause: java.sql.SQLException: Field \'company_credit_code\' doesn\'t have a default value\n### The error may exist in file [/Users/geniusmart/RuoYi-Vue/ruoyi-system/target/classes/mapper/system/DataCompanyLoanMapper.xml]\n### The error may involve com.ruoyi.system.mapper.DataCompanyLoanMapper.insertDataCompanyLoan-Inline\n### The error occurred while setting parameters\n### SQL: insert into data_company_loan          ( mzt_user_id,             company_name,                                                                                                        loan_band,             loan_amount,             loan_first,             loan_purpose,                          contact_name,             contact_phone,                          create_time )           values ( ?,             ?,                                                                                                        ?,             ?,             ?,             ?,                          ?,             ?,                          ? )\n### Cause: java.sql.SQLException: Field \'company_credit_code\' doesn\'t have a default value\n; Field \'company_credit_code\' doesn\'t have a default value; nested exception is java.sql.SQLException: Field \'company_credit_code\' doesn\'t have a default value','2021-07-15 16:59:39'),(139,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"code\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称2\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-15 16:59:57'),(140,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"短信验证码\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称2\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','{\"msg\":\"新增\'企业名称2\'失败，该企业名称已存在\",\"code\":500}',0,NULL,'2021-07-15 18:13:09'),(141,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"短信验证码\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称2\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','{\"msg\":\"新增\'企业名称2\'失败，该企业名称已存在\",\"code\":500}',0,NULL,'2021-07-15 18:24:18'),(142,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"短信验证码\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称2\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','{\"msg\":\"新增\'企业名称2\'失败，该企业名称已存在\",\"code\":500}',0,NULL,'2021-07-15 18:24:42'),(143,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"短信验证码\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称2\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','{\"msg\":\"新增\'企业名称2\'失败，该企业名称已存在\",\"code\":500}',0,NULL,'2021-07-15 18:25:03'),(144,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"短信验证码\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称2\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','{\"msg\":\"新增\'企业名称2\'失败，该企业名称已存在\",\"code\":500}',0,NULL,'2021-07-15 18:25:16'),(145,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"短信验证码\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称2\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','{\"msg\":\"新增\'企业名称2\'失败，该企业名称已存在\",\"code\":500}',0,NULL,'2021-07-15 18:25:52'),(146,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"短信验证码\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称3\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','null',1,'验证码错误','2021-07-15 18:26:25'),(147,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"短信验证码\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称3\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','null',1,'验证码错误','2021-07-15 18:26:55'),(148,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"888888\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称3\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','null',1,'验证码错误','2021-07-15 18:28:09'),(149,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"888888\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称3\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','null',1,'验证码错误','2021-07-15 18:29:48'),(150,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"888888\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称3\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-15 18:30:09'),(151,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"888888\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称3\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','{\"msg\":\"新增\'企业名称3\'失败，该企业名称已存在\",\"code\":500}',0,NULL,'2021-07-15 18:30:12'),(152,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"888888\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称4\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','null',1,'验证码错误','2021-07-15 18:30:18'),(153,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"888888\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称4\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','null',1,'验证码错误','2021-07-15 18:38:36'),(154,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"888888\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称4\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-15 18:39:08'),(155,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"888888\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称4\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','{\"msg\":\"新增\'企业名称4\'失败，该企业名称已存在\",\"code\":500}',0,NULL,'2021-07-15 18:39:11'),(156,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"888888\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称4\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','{\"msg\":\"新增\'企业名称4\'失败，该企业名称已存在\",\"code\":500}',0,NULL,'2021-07-15 18:39:12'),(157,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"888881\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称5\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','null',1,'验证码错误','2021-07-15 18:39:21'),(158,'字典类型',1,'com.ruoyi.web.controller.system.SysDictTypeController.add()','POST',1,'admin',NULL,'/system/dict/type','127.0.0.1','内网IP','{\"dictName\":\"贷款银行\",\"params\":{},\"dictType\":\"sys_user_sex\",\"status\":\"0\"}','{\"msg\":\"新增字典\'贷款银行\'失败，字典类型已存在\",\"code\":500}',0,NULL,'2021-07-18 16:53:14'),(159,'字典类型',1,'com.ruoyi.web.controller.system.SysDictTypeController.add()','POST',1,'admin',NULL,'/system/dict/type','127.0.0.1','内网IP','{\"createBy\":\"admin\",\"dictName\":\"贷款银行\",\"params\":{},\"dictType\":\"sys_user_band\",\"status\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-18 16:53:24'),(160,'字典数据',1,'com.ruoyi.web.controller.system.SysDictDataController.add()','POST',1,'admin',NULL,'/system/dict/data','127.0.0.1','内网IP','{\"dictValue\":\"1\",\"listClass\":\"default\",\"dictSort\":1,\"params\":{},\"dictType\":\"sys_user_band\",\"dictLabel\":\"农业发展银行泉州分行\",\"createBy\":\"admin\",\"default\":false,\"status\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-18 16:56:47'),(161,'字典数据',1,'com.ruoyi.web.controller.system.SysDictDataController.add()','POST',1,'admin',NULL,'/system/dict/data','127.0.0.1','内网IP','{\"dictValue\":\"2\",\"listClass\":\"default\",\"dictSort\":2,\"params\":{},\"dictType\":\"sys_user_band\",\"dictLabel\":\"工商银行泉州分行\",\"createBy\":\"admin\",\"default\":false,\"status\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-18 16:57:00'),(162,'字典数据',1,'com.ruoyi.web.controller.system.SysDictDataController.add()','POST',1,'admin',NULL,'/system/dict/data','127.0.0.1','内网IP','{\"dictValue\":\"3\",\"listClass\":\"default\",\"dictSort\":3,\"params\":{},\"dictType\":\"sys_user_band\",\"dictLabel\":\"农业银行泉州分行\",\"createBy\":\"admin\",\"default\":false,\"status\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-18 16:57:15'),(163,'字典数据',1,'com.ruoyi.web.controller.system.SysDictDataController.add()','POST',1,'admin',NULL,'/system/dict/data','127.0.0.1','内网IP','{\"dictValue\":\"4\",\"listClass\":\"default\",\"dictSort\":4,\"params\":{},\"dictType\":\"sys_user_band\",\"dictLabel\":\"中国银行泉州分行\",\"createBy\":\"admin\",\"default\":false,\"status\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-18 16:57:24'),(164,'字典数据',1,'com.ruoyi.web.controller.system.SysDictDataController.add()','POST',1,'admin',NULL,'/system/dict/data','127.0.0.1','内网IP','{\"dictValue\":\"5\",\"listClass\":\"default\",\"dictSort\":5,\"params\":{},\"dictType\":\"sys_user_band\",\"dictLabel\":\"建设银行泉州分行\",\"createBy\":\"admin\",\"default\":false,\"status\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-18 16:57:37'),(165,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"888888\",\"loanBand\":\"意向银行\",\"contactName\":\"张三\",\"companyName\":\"企业名称5\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','null',1,'验证码错误','2021-07-19 16:55:29'),(166,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"888888\",\"loanBand\":\"多个银行id用逗号隔开\",\"contactName\":\"张三\",\"companyName\":\"企业名称5\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','null',1,'验证码错误','2021-07-19 16:56:01'),(167,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"888888\",\"loanBand\":\"多个银行id用逗号隔开\",\"contactName\":\"张三\",\"companyName\":\"企业名称5\",\"mztUserId\":\"SDKLJFLA\",\"loanPurpose\":\"贷款用途\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','null',1,'验证码错误','2021-07-20 16:15:16'),(168,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"6666\",\"loanBand\":\"2,4\",\"contactName\":\"11\",\"companyName\":\"111\",\"loanPurpose\":\"111\",\"contactPhone\":\"13655988863\",\"loanAmount\":111,\"loanFirst\":\"0\"}','null',1,'验证码错误','2021-07-20 16:21:45'),(169,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"111\",\"loanBand\":\"1,2\",\"contactName\":\"11\",\"companyName\":\"111\",\"loanPurpose\":\"11\",\"contactPhone\":\"13655988863\",\"loanAmount\":111,\"loanFirst\":\"0\"}','null',1,'验证码错误','2021-07-20 16:24:25'),(170,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"7777\",\"loanBand\":\"1,3\",\"contactName\":\"100\",\"companyName\":\"树蛙大数据有限公司\",\"mztUserId\":\"123\",\"loanPurpose\":\"100\",\"contactPhone\":\"13655988863\",\"loanAmount\":111,\"loanFirst\":\"0\"}','null',1,'验证码错误','2021-07-22 10:32:44'),(171,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"167103\",\"loanBand\":\"1,2\",\"contactName\":\"张三\",\"companyName\":\"树蛙大数据有限公司\",\"mztUserId\":\"123\",\"loanPurpose\":\"测试\",\"contactPhone\":\"13655988863\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','null',1,'\n### Error updating database.  Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Data too long for column \'company_business\' at row 1\n### The error may exist in file [/Users/geniusmart/RuoYi-Vue/ruoyi-system/target/classes/mapper/system/DataCompanyLoanMapper.xml]\n### The error may involve com.ruoyi.system.mapper.DataCompanyLoanMapper.insertDataCompanyLoan-Inline\n### The error occurred while setting parameters\n### SQL: insert into data_company_loan          ( mzt_user_id,             company_name,             company_credit_code,                                                    company_type,             company_industry,             company_business,             loan_band,             loan_amount,             loan_first,             loan_purpose,                          contact_name,             contact_phone,                          create_time )           values ( ?,             ?,             ?,                                                    ?,             ?,             ?,             ?,             ?,             ?,             ?,                          ?,             ?,                          ? )\n### Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Data too long for column \'company_business\' at row 1\n; Data truncation: Data too long for column \'company_business\' at row 1; nested exception is com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Data too long for column \'company_business\' at row 1','2021-07-22 11:01:51'),(172,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"167103\",\"loanBand\":\"1,2\",\"contactName\":\"张三\",\"companyName\":\"树蛙大数据有限公司\",\"mztUserId\":\"123\",\"loanPurpose\":\"测试\",\"contactPhone\":\"13655988863\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','null',1,'验证码错误','2021-07-22 11:06:05'),(173,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"700598\",\"loanBand\":\"1,2\",\"contactName\":\"张三\",\"companyName\":\"树蛙大数据有限公司\",\"mztUserId\":\"123\",\"loanPurpose\":\"测试\",\"contactPhone\":\"13655988863\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-22 11:06:15'),(174,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"717484\",\"loanBand\":\"1,2\",\"contactName\":\"张三\",\"companyName\":\"晋江交发科技有限公司\",\"mztUserId\":\"123\",\"loanPurpose\":\"100\",\"contactPhone\":\"13655988863\",\"loanAmount\":100,\"loanFirst\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-22 11:12:01'),(175,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','192.168.1.94','内网IP','{\"code\":\"613590\",\"loanBand\":\"1,3\",\"contactName\":\"张三\",\"companyName\":\"树蛙大数据有限公司\",\"mztUserId\":\"123\",\"loanPurpose\":\"1000\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','{\"msg\":\"新增\'树蛙大数据有限公司\'失败，该企业名称已存在\",\"code\":500}',0,NULL,'2021-07-22 11:27:27'),(176,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','192.168.1.94','内网IP','{\"code\":\"613590\",\"loanBand\":\"1,3\",\"contactName\":\"张三\",\"companyName\":\"厦门恒言楠贸易有限公司\",\"mztUserId\":\"123\",\"loanPurpose\":\"1000\",\"contactPhone\":\"18805955908\",\"loanAmount\":1000,\"loanFirst\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-22 11:28:15'),(177,'企业贷款信息',1,'com.ruoyi.app.controller.AppCompanyLoanController.add()','POST',1,NULL,NULL,'/app/loan/add','127.0.0.1','内网IP','{\"code\":\"830224\",\"loanBand\":\"1,2\",\"contactName\":\"111\",\"companyName\":\"大数据\",\"mztUserId\":\"123\",\"loanPurpose\":\"111\",\"contactPhone\":\"13655988863\",\"loanAmount\":111,\"loanFirst\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-22 15:21:21'),(178,'字典数据',1,'com.ruoyi.web.controller.system.SysDictDataController.add()','POST',1,'admin',NULL,'/system/dict/data','127.0.0.1','内网IP','{\"dictValue\":\"测试\",\"listClass\":\"default\",\"dictSort\":5,\"params\":{},\"dictType\":\"sys_user_band\",\"dictLabel\":\"测试\",\"createBy\":\"admin\",\"default\":false,\"status\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-22 16:03:23'),(179,'字典类型',3,'com.ruoyi.web.controller.system.SysDictDataController.remove()','DELETE',1,'admin',NULL,'/system/dict/data/105','127.0.0.1','内网IP','{dictCodes=105}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-22 16:09:54'),(180,'字典类型',3,'com.ruoyi.web.controller.system.SysDictDataController.remove()','DELETE',1,'admin',NULL,'/system/dict/data/131,132,133,134','127.0.0.1','内网IP','{dictCodes=131,132,133,134}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-22 16:13:19'),(181,'字典类型',3,'com.ruoyi.web.controller.system.SysDictDataController.remove()','DELETE',1,'admin',NULL,'/system/dict/data/121,122,123,124,125,126,127,128,129,130','127.0.0.1','内网IP','{dictCodes=121,122,123,124,125,126,127,128,129,130}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-22 16:13:23'),(182,'字典类型',3,'com.ruoyi.web.controller.system.SysDictDataController.remove()','DELETE',1,'admin',NULL,'/system/dict/data/111,112,113,114,115,116,117,118,119,120','127.0.0.1','内网IP','{dictCodes=111,112,113,114,115,116,117,118,119,120}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-22 16:13:26'),(183,'字典类型',3,'com.ruoyi.web.controller.system.SysDictDataController.remove()','DELETE',1,'admin',NULL,'/system/dict/data/100,101,102,103,104,106,107,108,109,110','127.0.0.1','内网IP','{dictCodes=100,101,102,103,104,106,107,108,109,110}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-07-22 16:13:30');
/*!40000 ALTER TABLE `sys_oper_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_post`
--

DROP TABLE IF EXISTS `sys_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_post` (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) NOT NULL COMMENT '岗位名称',
  `post_sort` int(4) NOT NULL COMMENT '显示顺序',
  `status` char(1) NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='岗位信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_post`
--

LOCK TABLES `sys_post` WRITE;
/*!40000 ALTER TABLE `sys_post` DISABLE KEYS */;
INSERT INTO `sys_post` VALUES (1,'ceo','董事长',1,'0','admin','2021-07-12 18:37:11','',NULL,''),(2,'se','项目经理',2,'0','admin','2021-07-12 18:37:11','',NULL,''),(3,'hr','人力资源',3,'0','admin','2021-07-12 18:37:11','',NULL,''),(4,'user','普通员工',4,'0','admin','2021-07-12 18:37:11','',NULL,'');
/*!40000 ALTER TABLE `sys_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) DEFAULT '1' COMMENT '部门树选择项是否关联显示',
  `status` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'超级管理员','admin',1,'1',1,1,'0','0','admin','2021-07-12 18:37:11','',NULL,'超级管理员'),(2,'普通角色','common',2,'2',1,1,'0','0','admin','2021-07-12 18:37:11','',NULL,'普通角色');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_dept`
--

DROP TABLE IF EXISTS `sys_role_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_role_dept` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和部门关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_dept`
--

LOCK TABLES `sys_role_dept` WRITE;
/*!40000 ALTER TABLE `sys_role_dept` DISABLE KEYS */;
INSERT INTO `sys_role_dept` VALUES (2,100),(2,101),(2,105);
/*!40000 ALTER TABLE `sys_role_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (2,1),(2,2),(2,3),(2,4),(2,100),(2,101),(2,102),(2,103),(2,104),(2,105),(2,106),(2,107),(2,108),(2,109),(2,110),(2,111),(2,112),(2,113),(2,114),(2,115),(2,116),(2,500),(2,501),(2,1000),(2,1001),(2,1002),(2,1003),(2,1004),(2,1005),(2,1006),(2,1007),(2,1008),(2,1009),(2,1010),(2,1011),(2,1012),(2,1013),(2,1014),(2,1015),(2,1016),(2,1017),(2,1018),(2,1019),(2,1020),(2,1021),(2,1022),(2,1023),(2,1024),(2,1025),(2,1026),(2,1027),(2,1028),(2,1029),(2,1030),(2,1031),(2,1032),(2,1033),(2,1034),(2,1035),(2,1036),(2,1037),(2,1038),(2,1039),(2,1040),(2,1041),(2,1042),(2,1043),(2,1044),(2,1045),(2,1046),(2,1047),(2,1048),(2,1049),(2,1050),(2,1051),(2,1052),(2,1053),(2,1054),(2,1055),(2,1056),(2,1057),(2,1058),(2,1059),(2,1060);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) DEFAULT '' COMMENT '密码',
  `status` char(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,103,'admin','若依','00','ry@163.com','15888888888','1','','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2','0','0','127.0.0.1','2021-07-22 15:55:44','admin','2021-07-12 18:37:11','','2021-07-22 15:55:44','管理员'),(2,105,'ry','若依','00','ry@qq.com','15666666666','1','','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2','0','0','127.0.0.1','2021-07-12 18:37:11','admin','2021-07-12 18:37:11','',NULL,'测试员');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_post`
--

DROP TABLE IF EXISTS `sys_user_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_user_post` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与岗位关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_post`
--

LOCK TABLES `sys_user_post` WRITE;
/*!40000 ALTER TABLE `sys_user_post` DISABLE KEYS */;
INSERT INTO `sys_user_post` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `sys_user_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户和角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-22 18:51:21
