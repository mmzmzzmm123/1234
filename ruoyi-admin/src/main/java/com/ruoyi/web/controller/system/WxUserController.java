package com.ruoyi.web.controller.system;

import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.entity.WxUser;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.model.WxLoginBody;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.*;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.framework.web.service.WxUserService;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysPostService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 微信注册用户信息
 *
 * @author ruoyi
 */
@Api("微信用户管理")
@RestController
@RequestMapping("/wx/user")
public class WxUserController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private TokenService tokenService;


    @Autowired
    private WxUserService wxUserService;


    /**
     * 新增用户
     */
    @Log(title = "微信用户管理", businessType = BusinessType.INSERT)
    @ApiOperation("用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名称", dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(name = "nickName", value = "用户昵称", dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(name = "phonenumber", value = "用户手机", dataType = "String", dataTypeClass = String.class)
    })
    @PostMapping("/register")
    public AjaxResult add(@Validated @RequestBody SysUser user)
    {
        if (!userService.checkUserNameUnique(user))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        user.setUserType("01");
        return toAjax(userService.insertUser(user));
    }



    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    @ApiOperation("微信用户登录")
    public AjaxResult login(@RequestBody WxLoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = wxUserService.login(loginBody.getUsername(), loginBody.getPassword());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }


    /**
     * 获取用户列表
     */
    @PreAuthorize("@ss.hasPermi('system:wxuser:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUser user)
    {
        PageUtils.startPage();
        user.setUserType("01");
        List<SysUser> list = userService.selectUserList(user);
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    @PreAuthorize("@ss.hasPermi('system:wxuser:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUser user)
    {
        user.setUserType("01");
        List<SysUser> list = userService.selectUserList(user);
        List<WxUser> wxUserList = new ArrayList<>();
        for (SysUser sysUser : list) {
            WxUser wxUser = new WxUser();
            wxUser.setUserId(sysUser.getUserId());
            wxUser.setUserName(sysUser.getUserName());
            wxUser.setUserType(sysUser.getUserType());
            wxUser.setPhonenumber(sysUser.getPhonenumber());
            wxUser.setNickName(sysUser.getNickName());
            wxUserList.add(wxUser);
        }
        ExcelUtil<WxUser> util = new ExcelUtil<>(WxUser.class);
        util.exportExcel(response, wxUserList, "微信用户数据");
    }


    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows)
    {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

}
