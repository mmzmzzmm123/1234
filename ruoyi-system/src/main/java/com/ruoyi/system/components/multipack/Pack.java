package com.ruoyi.system.components.multipack;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Pack {

	private final String opt;

	private final String data;

	private final String dataClass;

	private String radioId;

	private long createTime = System.currentTimeMillis();

}
