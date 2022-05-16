package GYProject.mybatisTest.mapper;

import GYProject.mybatisTest.entity.testNoSpringVO;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

public interface testNoSpringMapper extends BaseMapper<testNoSpringVO> {

    Page<testNoSpringVO> findAllPage(Page<testNoSpringVO> page);
    List<testNoSpringVO> findAll();

}
