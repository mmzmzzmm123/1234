package com.ruoyi.web.test.controller;


import com.ruoyi.web.controller.yunbookmark.SqBookmarkController;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @Auther: Wang
 * @Date: 2020/08/08 18:51
 * 功能描述: SqBookmark 测试类
 */
public class SqBookmarkTest extends BaseSpringBootTest{


    @Autowired
    private SqBookmarkController sqBookmarkController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(sqBookmarkController).build();
        logger.info("setup().........");
    }
    @Test
    public void demo() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/bookmark/bookmark/2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

}
