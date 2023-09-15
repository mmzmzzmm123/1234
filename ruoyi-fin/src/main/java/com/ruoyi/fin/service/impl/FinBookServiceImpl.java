package com.ruoyi.fin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fin.mapper.FinBookMapper;
import com.ruoyi.fin.domain.FinBook;
import com.ruoyi.fin.service.IFinBookService;

/**
 * 记账账本Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-09-15
 */
@Service
public class FinBookServiceImpl implements IFinBookService 
{
    @Autowired
    private FinBookMapper finBookMapper;

    /**
     * 查询记账账本
     * 
     * @param bookId 记账账本主键
     * @return 记账账本
     */
    @Override
    public FinBook selectFinBookByBookId(Long bookId)
    {
        return finBookMapper.selectFinBookByBookId(bookId);
    }

    /**
     * 查询记账账本列表
     * 
     * @param finBook 记账账本
     * @return 记账账本
     */
    @Override
    public List<FinBook> selectFinBookList(FinBook finBook)
    {
        return finBookMapper.selectFinBookList(finBook);
    }

    /**
     * 新增记账账本
     * 
     * @param finBook 记账账本
     * @return 结果
     */
    @Override
    public int insertFinBook(FinBook finBook)
    {
        return finBookMapper.insertFinBook(finBook);
    }

    /**
     * 修改记账账本
     * 
     * @param finBook 记账账本
     * @return 结果
     */
    @Override
    public int updateFinBook(FinBook finBook)
    {
        return finBookMapper.updateFinBook(finBook);
    }

    /**
     * 批量删除记账账本
     * 
     * @param bookIds 需要删除的记账账本主键
     * @return 结果
     */
    @Override
    public int deleteFinBookByBookIds(Long[] bookIds)
    {
        return finBookMapper.deleteFinBookByBookIds(bookIds);
    }

    /**
     * 删除记账账本信息
     * 
     * @param bookId 记账账本主键
     * @return 结果
     */
    @Override
    public int deleteFinBookByBookId(Long bookId)
    {
        return finBookMapper.deleteFinBookByBookId(bookId);
    }
}
