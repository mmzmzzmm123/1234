package vip.carpool.api.gateway.vo;

import javax.validation.constraints.NotBlank;

public class WxMsg {

	@NotBlank(message = "消息参数必填")
    private String name;

	@NotBlank(message = "消息参数值必填")
    private String value;

	@NotBlank(message = "参数值颜色必填")
    private String color;

    public WxMsg() {

    }

    public WxMsg(String name , String value, String color) {
   	    this.name = name;
   	    this.value = value;
   	    this.color = color;
   }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
