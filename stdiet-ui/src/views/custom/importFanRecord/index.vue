<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="导粉日期" prop="importFanDate">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.importFanDate"
          type="date"
          value-format="yyyy-MM-dd" 
          placeholder="选择导粉日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="账号渠道" prop="importFanChannel">
          <el-select
              v-model="queryParams.importFanChannel"
              placeholder="请选择账号渠道"
              clearable
              filterable
              size="small"
            >
              <el-option
                v-for="dict in fanChanneloptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
        </el-form-item>
      <!--<el-form-item label="进粉直播间" prop="importFanLive">
        <el-input
          v-model="queryParams.importFanLive"
          placeholder="请输入进粉直播间"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="showDialog(null)"
          v-hasPermi="['custom:importFanRecord:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['custom:importFanRecord:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['custom:importFanRecord:remove']"
        >删除</el-button>
      </el-col>
      <!--<el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['custom:importFanRecord:export']"
        >导出</el-button>
      </el-col>-->
      <el-col :span="1.5">
      
        </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="importFanRecordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="导粉日期" align="center" prop="importFanDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.importFanDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="进粉账号渠道" align="center" prop="importFanChannelName" />
      <el-table-column label="所属直播间" align="center" prop="liveRoomName" >
        <template slot-scope="scope">
            {{(scope.row.liveRoomName != null || scope.row.liveNutritionistName != null) ? (scope.row.liveRoomName + "—" + scope.row.liveNutritionistName) : ""}}
        </template>
      </el-table-column>
      <el-table-column label="导粉总数" align="center" prop="wxAccountList" :formatter="getTotalFanNum">
      </el-table-column>
      <el-table-column label="导粉微信号" align="center" prop="wxAccountList" >
        <template slot-scope="scope">
          <el-popover
          placement="left"
          width="520"
          trigger="click">
          <el-table :data="scope.row.wxAccountList" style="width:450;height: 400px;overflow: auto;">
            <el-table-column width="80" property="saleName" label="销售" align="center"></el-table-column>
            <el-table-column width="160" property="wxAccount" label="微信号" align="center"></el-table-column>
            <el-table-column width="120" property="importFanNum" label="导粉数量" align="center">
                <template slot-scope="scope">
                  <div v-if="scope.row.updateFlag">
                    <el-input-number v-model="scope.row.importFanNum" controls-position="right" :min="1" :max="10000" style="width:100px"></el-input-number>
                  </div>
                  <div v-else>
                    {{scope.row.importFanNum}}
                  </div>
                </template>
            </el-table-column>
            <el-table-column width="120" label="操作" align="center">
              <template slot-scope="scope">
                <el-button
                    v-if="scope.row.updateFlag"
                    size="mini"
                    type="text"
                    icon="el-icon-check"
                    @click="saveWxAccountFanNum(scope.row)"
                    v-hasPermi="['custom:importFanRecord:edit']"
                  >保存</el-button>
                  <el-button 
                    v-else
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    @click="scope.row.updateFlag = true"
                    v-hasPermi="['custom:importFanRecord:edit']"
                  >修改</el-button>
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-delete"
                    @click="removeWxAccount(scope.row.id)"  
                    v-hasPermi="['custom:importFanRecord:remove']"
                  >移除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-button slot="reference">详情</el-button>
          </el-popover>
        </template>
      </el-table-column>
      
      
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!--<el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['custom:importFanRecord:edit']"
          >修改</el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:importFanRecord:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <!--<pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      :layout="'total, slot, sizes, prev, pager, next, jumper'"
      @pagination="getList" 
        <span style="margin-right: 12px"
        >总计导粉量：{{totalFanNum}}
        元</span
      >
    </pagination>-->

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      :layout="`${'total, slot, sizes, prev, pager, next, jumper'}`"
      @pagination="getList"
    >
      <span style="margin-right: 12px"
        >总计导粉量：{{
          totalFanNum
        }}
        </span
      >
    </pagination>

    <!-- 添加或修改导粉管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="导粉日期" prop="importFanDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.importFanDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择导粉日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="导粉渠道" prop="importFanChannel">
          <el-input v-model="form.importFanChannel" placeholder="请输入导粉渠道" />
        </el-form-item>
        <el-form-item label="进粉直播间" prop="importFanLive">
          <el-input v-model="form.importFanLive" placeholder="请输入进粉直播间" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <ImportFan ref="importFanRef"></ImportFan>
  </div>
