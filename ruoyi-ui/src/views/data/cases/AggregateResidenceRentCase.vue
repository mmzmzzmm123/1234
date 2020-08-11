<template>
  <div class="app-container">
    <el-form :model="queryParams" :rules="rules" ref="queryForm" :inline="true" label-width="100px">
      <el-form-item label="年月" prop="yearMonth">
        <el-select v-model="queryParams.yearMonth" placeholder="请选择年月">
          <el-option
            v-for="item in yearMonthList"
            :value="item.value"
            :label="item.label"
            :key="item.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="案例名称" prop="caseName" clearable>
        <el-input
          v-model="queryParams.caseName"
          placeholder="请输入案例名称"
          clearable
          size="small"
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
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:user:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="dataList">
      <!-- <el-table-column label="年月" align="center" prop="yearMonth" /> -->
      <el-table-column label="案例URL" align="center" prop="urlHouseholds" width="300">
        <template slot-scope="scope">
          <a
            :href="scope.row.urlHouseholds"
            target="_blank"
            v-bind:style="'color: blue;'"
          >{{scope.row.urlHouseholds}}</a>
        </template>
      </el-table-column>
      <el-table-column label="案例名称" align="center" prop="caseName" width="240" />
      <el-table-column label="案例来源" align="center" prop="origin" />
      <el-table-column label="挂牌/成交" align="center" prop="caseType" :formatter="caseTypeFormatter" />
      <el-table-column label="案例单套ID" align="center" prop="householdsIdSRC" width="120" />
      <el-table-column label="案例小区ID" align="center" prop="projectIdSRC" />
      <el-table-column label="小区ID" align="center" prop="communityId" />
      <el-table-column label="楼栋ID" align="center" prop="buildingId" />
      <el-table-column label="租赁类型" align="center" :formatter="rentTypeFormatter" />
      <el-table-column label="室-厅-卫">
        <template slot-scope="scope">
          <span>{{ scope.row.roomNum+'-'+scope.row.hallNum+'-'+(scope.row.bashRoomNum?scope.row.bashRoomNum:0) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="面积" align="center" prop="area" />
      <el-table-column label="朝向" align="center" prop="toward" />
      <el-table-column label="所在层/总楼层" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.currentFloor+'/'+scope.row.totalFloor }}</span>
        </template>
      </el-table-column>
      <el-table-column label="有无电梯" align="center" prop="elevator" :formatter="youOrWuFormatter" />
      <el-table-column label="装修类型" align="center" prop="decoration" />
      <el-table-column label="建成年代" align="center" prop="year" />
      <el-table-column label="房屋面积系数" align="center" prop="areaCoefficient" />
      <el-table-column label="朝向系数" align="center" prop="towardCoefficient" />
      <el-table-column label="楼层系数" align="center" prop="floorCoefficient" />
      <el-table-column label="装修系数" align="center" prop="decorationCoefficient" />
      <el-table-column label="建成年代系数" align="center" prop="yearCoefficient" />
      <el-table-column label="楼栋系数" align="center" prop="buildingCoefficient" />
      <el-table-column label="室厅修正系数" align="center" prop="roomTypeCoefficient" />
      <el-table-column label="总价(元)" align="center" prop="totalPrice" />
      <el-table-column label="单价" align="center" prop="unitPrice" />
      <el-table-column label="散租单价" align="center" prop="priceScatterRent" />
      <el-table-column label="整租单价" align="center" prop="priceEntireRent" />
      <el-table-column label="合租折算价" align="center" prop="priceShareRent" />
      <el-table-column label="带看总次数" align="center" prop="visitedNum" />
      <el-table-column label="首次带看时间" align="center" prop="firstVisitedDate" />
      <el-table-column label="15天内带看总次数" align="center" prop="visitedNum15" />
      <el-table-column label="30天内带看总次数" align="center" prop="visitedNum30" />
      <el-table-column label="案例状态" align="center" prop="status" :formatter="statusFormatter" />
      <el-table-column label="最近一次挂牌总价调整幅度" align="center" prop="adjustedValue" />
      <el-table-column label="最近一次挂牌总价调整比例" align="center" prop="adjustedPst" />
      <el-table-column label="累计挂牌总价调整幅度" align="center" prop="adjustedCumValue" />
      <el-table-column label="累计挂牌总价调整比例" align="center" prop="adjustedCumPst" />
      <el-table-column label="累计挂牌总价绝对调整幅度" align="center" prop="adjustedCumValueAbs" />
      <el-table-column label="累计挂牌总价绝对调整比例" align="center" prop="adjustedCumPstAbs" />
      <el-table-column label="累计挂牌总价调价次数" align="center" prop="adjustedCumNum" />
      <el-table-column label="首次挂牌总价" align="center" prop="priceTotalIn" />
      <el-table-column label="下架时挂牌总价" align="center" prop="priceTotalOut" />
      <el-table-column label="首次挂牌日期" align="center" width="120">
        <template slot-scope="scope">
          <span>{{ formatDate(scope.row.priceDateIn) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="下架日期" align="center" width="120">
        <template slot-scope="scope">
          <span>{{ formatDate(scope.row.priceDateOut) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="案例小区URL" align="center" width="400">
        <template slot-scope="scope">
          <a :href="scope.row.urlProjects" target="_blank">{{scope.row.urlProjects}}</a>
        </template>
      </el-table-column>
      <el-table-column label="平均租金(上周期)" align="center" prop="rentPrice_1" />
      <el-table-column label="案例涨跌幅" align="center" prop="range" />
      <el-table-column label="涨跌类型" align="center" prop="rangeFlag" />
      <el-table-column label="平均租金" align="center" prop="rentPrice" />
      <el-table-column label="整租比" align="center" prop="entireRentRatio" />
      <el-table-column label="分租比" align="center" prop="shareRentRatio" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageIndex"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import {
  list,
  export2File,
  getYearMonthList
} from "@/api/data/aggregateResidenceRentCase";

export default {
  name: "aggregateResidenceRentCase",
  data() {
    // 年月
    var checkYearMonth = (rule, value, callback) => {
      if (!value) {
        callback(new Error("请输入年月"));
      } else if (value === "" || isNaN(parseInt(value))) {
        callback(new Error("请输入年月"));
      } else {
        callback();
      }
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
      // 总条数
      total: 0,
      // 办公基价表格数据
      dataList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        yearMonth: undefined,
        caseName: undefined,
        pageIndex: 1,
        pageSize: 10
      },
      yearMonthList: [],
      statusOptions: [
        { value: 1, text: "正常" },
        { value: 1, text: "失效" }
      ],
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url:
          process.env.VUE_APP_BASE_API +
          "/data/compute/rentprice/residence/importData"
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        yearMonth: [
          { validator: checkYearMonth, trigger: "blur" },
          { validator: checkYearMonth, trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.loading = false;
    getYearMonthList().then(response => {
      this.yearMonthList = response.data;
    });
  },
  methods: {
    rentTypeFormatter: function(row, column, cellValue, index) {
      if (cellValue === 1) return "整租";
      if (cellValue === 2) return "散租";
      if (cellValue === 3) return "分租";
      return "无";
    },
    caseTypeFormatter: function(row, column, cellValue, index) {
      if (cellValue == "0") return "成交案例";
      return "挂牌案例";
    },
    youOrWuFormatter: function(row, column, cellValue, index) {
      if (cellValue) return "有";
      return "无";
    },
    yesOrNotFormatter: function(row, column, cellValue, index) {
      if (cellValue) return "是";
      return "否";
    },
    statusFormatter: function(row, column, cellValue, index) {
      if (cellValue === 3) return "当期活跃";
      if (cellValue === 4) return "当期下架";
      if (cellValue === 5) return "历史下架";
      return "否";
    },
    /** 查询办公基价列表 */
    getList() {
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          this.loading = true;
          list(this.queryParams).then(response => {
            this.dataList = response.rows;
            this.total = response.total;
            this.loading = false;
          });
        }
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
        id: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageIndex = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          this.$confirm("是否确认导出所有住宅租赁基价数据项?", "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          })
            .then(function() {
              return export2File(queryParams);
            })
            .then(response => {
              this.download(response.msg);
            })
            .catch(function() {});
        }
      });
    }
  }
};
</script>
