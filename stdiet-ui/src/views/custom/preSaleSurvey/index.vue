<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="客户信息" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名或手机号"
          clearable
          size="small"
        />
      </el-form-item>
      <el-form-item label="日期范围" prop="time">
        
        <el-date-picker
                v-model="timeScope"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                :picker-options="timePickerOptions"
              >
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!--<el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['custom:preSaleSurvey:add']"
        >新增</el-button>
      </el-col>-->
      <!--<el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['custom:preSaleSurvey:edit']"
        >修改</el-button>
      </el-col>-->
      <el-col :span="1.5">
      <el-button
            icon="el-icon-share"
          type="primary"
          size="mini"
          class="copyBtn"
          :data-clipboard-text="copyValue"
          @click="handleCopy"
          title="点击复制问卷链接"
        >问卷链接</el-button>
        <el-popover
              placement="bottom"
              trigger="click"
              style="margin: 0 12px"
            >
              <VueQr :text="copyValue" :logoSrc="logo" :size="256" />
              <el-button
                slot="reference"
                size="mini"
                icon="el-icon-picture-outline"
                type="primary"
                title="点击查看二维码"
                >二维码
              </el-button>
            </el-popover>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['custom:preSaleSurvey:remove']"
        >删除</el-button>
      </el-col>
      <!--<el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['custom:preSaleSurvey:export']"
        >导出</el-button>
      </el-col>-->
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="preSaleSurveyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="姓名" align="center" prop="name" width="100"/>
      <el-table-column label="年龄" align="center" prop="age" width="60"/>
      <el-table-column label="性别" align="center" prop="sex" width="60">
          <template slot-scope="scope">
                {{scope.row.sex == 0 ? '男' : '女'}}
          </template>
      </el-table-column>
      <el-table-column label="身高(厘米)" align="center" prop="tall" width="100"/>
      <el-table-column label="体重(斤)" align="center" prop="weight" width="80"/>
      <el-table-column label="标准体重(斤)" align="center" prop="standardWeight" width="100">
          <template slot-scope="scope">
                {{(scope.row.standardWeight.toFixed(2))}}
          </template>
      </el-table-column>
      <el-table-column label="超重(斤)" align="center" prop="overWeight" width="80">
          <template slot-scope="scope">
                {{(scope.row.overWeight.toFixed(2))}}
          </template>
      </el-table-column>
      <el-table-column label="电话" align="center" prop="phone" width="110"/>
      <el-table-column label="特殊体征" align="center" prop="physicalSignsNameArray" >
           <template slot-scope="scope">
               <AutoHideInfo :data="scope.row.physicalSignsNameArray" :line="1"></AutoHideInfo>
           </template>
      </el-table-column>
      <el-table-column label="作息时间" align="center" prop="timeTableArray">
            <template slot-scope="scope">
                <div>{{scope.row.timeTableArray[0] != null && scope.row.timeTableArray[0] != '' ? ('睡觉：'+scope.row.timeTableArray[0]) : ''}}</div>
                <div>{{scope.row.timeTableArray[1] != null && scope.row.timeTableArray[1] != '' ? ('起床：'+scope.row.timeTableArray[1]) : ''}}</div>
            </template>
      </el-table-column>
      <el-table-column label="调理经历" align="center" prop="experience" >
          <template slot-scope="scope">
                <AutoHideMessage :data="scope.row.experience" :maxLength="10"></AutoHideMessage>
          </template>
      </el-table-column>
      <el-table-column label="职业" align="center" prop="occupation" >
          <template slot-scope="scope">
               <AutoHideMessage :data="scope.row.occupation" :maxLength="10"></AutoHideMessage>
           </template>
      </el-table-column>
      <el-table-column label="湿气数据" align="center" prop="moistureDataNameArray"  >
          <template slot-scope="scope">
               <AutoHideInfo :data="scope.row.moistureDataNameArray" :line="1"></AutoHideInfo>
           </template>
      </el-table-column>
      <el-table-column label="气血数据" align="center" prop="bloodDataNameArray"  >
          <template slot-scope="scope">
               <AutoHideInfo :data="scope.row.bloodDataNameArray" :line="1"></AutoHideInfo>
           </template>
      </el-table-column>
       <el-table-column label="创建时间" align="center" prop="createTime" width="140">
           <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!--<el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['custom:preSaleSurvey:edit']"
          >修改</el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:preSaleSurvey:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改简易问卷调查对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="身高(厘米)" prop="tall">
          <el-input v-model="form.tall" placeholder="请输入身高，厘米" />
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age" placeholder="请输入年龄" />
        </el-form-item>
        <el-form-item label="0男 1女" prop="sex">
          <el-select v-model="form.sex" placeholder="请选择0男 1女">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入电话" />
        </el-form-item>
        <el-form-item label="特殊体征ID" prop="physicalSignsId">
          <el-input v-model="form.physicalSignsId" placeholder="请输入特殊体征ID" />
        </el-form-item>
        <el-form-item label="体重" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入体重" />
        </el-form-item>
        <el-form-item label="其他体征" prop="otherPhysicalSigns">
          <el-input v-model="form.otherPhysicalSigns" placeholder="请输入其他体征" />
        </el-form-item>
        <el-form-item label="作息时间" prop="timeTable">
          <el-input v-model="form.timeTable" placeholder="请输入作息时间" />
        </el-form-item>
        <el-form-item label="减脂或慢病调理经历" prop="experience">
          <el-input v-model="form.experience" placeholder="请输入减脂或慢病调理经历" />
        </el-form-item>
        <el-form-item label="职业" prop="occupation">
          <el-input v-model="form.occupation" placeholder="请输入职业" />
        </el-form-item>
        <el-form-item label="气血数据" prop="bloodData">
          <el-input v-model="form.bloodData" placeholder="请输入气血数据" />
        </el-form-item>
        <el-form-item label="售前ID" prop="preSaleId">
          <el-input v-model="form.preSaleId" placeholder="请输入售前ID" />
        </el-form-item>
        <el-form-item label="湿气数据" prop="moistureData">
          <el-input v-model="form.moistureData" placeholder="请输入湿气数据" />
        </el-form-item>
        <el-form-item label="问卷类型 0售前简易问卷  1简易评估问卷" prop="questionType">
          <el-select v-model="form.questionType" placeholder="请选择问卷类型 0售前简易问卷  1简易评估问卷">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPreSaleSurvey, getPreSaleSurvey, delPreSaleSurvey, addPreSaleSurvey, updatePreSaleSurvey, exportPreSaleSurvey } from "@/api/custom/preSaleSurvey";
