<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="86px"
    >
      <el-form-item label="客户信息" prop="name">
        <el-input
          v-model.trim="queryParams.name"
          placeholder="请输入名字或手机号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="进粉方式" prop="fansChannel">
        <el-select v-model="queryParams.fansChannel" placeholder="请选择">
          <el-option
            v-for="dict in fansChannelOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="parseInt(dict.dictValue)"
          />
        </el-select>
      </el-form-item> -->
      <el-form-item label="进粉渠道" prop="channelId">
        <el-select
          v-model="queryParams.channelId"
          filterable
          clearable
          placeholder="请选择"
        >
          <el-option
            v-for="dict in accountIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="parseInt(dict.dictValue)"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="营养师" prop="mainDietitian" v-if="!isPartner">
        <el-select
          v-model="queryParams.mainDietitian"
          clearable
          filterable
          placeholder="请选择"
        >
          <el-option
            v-for="dict in nutritionistIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="parseInt(dict.dictValue)"
          />
        </el-select>
      </el-form-item>
      <el-form-item
        label="营养师助理"
        prop="assistantDietitian"
        v-if="!isPartner"
      >
        <el-select
          v-model="queryParams.assistantDietitian"
          placeholder="请选择"
          clearable
          filterable
        >
          <el-option
            v-for="dict in nutriAssisIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="parseInt(dict.dictValue)"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="销售人员" prop="salesman" v-if="!isPartner">
        <el-select
          v-model="queryParams.salesman"
          clearable
          filterable
          placeholder="请选择"
        >
          <el-option
            v-for="dict in preSaleIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="parseInt(dict.dictValue)"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="售中人员" prop="onSaleId" v-if="!isPartner">
        <el-select
          v-model="queryParams.onSaleId"
          clearable
          filterable
          placeholder="请选择"
        >
          <el-option
            v-for="dict in onSaleIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="parseInt(dict.dictValue)"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="售后营养师" prop="afterDietitian" v-if="!isPartner">
        <el-select
          v-model="queryParams.afterDietitian"
          clearable
          filterable
          placeholder="请选择"
        >
          <el-option
            v-for="dict in afterSaleIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="parseInt(dict.dictValue)"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="病史体征" prop="physicalSignsId">
        <el-select
          v-model="queryParams.physicalSignsId"
          filterable
          clearable
          allow-create
          default-first-option
          placeholder="请选择病史体征"
        >
          <el-option
            v-for="physicalSign in physicalSignsList"
            :key="physicalSign.id"
            :label="physicalSign.name"
            :value="physicalSign.id"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['custom:customer:add']"
          >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['custom:customer:export']"
          >导出
        </el-button>
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="customerCenterList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        width="110"
      >
        <template slot-scope="scope">
          <div v-for="time in scope.row.createTime.split(' ')" :key="time">
            {{ time }}
          </div>
        </template>
      </el-table-column>
      <el-table-column
        label="进粉时间"
        align="center"
        prop="fansTime"
        width="100"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.fansTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column
        label="进粉方式"
        align="center"
        prop="fansChannel"
        :formatter="fansChannelFormat"
      /> -->
      <el-table-column
        label="进粉渠道"
        align="center"
        prop="channelId"
        :formatter="channelFormat"
      />
      <el-table-column label="客户姓名" align="center" prop="name" />
      <el-table-column label="手机号" align="center" prop="phone" width="120" />
      <el-table-column
        label="主营养师"
        align="center"
        prop="mainDietitian"
        :formatter="nutritionistIdFormat"
      />
      <el-table-column
        label="营养师助理"
        align="center"
        prop="assistantDietitian"
        :formatter="nutriAssisIdFormat"
      />
      <el-table-column
        label="售后营养师"
        align="center"
        prop="afterDietitian"
        :formatter="afterSaleIdFormat"
      />
      <el-table-column
        label="销售人员"
        align="center"
        prop="salesman"
        :formatter="preSaleIdFormat"
      />
      <el-table-column
        label="售中人员"
        align="center"
        prop="onSaleId"
        :formatter="onSaleIdFormat"
      />
      <el-table-column
        label="订单"
        align="center"
        v-hasPermi="['custom:order:list']"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleOnOrderClick(scope.row)"
            >详情
          </el-button>
        </template>
      </el-table-column>
      <el-table-column
        label="合同"
        align="center"
        v-hasPermi="['custom:contract:list']"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleOnContractClick(scope.row)"
            >详情
          </el-button>
        </template>
      </el-table-column>
      <el-table-column
        label="健康评估"
        align="center"
        v-hasPermi="['custom:healthy:list']"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleOnHealthSignClick(scope.row)"
            >详情
          </el-button>
        </template>
      </el-table-column>
      <el-table-column
        label="食谱计划"
        align="center"
        v-hasPermi="['recipes:plan:list']"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleOnMenuClick(scope.row)"
            >详情
          </el-button>
        </template>
      </el-table-column>
      <el-table-column
        label="外食热量"
        align="center"
        v-hasPermi="['custom:foodHeatStatistics:list']"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleClickHeatStatistics(scope.row)"
            >详情
          </el-button>
        </template>
      </el-table-column>
      <el-table-column
        label="打卡记录"
        align="center"
        v-hasPermi="['custom:wxUserLog:list']"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleClickCustomerPunchLog(scope.row)"
            >详情
          </el-button>
        </template>
      </el-table-column>

      <el-table-column
        label="操作"
        align="center"
        fixed="right"
        width="120"
        class-name="small-padding fixed-width"
        v-if="!isPartner"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['custom:customer:edit']"
            >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:customer:remove']"
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

    <!-- 添加或修改客户档案对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-row :gutter="15">
        <el-form ref="form" :model="form" :rules="rules" label-width="100px">
          <el-col :span="12">
            <el-form-item
              label="进粉渠道"
              prop="channelId"
              style="width: 400px"
            >
              <el-select
                v-model="form.channelId"
                placeholder="请选择"
                filterable
                clearable
                @change="channelAutoSelectNutritionist"
              >
                <el-option
                  v-for="dict in accountIdOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="进粉时间" prop="fansTime">
              <el-date-picker
                v-model="form.fansTime"
                type="datetime"
                placeholder="选择进粉时间"
                format="yyyy-MM-dd HH:mm"
                value-format="yyyy-MM-dd HH:mm"
                :picker-options="fanPickerOptions"
                @change="fanTimeAutoSelectNutritionist"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户名字" prop="name" style="width: 300px">
              <el-input v-model.trim="form.name" placeholder="请输入名字" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone" style="width: 300px">
              <el-input v-model.trim="form.phone" placeholder="请输入手机号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="售前" prop="salesman">
              <el-select v-model="form.salesman" placeholder="请选择">
                <el-option
                  v-for="dict in preSaleIdOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="售中" prop="salesman">
              <el-select v-model="form.onSaleId" placeholder="请选择">
                <el-option
                  v-for="dict in onSaleIdOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                />
              </el-select>
            </el-form-item>
          </el-col>    
          <el-col :span="12">
            <el-form-item label="主营养师" prop="mainDietitian">
              <el-select v-model="form.mainDietitian" placeholder="请选择" @change="handleOnDietIdChange">
                <el-option
                  v-for="dict in nutritionistIdOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                  
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="营养师助理" prop="assistantDietitian">
              <el-select v-model="form.assistantDietitian" placeholder="请选择">
                <el-option
                  v-for="dict in (screenNutriAssisIdOptions || nutriAssisIdOptions)"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="售后" prop="afterDietitian">
              <el-select v-model="form.afterDietitian" placeholder="请选择">
                <el-option
                  v-for="dict in (screenAfterSaleIdOptions || afterSaleIdOptions)"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 订单抽屉 -->
    <order-drawer ref="cusOrderDrawerRef" />
    <!-- 合同抽屉 -->
    <contract-drawer ref="cusContractDrawerRef"></contract-drawer>
    <!-- 健康评估弹窗 -->
    <physical-signs-dialog ref="physicalSignsDialogRef" />
    <!-- 外食热量统计 -->
    <heatStatisticsDrawer ref="heatStatisticsRef"></heatStatisticsDrawer>
    <!-- 食谱计划抽屉 -->
    <RecipesPlanDrawer ref="recipesPlanDrawerRef" />
    <!-- 客户打卡记录 -->
    <CustomerPunchLogDrawer
      ref="customerPunchLogDrawerRef"
    ></CustomerPunchLogDrawer>
  </div>
