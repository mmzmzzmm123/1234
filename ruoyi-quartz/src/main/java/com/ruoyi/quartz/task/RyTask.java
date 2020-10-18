package com.ruoyi.quartz.task;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.note.service.INmNoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.StringUtils;

/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask
{
    private static final Logger log = LoggerFactory.getLogger(RyTask.class);

    @Autowired
    private INmNoteService nmNoteService;

    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }

    /**
     * 每天凌晨 Redis缓存中的文章 转移到MongoDB数据库
     *
     * @param
     * @return
     */
    public void redisToMongonDB()
    {
        log.info(DateUtil.now()+"开始执行>>>Redis缓存中的文章 转移到MongoDB数据库");
        nmNoteService.redisToMongonDB();
        log.info(DateUtil.now()+"执行完毕>>>Redis缓存中的文章 转移到MongoDB数据库");
    }
}
