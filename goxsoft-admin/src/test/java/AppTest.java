import com.gox.GoxApplication;
import com.gox.basic.domain.form.Props;
import com.gox.basic.service.IPropsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Naruto
 * @Description: a
 * @date 2021-02-2119:40
 * @Version: 1.0
 */
@SpringBootTest(classes = GoxApplication.class)
public class AppTest {
    @Autowired
    private IPropsService propsService;
    @Test
    public void test(){
        Props p = propsService.selectPropsById(2364139667324928L);
        System.out.println(p);
    }
}
