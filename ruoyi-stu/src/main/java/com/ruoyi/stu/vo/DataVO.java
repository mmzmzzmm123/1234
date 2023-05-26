package com.ruoyi.stu.vo;

import lombok.Data;

@Data
public class DataVO {
    /*
    本届毕业生总数,需要提交的材料,已经提交完成的企业实习材料,已经提交完成的毕业综合实践材料对应学生信息(id,姓名,班级)
     */
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
}
