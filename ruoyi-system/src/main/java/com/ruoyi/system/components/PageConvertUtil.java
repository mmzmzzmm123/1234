package com.ruoyi.system.components;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ruoyi.system.extend.data.UtTouchPage;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页器转化工具类
 *
 * @author Jing.Zhang
 */
public abstract class PageConvertUtil {

    public static <T> Page<T> defaultPage() {
        return new Page<>(1, 10);
    }


    public static <T, R> Page<T> convertRecord(Page<R> page, Function<R, T> function) {
        Page<T> result = new Page<>();
        if (CollectionUtils.isNotEmpty(page.getRecords())) {
            result.setRecords(page.getRecords().stream().map(function).collect(Collectors.toList()));
        }
        result.setSize(page.getSize());
        result.setPages(page.getPages());
        result.setTotal(page.getTotal());
        return result;
    }

    public static <T, R> Page<T> convertRecordList(Page<R> page, Function<List<R>, List<T>> function) {
        Page<T> result = new Page<>();
        if (CollectionUtils.isNotEmpty(page.getRecords())) {
            result.setRecords(function.apply(page.getRecords()));
        }
        result.setSize(page.getSize());
        result.setPages(page.getPages());
        result.setTotal(page.getTotal());
        return result;
    }

    public static <T> Page<T> convertUtTouchPage(UtTouchPage<T> utTouchPage) {
        if (utTouchPage == null) {
            return defaultPage();
        }
        Page<T> result = new Page<>();
        if (CollectionUtils.isNotEmpty(utTouchPage.getDataList())) {
            result.setRecords(utTouchPage.getDataList());
        }
        result.setSize(utTouchPage.getPageSize());
        result.setCurrent(utTouchPage.getCurrentPage());
        result.setPages(calculatePages(utTouchPage.getRecordCount(), utTouchPage.getPageSize()));
        result.setTotal(utTouchPage.getRecordCount());
        return result;
    }


    public static long calculatePages(Integer total, Integer pageSize) {
        if (total == null || pageSize == null || pageSize == 0) {
            return 0L;
        }

        return Double.valueOf(Math.ceil(Double.valueOf(total) / pageSize)).longValue();
    }
}
