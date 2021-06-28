import com.ruoyi.common.utils.StringUtils;

/**
 * @author Fy
 * @description：TODO
 * @date 2021/6/22 11:21
 */
public class StringUtilsTest {
    public static void main(String[] args) {
        String msg = StringUtils.format("请求访问：{}，认证失败，无法访问系统资源", "www.baidu.com");
        System.out.println(msg);
    }
}
