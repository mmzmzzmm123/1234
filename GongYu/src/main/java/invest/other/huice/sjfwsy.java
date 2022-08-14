package invest.other.huice;

import invest.other.huice.mapper.huiceMapper;
import mybatisNoSpringUtils.mybatisNoSpringUtils;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//在某个时间范围的收益率
public class sjfwsy extends mybatisNoSpringUtils {
    public sjfwsy() throws FileNotFoundException {
    }
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse("2020-05-07");
        Date endDate = sdf.parse("2022-08-14");
        huiceMapper mapper = session.getMapper(huiceMapper.class);

    }
}
