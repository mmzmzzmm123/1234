import com.ruoyi.RuoYiApplication;
import com.ruoyi.system.service.ISysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zdw
 * @Date: 2022/5/24 14:34
 * @Description:
 */
@SpringBootTest(classes = RuoYiApplication.class)
public class UserTest {

    @Autowired
    private ISysUserService sysUserService;

    @Test
    public  void testQuery(){
        sysUserService.selectUserList(null);
    }
}
