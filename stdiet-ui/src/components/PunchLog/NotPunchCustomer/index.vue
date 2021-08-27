<template>
    <!--  -->
    <!-- 查询未打卡客户信息 -->
    <el-dialog :title="title" :visible.sync="open" width="1050px" :close-on-click-modal="false" append-to-body @closed="cancel">
      <!--<div style="margin-bottom: 20px;color:red"></div>-->
        <el-form :model="queryParams" ref="queryForm" :inline="true" >
        <el-form-item label="打卡日期" prop="startDate" >
          <el-date-picker
            v-model="queryParams.startDate"
            align="right"
            type="date"
            placeholder="选择打卡日期"
            value-format="yyyy-MM-dd"
            :picker-options="pickerOptions">
          </el-date-picker>
      </el-form-item>
        <el-form-item label="营养师" prop="mainDietitian" >
          <el-select
              v-model="queryParams.mainDietitian"
              placeholder="请选择销售"
              clearable
              filterable
              size="small"
            >
              <el-option
                v-for="dict in nutritionistIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
        </el-form-item>
         
         <!--<el-form-item label="营养师助理" prop="assistantDietitian" label-width="50px">
          <el-select
              v-model="queryParams.assistantDietitian"
              placeholder="请选择销售"
              clearable
              filterable
              size="small"
            >
              <el-option
                v-for="dict in nutriAssisIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
        </el-form-item>-->
        <el-form-item label="售后" prop="afterDietitian" >
          <el-select
              v-model="queryParams.afterDietitian"
              placeholder="请选择销售"
              clearable
              filterable
              size="small"
            >
              <el-option
                v-for="dict in afterSaleIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
        </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
        <div style="height: 550px; overflow: auto">
            <el-table v-loading="loading" :data="notPunchCustomerList">
                <el-table-column label="客户姓名" align="center" prop="name">
                  <template slot-scope="scope">
                     <div
                      @click="handleOnNameClick(scope.row.name)"
                      class="user_name_style"
                    >
                      {{ scope.row.name }}
                    </div>
                  </template>
                </el-table-column>
                <el-table-column label="营养师" align="center" prop="mainDietitian" :formatter="nutritionistFormat"/>
                <el-table-column label="售后营养师" align="center" prop="afterDietitian" :formatter="afterDietitianFormat"/>
            </el-table>
            <pagination
              v-show="total > 0"
              :total="total"
              :page.sync="queryParams.pageNum"
              :limit.sync="queryParams.pageSize"
              @pagination="getNotLunchCustomer"
            />
        </div>

        <div slot="footer" class="dialog-footer">
          <el-button @click="cancel">取消</el-button>
      </div>
    </el-dialog>
</template>
<script>
import { getNotPunchCustomer } from "@/api/custom/wxUserLog";
import { mapGetters } from "vuex";
import dayjs from "dayjs";
const nowTime = dayjs().format("YYYY-MM-DD");
export default {
  name: "NotPunchCustomer",
  components: {
      
  },
  data() {
    return {
        // 遮罩层
      loading: true,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      total: 0,
      // 表单校验
      rules: {
       
      },
      queryParams:{
          startDate: nowTime,
          mainDietitian: null,
          assistantDietitian: null,
          afterDietitian: null,
          pageNum: 1,
          pageSize: 10
      },
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
      },
      notPunchCustomerList: [],
      nutritionistIdOptions: [],
      afterSaleIdOptions: []
    };
  },
  created(){
    
        
  },
  computed: {
    ...mapGetters([
      // 售前字典
      //"preSaleIdOptions"
    ]),
  },
  methods: {
    showDialog(queryParams, nutritionistIdOptions, afterSaleIdOptions) {
      this.nutritionistIdOptions = nutritionistIdOptions != null ? nutritionistIdOptions.slice(1) : [];
      this.afterSaleIdOptions = afterSaleIdOptions != null ? afterSaleIdOptions.slice(1) : [];
      this.queryParams.startDate = queryParams.startDate != null ? queryParams.startDate : nowTime;
      this.queryParams.mainDietitian = queryParams.nutritionistId != null ? queryParams.nutritionistId : null;
      this.queryParams.afterDietitian = queryParams.afterNutritionistId != null ? queryParams.afterNutritionistId : null;
      this.title = "未打卡用户列表";
      this.reset();
      this.getNotLunchCustomer();
      this.open = true;
    },
    reset(){
       
    },
    /** 提交按钮 */
    submitForm() {
       
    },
    onClosed() {
      this.reset();
    },
    cancel(){
        this.open = false;
        this.reset();
    },
    //获取未打卡用户
    getNotLunchCustomer() {
        getNotPunchCustomer(this.queryParams).then((response) => {
          /*response.data.forEach((item,index) => {
            item.liveRoomName = item.liveNutritionistName + " " + item.liveRoomName;
          });*/
          this.notPunchCustomerList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
    },
    handleQuery(){
       this.getNotLunchCustomer();
    },
    resetQuery(){
        this.queryParams = {
          startDate: nowTime,
          mainDietitian: null,
          assistantDietitian: null,
          afterDietitian: null
        };
    },
    // 营养师字典翻译
    nutritionistFormat(row, column) {
      return this.selectDictLabel(this.nutritionistIdOptions, row.mainDietitian);
    },
    // 营养师助理字典翻译
    nutriAssisFormat(row, column) {
      return this.selectDictLabel(this.nutriAssisIdOptions, row.assistantDietitian);
    },
    // 售后营养师字典翻译
    afterDietitianFormat(row, column) {
      return this.selectDictLabel(this.afterSaleIdOptions, row.afterDietitian);
    },
    handleOnNameClick(name) {
      // console.log({ name });
      this.$router.push(`/customer?cusName=${name}`);
    },
  }
};
</script>

<style lang="scss" scoped>
  .user_name_style {
    cursor: pointer;
  }
</style>
