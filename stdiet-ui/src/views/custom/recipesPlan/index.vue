<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" >
      <el-form-item label="客户信息" prop="customer">
        <el-input
          v-model="queryParams.customer"
          placeholder="请输入客户姓名或手机号"
          clearable
          size="small"
        />
      </el-form-item>
      <el-form-item label="食谱开始日期范围" prop="planStartDateScope" label-width="130px">
        <el-date-picker
          v-model="planStartDateScope"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="营养师" prop="nutritionistId">
        <el-select v-model="queryParams.nutritionistId" placeholder="请选择营养师" clearable size="small">
          <el-option
            v-for="dict in nutritionistIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="营养师助理" prop="nutritionistAssisId">
        <el-select v-model="queryParams.nutritionistAssisId" placeholder="请选择营养师助理" clearable size="small">
          <el-option
            v-for="dict in nutriAssisIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <div><span style="color:#E6A23C;font-family:PingFang SC">
          注意事项：
          <br/>1、2021年1月开始的订单才会自动生成食谱计划</span></div>
    <el-row :gutter="10" class="mb8" style="margin-top:10px;">
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['recipes:recipesPlan:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['recipes:recipesPlan:export']"
        >导出</el-button>
      </el-col>
      <!--<div><span style="margin-left:10px;font-size:16px;color:#E6A23C;font-family:PingFang SC">备注：2021年1月开始的订单才会自动生成食谱计划</span></div>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recipesPlanList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="客户姓名" align="center" prop="customer" />
      <el-table-column label="客户手机号" align="center" prop="hidePhone" width="180"/>
      <el-table-column label="食谱日期范围" align="center" prop="scopeDate" width="200"/>
      <el-table-column label="营养师" align="center" prop="nutritionist" />
      <el-table-column label="营养师助理" align="center" prop="nutritionistAssis" width="180"/>
      <el-table-column label="是否发送" align="center" prop="sendFlag">
        <!--<template slot-scope="scope">
          <span>{{ scope.row.sendFlag == 1 ? "已发送" : "未发送"}}</span>
        </template>(.sendFlag == 1) ? 'success' : 'warning'-->
        <template slot-scope="scope">
          <el-tag
            :type="getTagType(scope.row)"
            disable-transitions>
            {{scope.row.sendFlag == 1 ? "已发送" : "未发送"}}
          </el-tag>
        </template>

      </el-table-column>
      <el-table-column label="食谱发送时间" align="center" prop="sendTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.sendTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="300">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['recipes:recipesPlan:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="allRecipesPlanQueryParam.orderId = scope.row.orderId; getAllPlanByOrderId()"
          >查看完整计划</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="getOrderDetail(scope.row)"
            v-hasPermi="['custom:order:query']"
          >查看订单</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="getCustomerSign(scope.row)"
            v-hasPermi="['custom:customer:query']"
          >查看体征</el-button>
          <!--<el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['recipes:recipesPlan:remove']"
          >删除</el-button>-->
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

    <!-- 添加或修改食谱计划对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="食谱是否已发送" prop="sendFlag" label-width="120px">
          <el-select v-model="form.sendFlag" placeholder="请选择">
            <el-option label="否" :value="parseInt('0')" />
            <el-option label="是" :value="parseInt('1')" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 查看完整计划 -->
    <el-dialog title="食谱计划表" v-if="allRecipesPlanOpen" :visible.sync="allRecipesPlanOpen" width="800px" append-to-body>
      <el-form :model="allRecipesPlanQueryParam" ref="allPlanQueryFrom" :inline="true">
        <el-form-item label="发送状态" prop="sendFlag">
          <el-select v-model="allRecipesPlanQueryParam.sendFlag" placeholder="请选择">
            <el-option label="全部" :value="null" />
            <el-option label="未发送" :value="parseInt('0')" />
            <el-option label="已发送" :value="parseInt('1')" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="cyan" icon="el-icon-search" size="mini" @click="getAllPlanByOrderId()">搜索</el-button>
        </el-form-item>
      </el-form>

      <el-table v-loading="loading" :data="allRecipesPlanList" width="700px">
        <el-table-column label="客户姓名" align="center" prop="customer" />
        <!--<el-table-column label="营养师名称" align="center" prop="nutritionist" />
        <el-table-column label="营养师助理名称" align="center" prop="nutritionistAssis" />-->

        <el-table-column label="食谱时间范围" align="center" prop="scopeDate" width="250"/>
        <el-table-column label="食谱是否发送" align="center" prop="sendFlag">
          <!--<template slot-scope="scope">
            <span>{{ scope.row.sendFlag == 1 ? "已发送" : "未发送"}}</span>
          </template>(.sendFlag == 1) ? 'success' : 'warning'-->
          <template slot-scope="scope">
            <el-tag
              :type="getTagType(scope.row)"
              disable-transitions>
              {{scope.row.sendFlag == 1 ? "已发送" : "未发送"}}
            </el-tag>
          </template>

        </el-table-column>
        <el-table-column label="食谱发送时间" align="center" prop="sendTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.sendTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="allRecipesPlanTotal>0"
        :total="allRecipesPlanTotal"
        :page.sync="allRecipesPlanQueryParam.pageNum"
        :limit.sync="allRecipesPlanQueryParam.pageSize"
        @pagination="getAllPlanByOrderId"
      />
      <!--<div slot="footer" class="dialog-footer">
        <el-button @click="allRecipesPlanOpen = false">关 闭</el-button>
      </div>-->
    </el-dialog>


    <!-- 查看体征 -->
    <el-dialog title="客户体征" v-if="customerOpen" :visible.sync="customerOpen" width="1000px" append-to-body>
      <el-table :show-header="false" :data="customerSignList" border :cell-style="columnStyle" style="width: 100%;">
        <el-table-column width="140" prop="attr_name_one">
        </el-table-column>
        <el-table-column prop="value_one">
          <template slot-scope="scope">
            <AutoHideMessage :data="scope.row.value_one == null ? '' : (scope.row.value_one+'')" :maxLength="20"/>
          </template>
        </el-table-column>
        <el-table-column width="140" prop="attr_name_two"></el-table-column>
        <el-table-column prop="value_two">
          <template slot-scope="scope">
            <AutoHideMessage :data="scope.row.value_two == null ? '' : (scope.row.value_two+'')" :maxLength="20"/>
          </template>
        </el-table-column>
        <el-table-column width="140" prop="attr_name_three"></el-table-column>
        <el-table-column prop="value_three">
          <template slot-scope="scope">
            <AutoHideMessage :data="scope.row.value_three == null ? '' : (scope.row.value_three+'')" :maxLength="20"/>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
    <!-- 查看订单 -->
    <el-dialog title="订单详情" v-if="orderDetailOpen" :visible.sync="orderDetailOpen" width="1000px" append-to-body>
      <el-table :show-header="false" :data="orderDetailList" border :cell-style="columnStyle" style="width: 100%;">
        <el-table-column width="120" prop="attr_name_one">
        </el-table-column>
        <el-table-column prop="value_one">
          <template slot-scope="scope" >
            <el-tag v-if="scope.row.attr_name_one === '订单状态'" :type="scope.row.value_two === '2' ? 'success' : scope.row.value_one === '0'? '': 'danger'" disable-transitions>
              {{scope.row.value_two === '2' ? '已完成': scope.row.value_one ==='0'? '进行中': '已暂停'}}
            </el-tag>
            <AutoHideMessage v-else :data="scope.row.value_one == null ? '' : (scope.row.value_one+'')" :maxLength="20"/>
          </template>
        </el-table-column>
        <el-table-column width="120" prop="attr_name_two"></el-table-column>
        <el-table-column prop="value_two">
          <template slot-scope="scope" >
            <el-tag v-if="scope.row.attr_name_two === '审核状态'" :type="scope.row.value_two === 'yes' ? 'success' : 'danger'">
              {{scope.row.value_two === 'yes' ? '已审核' : '未审核'}}
            </el-tag>
            <AutoHideMessage v-else :data="scope.row.value_two == null ? '' : (scope.row.value_two+'')" :maxLength="20"/>
          </template>
        </el-table-column>
        <el-table-column width="120" prop="attr_name_three"></el-table-column>
        <el-table-column prop="value_three">
          <template slot-scope="scope" >
            <AutoHideMessage :data="scope.row.value_three == null ? '' : (scope.row.value_three+'')" :maxLength="20"/>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
  import { listRecipesPlan, getRecipesPlan, updateRecipesPlan,exportRecipesPlan } from "@/api/custom/recipesPlan";
  import { getCustomerAndSignByPhone } from "@/api/custom/customer";
  import {getInfoDetail,getOptions} from "@/api/custom/order";
  import AutoHideMessage from "@/components/AutoHideMessage";
  import dayjs from 'dayjs';
  import store from "@/store";
  const nextDate = dayjs().add(1, 'day').format("YYYY-MM-DD");
  const weekDate = dayjs().add(6, 'day').format("YYYY-MM-DD");
  const userId = store.getters && store.getters.userId;
  export default {
    name: "recipesPlan",
    data() {
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
        // 食谱计划表格数据
        recipesPlanList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          customer: null,
          startDate: null,
          endDate: null,
          nutritionistId: null,
          nutritionistAssisId: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
        },
        //食谱开始时间范围
        planStartDateScope:[nextDate, nextDate],
        //用户体征数据
        customerSignList: [],
        //用户体征弹窗状态
        customerOpen: false,
        //订单对于所有计划安排数据
        allRecipesPlanList:[],
        //订单弹窗状态
        allRecipesPlanOpen: false,
        //订单弹窗中查询参数
        allRecipesPlanQueryParam: {
          pageNum: 1,
          pageSize: 10,
          orderId: null,
          sendFlag: 0
        },
        //订单弹窗中列表数据的总条数
        allRecipesPlanTotal: 0,
        //订单详情数据
        orderDetailList:[],
        //订单详情弹窗
        orderDetailOpen:false,
        //营养师
        nutritionistIdOptions:[],
        //营养师助理
        nutriAssisIdOptions:[],
        //订单详情的标题，按竖显示
        orderTitleData:[["订单状态","审核状态","成交时间"],["姓名","金额","体重"],["手机号","服务时长","赠送时长"],["调理项目","收款方式","售前"],
          ["售后","营养师","助理营养师"],["账号","策划","策划助理"],["运营","运营助理","进粉时间"],["开始时间","结束时间","备注"]],
        //订单详情的属性名称，与标题对应，按竖显示
        orderValueData:[["status","reviewStatus","orderTime"],["customer","amount","weight"],["phone","serveTime","giveServeDay"],["conditioningProject","payType","preSale"],
          ["afterSale","nutritionist","nutriAssis"],["account","planner","plannerAssis"],["operator","operatorAssis","becomeFanTime"],["startTime","serverEndTime","serverEndTime"]],
        signTitleData:[["姓名","手机号","性别"],["年龄(岁)","身高(厘米)","体重(斤)"],["南、北方","病史","忌口或过敏源"],["是否便秘","是否熬夜失眠","是否经常运动"],
          ["饮食方式","饮食备注","饮食特点"],["工作职业","是否上夜班","久坐多还是运动多"],["是否浑身乏力","是否减脂反弹","意识到生活习惯是减脂的关键"],
          ["睡觉时间","起床时间","方便沟通时间"],["湿气数据","气血数据","减脂经历"],["减脂遇到的困难","备注","创建时间"]],
        signValueData:[["name","phone","sex"],["age","tall","weight"],["position","signString","dishesIngredientId"],["constipation","staylate","motion"],
          ["makeFoodType","remarks","makeFoodTaste"],["vocation","night","walk"],["weakness","rebound","crux"],["sleepTime","getupTime","connectTime"],["bloodData","moistureDate","experience"],["difficulty","comments","createTime"]]
      };

    },
    components: {
      AutoHideMessage
    },
    created() {
      getOptions().then(response => {
        const options = response.data.reduce((opts, cur) => {
          if (!opts[cur.postCode]) {
            opts[cur.postCode] = [{dictValue: null, dictLabel: '全部', remark: null}];
          }
          opts[cur.postCode].push({dictValue: cur.userId, dictLabel: cur.userName, remark: cur.remark})
          return opts;
        }, {})
        this.nutritionistIdOptions = options['nutri'] || [];
        this.nutriAssisIdOptions = options['nutri_assis'] || [];
        const defaultNutritionist = this.nutritionistIdOptions.find(opt => opt.dictValue == userId);
        const defaultNutriAssisId = this.nutriAssisIdOptions.find(opt => opt.dictValue == userId);
        if(defaultNutritionist){
           this.queryParams.nutritionistId = userId;
        }
        if(defaultNutriAssisId){
          this.queryParams.nutritionistAssisId = userId;
        }
        this.getList();
      })
    },
    methods: {
      /** 查询食谱计划列表 */
      getList() {
        this.loading = true;
        if(this.planStartDateScope != null && this.planStartDateScope.length > 0){
          this.queryParams.startDate = dayjs(this.planStartDateScope[0]).format('YYYY-MM-DD');
          this.queryParams.endDate =  dayjs(this.planStartDateScope[1]).format('YYYY-MM-DD');
        }else{
          this.queryParams.startDate = null;
          this.queryParams.endDate = null;
        }
        listRecipesPlan(this.queryParams).then(response => {
          this.recipesPlanList = response.rows;
          this.recipesPlanList.forEach(function(item, index){
            item.scopeDate = dayjs(item.startDate).format("YYYY-MM-DD")+" 到 "+dayjs(item.endDate).format("YYYY-MM-DD")
          });
          this.total = response.total;
          this.loading = false;
        });
      },
      /** 查询客户体征 */
      getCustomerSign(row) {
        this.customerSignList = [];
        let message = "未找到该客户，请检查订单手机号与客户体征手机号是否一致";
        if(row.phone == null || row.phone == ""){
          this.alert(message);
          return;
        }
        getCustomerAndSignByPhone({"phone": row.phone}).then(response => {
          if(response.data == null){
            this.alert(message);
            return;
          }
          let sign = response.data.sign;
          sign.name = response.data.name;
          sign.phone = response.data.phone;
          sign.createTime = response.data.createTime;
          sign.sex = sign.sex == 0 ? `男` : '女';
          sign.position = sign.position == 0 ? `南方` : '北方';
          sign.signString = this.getSignString(sign.signList);
          sign.constipation = sign.constipation == 0 ? "是" : "否";
          sign.staylate = sign.staylate == 0 ? "是" : "否";
          sign.motion = sign.motion == 0 ? "是" : "否";
          sign.makeFoodType = sign.makeFoodType == 0 ? `自己做` : '外面吃';
          sign.makeFoodTaste = sign.makeFoodTaste == 0 ? "清淡" : '重口味';
          sign.night = sign.night == 0 ? `是` : '否';
          sign.walk = sign.walk == 0 ? `久坐多` : '走动多';
          sign.weakness = sign.weakness == 0 ? `是` : '否';
          sign.rebound = sign.rebound == 0 ? `是` : '否';
          sign.crux = sign.crux == 0 ? `是` : '否';
          sign.sleepTime = sign.sleepTime+"点";
          sign.getupTime = sign.getupTime + "点";
          sign.connectTime = sign.connectTime +"点";
          for(let i = 0; i < this.signTitleData.length; i++){
            this.customerSignList.push({"attr_name_one": this.signTitleData[i][0],"value_one": sign[this.signValueData[i][0]],"attr_name_two": this.signTitleData[i][1],"value_two": sign[this.signValueData[i][1]],"attr_name_three": this.signTitleData[i][2],"value_three": sign[this.signValueData[i][2]]});
          }
          this.customerOpen = true;
        });
      },
      getAllPlanByOrderId(){
        if(this.allRecipesPlanQueryParam.sendFlag === ""){
          this.allRecipesPlanQueryParam.sendFlag = null;
        }
        listRecipesPlan(this.allRecipesPlanQueryParam).then(response => {
            this.allRecipesPlanList = response.rows;
            this.allRecipesPlanList.forEach(function(item, index){
              item.scopeDate = dayjs(item.startDate).format("YYYY-MM-DD")+" 到 "+dayjs(item.endDate).format("YYYY-MM-DD")
            });
            this.allRecipesPlanOpen = true;
            this.allRecipesPlanTotal = response.total;
        });
      },
      getOrderDetail(row){
        this.orderDetailList = [];
        getInfoDetail({'orderId': row.orderId}).then(response => {
          response.data.weight = response.data.weight != null ? (response.data.weight +"斤") : "";
          response.data.giveServeDay = response.data.giveServeDay != null ? (response.data.giveServeDay +"天") : "";
          for(let i = 0; i < this.orderTitleData.length; i++){
             this.orderDetailList.push({"attr_name_one": this.orderTitleData[i][0],"value_one": response.data[this.orderValueData[i][0]],"attr_name_two": this.orderTitleData[i][1],"value_two": response.data[this.orderValueData[i][1]],"attr_name_three": this.orderTitleData[i][2],"value_three": response.data[this.orderValueData[i][2]]});
          }
          this.orderDetailOpen = true;
        });
      },
      alert(value){
        this.$alert(value, '提示', {confirmButtonText: '关闭'});
      },
      getSignString(signList){
        if(signList == null){
          return "";
        }
        let signStr = "";
        for(let i in signList){
          signStr += "，"+signList[i].name;
        }
        return signList.length > 0 ? signStr.substring(1) : signStr;
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
          startDate: null,
          endDate: null,
          recipesId: null,
          sendFlag: null,
          sendTime: null,
          createTime: null,
          createBy: null,
          updateTime: null,
          updateBy: null,
          delFlag: null
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
        this.planStartDateScope = [nextDate,nextDate];
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length!==1
        this.multiple = !selection.length
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getRecipesPlan(id).then(response => {
          this.form.id = response.data.id;
          this.form.sendFlag = response.data.sendFlag;
          this.open = true;
          this.title = "修改食谱计划";
      });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateRecipesPlan(this.form).then(response => {
                if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
              });
            }
          }
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有食谱计划数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportRecipesPlan(queryParams);
        }).then(response => {
          this.download(response.msg);
      }).catch(function() {});
      },
      getTagType(row){
         if(row.sendFlag == 1) {
           return "success";
         }
         return "danger";
        /* if(dayjs(row.startDate+"").diff(dayjs(),'day') <= 1){
              return "danger";
         }
         return '';*/
      },
      // 自定义列背景色
      columnStyle({ row, column, rowIndex, columnIndex }) {
        if (columnIndex == 0 || columnIndex == 2 || columnIndex == 4 || columnIndex == 6) {
          //第三第四列的背景色就改变了2和3都是列数的下标
          return "background:#f3f6fc;font-weight:bold";
        }else{
          return "background:#ffffff;";
        }
      },
      // 和并列
      objectSpanMethod({ row, column, rowIndex, columnIndex }) {
        if (columnIndex === 0) {
          if (rowIndex % 4 === 0) {
            return {
              rowspan: 4,
              colspan: 1
            };
          } else {
            return {
              rowspan: 0,
              colspan: 0
            };
          }
        }
      }
    }
  };
</script>



