<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
    >
      <el-form-item label="模板名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入模板名称" />
      </el-form-item>
      <el-form-item label="营养师" prop="nutritionistId">
        <el-select
          v-model="queryParams.nutritionistId"
          placeholder="请选择营养师"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in nutritionistIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="营养师助理" prop="nutriAssisId">
        <el-select
          v-model="queryParams.nutriAssisId"
          placeholder="请选择营养师助理"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in nutriAssisIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="审核状态" prop="reviewStatus">
        <el-select
          v-model="queryParams.reviewStatus"
          placeholder="请选审核状态"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in reviewStatusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="cyan"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8" style="margin-top: 10px">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['custom:ingredient:add']"
          >新增
        </el-button>
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recipesTemplateList">
      <el-table-column label="审核状态" align="center" width="120">
        <template slot-scope="scope">
          <el-tag
            :type="
              !scope.row.reviewStatus
                ? 'info'
                : scope.row.reviewStatus === 1
                ? 'danger'
                : 'success'
            "
            >{{
              `${
                !scope.row.reviewStatus
                  ? "未制作"
                  : scope.row.reviewStatus == 1
                  ? "未审核"
                  : "已审核"
              }`
            }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column
        label="模板名称"
        align="center"
        prop="name"
        width="160"
      />
      <el-table-column
        label="营养师"
        align="center"
        prop="nutritionistId"
        width="100"
        :formatter="nutritionistIdFormat"
      />
      <el-table-column
        label="营养师助理"
        align="center"
        width="100"
        prop="nutriAssisId"
        :formatter="nutriAssisIdFormat"
      />
      <el-table-column
        label="创建时间"
        align="center"
        width="180"
        prop="createTime"
      />
      <el-table-column label="备注" prop="remark" align="center" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="300"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleOnTemplateEdit(scope.row)"
          >
            修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            v-hasPermi="['recipes:template:edit']"
            :icon="`${
              scope.row.recipesId ? 'el-icon-edit' : 'el-icon-edit-outline'
            }`"
            @click="handleOnRecipesEdit(scope.row)"
            >{{ `${scope.row.recipesId ? "编辑" : "制作"}食谱` }}</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleOnDelete(scope.row)"
          >
            删除
          </el-button>
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

    <!-- 添加或修改食材对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="520px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="模板名称" prop="name" label-width="100px">
          <el-input v-model="form.name" placeholder="请输入模板名称" />
        </el-form-item>
        <el-form-item label="营养师" prop="nutritionistId" label-width="100px">
          <el-select v-model="form.nutritionistId" placeholder="请选择营养师">
            <el-option
              v-for="dict in nutritionistIdOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          label="营养师助理"
          prop="nutriAssisId"
          label-width="100px"
        >
          <el-select v-model="form.nutriAssisId" placeholder="请选择营养师助理">
            <el-option
              v-for="dict in nutriAssisIdOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark" label-width="100px">
          <el-input
            v-model="form.remark"
            :rows="4"
            type="textarea"
            placeholder="请输入内容"
          />
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
import {
  listRecipesTemplate,
  addRecipesTemplate,
  updateRecipesTemplate,
  deleteRecipesTemplate,
} from "@/api/custom/recipesTemplate";
import store from "@/store";
import { mapState } from "vuex";

const userId = store.getters && store.getters.userId;
export default {
  name: "recipesTemplate",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 食谱计划表格数据
      recipesTemplateList: [],
      orderDialog: undefined,
      reviewStatusOptions: [
        { dictValue: 0, dictLabel: "未制作" },
        { dictValue: 1, dictLabel: "未审核" },
        { dictValue: 2, dictLabel: "已审核" },
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        nutritionistId: null,
        nutriAssisId: null,
        reviewStatus: null,
      },
      open: false,
      title: "",
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [{ required: true, message: "请输入模板名称", trigger: "blur" }],
        nutritionistId: [
          { required: true, message: "请选择营养师", trigger: "blur" },
        ],
        nutriAssisId: [
          { required: true, message: "请选择营养师助理", trigger: "blur" },
        ],
      },
      //订单对于所有计划安排数据
      allRecipesPlanList: [],
      //订单弹窗状态
      allRecipesPlanOpen: false,
      allRecipesPlanTitle: "",
      //订单弹窗中查询参数
      allRecipesPlanQueryParam: {
        pageNum: 1,
        pageSize: 10,
        orderId: null,
        sendFlag: 0,
      },
      //订单弹窗中列表数据的总条数
      allRecipesPlanTotal: 0,
    };
  },
  components: {
    // "order-dialog": OrderDetail,
    // body_sign_dialog: BodySignDetail,
  },
  computed: {
    ...mapState({
      //营养师
      nutritionistIdOptions: (state) =>
        state.global.nutritionistIdOptions.slice(1),
      //营养师助理
      nutriAssisIdOptions: (state) => state.global.nutriAssisIdOptions.slice(1),
    }),
  },
  watch: {
    // nutritionistIdOptions: function (val, oldVal) {
    //   if (val.length && !oldVal.length) {
    //     const tarObj = val.find((opt) => opt.dictValue == userId);
    //     if (tarObj) {
    //       this.queryParams.nutritionistId = userId;
    //     }
    //   }
    // },
    // nutriAssisIdOptions: function (val, oldVal) {
    //   if (val.length && !oldVal.length) {
    //     const tarObj = val.find((opt) => opt.dictValue == userId);
    //     if (tarObj) {
    //       this.queryParams.nutriAssisId = userId;
    //     }
    //   }
    // },
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询食谱计划列表 */
    getList() {
      this.loading = true;
      listRecipesTemplate(this.queryParams).then((response) => {
        this.recipesTemplateList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加模板";
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
        remark: null,
        nutritionistId: null,
        nutriAssisId: null,
      };
      this.resetForm("form");
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有食谱计划数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportRecipesPlan(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    handleOnDelete(data) {
      this.$confirm(`是否确认删除「${data.name}」？`, "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          deleteRecipesTemplate(data.id).then(() => {
            this.getList();
            this.msgSuccess("删除成功");
          });
        })

        .catch(() => {});
    },
    handleOnTemplateEdit(data) {
      this.open = true;
      this.title = "修改模板";
      this.reset();
      this.form = data;
    },
    handleOnRecipesEdit(data) {
      // console.log(data);
      const { planId, name, id } = data;
      window.open(
        "/recipes/build/" + name + "/" + planId + "?temId=" + id,
        "_blank"
      );
    },
    // 主营养师字典翻译
    nutritionistIdFormat(row, column) {
      return this.selectDictLabel(
        this.nutritionistIdOptions,
        row.nutritionistId
      );
    },
    // 助理营养师字典翻译
    nutriAssisIdFormat(row, column) {
      return this.selectDictLabel(this.nutriAssisIdOptions, row.nutriAssisId);
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateRecipesTemplate(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addRecipesTemplate(this.form).then((response) => {
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
  },
};
</script>



