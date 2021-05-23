package com.ruoyi.web.test.controller;

import com.ruoyi.bookmark.domain.SqBookmark;
import com.ruoyi.bookmark.domain.SqMenu;
import com.ruoyi.bookmark.mapper.SqBookmarkMapper;
import com.ruoyi.bookmark.mapper.SqMenuMapper;
import com.ruoyi.bookmark.service.ISqBookmarkService;
import com.ruoyi.bookmark.service.ISqMenuService;
import com.ruoyi.bookmark.service.impl.SqMenuServiceImpl;
import com.ruoyi.common.utils.bookmarkhtml.Const;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.velocity.runtime.directive.Foreach;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.omg.PortableServer.THREAD_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
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
    private ISqMenuService iSqMenuService;
    @Autowired
    private ISqBookmarkService iSqBookmarkService;
    @Autowired
    SqBookmarkMapper sqBookmarkMapper;
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;










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

    @Test
    public void test67(){
        iSqBookmarkService.bookmarkDeleteRepetition(1L);
    }


    /**
     * 初始化是否有下级目录 subordinate 0无下级 1有下级
     * 批量计算新的目录下书签数量
     *
     * @param
     * @return
     */

    @Test
    public void test3() {
        iSqBookmarkService.bookmarkMenuCount(1L,442L);

    }
    /**
     * 初始化是否有下级目录 subordinate 0无下级 1有下级
     *
     * @param
     * @return
     */
    @Test
    public void test7() {
        SqMenu sqMenu2 = new SqMenu();
        sqMenu2.setUserId(1L);
        List<SqMenu> sqs = sqMenuMapper.select(sqMenu2);
        for (SqMenu s : sqs) {
            SqMenu sq = new SqMenu();
            sq.setParentId(s.getMenuId());
            List<SqMenu> list = sqMenuMapper.select(sq);
            if (list!=null&&!list.isEmpty())
            {
                SqMenu sqMenu = new SqMenu();
                sqMenu.setMenuId(s.getMenuId());
                sqMenu.setSubordinate(1);
                sqMenuMapper.updateSqMenu(sqMenu);
            }else{
                SqMenu sqMenu = new SqMenu();
                sqMenu.setMenuId(s.getMenuId());
                sqMenu.setSubordinate(0);
                sqMenuMapper.updateSqMenu(sqMenu);
            }

        }

    }





    //测试mybatis的事务
    @Test
    public void test4() throws Exception {
        SqMenu sqMenu1 = new SqMenu();
        sqMenu1.setUserId(2L);
        sqMenu1.setMenuName("AAAAAAAAA");
        int count =  iSqMenuService.insertSqMenu2(sqMenu1);
        }

    //批量插入
    @Test
    public void testInsertBatch2() throws Exception {
        long start = System.currentTimeMillis();
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);//跟上述sql区别
        SqMenuMapper mapper = sqlSession.getMapper(SqMenuMapper.class);
        for (int i = 0; i < 10; i++) {
            SqMenu sqMenu1 = new SqMenu();
            sqMenu1.setUserId(2L);
            sqMenu1.setMenuName("AAAAAAAAA"+i);
            mapper.insertSqMenu(sqMenu1);
        }
        sqlSession.commit();
        long end = System.currentTimeMillis();
        System.out.println("---------------" + (start - end) + "---------------");
    }



}
