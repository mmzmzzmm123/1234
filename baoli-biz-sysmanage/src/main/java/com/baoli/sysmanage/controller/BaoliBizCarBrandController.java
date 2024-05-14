package com.baoli.sysmanage.controller;

import java.nio.charset.Charset;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.baoli.sysmanage.domain.BaoliBizCarBrand;
import com.baoli.sysmanage.service.IBaoliBizCarBrandService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.client.RestTemplate;

/**
 * 汽车品牌Controller
 * 
 * @author niujs
 * @date 2024-03-18
 */
@RestController
@RequestMapping("/sysmanage/carbrand")
public class BaoliBizCarBrandController extends BaseController
{
    @Autowired
    private IBaoliBizCarBrandService baoliBizCarBrandService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 查询汽车品牌列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carbrand:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizCarBrand baoliBizCarBrand)
    {
        if(baoliBizCarBrand.isPageAble()){
            startPage();
        }
        List<BaoliBizCarBrand> list = baoliBizCarBrandService.selectBaoliBizCarBrandList(baoliBizCarBrand);
        return getDataTable(list);
    }

    /**
     * 导出汽车品牌列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carbrand:export')")
    @Log(title = "汽车品牌", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizCarBrand baoliBizCarBrand)
    {
        List<BaoliBizCarBrand> list = baoliBizCarBrandService.selectBaoliBizCarBrandList(baoliBizCarBrand);
        ExcelUtil<BaoliBizCarBrand> util = new ExcelUtil<BaoliBizCarBrand>(BaoliBizCarBrand.class);
        util.exportExcel(response, list, "汽车品牌数据");
    }

    /**
     * 获取汽车品牌详细信息
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carbrand:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizCarBrandService.selectBaoliBizCarBrandById(id));
    }

    /**
     * 新增汽车品牌
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carbrand:add')")
    @Log(title = "汽车品牌", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizCarBrand baoliBizCarBrand)
    {
        return toAjax(baoliBizCarBrandService.insertBaoliBizCarBrand(baoliBizCarBrand));
    }

    /**
     * 修改汽车品牌
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carbrand:edit')")
    @Log(title = "汽车品牌", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizCarBrand baoliBizCarBrand)
    {
        return toAjax(baoliBizCarBrandService.updateBaoliBizCarBrand(baoliBizCarBrand));
    }

    /**
     * 删除汽车品牌
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carbrand:remove')")
    @Log(title = "汽车品牌", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizCarBrandService.deleteBaoliBizCarBrandByIds(ids));
    }

    /**
     * @author shijiaqi
     * @date 2024/5/11 12:48
     * @Description 同步汽车品牌，系列，型号
     */
    @PostMapping("/sync")
    public AjaxResult sync(){
        // 清除品牌临时数据
        String sql ="truncate table baoli_sync_car_brand";
        jdbcTemplate.execute(sql);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(Charset.forName("GBK")));
        String response = restTemplate.getForObject("https://www.autohome.com.cn/js/index/findcarnew_branddata.js",String.class);
        response = response.substring(22);
        JSONArray brands = JSONArray.parseArray(response);
        for(int i=0;i<brands.size();i++){
            JSONObject brandItem = brands.getJSONObject(i);
            // 插入一级品牌
            sql = "insert into baoli_sync_car_brand values(?,?,?)";
            jdbcTemplate.update(sql,brandItem.get("id"),brandItem.get("name"),brandItem.get("letter"));
            // 插入二级品牌
            response = restTemplate.getForObject("https://www.autohome.com.cn/ashx/index/GetHomeFindCar.ashx?type=1&brandid=33&v=1",String.class);
            JSONObject level2Brand = JSONObject.parseObject(response);
            JSONArray level2Brands = level2Brand.getJSONArray("fctlist");
            sql ="insert into baoli_sync_car_brand_level2 values (?,?,?)";
            for(int j=0;j<level2Brands.size();j++){
                JSONObject brand2Item = level2Brands.getJSONObject(i);
                jdbcTemplate.update(sql,brand2Item.get("fctid"),brand2Item.get("fctname"),brand2Item.get("fctPy"));
            }
        }

        return toAjax(1);
    }
}
