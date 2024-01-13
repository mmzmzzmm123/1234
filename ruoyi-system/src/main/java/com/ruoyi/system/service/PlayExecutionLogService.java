package com.ruoyi.system.service;

import com.ruoyi.system.domain.mongdb.PlayExecutionLog;

import java.util.List;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/13/013 15:25
 * @Description :
 */
public interface PlayExecutionLogService {

    /**
     * 查询剧本执行日志列表
     *
     * @param playId
     * @return
     */
    List<PlayExecutionLog> listByPlayId(String playId);

    /**
     * 保存日志
     * @param playId
     * @param content
     */
    void saveLog(String playId, String content);


}
