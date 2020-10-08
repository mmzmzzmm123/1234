package com.ruoyi.note.service.impl;


import com.ruoyi.note.domain.NoteContentMgDb;
import com.ruoyi.note.service.INoteRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  @author: wanghao
 *  @Date: 2020/10/08 19:02
 *  @Description:
 */
@Service
public class NoteRepositoryServiceImpl implements INoteRepositoryService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 新增信息
     * @param student
     */
    @Override
    public void save(NoteContentMgDb student) {
        mongoTemplate.save(student);
    }

    /**
     * 修改信息
     * @param noteContentMgDb
     */
    @Override
    public void update(NoteContentMgDb noteContentMgDb) {
        //修改的条件
        Query query = new Query(Criteria.where("id").is(noteContentMgDb.getId()));

        //修改的内容
        Update update = new Update();
        update.set("name", noteContentMgDb.getNoteContent());

        mongoTemplate.updateFirst(query,update, NoteContentMgDb.class);
    }

    /**
     * 查询所有信息
     * @return
     */
    @Override
    public List<NoteContentMgDb> findAll() {
        return mongoTemplate.findAll(NoteContentMgDb.class);
    }

    /**
     * 根据id查询所有信息
     * @param id
     */
    @Override
    public void delete(Integer id) {
        NoteContentMgDb byId = mongoTemplate.findById(1, NoteContentMgDb.class);
        mongoTemplate.remove(byId);
    }
}
