-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: ry-vue
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `di_case_classification`
--

DROP TABLE IF EXISTS `di_case_classification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `di_case_classification` (
  `id` int NOT NULL,
  `classification_name` varchar(45) DEFAULT NULL COMMENT '分类名称',
  `icon` varchar(45) DEFAULT NULL COMMENT '图标',
  `explain` varchar(45) DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='案例分类';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `di_case_classification`
--

LOCK TABLES `di_case_classification` WRITE;
/*!40000 ALTER TABLE `di_case_classification` DISABLE KEYS */;
/*!40000 ALTER TABLE `di_case_classification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `di_classic_case`
--

DROP TABLE IF EXISTS `di_classic_case`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `di_classic_case` (
  `id` int NOT NULL,
  `case_classification` varchar(45) DEFAULT NULL COMMENT '案例分类',
  `case_number` varchar(45) DEFAULT NULL COMMENT '案例编号',
  `brief_explanation_case` varchar(45) DEFAULT NULL COMMENT '案例简要说明',
  `upload_other_attachments` varchar(45) DEFAULT NULL COMMENT '其它附件上传',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='典型案例';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `di_classic_case`
--

LOCK TABLES `di_classic_case` WRITE;
/*!40000 ALTER TABLE `di_classic_case` DISABLE KEYS */;
/*!40000 ALTER TABLE `di_classic_case` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `di_institutional`
--

DROP TABLE IF EXISTS `di_institutional`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `di_institutional` (
  `id` int NOT NULL,
  `institutional_name` varchar(45) DEFAULT NULL COMMENT '制度名称',
  `institutional_no` varchar(45) DEFAULT NULL COMMENT '制度编号',
  `institutional_type_id` varchar(45) DEFAULT NULL COMMENT '制度分类id',
  `file_year` varchar(45) DEFAULT NULL COMMENT '文件年度',
  `version` varchar(45) DEFAULT NULL COMMENT '版本',
  `upload_date` varchar(45) DEFAULT NULL COMMENT '上传日期',
  `creator` varchar(45) DEFAULT NULL COMMENT '创建人',
  `institutional_type_name` varchar(45) DEFAULT NULL COMMENT '制度分类名称',
  `file_classification` varchar(45) DEFAULT NULL COMMENT '文件分类',
  `technical_committees` varchar(45) DEFAULT NULL COMMENT '归口单位',
  `institutional_outline` varchar(45) DEFAULT NULL COMMENT '制度大纲',
  `main_text_upload` varchar(45) DEFAULT NULL COMMENT '制度正文上传',
  `upload_other_attachments` varchar(45) DEFAULT NULL COMMENT '其他附件上传',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='制度';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `di_institutional`
--

LOCK TABLES `di_institutional` WRITE;
/*!40000 ALTER TABLE `di_institutional` DISABLE KEYS */;
/*!40000 ALTER TABLE `di_institutional` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `di_institutional_type`
--

DROP TABLE IF EXISTS `di_institutional_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `di_institutional_type` (
  `id` int NOT NULL,
  `institutional_type_name` varchar(45) DEFAULT NULL COMMENT '制度分类名称',
  `parent_id` varchar(45) DEFAULT NULL COMMENT '父节点',
  `is_enabled` varchar(45) DEFAULT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='制度分类';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `di_institutional_type`
--

LOCK TABLES `di_institutional_type` WRITE;
/*!40000 ALTER TABLE `di_institutional_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `di_institutional_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `di_integrity_archives`
--

DROP TABLE IF EXISTS `di_integrity_archives`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `di_integrity_archives` (
  `id` int NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `job_id` varchar(45) DEFAULT NULL COMMENT '工号',
  `id_number` varchar(45) DEFAULT NULL COMMENT '身份证号',
  `year` varchar(45) DEFAULT NULL COMMENT '年度',
  `file_content` varchar(45) DEFAULT NULL COMMENT '文件内容',
  `upload_other_attachments` varchar(45) DEFAULT NULL COMMENT '其它附件上传',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='廉政档案';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `di_integrity_archives`
--

LOCK TABLES `di_integrity_archives` WRITE;
/*!40000 ALTER TABLE `di_integrity_archives` DISABLE KEYS */;
/*!40000 ALTER TABLE `di_integrity_archives` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `di_knowledge`
--

DROP TABLE IF EXISTS `di_knowledge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `di_knowledge` (
  `id` int NOT NULL,
  `document_name` varchar(45) DEFAULT NULL COMMENT '文档名称',
  `classification_belonging_knowledge` varchar(45) DEFAULT NULL COMMENT '所属知识分类',
  `creation_time` varchar(45) DEFAULT NULL COMMENT '创建时间',
  `file_description` varchar(45) DEFAULT NULL COMMENT '文件描述',
  `file_upload` varchar(45) DEFAULT NULL COMMENT '文件上传',
  `remarks` varchar(45) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='知识';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `di_knowledge`
--

LOCK TABLES `di_knowledge` WRITE;
/*!40000 ALTER TABLE `di_knowledge` DISABLE KEYS */;
/*!40000 ALTER TABLE `di_knowledge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `di_knowledge_classification`
--

DROP TABLE IF EXISTS `di_knowledge_classification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `di_knowledge_classification` (
  `id` int NOT NULL,
  `classification_name` varchar(45) DEFAULT NULL COMMENT '分类名称',
  `classification_labels` varchar(45) DEFAULT NULL COMMENT '分类标签',
  `icon` varchar(45) DEFAULT NULL,
  `explain` varchar(45) DEFAULT NULL COMMENT '说明',
  `parent_id` varchar(45) DEFAULT NULL COMMENT '分类父节点',
  `parent_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='知识分类';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `di_knowledge_classification`
--

LOCK TABLES `di_knowledge_classification` WRITE;
/*!40000 ALTER TABLE `di_knowledge_classification` DISABLE KEYS */;
/*!40000 ALTER TABLE `di_knowledge_classification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `di_talent_profile`
--

DROP TABLE IF EXISTS `di_talent_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `di_talent_profile` (
  `id` int NOT NULL,
  `name` varchar(45) DEFAULT NULL COMMENT '姓名',
  `id_number` varchar(45) DEFAULT NULL COMMENT '身份证号',
  `job_id` varchar(45) DEFAULT NULL COMMENT '工号',
  `birth_date` varchar(45) DEFAULT NULL COMMENT '出生年月',
  `rank` varchar(45) DEFAULT NULL COMMENT '职级',
  `qualification` varchar(45) DEFAULT NULL COMMENT '学历',
  `name_graduation_school` varchar(45) DEFAULT NULL COMMENT '毕业学校名称',
  `honorary_title` varchar(45) DEFAULT NULL COMMENT '荣誉称号',
  `remarks` varchar(45) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `di_talent_profilecol_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `di_talent_profile`
--

LOCK TABLES `di_talent_profile` WRITE;
/*!40000 ALTER TABLE `di_talent_profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `di_talent_profile` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-13 14:47:40
