package com.ruoyi.system.mongDomain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.util.List;
@Document
public class TestMongdb implements Serializable {
    @Id
    private String id;
    private String testMongdbId;
    private String name;
    private String age;
    private Long size;
    private List list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTestMongdbId() {
        return testMongdbId;
    }

    public void setTestMongdbId(String testMongdbId) {
        this.testMongdbId = testMongdbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
