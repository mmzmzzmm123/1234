package com.ruoyi.web.test.controller;

import com.ruoyi.note.domain.NoteContentMgDb;
import com.ruoyi.note.service.INoteRepositoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Auther: Wang
 * @Date: 2020/10/08 19:11
 * 功能描述:
 */
public class MongdbApplicationTests  extends BaseSpringBootTest{
    @Autowired
    private INoteRepositoryService noteRepositoryService;

    /**
     * 查询所有信息
     */
    @Test
    public void findAll() {
        List<NoteContentMgDb> all = noteRepositoryService.findAll();
        System.out.println(all.size());
    }

    /**
     * 新增信息
     */
    @Test
    public void save() {
        NoteContentMgDb noteContentMgDb = new NoteContentMgDb();
        noteContentMgDb.setId(19L);
        noteContentMgDb.setNoteContent("宋人头");
        noteRepositoryService.save(noteContentMgDb);
    }


    /**
     * 修改信息
     */
    @Test
    public void update() {
        NoteContentMgDb noteContentMgDb = new NoteContentMgDb();
        noteContentMgDb.setId(2L);
        noteContentMgDb.setNoteContent("吴很帅");
        noteRepositoryService.update(noteContentMgDb);
    }

    /**
     * 删除信息
     */
    @Test
    public void delete() {
        noteRepositoryService.delete(3);
    }
}
