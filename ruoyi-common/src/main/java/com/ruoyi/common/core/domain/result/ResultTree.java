package com.ruoyi.common.core.domain.result;


import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.Result;
import com.ruoyi.common.core.domain.TreeSelect;

import java.util.List;

/**
 * @author shoreu
 */
public class ResultTree extends Result {

    private List<Long> checkedKeys;
    private List<TreeSelect> depts;
    private List<TreeSelect> menus;

    public ResultTree() {
        super(HttpStatus.SUCCESS);
    }

    public static ResultTree successDepts(List<Long> checkedKeys, List<TreeSelect> depts) {
        ResultTree dept = new ResultTree();
        dept.setCheckedKeys(checkedKeys);
        dept.setDepts(depts);
        return dept;
    }

    public static ResultTree successMenus(List<Long> checkedKeys, List<TreeSelect> menus) {
        ResultTree menu = new ResultTree();
        menu.setCheckedKeys(checkedKeys);
        menu.setMenus(menus);
        return menu;
    }

    public List<Long> getCheckedKeys() {
        return this.checkedKeys;
    }

    public void setCheckedKeys(List<Long> checkedKeys) {
        this.checkedKeys = checkedKeys;
    }

    public List<TreeSelect> getDepts() {
        return this.depts;
    }

    public void setDepts(List<TreeSelect> depts) {
        this.depts = depts;
    }

    public List<TreeSelect> getMenus() {
        return this.menus;
    }

    public void setMenus(List<TreeSelect> menus) {
        this.menus = menus;
    }

}
