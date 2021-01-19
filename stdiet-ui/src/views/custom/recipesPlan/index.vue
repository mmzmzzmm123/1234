<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" >
      <el-form-item label="客户姓名" prop="customer">
        <el-input
          v-model="queryParams.customer"
          placeholder="请输入客户姓名"
          clearable
          size="small"
        />
      </el-form-item>
      <el-form-item label="食谱开始日期范围" prop="planStartDateScope" label-width="150px">
        <el-date-picker
          v-model="planStartDateScope"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>

      </el-form-item>

      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
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
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recipesPlanList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="客户姓名" align="center" prop="customer" />
      <el-table-column label="食谱日期范围" align="center" prop="scopeDate" width="200"/>
      <el-table-column label="营养师" align="center" prop="nutritionist" />
      <el-table-column label="营养师助理" align="center" prop="nutritionistAssis" />
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
      <el-table v-loading="loading" :data="allRecipesPlanList" width="700px">
        <el-table-column label="客户姓名" align="center" prop="customer" />
        <!--<el-table-column label="营养师名称" align="center" prop="nutritionist" />
        <el-table-column label="营养师助理名称" align="center" prop="nutritionistAssis" />-->

        <el-table-column label="食谱时间范围" align="center" prop="scopeDate" width="200"/>
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
      <div slot="footer" class="dialog-footer">
        <el-button @click="allRecipesPlanOpen = false">取 消</el-button>
      </div>
    </el-dialog>


    <!-- 查看体征 -->
    <el-dialog title="客户体征" v-if="customerOpen" :visible.sync="customerOpen" width="1000px" append-to-body>
      <el-table v-loading="loading" :data="customerSign" width="900px">
        <el-table-column label="姓名" align="center" prop="name" />
        <!--<el-table-column label="手机号" align="center" prop="phone" width="120" fixed="left"/>-->
        <el-table-column label="性别" align="center" prop="sign.sex" >
          <template slot-scope="scope">
            {{scope.row.sign.sex == 0 ? `男` : '女'}}
          </template>
        </el-table-column>
        <el-table-column label="年龄(岁)" align="center" prop="sign.age" />
        <el-table-column label="身高(厘米)" align="center" prop="sign.tall" />
        <el-table-column label="体重(斤)" align="center" prop="sign.weight"/>
        <el-table-column label="北方、南方" align="center" prop="sign.position">
          <template slot-scope="scope">
            {{scope.row.sign.position == 0 ? `南方` : '北方'}}
          </template>
        </el-table-column>
        <el-table-column label="病史" align="center" prop="sign.signList">
          <template slot-scope="scope">
            <span>{{getSignString(scope.row.sign.signList)}}</span>
          </template>
        </el-table-column>
      </el-table>
      <el-table v-loading="loading" :data="customerSign" width="900px">
        <el-table-column label="忌口或过敏源" align="center" prop="sign.dishesIngredientId" width="120"></el-table-column>
        <el-table-column label="是否便秘" align="center" prop="sign.constipation">
          <template slot-scope="scope">
            {{scope.row.sign.constipation == 0 ? `是` : '否'}}
          </template>
        </el-table-column>
        <el-table-column label="是否熬夜失眠" align="center" prop="sign.staylate">
          <template slot-scope="scope">
            {{scope.row.sign.staylate == 0 ? `是` : '否'}}
          </template>
        </el-table-column>
        <el-table-column label="是否经常运动" align="center" prop="sign.motion">
          <template slot-scope="scope">
            {{scope.row.sign.motion == 0 ? `是` : '否'}}
          </template>
        </el-table-column>
        <el-table-column label="饮食方式" align="center" prop="sign.makeFoodType" >
          <template slot-scope="scope">
            {{scope.row.sign.makeFoodType == 0 ? `自己做` : '外面吃'}}
          </template>
        </el-table-column>
        <el-table-column label="饮食备注" align="center" prop="sign.remarks"></el-table-column>
        <el-table-column label="饮食特点" align="center" prop="sign.makeFoodTaste">
          <template slot-scope="scope">
            {{scope.row.sign.makeFoodTaste == 0 ? `清淡` : '重口味'}}
          </template>
        </el-table-column>
       </el-table>
      <el-table v-loading="loading" :data="customerSign" width="900px">
        <el-table-column label="工作职业" align="center" prop="sign.vocation">
          <template slot-scope="scope">
            {{scope.row.sign.vocation}}
          </template>
        </el-table-column>
        <el-table-column label="是否上夜班" align="center" prop="sign.night">
          <template slot-scope="scope">
            {{scope.row.sign.night == 0 ? `是` : '否'}}
          </template>
        </el-table-column>
        <el-table-column label="久坐多还是运动多" align="center" prop="sign.walk">
          <template slot-scope="scope">
            {{scope.row.sign.walk == 0 ? `久坐多` : '走动多'}}
          </template>
        </el-table-column>
        <el-table-column label="是否浑身乏力" align="center" prop="sign.weakness">
          <template slot-scope="scope">
            {{scope.row.sign.weakness == 0 ? `是` : '否'}}
          </template>
        </el-table-column>
        <el-table-column label="是否减脂反弹" align="center" prop="sign.rebound">
          <template slot-scope="scope">
            {{scope.row.sign.rebound == 0 ? `是` : '否'}}
          </template>
        </el-table-column>
        <el-table-column label="意识到生活习惯是减脂关键" align="center" prop="sign.crux">
          <template slot-scope="scope">
            {{scope.row.sign.crux == 0 ? `是` : '否'}}
          </template>
        </el-table-column>
        <el-table-column label="睡觉时间" align="center" prop="sign.sleepTime">
          <template slot-scope="scope">
            {{scope.row.sign.sleepTime}}点
          </template>
        </el-table-column>
      </el-table>
      <el-table v-loading="loading" :data="customerSign" width="900px">
        <el-table-column label="起床时间" align="center" prop="sign.getupTime" >
          <template slot-scope="scope">
            {{scope.row.sign.getupTime}}点
          </template>
        </el-table-column>
        <el-table-column label="方便沟通时间" align="center" prop="sign.connectTime">
          <template slot-scope="scope">
            {{scope.row.sign.connectTime}}点
          </template>
        </el-table-column>
        <el-table-column label="湿气数据" align="center" prop="sign.bloodData">
          <template slot-scope="scope">
            {{scope.row.sign.bloodData}}
          </template>
        </el-table-column>
        <el-table-column label="气血数据" align="center" prop="sign.moistureDate" >
          <template slot-scope="scope">
            {{scope.row.sign.moistureDate}}
          </template>
        </el-table-column>
        <el-table-column label="减脂经历" align="center" prop="sign.experience" ></el-table-column>
        <el-table-column label="减脂遇到的困难" align="center" prop="sign.difficulty" ></el-table-column>
        <el-table-column label="备注" align="center" prop="sign.comments"></el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="customerOpen = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 查看订单 -->
    <el-dialog title="订单详情" v-if="orderDetailOpen" :visible.sync="orderDetailOpen" width="1000px" append-to-body>
      <el-table v-loading="loading" :data="orderDetailList" >
        <el-table-column label="订单状态" align="center" prop="orderId">
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.reviewStatus === '2' ? 'success' : scope.row.status ==='0'? '': 'danger'"
              disable-transitions>
              {{scope.row.reviewStatus === '2' ? '已完成': scope.row.status ==='0'? '进行中': '已暂停'}}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="审核状态" align="center" prop="reviewStatus">
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.reviewStatus === 'yes' ? 'success' : 'danger'"
              disable-transitions>
              {{scope.row.reviewStatus === 'yes' ? '已审核':'未审核'}}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="成交时间" align="center" prop="orderTime">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.orderTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="客户姓名" align="center" prop="customer"/>
        <el-table-column label="金额" align="center" prop="amount">
          <template slot-scope="scope">
            {{toThousands(scope.row.amount)}}
          </template>
        </el-table-column>
        <el-table-column label="体重" align="center" prop="weight">
          <template slot-scope="scope">
            {{scope.row.weight ? `${scope.row.weight}斤` : ''}}
          </template>
        </el-table-column>
      </el-table>
      <el-table v-loading="loading" :data="orderDetailList" >
        <el-table-column label="手机号" align="center" prop="phone"/>
        <el-table-column label="服务时长" align="center" prop="serveTime"/>
        <el-table-column label="赠送时长" align="center" prop="giveServeDay">
          <template slot-scope="scope">
            {{scope.row.giveServeDay ? `${scope.row.giveServeDay}天` : '0天'}}
          </template>
        </el-table-column>
        <el-table-column label="调理项目" align="center" prop="conditioningProject"/>
        <el-table-column label="收款方式" align="center" prop="payType"/>
        <el-table-column label="售前" align="center" prop="preSale"/>
      </el-table>
      <el-table v-loading="loading" :data="orderDetailList" >
        <el-table-column label="售后" align="center" prop="afterSale"/>
        <el-table-column label="主营养师" align="center" prop="nutritionist"/>
        <el-table-column label="助理营养师" align="center" prop="nutriAssis" />
        <el-table-column label="账号" align="center" prop="account"/>
        <el-table-column label="策划" align="center" prop="planner"/>
        <el-table-column label="策划助理" align="center" prop="plannerAssis"/>
      </el-table>
      <el-table v-loading="loading" :data="orderDetailList" >
        <el-table-column label="运营" align="center" prop="operator"/>
        <el-table-column label="运营助理" align="center" prop="operatorAssis"/>
        <!--<el-table-column label="推荐人" align="center" prop="recommender"/>-->
        <el-table-column label="进粉时间" align="center" prop="becomeFanTime">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.becomeFanTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="开始时间" align="center" prop="startTime">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="结束时间" align="center" prop="serverEndTime">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.serverEndTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="备注" align="center" prop="remark"/>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="orderDetailOpen = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { listRecipesPlan, getRecipesPlan, updateRecipesPlan,exportRecipesPlan } from "@/api/custom/recipesPlan";
  import { getCustomerAndSignByPhone } from "@/api/custom/customer";
  import {getInfoDetail} from "@/api/custom/order";
  import dayjs from 'dayjs';
  const nextDate = dayjs().add(1, 'day').format("YYYY-MM-DD");
  const weekDate = dayjs().add(6, 'day').format("YYYY-MM-DD");
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
          endDate: null
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
          orderId: null
        },
        //订单弹窗中列表数据的总条数
        allRecipesPlanTotal: 0,
        //订单详情数据
        orderDetailList:[],
        //订单详情弹窗
        orderDetailOpen:false
      };
    },
    created() {
      this.getList();
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
        this.customerSign = [];
        let message = "未找到该客户，请检查订单手机号与客户体征手机号是否一致";
        if(row.phone == null || row.phone == ""){
          this.alert(message);
          return;
        }
        getCustomerAndSignByPhone({"phone": row.phone}).then(response => {
          this.customerSign[0] = response.data;
          if(this.customerSign[0] == null){
            this.alert(message);
          }else{
            this.customerOpen = true;
          }
        });
      },
      getAllPlanByOrderId(){
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
          this.orderDetailList[0] = response.data;
          this.orderDetailOpen = true;
        });
      },
      alert(value){
        this.$alert(value, '提示', {confirmButtonText: '确定'});
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
         if(dayjs(row.startDate+"").diff(dayjs(),'day') <= 1){
              return "danger";
         }
         return '';
      }
    }
  };
</script>



