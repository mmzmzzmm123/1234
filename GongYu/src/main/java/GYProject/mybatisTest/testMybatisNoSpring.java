package GYProject.mybatisTest;

import GYProject.mybatisTest.entity.testNoSpringVO;
import GYProject.mybatisTest.mapper.testNoSpringMapper;
import mybatisNoSpringUtils.mybatisNoSpringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class testMybatisNoSpring extends mybatisNoSpringUtils {
    public testMybatisNoSpring() throws FileNotFoundException {
    }

    @Test
    public void testSelectPage() {
        testNoSpringMapper mapper = session.getMapper(testNoSpringMapper.class);
        // current表示第x页，size表每页多少数据
        Page<testNoSpringVO> pageResult = mapper.selectPage(new Page<>(2, 3), new QueryWrapper<>());
        System.out.println("数据总条数：" + pageResult.getTotal());
        System.out.println("总页数：" + pageResult.getPages());
        System.out.println("查询到的数据：" + pageResult.getRecords());
    }

    @Test
    public void testDelete() {
        testNoSpringMapper mapper = session.getMapper(testNoSpringMapper.class);
        int result = mapper.deleteById(5);
        System.out.println(result);
        this.testSelectList();
    }

    @Test
    public void testInsert() {
        testNoSpringVO testvo = new testNoSpringVO();
        testvo.setAge(12);
        testvo.setEmail("asdfasdfa@qq.com");
        testvo.setId(4);
        testNoSpringMapper mapper = session.getMapper(testNoSpringMapper.class);
        mapper.insert(testvo);
        System.out.println(testvo);
    }

    @Test
    public void testUpdate() {
        testNoSpringMapper mapper = session.getMapper(testNoSpringMapper.class);
        testNoSpringVO vo = mapper.selectById(5);
        vo.setAge(50);
        vo.setGender(0);
        mapper.updateById(vo);
        System.out.println(vo);
        System.out.println("--------------");
        UpdateWrapper<testNoSpringVO> updateWrapper = new UpdateWrapper<>();
        //如果找到last_name==asdf，那么就把age设为35
        updateWrapper.eq("last_name", "asdf").set("age", 35);
        Integer rows = mapper.update(null, updateWrapper);
        System.out.println("更新的数量==" + rows);
    }

    @Test
    public void testSelectList() {
        testNoSpringMapper mapper = session.getMapper(testNoSpringMapper.class);
        System.out.println("----查询所有----");
        List<testNoSpringVO> list = mapper.selectList(new QueryWrapper<>());
        System.out.println(list);
        System.out.println("-----按list进行查询---");
        List<testNoSpringVO> listBatch = mapper.selectBatchIds(Arrays.asList(2, 3, 4));
        listBatch.forEach(System.out::println);
        System.out.println("----查询记录总数----");
        long count = mapper.selectCount(null);
        System.out.println("查询到的总记录条数==" + count);

    }


    @Test
    public void testFindAllPage() {
        testNoSpringMapper mapper = session.getMapper(testNoSpringMapper.class);
        Page<testNoSpringVO> pageResult = mapper.findAllPage(new Page<>(1, 3));
        System.out.println(pageResult.getTotal());
        System.out.println(pageResult.getPages());
        System.out.println(pageResult.getRecords());
    }
    @Test
    public void testFindAll() {
        testNoSpringMapper mapper = session.getMapper(testNoSpringMapper.class);
        List<testNoSpringVO> pageResult = mapper.findAll();
        pageResult.forEach(System.out::println);
    }
}
