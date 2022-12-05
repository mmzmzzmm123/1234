package invest.other.huice;

import GYProject.mybatisTest.entity.testNoSpringVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import invest.other.huice.eneity.hsagVO;
import invest.other.huice.mapper.huiceMapper;
import mybatisNoSpringUtils.mybatisNoSpringUtils;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// 在某个时间范围的收益率
public class sjfwsy extends mybatisNoSpringUtils {
    public sjfwsy() throws FileNotFoundException {
    }
    @Test
    public void test() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse("2020-05-07");
        Date endDate = sdf.parse("2022-08-14");

        huiceMapper mapper = session.getMapper(huiceMapper.class);
        hsagVO vo = mapper.nearestDateInDB();
        Date date = vo.getSj();
    }
}
