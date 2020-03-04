package com.ruoyi.project.mr.api;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.mr.base.domain.DataIdiom;
import com.ruoyi.project.mr.base.service.IDataIdiomService;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * swagger 成语接口
 *
 * @author zzp
 */
@Api(tags = "成语接口")
@RestController
@RequestMapping("/mr/base")
public class IdiomController extends BaseController {
    // 令牌秘钥
    @Value("${token.apiSecret}")
    private String apiSecret;

    @Autowired
    private IDataIdiomService dataIdiomService;

    @ApiOperation(value = "成语信息", notes = "根据ID查询成语信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "成语ID", dataType = "int", paramType = "query", example = "1")})
    @GetMapping("/IdiomByID")
    public AjaxResult getIdiomByID(@RequestHeader(value = "signature", required = false) String secret, Long id) {
        if (StringUtils.isEmpty(secret)) {
            return AjaxResult.error(400, MessageUtils.message("api.error.msg"));
        } else if (!apiSecret.equals(secret)) {
            return AjaxResult.error(400, MessageUtils.message("api.error.msg"));
        }

        DataIdiom idiom = dataIdiomService.selectDataIdiomById(id);

        if(idiom!= null){
            return AjaxResult.success(idiom);
        }else{
            return AjaxResult.error(400, MessageUtils.message("api.error.null"));
        }
    }

    @ApiOperation(value = "成语信息", notes = "根据词语查询成语信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "词语", dataType = "String", paramType = "query", example = "双")})
    @GetMapping("/IdiomByName")
    public AjaxResult getIdiomByName(@RequestHeader(value = "signature", required = false) String secret, String name) {
        if (StringUtils.isEmpty(secret)) {
            return AjaxResult.error(400, MessageUtils.message("api.error.msg"));
        } else if (!apiSecret.equals(secret)) {
            return AjaxResult.error(400, MessageUtils.message("api.error.msg"));
        }
        DataIdiom idiom = dataIdiomService.selectDataIdiomByName(name);
        if(idiom!= null){
            return AjaxResult.success(idiom);
        }else{
            return AjaxResult.error(400, MessageUtils.message("api.error.null"));
        }
    }
}
