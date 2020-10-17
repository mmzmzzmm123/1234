package com.ruoyi.note.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import cn.hutool.core.date.DateUtil;
import com.github.wujun234.uid.UidGenerator;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.note.domain.NmNoteContent;
import com.ruoyi.note.domain.NoteContentMgDb;
import com.ruoyi.note.service.INmNoteContentService;
import com.ruoyi.note.service.INoteRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;
import com.ruoyi.note.mapper.NmNoteMapper;
import com.ruoyi.note.domain.NmNote;
import com.ruoyi.note.service.INmNoteService;

import javax.annotation.Resource;

/**
 * 便签管理Service业务层处理
 *
 * @author wang
 * @date 2020-09-12
 */
@Service
public class NmNoteServiceImpl implements INmNoteService {
    @Autowired
    private NmNoteMapper nmNoteMapper;

    @Resource
    private UidGenerator defaultUidGenerator;

    @Autowired
    private INmNoteContentService nmNoteContentService;

    @Autowired
    private INoteRepositoryService noteRepositoryService;

    @Autowired
    private RedisCache redisCache;


    /**
     * 查询便签管理
     *
     * @param noteId 便签管理ID
     * @return 便签管理
     */
    @Override
    public NmNote selectNmNoteById(Long noteId) {
        return nmNoteMapper.selectNmNoteById(noteId);
    }

    /**
     * 查询便签管理列表
     *
     * @param nmNote 便签管理
     * @return 便签管理
     */
    @Override
    public List<NmNote> selectNmNoteList(NmNote nmNote) {
        return nmNoteMapper.selectNmNoteList(nmNote);
    }

    /**
     * 新增便签管理
     *
     * @param nmNote 便签管理
     * @return 结果
     */
    @Override
    public int insertNmNote(NmNote nmNote) {
        nmNote.setCreateTime(DateUtils.getNowDate());
        nmNote.setTitle(DateUtil.now());
        nmNote.setTiymceUeditor(defaultUidGenerator.getUID());
        //创建文章>>mongodb
        redisCache.setCacheObject(Constants.NM_NOTE_CONTENT+nmNote.getTiymceUeditor(),"请开始你的创作!");
        return nmNoteMapper.insertSelective(nmNote);
    }

    /**
     * 修改便签管理
     *
     * @param nmNote 便签管理
     * @return 结果
     */
    @Override
    public int updateNmNote(NmNote nmNote) {
        nmNote.setUpdateTime(DateUtils.getNowDate());
        return nmNoteMapper.updateNmNote(nmNote);
    }

    /**
     * 批量删除便签管理
     *
     * @param noteIds 需要删除的便签管理ID
     * @return 结果
     */
    @Override
    public int deleteNmNoteByIds(Long[] noteIds) {
        return nmNoteMapper.deleteNmNoteByIds(noteIds);
    }

    /**
     * 删除便签管理信息
     *
     * @param noteId 便签管理ID
     * @return 结果
     */
    @Override
    public int deleteNmNoteById(Long noteId) {
        return nmNoteMapper.deleteNmNoteById(noteId);
    }

    /**
     * 用户根据ID查询便签
     *
     * @param noteId 便签管理ID
     * @return 便签管理
     */
    @Override
    public NmNote selectNmNoteuserById(Long noteId, Long userID) {
        NmNote nmNote = new NmNote();
        nmNote.setNoteId(noteId);
        nmNote.setUserId(userID);
        NmNote isnmNote1 = nmNoteMapper.selectOne(nmNote);
        //查询对应的文章数据
        //1.查redis缓存
        String noteContent = redisCache.getCacheObject(Constants.NM_NOTE_CONTENT + isnmNote1.getTiymceUeditor());
        if (noteContent != null && !"".equals(noteContent)) {
            isnmNote1.setUeditorContent(noteContent);
        } else {
        // 2不存在就走mogodb
            List<NoteContentMgDb> NoteContentMgDb = noteRepositoryService.findById(isnmNote1.getTiymceUeditor() + "");
            isnmNote1.setUeditorContent(NoteContentMgDb.get(0).getNoteContent());
        }
        return isnmNote1;
    }

    /**
     * 实时修改数据 保存到缓存中
     *
     * @param nmNote
     * @return int
     */
    @Override
    public int userUpdateNote(NmNote nmNote) {
        //储存到redis中  只缓存频繁操作的文章内容
        redisCache.setCacheObject(Constants.NM_NOTE_CONTENT+nmNote.getTiymceUeditor(),nmNote.getUeditorContent());
        return  1;
    }

    /**
     * redis的文章 转移更新到MongoDb
     *
     * @param
     * @return
     */
    @Override
    public void redisToMongonDB() {
        //模糊查询 获取所有的key
        Collection<String> listNote= redisCache.keys(Constants.NM_NOTE_CONTENT+"*");
        for(String str:listNote){
            //文章UUID
            String mgDbContentUUID=str.replace(Constants.NM_NOTE_CONTENT,"");
            //文章
            String redisContent= redisCache.getCacheObject(str);
            //查询mongoDb  存在就修改 不存在就新增
            List<NoteContentMgDb> listMgDbContent =  noteRepositoryService.findById(mgDbContentUUID);
            if (listMgDbContent!=null&&!listMgDbContent.isEmpty()){
                //修改
                NoteContentMgDb noteContentMgDb = new NoteContentMgDb();
                noteContentMgDb.setId(Long.valueOf(mgDbContentUUID));
                noteContentMgDb.setNoteContent(redisContent);
                noteRepositoryService.update(noteContentMgDb);
            }else {
                //新增
                NoteContentMgDb noteContentMgDb = new NoteContentMgDb();
                noteContentMgDb.setId(Long.valueOf(mgDbContentUUID));
                noteContentMgDb.setNoteContent(redisContent);
                noteRepositoryService.save(noteContentMgDb);
            }
            //删除对应缓存
            redisCache.deleteObject(str);
        }

    }


}