import Clipboard from "clipboard";
import AutoHideInfo from "@/components/AutoHideInfo";
import AutoHideMessage from "@/components/AutoHideMessage"
import VueQr from "vue-qr";
import dayjs from "dayjs";
const logo = require("@/assets/logo/logo_b.png");
export default {
  name: "PreSaleSurvey",
  data() {
    return {
        logo,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 简易问卷调查表格数据
      preSaleSurveyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        
      },
      simpleSignTypeOption: [],
      moistureDataExtendedOption:[],
      bloodDataExtendedOption:[],
      copyValue: "",
      timeScope: null,
      timePickerOptions: {
        disabledDate(time) {
          return time.getTime() > dayjs()
        },
      }
    };
  },
  created() {
      this.copyValue = window.location.origin.replace("manage", "sign") +"/simple/preSaleSurvey"
     this.getDicts("simple_sign_type").then((response) => {
      this.simpleSignTypeOption = response.data;
    });
     this.getDicts("moisture_data_extended").then((response) => {
      this.moistureDataExtendedOption = response.data;
    });
     this.getDicts("blood_data_extended").then((response) => {
      this.bloodDataExtendedOption = response.data;
      this.getList();
    });

  },
  components:{
      AutoHideInfo,AutoHideMessage,VueQr
  },
  methods: {
    /** 查询简易问卷调查列表 */
    getList() {
      this.loading = true;
      this.queryParams.beginTime = this.timeScope && this.timeScope.length > 0 ? this.timeScope[0] : null;
      this.queryParams.endTime = this.timeScope && this.timeScope.length > 0 ? this.timeScope[1] : null;
      listPreSaleSurvey(this.queryParams).then(response => {
        response.rows.forEach(item => {
            item.physicalSignsIdArray = item.physicalSignsId.split(",");
            item.timeTableArray = item.timeTable.split(",");
            item.bloodDataArray = item.bloodData.split(",");
            item.moistureDataArray = item.moistureData.split(",");
            item.physicalSignsNameArray = this.getPhysicalSignsArray(item);
            item.moistureDataNameArray = this.getMoistureDataNameArray(item);
            item.bloodDataNameArray = this.getBloodDataNameArray(item);
            if(item.standardWeight != null && item.standardWeight > 0){
                item.overWeight = item.weight - item.standardWeight;
            }else{
                item.overWeight = 0.0;
            }
        });
        this.preSaleSurveyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getPhysicalSignsArray(item){
        let array = [];
        item.physicalSignsIdArray.forEach((sign,index) => {
            array.push(this.getSignLabelByValue(sign, 1));
        });
        if(item.otherPhysicalSigns != null && item.otherPhysicalSigns != ""){
            item.otherPhysicalSigns = item.otherPhysicalSigns.replace("，", ",");
            array = array.concat(item.otherPhysicalSigns.split(","));
        }
        return array;
    },
    getMoistureDataNameArray(item){
        let array = [];
        item.moistureDataArray.forEach((moisture,index) => {
            array.push(this.getSignLabelByValue(moisture, 2));
        });
        return array;
    },
    getBloodDataNameArray(item){
        let array = [];
        item.bloodDataArray.forEach((blood,index) => {
            array.push(this.getSignLabelByValue(blood, 3));
        });
        return array;
    },
    getSignLabelByValue(value, type){
        if(type == 1){
            let signObj = this.simpleSignTypeOption.find((obj) => obj.dictValue === value);
            return signObj != null ? signObj.dictLabel : null;
        }else if(type == 2){
            let signObj = this.moistureDataExtendedOption.find((obj) => obj.dictValue === value);
            return signObj != null ? signObj.dictLabel : null;
        }else{
            let signObj = this.bloodDataExtendedOption.find((obj) => obj.dictValue === value);
            return signObj != null ? signObj.dictLabel : null;
        }
    },
    
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        customerKey: null,
        tall: null,
        age: null,
        sex: null,
        phone: null,
        physicalSignsId: null,
        weight: null,
        otherPhysicalSigns: null,
        timeTable: null,
        experience: null,
        occupation: null,
        bloodData: null,
        preSaleId: null,
        createTime: null,
        moistureData: null,
        updateTime: null,
        createBy: null,
        questionType: null,
        updateBy: null,
        delFlag: null
      };
      this.resetForm("form");
    },
    handleCopy() {
      new Clipboard(".copyBtn");
      this.$message({
        message: "拷贝成功",
        type: "success",
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.timeScope = null;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加简易问卷调查";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPreSaleSurvey(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改简易问卷调查";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePreSaleSurvey(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addPreSaleSurvey(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      let confirmTitle = "";
      if(row != undefined && row.id != undefined){
          confirmTitle = '是否确认删除名字为 '+row.name+' 的简易问卷信息?';
      }else{
          confirmTitle = '是否确认删除所勾选的 '+ids.length+' 条的简易问卷信息?';
      }
      this.$confirm(confirmTitle, "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delPreSaleSurvey(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有简易问卷调查数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportPreSaleSurvey(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>