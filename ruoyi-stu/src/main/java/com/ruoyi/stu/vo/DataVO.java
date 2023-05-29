package com.ruoyi.stu.vo;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.stu.domain.StuCls;
import com.ruoyi.stu.domain.StuClsCountData;
import com.ruoyi.stu.domain.StuMaterial;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DataVO extends BaseEntity {
    /*
    本届毕业生总数,需要提交的材料,已经提交完成的企业实习材料,已经提交完成的毕业综合实践材料对应学生信息(id,姓名,班级)
     */
    private String level;
    /*
    学生总数
     */
    private Integer stuNum;
    /*
    已经提交的企业实习材料数量
     */
    private Integer finshedEnMaterial;
    /*
    全部需要提交的企业实习材料数量
     */
    private Integer totalEnMaterial;
    /*
    班级信息-人数
     */
    private List<StuCls> clsInfo;
   /*
   不同班级不同材料数量
    */
    private List<StuClsCountData> clsCountData;
    /*
    不同班级不同材料数量
     */
    private List<StuMaterial> stuMaterials;
}
