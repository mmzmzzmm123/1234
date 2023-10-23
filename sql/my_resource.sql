-- `ruoyi-vue`.t_classifications definition

CREATE TABLE `t_classifications` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '更新者',
  `parent_id` bigint DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `level` int DEFAULT NULL COMMENT '当前分类层级数',
  `english_name` varchar(100) DEFAULT NULL COMMENT '英文名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='多级分类树表';


-- `ruoyi-vue`.t_content definition

CREATE TABLE `t_content` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `file_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `display_name` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `description` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '资源描述',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源目录',
  `minimum_compatibility` double DEFAULT '0' COMMENT '最小支持版本',
  `maximum_compatibility` double DEFAULT NULL COMMENT '最高支持版本',
  `replace_id` bigint unsigned DEFAULT NULL COMMENT '被取代资源pak_id',
  `classification1` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '一级分类',
  `classification2` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '二级分类',
  `classification3` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '三级分类',
  `classification4` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '四级分类',
  `special_tag` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '特殊标签,用来标记....',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '类型',
  `icon_osskey` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `pak_osskey` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `is_test` tinyint unsigned DEFAULT '0' COMMENT '是否为测试资源,测试资源无法同步到下个环境',
  `ownerid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名id',
  `is_hide` tinyint unsigned DEFAULT '0' COMMENT '是否隐藏',
  `replace_times` int unsigned DEFAULT '0' COMMENT '被覆盖次数',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int unsigned DEFAULT '0',
  `idem_hash` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字段幂等键哈希',
  `pak_sha1` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '资源下载时的sha1,客户端用来做更新比对校验',
  `current_upload_time` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最近上传时间',
  `guid` bigint unsigned NOT NULL COMMENT 'pak_id资源的实际id',
  `identifier` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '唯一标识,用来分配pakId的key',
  `custom` int DEFAULT '0' COMMENT '定制资源',
  `json_osskey` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'json文件的osskey',
  `json_sha1` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `pak_file_size` int DEFAULT NULL,
  `json_file_size` int DEFAULT NULL,
  `sort` int DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_content_path_IDX` (`path`),
  UNIQUE KEY `t_content_pak_id_IDX` (`guid`),
  KEY `t_content_classification1_IDX` (`classification1`) USING BTREE,
  KEY `t_content_classification2_IDX` (`classification2`) USING BTREE,
  KEY `t_content_classification3_IDX` (`classification3`) USING BTREE,
  KEY `t_content_idem_hash_IDX` (`idem_hash`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=498 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='资源冗余大表';


-- `ruoyi-vue`.t_content_oper_log definition

CREATE TABLE `t_content_oper_log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `file_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `display_name` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `description` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '资源描述',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '资源目录',
  `minimum_compatibility` double DEFAULT '0' COMMENT '最小支持版本',
  `maximum_compatibility` double DEFAULT NULL COMMENT '最高支持版本',
  `replace_id` bigint unsigned DEFAULT NULL COMMENT '被取代资源pak_id',
  `classification1` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '一级分类',
  `classification2` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '二级分类',
  `classification3` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '三级分类',
  `classification4` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '四级分类',
  `special_tag` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '特殊标签,用来标记....',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '类型',
  `icon_osskey` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `pak_osskey` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `is_test` tinyint unsigned DEFAULT '0' COMMENT '是否为测试资源,测试资源无法同步到下个环境',
  `ownerid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名id',
  `is_hide` tinyint unsigned DEFAULT '0' COMMENT '是否隐藏',
  `replace_times` int unsigned DEFAULT '0' COMMENT '被覆盖次数',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int unsigned DEFAULT '0',
  `idem_hash` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字段幂等键哈希',
  `pak_sha1` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '资源下载时的sha1,客户端用来做更新比对校验',
  `current_upload_time` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最近上传时间',
  `guid` bigint unsigned DEFAULT NULL COMMENT 'pak_id资源的实际id',
  `identifier` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '唯一标识,用来分配pakId的key',
  `general_tag` varchar(70) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '通用标签,起初是为了存父资源id',
  `custom` int DEFAULT '0' COMMENT '定制资源',
  `partition_sort` bigint unsigned DEFAULT '0' COMMENT '分组内的顺序',
  `json_osskey` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'json文件的osskey',
  `json_sha1` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `pak_file_size` int DEFAULT NULL,
  `json_file_size` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `t_content_classification1_IDX` (`classification1`) USING BTREE,
  KEY `t_content_classification2_IDX` (`classification2`) USING BTREE,
  KEY `t_content_classification3_IDX` (`classification3`) USING BTREE,
  KEY `t_content_idem_hash_IDX` (`idem_hash`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=503 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='资源冗余大表操作记录表，作为备份留存';


-- `ruoyi-vue`.t_pak_path_id_map definition

CREATE TABLE `t_pak_path_id_map` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'pak资源的路径',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_pak_path_id_map_pak_path_IDX` (`path`)
) ENGINE=InnoDB AUTO_INCREMENT=3315 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='pak资源path和guid的映射表';