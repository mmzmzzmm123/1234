package com.ruoyi.fin.service;

import java.util.List;
import com.ruoyi.fin.domain.FinBook;

/**
 * 记账账本Service接口
 * 
 * @author ruoyi
 * @date 2023-09-15
 */
public interface IFinBookService 
{
    /**
     * 查询记账账本
     * 
     * @param bookId 记账账本主键
     * @return 记账账本
     */
    public FinBook selectFinBookByBookId(Long bookId);

    /**
     * 查询记账账本列表
     * 
     * @param finBook 记账账本
     * @return 记账账本集合
     */
    public List<FinBook> selectFinBookList(FinBook finBook);

    /**
     * 新增记账账本
     * 
     * @param finBook 记账账本
     * @return 结果
     */
    public int insertFinBook(FinBook finBook);

    /**
     * 修改记账账本
     * 
     * @param finBook 记账账本
     * @return 结果
     */
    public int updateFinBook(FinBook finBook);

    /**
     * 批量删除记账账本
     * 
     * @param bookIds 需要删除的记账账本主键集合
     * @return 结果
     */
    public int deleteFinBookByBookIds(Long[] bookIds);

    /**
     * 删除记账账本信息
     * 
     * @param bookId 记账账本主键
     * @return 结果
     */
    public int deleteFinBookByBookId(Long bookId);
}
