<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="姓名" prop=".stuName">
        <el-input
          v-model="queryParams.stuName"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="班级" prop="stuCls">
        <el-input
          v-model="queryParams.stuCls"
          placeholder="请输入班级"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="年级" prop="stuClsYear">
        <el-input
          v-model="queryParams.stuClsYear"
          placeholder="请输入年级"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="提交状态" prop="flag">
        <el-input
          v-model="queryParams.flag"
          placeholder="请输入提交状态"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="材料名" prop="materialName">
        <el-input
          v-model="queryParams.materialName"
          placeholder="请输入材料名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['stu:biye:add']"
        >新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="oneList">
      <el-table-column type="expand"  >
        <template slot-scope="props">
          <el-form   class="demo-table-expand">
              <el-form-item  v-for=" k in props.row.material" :key="k.stuMaterial.id" :label="k.stuMaterial.name"  label-width="135px">
                <el-input  type="hidden" name="materialId" :value="k.stuMaterial.id"></el-input>
                  <el-switch v-if="checkRole(['admin'])"
                    v-model="k.flag"
                    active-color="#13ce66"
                    inactive-color="#ff4949"
                    :active-value=0
                    :inactive-value=1
                    @change="changeSwitch($event,k)">
                  </el-switch>
                <el-switch v-if="checkRole(['common'])"
                           v-model="k.flag"
                           active-color="#13ce66"
                           inactive-color="#ff4949"
                           :active-value=0
                           :inactive-value=1
                           :disabled=true
                           @change="changeSwitch($event,k)">
                </el-switch>
                <el-form-item>
                  <el-upload
                    class="upload-demo"
                    drag
                    accept="image/jpeg,image/jpg,image/png"
                    :action="upload.url"
                    :headers="upload.headers"
                    :data="{'id':k.id,'flag':0}"
                    :on-success="uploadSuccess"
                  >
                    <el-image
                      style="width: 750px; height: 750px"
                      :src="k.url"
                    >
                    </el-image>
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                    <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
                  </el-upload>
                </el-form-item>
              </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="Id" align="center" prop="stuId"  />
      <el-table-column label="姓名" align="center" prop="stuName"  />
      <el-table-column label="班级" align="center" prop="stuCls" />
      <el-table-column label="年级" align="center" prop="stuClsYear" />
    </el-table>
  </div>
</template>

<script>
import {getList,updateMaterialFlag} from "@/api/stu/biye";
import {getToken} from "@/utils/auth";
import { checkPermi, checkRole } from "@/utils/permission"; // 权限判断函数

export default {
  name: "biye",
  data() {
    return {
    // 上传参数
      upload: {
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/stu/biye/upload",
        // 上传的文件列表
        fileList: []
      },

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
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        flag:0,
        stuName:'',
        stuCls:'',
        stuClsYear: '',
        materialName:''
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      oneList:[],
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false
    };
  },
  created() {
    this.getList();
  },
  methods: {

    checkRole,
    uploadSuccess(response, file, fileList){
      console.log(response)
      this.getList();
    },

    /*提交照片后修改材料状态*/
    changeSwitch(flag,row){
      const data = {
        "id":row.id,
        "flag":flag,
        "stuId":row.stuId,
        "materialId":row.materialId
      };
      updateMaterialFlag(data).then(response=>{
        if(response.code===200){
          this.$message({
            message: '提交成功',
            type: 'success'
          });
        }else{
          this.$message({
            message: '网络繁忙，提交失败',
            type: 'warning'
          });
        }
      })
    },

    /** 查询学生信息列表 */
    getList() {
      this.oneList = []
      this.loading = true;
      getList(this.queryParams).then(response => {
        for (const dataKey in response.data) {
          const key = JSON.parse(dataKey);
          key.material = response.data[dataKey]
          this.oneList.push(key);
        }
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        flag:0,
        stuInfo:{
          stuName:'',
          stuCls:'',
          stuClsYear: '',
        },
        StuMaterial:{
          name:''
        }
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    // /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加学生信息";
    },
  }
};
</script>
<style>

</style>
