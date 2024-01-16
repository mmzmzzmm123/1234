package com.ruoyi.bk.mapper;

import java.util.List;
import com.ruoyi.bk.domain.WebInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 网站信息Mapper接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface WebInfoMapper extends BaseMapper<WebInfo>{
    /**
     * 查询网站信息
     *
     * @param id 网站信息主键
     * @return 网站信息
     */
    public WebInfo selectWebInfoById(Long id);

    /**
     * 查询网站信息列表
     *
     * @param webInfo 网站信息
     * @return 网站信息集合
     */
    public List<WebInfo> selectWebInfoList(WebInfo webInfo);

    /**
     * 新增网站信息
     *
     * @param webInfo 网站信息
     * @return 结果
     */
    public int insertWebInfo(WebInfo webInfo);

    /**
     * 修改网站信息
     *
     * @param webInfo 网站信息
     * @return 结果
     */
    public int updateWebInfo(WebInfo webInfo);

    /**
     * 删除网站信息
     *
     * @param id 网站信息主键
     * @return 结果
     */
    public int deleteWebInfoById(Long id);

    /**
     * 批量删除网站信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWebInfoByIds(Long[] ids);
}
