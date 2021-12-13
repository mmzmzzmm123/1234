<template>
  <div class="app-container">
<!--    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="用户ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="全景ID" prop="panoramaId">
        <el-input
          v-model="queryParams.panoramaId"
          placeholder="请输入全景ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="大屏ID" prop="visualId">
        <el-input
          v-model="queryParams.visualId"
          placeholder="请输入大屏ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form> -->

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:repo:add']"
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
          v-hasPermi="['system:repo:edit']"
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
          v-hasPermi="['system:repo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['system:repo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="repoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="项目名称" align="center" prop="title" />
      <!-- <el-table-column label="用户ID" align="center" prop="userId" /> -->
      <el-table-column label="用户名" align="center" prop="userName" />

      <!-- <el-table-column label="有效时间" align="center" prop="avaTime" /> -->
      <el-table-column label="大屏ID" align="center" prop="visualId" />
      <el-table-column label="大屏标题" align="center" prop="visualName" />
      <el-table-column label="全景ID" align="center" prop="panoramaId" />
      <el-table-column label="全景名称" align="center" prop="panName" />
      <el-table-column label="描述信息" align="center" prop="descri" />
      <el-table-column label="链接有效开始时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d} {h}-{i}-{s}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="链接有效结束时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d} {h}-{i}-{s}') }}</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="删除标志" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column> -->
     <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"

            @click="jump(scope.row)"

          >跳转</el-button>
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

    <!-- 添加或修改链接仓库对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="200px">
        <el-form-item label="项目名称" prop="title">
          <el-input v-model="form.title" placeholder="请输入项目名称" />
        </el-form-item>

        <el-form-item label="所属用户" prop="userId" :rules="[{ required: true, message: '请选择用户', trigger: 'blur' }]">
          <!-- <el-input v-model="form.userId" placeholder="请选择所属用户" /> -->
          <el-select v-model="form.userId" placeholder="请选择所属用户" >
              <el-option
                v-for="item in users"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
        </el-form-item>

        <el-form-item label="大屏" prop="panoramaId" :rules="[{ required: true, message: '请选择大屏', trigger: 'blur' }]">
          <el-select v-model="form.visualId" placeholder="请选择">
              <el-option
                v-for="item in visualIds"
                :key="item.id"
                :label="`${item.name}\t【id:${item.id}】`"
                :value="item.id">
              </el-option>
            </el-select>
        </el-form-item>

        <el-form-item label="全景" prop="visualId" :rules="[{ required: true, message: '请选择全景', trigger: 'blur' }]">
          <el-select v-model="form.panoramaId" placeholder="请选择">
                <el-option
                  v-for="item in panIds"
                  :key="item.id"
                  :label="`${item.name}\t【id:${item.id}】`"
                  :value="item.id">
                </el-option>
              </el-select>
          </el-form-item>
        </el-form-item>

        <el-form-item label="描述信息" prop="descri">
          <el-input v-model="form.descri" placeholder="请输入描述信息" />
        </el-form-item>

        <el-form-item label="链接有效时间范围" prop="startTime" :rules="[{ required: true, message: '请选择时间范围', trigger: 'blur' }]">
          <el-date-picker
                v-model="timerange"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期">
              </el-date-picker>
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
import { listRepo, getRepo, delRepo, addRepo, updateRepo, exportRepo,getPanIds,getVisualId,getUsers } from "@/api/system/repo";
import {checkExpire} from '@/api/system/repo'
export default {
  name: "Repo",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
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
      // 链接仓库表格数据
      repoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        descri: null,
        panoramaId: null,
        startTime: null,
        visualId: null,
        endTime: null,
      },
      // 表单参数
      form: {},
      timerange:[],
      visualIds:[],
      panIds:[],
      users:[],
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询链接仓库列表 */
    getList() {
      this.loading = true;
      listRepo(this.queryParams).then(response => {
        this.repoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    jump(row){
      checkExpire(row.visualId,row.panoramaId).then(res=>{
          console.log('权限正常');
          let routeUrl = this.$router.resolve({
            path: '/view/' + row.visualId+"/"+row.panoramaId
          })
          window.open(routeUrl.href, '_blank');
        }).catch(err=>{
          alert('您的权限已过期')
        }

      )
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
        title: null,
        userId: null,
        descri: null,
        avaTime: null,
        panoramaId: null,
        startTime: null,
        visualId: null,
        endTime: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,

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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    getVisualsAndPans(){
      getVisualId().then(res=>{
        // console.log(res);
        this.visualIds = res
      })
      getPanIds().then(res=>{
        // console.log(res);
        this.panIds = res
      })
      getUsers().then(res=>{
        this.users = res
      })
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加链接仓库";
      this.getVisualsAndPans()
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRepo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改链接仓库";
      });
    },
    /** 提交按钮 */
    formatDateTime(date) {
                    var y = date.getFullYear();
                    var m = date.getMonth() + 1;
                    m = m < 10 ? ('0' + m) : m;
                    var d = date.getDate();
                    d = d < 10 ? ('0' + d) : d;
                    var h = date.getHours();
                    h=h < 10 ? ('0' + h) : h;
                    var minute = date.getMinutes();
                    minute = minute < 10 ? ('0' + minute) : minute;
                    var second=date.getSeconds();
                    second=second < 10 ? ('0' + second) : second;
                    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
                },
    submitForm() {
      console.log(this.timerange[0]);
      this.form.startTime = this.formatDateTime(this.timerange[0])
      this.form.endTime = this.formatDateTime(this.timerange[1])
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRepo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRepo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除链接仓库编号为"' + ids + '"的数据项？').then(function() {
        return delRepo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal.confirm('是否确认导出所有链接仓库数据项？').then(() => {
        this.exportLoading = true;
        return exportRepo(queryParams);
      }).then(response => {
        this.$download.name(response.msg);
        this.exportLoading = false;
      }).catch(() => {});
    }
  }
};
</script>
