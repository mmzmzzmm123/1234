package com.boot.web.controller.system;

import com.boot.common.config.BootConfig;
import com.boot.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 首页
 *
 * @author boot
 */
@Controller
public class SysIndexController
{
    /** 系统基础配置 */
    @Autowired
    private BootConfig bootConfig;
	@Autowired
	private ISysConfigService configService;
	@RequestMapping("/")
	public void index(HttpServletResponse response) throws IOException {
		response.sendRedirect(configService.selectConfigByKey("sys.dns")+"/demo/chatgpt/chat");
//		return "/demo/chatgpt/chat";
	}
	@RequestMapping("/index")
	public void index2(HttpServletResponse response) throws IOException {
		response.sendRedirect(configService.selectConfigByKey("sys.dns")+"/demo/chatgpt/chat");
//		return "/demo/chatgpt/chat";
	}
    @RequestMapping("/demo/chatgpt/chat")
    public String chat() throws IOException {
		return "/demo/chatgpt/chat";
    }
	@RequestMapping("/demo/chatgpt/chat1")
	public String chat1() throws IOException {
		return "/demo/chatgpt/chat1";
	}
	@RequestMapping("/demo/chatgpt/test")
	public String test() throws IOException {
		return "/demo/chatgpt/test";
	}
	@RequestMapping("/demo/chatgpt/test2")
	public String test2() throws IOException {
		return "/demo/chatgpt/test2";
	}
	@RequestMapping("/demo/chatgpt/prompt")
	public String prompt() throws IOException {
		return "/demo/chatgpt/prompt";
	}
	@RequestMapping("/demo/chatgpt/uploadFile")
	public String uploadFile() throws IOException {
		return "/demo/chatgpt/uploadFile";
	}
}
