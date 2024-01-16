package com.ruoyi.system.components.spi;

import java.util.Map;

import com.ruoyi.common.core.domain.R;

public interface Settings {

	public static final String Key_Id = "ID";

	public static final String Key_Names = "Key_Group_Names";
	public static final String Key_Img = "Key_Group_Img";

	public static final String Key_Names1 = "Key_Group_Names1";
	public static final String Key_Names2 = "Key_Group_Names2";

	public static final String Key_Admin_Flag = "Key_Admin_Flag";

	R<String> set(Map<String, Object> param);
}
