package invest.lixinger.index.fundamental.VO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class fundamentalParam_MetricsListVO {
    List<String>metricsList=new ArrayList<>();
    public List<String> getMetricsList() {
        return metricsList;
    }
    public void setMetricsList(List<String> metricsList) {
        this.metricsList = metricsList;
    }
}
