<template>
  <el-drawer
    :title="drawerTitle"
    :append-to-body="true"
    :close-on-press-escape="false"
    :visible.sync="visible"
    size="35%"
  >
    <div class="plan_pause_drawer_wrapper">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['recipes:pause:add']"
            >新增</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['recipes:pause:export']"
            >导出</el-button
          >
        </el-col>
      </el-row>

      <el-table v-loading="loading" :data="pauseList" height="80%">
        <el-table-column label="时间" align="center" width="120">
          <template slot-scope="scope">
            <div>
              {{ scope.row.pauseStartDate }}
            </div>
            至
            <div>
              {{ scope.row.pauseEndDate }}
            </div>
          </template>
        </el-table-column>
        <el-table-column label="暂停理由" align="center" prop="reason">
          <template slot-scope="scope">
            <el-popover
              v-if="scope.row.reason != null && scope.row.reason.length > 10"
              placement="top"
              title="事由"
              width="200"
              trigger="hover"
              :content="scope.row.reason"
            >
              <el-button slot="reference" type="text">详情 </el-button>
            </el-popover>
            <span v-else>{{ scope.row.reason }}</span>
          </template>
        </el-table-column>
        <el-table-column label="备注" align="center" prop="remarks">
          <template slot-scope="scope">
            <el-popover
              v-if="scope.row.remarks != null && scope.row.remarks.length > 10"
              placement="top"
              title="备注"
              width="200"
              trigger="hover"
              :content="scope.row.remarks"
            >
              <el-button slot="reference" type="text">详情 </el-button>
            </el-popover>
            <span v-else>{{ scope.row.remarks }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="创建时间"
          align="center"
          prop="createTime"
          width="120"
        >
          <template slot-scope="scope">
            <div v-for="str in scope.row.createTime.split(' ')" :key="str">
              {{ str }}
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
          width="120"
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['recipes:pause:edit']"
              >修改</el-button
            >
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['recipes:pause:remove']"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
        layout="total, slot,  prev, pager, next, jumper"
      />

      <!-- 添加或修改订单服务暂停对话框 -->
      <el-dialog
        :title="title"
        :visible.sync="open"
        width="500px"
        append-to-body
      >
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="时间范围" prop="dateScope">
            <el-date-picker
              v-model="dateScope"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              :picker-options="orderPickerOptions"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="事由" prop="reason">
            <el-input
              type="textarea"
              placeholder="请输入暂停事由"
              v-model="form.reason"
              maxlength="200"
              show-word-limit
              rows="5"
            ></el-input>
          </el-form-item>
          <el-form-item label="备注" prop="remarks">
            <el-input
              type="textarea"
              placeholder="请输入备注"
              v-model="form.remarks"
              maxlength="200"
              show-word-limit
              rows="5"
            ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </div>
  </el-drawer>
</template>

<script>
import {
  listPause,
  getPause,
  delPause,
  addPause,
  updatePause,
  exportPause,
} from "@/api/custom/recipesPause";
import dayjs from "dayjs";

const beginTime = dayjs().startOf("month").format("YYYY-MM-DD");
const endTime = dayjs().format("YYYY-MM-DD");

export default {
  name: "Pause",
  data() {
    const checkcDateScope = (rule, value, callback) => {
      if (
        this.dateScope == null ||
        this.dateScope == undefined ||
        this.dateScope.length == 0
      ) {
        return callback(new Error("日期范围不能为空"));
      }
      callback();
    };

    return {
      // 遮罩层
      loading: true,
      drawerTitle: "",
      //
      visible: false,
      data: undefined,
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
        cusId: undefined,
      },
      // 表单参数
      form: {},
      dateScope: [beginTime, endTime],
      // 表单校验
      rules: {
        dateScope: [
          { required: true, trigger: "blur", validator: checkcDateScope },
        ],
        reason: [
          { required: true, message: "暂停理由不能为空", trigger: "blur" },
        ],
      },
      orderPickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 24 * 60 * 60 * 1000;
        },
      },
    };
  },
  methods: {
    /** 查询订单服务暂停列表 */
    getList() {
      this.loading = true;
      listPause(this.queryParams).then((response) => {
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
        pauseStartDate: null,
        pauseEndDate: null,
        reason: null,
        remarks: null,
      };
      this.resetForm("form");
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
      getPause(row.id).then((response) => {
        this.form = response.data;
        this.dateScope = [];
        this.dateScope[0] = this.form.pauseStartDate;
        this.dateScope[1] = this.form.pauseEndDate;
        this.open = true;
        this.title = "修改暂停记录";
      });
    },
    showDrawer(data) {
      // console.log(data);
      if (!data) {
        return;
      }
      this.data = data;
      this.visible = true;
      this.drawerTitle = `「${this.data.name}」暂停记录`;
      this.queryParams.cusId = this.data.id;
      this.getList();
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.form.pauseStartDate = this.dateScope[0];
          this.form.pauseEndDate = this.dateScope[1];
          this.form.cusId = this.data.id;

          if (this.form.id != null) {
            updatePause(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addPause(this.form).then((response) => {
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
      this.$confirm('是否确认删除暂停编号为"' + row.id + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delPause(row.id);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有暂停记录数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportPause(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
  },
};
</script>
<style lang="scss" scoped>
.plan_pause_drawer_wrapper {
  padding: 20px;
  height: calc(100vh - 77px);
  overflow: auto;
}
</style>
