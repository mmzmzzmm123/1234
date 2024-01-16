package com.ruoyi.system.components.multipack;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PckRadio {
	private final String radioId;
	private final List<String> pckOpt;

	private int current = 0;

	public boolean isFull() {
		if (getCurrent() >= getPckOpt().size()) {
			return true;
		}
		return false;
	}
}
