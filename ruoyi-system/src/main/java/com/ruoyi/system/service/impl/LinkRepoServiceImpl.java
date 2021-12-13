package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.LinkRepoMapper;
import com.ruoyi.system.domain.LinkRepo;
import com.ruoyi.system.service.ILinkRepoService;

/**
 * 链接仓库Service业务层处理
 *
 * @author ysj
 * @date 2021-12-07
 */
@Service
public class LinkRepoServiceImpl implements ILinkRepoService
{
    @Autowired
    private LinkRepoMapper linkRepoMapper;

    /**
     * 查询链接仓库
     *
     * @param id 链接仓库主键
     * @return 链接仓库
     */
    @Override
    public LinkRepo selectLinkRepoById(Long id)
    {
        return linkRepoMapper.selectLinkRepoById(id);
    }

    /**
     * 查询链接仓库列表
     *
     * @param linkRepo 链接仓库
     * @return 链接仓库
     */
    @Override
    public List<LinkRepo> selectLinkRepoList(LinkRepo linkRepo)
    {
        return linkRepoMapper.selectLinkRepoList(linkRepo);
    }

    /**
     * 新增链接仓库
     *
     * @param linkRepo 链接仓库
     * @return 结果
     */
    @Override
    public int insertLinkRepo(LinkRepo linkRepo)
    {
        linkRepo.setCreateTime(DateUtils.getNowDate());
        return linkRepoMapper.insertLinkRepo(linkRepo);
    }

    /**
     * 修改链接仓库
     *
     * @param linkRepo 链接仓库
     * @return 结果
     */
    @Override
    public int updateLinkRepo(LinkRepo linkRepo)
    {
        linkRepo.setUpdateTime(DateUtils.getNowDate());
        return linkRepoMapper.updateLinkRepo(linkRepo);
    }

    /**
     * 批量删除链接仓库
     *
     * @param ids 需要删除的链接仓库主键
     * @return 结果
     */
    @Override
    public int deleteLinkRepoByIds(Long[] ids)
    {
        return linkRepoMapper.deleteLinkRepoByIds(ids);
    }

    /**
     * 删除链接仓库信息
     *
     * @param id 链接仓库主键
     * @return 结果
     */
    @Override
    public int deleteLinkRepoById(Long id)
    {
        return linkRepoMapper.deleteLinkRepoById(id);
    }

    @Override
    public List<LinkRepo> selectLinkRepoListOfUser(Long userId) {

        return linkRepoMapper.selectLinkRepoListOfUser(userId);
    }

    @Override
    public List<LinkRepo> selectLink(Long userId, Long visualId, Long panId) {

        LinkRepo linkRepo = new LinkRepo();
        linkRepo.setUserId(userId);
        linkRepo.setVisualId(visualId);
        linkRepo.setPanoramaId(panId);
        return linkRepoMapper.selectLinkRepoList(linkRepo);
    }

    @Override
    public Boolean check(Long userId, Long visualId, Long panId) {
        LinkRepo linkRepo = new LinkRepo();
        linkRepo.setUserId(userId);
        linkRepo.setVisualId(visualId);
        linkRepo.setPanoramaId(panId);
        List<LinkRepo> links = linkRepoMapper.selectLinkRepoList(linkRepo);

        Date now = new Date();
        for (LinkRepo link : links) {

            if(now.compareTo(link.getStartTime()) >= 0 && now.compareTo(link.getEndTime()) <= 0){
                return true;
            }
        }
        return false;
    }
}
