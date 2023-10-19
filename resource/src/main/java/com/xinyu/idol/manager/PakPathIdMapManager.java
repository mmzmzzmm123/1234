package com.xinyu.idol.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinyu.idol.mapper.PakPathIdMapMapper;
import com.xinyu.idol.pojo.entity.PakPathIdMapEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PakPathIdMapManager {

    @Autowired
    private PakPathIdMapMapper pakPathIdMapMapper;

    public PakPathIdMapEntity getByPath(PakPathIdMapEntity pakPathIdMapEntity){

        PakPathIdMapEntity pakPathIdMap = pakPathIdMapMapper.selectOne(new QueryWrapper<PakPathIdMapEntity>().eq("path", pakPathIdMapEntity.getPath()));
        return pakPathIdMap;
    }

    public void insert(PakPathIdMapEntity pakPathIdMapEntity){
        pakPathIdMapMapper.insert(pakPathIdMapEntity);

    }


}
