<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="菜品名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入菜品名称"
          clearable
          size="mini"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="菜品种类" prop="dishClass">
        <el-cascader
          size="mini"
          v-model="dishClassQueryParam"
          :options="dishClassOptions"
          :props="{ expandTrigger: 'hover' }"
          :show-all-levels="true"
          filterable
          clearable
          placeholder="请选择菜品种类"
        ></el-cascader>
      </el-form-item>
      <el-form-item label="菜品类型" prop="type">
        <el-select
          v-model="queryParams.type"
          placeholder="请选择菜品类型"
          clearable
          size="mini"
        >
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="审核状态" prop="reviewStatus">
        <el-select
          v-model="queryParams.reviewStatus"
          placeholder="请选择审核状态"
          clearable
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
          >搜索
        </el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置
        </el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['custom:dishes:add']"
          >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['custom:dishes:export']"
          >导出
        </el-button>
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="dishesList">
      <el-table-column label="审核状态" align="center" width="80">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.reviewStatus === 'yes' ? 'success' : 'danger'"
            disable-transitions
          >
            {{ scope.row.reviewStatus === "yes" ? "已审核" : "未审核" }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column label="菜品名称" align="center" prop="name" />
      <el-table-column label="菜品种类" align="center" prop="bigClass">
        <template slot-scope="scope">
          {{ dishClassFormat(scope.row) }}
        </template>
      </el-table-column>
      <el-table-column label="菜品类型" align="center" prop="type">
        <template slot-scope="scope">
          <autohideinfo :data="typeFormat(scope.row)" />
        </template>
      </el-table-column>
      <el-table-column label="包含食材" align="center">
        <template slot-scope="scope">
          <div v-for="igd in scope.row.igdList" :key="igd.id">
            {{ igd.name }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="推荐人群" align="center">
        <template slot-scope="scope">
          <autohideinfo :data="scope.row.recTags" />
        </template>
      </el-table-column>
      <el-table-column label="忌口人群" align="center">
        <template slot-scope="scope">
          <autohideinfo :data="scope.row.notRecTags" />
        </template>
      </el-table-column>
      <el-table-column label="做法" align="center" prop="methods" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['custom:dishes:edit']"
            >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:dishes:remove']"
            >删除
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

    <!-- 添加或修改菜品对话框 -->
    <EditDishesDrawer
      ref="dishesDrawerRef"
      :typeOptions="typeOptions"
      :reviewStatusOptions="reviewStatusOptions"
      :dishClassBigOptions="dishClassBigOptions"
      :dishClassSmallOptions="dishClassSmallOptions"
      :dishClassOptions="dishClassOptions"
      @onSuccess="getList"
    />
  </div>
</template>

<script>
import { delDishes, exportDishes, listDishes } from "@/api/custom/dishes";
import AutoHideInfo from "@/components/AutoHideInfo";
import EditDishesDrawer from "./EditDishesDrawer";

export default {
  name: "Dishes",
  components: {
    autohideinfo: AutoHideInfo,
    EditDishesDrawer,
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 菜品表格数据
      dishesList: [],
      // 审核状态
      reviewStatusOptions: [],

      // 远程数据缓存，预防新增的食材找不到
      oriDataList: [],
      // 备选食材列表
      ingDataList: [],
      // 选中的食材列表
      selIngList: [],
      //
      selRec: [],
      //
      selNotRec: [],
      // 选中的食材id
      selIngIds: [],

      // 菜品类别字典
      typeOptions: [],

      dishClassOptions: [],
      dishClassBigOptions: [],
      dishClassSmallOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        type: null,
        bigClass: null,
        smallClass: null,
      },
      //菜品种类查询种类
      dishClassQueryParam: [],
    };
  },
  created() {
    this.getList();
    this.getDicts("cus_dishes_type").then((response) => {
      this.typeOptions = response.data;
    });

    this.getDicts("cus_review_status").then((response) => {
      this.reviewStatusOptions = response.data;
    });
    this.getDicts("dish_class_big").then((response) => {
      this.dishClassBigOptions = response.data;
      this.getDicts("dish_class_small").then((res) => {
        this.dishClassSmallOptions = res.data;
        this.dealDishClassBigAndSmall();
      });
    });
  },
  methods: {
    /** 查询菜品列表 */
    getList() {
      this.loading = true;
      if (
        this.dishClassQueryParam != null &&
        this.dishClassQueryParam.length > 0
      ) {
        this.queryParams.smallClass = this.dishClassQueryParam[1];
      } else {
        this.queryParams.smallClass = null;
      }
      listDishes(this.queryParams).then((response) => {
        this.dishesList = response.rows.map((d) => {
          const recTags = [],
            notRecTags = [];
          d.igdList.forEach((igd) => {
            if (igd.rec) {
              igd.rec.split(",").forEach((rec) => {
                if (!recTags.includes(rec)) {
                  recTags.push(rec);
                }
              });
            }
            if (igd.notRec) {
              igd.notRec.split(",").forEach((notRec) => {
                if (!notRecTags.includes(notRec)) {
                  notRecTags.push(notRec);
                }
              });
            }
          });
          return {
            ...d,
            recTags,
            notRecTags,
          };
        });
        // console.log(this.dishesList);
        this.total = response.total;
        this.loading = false;
      });
    },
    //处理菜品大类小类的关系
    dealDishClassBigAndSmall() {
      this.dishClassBigOptions.forEach((item, index) => {
        this.dishClassOptions.push({
          value: parseInt(item.dictValue),
          label: item.dictLabel,
          children: [],
        });
        if (index == this.dishClassBigOptions.length - 1) {
          this.dishClassSmallOptions.forEach((smallClass, i) => {
            if (smallClass.remark) {
              this.dishClassOptions[
                parseInt(smallClass.remark - 1)
              ].children.push({
                value: parseInt(smallClass.dictValue),
                label: smallClass.dictLabel,
              });
            }
          });
        }
      });
    },
    // 菜品类型字典翻译
    typeFormat(row, column) {
      return !row.type
        ? ""
        : row.type
            .split(",")
            .map((type) => this.selectDictLabel(this.typeOptions, type));
    },

    // 地域字典翻译
    reviewStatusFormat(row, column) {
      return this.selectDictLabel(this.reviewStatusOptions, row.area);
    },
    //菜品种类翻译
    dishClassFormat(row) {
      if (row.bigClass > 0 && row.smallClass > 0) {
        let bigClassName = this.selectDictLabel(
          this.dishClassBigOptions,
          row.bigClass
        );
        let smallClassName = this.selectDictLabel(
          this.dishClassSmallOptions,
          row.smallClass
        );
        return bigClassName + "/" + smallClassName;
      }
      return "";
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.dishClassQueryParam = [];
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$refs.dishesDrawerRef.showDrawer();
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.$refs.dishesDrawerRef.showDrawer(row.id);
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm('是否确认删除菜品「' + row.name + '」的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delDishes(row.id);
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
      this.$confirm("是否确认导出所有菜品数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportDishes(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
  },
};
</script>
<style rel="stylesheet/scss" lang="scss">
</style>
