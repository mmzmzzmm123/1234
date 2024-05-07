package com.ruoyi.app.articles.ao;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.system.articles.domain.TArticles;
import com.ruoyi.system.articles.service.ITArticlesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("mobileArticlesAO")
public class MobileArticlesAO {

    @Resource
    private ITArticlesService articlesService;

    public R<TableDataInfo> queryHostListPage(){

        return R.ok();
    }
}
