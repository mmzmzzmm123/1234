package com.ruoyi.project.monitor.controller;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.redis.RedisCache;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.monitor.domain.TeemLinkExt;
import com.ruoyi.project.monitor.domain.TeemLinkMonitorQueryModel;
import com.ruoyi.project.monitor.domain.TeemLinkTable;
import com.ruoyi.project.monitor.service.ITeemLinkMonitorService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * teemlink数据监控
 *
 * @author lihe
 */
@RestController
@RequestMapping("/monitor/teemlink")
public class TeemLinkMonitorController extends BaseController {

    @Value("${uv.formMonitor}")
    private String teemLinkPath;
    @Autowired
    private ITeemLinkMonitorService teemLinkMonitorService;
//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private RedisCache redisCache;

    /**
     * teemlink表单查询
     *
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo list(TeemLinkMonitorQueryModel monitorQueryModel) {

        List<TeemLinkExt> list = redisCache.getCacheList("teemlink_form");
        if (null == list || 0 == list.size()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(teemLinkPath)
                    .build();
            try (Response response = client.newCall(request).execute()) {

                TeemLinkTable[] arr = JSON.parseObject(response.body().string(), TeemLinkTable[].class);
                list = buildTeemLinkExt(Arrays.asList(arr));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        List<TeemLinkExt> selectList =
                list.stream()
                        .filter(sp -> (StringUtils.isNotEmpty(monitorQueryModel.getTableName())
                                && ((sp.getTableChineseName().contains(monitorQueryModel.getTableName())) ||
                                (sp.getTableDbName().contains(monitorQueryModel.getTableName()))) || (StringUtils.isEmpty(monitorQueryModel.getTableName())))
                        ).collect(Collectors.toList());
        int total = selectList.size();
        selectList = selectList.stream().skip((monitorQueryModel.getPageIndex() - 1) * monitorQueryModel.getPageSize())
                .limit(monitorQueryModel.getPageSize()).collect(Collectors.toList());

        return getDataTable(selectList, total);
    }

    private List<TeemLinkExt> buildTeemLinkExt(List<TeemLinkTable> teemLinkTables) {
        List<TeemLinkExt> linkExts = new LinkedList<>();

        teemLinkTables.forEach(tableLinkTable -> {
            tableLinkTable.getColumns().forEach(teemLinkColumn -> {
                TeemLinkExt teemLinkExt = new TeemLinkExt();
                teemLinkExt.setTableChineseName(tableLinkTable.getChineseName());
                teemLinkExt.setTableDbName(tableLinkTable.getDbName());
                teemLinkExt.setColumnChineseName(teemLinkColumn.getChineseName());
                teemLinkExt.setColumnDbName(teemLinkColumn.getDbName());
                teemLinkExt.setFieldType(teemLinkColumn.getFieldType());
                teemLinkExt.setDictType(teemLinkColumn.getDictType());
                linkExts.add(teemLinkExt);
            });
        });

        return linkExts;
    }

    /**
     * teemlink表单数据统计
     *
     * @return
     */
    @GetMapping("/stat")
    public AjaxResult stat(@RequestParam("tableName") String tableName, @RequestParam("columnName") String columnName) {
        return AjaxResult.success(teemLinkMonitorService.stat(tableName, columnName));
    }
}
