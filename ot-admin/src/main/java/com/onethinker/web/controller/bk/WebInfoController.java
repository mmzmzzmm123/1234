package com.onethinker.web.controller.bk;

import com.alibaba.fastjson2.JSONObject;
import com.onethinker.bk.domain.*;
import com.onethinker.bk.service.*;
import com.onethinker.bk.vo.BaseRequestVO;
import com.onethinker.bk.vo.ResourcePathVO;
import com.onethinker.common.constant.BkConstants;
import com.onethinker.common.core.controller.BaseController;
import com.onethinker.common.core.domain.AjaxResult;
import com.onethinker.common.core.page.TableDataInfo;
import com.onethinker.common.utils.ip.IpUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 网站信息Controller
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@RestController
@RequestMapping("/bk/webInfo")
public class WebInfoController extends BaseController {
    @Autowired
    private IWebInfoService webInfoService;
    @Autowired
    private ISortService sortService;
    @Autowired
    private IHistoryInfoService historyInfoService;
    @Autowired
    private IResourcePathService resourcePathService;
    @Autowired
    private ITreeHoleService treeHoleService;
    @Autowired
    private ILabelService labelService;

    /**
     * 查询网站信息
     */
    @GetMapping("/getWebInfo")
    public AjaxResult getWebInfo() {
        WebInfo webInfo = webInfoService.getWebInfo();
        webInfo.setRandomAvatar(null);
        webInfo.setRandomCover(null);
        webInfo.setRandomName(null);
        webInfo.setWaifuJson(null);
        return AjaxResult.success(webInfo);
    }

    /**
     * 获取分类标签信息
     */
    @GetMapping(value = "/getSortInfo")
    public AjaxResult getSortInfo() {
        return AjaxResult.success(sortService.getSortInfo());
    }


    /**
     * 获取看板娘消息
     */
    @GetMapping("/getWaifuJson")
    public AjaxResult getWaifuJson() {
        return AjaxResult.success(JSONObject.parseObject(webInfoService.getWebInfo().getWaifuJson()));
    }

    /**
     * 获取网站统计信息
     */
    @GetMapping("/getHistoryInfo")
    public AjaxResult getHistoryInfo() {
        return AjaxResult.success(historyInfoService.getHistoryInfo());
    }

    /**
     * 获取赞赏
     */
    @GetMapping("/getAdmire")
    public AjaxResult getAdmire() {
        return AjaxResult.success(Lists.newArrayList());
    }

    /**
     * 更新博客信息
     *
     * @param webInfo
     * @return
     */
    @PostMapping("/updateWebInfo")
    public AjaxResult updateWebInfo(@RequestBody WebInfo webInfo) {
        webInfoService.updateWebInfo(webInfo);
        return AjaxResult.success();
    }

    /**
     * 保存资源信息
     *
     * @param resourcePathVO
     * @return
     */
    @PostMapping("/saveResourcePath")
    public AjaxResult saveResourcePath(@RequestBody ResourcePathVO resourcePathVO) {
        resourcePathService.insertResourcePath(resourcePathVO, ResourcePathVO.SAVE_RESOURCE_PATH);
        return AjaxResult.success();
    }

    /**
     * 保存友链
     *
     * @param resourcePathVO
     * @return
     */
    @PostMapping("/saveFriend")
    public AjaxResult saveFriend(@RequestBody ResourcePathVO resourcePathVO) {
        resourcePathService.insertResourcePath(resourcePathVO, ResourcePathVO.SAVE_FRIEND);
        return AjaxResult.success();
    }

    /**
     * 查询友链
     *
     * @return
     */
    @GetMapping("/listFriend")
    public AjaxResult listFriend() {
        return AjaxResult.success(resourcePathService.queryResourcePathByType(BkConstants.RESOURCE_PATH_TYPE_FRIEND));
    }

    /**
     * 删除资源信息
     */
    @GetMapping("/deleteResourcePath")
    public AjaxResult deleteResourcePath(@RequestParam("id") Long id) {
        resourcePathService.deleteResourcePathById(id);
        return AjaxResult.success();
    }

    /**
     * 更新资源路径信息
     *
     * @param resourcePathVO
     * @return
     */
    @PostMapping("/updateResourcePath")
    public AjaxResult updateResourcePath(@RequestBody ResourcePathVO resourcePathVO) {
        resourcePathService.updateResourcePath(resourcePathVO);
        return AjaxResult.success();
    }

