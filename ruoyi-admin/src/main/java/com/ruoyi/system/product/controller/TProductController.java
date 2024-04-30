package com.ruoyi.system.product.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.ruoyi.system.category.domain.TCategory;
import com.ruoyi.system.category.service.ITCategoryService;
import com.ruoyi.system.product.domain.vo.TProductVO;
import com.ruoyi.system.productcategory.domain.TProductCategory;
import com.ruoyi.system.productcategory.service.ITProductCategoryService;
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
import com.ruoyi.system.product.domain.TProduct;
import com.ruoyi.system.product.service.ITProductService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 商品Controller
 *
 * @author ruoyi
 * @date 2024-04-19
 */
@RestController
@RequestMapping("/system/product")
public class TProductController extends BaseController {
    @Autowired
    private ITProductService tProductService;
    @Resource
    private ITProductCategoryService productCategoryService;
    @Resource
    private ITCategoryService categoryService;

    /**
     * 查询商品列表
     */
    @PreAuthorize("@ss.hasPermi('system:product:list')")
    @GetMapping("/list")
    public TableDataInfo list(TProduct tProduct) {
        startPage();
        List<TProduct> list = tProductService.selectTProductList(tProduct);
        return getDataTable(list);
    }

    @GetMapping("/listvo")
    public TableDataInfo listvo(TProduct tProduct) {
        startPage();
        List<TProduct> list = tProductService.selectTProductList(tProduct);
        List<TProductVO> voList = new ArrayList<>();

        if (CollUtil.isNotEmpty(list)) {
            // 组装分类
            for (TProduct t : list) {
                TProductVO tProductVO = new TProductVO();
                BeanUtil.copyProperties(t, tProductVO);
                TProductCategory tProductCategoryQuery = new TProductCategory();
                tProductCategoryQuery.setProductId(tProductVO.getId());
                List<TProductCategory> tProductCategories = productCategoryService.selectTProductCategoryList(tProductCategoryQuery);
                if (CollUtil.isNotEmpty(tProductCategories)) {
                    tProductVO.setCategoryList(tProductCategories.stream().map(TProductCategory::getCategoryId).collect(Collectors.toList()));
                }
                //隐藏价格模糊处理
//                tProductVO.setHidePrice(BigDecimal.valueOf(0));
                voList.add(tProductVO);
            }
        }
        TableDataInfo tableDataInfo = getDataTable(list);
        tableDataInfo.setRows(voList);
        return tableDataInfo;
    }

    /**
     * 导出商品列表
     */
    @PreAuthorize("@ss.hasPermi('system:product:export')")
    @Log(title = "商品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TProduct tProduct) {
        List<TProduct> list = tProductService.selectTProductList(tProduct);
        ExcelUtil<TProduct> util = new ExcelUtil<TProduct>(TProduct.class);
        util.exportExcel(response, list, "商品数据");
    }

    /**
     * 获取商品详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:product:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(tProductService.selectTProductById(id));
    }

    @PreAuthorize("@ss.hasPermi('system:product:query')")
    @GetMapping(value = "/vo/{id}")
    public AjaxResult getVoInfo(@PathVariable("id") Long id) {
        return success(tProductService.selectTProducVotById(id));
    }


    /**
     * 新增商品
     */
    @PreAuthorize("@ss.hasPermi('system:product:add')")
    @Log(title = "商品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TProduct tProduct) {
        return toAjax(tProductService.insertTProduct(tProduct));
    }


    @Log(title = "商品", businessType = BusinessType.INSERT)
    @PostMapping("addVo")
    public AjaxResult addAVO(@RequestBody TProductVO tProductVO) {
        return toAjax(tProductService.insertTProductVO(tProductVO));
    }

    /**
     * 修改商品
     */
    @PreAuthorize("@ss.hasPermi('system:product:edit')")
    @Log(title = "商品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TProduct tProduct) {
        return toAjax(tProductService.updateTProduct(tProduct));
    }


    @PreAuthorize("@ss.hasPermi('system:product:edit')")
    @Log(title = "商品", businessType = BusinessType.UPDATE)
    @PutMapping("/editVO")
    public AjaxResult edit(@RequestBody TProductVO tProductVO) {
        return toAjax(tProductService.updateTProductVO(tProductVO));
    }


    /**
     * 删除商品
     */
    @PreAuthorize("@ss.hasPermi('system:product:remove')")
    @Log(title = "商品", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tProductService.deleteTProductByIds(ids));
    }
}
