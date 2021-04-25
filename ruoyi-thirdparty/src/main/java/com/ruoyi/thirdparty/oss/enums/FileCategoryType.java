package com.ruoyi.thirdparty.oss.enums;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public enum FileCategoryType {
    IMAGE("图片", "image"),
    AUDIO("音频", "audio"),
    VIDEO("视频", "video"),

    INVALID("无效类型", "invalid"),
    ;

    private static final List<String> imageSupportFileFormat = new ImmutableList.Builder<String>()
            .addAll(Arrays.asList(".jpg", ".png", ".bmp", ".jpeg", ".bmp")).build();
    private static final List<String> audioSupportFileFormat = new ImmutableList.Builder<String>()
            .addAll(Arrays.asList("", "", "")).build();
    private static final List<String> videoSupportFileFormat = new ImmutableList.Builder<String>()
            .addAll(Arrays.asList(".mp4", ".ogg", ".flv", ".avi", ".wmv", ".rmvb",".3gp", ".mov")).build();

    private String description;
    private String categoryType;

    public static FileCategoryType of(String category) {
        for (FileCategoryType _enum :values()) {
            if (StringUtils.equalsIgnoreCase(_enum.getCategoryType(), category)) {
                return _enum;
            }
        }
        return INVALID;
    }

    public static boolean isValidType(FileCategoryType type) {
        if (INVALID.equals(type)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidFormat(FileCategoryType type, String fileFormat) {
        switch (type) {
            case IMAGE:
                return imageSupportFileFormat.contains(fileFormat.toLowerCase());
            case AUDIO:
                return audioSupportFileFormat.contains(fileFormat.toLowerCase());
            case VIDEO:
                return videoSupportFileFormat.contains(fileFormat.toLowerCase());

        }

        return false;
    }

    FileCategoryType(String description, String categoryType) {
        this.description = description;
        this.categoryType = categoryType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

}
