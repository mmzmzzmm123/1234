CREATE TABLE t_file
(
    id                 VARCHAR(36) NOT NULL PRIMARY KEY COMMENT '文件Id',
    tenant_id          VARCHAR(36) COMMENT '租户id',
    file_name          TEXT COMMENT '文件名称',
    fingerprint        TEXT COMMENT '文件指纹',
    file_size          DECIMAL(10)  DEFAULT 0 COMMENT '文件大小(字节)',
    mime_type          TEXT COMMENT 'MIME文件类型',
    extension          TEXT COMMENT '文件扩展名',
    path               TEXT COMMENT '文件路径',
    app_name           TEXT COMMENT '应用名称',
    create_user_id     VARCHAR(36) COMMENT '创建人id',
    create_time        TIMESTAMP(6)  COMMENT '创建人时间',
    update_user_id     VARCHAR(36) COMMENT '更新人',
    update_time        TIMESTAMP(6) COMMENT '更新时间',
    remark             TEXT COMMENT '备注',
    creator_ip_address TEXT COMMENT '创建人ip',
    host_name          TEXT COMMENT '主机名称',
    del_flag           DECIMAL(1)   DEFAULT 0 COMMENT '删除标识；1-删除, 0-可用',
    disk_path          TEXT COMMENT '磁盘存储路径',
    detect_mime        TEXT COMMENT '检测的MIME文件类型'
) COMMENT = '文件信息表';

CREATE TABLE t_file_relation (
                                 id VARCHAR(36) NOT NULL PRIMARY KEY COMMENT '关联信息ID',
                                 tenant_id VARCHAR(36) COMMENT '租户ID',
                                 file_id VARCHAR(255) COMMENT '文件ID',
                                 relation_type VARCHAR(50) COMMENT '关联类型',
                                 relation_target VARCHAR(255) COMMENT '关联类型业务ID',
                                 create_time TIMESTAMP(6) COMMENT '创建时间',
                                 update_time TIMESTAMP(6) COMMENT '更新时间',
                                 del_flag INT DEFAULT 0 COMMENT '删除标识：1-删除, 0-正常可用',
                                 update_user_id VARCHAR(36) COMMENT '更新人ID',
                                 create_user_id VARCHAR(36) COMMENT '创建人ID',
                                 create_user_name VARCHAR(255) COMMENT '创建用户姓名',
                                 update_user_name VARCHAR(255) COMMENT '更新用户姓名'
) COMMENT = '文件关联信息表';

CREATE INDEX idx_file_relation_02 ON t_file_relation (file_id);

CREATE INDEX idx_file_relation_01 ON t_file_relation (tenant_id, relation_type, relation_target);

-- 为表更改所有者在MySQL中通常不是一个常见的操作，通常在数据库级别处理用户权限
-- 在MySQL中，你可以使用GRANT命令来授予特定用户对表的权限，例如：
-- GRANT ALL PRIVILEGES ON t_file_relation TO 'iops'@'your_host';
