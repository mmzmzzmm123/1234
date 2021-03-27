<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="项目标题" prop="projectTitle">
        <el-input
          v-model="queryParams.projectTitle"
          placeholder="请输入项目标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目类型" prop="projectType">
        <el-select v-model="queryParams.projectType" placeholder="项目类型" clearable size="small">
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="项目状态" prop="status">
        <el-select v-model="queryParams.projectStatus" placeholder="项目状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
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
          v-hasPermi="['system:notice:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:notice:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:notice:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="noticeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="projectNoticeId" />
      <el-table-column
        label="项目标题"
        align="center"
        prop="noticeTitle"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="项目类型"
        align="center"
        prop="projectType"
        :formatter="(row)=>formatProjectType[row.projectType]"
      />
      <el-table-column
        label="项目等级"
        align="center"
        prop="typeLevel"
        :formatter="(row)=>formatProjectLevel[row.typeLevel]"
      />
      <el-table-column
        label="状态"
        align="center"
        prop="status"
        :formatter="(row)=>formatProjectStatus[row.status]"
      />
      <el-table-column label="开始申请时间" align="center" prop="startTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束申请时间" align="center" prop="endTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:notice:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:notice:remove']"
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

    <!-- 添加或修改项目对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目标题" prop="noticeTitle">
              <el-input v-model="form.noticeTitle" placeholder="请输入项目标题" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目预算" prop="noticeBudget">
              <el-input v-model="form.noticeBudget" placeholder="请输入项目预算(单位万元)" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="项目类型" prop="projectType">
              <el-select v-model="form.projectType" placeholder="请选择">
                <el-option
                  v-for="dict in typeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="项目等级" prop="typeLevel">
              <el-select v-model="form.typeLevel" placeholder="请选择">
                <el-option
                  v-for="dict in levelOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="项目状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择">
                <el-option
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开始时间" prop="startTime">
              <el-date-picker
                v-model="form.startTime"
                type="date"
                value-format="yyyy-MM-dd hh:mm:ss"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结束时间" prop="endTime">
              <el-date-picker
                v-model="form.endTime"
                type="date"
                value-format="yyyy-MM-dd hh:mm:ss"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="24">
            <el-form-item label="项目状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col> -->
          <el-col :span="24">
            <el-form-item label="内容">
              <editor v-model="form.noticeContent" :min-height="192"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="上传附件" prop="field101fileList" required>
              <el-upload ref="field101fileList" :file-list="field101fileList" :action="field101Action"
                :before-upload="upload" enctype="multipart/form-data" :headers="token" :on-success="uploadSuccess">
                <el-button size="small" type="primary" icon="el-icon-upload">点击上传</el-button>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listApplyNotice, getApplyNotice, delApplyNotice, addApplyNotice, updateApplyNotice, exportNotice } from "@/api/project/index";
import Editor from '@/components/Editor';
import { getToken } from '@/utils/auth';

export default {
  name: "ProjectNotice",
  components: {
    Editor
  },
  data() {
    return {
      field101Action: 'http://10.179.25.97:8080/common/upload',
      field101fileList: [],
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
      // 项目表格数据
      noticeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 类型数据字典
      statusOptions: [],
      // 状态数据字典
      typeOptions: [],
      // 等级
      levelOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectTitle: undefined,
        projectStatus: undefined,
        projectType: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        noticeTitle: [
          { required: true, message: "项目标题不能为空", trigger: "blur" }
        ],
        noticeBudget: [
          { required: true, message: "项目预算不能为空", trigger: "blur" }
        ],
        projectType: [
          { required: true, message: "项目类型不能为空", trigger: "change" }
        ],
        typeLevel: [
          { required: true, message: "项目等级不能为空", trigger: "change" }
        ],
        status: [
          { required: true, message: "项目状态不能为空", trigger: "change" }
        ],
        startTime: [
          { required: true, message: "项目开始时间不能为空", trigger: "change" }
        ],
        endTime: [
          { required: true, message: "项目结束不能为空", trigger: "change" }
        ],
        field101fileList:[
          {required: true, message: "必须上传复件",}
        ]
      },
      //项目类型
      formatProjectType:{
        VerticalProject:'纵向项目',
        HorizontalProject:'横向项目',
        SchoolLevelProject:'校级项目',
      },
      //项目状态
      formatProjectStatus:{
        Announcement:'公告申请中',
        Expired:'已过期',
        Approved:'已立项'
      },
      //项目等级
      formatProjectLevel:{
        National:'国家级',
        Provincial:'省级',
        General:'一般'
      }
    };
  },
  computed: {
    // 计算属性的 getter
    token: function () {
      return {'Authorization': 'Bearer ' + getToken() }// 让每个请求携带自定义token 请根据实际情况自行修改
    }
  },
  created() {
    this.getList();
    // this.getDicts("sys_notice_status").then(response => {
    //   this.statusOptions = response.data;
    // });
    /*
    this.getDicts("sys_notice_type").then(response => {
      this.typeOptions = response.data;
      console.log('response.data:: ',response.data);
    });
    */
    this.typeOptions = [
      {dictLabel:'横向项目',dictValue:1},
      {dictLabel:'纵向项目',dictValue:0},
      {dictLabel:'校级项目',dictValue:2},
    ]
    this.statusOptions = [
      {dictLabel:'公告申请中',dictValue:0},
      {dictLabel:'已立项',dictValue:1},
      {dictLabel:'已过期',dictValue:2},
    ]
    this.levelOptions = [
      {dictLabel:'国家级',dictValue:0},
      {dictLabel:'省级',dictValue:1},
      {dictLabel:'一般',dictValue:2},
    ]
  },
  methods: {
    uploadSuccess(res, file, fileList){
      if(res.code == 200){
        this.msgSuccess("文件上传成功");
        // console.log("上传文件的URL",res.fileUrl);
        const resFileList = fileList.map(v=>({
          fileName:v.name,
          fileUrl:v.response.fileUrl
        }))
        this.form.annexName = resFileList.map(v=>v.fileName).join(',');
        this.form.annexAddress = resFileList.map(v=>v.fileUrl).join(',');
        this.form.field101fileList = resFileList;
      }
    },
    upload(file) {
      // console.log("上传的文件名",file.name);
    },
    /** 查询项目列表 */
    getList() {
      this.loading = true;
      listApplyNotice(this.queryParams).then(response => {
        this.noticeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 项目状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 项目状态字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.noticeType);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        noticeId: undefined,
        noticeTitle: undefined,
        noticeType: undefined,
        noticeContent: undefined,
        status: undefined,
        field101fileList:[]
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.noticeId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加项目";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const noticeId = row.projectNoticeId || this.ids
      // console.log('noticeId:: ',row);
      getApplyNotice(noticeId).then(response => {
        // console.log('res:: ',response.data)
        // response.data.projectType = this.formatProjectType[response.data.projectType];
        // response.data.projectType = this.formatProjectType[response.data.projectType];
        // response.data.projectType = this.formatProjectType[response.data.projectType];
        this.form = response.data;
        this.open = true;
        this.title = "修改项目";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // this.form.startTime = moment(this.form.startTime).format('YYYY-MM-DD');
          // this.form.endTime = moment(this.form.endTime).format('YYYY-MM-DD');
          if (this.form.noticeId != undefined) {
            updateApplyNotice(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addApplyNotice(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const noticeIds = row.noticeId || this.ids
      this.$confirm('是否确认删除项目编号为"' + noticeIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delApplyNotice(noticeIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    }
  }
};
</script>