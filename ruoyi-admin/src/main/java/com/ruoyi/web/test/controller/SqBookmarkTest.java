package com.ruoyi.web.test.controller;


import com.github.wujun234.uid.UidGenerator;
import com.ruoyi.bookmark.mapper.SqMenuMapper;
import com.ruoyi.bookmark.service.ISqUserTagService;
import com.ruoyi.web.controller.yunbookmark.SqBookmarkController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Wang
 * @Date: 2020/08/08 18:51
 * 功能描述: SqBookmark 测试类
 */
public class SqBookmarkTest extends BaseSpringBootTest{


    @Autowired
    private SqBookmarkController sqBookmarkController;
    @Autowired
    private SqMenuMapper sqMenuMapper;
    @Autowired
    private ISqUserTagService iSqUserTagService;





    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(sqBookmarkController).build();
        logger.info("setup().........");
    }
//    @Test
//    public void demo() throws Exception {
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/bookmark/bookmark/2"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//
//        logger.info(mvcResult.getResponse().getContentAsString());
//
//    }
//    @Test
//    public void demo() throws Exception {
//        Long[] menuIds={1L,4L,6L};
//        sqMenuMapper.updateCountAdd(menuIds,5);
//
//        logger.info("执行完毕");
//
//    }
    /**
     * @auther: Wang
     * @date: 2020/09/04 23:58
     * 功能描述: 测试查询用户的 标签
     */

    @Test
    public void selectSqUserTagByUserId() throws Exception {
      List<Map<String,Object>> map =  iSqUserTagService.selectSqUserTagByUserId(1L);
        logger.info("执行完毕");
        for (Map<String, Object> list: map) {
            for (Map.Entry<String,Object> entry: list.entrySet() ) {
                System.out.println(entry.getKey()+"="+entry.getValue());
            }
            logger.info("========================================");
        }


    }






}
