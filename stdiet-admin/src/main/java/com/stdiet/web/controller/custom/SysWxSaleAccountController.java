package com.stdiet.web.controller.custom;

import com.stdiet.common.annotation.Log;
import com.stdiet.common.config.RuoYiConfig;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.core.page.TableDataInfo;
import com.stdiet.common.core.redis.RedisCache;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.file.FileUploadUtils;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.custom.domain.SysWxSaleAccount;
import com.stdiet.custom.domain.wechat.WxAccessToken;
import com.stdiet.custom.domain.wechat.WxFileUploadResult;
import com.stdiet.custom.service.ISysWxSaleAccountService;
import com.stdiet.custom.utils.WxTokenUtils;
import com.stdiet.framework.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 微信销售账号Controller
 *
 * @author wonder
 * @date 2021-01-29
 */
@RestController
@RequestMapping("/custom/WxAccount")
public class SysWxSaleAccountController extends BaseController {
    @Autowired
    private ISysWxSaleAccountService sysWxSaleAccountService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ServerConfig serverConfig;

    /**
     * 查询微信销售账号列表
     */
    @PreAuthorize("@ss.hasPermi('custom:WxAccount:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysWxSaleAccount sysWxSaleAccount) {
        startPage();
        List<SysWxSaleAccount> list = sysWxSaleAccountService.selectSysWxSaleAccountList(sysWxSaleAccount);
        return getDataTable(list);
    }

    /**
     * 导出微信销售账号列表
     */
    @PreAuthorize("@ss.hasPermi('custom:WxAccount:export')")
    @Log(title = "微信销售账号", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysWxSaleAccount sysWxSaleAccount) {
        List<SysWxSaleAccount> list = sysWxSaleAccountService.selectSysWxSaleAccountList(sysWxSaleAccount);
        ExcelUtil<SysWxSaleAccount> util = new ExcelUtil<SysWxSaleAccount>(SysWxSaleAccount.class);
        return util.exportExcel(list, "WxAccount");
    }

    /**
     * 获取微信销售账号详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:WxAccount:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysWxSaleAccountService.selectSysWxSaleAccountById(id));
    }

    /**
     * 新增微信销售账号
     */
    @PreAuthorize("@ss.hasPermi('custom:WxAccount:add')")
    @Log(title = "微信销售账号", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysWxSaleAccount sysWxSaleAccount) {
        return toAjax(sysWxSaleAccountService.insertSysWxSaleAccount(sysWxSaleAccount));
    }

    /**
     * 修改微信销售账号
     */
    @PreAuthorize("@ss.hasPermi('custom:WxAccount:edit')")
    @Log(title = "微信销售账号", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysWxSaleAccount sysWxSaleAccount) {
        return toAjax(sysWxSaleAccountService.updateSysWxSaleAccount(sysWxSaleAccount));
    }

    /**
     * 删除微信销售账号
     */
    @PreAuthorize("@ss.hasPermi('custom:WxAccount:remove')")
    @Log(title = "微信销售账号", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sysWxSaleAccountService.deleteSysWxSaleAccountByIds(ids));
    }

    @GetMapping("/redisTest")
    public AjaxResult redisTest() {
        String accessToken = redisCache.getCacheObject(WxTokenUtils.KEY_ACCESS_TOKEN);
        if (StringUtils.isEmpty(accessToken)) {
            WxAccessToken wxAccessToken = WxTokenUtils.fetchAccessToken();
            redisCache.setCacheObject(WxTokenUtils.KEY_ACCESS_TOKEN, wxAccessToken.getAccessToken(), wxAccessToken.getExpiresIn(), TimeUnit.SECONDS);
        }
        return AjaxResult.success(accessToken);
    }

    /**
     * 上传图片
     */
    @PostMapping("/upload")
    public AjaxResult wxAccountUpload(MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            String oriFileName = file.getOriginalFilename();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;

            String oriFilePath = filePath + fileName.substring(fileName.indexOf("upload") + 6);

            String accessToken = redisCache.getCacheObject(WxTokenUtils.KEY_ACCESS_TOKEN);
            if (StringUtils.isEmpty(accessToken)) {
                WxAccessToken wxAccessToken = WxTokenUtils.fetchAccessToken();
                redisCache.setCacheObject(WxTokenUtils.KEY_ACCESS_TOKEN, wxAccessToken.getAccessToken(), wxAccessToken.getExpiresIn(), TimeUnit.SECONDS);
            }

            WxFileUploadResult result = WxTokenUtils.uploadImage(oriFilePath, oriFileName, accessToken);

            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("mediaId", result.getMediaId());
            ajax.put("url", url);
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

}