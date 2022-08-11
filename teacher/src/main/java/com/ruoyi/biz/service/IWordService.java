package com.ruoyi.biz.service;

import java.util.List;
import com.ruoyi.biz.domain.Word;

/**
 * 作业安排Service接口
 * 
 * @author ruoyi
 * @date 2022-08-05
 */
public interface IWordService 
{
    /**
     * 查询作业安排
     * 
     * @param id 作业安排主键
     * @return 作业安排
     */
    public Word selectWordById(Long id);

    /**
     * 查询作业安排列表
     * 
     * @param word 作业安排
     * @return 作业安排集合
     */
    public List<Word> selectWordList(Word word);

    /**
     * 新增作业安排
     * 
     * @param word 作业安排
     * @return 结果
     */
    public int insertWord(Word word);

    /**
     * 修改作业安排
     * 
     * @param word 作业安排
     * @return 结果
     */
    public int updateWord(Word word);

    /**
     * 批量删除作业安排
     * 
     * @param ids 需要删除的作业安排主键集合
     * @return 结果
     */
    public int deleteWordByIds(Long[] ids);

    /**
     * 删除作业安排信息
     * 
     * @param id 作业安排主键
     * @return 结果
     */
    public int deleteWordById(Long id);
}
