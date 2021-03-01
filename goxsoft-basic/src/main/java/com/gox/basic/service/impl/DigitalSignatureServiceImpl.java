package com.gox.basic.service.impl;

import com.gox.basic.domain.DigitalSignature;
import com.gox.basic.mapper.DigitalSignatureMapper;
import com.gox.basic.service.IDigitalSignatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数字签名Service业务层处理
 *
 * @author gox
 * @date 2020-12-29
 */
@Service
public class DigitalSignatureServiceImpl implements IDigitalSignatureService {
    @Autowired
    private DigitalSignatureMapper digitalSignatureMapper;

    /**
     * 查询数字签名
     *
     * @param id 数字签名ID
     * @return 数字签名
     */
    @Override
    public DigitalSignature selectDigitalSignatureById(Long id) {
        return digitalSignatureMapper.selectDigitalSignatureById(id);
    }

    /**
     * 查询数字签名列表
     *
     * @param digitalSignature 数字签名
     * @return 数字签名
     */
    @Override
    public List<DigitalSignature> selectDigitalSignatureList(DigitalSignature digitalSignature) {
        return digitalSignatureMapper.selectDigitalSignatureList(digitalSignature);
    }

    /**
     * 新增数字签名
     *
     * @param digitalSignature 数字签名
     * @return 结果
     */
    @Override
    public int insertDigitalSignature(DigitalSignature digitalSignature) {
        return digitalSignatureMapper.insertDigitalSignature(digitalSignature);
    }

    /**
     * 修改数字签名
     *
     * @param digitalSignature 数字签名
     * @return 结果
     */
    @Override
    public int updateDigitalSignature(DigitalSignature digitalSignature) {
        return digitalSignatureMapper.updateDigitalSignature(digitalSignature);
    }

    /**
     * 批量删除数字签名
     *
     * @param ids 需要删除的数字签名ID
     * @return 结果
     */
    @Override
    public int deleteDigitalSignatureByIds(Long[] ids) {
        return digitalSignatureMapper.deleteDigitalSignatureByIds(ids);
    }

    /**
     * 删除数字签名信息
     *
     * @param id 数字签名ID
     * @return 结果
     */
    @Override
    public int deleteDigitalSignatureById(Long id) {
        return digitalSignatureMapper.deleteDigitalSignatureById(id);
    }
}
