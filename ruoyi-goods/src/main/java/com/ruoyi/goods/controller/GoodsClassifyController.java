package com.ruoyi.goods.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.goods.consts.GoodsConst;
import org.apache.commons.lang3.ArrayUtils;
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
import com.ruoyi.goods.domain.GoodsClassify;
import com.ruoyi.goods.service.IGoodsClassifyService;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 货品分类Controller
 *
 * @author lixin
 * @date 2023-02-02
 */
@RestController
@RequestMapping("/goods/classify")
public class GoodsClassifyController extends BaseController {
    @Autowired
    private IGoodsClassifyService goodsClassifyService;

    /**
     * 层级检查
     *
     * @param goodsClassify
     * @return
     */
    private boolean checkPassLevel(GoodsClassify goodsClassify) {
        if (goodsClassify.getParentId() == null || goodsClassify.getParentId() <= 0) {
            goodsClassify.setParentId(0L);
        }

        if (goodsClassify.getParentId().equals(0L)) {
            return true;
        }

        // 父层级检查
        Integer parentLevel = goodsClassifyService.selectLevel(goodsClassify.getParentId());
        if (parentLevel == null) {
            return false;
        }
        // 子层级检查
        int currentLevel = parentLevel + 1;
        Integer currentChildMaxLevel = 0;
        if (goodsClassify.getClassifyId() != null) {
            currentChildMaxLevel = goodsClassifyService.selectLevelMax(goodsClassify.getClassifyId());
        }

        if (currentLevel + currentChildMaxLevel > GoodsConst.CLASSIFY_MAX_LEVEL - 1) {
            // 不允许最大层级
            return false;
        }
        return true;
    }

    /**
     * 查询货品分类列表
     */
    @PreAuthorize("@ss.hasPermi('goods:classify:list')")
    @GetMapping("/list")
    public AjaxResult list(GoodsClassify goodsClassify) {
        List<GoodsClassify> list = goodsClassifyService.selectGoodsClassifyList(goodsClassify);
        return success(list);
    }

    /**
     * 查询分类列表（排除节点）
     */
    @PreAuthorize("@ss.hasPermi('goods:classify:list')")
    @GetMapping("/list/exclude/{classifyId}")
    public AjaxResult excludeChild(@PathVariable(value = "classifyId", required = false) Long classifyId) {
        List<GoodsClassify> classifys = goodsClassifyService.selectGoodsClassifyList(new GoodsClassify());
        classifys.removeIf(d -> d.getClassifyId().longValue() == classifyId.longValue() || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), classifyId + ""));
        return success(classifys);
    }

    /**
     * 获取货品分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('goods:classify:query')")
    @GetMapping(value = "/{classifyId}")
    public AjaxResult getInfo(@PathVariable("classifyId") Long classifyId) {
        return success(goodsClassifyService.selectGoodsClassifyByClassifyId(classifyId));
    }

    /**
     * 新增货品分类
     */
    @PreAuthorize("@ss.hasPermi('goods:classify:add')")
    @Log(title = "货品分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsClassify goodsClassify) {
        if (!checkPassLevel(goodsClassify)) {
            return error("最多" + GoodsConst.CLASSIFY_MAX_LEVEL + "层分类");
        }
        goodsClassify.setCreateBy(getUsername());
        return toAjax(goodsClassifyService.insertGoodsClassify(goodsClassify));
    }

    /**
     * 修改货品分类
     */
    @PreAuthorize("@ss.hasPermi('goods:classify:edit')")
    @Log(title = "货品分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsClassify goodsClassify) {
        if (goodsClassify.getParentId().equals(goodsClassify.getClassifyId())) {
            return error("修改分类'" + goodsClassify.getClassifyName() + "'失败，上级分类不能是自己");
        }
        if (!checkPassLevel(goodsClassify)) {
            return error("最多" + GoodsConst.CLASSIFY_MAX_LEVEL + "层分类");
        }
        goodsClassify.setUpdateBy(getUsername());
        return toAjax(goodsClassifyService.updateGoodsClassify(goodsClassify));
    }

    /**
     * 删除货品分类
     */
    @PreAuthorize("@ss.hasPermi('goods:classify:remove')")
    @Log(title = "货品分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{classifyId}")
    public AjaxResult remove(@PathVariable Long classifyId) {
        if (goodsClassifyService.hasChildByClassifyId(classifyId)) {
            return warn("存在下级货品分类,不允许删除");
        }
        return toAjax(goodsClassifyService.deleteGoodsClassifyByClassifyId(classifyId));
    }
}
