package com.ruoyi.system.extend.data;

import lombok.Data;

import java.util.List;


@Data
public class PostBotMsg {

    private int materialMediaType;

    private String mediaUrl;

    private String title;

    private List<TextLinkList> textLinkList;

}