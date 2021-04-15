package com.ruoyi.generator.util;

public interface Constant {

    final String TARGET_PACKAGE    = "targetPackage";

    final String TARGET_PROJECT    = "targetProject";

    final String ROOT_PACKAGE      = "rootPackage";

    final String TARGET_ENTITY     = "targetEntity";

    final String TARGET_MAPPER     = "targetMapper";

    final String TARGET_DAO        = "targetDao";

    final String SUFFIX_DAO        = "suffixDao";

    final String TARGET_MANAGER    = "targetManager";

    final String SUFFIX_MANAGER    = "suffixManager";

    final String TARGET_CONTROLLER = "targetController";

    final String SUFFIX_CONTROLLER = "suffixController";

    final String OVERRIDE          = "override";

    /**
     * 配置配置
     */
    interface Backup {

        final String CODE_SOURCE_PATH = "backupCodeSourcePath";

        final String CODE_TARGET_PATH = "backupCodeTargetPath";

        final String SQL_TARGET_PATH  = "backupSqlTargetPath";

    }

}
