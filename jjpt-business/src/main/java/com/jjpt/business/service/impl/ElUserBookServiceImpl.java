package com.jjpt.business.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.jjpt.business.domain.dto.ElQuDto;
import com.jjpt.business.service.IElQuService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jjpt.business.mapper.ElUserBookMapper;
import com.jjpt.business.domain.ElUserBook;
import com.jjpt.business.service.IElUserBookService;

/**
 * 错题本Service业务层处理
 * 
 * @author 卢亚峰
 * @date 2024-05-22
 */
@Service
public class ElUserBookServiceImpl implements IElUserBookService 
{
    @Autowired
    private ElUserBookMapper elUserBookMapper;
    @Autowired
    private IElQuService quService;

    /**
     * 查询错题本
     * 
     * @param id 错题本主键
     * @return 错题本
     */
    @Override
    public ElUserBook selectElUserBookById(String id)
    {
        return elUserBookMapper.selectElUserBookById(id);
    }

    /**
     * 查询错题本列表
     * 
     * @param elUserBook 错题本
     * @return 错题本
     */
    @Override
    public List<ElUserBook> selectElUserBookList(ElUserBook elUserBook)
    {
        return elUserBookMapper.selectElUserBookList(elUserBook);
    }

    /**
     * 新增错题本
     * 
     * @param elUserBook 错题本
     * @return 结果
     */
    @Override
    public int insertElUserBook(ElUserBook elUserBook)
    {
        elUserBook.setCreateTime(DateUtils.getNowDate());
        return elUserBookMapper.insertElUserBook(elUserBook);
    }

    /**
     * 修改错题本
     * 
     * @param elUserBook 错题本
     * @return 结果
     */
    @Override
    public int updateElUserBook(ElUserBook elUserBook)
    {
        elUserBook.setUpdateTime(DateUtils.getNowDate());
        return elUserBookMapper.updateElUserBook(elUserBook);
    }

    /**
     * 批量删除错题本
     * 
     * @param ids 需要删除的错题本主键
     * @return 结果
     */
    @Override
    public int deleteElUserBookByIds(String[] ids)
    {
        return elUserBookMapper.deleteElUserBookByIds(ids);
    }

    /**
     * 删除错题本信息
     * 
     * @param id 错题本主键
     * @return 结果
     */
    @Override
    public int deleteElUserBookById(String id)
    {
        return elUserBookMapper.deleteElUserBookById(id);
    }

    @Override
    public void addBook(String examId, String quId) {
        //查找已有的错题信息
        ElUserBook queryElUserBook = new ElUserBook();
        queryElUserBook.setUserId(SecurityUtils.getUserId());
        queryElUserBook.setExamId(examId);
        queryElUserBook.setQuId(quId);
        ElUserBook book =elUserBookMapper.selectElUserBookInfo(queryElUserBook);

        // 问题
        ElQuDto elQuDto = quService.selectElQuById(quId);
        if (book == null) {
            book = new ElUserBook();
            book.setExamId(examId);
            book.setUserId(SecurityUtils.getUserId());
            book.setTitle(elQuDto.getContent());
            book.setQuId(quId);
            book.setWrongCount(1);
            Integer maxSort = this.findMaxSort(examId);
            book.setSort(maxSort+1);
            book.setId(IdWorker.getIdStr());

            elUserBookMapper.insertElUserBook(book);
        } else {
            book.setWrongCount(book.getWrongCount()+1);
            elUserBookMapper.updateElUserBook(book);
        }

    }

    @Override
    public String findNext(String examId, String quId) {
        Integer sort = 999999;
        if(!StringUtils.isEmpty(quId)){
            ElUserBook queryElUserBook = new ElUserBook();
            queryElUserBook.setUserId(SecurityUtils.getUserId());
            queryElUserBook.setExamId(examId);
            queryElUserBook.setQuId(quId);
            ElUserBook last =elUserBookMapper.selectElUserBookInfo(queryElUserBook);

            if(last!=null){
                sort = last.getSort();
            }
        }

        ElUserBook queryElUserBookTwo = new ElUserBook();
        queryElUserBookTwo.setUserId(SecurityUtils.getUserId());
        queryElUserBookTwo.setExamId(examId);
        queryElUserBookTwo.setSort(sort);
        ElUserBook next =elUserBookMapper.selectElUserBookInfo(queryElUserBookTwo);
        if(next != null){
            return next.getQuId();
        }

        return null;
    }

    /**
     * 查找最大的排序
     * @param userId
     * @return
     */
    private Integer findMaxSort(String examId){
        ElUserBook queryElUserBook = new ElUserBook();
        queryElUserBook.setUserId(SecurityUtils.getUserId());
        queryElUserBook.setExamId(examId);
        ElUserBook book =elUserBookMapper.selectElUserBookInfo(queryElUserBook);

        if(book == null){
            return 0;
        }
        return book.getSort();
    }

}