    /**
     * 查询资源
     *
     * @param baseRequestVO
     * @return
     */
    @PostMapping("/listResourcePath")
    public TableDataInfo listResourcePath(@RequestBody BaseRequestVO baseRequestVO) {
        startPage();
        List<ResourcePath> resourcePaths = resourcePathService.listResourcePath(baseRequestVO);
        return getDataTable(resourcePaths);
    }

    /**
     * 查询音乐
     */
    @GetMapping("/listFunny")
    public AjaxResult listFunny() {
        return AjaxResult.success(resourcePathService.queryClassifyByType(BkConstants.RESOURCE_PATH_TYPE_FUNNY));
    }

    /**
     * 查询收藏
     *
     * @return
     */
    @GetMapping("/listCollect")
    public AjaxResult listCollect() {
        return AjaxResult.success(resourcePathService.queryResourcePathByType(BkConstants.RESOURCE_PATH_TYPE_FAVORITES));
    }

    /**
     * 保存音乐
     */
    @PostMapping("/saveFunny")
    public AjaxResult saveFunny(@RequestBody ResourcePathVO resourcePathVO) {
        resourcePathService.insertResourcePath(resourcePathVO, ResourcePathVO.SAVE_FUNNY);
        return AjaxResult.success();
    }

    /**
     * 查询爱情
     */
    @GetMapping("/listAdminLovePhoto")
    public AjaxResult listAdminLovePhoto() {
        return AjaxResult.success(resourcePathService.queryClassifyByType(BkConstants.RESOURCE_PATH_TYPE_LOVE_PHOTO));
    }

    /**
     * 保存爱情
     *
     * @param resourcePathVO
     * @return
     */
    @PostMapping("/saveLovePhoto")
    public AjaxResult saveLovePhoto(@RequestBody ResourcePathVO resourcePathVO) {
        resourcePathService.insertResourcePath(resourcePathVO, ResourcePathVO.SAVE_LOVE_PHOTO);
        return AjaxResult.success();
    }

    /**
     * 保存树洞信息
     *
     * @param treeHole
     * @return
     */
    @PostMapping("/saveTreeHole")
    public AjaxResult saveTreeHole(@RequestBody TreeHole treeHole, HttpServletRequest request) {
        String ipAddr = IpUtils.getIpAddr(request);
        treeHoleService.insertTreeHole(treeHole, ipAddr);
        return AjaxResult.success();
    }

    /**
     * 删除树洞
     */
    @GetMapping("/deleteTreeHole")
    public AjaxResult deleteTreeHole(@RequestParam("id") Long id) {
        treeHoleService.deleteTreeHoleById(id);
        return AjaxResult.success();
    }

    /**
     * 查询树洞List
     */
    @GetMapping("/listTreeHole")
    public AjaxResult listTreeHole() {
        return AjaxResult.success(treeHoleService.listTreeHole());
    }

    /**
     * 保存分类信息
     */
    @PostMapping("/saveSort")
    public AjaxResult saveSort(@RequestBody Sort sort) {
        sortService.insertSort(sort);
        return AjaxResult.success();
    }

    /**
     * 删除分类
     */
    @GetMapping("/deleteSort")
    public AjaxResult deleteSort(@RequestParam("id") Long id) {
        sortService.deleteSortById(id);
        return AjaxResult.success();
    }

    /**
     * 更新分类
     */
    @PostMapping("/updateSort")
    public AjaxResult updateSort(@RequestBody Sort sort) {
        sortService.updateSort(sort);
        return AjaxResult.success();
    }

    /**
     * 查询分类
     */
    @GetMapping("/listSort")
    public AjaxResult listSort() {
        return AjaxResult.success(sortService.selectSortList(null));
    }

    /**
     * 保存标签信息
     */
    @PostMapping("/saveLabel")
    public AjaxResult saveLabel(@RequestBody Label label) {
        labelService.insertLabel(label);
        return AjaxResult.success();
    }

    /**
     * 删除标签信息
     */
    @GetMapping("/deleteLabel")
    public AjaxResult deleteLabel(@RequestParam("id") Long id) {
        labelService.deleteLabelById(id);
        return AjaxResult.success();
    }

    /**
     * 更新标签信息
     */
    @PostMapping("/updateLabel")
    public AjaxResult updateLabel(@RequestBody Label label) {
        labelService.updateLabel(label);
        return AjaxResult.success();
    }

    /**
     * 查询标签List
     */
    @GetMapping("/listLabel")
    public AjaxResult listLabel() {
        return AjaxResult.success(labelService.getLabelInfo());
    }

    /**
     * 查询全部分类标签
     */
    @GetMapping("/listSortAndLabel")
    public AjaxResult listSortAndLabel() {
        return AjaxResult.success(sortService.listSortAndLabel());
    }

}
