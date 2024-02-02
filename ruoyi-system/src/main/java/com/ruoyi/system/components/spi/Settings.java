package com.ruoyi.system.components.spi;

import java.util.Map;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPackLog;

public interface Settings {
	public static final String Key_PlayId = "Key_PlayId";
	public static final String Key_RobotId = "Key_RobotId";
	public static final String Key_GroupId = "Key_GroupId";
	public static final String Key_Names = "Key_Group_Names";
	public static final String Key_Img = "Key_Group_Img";
	public static final String Key_Names1 = "Key_Group_Names1";
	public static final String Key_Names2 = "Key_Group_Names2";
	public static final String Key_Admin_Flag = "Key_Admin_Flag";
	public static final String Key_Backup_Flag = "Key_Backup_Flag";
	public static final String Key_AttachContent = "Key_AttachContent";
	public static final String Key_OldOpt = "Key_OldOpt";

	PlayRobotPackLog set(Map<String, Object> param);
}
