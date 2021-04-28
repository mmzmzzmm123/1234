package com.stdiet.custom.domain.entityEnum;

public enum MessageNoticeEnum{

    systemMessage("系统通知", 0, 0, "系统通知"),
    punchComment("打卡点评", 1, 1, "%s打卡点评"); //%s 为打卡时间


    //消息名称
    private String name;

    //消息属性 0公共 1私有
    private Integer property;

    //消息类型
    private Integer type;

    //消息标题模板
    private String titleTemplate;

    MessageNoticeEnum(String name, Integer property, Integer type, String titleTemplate){
        this.name = name;
        this.property = property;
        this.type = type;
        this.titleTemplate = titleTemplate;
    }

    /**
     * 根据type类型获取枚举对象
     * @param type
     * @return
     */
    public static MessageNoticeEnum getNoticeEnumByType(Integer type){
        for (MessageNoticeEnum messageEnum : MessageNoticeEnum.values()) {
            if(messageEnum.getType().intValue() == type.intValue()){
                return messageEnum;
            }
        }
        return systemMessage;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getProperty() {
        return property;
    }

    public void setProperty(Integer property) {
        this.property = property;
    }

    public String getTitleTemplate() {
        return titleTemplate;
    }

    public void setTitleTemplate(String titleTemplate) {
        this.titleTemplate = titleTemplate;
    }

}
