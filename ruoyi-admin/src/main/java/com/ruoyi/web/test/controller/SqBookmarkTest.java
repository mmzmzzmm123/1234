package com.ruoyi.web.test.controller;






import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.web.controller.common.CaptchaController;
import com.ruoyi.web.controller.system.SysLoginController;
import com.ruoyi.web.controller.yunbookmark.SqBookmarkController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;

/**
 * @Auther: Wang
 * @Date: 2020/08/08 18:51
 * 功能描述: SqBookmark 测试类  https://www.it610.com/article/1254347008462852096.htm  断言库教程
 */
public class SqBookmarkTest extends BaseSpringBootTest{
    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private SqBookmarkController sqBookmarkController;
    @Autowired
    private CaptchaController captchaController;

    @Autowired
    private SysLoginController sysLoginController;

    private MockHttpServletRequest request;


    private MockMvc mockMvc;
    private MockMvc loginmockMvc;

    private String url;

    @Autowired
    private RedisCache redisCache;


    private String Token;







    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(captchaController).build();
        loginmockMvc = MockMvcBuilders.standaloneSetup(sysLoginController).build();
    }





    @Test
    public void login2() throws Exception {
        //获取验证码UUID 然后从Redis中取UUID
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("cache-control", "no-cache, no-store, max-age=0, must-revalidate");
//        httpHeaders.add("connection", "close");
//        httpHeaders.add("content-type", "application/json;charset=UTF-8");
//        httpHeaders.add("date", "Tue, 15 Sep 2020 13:33:02 GMT");
//        httpHeaders.add("expires", " 0");
//        httpHeaders.add("pragma", "no-cache");
//        httpHeaders.add("transfer-encoding", "chunked");
//        httpHeaders.add("x-content-type-options", " nosniff");
//        httpHeaders.add("X-Powered-By", "Express");
//        httpHeaders.add("x-xss-protection", " 1; mode=block");


        MvcResult code = this.mockMvc.perform(MockMvcRequestBuilders.get("/captchaImage")
                .accept(MediaType.APPLICATION_JSON) //请求头，Accept代表发送端（客户端）希望接收的数据类型
//                .headers(httpHeaders)  //发送端发送的header信息
                .contentType(MediaType.APPLICATION_JSON)//实体头，Content-Type代表发送端（客户端|服务器）发送的实体数据的数据类型
        )
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                )//添加ResultMatcher验证规则，验证控制器执行完成后结果是否正确
                .andDo(
                        MockMvcResultHandlers.print()//输出整个响应结果信息 //new EncodingResultHandler()//自定义结果处理器
                )
                .andReturn();


        String returntext=code.getResponse().getContentAsString();
        JSONObject jsonObject = JSONUtil.parseObj(returntext);
        String uuid= jsonObject.get("uuid").toString();
        String captcha = redisCache.getCacheObject(Constants.CAPTCHA_CODE_KEY +uuid);

        logger.debug("验证码接口UUID:"+uuid);
        logger.debug("验证码接口缓存中的验证码结果:"+captcha);



        LoginBody request = new LoginBody();
        request.setUsername("admin");
        request.setPassword("admin123");
        request.setCode(captcha);
        request.setUuid(uuid);
        MvcResult mvcResult = loginmockMvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONUtil.parseObj(request, false).toString())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        logger.debug("登录接口返回:"+mvcResult.getResponse().getContentAsString());

        logger.debug("TOKEN:"+JSONUtil.parseObj(mvcResult.getResponse().getContentAsString()).get("token"));
    }





    @Test
    public void testGetUser() throws Exception {
//        //获取验证码UUID 然后从Redis中取UUID
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("cache-control", "no-cache, no-store, max-age=0, must-revalidate");
//        httpHeaders.add("connection", "close");
//        httpHeaders.add("content-type", "application/json;charset=UTF-8");
//        httpHeaders.add("date", "Tue, 15 Sep 2020 13:33:02 GMT");
//        httpHeaders.add("expires", " 0");
//        httpHeaders.add(AUTHORIZATION, "no-cache");
//        httpHeaders.add("transfer-encoding", "chunked");
//        httpHeaders.add("x-content-type-options", " nosniff");
//        httpHeaders.add("X-Powered-By", "Express");
//        httpHeaders.add("x-xss-protection", " 1; mode=block");
//
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/bookmark/bookmark/list")
//                .accept(MediaType.APPLICATION_JSON).param("pageNum", "2").param("pageSize","10"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//
//        logger.info(mvcResult.getResponse().getContentAsString());
        logger.info("成功");
    }


    /**
     * 获取登入信息session
     * @return
     * @throws Exception
     */



//单元测试 教程
//    https://www.cnblogs.com/shunyang/p/8681111.html






}
