package com.ruoyi.system.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;


import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.page.VisualRespEmbData;
import com.ruoyi.system.domain.BladeVisualConfig;
import com.ruoyi.system.domain.vo.BladeImageVo;
import com.ruoyi.system.domain.vo.BladeVisualVo;
import com.ruoyi.system.domain.vo.IdNameVo;
import com.ruoyi.system.mapper.BladeVisualConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.BladeVisualMapper;
import com.ruoyi.system.domain.BladeVisual;
import com.ruoyi.system.service.IBladeVisualService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 可视化Service业务层处理
 *
 * @author yangsj
 * @date 2021-10-25
 */
@Service
public class BladeVisualServiceImpl implements IBladeVisualService
{
    @Autowired
    private BladeVisualMapper bladeVisualMapper;

    @Autowired
    private BladeVisualConfigServiceImpl configService;
    /**
     * 查询可视化
     *
     * @param id 可视化主键
     * @return 可视化
     */
    @Override
    public BladeVisualVo selectBladeVisualById(Long id)
    {
        BladeVisualVo bladeVisualVo = new BladeVisualVo();
        BladeVisual bladeVisual = bladeVisualMapper.selectBladeVisualById(id);
        BladeVisualConfig config = configService.selectBladeVisualConfigById(id);
        bladeVisualVo.setVisual(bladeVisual);
        bladeVisualVo.setConfig(config);
        return bladeVisualVo;
    }

    /**
     * 查询可视化列表
     *
     * @param bladeVisual 可视化
     * @return 可视化
     */
    @Override
    public List<BladeVisual> selectBladeVisualList(BladeVisual bladeVisual)
    {
        return bladeVisualMapper.selectBladeVisualList(bladeVisual);
    }

    @Override
    public List<BladeVisual> selectVisualListByCategory(Long category) {
        return bladeVisualMapper.selectVisualByCategory(category);
    }

    @Value("${server.port}")
    private int serverPort;

    public String getUrl() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch ( UnknownHostException e) {
            e.printStackTrace();
        }
//        return "http://"+address.getHostAddress() +":"+this.serverPort;
        return "http://"+"172.17.172.240"+":"+this.serverPort;
    }

    @Value("${imageDir.bigScreen}")
    private String screenPrefix;

    @Override
    public VisualRespEmbData uploadFile(MultipartFile file) {
        // 保存到本地
        String filePath =  RuoYiConfig.getProfile() + screenPrefix;
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        String filename = file.getOriginalFilename();
        String fullFileName = filePath + "/" + filename;
//        System.out.println("image fullName"+fullFileName);
        try {
            FileOutputStream out = new FileOutputStream(fullFileName);
            out.write(file.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        VisualRespEmbData resp = new VisualRespEmbData();

        BladeImageVo imageVo = new BladeImageVo();
        imageVo.setDomain(filePath);
        String link = this.getUrl() + Constants.RESOURCE_PREFIX +screenPrefix +"/"+filename;
        imageVo.setLink(link);
        resp.setData(imageVo);
        return resp;
    }

    @Autowired
    private BladeVisualConfigMapper configMapper;

    @Override
    public void copyById(Long id) {
        BladeVisual visual = bladeVisualMapper.selectBladeVisualById(id);
        visual.setId(System.currentTimeMillis());
        bladeVisualMapper.insertBladeVisual(visual);

        BladeVisualConfig config = configMapper.selectConfigByVisualId(id);
        config.setVisualId(visual.getId());
        config.setId(visual.getId());
        configMapper.insertBladeVisualConfig(config);

    }

    @Override
    public List<IdNameVo> getIdNames() {
        return bladeVisualMapper.getIdNames();
    }

    /**
     * 新增可视化
     *
     * @param bladeVisual 可视化
     * @return 结果
     */
    @Override
    public Long insertBladeVisual(BladeVisual bladeVisual)
    {

        bladeVisual.setId(System.currentTimeMillis());

        bladeVisualMapper.insertBladeVisual(bladeVisual);
        return bladeVisual.getId();
    }

    /**
     * 修改可视化
     *
     * @param bladeVisual 可视化
     * @return 结果
     */
    @Override
    public int updateBladeVisual(BladeVisual bladeVisual)
    {
        return bladeVisualMapper.updateBladeVisual(bladeVisual);
    }

    /**
     * 批量删除可视化
     *
     * @param ids 需要删除的可视化主键
     * @return 结果
     */
    @Override
    public int deleteBladeVisualByIds(Long[] ids)
    {
        return bladeVisualMapper.deleteBladeVisualByIds(ids);
    }

    /**
     * 删除可视化信息
     *
     * @param id 可视化主键
     * @return 结果
     */
    @Override
    public int deleteBladeVisualById(Long id)
    {
        return bladeVisualMapper.deleteBladeVisualById(id);
    }


}
