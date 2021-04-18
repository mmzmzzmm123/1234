package com.ruoyi.web.controller.common;


import com.ruoyi.bookmark.service.ISqMenuService;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.PhoneCode;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bookmarkhtml.Const;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 验证码操作处理
 *
 * @author ruoyi
 */
@RestController
public class RegisterPhoneController extends BaseController
{

    @Autowired
    private ISqMenuService iSqMenuService;

    @Autowired
    private ISysUserService userService;
    /**
     * 发送注册验证码
     */
    @GetMapping("/registerPhoneCode")
    public AjaxResult registerPhoneCode(String phone)
    {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.put("code", HttpStatus.SUCCESS);

        if (StringUtils.isEmpty(phone)){
            ajaxResult.put("msg", "非法手机号，请重新输入!");
            return ajaxResult;
        }
        int phoneCount = userService.countUserByPhone(phone);
        if (phoneCount != 0) {
            ajaxResult.put("msg", "此手机账号已经被使用,请前往登陆!");
            return ajaxResult;
        }

        Long time = 3600L;//限制规定时间内 访问次数
        //防止重复点击
        boolean phoneCodelFlag = iSqMenuService.countRepetition(Const.PHONE_REGISTER,Long.valueOf(phone),time,10); //3600秒 最多请求10次验证码

        if(!phoneCodelFlag){
            ajaxResult.put("msg","短时间内发送频繁,请过几个小时就再操作!");
            return ajaxResult;
        }

        //通过手机号发送短信
        Boolean flag =  PhoneCode.getPhonemsg(phone);

        if (flag){
            ajaxResult.put("msg","短信发送成功,请在"+Constants.CAPTCHA_PHONE_EXPIRATION+"分钟内验证。");
            return ajaxResult;
        }else {
            ajaxResult.put("code", HttpStatus.ERROR);
            ajaxResult.put("msg","系统错误，短信发送失败，请稍后再试!");
            return ajaxResult;
        }
    }

}
