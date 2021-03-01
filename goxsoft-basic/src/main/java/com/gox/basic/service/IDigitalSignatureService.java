package com.gox.basic.service;

import com.gox.basic.domain.DigitalSignature;

import java.util.List;

/**
 * 数字签名Service接口
 *
 * @author gox
 * @date 2020-12-29
 */
public interface IDigitalSignatureService {
    /**
     * 查询数字签名
     *
     * @param id 数字签名ID
     * @return 数字签名
     */
    public DigitalSignature selectDigitalSignatureById(Long id);

    /**
     * 查询数字签名列表
     *
     * @param digitalSignature 数字签名
     * @return 数字签名集合
     */
    public List<DigitalSignature> selectDigitalSignatureList(DigitalSignature digitalSignature);

    /**
     * 新增数字签名
     *
     * @param digitalSignature 数字签名
     * @return 结果
     */
    public int insertDigitalSignature(DigitalSignature digitalSignature);

    /**
     * 修改数字签名
     *
     * @param digitalSignature 数字签名
     * @return 结果
     */
    public int updateDigitalSignature(DigitalSignature digitalSignature);

    /**
     * 批量删除数字签名
     *
     * @param ids 需要删除的数字签名ID
     * @return 结果
     */
    public int deleteDigitalSignatureByIds(Long[] ids);

    /**
     * 删除数字签名信息
     *
     * @param id 数字签名ID
     * @return 结果
     */
    public int deleteDigitalSignatureById(Long id);
}
