package com.ruoyi.web.test.controller;

import com.ruoyi.bookmark.domain.SqBookmark;
import com.ruoyi.bookmark.domain.SqMenu;
import com.ruoyi.bookmark.mapper.SqBookmarkMapper;
import com.ruoyi.bookmark.mapper.SqMenuMapper;
import org.apache.velocity.runtime.directive.Foreach;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Auther: Wang
 * @Date: 2021/02/14 20:41
 * 功能描述:
 */
public class SqMenuTest extends BaseSpringBootTest{

    @Autowired
    SqMenuMapper sqMenuMapper;
    @Autowired
    SqBookmarkMapper sqBookmarkMapper;
    @Test
    public void addMenuUplinkSeries() {


      List<com.ruoyi.bookmark.domain.SqMenu> sqMenus =  sqMenuMapper.selectbymenuUplinkSeries();
      if (null!=sqMenus&&!sqMenus.isEmpty()) {
          for (com.ruoyi.bookmark.domain.SqMenu s:sqMenus) {
              StringBuilder menuus = new StringBuilder();
              com.ruoyi.bookmark.domain.SqMenu sqMenu = sqMenuMapper.selectSqMenuById(s.getMenuId());
              Long parentId = sqMenu.getParentId();
              //所有的上级目录ID
              List<Long> list = new ArrayList<>();
              list.add(s.getMenuId());
              while (parentId.intValue() != 0) {
                  sqMenu = sqMenuMapper.selectSqMenuById(parentId);
                  list.add(sqMenu.getMenuId());
                  parentId = sqMenu.getParentId();
              }
              //倒序
              for (int i = list.size(); i > 0; i--) {
                  menuus.append(",").append(list.get(i - 1));
              }
              menuus.append(",");
              System.out.println(menuus.toString());
              sqMenuMapper.updateSqMenu(new com.ruoyi.bookmark.domain.SqMenu(s.getMenuId(),menuus.toString()));
          }
          }
    }

    /**
     * 批量计算新的目录下书签数量
     *
     * @param
     * @return
     */
    @Test
    public void test2() {
        SqMenu sqMenu2 = new SqMenu();
        sqMenu2.setUserId(1L);
        List<SqMenu> sqs = sqMenuMapper.select(sqMenu2);
        for (SqMenu s : sqs) {
            int count = sqBookmarkMapper.countBookMakeByMenuId(s.getMenuId());
            SqMenu sqMenu = new SqMenu();
            sqMenu.setMenuId(s.getMenuId());
            sqMenu.setBookmarkCount(count);
            sqMenuMapper.updateSqMenu(sqMenu);
        }

    }



}
