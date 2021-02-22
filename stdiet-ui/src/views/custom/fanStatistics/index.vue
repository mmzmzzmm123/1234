<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="进粉日期" prop="fanTime">
        <el-date-picker clearable style="width: 200px"
                        v-model="queryParams.fanTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="销售" prop="userId" label-width="68px">
        <el-select v-model="queryParams.userId" placeholder="请选择销售" filterable clearable>
          <el-option
            v-for="dict in preSaleIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="parseInt(dict.dictValue)"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="进粉渠道" prop="accountId" label-width="88px">
        <el-select v-model="queryParams.accountId" filterable placeholder="请选择渠道" clearable>
          <el-option
            v-for="dict in accountIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="parseInt(dict.dictValue)"
          />
        </el-select>
      </el-form-item>
      <el-form-item style="margin-left: 20px">
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
          @click="handleAdd"
          v-hasPermi="['custom:fanStatistics:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['custom:fanStatistics:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['custom:fanStatistics:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['custom:fanStatistics:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="fanStatisticsList" stripe @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="进粉日期" align="center" prop="fanTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.fanTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="销售" align="center" prop="userName" />
      <el-table-column label="进粉渠道" align="center" prop="account" />
      <el-table-column label="微信昵称" align="center" prop="wxNickName" />
      <el-table-column label="微信号" align="center" prop="wxAccount" />
      <el-table-column label="进粉数量" align="center" prop="fanNum" />
      <!--<el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['custom:fanStatistics:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:fanStatistics:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      layout="total, slot, sizes, prev, pager, next, jumper"
      @pagination="getList"
    >
      <span style="margin-right: 12px;font-size:13px;color:rgb(134 136 140)">总计进粉量： {{totalFanNum}}</span>
    </pagination>


    <!-- 添加进粉统计对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="进粉日期" prop="fanTime">
          <el-date-picker clearable  style="width: 200px"
                          v-model="form.fanTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="销售" prop="userId">
          <el-select v-model="form.userId" placeholder="请选择销售" filterable clearable size="small">
            <el-option
              v-for="dict in preSaleIdOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            />
          </el-select>
        </el-form-item>
        <p style="margin: 20px auto 20px 40px;font-size: 16px;">请先选择销售，再填写每个微信号对应进粉数量</p>
        <div v-if="showWxFlag">
          <div v-for="(item, index) in wxList" style="margin: 10px auto auto 40px;">
            <span>{{item.wxAccount}}</span><el-input-number controls-position="right" v-model="form.fanNum[index]" style="width: 150px;margin-left: 10px" :min="0" :max="1000000000"></el-input-number>
          </div>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer" >
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 修改进粉统计对话框 -->
    <el-dialog :title="title" :visible.sync="editOpen" width="500px" append-to-body>
      <el-form ref="editForm" :model="editForm" :rules="editRules">
        <el-form-item label="进粉日期" prop="fanTime">
          <el-date-picker clearable  style="width: 200px"
                          v-model="editForm.fanTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="进粉数量" prop="fanNum">
          <el-input-number controls-position="right" v-model="editForm.fanNum" :min="0" :max="1000000000"></el-input-number>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer" >
        <el-button type="primary" @click="editSubmit">确 定</el-button>
        <el-button @click="editCancel">取 消</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
  import { listFanStatistics, getFanStatistics, delFanStatistics, addFanStatistics, updateFanStatistics, exportFanStatistics, getWxByUserId } from "@/api/custom/fanStatistics";
  import { getOptions } from "@/api/custom/order";
  import store from "@/store";
  import dayjs from "dayjs";
  const loginUserId = store.getters && store.getters.userId;
  const nowDate = dayjs().subtract(1, 'day').format("YYYY-MM-DD");
  export default {
    name: "FanStatistics",
    data() {
      const checkOrderTime = (rule, value, callback) => {
        if (!value) {
          return callback(new Error("成交时间不能为空"));
        }
        callback();
      };
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
        //总进粉量
        totalFanNum: 0,
        // 进粉统计表格数据
        fanStatisticsList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          fanTime: nowDate,
          userId: null,
          accountId: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          fanTime: [{ required: true, trigger: "blur", message: "请选择日期" }],
          userId: [{ required: true, trigger: "blur", message: "请选择销售" }]
        },
        showWxFlag:false,
        //当前选择的用户被分配的微信号
        wxList:[],
        //销售列表
        preSaleIdOptions:[],
        //进粉渠道列表
        accountIdOptions:[],
        editOpen: false,
        editForm:{},
        // 表单校验
        editRules: {
          fanTime: [{ required: true, trigger: "blur", message: "请选择日期" }],
          fanNum: [{ required: true, trigger: "blur", message: "请输入进粉数量" }],
        },
      };
    },
    created() {
      this.getList();
      this.getSaleUserList();
      this.getDicts("fan_channel").then((response) => {
        this.accountIdOptions = response.data;
      });
    },
    methods: {
      /** 查询进粉统计列表 */
      getList() {
        this.loading = true;
        listFanStatistics(this.queryParams).then(response => {
          this.fanStatisticsList = response.rows;
          this.totalFanNum = 0;
          if(this.fanStatisticsList != null && this.fanStatisticsList.length > 0){
             this.totalFanNum = this.fanStatisticsList[0].totalFanNum;
          }
          this.total = response.total;
          this.loading = false;
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 取消按钮
      editCancel() {
        this.editOpen = false;
        this.editFormReset();
      },
      // 表单重置
      reset() {
        this.form = {
          userId: null,
          fanTime: nowDate,
          wxId: [],
          fanNum: []
        };
        this.wxList = [];
        this.showWxFlag = false;
        this.resetForm("form");
      },
      editFormReset(){
        this.editForm = {
          id: null,
          wxId: null,
          fanTime: null,
          fanNum: 0
        };
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
        //查看当前登录用户是否为销售
        const defaultUserId = this.preSaleIdOptions.find(
          (opt) => opt.dictValue === loginUserId
        );
        if (defaultUserId){
           this.form.userId = defaultUserId.dictValue;
        }
        this.open = true;
        this.title = "添加进粉统计";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.editFormReset();
        const id = row.id || this.ids
        getFanStatistics(id).then(response => {
          this.editForm.id = response.data.id;
          this.editForm.wxId = response.data.wxId;
          this.editForm.fanTime = response.data.fanTime;
          this.editForm.fanNum = response.data.fanNum;
          this.editOpen = true;
          this.title = "修改「"+row.wxAccount+"」的进粉统计";
        });


      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if(this.form.wxId.length == 0){
                this.msgError("该销售未被分配微信");
                return;
            }
            let flag = true;
            this.form.fanNum.forEach( (value, index) => {
               if(value == null || value == undefined){
                 this.msgError("请填写进粉数量");
                 flag = false;
                 return;
               }
            });
            if(flag){
              addFanStatistics(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("新增成功");
                  this.open = false;
                  this.reset();
                  this.getList();
                }
              });
            }
          }else{
            this.msgError("数据未填写完整");
          }
        });
      },
      editSubmit(){
        this.$refs["editForm"].validate(valid => {
          if (valid) {
            updateFanStatistics(this.editForm).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.editOpen = false;
                this.getList();
              }
            });
          }else{
            this.msgError("数据未填写完整");
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除进粉统计编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delFanStatistics(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有进粉统计数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportFanStatistics(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
      },
      //获取销售用户列表
      getSaleUserList(){
        getOptions().then((response) => {
          const options = response.data.reduce((opts, cur) => {
            if (!opts[cur.postCode]) {
              opts[cur.postCode] = [];
            }
            opts[cur.postCode].push({
              dictValue: cur.userId,
              dictLabel: cur.userName,
              remark: cur.remark,
            });
            return opts;
          }, {});
          this.preSaleIdOptions = options["pre_sale"] || [];
        });
      },
      //根据用户ID查询被分配的微信号
      getWxByUserId(userId){
        this.showWxFlag = false;
        this.form.wxId = [];
        this.form.fanNum = [];
        this.wxList = [];
        if(userId == null || userId == undefined || userId == ""){
           return;
        }
        getWxByUserId(userId).then(response => {
          if (response.code === 200) {
            this.wxList = response.data ? response.data : [];
            if(this.wxList.length > 0){
              for(let i=0; i < this.wxList.length; i++){
                this.form.wxId[i] = this.wxList[i].wechatAccount;
                this.form.fanNum[i] = 0;
              }
              this.showWxFlag = true;
            }
          }
        });
      },
    },
    watch: {
      // 监听用户ID变化
      "form.userId": function (newVal, oldVal) {
        this.getWxByUserId(newVal);
      },
    }
  };
</script>
