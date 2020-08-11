<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true">
      <el-form-item label="成交ID" prop="dealId">
        <el-input
          v-model="queryParams.dealId"
          placeholder="请输入成交ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="案例小区名称" prop="caseCommunityName">
        <el-input
          v-model="queryParams.caseCommunityName"
          placeholder="请输入案例小区名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="案例地址" prop="caseAddress">
        <!-- <el-input
          v-model="queryParams.caseAddress"
          placeholder="请输入案例地址"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />-->
        <template>
          <el-select
            v-model="address"
            remote
            filterable
            placeholder="请输入案例地址"
            :remote-method="findDealId"
            :loading="loading"
            @change="dealIdChange"
          >
            <el-option
              v-for="item in dealIdList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </template>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="dataList" style="width: 100%;">
      <!-- :show-overflow-tooltip="true"  -->
      <el-table-column label="成交id" align="center" prop="dealId" width="120" />
      <el-table-column label="成交类型" align="center" prop="dealType" width="100" />
      <el-table-column label="案例位置" align="center" prop="location" width="200" />
      <el-table-column label="案例小区名称" align="center" prop="caseCommunityName" width="200" />
      <el-table-column label="案例地址" align="center" prop="caseAddress" width="300" />
      <el-table-column label="案例签约日期" align="center" prop="caseContractDate" width="100" />
      <el-table-column label="案例面积" align="center" prop="caseArea" width="180" />
      <el-table-column label="案例楼层" align="center" prop="caseFloor" />
      <el-table-column label="案例户型" align="center" prop="caseApartmentLayout" />
      <el-table-column label="案例总价(万元)" align="center" prop="caseTotalPrice" />
      <el-table-column label="案例单价(元/㎡)" align="center" prop="caseUnitPrice" />
      <el-table-column label="案例房屋类型" align="center" prop="caseHouseType" />
      <el-table-column label="案例房屋性质" align="center" prop="caseHouseProperty" />

      <el-table-column label="标准小区ID" align="center" prop="communityId" width="100" />
      <el-table-column label="标准楼栋ID" align="center" prop="buildingId" width="100" />
      <el-table-column label="标准单套ID" align="center" prop="condoId" width="100" />
      <el-table-column label="清洗物业类型" align="center" prop="propertyType" width="100" />
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
import { list, findByText } from "@/api/data/uvClosingCase";

export default {
  name: "uvClosingCase",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 表格数据
      dataList: [],
      // 案例
      dealIdList: [],
      // 地址文本
      address: undefined,
      // 查询参数
      queryParams: {
        dealId: undefined,
        caseCommunityName: undefined,
        caseAddress: undefined,
        pageIndex: 1,
        pageSize: 10
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询登录日志列表 */
    getList() {
      this.loading = true;
      list(this.queryParams).then(response => {
        var arr = [];
        for (var i = 0; i < response.rows.length; i++) {
          var v = response.rows[i];
          v["location"] =
            v["caseDistrict"] + "/" + v["caseBlock"] + "/" + v["caseLoop"];
          arr.push(v);
        }
        console.log(arr);
        this.dataList = arr;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.pageIndex = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.dealIdList = [];
      this.address = undefined;
      this.handleQuery();
    },
    dealIdChange(val) {
      this.queryParams.dealId = val;
    },
    findDealId(text) {
      if (text) {
        this.loading = true;

        findByText(text).then(response => {
          this.dealIdList = [];

          var arr = new Array();
          for (var i = 0; i < response.rows.length; i++) {
            arr.push({
              value: response.rows[i]["dealId"],
              label:
                response.rows[i]["countryName"] +
                " " +
                response.rows[i]["cleanAddress"]
            });
          }
          this.dealIdList = arr;
          this.loading = false;
        });
      }
    }
  }
};
</script>

