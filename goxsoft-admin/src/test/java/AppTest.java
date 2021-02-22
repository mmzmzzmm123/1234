import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gox.GoxApplication;
import com.gox.basic.domain.FormJson;
import com.gox.basic.domain.form.FormDesignerData;
import com.gox.basic.domain.form.Props;
import com.gox.basic.service.IFormDesignerDataService;
import com.gox.basic.service.IFormJsonService;
import com.gox.basic.service.IPropsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author Naruto
 * @Description: a
 * @date 2021-02-2119:40
 * @Version: 1.0
 */
@SpringBootTest(classes = GoxApplication.class)
public class AppTest {
    @Autowired
    private IFormJsonService formJsonService;
    @Autowired
    private IFormDesignerDataService formDesignerDataService;
    @Test
    public void test(){
        List<FormJson> list = formJsonService.selectFormJsonList(new FormJson());
        for (FormJson formJson : list) {
            String j = formJson.getFormData();
            //FormDesignerData fdd = JSON.parseObject(j, FormDesignerData.class);
            JSONObject jsonObject = JSON.parseObject(j);
            FormDesignerData fdd = jsonObject.toJavaObject(FormDesignerData.class);
            formDesignerDataService.insertFormDesignerData(fdd);
        }
        //System.out.println(p);
    }
}
