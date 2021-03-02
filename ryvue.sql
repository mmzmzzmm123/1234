-- MySQL dump 10.13  Distrib 8.0.17, for osx10.14 (x86_64)
--
-- Host: localhost    Database: ryvue
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `gen_table`
--

DROP TABLE IF EXISTS `gen_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gen_table` (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) COLLATE utf8mb4_general_ci DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) COLLATE utf8mb4_general_ci DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='代码生成业务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_table`
--

LOCK TABLES `gen_table` WRITE;
/*!40000 ALTER TABLE `gen_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `gen_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gen_table_column`
--

DROP TABLE IF EXISTS `gen_table_column`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gen_table_column` (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) COLLATE utf8mb4_general_ci DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典类型',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='代码生成业务表字段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_table_column`
--

LOCK TABLES `gen_table_column` WRITE;
/*!40000 ALTER TABLE `gen_table_column` DISABLE KEYS */;
/*!40000 ALTER TABLE `gen_table_column` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_BLOB_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `sched_name` varchar(120) COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_group` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `blob_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_CALENDARS` (
  `sched_name` varchar(120) COLLATE utf8mb4_general_ci NOT NULL,
  `calendar_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `calendar` blob NOT NULL,
  PRIMARY KEY (`sched_name`,`calendar_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `sched_name` varchar(120) COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_group` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `cron_expression` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `time_zone_id` varchar(80) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `sched_name` varchar(120) COLLATE utf8mb4_general_ci NOT NULL,
  `entry_id` varchar(95) COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_group` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `instance_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `fired_time` bigint(13) NOT NULL,
  `sched_time` bigint(13) NOT NULL,
  `priority` int(11) NOT NULL,
  `state` varchar(16) COLLATE utf8mb4_general_ci NOT NULL,
  `job_name` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `job_group` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `is_nonconcurrent` varchar(1) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `requests_recovery` varchar(1) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `sched_name` varchar(120) COLLATE utf8mb4_general_ci NOT NULL,
  `job_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `job_group` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(250) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `job_class_name` varchar(250) COLLATE utf8mb4_general_ci NOT NULL,
  `is_durable` varchar(1) COLLATE utf8mb4_general_ci NOT NULL,
  `is_nonconcurrent` varchar(1) COLLATE utf8mb4_general_ci NOT NULL,
  `is_update_data` varchar(1) COLLATE utf8mb4_general_ci NOT NULL,
  `requests_recovery` varchar(1) COLLATE utf8mb4_general_ci NOT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_JOB_DETAILS`
--

LOCK TABLES `QRTZ_JOB_DETAILS` WRITE;
/*!40000 ALTER TABLE `QRTZ_JOB_DETAILS` DISABLE KEYS */;
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('RuoyiScheduler','TASK_CLASS_NAME1','DEFAULT',NULL,'com.ruoyi.quartz.util.QuartzDisallowConcurrentExecution','0','1','0','0',_binary '�\�\0sr\0org.quartz.JobDataMap���迩�\�\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�\�\��\�](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\�.�(v\n\�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\��\�`\�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0TASK_PROPERTIESsr\0com.ruoyi.quartz.domain.SysJob\0\0\0\0\0\0\0\0L\0\nconcurrentt\0Ljava/lang/String;L\0cronExpressionq\0~\0	L\0invokeTargetq\0~\0	L\0jobGroupq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0jobNameq\0~\0	L\0\rmisfirePolicyq\0~\0	L\0statusq\0~\0	xr\0\'com.ruoyi.common.core.domain.BaseEntity\0\0\0\0\0\0\0\0L\0createByq\0~\0	L\0\ncreateTimet\0Ljava/util/Date;L\0paramsq\0~\0L\0remarkq\0~\0	L\0searchValueq\0~\0	L\0updateByq\0~\0	L\0\nupdateTimeq\0~\0xpt\0adminsr\0java.util.Datehj�KYt\0\0xpw\0\0w\�t�xpt\0\0pppt\01t\00/10 * * * * ?t\0ryTask.ryNoParamst\0DEFAULTsr\0java.lang.Long;�\�̏#\�\0J\0valuexr\0java.lang.Number����\��\0\0xp\0\0\0\0\0\0\0t\0系统默认（无参）t\03t\01x\0'),('RuoyiScheduler','TASK_CLASS_NAME2','DEFAULT',NULL,'com.ruoyi.quartz.util.QuartzDisallowConcurrentExecution','0','1','0','0',_binary '�\�\0sr\0org.quartz.JobDataMap���迩�\�\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�\�\��\�](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\�.�(v\n\�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\��\�`\�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0TASK_PROPERTIESsr\0com.ruoyi.quartz.domain.SysJob\0\0\0\0\0\0\0\0L\0\nconcurrentt\0Ljava/lang/String;L\0cronExpressionq\0~\0	L\0invokeTargetq\0~\0	L\0jobGroupq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0jobNameq\0~\0	L\0\rmisfirePolicyq\0~\0	L\0statusq\0~\0	xr\0\'com.ruoyi.common.core.domain.BaseEntity\0\0\0\0\0\0\0\0L\0createByq\0~\0	L\0\ncreateTimet\0Ljava/util/Date;L\0paramsq\0~\0L\0remarkq\0~\0	L\0searchValueq\0~\0	L\0updateByq\0~\0	L\0\nupdateTimeq\0~\0xpt\0adminsr\0java.util.Datehj�KYt\0\0xpw\0\0w\�t�xpt\0\0pppt\01t\00/15 * * * * ?t\0ryTask.ryParams(\'ry\')t\0DEFAULTsr\0java.lang.Long;�\�̏#\�\0J\0valuexr\0java.lang.Number����\��\0\0xp\0\0\0\0\0\0\0t\0系统默认（有参）t\03t\01x\0'),('RuoyiScheduler','TASK_CLASS_NAME3','DEFAULT',NULL,'com.ruoyi.quartz.util.QuartzDisallowConcurrentExecution','0','1','0','0',_binary '�\�\0sr\0org.quartz.JobDataMap���迩�\�\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�\�\��\�](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\�.�(v\n\�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\��\�`\�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0TASK_PROPERTIESsr\0com.ruoyi.quartz.domain.SysJob\0\0\0\0\0\0\0\0L\0\nconcurrentt\0Ljava/lang/String;L\0cronExpressionq\0~\0	L\0invokeTargetq\0~\0	L\0jobGroupq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0jobNameq\0~\0	L\0\rmisfirePolicyq\0~\0	L\0statusq\0~\0	xr\0\'com.ruoyi.common.core.domain.BaseEntity\0\0\0\0\0\0\0\0L\0createByq\0~\0	L\0\ncreateTimet\0Ljava/util/Date;L\0paramsq\0~\0L\0remarkq\0~\0	L\0searchValueq\0~\0	L\0updateByq\0~\0	L\0\nupdateTimeq\0~\0xpt\0adminsr\0java.util.Datehj�KYt\0\0xpw\0\0w\�t�xpt\0\0pppt\01t\00/20 * * * * ?t\08ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)t\0DEFAULTsr\0java.lang.Long;�\�̏#\�\0J\0valuexr\0java.lang.Number����\��\0\0xp\0\0\0\0\0\0\0t\0系统默认（多参）t\03t\01x\0');
/*!40000 ALTER TABLE `QRTZ_JOB_DETAILS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_LOCKS`
--

DROP TABLE IF EXISTS `QRTZ_LOCKS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_LOCKS` (
  `sched_name` varchar(120) COLLATE utf8mb4_general_ci NOT NULL,
  `lock_name` varchar(40) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`sched_name`,`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `sched_name` varchar(120) COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_group` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `sched_name` varchar(120) COLLATE utf8mb4_general_ci NOT NULL,
  `instance_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `last_checkin_time` bigint(13) NOT NULL,
  `checkin_interval` bigint(13) NOT NULL,
  PRIMARY KEY (`sched_name`,`instance_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SCHEDULER_STATE`
--

LOCK TABLES `QRTZ_SCHEDULER_STATE` WRITE;
/*!40000 ALTER TABLE `QRTZ_SCHEDULER_STATE` DISABLE KEYS */;
INSERT INTO `QRTZ_SCHEDULER_STATE` VALUES ('RuoyiScheduler','Tom1614593939702',1614594649041,15000);
/*!40000 ALTER TABLE `QRTZ_SCHEDULER_STATE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_SIMPLE_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `sched_name` varchar(120) COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_group` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `repeat_count` bigint(7) NOT NULL,
  `repeat_interval` bigint(12) NOT NULL,
  `times_triggered` bigint(10) NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `sched_name` varchar(120) COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_group` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `str_prop_1` varchar(512) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `str_prop_2` varchar(512) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `str_prop_3` varchar(512) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `int_prop_1` int(11) DEFAULT NULL,
  `int_prop_2` int(11) DEFAULT NULL,
  `long_prop_1` bigint(20) DEFAULT NULL,
  `long_prop_2` bigint(20) DEFAULT NULL,
  `dec_prop_1` decimal(13,4) DEFAULT NULL,
  `dec_prop_2` decimal(13,4) DEFAULT NULL,
  `bool_prop_1` varchar(1) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bool_prop_2` varchar(1) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_TRIGGERS` (
  `sched_name` varchar(120) COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_group` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `job_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `job_group` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(250) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `next_fire_time` bigint(13) DEFAULT NULL,
  `prev_fire_time` bigint(13) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `trigger_state` varchar(16) COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_type` varchar(8) COLLATE utf8mb4_general_ci NOT NULL,
  `start_time` bigint(13) NOT NULL,
  `end_time` bigint(13) DEFAULT NULL,
  `calendar_name` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `misfire_instr` smallint(2) DEFAULT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_TRIGGERS`
--

LOCK TABLES `QRTZ_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_TRIGGERS` DISABLE KEYS */;
INSERT INTO `QRTZ_TRIGGERS` VALUES ('RuoyiScheduler','TASK_CLASS_NAME1','DEFAULT','TASK_CLASS_NAME1','DEFAULT',NULL,1614593940000,-1,5,'PAUSED','CRON',1614593939000,0,NULL,2,''),('RuoyiScheduler','TASK_CLASS_NAME2','DEFAULT','TASK_CLASS_NAME2','DEFAULT',NULL,1614593940000,-1,5,'PAUSED','CRON',1614593939000,0,NULL,2,''),('RuoyiScheduler','TASK_CLASS_NAME3','DEFAULT','TASK_CLASS_NAME3','DEFAULT',NULL,1614593940000,-1,5,'PAUSED','CRON',1614593940000,0,NULL,2,'');
/*!40000 ALTER TABLE `QRTZ_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_config` (
  `config_id` int(5) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) COLLATE utf8mb4_general_ci DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='参数配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_config`
--

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` VALUES (1,'主框架页-默认皮肤样式名称','sys.index.skinName','skin-blue','Y','admin','2021-02-24 15:47:04','',NULL,'蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow'),(2,'用户管理-账号初始密码','sys.user.initPassword','123456','Y','admin','2021-02-24 15:47:04','',NULL,'初始化密码 123456'),(3,'主框架页-侧边栏主题','sys.index.sideTheme','theme-dark','Y','admin','2021-02-24 15:47:04','',NULL,'深色主题theme-dark，浅色主题theme-light');
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(50) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `status` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` VALUES (100,0,'0','若依科技2',0,'若依','15888888888','ry@qq.com','0','0','admin','2021-02-24 15:47:02','admin','2021-02-24 16:22:04'),(101,100,'0,100','深圳总公司',1,'若依','15888888888','ry@qq.com','0','0','admin','2021-02-24 15:47:02','',NULL),(102,100,'0,100','长沙分公司',2,'若依','15888888888','ry@qq.com','0','0','admin','2021-02-24 15:47:02','',NULL),(103,101,'0,100,101','研发部门',1,'若依','15888888888','ry@qq.com','0','0','admin','2021-02-24 15:47:02','',NULL),(104,101,'0,100,101','市场部门',2,'若依','15888888888','ry@qq.com','0','0','admin','2021-02-24 15:47:02','',NULL),(105,101,'0,100,101','测试部门',3,'若依','15888888888','ry@qq.com','0','0','admin','2021-02-24 15:47:02','',NULL),(106,101,'0,100,101','财务部门',4,'若依','15888888888','ry@qq.com','0','0','admin','2021-02-24 15:47:02','',NULL),(107,101,'0,100,101','运维部门',5,'若依','15888888888','ry@qq.com','0','0','admin','2021-02-24 15:47:02','',NULL),(108,102,'0,100,102','市场部门',1,'若依','15888888888','ry@qq.com','0','0','admin','2021-02-24 15:47:02','',NULL),(109,102,'0,100,102','财务部门',2,'若依','15888888888','ry@qq.com','0','0','admin','2021-02-24 15:47:02','',NULL);
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_data`
--

DROP TABLE IF EXISTS `sys_dict_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_data` (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) COLLATE utf8mb4_general_ci DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='字典数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_data`
--

LOCK TABLES `sys_dict_data` WRITE;
/*!40000 ALTER TABLE `sys_dict_data` DISABLE KEYS */;
INSERT INTO `sys_dict_data` VALUES (1,1,'男','0','sys_user_sex','','','Y','0','admin','2021-02-24 15:47:04','',NULL,'性别男'),(2,2,'女','1','sys_user_sex','','','N','0','admin','2021-02-24 15:47:04','',NULL,'性别女'),(3,3,'未知','2','sys_user_sex','','','N','0','admin','2021-02-24 15:47:04','',NULL,'性别未知'),(4,1,'显示','0','sys_show_hide','','primary','Y','0','admin','2021-02-24 15:47:04','',NULL,'显示菜单'),(5,2,'隐藏','1','sys_show_hide','','danger','N','0','admin','2021-02-24 15:47:04','',NULL,'隐藏菜单'),(6,1,'正常','0','sys_normal_disable','','primary','Y','0','admin','2021-02-24 15:47:04','',NULL,'正常状态'),(7,2,'停用','1','sys_normal_disable','','danger','N','0','admin','2021-02-24 15:47:04','',NULL,'停用状态'),(8,1,'正常','0','sys_job_status','','primary','Y','0','admin','2021-02-24 15:47:04','',NULL,'正常状态'),(9,2,'暂停','1','sys_job_status','','danger','N','0','admin','2021-02-24 15:47:04','',NULL,'停用状态'),(10,1,'默认','DEFAULT','sys_job_group','','','Y','0','admin','2021-02-24 15:47:04','',NULL,'默认分组'),(11,2,'系统','SYSTEM','sys_job_group','','','N','0','admin','2021-02-24 15:47:04','',NULL,'系统分组'),(12,1,'是','Y','sys_yes_no','','primary','Y','0','admin','2021-02-24 15:47:04','',NULL,'系统默认是'),(13,2,'否','N','sys_yes_no','','danger','N','0','admin','2021-02-24 15:47:04','',NULL,'系统默认否'),(14,1,'通知','1','sys_notice_type','','warning','Y','0','admin','2021-02-24 15:47:04','',NULL,'通知'),(15,2,'公告','2','sys_notice_type','','success','N','0','admin','2021-02-24 15:47:04','',NULL,'公告'),(16,1,'正常','0','sys_notice_status','','primary','Y','0','admin','2021-02-24 15:47:04','',NULL,'正常状态'),(17,2,'关闭','1','sys_notice_status','','danger','N','0','admin','2021-02-24 15:47:04','',NULL,'关闭状态'),(18,1,'新增','1','sys_oper_type','','info','N','0','admin','2021-02-24 15:47:04','',NULL,'新增操作'),(19,2,'修改','2','sys_oper_type','','info','N','0','admin','2021-02-24 15:47:04','',NULL,'修改操作'),(20,3,'删除','3','sys_oper_type','','danger','N','0','admin','2021-02-24 15:47:04','',NULL,'删除操作'),(21,4,'授权','4','sys_oper_type','','primary','N','0','admin','2021-02-24 15:47:04','',NULL,'授权操作'),(22,5,'导出','5','sys_oper_type','','warning','N','0','admin','2021-02-24 15:47:04','',NULL,'导出操作'),(23,6,'导入','6','sys_oper_type','','warning','N','0','admin','2021-02-24 15:47:04','',NULL,'导入操作'),(24,7,'强退','7','sys_oper_type','','danger','N','0','admin','2021-02-24 15:47:04','',NULL,'强退操作'),(25,8,'生成代码','8','sys_oper_type','','warning','N','0','admin','2021-02-24 15:47:04','',NULL,'生成操作'),(26,9,'清空数据','9','sys_oper_type','','danger','N','0','admin','2021-02-24 15:47:04','',NULL,'清空操作'),(27,1,'成功','0','sys_common_status','','primary','N','0','admin','2021-02-24 15:47:04','',NULL,'正常状态'),(28,2,'失败','1','sys_common_status','','danger','N','0','admin','2021-02-24 15:47:04','',NULL,'停用状态');
/*!40000 ALTER TABLE `sys_dict_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_type`
--

DROP TABLE IF EXISTS `sys_dict_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_type` (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典类型',
  `status` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='字典类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_type`
--

LOCK TABLES `sys_dict_type` WRITE;
/*!40000 ALTER TABLE `sys_dict_type` DISABLE KEYS */;
INSERT INTO `sys_dict_type` VALUES (1,'用户性别','sys_user_sex','0','admin','2021-02-24 15:47:04','',NULL,'用户性别列表'),(2,'菜单状态','sys_show_hide','0','admin','2021-02-24 15:47:04','',NULL,'菜单状态列表'),(3,'系统开关','sys_normal_disable','0','admin','2021-02-24 15:47:04','',NULL,'系统开关列表'),(4,'任务状态','sys_job_status','0','admin','2021-02-24 15:47:04','',NULL,'任务状态列表'),(5,'任务分组','sys_job_group','0','admin','2021-02-24 15:47:04','',NULL,'任务分组列表'),(6,'系统是否','sys_yes_no','0','admin','2021-02-24 15:47:04','',NULL,'系统是否列表'),(7,'通知类型','sys_notice_type','0','admin','2021-02-24 15:47:04','',NULL,'通知类型列表'),(8,'通知状态','sys_notice_status','0','admin','2021-02-24 15:47:04','',NULL,'通知状态列表'),(9,'操作类型','sys_oper_type','0','admin','2021-02-24 15:47:04','',NULL,'操作类型列表'),(10,'系统状态','sys_common_status','0','admin','2021-02-24 15:47:04','',NULL,'登录状态列表');
/*!40000 ALTER TABLE `sys_dict_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job`
--

DROP TABLE IF EXISTS `sys_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) COLLATE utf8mb4_general_ci DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='定时任务调度表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job`
--

LOCK TABLES `sys_job` WRITE;
/*!40000 ALTER TABLE `sys_job` DISABLE KEYS */;
INSERT INTO `sys_job` VALUES (1,'系统默认（无参）','DEFAULT','ryTask.ryNoParams','0/10 * * * * ?','3','1','1','admin','2021-02-24 15:47:04','',NULL,''),(2,'系统默认（有参）','DEFAULT','ryTask.ryParams(\'ry\')','0/15 * * * * ?','3','1','1','admin','2021-02-24 15:47:04','',NULL,''),(3,'系统默认（多参）','DEFAULT','ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)','0/20 * * * * ?','3','1','1','admin','2021-02-24 15:47:04','',NULL,'');
/*!40000 ALTER TABLE `sys_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job_log`
--

DROP TABLE IF EXISTS `sys_job_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_job_log` (
  `job_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日志信息',
  `status` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='定时任务调度日志表';
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_logininfor` (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作系统',
  `status` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统访问记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_logininfor`
--

LOCK TABLES `sys_logininfor` WRITE;
/*!40000 ALTER TABLE `sys_logininfor` DISABLE KEYS */;
INSERT INTO `sys_logininfor` VALUES (100,'admin','127.0.0.1','内网IP','Chrome 8','Mac OS X','0','登录成功','2021-02-24 16:06:14'),(101,'admin','127.0.0.1','内网IP','Chrome 8','Mac OS X','0','登录成功','2021-02-24 16:08:06'),(102,'admin','127.0.0.1','内网IP','Chrome 8','Mac OS X','0','登录成功','2021-02-24 16:13:59'),(103,'admin','127.0.0.1','内网IP','Chrome 8','Mac OS X','0','登录成功','2021-02-24 16:15:52'),(104,'admin','127.0.0.1','内网IP','Chrome 8','Mac OS X','0','退出成功','2021-02-24 17:31:00'),(105,'admin','127.0.0.1','内网IP','Chrome 8','Mac OS X','0','登录成功','2021-02-24 17:44:36'),(106,'admin','127.0.0.1','内网IP','Chrome 8','Mac OS X','0','退出成功','2021-02-24 17:46:27'),(107,'admin','127.0.0.1','内网IP','Chrome 8','Mac OS X','0','登录成功','2021-02-24 17:47:49'),(108,'admin','127.0.0.1','内网IP','Chrome 8','Mac OS X','0','退出成功','2021-02-24 17:50:47'),(109,'admin','127.0.0.1','内网IP','Chrome 8','Mac OS X','0','登录成功','2021-02-24 17:50:57'),(110,'admin','127.0.0.1','内网IP','Chrome 8','Mac OS X','0','登录成功','2021-03-01 17:20:31');
/*!40000 ALTER TABLE `sys_logininfor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组件路径',
  `is_frame` int(1) DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `is_cache` int(1) DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2003 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'系统管理',0,1,'system',NULL,1,0,'M','0','0','','system','admin','2021-02-24 15:47:03','',NULL,'系统管理目录'),(2,'系统监控',0,2,'monitor',NULL,1,0,'M','0','0','','monitor','admin','2021-02-24 15:47:03','',NULL,'系统监控目录'),(3,'系统工具',0,3,'tool',NULL,1,0,'M','0','0','','tool','admin','2021-02-24 15:47:03','',NULL,'系统工具目录'),(4,'若依官网',0,4,'http://ruoyi.vip',NULL,0,0,'M','1','1','','guide','admin','2021-02-24 15:47:03','admin','2021-02-24 18:04:37','若依官网地址'),(100,'用户管理',1,1,'user','system/user/index',1,0,'C','0','0','system:user:list','user','admin','2021-02-24 15:47:03','',NULL,'用户管理菜单'),(101,'角色管理',1,2,'role','system/role/index',1,0,'C','0','0','system:role:list','peoples','admin','2021-02-24 15:47:03','',NULL,'角色管理菜单'),(102,'菜单管理',1,3,'menu','system/menu/index',1,0,'C','0','0','system:menu:list','tree-table','admin','2021-02-24 15:47:03','',NULL,'菜单管理菜单'),(103,'部门管理',1,4,'dept','system/dept/index',1,0,'C','0','0','system:dept:list','tree','admin','2021-02-24 15:47:03','',NULL,'部门管理菜单'),(104,'岗位管理',1,5,'post','system/post/index',1,0,'C','0','0','system:post:list','post','admin','2021-02-24 15:47:03','',NULL,'岗位管理菜单'),(105,'字典管理',1,6,'dict','system/dict/index',1,0,'C','0','0','system:dict:list','dict','admin','2021-02-24 15:47:03','',NULL,'字典管理菜单'),(106,'参数设置',1,7,'config','system/config/index',1,0,'C','0','0','system:config:list','edit','admin','2021-02-24 15:47:03','',NULL,'参数设置菜单'),(107,'通知公告',1,8,'notice','system/notice/index',1,0,'C','0','0','system:notice:list','message','admin','2021-02-24 15:47:03','',NULL,'通知公告菜单'),(108,'日志管理',1,9,'log','',1,0,'M','0','0','','log','admin','2021-02-24 15:47:03','admin','2021-02-24 16:26:41','日志管理菜单'),(109,'在线用户',2,1,'online','monitor/online/index',1,0,'C','0','0','monitor:online:list','online','admin','2021-02-24 15:47:03','',NULL,'在线用户菜单'),(110,'定时任务',2,2,'job','monitor/job/index',1,0,'C','0','0','monitor:job:list','job','admin','2021-02-24 15:47:03','',NULL,'定时任务菜单'),(111,'数据监控',2,3,'druid','monitor/druid/index',1,0,'C','0','0','monitor:druid:list','druid','admin','2021-02-24 15:47:03','',NULL,'数据监控菜单'),(112,'服务监控',2,4,'server','monitor/server/index',1,0,'C','0','0','monitor:server:list','server','admin','2021-02-24 15:47:03','',NULL,'服务监控菜单'),(113,'缓存监控',2,5,'cache','monitor/cache/index',1,0,'C','0','0','monitor:cache:list','redis','admin','2021-02-24 15:47:03','',NULL,'缓存监控菜单'),(114,'表单构建',3,1,'build','tool/build/index',1,0,'C','0','0','tool:build:list','build','admin','2021-02-24 15:47:03','',NULL,'表单构建菜单'),(115,'代码生成',3,2,'gen','tool/gen/index',1,0,'C','0','0','tool:gen:list','code','admin','2021-02-24 15:47:03','',NULL,'代码生成菜单'),(116,'系统接口',3,3,'swagger','tool/swagger/index',1,0,'C','0','0','tool:swagger:list','swagger','admin','2021-02-24 15:47:03','',NULL,'系统接口菜单'),(500,'操作日志',108,1,'operlog','monitor/operlog/index',1,0,'C','0','0','monitor:operlog:list','form','admin','2021-02-24 15:47:03','',NULL,'操作日志菜单'),(501,'登录日志',108,2,'logininfor','monitor/logininfor/index',1,0,'C','0','0','monitor:logininfor:list','logininfor','admin','2021-02-24 15:47:03','',NULL,'登录日志菜单'),(1001,'用户查询',100,1,'','',1,0,'F','0','0','system:user:query','#','admin','2021-02-24 15:47:03','',NULL,''),(1002,'用户新增',100,2,'','',1,0,'F','0','0','system:user:add','#','admin','2021-02-24 15:47:03','',NULL,''),(1003,'用户修改',100,3,'','',1,0,'F','0','0','system:user:edit','#','admin','2021-02-24 15:47:03','',NULL,''),(1004,'用户删除',100,4,'','',1,0,'F','0','0','system:user:remove','#','admin','2021-02-24 15:47:03','',NULL,''),(1005,'用户导出',100,5,'','',1,0,'F','0','0','system:user:export','#','admin','2021-02-24 15:47:03','',NULL,''),(1006,'用户导入',100,6,'','',1,0,'F','0','0','system:user:import','#','admin','2021-02-24 15:47:03','',NULL,''),(1007,'重置密码',100,7,'','',1,0,'F','0','0','system:user:resetPwd','#','admin','2021-02-24 15:47:03','',NULL,''),(1008,'角色查询',101,1,'','',1,0,'F','0','0','system:role:query','#','admin','2021-02-24 15:47:03','',NULL,''),(1009,'角色新增',101,2,'','',1,0,'F','0','0','system:role:add','#','admin','2021-02-24 15:47:03','',NULL,''),(1010,'角色修改',101,3,'','',1,0,'F','0','0','system:role:edit','#','admin','2021-02-24 15:47:03','',NULL,''),(1011,'角色删除',101,4,'','',1,0,'F','0','0','system:role:remove','#','admin','2021-02-24 15:47:03','',NULL,''),(1012,'角色导出',101,5,'','',1,0,'F','0','0','system:role:export','#','admin','2021-02-24 15:47:03','',NULL,''),(1013,'菜单查询',102,1,'','',1,0,'F','0','0','system:menu:query','#','admin','2021-02-24 15:47:03','',NULL,''),(1014,'菜单新增',102,2,'','',1,0,'F','0','0','system:menu:add','#','admin','2021-02-24 15:47:03','',NULL,''),(1015,'菜单修改',102,3,'','',1,0,'F','0','0','system:menu:edit','#','admin','2021-02-24 15:47:03','',NULL,''),(1016,'菜单删除',102,4,'','',1,0,'F','0','0','system:menu:remove','#','admin','2021-02-24 15:47:03','',NULL,''),(1017,'部门查询',103,1,'','',1,0,'F','0','0','system:dept:query','#','admin','2021-02-24 15:47:03','',NULL,''),(1018,'部门新增',103,2,'','',1,0,'F','0','0','system:dept:add','#','admin','2021-02-24 15:47:03','',NULL,''),(1019,'部门修改',103,3,'','',1,0,'F','0','0','system:dept:edit','#','admin','2021-02-24 15:47:03','',NULL,''),(1020,'部门删除',103,4,'','',1,0,'F','0','0','system:dept:remove','#','admin','2021-02-24 15:47:03','',NULL,''),(1021,'岗位查询',104,1,'','',1,0,'F','0','0','system:post:query','#','admin','2021-02-24 15:47:03','',NULL,''),(1022,'岗位新增',104,2,'','',1,0,'F','0','0','system:post:add','#','admin','2021-02-24 15:47:03','',NULL,''),(1023,'岗位修改',104,3,'','',1,0,'F','0','0','system:post:edit','#','admin','2021-02-24 15:47:03','',NULL,''),(1024,'岗位删除',104,4,'','',1,0,'F','0','0','system:post:remove','#','admin','2021-02-24 15:47:03','',NULL,''),(1025,'岗位导出',104,5,'','',1,0,'F','0','0','system:post:export','#','admin','2021-02-24 15:47:03','',NULL,''),(1026,'字典查询',105,1,'#','',1,0,'F','0','0','system:dict:query','#','admin','2021-02-24 15:47:03','',NULL,''),(1027,'字典新增',105,2,'#','',1,0,'F','0','0','system:dict:add','#','admin','2021-02-24 15:47:03','',NULL,''),(1028,'字典修改',105,3,'#','',1,0,'F','0','0','system:dict:edit','#','admin','2021-02-24 15:47:03','',NULL,''),(1029,'字典删除',105,4,'#','',1,0,'F','0','0','system:dict:remove','#','admin','2021-02-24 15:47:03','',NULL,''),(1030,'字典导出',105,5,'#','',1,0,'F','0','0','system:dict:export','#','admin','2021-02-24 15:47:03','',NULL,''),(1031,'参数查询',106,1,'#','',1,0,'F','0','0','system:config:query','#','admin','2021-02-24 15:47:03','',NULL,''),(1032,'参数新增',106,2,'#','',1,0,'F','0','0','system:config:add','#','admin','2021-02-24 15:47:03','',NULL,''),(1033,'参数修改',106,3,'#','',1,0,'F','0','0','system:config:edit','#','admin','2021-02-24 15:47:03','',NULL,''),(1034,'参数删除',106,4,'#','',1,0,'F','0','0','system:config:remove','#','admin','2021-02-24 15:47:03','',NULL,''),(1035,'参数导出',106,5,'#','',1,0,'F','0','0','system:config:export','#','admin','2021-02-24 15:47:03','',NULL,''),(1036,'公告查询',107,1,'#','',1,0,'F','0','0','system:notice:query','#','admin','2021-02-24 15:47:03','',NULL,''),(1037,'公告新增',107,2,'#','',1,0,'F','0','0','system:notice:add','#','admin','2021-02-24 15:47:03','',NULL,''),(1038,'公告修改',107,3,'#','',1,0,'F','0','0','system:notice:edit','#','admin','2021-02-24 15:47:03','',NULL,''),(1039,'公告删除',107,4,'#','',1,0,'F','0','0','system:notice:remove','#','admin','2021-02-24 15:47:03','',NULL,''),(1040,'操作查询',500,1,'#','',1,0,'F','0','0','monitor:operlog:query','#','admin','2021-02-24 15:47:03','',NULL,''),(1041,'操作删除',500,2,'#','',1,0,'F','0','0','monitor:operlog:remove','#','admin','2021-02-24 15:47:03','',NULL,''),(1042,'日志导出',500,4,'#','',1,0,'F','0','0','monitor:operlog:export','#','admin','2021-02-24 15:47:03','',NULL,''),(1043,'登录查询',501,1,'#','',1,0,'F','0','0','monitor:logininfor:query','#','admin','2021-02-24 15:47:03','',NULL,''),(1044,'登录删除',501,2,'#','',1,0,'F','0','0','monitor:logininfor:remove','#','admin','2021-02-24 15:47:03','',NULL,''),(1045,'日志导出',501,3,'#','',1,0,'F','0','0','monitor:logininfor:export','#','admin','2021-02-24 15:47:03','',NULL,''),(1046,'在线查询',109,1,'#','',1,0,'F','0','0','monitor:online:query','#','admin','2021-02-24 15:47:03','',NULL,''),(1047,'批量强退',109,2,'#','',1,0,'F','0','0','monitor:online:batchLogout','#','admin','2021-02-24 15:47:03','',NULL,''),(1048,'单条强退',109,3,'#','',1,0,'F','0','0','monitor:online:forceLogout','#','admin','2021-02-24 15:47:03','',NULL,''),(1049,'任务查询',110,1,'#','',1,0,'F','0','0','monitor:job:query','#','admin','2021-02-24 15:47:03','',NULL,''),(1050,'任务新增',110,2,'#','',1,0,'F','0','0','monitor:job:add','#','admin','2021-02-24 15:47:03','',NULL,''),(1051,'任务修改',110,3,'#','',1,0,'F','0','0','monitor:job:edit','#','admin','2021-02-24 15:47:03','',NULL,''),(1052,'任务删除',110,4,'#','',1,0,'F','0','0','monitor:job:remove','#','admin','2021-02-24 15:47:03','',NULL,''),(1053,'状态修改',110,5,'#','',1,0,'F','0','0','monitor:job:changeStatus','#','admin','2021-02-24 15:47:03','',NULL,''),(1054,'任务导出',110,7,'#','',1,0,'F','0','0','monitor:job:export','#','admin','2021-02-24 15:47:03','',NULL,''),(1055,'生成查询',115,1,'#','',1,0,'F','0','0','tool:gen:query','#','admin','2021-02-24 15:47:03','',NULL,''),(1056,'生成修改',115,2,'#','',1,0,'F','0','0','tool:gen:edit','#','admin','2021-02-24 15:47:03','',NULL,''),(1057,'生成删除',115,3,'#','',1,0,'F','0','0','tool:gen:remove','#','admin','2021-02-24 15:47:03','',NULL,''),(1058,'导入代码',115,2,'#','',1,0,'F','0','0','tool:gen:import','#','admin','2021-02-24 15:47:03','',NULL,''),(1059,'预览代码',115,4,'#','',1,0,'F','0','0','tool:gen:preview','#','admin','2021-02-24 15:47:03','',NULL,''),(1060,'生成代码',115,5,'#','',1,0,'F','0','0','tool:gen:code','#','admin','2021-02-24 15:47:03','',NULL,''),(2000,'项目管理',0,4,'project',NULL,1,0,'M','0','0','','education','admin','2021-02-24 16:58:12','admin','2021-02-24 16:58:38',''),(2001,'纵向项目',2000,1,'zx','project/portrait/index',1,0,'C','0','0','project:portrait:list','online','admin','2021-02-24 16:59:21','admin','2021-02-24 18:06:47',''),(2002,'新增项目',2001,1,'',NULL,1,0,'F','0','0','project:portrait:add','#','admin','2021-02-24 17:03:18','admin','2021-02-24 18:07:11','');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_notice`
--

DROP TABLE IF EXISTS `sys_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_notice` (
  `notice_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告标题',
  `notice_type` char(1) COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob COMMENT '公告内容',
  `status` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='通知公告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notice`
--

LOCK TABLES `sys_notice` WRITE;
/*!40000 ALTER TABLE `sys_notice` DISABLE KEYS */;
INSERT INTO `sys_notice` VALUES (1,'温馨提醒：2018-07-01 若依新版本发布啦','2',_binary '新版本内容','0','admin','2021-02-24 15:47:05','',NULL,'管理员'),(2,'维护通知：2018-07-01 若依系统凌晨维护','1',_binary '维护内容','0','admin','2021-02-24 15:47:05','',NULL,'管理员');
/*!40000 ALTER TABLE `sys_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_oper_log`
--

DROP TABLE IF EXISTS `sys_oper_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_oper_log` (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '模块标题',
  `business_type` int(2) DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求方式',
  `operator_type` int(1) DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '返回参数',
  `status` int(1) DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_oper_log`
--

LOCK TABLES `sys_oper_log` WRITE;
/*!40000 ALTER TABLE `sys_oper_log` DISABLE KEYS */;
INSERT INTO `sys_oper_log` VALUES (100,'部门管理',2,'com.ruoyi.web.controller.system.SysDeptController.edit()','PUT',1,'admin',NULL,'/system/dept','127.0.0.1','内网IP','{\"deptName\":\"若依科技2\",\"leader\":\"若依\",\"deptId\":100,\"orderNum\":\"0\",\"delFlag\":\"0\",\"params\":{},\"parentId\":0,\"createBy\":\"admin\",\"children\":[],\"createTime\":1614152822000,\"phone\":\"15888888888\",\"updateBy\":\"admin\",\"ancestors\":\"0\",\"email\":\"ry@qq.com\",\"status\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-02-24 16:22:04'),(101,'菜单管理',2,'com.ruoyi.web.controller.system.SysMenuController.edit()','PUT',1,'admin',NULL,'/system/menu','127.0.0.1','内网IP','{\"visible\":\"1\",\"icon\":\"log\",\"orderNum\":\"9\",\"menuName\":\"日志管理\",\"params\":{},\"parentId\":1,\"isCache\":\"0\",\"path\":\"log\",\"component\":\"\",\"children\":[],\"createTime\":1614152823000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":108,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-02-24 16:26:14'),(102,'菜单管理',2,'com.ruoyi.web.controller.system.SysMenuController.edit()','PUT',1,'admin',NULL,'/system/menu','127.0.0.1','内网IP','{\"visible\":\"0\",\"icon\":\"log\",\"orderNum\":\"9\",\"menuName\":\"日志管理\",\"params\":{},\"parentId\":1,\"isCache\":\"0\",\"path\":\"log\",\"component\":\"\",\"children\":[],\"createTime\":1614152823000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":108,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-02-24 16:26:41'),(103,'字典类型',9,'com.ruoyi.web.controller.system.SysDictTypeController.clearCache()','DELETE',1,'admin',NULL,'/system/dict/type/clearCache','127.0.0.1','内网IP','{}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-02-24 16:27:45'),(104,'字典类型',5,'com.ruoyi.web.controller.system.SysDictTypeController.export()','GET',1,'admin',NULL,'/system/dict/type/export','127.0.0.1','内网IP','{}','{\"msg\":\"b28f4820-7769-4122-aa3f-2ae9c23104e4_字典类型.xlsx\",\"code\":200}',0,NULL,'2021-02-24 16:27:50'),(105,'字典类型',5,'com.ruoyi.web.controller.system.SysDictTypeController.export()','GET',1,'admin',NULL,'/system/dict/type/export','127.0.0.1','内网IP','{}','{\"msg\":\"51774dc2-d2f4-4b16-9307-e3688ec1f245_字典类型.xlsx\",\"code\":200}',0,NULL,'2021-02-24 16:28:15'),(106,'字典类型',5,'com.ruoyi.web.controller.system.SysDictTypeController.export()','GET',1,'admin',NULL,'/system/dict/type/export','127.0.0.1','内网IP','{}','{\"msg\":\"80bb2c4b-2718-48d5-b265-3be092a501ff_字典类型.xlsx\",\"code\":200}',0,NULL,'2021-02-24 16:28:19'),(107,'角色管理',1,'com.ruoyi.web.controller.system.SysRoleController.add()','POST',1,'admin',NULL,'/system/role','127.0.0.1','内网IP','{\"flag\":false,\"roleId\":100,\"admin\":false,\"params\":{},\"roleSort\":\"0\",\"deptCheckStrictly\":true,\"createBy\":\"admin\",\"menuCheckStrictly\":true,\"roleKey\":\"hose\",\"roleName\":\"仓库管理\",\"deptIds\":[],\"menuIds\":[4],\"status\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-02-24 16:49:28'),(108,'菜单管理',3,'com.ruoyi.web.controller.system.SysMenuController.remove()','DELETE',1,'admin',NULL,'/system/menu/4','127.0.0.1','内网IP','{menuId=4}','{\"msg\":\"菜单已分配,不允许删除\",\"code\":500}',0,NULL,'2021-02-24 16:57:10'),(109,'菜单管理',3,'com.ruoyi.web.controller.system.SysMenuController.remove()','DELETE',1,'admin',NULL,'/system/menu/4','127.0.0.1','内网IP','{menuId=4}','{\"msg\":\"菜单已分配,不允许删除\",\"code\":500}',0,NULL,'2021-02-24 16:57:16'),(110,'菜单管理',1,'com.ruoyi.web.controller.system.SysMenuController.add()','POST',1,'admin',NULL,'/system/menu','127.0.0.1','内网IP','{\"visible\":\"0\",\"icon\":\"education\",\"orderNum\":\"4\",\"menuName\":\"项目管理\",\"params\":{},\"parentId\":0,\"isCache\":\"0\",\"path\":\"xs\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"M\",\"status\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-02-24 16:58:12'),(111,'菜单管理',2,'com.ruoyi.web.controller.system.SysMenuController.edit()','PUT',1,'admin',NULL,'/system/menu','127.0.0.1','内网IP','{\"visible\":\"0\",\"icon\":\"education\",\"orderNum\":\"4\",\"menuName\":\"项目管理\",\"params\":{},\"parentId\":0,\"isCache\":\"0\",\"path\":\"project\",\"children\":[],\"createTime\":1614157092000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2000,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-02-24 16:58:38'),(112,'菜单管理',1,'com.ruoyi.web.controller.system.SysMenuController.add()','POST',1,'admin',NULL,'/system/menu','127.0.0.1','内网IP','{\"visible\":\"0\",\"icon\":\"online\",\"orderNum\":\"1\",\"menuName\":\"纵向项目\",\"params\":{},\"parentId\":2000,\"isCache\":\"0\",\"path\":\"project/zx\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"M\",\"status\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-02-24 16:59:21'),(113,'菜单管理',2,'com.ruoyi.web.controller.system.SysMenuController.edit()','PUT',1,'admin',NULL,'/system/menu','127.0.0.1','内网IP','{\"visible\":\"0\",\"icon\":\"online\",\"orderNum\":\"1\",\"menuName\":\"纵向项目\",\"params\":{},\"parentId\":2000,\"isCache\":\"0\",\"path\":\"zx\",\"children\":[],\"createTime\":1614157161000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2001,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-02-24 17:00:13'),(114,'菜单管理',2,'com.ruoyi.web.controller.system.SysMenuController.edit()','PUT',1,'admin',NULL,'/system/menu','127.0.0.1','内网IP','{\"visible\":\"0\",\"icon\":\"online\",\"orderNum\":\"1\",\"menuName\":\"纵向项目\",\"params\":{},\"parentId\":2000,\"isCache\":\"0\",\"path\":\"zx\",\"component\":\"project/zx/index\",\"children\":[],\"createTime\":1614157161000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2001,\"menuType\":\"C\",\"perms\":\"project:zx:index\",\"status\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-02-24 17:02:52'),(115,'菜单管理',1,'com.ruoyi.web.controller.system.SysMenuController.add()','POST',1,'admin',NULL,'/system/menu','127.0.0.1','内网IP','{\"visible\":\"0\",\"orderNum\":\"1\",\"menuName\":\"新增项目\",\"params\":{},\"parentId\":2001,\"isCache\":\"0\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"F\",\"perms\":\"project/zx/add\",\"status\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-02-24 17:03:18'),(116,'菜单管理',2,'com.ruoyi.web.controller.system.SysMenuController.edit()','PUT',1,'admin',NULL,'/system/menu','127.0.0.1','内网IP','{\"visible\":\"0\",\"icon\":\"online\",\"orderNum\":\"1\",\"menuName\":\"纵向项目\",\"params\":{},\"parentId\":2000,\"isCache\":\"0\",\"path\":\"zx\",\"component\":\"project/portrait/index\",\"children\":[],\"createTime\":1614157161000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2001,\"menuType\":\"C\",\"perms\":\"project:portrait:index\",\"status\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-02-24 17:10:38'),(117,'菜单管理',2,'com.ruoyi.web.controller.system.SysMenuController.edit()','PUT',1,'admin',NULL,'/system/menu','127.0.0.1','内网IP','{\"visible\":\"0\",\"icon\":\"#\",\"orderNum\":\"1\",\"menuName\":\"新增项目\",\"params\":{},\"parentId\":2001,\"isCache\":\"0\",\"path\":\"\",\"children\":[],\"createTime\":1614157398000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2002,\"menuType\":\"F\",\"perms\":\"project/portrait/add\",\"status\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-02-24 17:10:50'),(118,'菜单管理',3,'com.ruoyi.web.controller.system.SysMenuController.remove()','DELETE',1,'admin',NULL,'/system/menu/4','127.0.0.1','内网IP','{menuId=4}','{\"msg\":\"菜单已分配,不允许删除\",\"code\":500}',0,NULL,'2021-02-24 18:03:52'),(119,'菜单管理',2,'com.ruoyi.web.controller.system.SysMenuController.edit()','PUT',1,'admin',NULL,'/system/menu','127.0.0.1','内网IP','{\"visible\":\"1\",\"icon\":\"guide\",\"orderNum\":\"4\",\"menuName\":\"若依官网\",\"params\":{},\"parentId\":0,\"isCache\":\"0\",\"path\":\"http://ruoyi.vip\",\"children\":[],\"createTime\":1614152823000,\"updateBy\":\"admin\",\"isFrame\":\"0\",\"menuId\":4,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"1\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-02-24 18:04:03'),(120,'菜单管理',3,'com.ruoyi.web.controller.system.SysMenuController.remove()','DELETE',1,'admin',NULL,'/system/menu/4','127.0.0.1','内网IP','{menuId=4}','{\"msg\":\"菜单已分配,不允许删除\",\"code\":500}',0,NULL,'2021-02-24 18:04:07'),(121,'菜单管理',2,'com.ruoyi.web.controller.system.SysMenuController.edit()','PUT',1,'admin',NULL,'/system/menu','127.0.0.1','内网IP','{\"visible\":\"1\",\"icon\":\"guide\",\"orderNum\":\"4\",\"menuName\":\"若依官网\",\"params\":{},\"parentId\":0,\"isCache\":\"0\",\"path\":\"http://ruoyi.vip\",\"children\":[],\"createTime\":1614152823000,\"updateBy\":\"admin\",\"isFrame\":\"0\",\"menuId\":4,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"1\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-02-24 18:04:37'),(122,'菜单管理',3,'com.ruoyi.web.controller.system.SysMenuController.remove()','DELETE',1,'admin',NULL,'/system/menu/4','127.0.0.1','内网IP','{menuId=4}','{\"msg\":\"菜单已分配,不允许删除\",\"code\":500}',0,NULL,'2021-02-24 18:06:00'),(123,'菜单管理',2,'com.ruoyi.web.controller.system.SysMenuController.edit()','PUT',1,'admin',NULL,'/system/menu','127.0.0.1','内网IP','{\"visible\":\"0\",\"icon\":\"online\",\"orderNum\":\"1\",\"menuName\":\"纵向项目\",\"params\":{},\"parentId\":2000,\"isCache\":\"0\",\"path\":\"zx\",\"component\":\"project/portrait/list\",\"children\":[],\"createTime\":1614157161000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2001,\"menuType\":\"C\",\"perms\":\"project:portrait:index\",\"status\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-02-24 18:06:32'),(124,'菜单管理',2,'com.ruoyi.web.controller.system.SysMenuController.edit()','PUT',1,'admin',NULL,'/system/menu','127.0.0.1','内网IP','{\"visible\":\"0\",\"icon\":\"online\",\"orderNum\":\"1\",\"menuName\":\"纵向项目\",\"params\":{},\"parentId\":2000,\"isCache\":\"0\",\"path\":\"zx\",\"component\":\"project/portrait/index\",\"children\":[],\"createTime\":1614157161000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2001,\"menuType\":\"C\",\"perms\":\"project:portrait:list\",\"status\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-02-24 18:06:47'),(125,'菜单管理',2,'com.ruoyi.web.controller.system.SysMenuController.edit()','PUT',1,'admin',NULL,'/system/menu','127.0.0.1','内网IP','{\"visible\":\"0\",\"icon\":\"#\",\"orderNum\":\"1\",\"menuName\":\"新增项目\",\"params\":{},\"parentId\":2001,\"isCache\":\"0\",\"path\":\"\",\"children\":[],\"createTime\":1614157398000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2002,\"menuType\":\"F\",\"perms\":\"project:portrait:add\",\"status\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2021-02-24 18:07:11'),(126,'菜单管理',3,'com.ruoyi.web.controller.system.SysMenuController.remove()','DELETE',1,'admin',NULL,'/system/menu/4','127.0.0.1','内网IP','{menuId=4}','{\"msg\":\"菜单已分配,不允许删除\",\"code\":500}',0,NULL,'2021-02-24 18:08:44'),(127,'菜单管理',3,'com.ruoyi.web.controller.system.SysMenuController.remove()','DELETE',1,'admin',NULL,'/system/menu/4','127.0.0.1','内网IP','{menuId=4}','{\"msg\":\"菜单已分配,不允许删除\",\"code\":500}',0,NULL,'2021-02-24 18:09:58'),(128,'菜单管理',3,'com.ruoyi.web.controller.system.SysMenuController.remove()','DELETE',1,'admin',NULL,'/system/menu/4','127.0.0.1','内网IP','{menuId=4}','{\"msg\":\"菜单已分配,不允许删除\",\"code\":500}',0,NULL,'2021-02-24 18:11:18'),(129,'菜单管理',3,'com.ruoyi.web.controller.system.SysMenuController.remove()','DELETE',1,'admin',NULL,'/system/menu/4','127.0.0.1','内网IP','{menuId=4}','{\"msg\":\"菜单已分配,不允许删除\",\"code\":500}',0,NULL,'2021-03-01 17:21:27'),(130,'菜单管理',3,'com.ruoyi.web.controller.system.SysMenuController.remove()','DELETE',1,'admin',NULL,'/system/menu/4','127.0.0.1','内网IP','{menuId=4}','{\"msg\":\"菜单已分配,不允许删除\",\"code\":500}',0,NULL,'2021-03-01 17:26:19'),(131,'菜单管理',3,'com.ruoyi.web.controller.system.SysMenuController.remove()','DELETE',1,'admin',NULL,'/system/menu/4','127.0.0.1','内网IP','{menuId=4}','{\"msg\":\"菜单已分配,不允许删除\",\"code\":500}',0,NULL,'2021-03-01 17:35:56');
/*!40000 ALTER TABLE `sys_oper_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_post`
--

DROP TABLE IF EXISTS `sys_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_post` (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int(4) NOT NULL COMMENT '显示顺序',
  `status` char(1) COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='岗位信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_post`
--

LOCK TABLES `sys_post` WRITE;
/*!40000 ALTER TABLE `sys_post` DISABLE KEYS */;
INSERT INTO `sys_post` VALUES (1,'ceo','董事长',1,'0','admin','2021-02-24 15:47:02','',NULL,''),(2,'se','项目经理',2,'0','admin','2021-02-24 15:47:02','',NULL,''),(3,'hr','人力资源',3,'0','admin','2021-02-24 15:47:02','',NULL,''),(4,'user','普通员工',4,'0','admin','2021-02-24 15:47:02','',NULL,'');
/*!40000 ALTER TABLE `sys_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) DEFAULT '1' COMMENT '部门树选择项是否关联显示',
  `status` char(1) COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'超级管理员','admin',1,'1',1,1,'0','0','admin','2021-02-24 15:47:03','',NULL,'超级管理员'),(2,'普通角色','common',2,'2',1,1,'0','0','admin','2021-02-24 15:47:03','',NULL,'普通角色'),(100,'仓库管理','hose',0,'1',1,1,'0','0','admin','2021-02-24 16:49:28','',NULL,NULL);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_dept`
--

DROP TABLE IF EXISTS `sys_role_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_dept` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色和部门关联表';
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色和菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (2,1),(2,2),(2,3),(2,4),(2,100),(2,101),(2,102),(2,103),(2,104),(2,105),(2,106),(2,107),(2,108),(2,109),(2,110),(2,111),(2,112),(2,113),(2,114),(2,115),(2,116),(2,500),(2,501),(2,1000),(2,1001),(2,1002),(2,1003),(2,1004),(2,1005),(2,1006),(2,1007),(2,1008),(2,1009),(2,1010),(2,1011),(2,1012),(2,1013),(2,1014),(2,1015),(2,1016),(2,1017),(2,1018),(2,1019),(2,1020),(2,1021),(2,1022),(2,1023),(2,1024),(2,1025),(2,1026),(2,1027),(2,1028),(2,1029),(2,1030),(2,1031),(2,1032),(2,1033),(2,1034),(2,1035),(2,1036),(2,1037),(2,1038),(2,1039),(2,1040),(2,1041),(2,1042),(2,1043),(2,1044),(2,1045),(2,1046),(2,1047),(2,1048),(2,1049),(2,1050),(2,1051),(2,1052),(2,1053),(2,1054),(2,1055),(2,1056),(2,1057),(2,1058),(2,1059),(2,1060),(100,4);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) COLLATE utf8mb4_general_ci DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '手机号码',
  `sex` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '密码',
  `status` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,103,'admin','若依','00','ry@163.com','15888888888','1','','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2','0','0','127.0.0.1','2021-02-24 15:47:02','admin','2021-02-24 15:47:02','',NULL,'管理员'),(2,105,'ry','若依','00','ry@qq.com','15666666666','1','','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2','0','0','127.0.0.1','2021-02-24 15:47:02','admin','2021-02-24 15:47:02','',NULL,'测试员');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_post`
--

DROP TABLE IF EXISTS `sys_user_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_post` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户与岗位关联表';
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户和角色关联表';
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

-- Dump completed on 2021-03-01 18:34:04
