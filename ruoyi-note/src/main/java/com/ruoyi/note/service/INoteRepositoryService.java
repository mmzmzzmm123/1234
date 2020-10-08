package com.ruoyi.note.service;

import com.ruoyi.note.domain.NoteContentMgDb;

import java.util.List;

/**
 * @Auther: Wang
 * @Date: 2020/10/08 19:01
 * 功能描述:
 */
public interface INoteRepositoryService {

    void save(NoteContentMgDb noteContentMgDb);

    void update(NoteContentMgDb noteContentMgDb);

    List<NoteContentMgDb> findAll();

    void delete(Integer id);
}
