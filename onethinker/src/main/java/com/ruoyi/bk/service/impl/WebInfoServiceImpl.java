package com.ruoyi.bk.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bk.mapper.WebInfoMapper;
import com.ruoyi.bk.domain.WebInfo;
import com.ruoyi.bk.service.IWebInfoService;
import lombok.extern.log4j.Log4j2;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * 网站信息Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class WebInfoServiceImpl extends ServiceImpl<WebInfoMapper,WebInfo> implements IWebInfoService {
    @Resource
    private WebInfoMapper webInfoMapper;

    /**
     * 查询网站信息
     *
     * @param id 网站信息主键
     * @return 网站信息
     */
    @Override
    public WebInfo selectWebInfoById(Long id) {
        return webInfoMapper.selectWebInfoById(id);
    }

    /**
     * 查询网站信息列表
     *
     * @param webInfo 网站信息
     * @return 网站信息
     */
    @Override
    public List<WebInfo> selectWebInfoList(WebInfo webInfo) {
        return webInfoMapper.selectWebInfoList(webInfo);
    }

    /**
     * 新增网站信息
     *
     * @param webInfo 网站信息
     * @return 结果
     */
    @Override
    public int insertWebInfo(WebInfo webInfo) {
            return webInfoMapper.insertWebInfo(webInfo);
    }

    /**
     * 修改网站信息
     *
     * @param webInfo 网站信息
     * @return 结果
     */
    @Override
    public int updateWebInfo(WebInfo webInfo) {
        return webInfoMapper.updateWebInfo(webInfo);
    }

    /**
     * 批量删除网站信息
     *
     * @param ids 需要删除的网站信息主键
     * @return 结果
     */
    @Override
    public int deleteWebInfoByIds(Long[] ids) {
        return webInfoMapper.deleteWebInfoByIds(ids);
    }

    /**
     * 删除网站信息信息
     *
     * @param id 网站信息主键
     * @return 结果
     */
    @Override
    public int deleteWebInfoById(Long id) {
        return webInfoMapper.deleteWebInfoById(id);
    }
}
