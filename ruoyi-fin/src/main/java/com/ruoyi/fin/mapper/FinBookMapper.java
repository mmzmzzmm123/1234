package com.ruoyi.fin.mapper;

import java.util.List;
import com.ruoyi.fin.domain.FinBook;

/**
 * 记账账本Mapper接口
 * 
 * @author ruoyi
 * @date 2023-09-23
 */
public interface FinBookMapper 
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
     * 删除记账账本
     * 
     * @param bookId 记账账本主键
     * @return 结果
     */
    public int deleteFinBookByBookId(Long bookId);

    /**
     * 批量删除记账账本
     * 
     * @param bookIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinBookByBookIds(Long[] bookIds);
}
