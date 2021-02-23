package com.stdiet.custom.domain;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysRecipesDailyDishes {
    private Long id;

    private Long menuId;

    private String name;

    private Long dishesId;

    private JSONArray detail;

    private String methods;

    private List<SysDishesIngredient> igdList;

    private String type;

    private Integer isMain;
}
