<template>
  <el-drawer
    :visible.sync="visible"
    :close-on-press-escape="false"
    :title="dialogTitle"
    append-to-body
    @closed="onClosed"
    size="40%"
  >
    <div class="app-container">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['custom:orderNutritionistReplaceRecord:add']"
            >新增</el-button
          >
        </el-col>
        <right-toolbar
          :showSearch.sync="showSearch"
          @queryTable="getList"
        ></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="orderNutritionistReplaceRecordList">
        <el-table-column label="营养师" align="center" prop="nutritionist">
          <template slot-scope="scope">
            {{ `${orderData.nutritionist} -> ${scope.row.nutritionist}` }}
          </template>
        </el-table-column>
        <el-table-column label="营养师助理" align="center" prop="nutriAssis">
          <template slot-scope="scope">
            {{ `${orderData.nutriAssis} -> ${scope.row.nutriAssis}` }}
          </template>
        </el-table-column>
        <el-table-column label="售后" align="center" prop="afterSale">
          <template slot-scope="scope">
            {{ `${orderData.afterSale} -> ${scope.row.afterSale}` }}
          </template>
        </el-table-column>
        <el-table-column
          label="开始服务时间"
          align="center"
          prop="startTime"
          width="100"
        >
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.startTime, "{y}-{m}-{d}") }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          width="100"
          class-name="small-padding "
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['custom:orderNutritionistReplaceRecord:remove']"
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
      />

      <!-- 添加或修改订单营养师、售后更换记录对话框 -->
      <el-dialog
        :title="title"
        :visible.sync="open"
        width="400px"
        append-to-body
      >
        <el-form ref="form" :model="form" :rules="rules" label-width="100px">
          <el-form-item label="营养师" prop="nutritionistId">
            <el-select v-model="form.nutritionistId" placeholder="请选择营养师">
              <el-option
                v-for="dict in nutritionistIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="营养师助理" prop="nutriAssisId">
            <el-select
              v-model="form.nutriAssisId"
              placeholder="请选择营养师助理"
            >
              <el-option
                v-for="dict in nutriAssisIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="售后" prop="afterSaleId">
            <el-select v-model="form.afterSaleId" placeholder="请选择售后">
              <el-option
                v-for="dict in afterSaleIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="开始时间" prop="startTime">
            <el-date-picker
              clearable
              size="small"
              style="width: 200px"
              v-model="form.startTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择开始时间"
            >
            </el-date-picker>
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
  listOrderNutritionistReplaceRecord,
  getOrderNutritionistReplaceRecord,
  delOrderNutritionistReplaceRecord,
  addOrderNutritionistReplaceRecord,
  updateOrderNutritionistReplaceRecord,
  exportOrderNutritionistReplaceRecord,
} from "@/api/custom/OrderNutritionistReplaceRecord";
import { mapGetters } from "vuex";
export default {
  name: "OrderNutritionistReplaceRecord",
  data() {
    return {
      dialogTitle: "",
      visible: false,
      orderData: null,
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
      // 订单营养师、售后更换记录表格数据
      orderNutritionistReplaceRecordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        nutritionistId: [
          { required: true, message: "营养师不能为空", trigger: "blur" },
        ],
        nutriAssisId: [
          { required: true, message: "营养师助理不能为空", trigger: "blur" },
        ],
        afterSaleId: [
          { required: true, message: "售后不能为空", trigger: "blur" },
        ],
        startTime: [
          { required: true, message: "开始时间不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {},
  computed: {
    ...mapGetters([
      // 售后字典
      "afterSaleIdOptions",
      // 主营养师字典
      "nutritionistIdOptions",
      //
      "nutriAssisIdOptions",
    ]),
  },
  methods: {
    showDialog(data) {
      if (data == undefined || data == null) {
        return;
      }
      this.orderData = data;
      this.dialogTitle =
        "「" + data.orderTime + "-" + data.customer + "」订单服务人员更换记录";
      this.visible = true;
      this.queryParams.orderId = this.orderData.orderId;
      this.getList();
    },
    /** 查询订单营养师、售后更换记录列表 */
    getList() {
      this.loading = true;
      listOrderNutritionistReplaceRecord(this.queryParams).then((response) => {
        this.orderNutritionistReplaceRecordList = response.rows;
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
        nutritionistId: null,
        afterSaleId: null,
        startTime: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null,
        delFlag: null,
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加订单营养师、售后更换记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getOrderNutritionistReplaceRecord(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改订单营养师、售后更换记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.form.orderId = this.orderData.orderId;
          if (this.form.id != null) {
            updateOrderNutritionistReplaceRecord(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addOrderNutritionistReplaceRecord(this.form).then((response) => {
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
      this.$confirm(
        '是否确认删除订单营养师、售后更换记录编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delOrderNutritionistReplaceRecord(ids);
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
      this.$confirm("是否确认导出所有订单营养师、售后更换记录数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportOrderNutritionistReplaceRecord(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    onClosed() {
      this.orderData = null;
      this.visible = false;
    },
  },
};
</script>
