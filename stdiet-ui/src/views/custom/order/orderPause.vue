<template>
  <div class="app-container">
    <!--<el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单id" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入订单id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createDate" label-width="120px">
        <el-date-picker
          v-model="queryParams.createDate"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <!--<el-form-item label="服务暂停理由" prop="reason">
        <el-input
          v-model="queryParams.reason"
          placeholder="请输入服务暂停理由"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="备注" prop="remarks">
        <el-input
          v-model="queryParams.remarks"
          placeholder="请输入备注"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>-->

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['orderPause:pause:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['orderPause:pause:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['orderPause:pause:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['orderPause:pause:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="pauseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />

      <!--<el-table-column label="订单成交时间" align="center" prop="orderTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.orderTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="客户姓名" align="center" prop="customer" />-->
      <el-table-column label="暂停开始日期" align="center" prop="pauseStartDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.pauseStartDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="暂停结束日期" align="center" prop="pauseEndDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.pauseEndDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="暂停理由" align="center" prop="reason" />
      <el-table-column label="备注" align="center" prop="remarks" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['orderPause:pause:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['orderPause:pause:remove']"
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

    <!-- 添加或修改订单服务暂停对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!--<el-form-item label="订单id" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入订单id" />
        </el-form-item>-->
        <el-form-item label="暂停日期范围" prop="dateScope" label-width="120px">
          <el-date-picker
            v-model="dateScope"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
          <!--<el-date-picker clearable size="small" style="width: 200px"
                          v-model="form.pauseStartDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择服务暂停开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="暂停结束时间" prop="pauseEndDate">
          <el-date-picker clearable size="small" style="width: 200px"
                          v-model="form.pauseEndDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择服务暂停结束时间">
          </el-date-picker>-->
        </el-form-item>
        <el-form-item label="暂停理由" prop="reason" label-width="120px">
          <el-input type="textarea" placeholder="请输入暂停理由" v-model="form.reason" maxlength="200" show-word-limit rows="5"></el-input>
          <!--<el-input v-model="form.reason" placeholder="请输入暂停理由" />-->
        </el-form-item>
        <el-form-item label="备注" prop="remarks" label-width="120px">
          <el-input type="textarea" placeholder="请输入备注" v-model="form.remarks" maxlength="200" show-word-limit rows="5"></el-input>
          <!--<el-input v-model="form.remarks" placeholder="请输入备注" />-->
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
  import { listPause, getPause, delPause, addPause, updatePause, exportPause } from "@/api/custom/orderPause";
  import dayjs from 'dayjs';

  const beginTime = dayjs().startOf('month').format('YYYY-MM-DD');
  const endTime = dayjs().format('YYYY-MM-DD');

  export default {
    name: "Pause",
    data() {
      const checkcDateScope = (rule, value, callback) => {
        if (this.dateScope == null || this.dateScope == undefined || this.dateScope.length == 0) {
          return callback(new Error('日期范围不能为空'))
        }
        callback();
      }

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
        // 订单服务暂停表格数据
        pauseList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          orderId: null,
          createDate: null,
          pauseEndDate: null
          //reason: null,
          //remarks: null,
        },
        orderId: null,
        // 表单参数
        form: {

        },
        dateScope:[beginTime, endTime],
        // 表单校验
        rules: {
          dateScope: [
            {required: true, trigger: "blur", validator: checkcDateScope}
          ],
          reason: [
            {required: true, message: "暂停理由不能为空", trigger: "blur"}
          ]
        }
      };
    },
    created() {
      this.getList();
    },
    mounted() {
      this.orderId = this.$route.params.orderId;
      this.queryParams.orderId = this.orderId;
    },
    methods: {
      /** 查询订单服务暂停列表 */
      getList() {
        this.loading = true;
        listPause(this.queryParams).then(response => {
          this.pauseList = response.rows;
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
          orderId: null,
          pauseStartDate: null,
          pauseEndDate: null,
          reason: null,
          remarks: null
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        console.log(this.queryParams.pauseStartDate);
        //this.getList();
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
        this.dateScope = [];
        this.open = true;
        this.title = "添加暂停记录";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids;
        getPause(id).then(response => {
          this.form = response.data;
          this.dateScope = [];
          this.dateScope[0] = this.form.pauseStartDate;
          this.dateScope[1] = this.form.pauseEndDate;
          this.open = true;
          this.title = "修改暂停记录";
      });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            this.form.pauseStartDate = dayjs(this.dateScope[0]).format("YYYY-MM-DD");
            this.form.pauseEndDate =  dayjs(this.dateScope[1]).format("YYYY-MM-DD");
            console.log(this.form.pauseStartDate + "-" + this.form.pauseEndDate);
            if (this.form.id != null) {
              updatePause(this.form).then(response => {
                if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
            } else {
              this.form.orderId = this.orderId;
              addPause(this.form).then(response => {
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
        this.$confirm('是否确认删除订单服务暂停编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delPause(ids);
        }).then(() => {
          this.getList();
        this.msgSuccess("删除成功");
      }).catch(function() {});
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有订单服务暂停数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportPause(queryParams);
        }).then(response => {
          this.download(response.msg);
      }).catch(function() {});
      }
    },
    checkcDateScope(value){
       console.log(value[0]);
       return false;
    }
  };
</script>