</template>

<script>
import {
  addCustomer,
  delCustomer,
  exportCustomer,
  getCustomer,
  listCustomer,
  updateCustomer,
} from "@/api/custom/customer";
import { getLiveSchedulByTime } from "@/api/custom/liveSchedul";

import store from "@/store";
import OrderDrawer from "@/components/OrderDrawer";
import PhysicalSignsDialog from "@/components/PhysicalSignsDialog";
import ContractDrawer from "@/components/ContractDrawer";
import HeatStatisticsDrawer from "@/components/HeatStatisticsDrawer";
import RecipesPlanDrawer from "@/components/RecipesPlanDrawer";
import CustomerPunchLogDrawer from "@/components/PunchLog/CustomerPunchLog";
import { listPhysicalSigns } from "@/api/custom/physicalSigns";
import { mapGetters } from "vuex";
import * as orderUtils from "@/utils/orderUtils";

export default {
  name: "Customer",
  components: {
    "order-drawer": OrderDrawer,
    "physical-signs-dialog": PhysicalSignsDialog,
    "contract-drawer": ContractDrawer,
    heatStatisticsDrawer: HeatStatisticsDrawer,
    RecipesPlanDrawer,
    CustomerPunchLogDrawer,
  },
  data() {
    const userId = store.getters && store.getters.userId;
    return {
      userId,
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
      // 客户档案表格数据
      customerCenterList: [],
      //
      fansChannelOptions: [],
      //
      accountIdOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        fansChannel: null,
        name: null,
        phone: null,
        mainDietitian: null,
        assistantDietitian: null,
        afterDietitian: null,
        salesman: null,
        onSaleId: null,
        physicalSignsId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "客户姓名不能为空", trigger: "blur" },
        ],
        phone: [
          { required: true, message: "手机号不能为空", trigger: "blur" },
          {
            required: true,
            trigger: "blur",
            pattern: /^[0-9]{5,11}$/,
            message: "手机号格式不正确",
          },
        ],
        salesman: [
          { required: true, message: "售前不能为空", trigger: "blur" },
        ],
        afterDietitian: [
          { required: true, message: "售后不能为空", trigger: "blur" },
        ],
        mainDietitian: [
          { required: true, message: "主营养师不能为空", trigger: "blur" },
        ],
        assistantDietitian: [
          { required: true, message: "营养师助理不能为空", trigger: "blur" },
        ],
        fansTime: [
          { required: true, message: "进粉时间不能为空", trigger: "blur" },
        ],
        channelId: [
          { required: true, message: "进粉渠道不能为空", trigger: "blur" },
        ],
      },
      fanPickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
      },
      //病史体征
      physicalSignsList: [],

       //下拉营养师对应助理、售后对于关系
      dietitianAfterAssistantOptions: [],
      //筛选过后的营养师助理列表
      screenNutriAssisIdOptions: null,
      //筛选过后面售后列表
      screenAfterSaleIdOptions: null,
    };
  },
  created() {
    this.getDicts("customer_fans_channel").then((response) => {
      this.fansChannelOptions = response.data;
    });
    this.getDicts("cus_account").then((response) => {
      if (this.isPartner) {
        const accRange = this.userRemark.split("|");
        this.accountIdOptions = accRange.reduce((arr, accId) => {
          const tarObj = response.data.find((obj) => obj.dictValue === accId);
          if (tarObj) {
            arr.push(tarObj);
          }
          return arr;
        }, []);
      } else {
        this.accountIdOptions = response.data;
      }
    });
    this.getDicts("dietitian_after_assistant").then((response) => {
      this.dietitianAfterAssistantOptions = response.data;
    });
    this.getList();
    listPhysicalSigns().then((response) => {
      this.physicalSignsList = response.rows;
    });
  },
  computed: {
    isPartner() {
      return this.roles && this.roles.includes("partner");
    },
    ...mapGetters([
      // 售前字典
      "preSaleIdOptions",
      //售中
      "onSaleIdOptions",
      // 售后字典
      "afterSaleIdOptions",
      // 主营养师字典
      "nutritionistIdOptions",
      // 助理营养师字典
      "nutriAssisIdOptions",
      //
      "userRemark",
      //
      "roles",
    ]),
  },
  methods: {
    /** 查询客户档案列表 */
    getList() {
      this.loading = true;
      listCustomer(this.queryParams).then((response) => {
        this.customerCenterList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 售前字典翻译
    preSaleIdFormat(row, column) {
      return this.selectDictLabel(this.preSaleIdOptions, row.salesman);
    },
    // 售中字典翻译
    onSaleIdFormat(row, column) {
      return this.selectDictLabel(this.onSaleIdOptions, row.onSaleId);
    },
    // 售后字典翻译
    afterSaleIdFormat(row, column) {
      return this.selectDictLabel(this.afterSaleIdOptions, row.afterDietitian);
    },
    // 主营养师字典翻译
    nutritionistIdFormat(row, column) {
      return this.selectDictLabel(
        this.nutritionistIdOptions,
        row.mainDietitian
      );
    },
    // 助理营养师字典翻译
    nutriAssisIdFormat(row, column) {
      return this.selectDictLabel(
        this.nutriAssisIdOptions,
        row.assistantDietitian
      );
    },
    fansChannelFormat(row, column) {
      return this.selectDictLabel(this.fansChannelOptions, row.fansChannel);
    },
    channelFormat(row, column) {
      return this.selectDictLabel(this.accountIdOptions, row.channelId);
    },
    handleOnOrderClick(row) {
      this.$refs["cusOrderDrawerRef"].showDrawer(row);
    },
    handleOnContractClick(row) {
      this.$refs["cusContractDrawerRef"].showDrawer(row);
    },
    handleOnBodySignClick(row) {
      // console.log(row);
    },
    handleOnHealthSignClick(row) {
      this.$refs["physicalSignsDialogRef"].showDialog(row);
    },
    handleOnMenuClick(row) {
      // console.log(row);
      this.$refs["recipesPlanDrawerRef"].showDrawer(row);
    },
    handleClickHeatStatistics(row) {
      this.$refs["heatStatisticsRef"].showDrawer(row);
    },
    handleClickCustomerPunchLog(row) {
      this.$refs["customerPunchLogDrawerRef"].showDrawer(row);
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
        phone: null,
        email: null,
        address: null,
        payDate: null,
        startDate: null,
        purchaseNum: null,
        payTotal: null,
        mainDietitian: null,
        assistantDietitian: null,
        afterDietitian: null,
        salesman: null,
        onSaleId: null,
        chargePerson: null,
        fansChannel: 0,
        fansTime: null,
        followStatus: 0,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null,
        delFlag: null,
      };
      this.screenNutriAssisIdOptions = this.nutriAssisIdOptions;
      this.screenAfterSaleIdOptions = this.afterSaleIdOptions;
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加客户档案";

      // 选择默认销售
      const tarSale = this.preSaleIdOptions.find(
        (obj) => parseInt(obj.dictValue) === this.userId
      );
      this.form.salesman = tarSale ? tarSale.dictValue : null;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getCustomer(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改客户档案";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateCustomer(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addCustomer(this.form).then((response) => {
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
        '是否确认删除客户姓名为为"' + row.name + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delCustomer(ids);
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
      this.$confirm("是否确认导出所有客户档案数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportCustomer(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    channelAutoSelectNutritionist(channelValue) {
      this.form.fansChannel = channelValue == "" ? null : channelValue;
      if (
        channelValue == undefined ||
        channelValue == null ||
        channelValue == ""
      ) {
        this.form.mainDietitian = null;
        return;
      }
      if (this.form.fansTime == undefined || this.form.fansTime == null) {
        this.form.mainDietitian = null;
        return;
      }
      this.autoSelectNutritionist();
    },
    fanTimeAutoSelectNutritionist(fansTime) {
      this.form.fansTime = fansTime;
      if (fansTime == undefined || fansTime == null) {
        this.form.mainDietitian = null;
        return;
      }
      if (
        this.form.fansChannel == undefined ||
        this.form.fansChannel == null ||
        this.form.fansChannel == ""
      ) {
        this.form.mainDietitian = null;
        return;
      }
      this.autoSelectNutritionist();
    },
    autoSelectNutritionist() {
      getLiveSchedulByTime({
        fanChannel: this.form.fansChannel,
        liveStartTimeString: encodeURIComponent(this.form.fansTime),
      }).then((response) => {
        if (response.code === 200) {
          let live = response.data;
          if (
            live != undefined &&
            live != null &&
            live.liveNutritionistId != null &&
            this.nutritionistIdOptions != null
          ) {
            let mainDietitian = null;
            this.nutritionistIdOptions.forEach((item, index) => {
              if (live.liveNutritionistId == item.dictValue) {
                mainDietitian = live.liveNutritionistId;
              }
              if (index == this.nutritionistIdOptions.length - 1) {
                this.form.mainDietitian = mainDietitian;
              }
            });
          } else {
            this.form.mainDietitian = null;
          }
        } else {
          this.form.mainDietitian = null;
        }
      });
    },
    //监听营养师下拉列表
    handleOnDietIdChange(val) {
       let assistantAfterArray = orderUtils.getAfterSaleAndAssistantByDietId(this.dietitianAfterAssistantOptions, val);
       this.form = {
          ...this.form,
          assistantDietitian: assistantAfterArray ? orderUtils.getRandomValueByArray(assistantAfterArray[0]) : 0,
          afterDietitian: assistantAfterArray ? orderUtils.getRandomValueByArray(assistantAfterArray[1]) : 0,
        };
        this.screenNutriAssisIdOptions = orderUtils.getAfterSaleOrAssistantByIds(this.nutriAssisIdOptions, assistantAfterArray[0]);
        this.screenAfterSaleIdOptions = orderUtils.getAfterSaleOrAssistantByIds(this.afterSaleIdOptions, assistantAfterArray[1]);
    }
    
  },
};
</script>
