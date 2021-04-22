package com.ruoyi.project.system.controller;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.project.common.SchoolCommon;
import com.ruoyi.project.system.domain.ByTeacherJbxx;
import com.ruoyi.project.system.domain.SysDept;
import com.ruoyi.project.system.service.*;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.security.service.TokenService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.domain.SysUser;

/**
 * 用户信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {

    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.secret}")
    private String secret;

    @Value("${wx.domain}")
    private String domain;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private IByTeacherJbxxService byTeacherJbxxService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SchoolCommon schoolCommon;

    @Autowired
    private ISysDeptService deptService;


    /**
     * 获取用户列表
     */
    //@PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUser user) {
        startPage();
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    @GetMapping("/lrrlist")
    public TableDataInfo lrrlist(SysUser user) {
        int l1 = 103;
        user.setDeptId(Long.valueOf(l1));
        List<SysUser> list = userService.selectUserList(user);

        int l2 = 105;
        user.setDeptId(Long.valueOf(l2));
        list.addAll(userService.selectUserList(user));

        int l3 = 107;
        user.setDeptId(Long.valueOf(l3));
        list.addAll(userService.selectUserList(user));

        return getDataTable(list);
    }

    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @GetMapping("/export")
    public AjaxResult export(SysUser user) {
        List<SysUser> list = userService.selectUserList(user);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.exportExcel(list, "用户数据");
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = userService.importUser(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.importTemplateExcel("用户数据");
    }

    /**
     * 根据用户编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = {"/", "/{userId}"})
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        AjaxResult ajax = AjaxResult.success();

        SysDept dept = new SysDept();
        dept.setParentId((long) 200);

        //在添加用户时判断是否为幼儿园平台
        if (schoolCommon.isSchool() == true) {
            //只显示幼儿园相关的岗位和角色
            ajax.put("roles", roleService.selectYeyRoleAll());
            ajax.put("posts", postService.selectYeyPostAll());
            ajax.put("isSchool", "1");
        } else {
            ajax.put("roles", roleService.selectRoleAll());
            ajax.put("posts", postService.selectPostAll());
            ajax.put("isSchool", "0");
            ajax.put("kindergartens", deptService.selectDeptList(dept));
        }

        //在修改用户时判断是否为幼儿园平台
        if (StringUtils.isNotNull(userId)) {
            if (schoolCommon.isSchool() == true) {
                ajax.put(AjaxResult.DATA_TAG, userService.selectUserById(userId));
                ajax.put("postIds", postService.selectYeyPostListByUserId(userId));
                ajax.put("roleIds", roleService.selectYeyRoleListByUserId(userId));
                ajax.put("isSchool", "1");
            } else {
                ajax.put(AjaxResult.DATA_TAG, userService.selectUserById(userId));
                ajax.put("postIds", postService.selectPostListByUserId(userId));
                ajax.put("roleIds", roleService.selectRoleListByUserId(userId));
                ajax.put("isSchool", "0");
                ajax.put("kindergartenIds", deptService.selectDeptListByUserId(userId));
            }
        }
        return ajax;
    }

    /**
     * 根据岗位id获取用户信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping("/listbyroleid")
    public AjaxResult getUserList() {
        AjaxResult ajax = AjaxResult.success();
        Long deptId = SecurityUtils.getLoginUser().getUser().getDept().getDeptId();
        SysUser user = new SysUser();
        //主班教师
        user.setUserId((long) 102);
        user.setDeptId(deptId);
        ajax.put("zbjs", userService.selectUserListByRoleId(user));
        //配班教师
        user = new SysUser();
        user.setUserId((long) 104);
        user.setDeptId(deptId);
        ajax.put("pbjs", userService.selectUserListByRoleId(user));
        //助理教师
        user = new SysUser();
        user.setUserId((long) 105);
        user.setDeptId(deptId);
        ajax.put("zljs", userService.selectUserListByRoleId(user));

        return ajax;
    }

    /**
     * 根据roleId获取用户信息列表
     */
    //@PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = {"/onlybyroleid/{roleId}"})
    public AjaxResult getUserOnlyByRoleId(@PathVariable Long roleId) {
        AjaxResult ajax = AjaxResult.success();
        List<SysUser> list = userService.selectUserOnlyByRoleId(roleId);
        ajax.put(AjaxResult.DATA_TAG, list);
        return ajax;
    }

    /**
     * 新增用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysUser user) {
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName()))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }

        //判断当前学校有多少个幼儿园管理员 需求至多3个幼儿园管理员
        Long[] roleIds = user.getRoleIds();
        int iCount = 0;
        if (roleIds.length > 0) {
            System.out.println(roleIds.length);
            for (int i = 0; i < roleIds.length; i++) {
                if (roleIds[i] == 100) {
                    iCount = userService.countUserSchoolAdminRoleByDeptId(user.getDeptId(), roleIds[i]);
                    if (iCount >= 2) {
                        return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，当前学校管理员角色最多设置2人");
                    }
                }
            }
        }


        user.setPhonenumber(user.getUserName());
        user.setEmail(user.getUserName() + "@benyi.com");
        user.setCreateBy(SecurityUtils.getUsername());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        int bl = userService.insertUser(user);
        if (bl > 0) {
            ByTeacherJbxx byTeacherJbxx = new ByTeacherJbxx();
            //并赋值给教师userid
            //user可以直接获取出入数据的主键值
            byTeacherJbxx.setUserid(user.getUserId());
            byTeacherJbxx.setCreatetime(new Date());
            //插入数据到教师表
            byTeacherJbxxService.insertByTeacherJbxx(byTeacherJbxx);
        }
        return toAjax(bl);

    }

    /**
     * 修改用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }

        //先判断前端的roleids是否有变化
        boolean isPd = true;
        if (schoolCommon.isSchool() == true) {
            List<Integer> roleIdsOld = roleService.selectYeyRoleListByUserId(user.getUserId());
            if (roleIdsOld != null && roleIdsOld.size() > 0) {
                for (int i = 0; i < roleIdsOld.size(); i++) {
                    if (roleIdsOld.get(i) == 100) {
                        isPd = false;
                    }
                }
            }
        }
        if (isPd) {
            //判断当前学校有多少个幼儿园管理员 需求至多3个幼儿园管理员
            Long[] roleIds = user.getRoleIds();
            int iCount = 0;
            if (roleIds.length > 0) {
                System.out.println(roleIds.length);
                for (int i = 0; i < roleIds.length; i++) {
                    if (roleIds[i] == 100) {
                        iCount = userService.countUserSchoolAdminRoleByDeptId(user.getDeptId(), roleIds[i]);
                        if (iCount >= 2) {
                            return AjaxResult.error("用户'" + user.getUserName() + "'修改失败，当前学校管理员角色最多设置2人");
                        }
                    }
                }
            }
        }

        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:remove')")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds) {
        return toAjax(userService.deleteUserByIds(userIds));
    }

    /**
     * 重置密码
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setPw(user.getPassword());
        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(userService.resetPwd(user));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(userService.updateUserStatus(user));
    }

    @Log(title = "用户管理-绑定微信", businessType = BusinessType.UPDATE)
    @PostMapping("/bindwx/{code}")
    public AjaxResult bindWx(@PathVariable String code) {

        String url = domain;
        String parmas = "appid=" + appid + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";
        System.out.println(parmas);
        String strResult = HttpUtils.sendGet(url, parmas);
        JSONObject jsonObject = JSONObject.parseObject(strResult);
        try {
            String openId = jsonObject.get("openid").toString();
            System.out.println(openId);
            //首先判断当前用户是否绑定微信
            SysUser sysUser = userService.selectUserById(SecurityUtils.getLoginUser().getUser().getUserId());
            if (schoolCommon.isStringEmpty(sysUser.getOpenId())) {
                //其次查询当前微信是否被绑定
                SysUser sysUserNew = new SysUser();
                sysUserNew.setOpenId(openId);
                List<SysUser> list = userService.selectUserList(sysUserNew);
                if (list != null && list.size() > 0) {
                    return AjaxResult.error("当前微信已绑定其他账号");
                } else {
                    //绑定
                    sysUser.setOpenId(openId);
                    int iCount = userService.updateUserWx(sysUser);
                    return AjaxResult.success(iCount);
                }
            } else {
                return AjaxResult.error("当前账户已绑定微信");
            }
        } catch (Exception e) {
            return AjaxResult.error("获取信息失败");
        }

    }

    @GetMapping("/isbindwx")
    public AjaxResult isbindWx() {
        //首先判断当前用户是否绑定微信
        SysUser sysUser = userService.selectUserById(SecurityUtils.getLoginUser().getUser().getUserId());
        if (schoolCommon.isStringEmpty(sysUser.getOpenId())) {
            return AjaxResult.success("1");
        } else {
            return AjaxResult.success("0");
        }
    }

}