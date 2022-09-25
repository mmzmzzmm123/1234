package GYProject.mybatisTest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("tbl_employee")
@Data
public class testVO {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "last_name")
    private String lastName;
    @TableField(value = "email")
    private String email;
    @TableField(value = "gender")
    private Integer gender;
    @TableField(value = "age")
    private Integer age;
}
