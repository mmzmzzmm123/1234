package com.ruoyi.note.domain;

//import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Auther: Wang
 * @Date: 2020/10/08 19:07
 * 功能描述:
 */
//@Document(collection = "NoteContent")
public class NoteContentMgDb implements Serializable {

    @Id
    private Long id;

    private String NoteContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoteContent() {
        return NoteContent;
    }

    public void setNoteContent(String noteContent) {
        NoteContent = noteContent;
    }
}
