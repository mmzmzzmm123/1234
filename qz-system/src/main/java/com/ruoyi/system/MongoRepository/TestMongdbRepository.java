package com.ruoyi.system.MongoRepository;

import com.ruoyi.system.mongDomain.TestMongdb;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public  interface TestMongdbRepository extends MongoRepository<TestMongdb, String> {

    TestMongdb findAllByName(String name);

    @Override
    List<TestMongdb> findAll();

    @Override
    TestMongdb save(TestMongdb s);

    @Override
    void deleteById(String s);


}