</template>

<script>
import { listImportFanRecord, getImportFanRecord, delImportFanRecord, addImportFanRecord, updateImportFanRecord, exportImportFanRecord,removeFanWxAccount,saveWxAccountFanNum } from "@/api/custom/importFanRecord";
import ImportFan from "@/components/ImportFanRecord/ImportFan";
import { getLiveSchedulByTime } from "@/api/custom/liveSchedul";
import dayjs from "dayjs";
const nowDate = dayjs().format("YYYY-MM-DD");
const nowTime = dayjs().format("YYYY-MM-DD HH:mm");
export default {
  name: "ImportFanRecord",
  data() {
    return {
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
      // 导粉管理表格数据
      importFanRecordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        importFanDate: nowDate,
        importFanChannel: null,
        importFanLive: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      //导粉账号渠道
      fanChanneloptions:[],
      //总导粉量
      totalFanNum:0
    };
  },
  created() {
    this.getList();
    this.getDicts("cus_account").then((response) => {
      this.fanChanneloptions = response.data;
    });
  },
  components:{
      ImportFan
  },
  methods: {
    /** 查询导粉管理列表 */
    getList() {
      this.loading = true;
      listImportFanRecord(this.queryParams).then(response => {
        if(response.rows != null && response.rows.length > 0){
          this.totalFanNum = response.rows[0].totalFanNum;
          response.rows.forEach((item,index) => {
              if(item.wxAccountList != null && item.wxAccountList.length > 0){
                 item.wxAccountList.forEach((wx,i) => {
                    wx.updateFlag = false;
                 })
              }
          });
        }else{
          this.totalFanNum = 0;
        }
        this.importFanRecordList = response.rows;
        this.total = response.total;
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
        id: null,
        importFanDate: null,
        importFanChannel: null,
        importFanLive: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null,
        delFlag: null
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加导粉管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getImportFanRecord(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改导粉管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateImportFanRecord(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addImportFanRecord(this.form).then(response => {
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
      this.$confirm('是否确认删除导粉管理编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delImportFanRecord(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有导粉管理数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportImportFanRecord(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
    showDialog(data){
        if(this.queryParams.importFanDate == undefined || this.queryParams.importFanDate == null){
            this.$message({
                type: 'warning',
                message: '导粉日期不能为空',
                center: true
            });
            return;
        }
        if(data == null){
            data = {};
        }
        data.importFanDate = this.queryParams.importFanDate;
        this.$refs.importFanRef.showDialog(data,()=>{
            this.getList();
        }, this.fanChanneloptions);
    },
    getTotalFanNum(row, column){
      let fanNum = 0;
      if(row.wxAccountList != null){
        row.wxAccountList.forEach((item,index) => {
          fanNum += item.importFanNum == null ? 0 : item.importFanNum;
        });
      }
      return fanNum;
    },
    removeWxAccount(id){
       removeFanWxAccount(id).then(response => {
          if (response.code === 200) {
              this.msgSuccess("移除成功");
              this.getList();
          }
      });
    },
    saveWxAccountFanNum(row){
        let param = {
          id: row.id,
          importFanNum: row.importFanNum
        };
        saveWxAccountFanNum(param).then(response => {
          if (response.code === 200) {
              this.msgSuccess("保存成功");
              this.getList();
          }
        });
    }
  }
};
</script>