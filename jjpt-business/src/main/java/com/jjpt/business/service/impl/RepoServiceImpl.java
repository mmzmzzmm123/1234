package com.jjpt.business.service.impl;




import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjpt.business.mapper.RepoMapper;
import com.jjpt.business.modules.dto.PagingReqDTO;
import com.jjpt.business.modules.dto.RepoDTO;
import com.jjpt.business.modules.dto.RepoReqDTO;
import com.jjpt.business.modules.dto.RepoRespDTO;
import com.jjpt.business.modules.entity.Repo;
import com.jjpt.business.service.RepoService;
import com.jjpt.business.utils.BeanMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <p>
* 语言设置 服务实现类
* </p>
*
* @author 聪明笨狗
* @since 2020-05-25 13:23
*/
@Service
public class RepoServiceImpl extends ServiceImpl<RepoMapper, Repo> implements RepoService {

    @Override
    public Page<RepoRespDTO> paging(PagingReqDTO<RepoReqDTO> reqDTO) {
       Page<RepoRespDTO> paging = baseMapper.paging(reqDTO.toPage(), reqDTO.getParams());


        return baseMapper.paging(reqDTO.toPage(), reqDTO.getParams());
     }

    @Override
    public void save(RepoDTO reqDTO) {

        //复制参数
        Repo entity = new Repo();
        BeanMapper.copy(reqDTO, entity);
        this.saveOrUpdate(entity);
    }
}
