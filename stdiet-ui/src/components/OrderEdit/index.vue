<template>
  <el-dialog
    :title="title"
    :visible.sync="visible"
    width="820px"
    append-to-body
    :close-on-click-modal="false"
    @closed="handleOnClosed"
  >
    <el-row :gutter="15">
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-col :span="16" v-show="orderModuleshow.orderTypeShow">
          <el-form-item label="订单类型" prop="orderTypeName">
            <el-input
              v-model="form.orderTypeName"
              placeholder=""
              :disabled="true"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8" v-show="orderModuleshow.amountShow">
          <el-form-item label="成交金额" prop="amount">
            <el-input v-model.trim="form.amount" placeholder="请输入金额" />
          </el-form-item>
        </el-col>
        <el-col :span="8" v-show="orderModuleshow.payTypeShow">
          <el-form-item label="收款方式" prop="payTypeId">
            <el-select v-model="form.payTypeId" filterable clearable placeholder="请选择">
              <el-option
                v-for="dict in payTypeIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8" v-show="orderModuleshow.accountShow">
          <el-form-item label="进粉渠道" prop="accountId">
            <el-select
              v-model="form.accountId"
              placeholder="请选择"
              filterable clearable
              @change="handleOnChannelIdChange"
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
        <el-col :span="8"  v-show="orderModuleshow.serveTimeShow">
          <el-form-item label="服务时长" prop="serveTime">
            <el-select v-model="form.serveTimeId" placeholder="请选服">
              <el-option
                v-for="dict in serveTimeIdOption"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8" v-show="orderModuleshow.giveServeDayShow">
          <el-form-item label="赠送时长" prop="giveServeDay">
            <el-select v-model="form.giveServeDay" placeholder="请选择">
              <el-option
                v-for="dict in giveTimeIdOption"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8" v-show="orderModuleshow.conditioningProjectShow">
          <el-form-item label="调理项目" prop="conditioningProjectId">
            <el-select
              v-model="form.conditioningProjectId"
              placeholder="请选择"
              filterable clearable
            >
              <el-option
                v-for="dict in conditioningProjectIdOption"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8" v-show="orderModuleshow.preSaleShow">
          <el-form-item label="售前" prop="preSaleId">
            <el-select v-model="form.preSaleId" filterable clearable placeholder="请选择">
              <el-option
                v-for="dict in preSaleIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8" v-show="orderModuleshow.businessAffairShow">
          <el-form-item label="商务" prop="businessAffairId" >
            <el-select v-model="form.businessAffairId" placeholder="请选择" filterable clearable>
              <el-option
                v-for="dict in bdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8" v-show="orderModuleshow.pushPreSaleShow">
          <el-form-item label="售前推送" prop="preSaleId">
            <el-select v-model="form.pushPreSaleId" placeholder="请选择">
              <el-option
                v-for="dict in pushPreSaleIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8" v-show="orderModuleshow.onSaleShow">
          <el-form-item label="售中" prop="onSaleId">
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
        <el-col :span="8" v-show="orderModuleshow.nutritionistShow">
          <el-form-item label="主营养师" prop="nutritionistId">
            <el-select v-model="form.nutritionistId" filterable clearable placeholder="请选择" @change="handleOnDietIdChange">
              <el-option
                v-for="dict in nutritionistIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8" v-show="orderModuleshow.nutriAssisShow">
          <el-form-item label="助理营养师" prop="nutriAssisId">
            <el-select v-model="form.nutriAssisId" filterable clearable placeholder="请选择">
              <el-option
                v-for="dict in (screenNutriAssisIdOptions || nutriAssisIdOptions)"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8" v-show="orderModuleshow.afterSaleShow">
          <el-form-item label="售后"  prop="afterSaleId">
            <el-select v-model="form.afterSaleId" filterable clearable placeholder="请选择">
              <el-option
                v-for="dict in (screenAfterSaleIdOptions || afterSaleIdOptions)"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8" v-show="orderModuleshow.plannerShow">
          <el-form-item label="策划" prop="plannerId">
            <el-select v-model="form.plannerId" placeholder="请选择">
              <el-option
                v-for="dict in plannerIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8" v-show="orderModuleshow.plannerAssisShow">
          <el-form-item label="策划助理" prop="plannerAssisId">
            <el-select v-model="form.plannerAssisId" placeholder="请选择">
              <el-option
                v-for="dict in plannerAssisIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8" v-show="orderModuleshow.operatorShow">
          <el-form-item label="运营" prop="operatorId">
            <el-select v-model="form.operatorId" placeholder="请选择">
              <el-option
                v-for="dict in operatorIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8" v-show="orderModuleshow.operatorAssisShow">
          <el-form-item label="运营助理" prop="operatorAssisId">
            <el-select v-model="form.operatorAssisId" placeholder="请选择">
              <el-option
                v-for="dict in operatorAssisIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <!--<el-col :span="8">
          <el-form-item label="推荐人" prop="recommender">
            <el-input v-model="form.recommender" placeholder="请输入推荐人" />
          </el-form-item>
        </el-col>-->
        <el-col :span="10" v-show="orderModuleshow.orderTimeShow">
          <el-form-item label="成交时间" prop="orderTime">
            <el-date-picker
              style="width: 182.5px"
              v-model="form.orderTime"
              type="datetime"
              placeholder="选择成交时间"
              format="yyyy-MM-dd HH:mm"
              value-format="yyyy-MM-dd HH:mm:ss"
              :picker-options="orderPickerOptions"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12" v-show="orderModuleshow.startTimeShow">
          <el-form-item label="服务开始时间" prop="startTime" label-width="160">
            <el-date-picker
              style="width: 182.5px"
              v-model="form.startTime"
              type="date"
              placeholder="选择开始日期"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col v-hasPermi="['custom:order:review']" v-show="orderModuleshow.reviewStatusShow">
          <el-form-item label="审核状态" prop="reviewStatus">
            <el-select v-model="form.reviewStatus" placeholder="请选择审核状态">
              <el-option
                v-for="dict in reviewStatusOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col v-show="orderModuleshow.remarkShow">
          <el-form-item label="备注" prop="remark">
            <el-input
              v-model="form.remark"
              type="textarea"
              placeholder="请输入内容"
            />
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { addOrder, updateOrder } from "@/api/custom/order";
import dayjs from "dayjs";
import { mapGetters } from "vuex";
import * as orderTypeData from "@/utils/orderType";
import * as orderUtils from "@/utils/orderUtils";
export default {
  name: "OrderEdit",
  props: {
    id: {
      type: String,
    },
  },
  data() {
    const checkStartTime = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("开始时间不能为空"));
      }
      if (!this.form.orderTime) {
        return callback(new Error("请先选择成交时间"));
      }
      /*if (
        dayjs(this.form.startTime).diff(dayjs(this.form.orderTime), "day") < 0
      ) {
        return callback(new Error("开始时间不能先于成交时间"));
      }*/
      callback();
    };
    const checkOrderTime = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("成交时间不能为空"));
      }
      callback();
    };
    return {
      title: "",
      data: undefined,
      callback: undefined,
      form: {},
      visible: false,
      // 表单校验
      rules: {
        customer: [
          { required: true, message: "客户姓名不能为空", trigger: "blur" },
        ],
        amount: [{ required: true, message: "金额不能为空", trigger: "blur" }],
        /*phone: [
          { required: true, message: "手机号不能为空", trigger: "blur" },
          {
            required: true,
            trigger: "blur",
            pattern: /^[0-9]{5,11}$/,
            message: "手机号格式不正确",
          },
        ],*/
        orderTime: [
          { required: true, message: "成交时间不能为空", trigger: "blur" },
          { required: true, trigger: "blur", validator: checkOrderTime },
        ],
        startTime: [
          { required: true, message: "开始时间不能为空", trigger: "blur" },
          { required: true, trigger: "blur", validator: checkStartTime },
        ],

        // payTypeId: [
        //   {required: true, message: "收款方式不能为空", trigger: "blur"}
        // ],
        // accountId: [
        //   {required: true, message: "账号不能为空", trigger: "blur"}
        // ],
        // serveTimeId: [
        //   {required: true, message: "服务时长不能为空", trigger: "blur"}
        // ],
      },
      pickerOptions: {
        shortcuts: [
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近三个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            },
          },
        ],
      },
      orderPickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
      },
      fanPickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
      },
      startPickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now();
        },
      },
      // 收款方式字典
      payTypeIdOptions: [],
      // 账号
      accountIdOptions: [],
      // 服务时长
      serveTimeIdOption: [],
      // 赠送时长
      giveTimeIdOption: [],
      //调理项目
      conditioningProjectIdOption: [],
      // 审核状态
      reviewStatusOptions: [],
      //下拉列表对应关系(用于选择收款账号自动选择策划、策划助理、运营、运营助理)
      orderDropdownCorrespondingOptions: [],
      //下拉营养师对应助理、售后对于关系
      dietitianAfterAssistantOptions: [],
      //筛选过后的营养师助理列表
      screenNutriAssisIdOptions: null,
      //筛选过后面售后列表
      screenAfterSaleIdOptions: null,
      //订单模块控制
      orderModuleshow: Object.assign({}, orderTypeData.orderModuleshow)
    };
  },
  created() {
    this.getDicts("cus_pay_type").then((response) => {
      this.payTypeIdOptions = response.data;
    });
    this.getDicts("cus_account").then((response) => {
      this.accountIdOptions = response.data;
      // console.log(response.data);
      this.accountIdOptions.splice(0, 0, {
        dictLabel: "无",
        dictValue: "0",
      });
    });
    this.getDicts("cus_serve_time").then((response) => {
      this.serveTimeIdOption = response.data;
    });
    this.getDicts("give_serve_daye_type").then((response) => {
      this.giveTimeIdOption = response.data;
    });
    this.getDicts("conditioning_project").then((response) => {
      this.conditioningProjectIdOption = response.data;
    });
    this.getDicts("cus_review_status").then((response) => {
      this.reviewStatusOptions = response.data;
    });
    this.getDicts("order_dropdown_corresponding").then((response) => {
      this.orderDropdownCorrespondingOptions = response.data;
    });
    this.getDicts("dietitian_after_assistant").then((response) => {
      this.dietitianAfterAssistantOptions = response.data;
    });
  },
  computed: {
    ...mapGetters([
      // 售前字典
      "preSaleIdOptions",
      // 售中字典
      "onSaleIdOptions",
      // 售后字典
      "afterSaleIdOptions",
      // 主营养师字典
      "nutritionistIdOptions",
      // 助理营养师字典
      "nutriAssisIdOptions",
      // 策划字典
      "plannerIdOptions",
      // 策划助理字典
      "plannerAssisIdOptions",
      // 运营字典
      "operatorIdOptions",
      // 运营助理字典
      "operatorAssisIdOptions",
      //售前推送
      "pushPreSaleIdOptions",
      //商务
      "bdOptions"
    ]),
  },
  methods: {
    showDialog(data, callback) {
      // this.data = data;
      this.callback = callback;
      this.reset(data);

      this.title = `${data.orderId ? "修改" : "创建"}「${
        data.customer
      }」客户订单`;
      this.visible = true;
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.orderId != null) {
            updateOrder(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.visible = false;
                this.callback && this.callback();
              }
            });
          } else {
            addOrder(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.visible = false;
                this.callback && this.callback();
              }
            });
          }
        }
      });
    },
    reset(obj = {}) {
      const defaultPayType = this.payTypeIdOptions.find(
        (opt) => opt.remark === "default"
      );
      const defaultServeTime = this.serveTimeIdOption.find(
        (opt) => opt.remark === "default"
      );
      const defaultGiveServeTime = this.giveTimeIdOption.find(
        (opt) => opt.remark === "default"
      );
      const defaultConditioningProjectIdOption = this.conditioningProjectIdOption.find(
        (opt) => opt.remark === "default"
      );
      const defaultAccount = this.accountIdOptions.find(
        (opt) => opt.remark === "default"
      );
      const defaultPresale = this.preSaleIdOptions.find(
        (opt) => opt.remark === "default"
      );
      const defaultAftersale = this.afterSaleIdOptions.find(
        (opt) => opt.remark === "default"
      );
      const defaultNutritionist = this.nutritionistIdOptions.find(
        (opt) => opt.remark === "default"
      );
      const defaultNutriAssis = this.nutriAssisIdOptions.find(
        (opt) => opt.remark === "default"
      );

      const accountId = defaultAccount ? parseInt(defaultAccount.dictValue) : 0;

      const planningAndOperationValue = this.orderDropdownCorrespondingOptions.find(
        (opt) => parseInt(opt.dictValue) === accountId
      );

      const [
        plannerId,
        plannerAssisId,
        operatorId,
        operatorAssisId,
      ] = planningAndOperationValue
        ? planningAndOperationValue.dictLabel
            .split("|")
            .map((str) => parseInt(str))
        : [0, 0, 0, 0];

      this.form = {
        orderId: null,
        customer: null,
        //phone: null,
        amount: null,
        weight: null,
        plannerId,
        plannerAssisId,
        operatorId,
        operatorAssisId,
        startTime: dayjs().add(3, "day").format("YYYY-MM-DD"),
        pauseTime: null,
        payTypeId: defaultPayType ? parseInt(defaultPayType.dictValue) : null,
        preSaleId: defaultPresale ? parseInt(defaultPresale.dictValue) : null,
        pushPreSaleId: null,
        onSaleId: null,
        createBy: null,
        createTime: null,
        afterSaleId: defaultAftersale
          ? parseInt(defaultAftersale.dictValue)
          : null,
        updateBy: null,
        updateTime: null,
        nutritionistId: defaultNutritionist
          ? parseInt(defaultNutritionist.dictValue)
          : null,
        remark: null,
        nutriAssisId: defaultNutriAssis
          ? parseInt(defaultNutriAssis.dictValue)
          : null,
        recommender: null,
        orderTime: dayjs().format("YYYY-MM-DD HH:mm:ss"),
        serveTimeId: defaultServeTime
          ? parseInt(defaultServeTime.dictValue)
          : null,
        reviewStatus: "no",
        giveServeDay: defaultGiveServeTime
          ? parseInt(defaultGiveServeTime.dictValue)
          : null,
        conditioningProjectId: defaultConditioningProjectIdOption
          ? parseInt(defaultConditioningProjectIdOption.dictValue)
          : null,
        accountId,
        ...obj,
      };
      this.resetForm("form");
      
      let orderTypeList = [parseInt(this.form.orderType), parseInt(this.form.orderCountType), parseInt(this.form.orderMoneyType)];

      let resultArray = orderTypeData.dealOrderModuleshowByOrderType(orderTypeList, this.orderModuleshow, this.form);
      this.orderModuleshow = resultArray[0];

      this.screenNutriAssisIdOptions = this.nutriAssisIdOptions;
      this.screenAfterSaleIdOptions = this.afterSaleIdOptions;

      console.log("重置操作");
    },
    handleOnClosed() {
      this.reset();
    },
    // 取消按钮
    cancel() {
      this.visible = false;
      // this.reset();
    },
    //根据收款账号ID初始化策划、策划助理、运营、运营助理
    initPlanningAndOperation() {
      if (this.form.accountId != null && this.form.accountId != undefined) {
        const planningAndOperationValue = this.orderDropdownCorrespondingOptions.find(
          (opt) => parseInt(opt.dictValue) === this.form.accountId
        );
        const [
          plannerId,
          plannerAssisId,
          operatorId,
          operatorAssisId,
        ] = planningAndOperationValue
          ? planningAndOperationValue.dictLabel
              .split("|")
              .map((str) => parseInt(str))
          : [0, 0, 0, 0];

        this.form = {
          ...this.form,
          plannerId,
          plannerAssisId,
          operatorId,
          operatorAssisId,
        };
      }
    },
    handleOnChannelIdChange(val) {
      console.log(val);
      this.initPlanningAndOperation();
    },
    //监听营养师下拉列表
    handleOnDietIdChange(val) {
      console.log(val);
       let assistantAfterArray = orderUtils.getAfterSaleAndAssistantByDietId(this.dietitianAfterAssistantOptions, val);
       console.log(assistantAfterArray);
       this.form = {
          ...this.form,
          nutriAssisId: assistantAfterArray ? orderUtils.getRandomValueByArray(assistantAfterArray[0]) : 0,
          afterSaleId: assistantAfterArray ? orderUtils.getRandomValueByArray(assistantAfterArray[1]) : 0,
        };
        this.screenNutriAssisIdOptions = orderUtils.getAfterSaleOrAssistantByIds(this.nutriAssisIdOptions, assistantAfterArray[0]);
        this.screenAfterSaleIdOptions = orderUtils.getAfterSaleOrAssistantByIds(this.afterSaleIdOptions, assistantAfterArray[1]);
    },
  },
  watch: {
    // 监听收款账号的变化
    // "form.accountId": function (newVal, oldVal) {
    //   // console.log("updte");
    //   this.initPlanningAndOperation();
    // },
  },
};
</script>
