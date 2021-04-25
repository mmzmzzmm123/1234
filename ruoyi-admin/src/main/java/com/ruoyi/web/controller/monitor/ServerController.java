package com.ruoyi.web.controller.monitor;

import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.framework.web.domain.Server;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 服务器监控
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/monitor/server")
public class ServerController
{
    @PreAuthorize("@ss.hasPermi('monitor:server:list')")
    @GetMapping()
    public AjaxResult getInfo() throws Exception
    {
        Server server = new Server();
        server.copyTo();
        return AjaxResult.success(server);
    }


    /**
     * AbstractController 使用示例，传递参数可以用parseRequestParamsForm来代替
     * @param params
     * @param request
     * @param headers
     * @return
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody(required = true) Map<String, Object> params, HttpServletRequest request, @RequestHeader HttpHeaders headers) {
        Server userLoginForm = parseRequestParamsForm(params, Server.class);
        return AjaxResult.success(userLoginForm);
    }
}
