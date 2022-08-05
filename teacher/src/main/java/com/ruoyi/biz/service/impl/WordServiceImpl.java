package com.ruoyi.biz.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.mapper.WordMapper;
import com.ruoyi.biz.domain.Word;
import com.ruoyi.biz.service.IWordService;

/**
 * 作业安排Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-08-05
 */
@Service
public class WordServiceImpl implements IWordService 
{
    @Autowired
    private WordMapper wordMapper;

    /**
     * 查询作业安排
     * 
     * @param id 作业安排主键
     * @return 作业安排
     */
    @Override
    public Word selectWordById(Long id)
    {
        return wordMapper.selectWordById(id);
    }

    /**
     * 查询作业安排列表
     * 
     * @param word 作业安排
     * @return 作业安排
     */
    @Override
    public List<Word> selectWordList(Word word)
    {
        return wordMapper.selectWordList(word);
    }

    /**
     * 新增作业安排
     * 
     * @param word 作业安排
     * @return 结果
     */
    @Override
    public int insertWord(Word word)
    {
        return wordMapper.insertWord(word);
    }

    /**
     * 修改作业安排
     * 
     * @param word 作业安排
     * @return 结果
     */
    @Override
    public int updateWord(Word word)
    {
        return wordMapper.updateWord(word);
    }

    /**
     * 批量删除作业安排
     * 
     * @param ids 需要删除的作业安排主键
     * @return 结果
     */
    @Override
    public int deleteWordByIds(Long[] ids)
    {
        return wordMapper.deleteWordByIds(ids);
    }

    /**
     * 删除作业安排信息
     * 
     * @param id 作业安排主键
     * @return 结果
     */
    @Override
    public int deleteWordById(Long id)
    {
        return wordMapper.deleteWordById(id);
    }
}
