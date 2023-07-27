package com.ruoyi.app;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.RuoYiApplication;
import com.ruoyi.app.controller.wechat.WechatPayV3ApiController;
import com.ruoyi.app.controller.wechat.dto.WechatPayDTO;
import com.ruoyi.app.controller.wechat.utils.WechatPayV3Utils;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.course.constant.CourConstant;
import com.ruoyi.course.domain.CourCourse;
import com.ruoyi.course.service.ICourCourseService;
import com.ruoyi.gauge.constant.GaugeConstant;
import com.ruoyi.gauge.domain.PsyGauge;
import com.ruoyi.gauge.service.IPsyGaugeService;
import com.ruoyi.psychology.constant.ConsultConstant;
import com.ruoyi.psychology.service.IPsyConsultWorkService;
import com.ruoyi.psychology.service.IPsyUserService;
import com.ruoyi.psychology.vo.PsyConsultWorkVO;
import com.ruoyi.wechat.service.WechatPayV3ApiService;
import com.ruoyi.wechat.vo.WechatPayVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
@Slf4j
public class WxPayTest {

    @Resource
    private IPsyUserService psyUserService;

    @Resource
    private IPsyConsultWorkService workService;

    @Resource
    private WechatPayV3ApiService wechatPayV3ApiService;

    @Resource
    private ICourCourseService iCourCourseService;

    @Resource
    private IPsyGaugeService iPsyGaugeService;

    @Test
    public void payTest() {
        WechatPayDTO dto = new WechatPayDTO();
        dto.setUserId(84);
        dto.setServeId(1673151921080299521L);
        dto.setWorkId(0L);
        dto.setAmount(new BigDecimal("106.00"));
        dto.setModule("consult");
        Integer userId = dto.getUserId(); //用户id
        String out_trade_no = null;

        BigDecimal amount = dto.getAmount(); //单位：元
        String content = "支付demo-课程金"; //先写死一个商品描述

        switch (dto.getModule()) {
            case CourConstant.MODULE_COURSE:
                out_trade_no = createOrderNo("COU_DJ", userId); //创建商户订单号
                CourCourse courCourse = iCourCourseService.selectCourCourseById(dto.getCourseId());
                content = courCourse.getName() + "-" + courCourse.getAuthor();

                break;
            case GaugeConstant.MODULE_GAUGE:
                out_trade_no = createOrderNo("GAU_DJ", userId); //创建商户订单号
                PsyGauge psyGauge = iPsyGaugeService.selectPsyGaugeById(dto.getGaugeId());
                content = psyGauge.getTitle();
                break;
            case ConsultConstant.MODULE_CONSULT:
                out_trade_no = createOrderNo("CON_DJ", userId); //创建商户订单号
                content = "预约心理咨询服务";

                if (dto.getWorkId() > 0) {
                    PsyConsultWorkVO work = workService.getOne(dto.getWorkId());
                    if ("1".equals(work.getStatus())) {
                        System.out.println("排班状态异常");
                    }
//                    if (work.getNum() == 0) {
//                        System.out.println("当前班次已经约满");
//                    }
                }
                break;
        }

        // 将订单、支付单放入事务中
        String attach = "订单号: " + out_trade_no; //先写死一个附加数据 这是可选的 可以用来判断支付内容做支付成功后的处理
        WechatPayVO vo = BeanUtil.toBean(dto, WechatPayVO.class);
        vo.setOutTradeNo(out_trade_no);
        wechatPayV3ApiService.wechatPay(vo);
        System.out.println(attach);
    }

    @Test
    public void notifyTest() {
        String trade = "CON_DJ20230726173236000000847088";
        String order = "1222";
        wechatPayV3ApiService.wechatPayNotify(trade, order);
    }

    private String  createOrderNo(String head, Integer id) {
        StringBuilder uid = new StringBuilder(id.toString());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        int length = uid.length();
        for (int i = 0; i < 8 - length; i++) {
            uid.insert(0, "0");
        }
        return head + sdf.format(date) + uid + (int) ((Math.random() * 9 + 1) * 1000);
    }

}