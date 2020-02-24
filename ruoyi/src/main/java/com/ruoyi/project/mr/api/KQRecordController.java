package com.ruoyi.project.mr.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.mr.base.domain.BaseAttendanceRecord;
import com.ruoyi.project.mr.base.service.IBaseAttendanceRecordService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.Date;

/**
 * swagger 考勤信息方法
 *
 * @author ruoyi
 */
@Api(tags = "考勤记录")
@RestController
@RequestMapping("/mr/base")
public class KQRecordController extends BaseController {
    @Autowired
    private IBaseAttendanceRecordService baseAttendanceRecordService;
    // 令牌秘钥
    @Value("${token.apiSecret}")
    private String apiSecret;

    @ApiOperation(value = "考勤记录", notes = "添加考勤记录")
    @PostMapping("/save")
    @ResponseBody
    public AjaxResult save(@RequestHeader(value = "signature", required = false) String secret,@Validated @RequestBody RecordEntity record)
    {
        if (!apiSecret.equals(secret)) {
            return AjaxResult.error(400, MessageUtils.message("api.error.msg"));
        }else{
            record.setId(IdUtils.fastUUID());
            BaseAttendanceRecord baseAttendanceRecord = new BaseAttendanceRecord();
            baseAttendanceRecord.setId(record.getId());
            baseAttendanceRecord.setDeptName(record.getDept_name());
            baseAttendanceRecord.setUserName(record.getUser_name());
            baseAttendanceRecord.setPhonenumber(record.getPhonenumber());
            baseAttendanceRecord.setClasses(record.getClasses());
            baseAttendanceRecord.setWorkDate(record.getWork_date());
            baseAttendanceRecord.setPunchDate(record.getPunch_date());
            baseAttendanceRecord.setTimeQuantum(record.getTime_quantum());
            baseAttendanceRecord.setPunchType(record.getPunch_type());
            baseAttendanceRecord.setPunchTime(record.getPunch_time());
            baseAttendanceRecord.setStatus(record.getStatus());
            baseAttendanceRecord.setPicUrl(record.getPic_url());
            baseAttendanceRecord.setAddress(record.getAddress());
            baseAttendanceRecord.setCreateBy(record.getCreate_by());
            baseAttendanceRecord.setRemark(record.getRemark());
            int i = baseAttendanceRecordService.insertBaseAttendanceRecord(baseAttendanceRecord);
            if(i > 0){
                return AjaxResult.success(record);
            }else {
                return AjaxResult.error(400, MessageUtils.message("api.error.msg"));
            }
        }
    }

    @ApiOperation(value = "图片上传", notes = "添加图片上传地址")
    @PostMapping(value = "/upload", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    @ResponseBody
    public AjaxResult save(@RequestHeader(value = "signature", required = false) String secret,@RequestParam("avatarfile") MultipartFile file) throws IOException
    {
        if (!file.isEmpty())
        {
            String avatar = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file);
            return AjaxResult.success(avatar);
        }else{
            return AjaxResult.error(400, MessageUtils.message("api.error.msg"));
        }
    }
}

@ApiModel("考勤实体")
class RecordEntity
{
    @ApiModelProperty(value = "UUID", name = "id", hidden = true)
    private String id;

    @ApiModelProperty(value = "考勤组名称", name = "dept_name", required = true, example = "默认考勤组")
    @NotBlank(message = "默认考勤组不允许为空,请输入")
    private String dept_name;

    @ApiModelProperty(value = "姓名", name = "user_name", required = true, example = "张三")
    private String user_name;

    @ApiModelProperty(value = "考勤组名称", name = "phonenumber", required = true, example = "15677777777")
    @NotBlank(message = "手机号不允许为空,请输入")
    private String phonenumber;

    @ApiModelProperty(value = "班次", name = "classes", required = true, example = "1")
    private String classes;

    @ApiModelProperty(value = "工作日期", name = "work_date", required = true, example = "2019-02-13")
    private Date work_date;

    @ApiModelProperty(value = "打卡日期", name = "punch_date", required = true, example = "2019-02-13")
    private Date punch_date;

    @ApiModelProperty(value = "时间段", name = "time_quantum", required = true, example = "8:30-18:30")
    private String time_quantum;

    @ApiModelProperty(value = "打卡类型", name = "punch_type", required = true, example = "0")
    private String punch_type;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty(value = "打卡时间", name = "punch_time", required = true, example = "2020-02-13 15:25:55")
    private Date punch_time;

    @ApiModelProperty(value = "状态", name = "status", required = true, example = "0")
    private String status;

    @ApiModelProperty(value = "拍照路径", name = "pic_url", required = true, example = "111")
    private String pic_url;

    @ApiModelProperty(value = "地址", name = "address", required = true, example = "河南省焦作市示范区明仁药业")
    private String address;

    @ApiModelProperty(value = "创建人", name = "create_by", required = true, example = "张三")
    private String create_by;

    @ApiModelProperty(value = "备注", name = "remark", example = "疫情期间，在家打卡")
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public Date getWork_date() {
        return work_date;
    }

    public void setWork_date(Date work_date) {
        this.work_date = work_date;
    }

    public Date getPunch_date() {
        return punch_date;
    }

    public void setPunch_date(Date punch_date) {
        this.punch_date = punch_date;
    }

    public String getTime_quantum() {
        return time_quantum;
    }

    public void setTime_quantum(String time_quantum) {
        this.time_quantum = time_quantum;
    }

    public String getPunch_type() {
        return punch_type;
    }

    public void setPunch_type(String punch_type) {
        this.punch_type = punch_type;
    }

    public Date getPunch_time() {
        return punch_time;
    }

    public void setPunch_time(Date punch_time) {
        this.punch_time = punch_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public RecordEntity()
    {

    }

    public RecordEntity(String id, @NotBlank(message = "默认考勤组不允许为空,请输入") String dept_name, String user_name, @NotBlank(message = "手机号不允许为空,请输入") String phonenumber, String classes, Date work_date, Date punch_date, String time_quantum, String punch_type, Date punch_time, String status, String pic_url, String address, String create_by, String remark) {
        this.id = id;
        this.dept_name = dept_name;
        this.user_name = user_name;
        this.phonenumber = phonenumber;
        this.classes = classes;
        this.work_date = work_date;
        this.punch_date = punch_date;
        this.time_quantum = time_quantum;
        this.punch_type = punch_type;
        this.punch_time = punch_time;
        this.status = status;
        this.pic_url = pic_url;
        this.address = address;
        this.create_by = create_by;
        this.remark = remark;
    }
}
