<template>
  <div class="app-container">
    <el-row>
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="70px">
        <!--<el-col :span="6">
          <el-form-item label="手机号" prop="phone">
            <el-input
              v-model="queryParams.phone"
              placeholder="请输入手机号"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>-->
        <el-col :span="6">
          <el-form-item label="客户信息" prop="customer">
            <el-input
              v-model="queryParams.customer"
              placeholder="请输入客户姓名或手机号"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="收款方式" prop="payTypeId">
            <el-select v-model="queryParams.payTypeId" placeholder="请选择收款方式" clearable size="small">
              <el-option v-for="dict in payTypeIdOptions"
                         :key="dict.dictValue"
                         :label="dict.dictLabel"
                         :value="parseInt(dict.dictValue)"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="售前" prop="preSaleId">
            <el-select v-model="queryParams.preSaleId" placeholder="请选择售前" clearable size="small">
              <el-option v-for="dict in preSaleIdOptions"
                         :key="dict.dictValue"
                         :label="dict.dictLabel"
                         :value="parseInt(dict.dictValue)"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="售后" prop="afterSaleId">
            <el-select v-model="queryParams.afterSaleId" placeholder="请选择售后" clearable size="small">
              <el-option v-for="dict in afterSaleIdOptions"
                         :key="dict.dictValue"
                         :label="dict.dictLabel"
                         :value="parseInt(dict.dictValue)"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="主营养师" prop="nutritionistId">
            <el-select v-model="queryParams.nutritionistId" placeholder="请选择主营养师" clearable size="small">
              <el-option v-for="dict in nutritionistIdOptions"
                         :key="dict.dictValue"
                         :label="dict.dictLabel"
                         :value="parseInt(dict.dictValue)"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="助理营养师" prop="nutriAssisId" label-width="90px">
            <el-select v-model="queryParams.nutriAssisId" placeholder="请选择助理营养师" clearable style="width: 170px"
                       size="small">
              <el-option v-for="dict in nutriAssisIdOptions"
                         :key="dict.dictValue"
                         :label="dict.dictLabel"
                         :value="parseInt(dict.dictValue)"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="账号" prop="accountId">
            <el-select v-model="queryParams.accountId" placeholder="请选择账号" clearable
                       size="small">
              <el-option
                v-for="dict in accountIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="策划" prop="plannerId">
            <el-select v-model="queryParams.plannerId" placeholder="请选择策划" clearable
                       size="small">
              <el-option v-for="dict in plannerIdOptions"
                         :key="dict.dictValue"
                         :label="dict.dictLabel"
                         :value="parseInt(dict.dictValue)"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="策划助理" prop="plannerAssisId">
            <el-select v-model="queryParams.plannerAssisId" placeholder="请选择策划助理"
                       clearable size="small">
              <el-option v-for="dict in plannerAssisIdOptions"
                         :key="dict.dictValue"
                         :label="dict.dictLabel"
                         :value="parseInt(dict.dictValue)"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="运营" prop="operatorId">
            <el-select v-model="queryParams.operatorId" placeholder="请选择运营"
                       clearable size="small">
              <el-option v-for="dict in operatorIdOptions"
                         :key="dict.dictValue"
                         :label="dict.dictLabel"
                         :value="parseInt(dict.dictValue)"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="运营助理" prop="operatorAssisId">
            <el-select v-model="queryParams.operatorAssisId" placeholder="请选择运营助理"
                       clearable size="small">
              <el-option v-for="dict in operatorAssisIdOptions"
                         :key="dict.dictValue"
                         :label="dict.dictLabel"
                         :value="parseInt(dict.dictValue)"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="审核状态" prop="reviewStatus">
            <el-select v-model="queryParams.reviewStatus" placeholder="请选择审核状态"
                       clearable size="small">
              <el-option v-for="dict in reviewStatusOptions"
                         :key="dict.dictValue"
                         :label="dict.dictLabel"
                         :value="dict.dictValue"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
        <el-form-item label="服务时长" prop="serveTimeId">
          <el-select v-model="queryParams.serveTimeId" placeholder="请选服务时长">
            <el-option
              v-for="dict in serveTimeIdOption"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            />
          </el-select>
        </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="订单金额" prop="amountFlag">
            <el-select v-model="queryParams.amountFlag" placeholder="请选金额状态">
              <el-option :key="0" label="全部订单" :value="null"/>
              <el-option :key="1" label="正常订单" :value="0"/>
              <el-option :key="2" label="退款订单" :value="1"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="成交日期" prop="orderTime">
            <el-date-picker
              v-model="daterange"
              type="daterange"
              size="small"
              align="right"
              unlink-panels
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd"
              :picker-options="pickerOptions">
            </el-date-picker>
          </el-form-item>
        </el-col>

        <el-col>
          <el-form-item>
            <el-button type="cyan" icon="el-icon-search" size="mini"
                       @click="handleQuery">搜索
            </el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">
              重置
            </el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['custom:order:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['custom:order:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column label="订单状态" align="center" prop="orderId" width="80" fixed="left">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.reviewStatus === '2' ? 'success' : scope.row.status ==='0'? '': 'danger'"
            disable-transitions>
            {{scope.row.reviewStatus === '2' ? '已完成': scope.row.status ==='0'? '进行中': '已暂停'}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="reviewStatus" width="80" fixed="left">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.reviewStatus === 'yes' ? 'success' : 'danger'"
            disable-transitions>
            {{scope.row.reviewStatus === 'yes' ? '已审核':'未审核'}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="成交时间" align="center" prop="orderTime" width="180" fixed="left">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.orderTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="客户姓名" align="center" prop="customer" width="120" fixed="left"/>
      <el-table-column label="金额" align="center" prop="amount" width="120" fixed="left">
        <template slot-scope="scope">
          {{toThousands(scope.row.amount)}}
        </template>
      </el-table-column>
      <el-table-column label="体重" align="center" prop="weight" width="80">
        <template slot-scope="scope">
          {{scope.row.weight ? `${scope.row.weight}斤` : ''}}
        </template>
      </el-table-column>
      <el-table-column label="手机号" align="center" prop="phone" width="120"/>
      <el-table-column label="服务时长" align="center" prop="serveTime" width="80"/>
      <el-table-column label="赠送时长" align="center" prop="giveServeDay" width="80">
        <template slot-scope="scope">
          {{scope.row.giveServeDay ? `${scope.row.giveServeDay}天` : '0天'}}
        </template>
      </el-table-column>
      <el-table-column label="调理项目" align="center" prop="conditioningProject" width="120"/>
      <el-table-column label="收款方式" align="center" prop="payType" width="120"/>
      <el-table-column label="售前" align="center" prop="preSale" width="120"/>
      <el-table-column label="售后" align="center" prop="afterSale" width="120"/>
      <el-table-column label="主营养师" align="center" prop="nutritionist" width="120"/>
      <el-table-column label="助理营养师" align="center" prop="nutriAssis" width="120"/>
      <el-table-column label="账号" align="center" prop="account" width="120"/>
      <el-table-column label="策划" align="center" prop="planner" width="120"/>
      <el-table-column label="策划助理" align="center" prop="plannerAssis" width="120"/>
      <el-table-column label="运营" align="center" prop="operator" width="120"/>
      <el-table-column label="运营助理" align="center" prop="operatorAssis" width="120"/>
      <el-table-column label="推荐人" align="center" prop="recommender" width="120"/>
      <el-table-column label="进粉时间" align="center" prop="becomeFanTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.becomeFanTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="开始时间" align="center" prop="startTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="serverEndTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.serverEndTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="服务天数" align="center" prop="serverDay" width="80">
        <template slot-scope="scope">
          {{scope.row.serverDay ? `${scope.row.serverDay}天` : '0天'}}
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" width="120"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="300" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['custom:order:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-data"
            @click="orderPauseManage(scope.row)"
            v-hasPermi="['orderPause:pause:query']"
          >暂停记录管理
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:order:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      layout="total, slot, sizes, prev, pager, next, jumper"
      @pagination="getList"
    >
      <span style="margin-right: 12px;">总计：{{toThousands(this.totalAmount)}} 元</span>
    </pagination>

    <!-- 暂停记录管理 -->
    <el-dialog :title="pauseTitle" v-if="openPause" :visible.sync="openPause" width="900px" append-to-body>
        <span style="color:#E6A23C;font-family:PingFang SC">
          注意事项：
          <br/>1、日期包含当天，如：2021-01-01到2021-01-07，总共暂停七天，2021-01-08继续服务
          <br/>2、每条暂停记录的时间范围不能重叠</span>
        <orderPause v-bind:orderPauseId="orderPauseId"></orderPause>
    </el-dialog>

    <!-- 添加或修改销售订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="720px" append-to-body>
      <el-row :gutter="15">
        <el-form ref="form" :model="form" :rules="rules" label-width="90px">
          <el-col :span="8">
            <el-form-item label="客户姓名" prop="customer">
              <el-input v-model="form.customer" placeholder="请输入姓名"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="体重" prop="weight">
                <el-input style="width: 100px" v-model="form.weight" placeholder="请输入体重"/>
                斤
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="电话" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入电话"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="成交金额" prop="amount">
              <el-input v-model="form.amount" placeholder="请输入金额"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="收款方式" prop="payTypeId">
              <el-select v-model="form.payTypeId" placeholder="请选择">
                <el-option
                  v-for="dict in payTypeIdOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="收款账号" prop="accountId">
              <el-select v-model="form.accountId" placeholder="请选择">
                <el-option
                  v-for="dict in accountIdOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
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
          <el-col :span="8">
            <el-form-item label="赠送时长" prop="serveTime">
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
          <el-col :span="8">
            <el-form-item label="调理项目" prop="conditioningProjectId">
              <el-select v-model="form.conditioningProjectId" placeholder="请选择">
                <el-option
                  v-for="dict in conditioningProjectIdOption"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="售前" prop="preSaleId">
              <el-select v-model="form.preSaleId" placeholder="请选择">
                <el-option
                  v-for="dict in preSaleIdOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="售后" prop="afterSaleId">
              <el-select v-model="form.afterSaleId" placeholder="请选择">
                <el-option
                  v-for="dict in afterSaleIdOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="主营养师" prop="nutritionistId">
              <el-select v-model="form.nutritionistId" placeholder="请选择">
                <el-option
                  v-for="dict in nutritionistIdOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="助理营养师" prop="nutriAssisId">
              <el-select v-model="form.nutriAssisId" placeholder="请选择">
                <el-option
                  v-for="dict in nutriAssisIdOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
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
          <el-col :span="8">
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
          <el-col :span="8">
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
          <el-col :span="8">
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
          <el-col :span="8">
            <el-form-item label="推荐人" prop="recommender">
              <el-input v-model="form.recommender" placeholder="请输入推荐人"/>
            </el-form-item>
          </el-col>
          <el-col :span="8" v-hasPermi="['custom:order:review']">
            <el-form-item label="审核状态" prop="reviewStatus">
              <el-select v-model="form.reviewStatus" placeholder="请选择审核状态" @change="handleOnRviewChange">
                <el-option
                  v-for="dict in reviewStatusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="进粉时间" prop="becomeFanTime">
              <el-date-picker
                style="width: 182.5px"
                v-model="form.becomeFanTime"
                type="date"
                placeholder="选择进粉时间"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                :picker-options="fanPickerOptions"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="10">
            <el-form-item label="成交时间" prop="orderTime">
              <el-date-picker
                style="width: 182.5px"
                v-model="form.orderTime"
                type="datetime"
                placeholder="选择成交时间"
                format="yyyy-MM-dd HH:mm"
                value-format="yyyy-MM-dd HH:mm:ss"
                :picker-options="orderPickerOptions">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开始时间" prop="startTime">
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
          <el-col>
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {addOrder, delOrder, exportOrder, getOptions, getOrder, listOrder, updateOrder} from "@/api/custom/order";
  import dayjs from 'dayjs';
  import orderPause from "./orderPause";

  const beginTime = dayjs().startOf('month').format('YYYY-MM-DD');
  const endTime = dayjs().format('YYYY-MM-DD');
  // const beginTime = dayjs().format('YYYY-MM-DD');
  // const endTime = dayjs().format('YYYY-MM-DD');
  export default {
    name: "Order",
    data() {
      const checkStartTime = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('开始时间不能为空'))
        }
        if (!this.form.orderTime) {
          return callback(new Error('请先选择成交时间'))
        }
        if(dayjs(this.form.startTime).diff(dayjs(this.form.orderTime),'day') < 0){
          return callback(new Error('开始时间不能小于成交时间'))
        }
        callback();
      };
      const checkOrderTime = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('成交时间不能为空'))
        }
        if (!this.form.becomeFanTime) {
          return callback(new Error('请先填写进粉时间'))
        }
        if(dayjs(this.form.orderTime).diff(dayjs(this.form.becomeFanTime),'day') < 0){
          return callback(new Error('成交时间不能小于进粉时间'))
        }
        callback();
      }
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
        showSearch: false,
        // 总条数
        total: 0,
        // 销售订单表格数据
        orderList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 是否显示暂停记录弹窗
        openPause: false,
        pauseTitle: "暂停记录",
        orderPauseId: null,
        //
        totalAmount: 0,
        //
        review: 'no',
        //
        daterange: [beginTime, endTime],
        // 收款方式字典
        payTypeIdOptions: [],
        // 售前字典
        preSaleIdOptions: [],
        // 售后字典
        afterSaleIdOptions: [],
        // 主营养师字典
        nutritionistIdOptions: [],
        // 助理营养师字典
        nutriAssisIdOptions: [],
        // 策划字典
        plannerIdOptions: [],
        // 账号
        accountIdOptions: [],
        // 服务时长
        serveTimeIdOption: [],
        // 赠送时长
        giveTimeIdOption: [],
        //调理项目
        conditioningProjectIdOption:[],
        // 审核状态
        reviewStatusOptions: [],
        // 策划助理字典
        plannerAssisIdOptions: [],
        // 运营字典
        operatorIdOptions: [],
        //
        operatorAssisIdOptions: [],
        //下拉列表对应关系(用于选择收款账号自动选择策划、策划助理、运营、运营助理)
        orderDropdownCorrespondingOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          orderId: null,
          customer: null,
          phone: null,
          payTypeId: null,
          preSaleId: null,
          afterSaleId: null,
          nutritionistId: null,
          nutriAssisId: null,
          accountId: null,
          plannerId: null,
          plannerAssisId: null,
          operatorId: null,
          operatorAssisId: null,
          recommender: null,
          reviewStatus: null,
          serveTimeId: null,
          amountFlag: null
        },
        initPlanningAndOperationFlag: false, //是否需要根据账号初始化运营、运营助理、策划、策划助理
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          customer: [
            {required: true, message: "客户姓名不能为空", trigger: "blur"}
          ],
          weight: [
            {required: true, message: "客户体重不能为空", trigger: "blur"}
          ],
          amount: [
            {required: true, message: "金额不能为空", trigger: "blur"}
          ],
          phone: [
            {required: true, message: "手机号不能为空", trigger: "blur"},
            {
              required: true,
              trigger: "blur",
              pattern: /^[0-9]{5,11}$/,
              message: "手机号格式不正确",
            }
          ],
          orderTime: [
            {required: true, message: "成交时间不能为空", trigger: "blur"},
            {required: true, trigger: "blur", validator: checkOrderTime}
          ],
          startTime: [
            {required: true, message: "开始时间不能为空", trigger: "blur"},
            {required: true, trigger: "blur", validator: checkStartTime}
          ],
          becomeFanTime: [
            {required: true, message: "进粉时间不能为空", trigger: "blur"}
          ]


          // payTypeId: [
          //   {required: true, message: "收款方式不能为空", trigger: "blur"}
          // ],
          // accountId: [
          //   {required: true, message: "账号不能为空", trigger: "blur"}
          // ],
          // serveTimeId: [
          //   {required: true, message: "服务时长不能为空", trigger: "blur"}
          // ],
          // reviewStatus: [
          //   {trigger: "change", validator: handleOnPreviewChange}
          // ]
        },
        pickerOptions: {
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }]
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
        }
      };
    },
    components: {
      orderPause
    },
    created() {
      this.getList();
      getOptions().then(response => {
        const options = response.data.reduce((opts, cur) => {
          if (!opts[cur.postCode]) {
            opts[cur.postCode] = [{dictValue: 0, dictLabel: '无', remark: null}];
          }
          opts[cur.postCode].push({dictValue: cur.userId, dictLabel: cur.userName, remark: cur.remark})
          return opts;
        }, {})
        this.preSaleIdOptions = options['pre_sale'] || [];
        this.afterSaleIdOptions = options['after_sale'] || [];
        this.nutritionistIdOptions = options['nutri'] || [];
        this.nutriAssisIdOptions = options['nutri_assis'] || [];
        this.plannerIdOptions = options['planner'] || [];
        this.plannerAssisIdOptions = options['planner_assis'] || [];
        this.operatorIdOptions = options['operator'] || [];
        this.operatorAssisIdOptions = options['operator_assis'] || [];
      })
      this.getDicts("cus_pay_type").then(response => {
        this.payTypeIdOptions = response.data;
      });
      this.getDicts("cus_account").then(response => {
        response.data.splice(0,0,{dictValue: 0, dictLabel: '无', remark: null})
        this.accountIdOptions = response.data;
      });
      this.getDicts("cus_serve_time").then(response => {
        this.serveTimeIdOption = response.data;
      });
      this.getDicts("give_serve_daye_type").then(response => {
        this.giveTimeIdOption = response.data;
      });
      this.getDicts("conditioning_project").then(response => {
        this.conditioningProjectIdOption = response.data;
    });
      this.getDicts("cus_review_status").then(response => {
        this.reviewStatusOptions = response.data;
      });
      this.getDicts("order_dropdown_corresponding").then(response => {
        this.orderDropdownCorrespondingOptions = response.data;
      });
    },
    methods: {
      /** 查询销售订单列表 */
      getList() {
        this.loading = true;
        listOrder(this.addDateRange(this.queryParams, this.daterange)).then(response => {
          this.orderList = response.rows;
          this.total = response.total;
          this.loading = false;
          this.totalAmount = response.totalAmount;
        });
      },// 收款方式字典翻译
      payTypeIdFormat(row, column) {
        return this.selectDictLabel(this.payTypeIdOptions, row.payTypeId);
      },
      // 售前字典翻译
      preSaleIdFormat(row, column) {
        return this.selectDictLabel(this.preSaleIdOptions, row.preSaleId);
      },
      // 售后字典翻译
      afterSaleIdFormat(row, column) {
        return this.selectDictLabel(this.afterSaleIdOptions, row.afterSaleId);
      },
      // 主营养师字典翻译
      nutritionistIdFormat(row, column) {
        return this.selectDictLabel(this.nutritionistIdOptions, row.nutritionistId);
      },
      // 助理营养师字典翻译
      nutriAssisIdFormat(row, column) {
        return this.selectDictLabel(this.nutriAssisIdOptions, row.nutriAssisId);
      },
      // 策划字典翻译
      plannerIdFormat(row, column) {
        return this.selectDictLabel(this.plannerIdOptions, row.plannerId);
      },
      // 账号字典翻译
      accountIdFormat(row, column) {
        return this.selectDictLabel(this.accountIdOptions, row.accountId);
      },
      // 策划助理字典翻译
      plannerAssisIdFormat(row, column) {
        return this.selectDictLabel(this.plannerAssisIdOptions, row.plannerAssisId);
      },
      // 运营字典翻译
      operatorIdFormat(row, column) {
        return this.selectDictLabel(this.operatorIdOptions, row.operatorId);
      },
      // 运营助理字典翻译
      operatorAssisIdFormat(row, column) {
        return this.selectDictLabel(this.operatorAssisIdOptions, row.operatorAssisId);
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.review = 'no';
        this.reset();
      },
      // 表单重置
      reset() {
        const defaultPayType = this.payTypeIdOptions.find(opt => opt.remark === 'default');
        const defaultServeTime = this.serveTimeIdOption.find(opt => opt.remark === 'default');
        const defaultGiveServeTime = this.giveTimeIdOption.find(opt => opt.remark === 'default');
        const defaultConditioningProjectIdOption = this.conditioningProjectIdOption.find(opt => opt.remark === 'default');
        const defaultAccount = this.accountIdOptions.find(opt => opt.remark === 'default');
        const defaultOperator = this.operatorIdOptions.find(opt => opt.remark === 'default');
        const defaultOperatorAssis = this.operatorAssisIdOptions.find(opt => opt.remark === 'default');
        const defaultPresale = this.preSaleIdOptions.find(opt => opt.remark === 'default');
        const defaultAftersale = this.afterSaleIdOptions.find(opt => opt.remark === 'default');
        const defaultNutritionist = this.nutritionistIdOptions.find(opt => opt.remark === 'default');
        const defaultNutriAssis = this.nutriAssisIdOptions.find(opt => opt.remark === 'default');
        const defaultPlanner = this.plannerIdOptions.find(opt => opt.remark === 'default');
        const defaultPlannerAssis = this.plannerAssisIdOptions.find(opt => opt.remark === 'default');
        this.form = {
          orderId: null,
          customer: null,
          phone: null,
          amount: null,
          weight: null,
          startTime: dayjs().add(3, 'day').format("YYYY-MM-DD"),
          pauseTime: null,
          payTypeId: defaultPayType ? parseInt(defaultPayType.dictValue) : null,
          accountId: defaultAccount ? parseInt(defaultAccount.dictValue) : null,
          preSaleId: defaultPresale ? parseInt(defaultPresale.dictValue) : null,
          createBy: null,
          createTime: null,
          afterSaleId: defaultAftersale ? parseInt(defaultAftersale.dictValue) : null,
          updateBy: null,
          updateTime: null,
          nutritionistId: defaultNutritionist ? parseInt(defaultNutritionist.dictValue) : null,
          remark: null,
          nutriAssisId: defaultNutriAssis ? parseInt(defaultNutriAssis.dictValue) : null,
          plannerId: defaultPlanner ? parseInt(defaultPlanner.dictValue) : null,
          plannerAssisId: defaultPlannerAssis ? parseInt(defaultPlannerAssis.dictValue) : null,
          operatorId: defaultOperator ? parseInt(defaultOperator.dictValue) : null,
          operatorAssisId: defaultOperatorAssis ? parseInt(defaultOperatorAssis.dictValue) : null,
          recommender: null,
          orderTime: dayjs().format("YYYY-MM-DD HH:mm:ss"),
          serveTimeId: defaultServeTime ? parseInt(defaultServeTime.dictValue) : null,
          reviewStatus: this.review,
          giveServeDay: defaultGiveServeTime ? parseInt(defaultGiveServeTime.dictValue) : null,
          conditioningProjectId: defaultConditioningProjectIdOption ? parseInt(defaultConditioningProjectIdOption.dictValue) : null,
          becomeFanTime: dayjs().format("YYYY-MM-DD")
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
        this.daterange = [beginTime, endTime];
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.orderId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加销售订单";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const orderId = row.orderId || this.ids;
        this.form.accountId = "no";
        getOrder(orderId).then(response => {
          this.form = response.data;
          this.form.giveServeDay = parseInt(this.form.giveServeDay+"");
          if(this.form.becomeFanTime == null){
            this.form.becomeFanTime = this.form.orderTime;
          }
          this.open = true;
          this.title = "修改销售订单";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.orderId != null) {
              this.form.reviewStatus = this.review;
              updateOrder(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              this.form.status = '0';
              addOrder(this.form).then(response => {
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
        const orderIds = row.orderId || this.ids;
        this.$confirm('是否确认删除销售订单编号为"' + orderIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delOrder(orderIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function () {
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有销售订单数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportOrder(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function () {
        });
      },
      handleOnRviewChange(val) {
        this.review = val;
      },
      handleStatusClick(data) {
        console.log(data);
      },
      orderPauseManage(order) {
        console.log(order.orderId);
        this.pauseTitle = order.customer;
        this.orderPauseId = order.orderId;
        this.openPause = true;
        //this.$router.push({ name: 'orderPause', params: { 'orderId': order.orderId }})
      },
      //根据收款账号ID初始化策划、策划助理、运营、运营助理
      initPlanningAndOperation(){
         if(this.form.accountId != null && this.form.accountId != undefined){
           let planningAndOperationValue = this.orderDropdownCorrespondingOptions.find(opt => parseInt(opt.dictValue) === this.form.accountId);
           if(planningAndOperationValue){
             let array = planningAndOperationValue.dictLabel.split("|");
             let plannerIdOption = this.plannerIdOptions.find(opt => opt.dictValue == array[0]);
             this.form.plannerId = plannerIdOption ? parseInt(plannerIdOption.dictValue) : 0;
             let plannerAssisIdOption = this.plannerAssisIdOptions.find(opt => opt.dictValue == array[1]);
             this.form.plannerAssisId = plannerAssisIdOption ? parseInt(plannerAssisIdOption.dictValue) : 0;
             let operatorIdOption = this.operatorIdOptions.find(opt => opt.dictValue == array[2]);
             this.form.operatorId = operatorIdOption ? parseInt(operatorIdOption.dictValue) : 0;
             let operatorAssisIdOption = this.operatorAssisIdOptions.find(opt => opt.dictValue == array[3]);
             this.form.operatorAssisId = operatorAssisIdOption ? parseInt(operatorAssisIdOption.dictValue) : 0;
           }else{
             this.form.plannerId = 0;
             this.form.plannerAssisId = 0;
             this.form.operatorId = 0;
             this.form.operatorAssisId = 0;
           }
         }
      }
    },
    watch:{
      // 监听收款账号的变化
      "form.accountId": function(newVal, oldVal){
        if(oldVal != "no" && newVal != "no"){
           this.initPlanningAndOperation();
        }
      }
    }
  };
</script>

<style lang="scss" scoped>
  .s_success {
    color: #1ab394;
    font-size: 22px;
  }

  .s_pause {
    color: #f56c6c;
    font-size: 22px;
    cursor: pointer;
  }

  .s_play {
    color: #1c84c6;
    font-size: 22px;
    cursor: pointer;
  }

</style>
