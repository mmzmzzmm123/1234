package com.boot.quartz.task;

import com.boot.chatgpt.controller.ChatGPTWebSocket;
import com.boot.chatgpt.controller.ChatGPTWebSocket2;
import com.boot.chatgpt.service.ChatGPTPromptService;
import com.boot.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.boot.common.utils.StringUtils;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 定时任务调度测试
 * 
 * @author boot
 */
@Component("bootTask")
@Slf4j
public class BootTask
{
	@Autowired
	private ChatGPTPromptService chatGPTPromptService;
    public void multipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void params(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void noParams()
    {
        System.out.println("执行无参方法");
    }
	
	public void closeSession(Integer i){
//		Map<WebSocketSession, Long> sessionMap = ChatGPTWebSocket.sessionMap;
//		Set<WebSocketSession> webSocketSessions = sessionMap.keySet();
//		for (WebSocketSession webSocketSession : webSocketSessions) {
//			Long aLong = sessionMap.get(webSocketSession);
//			long l = System.currentTimeMillis() - aLong;
//			if(l > 1000*60*chatGPTPromptService.getOverTime()){
//				try {
//					webSocketSession.close();
//					sessionMap.remove(webSocketSession);
//				} catch (IOException e) {
//					log.error("非活跃会话关闭失败",e);
//				}
//			}
//		}

		Map<WebSocketSession, Long> sessionMap2 = ChatGPTWebSocket2.sessionMap;
		Set<WebSocketSession> webSocketSessions2 = sessionMap2.keySet();
		log.info("当前会话数="+webSocketSessions2.size());
		for (WebSocketSession webSocketSession : webSocketSessions2) {
			Long aLong = sessionMap2.get(webSocketSession);
			long l = System.currentTimeMillis() - aLong;
			if(l > 1000*60*chatGPTPromptService.getOverTime()){
				try {
					Date createDate = new Date(aLong);
					String createDateFormat = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,createDate);
					String timeNow = DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD_HH_MM_SS);
					log.info("会话已过期:"+webSocketSession.getId()+"创建时间="+createDateFormat+"----销毁时间="+timeNow);
					webSocketSession.close();
					sessionMap2.remove(webSocketSession);
				} catch (IOException e) {
					log.error("非活跃会话关闭失败",e);
				}
			}
		}
	}
	
}
