<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="直播日期" prop="liveSchedulDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="queryParams.liveSchedulDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择直播日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="直播间" prop="liveRoom">
          <el-select
              v-model="queryParams.liveRoom"
              placeholder="请选择直播间"
              clearable
              filterable
              size="small"
            >
              <el-option
                v-for="dict in liveTypeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
        </el-form-item>
         <el-form-item label="所属账号" prop="fanChannel">
          <el-select
              v-model="queryParams.fanChannel"
              placeholder="请选择账号渠道"
              clearable
              filterable
              size="small"
            >
              <el-option
                v-for="dict in fanChanneloptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
        </el-form-item>
        <el-form-item label="微信号" prop="wxAccountId">
          <el-select
            v-model="queryParams.wxAccountId"
            filterable
            placeholder="请选择微信号"
            clearable
            size="small"
          >
            <el-option
              v-for="dict in wxAccountList"
              :key="dict.id"
              :label="dict.wxAccount"
              :value="parseInt(dict.id)"
            />
          </el-select>
        </el-form-item>

        
        
      <el-form-item style="margin-left:10px;">
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['custom:liveSchedul:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['custom:liveSchedul:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['custom:liveSchedul:remove']"
        >删除</el-button>
      </el-col>
      <!--<el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['custom:liveSchedul:export']"
        >导出</el-button>
      </el-col>-->
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="liveSchedulList" :span-method="objectSpanMethod" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="直播日期" align="center" prop="liveSchedulDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.liveSchedulDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="直播间" align="center" prop="liveRoomName" />
      
      <el-table-column label="直播时间" align="center" prop="liveStartTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.liveStartTime, '{h}:{i}') }}-{{ parseTime(scope.row.liveEndTime, '{h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="直播状态" align="center" prop="liveStatus">
        <template slot-scope="scope">
          <span><el-tag :type="scope.row.liveStatus == 0 ? 'info' : (scope.row.liveStatus == 1 ? 'success' : '')">{{scope.row.liveStatus == 0 ? '未开播' : (scope.row.liveStatus == 1 ? '直播中' : '已下播')}}</el-tag></span>
        </template>
      </el-table-column>
      <el-table-column label="直播营养师" align="center" prop="liveNutritionistName" />
      <el-table-column label="直播运营" align="center" prop="liveOperatorName" />
      <el-table-column label="直播策划" align="center" prop="livePlannerName" />
      <el-table-column label="所属账号" align="center" prop="fanChannelName" />
      <el-table-column label="接粉微信号" align="center" prop="wxAccountList">
        <template slot-scope="scope">
          <el-popover
          placement="left"
          width="390"
          trigger="click">
          <el-table :data="scope.row.wxAccountList" style="width:370;height: 400px;overflow: auto;">
            <el-table-column width="100" property="nickName" label="销售"></el-table-column>
            <el-table-column width="120" property="wxAccount" label="微信号"></el-table-column>
            <el-table-column width="120" property="saleGroupName" label="销售组别"></el-table-column>
          </el-table>
          <el-button slot="reference">详情</el-button>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="300">
        <template slot-scope="scope"><!-- scope.row.statusTrueName,scope.row.statusFalseName -->
          <el-switch
              v-hasPermi="['custom:liveSchedul:edit']"
                v-model="scope.row.status"
                :active-text="scope.row.statusTrueName"
                :inactive-text="scope.row.statusFalseName"
                @change="updateLiveStatus($event, scope.row)"
                >
              </el-switch>
          <el-button
          style="margin-left:16px"
            size="normal"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['custom:liveSchedul:edit']"
          >修改</el-button>
          <el-button
             size="normal"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:liveSchedul:remove']"
          >删除</el-button>
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

    <!-- 添加或修改直播排班对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        
        <el-form-item label="直播日期" prop="liveSchedulDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.liveSchedulDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择直播日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="直播间" prop="liveRoom">
          <el-select
              v-model="form.liveRoom"
              placeholder="请选择直播间"
              clearable
              filterable
              size="small"
            >
              <el-option
                v-for="dict in liveTypeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
        </el-form-item>
        <!--<el-form-item label="开始时间" prop="liveStartTime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.liveStartTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择开始时间">
          </el-date-picker>
        </el-form-item>-->
        <el-form-item label="直播时间" prop="liveTime">
                <el-time-picker
                v-model="form.startTime"
                value-format="yyyy-MM-dd HH:mm"
                start-placeholder="开始时间"
                placeholder="选择开始时间(必须)">
            </el-time-picker>
            <span style="margin-left:10px;margin-right:10px">至</span>
             <el-time-picker
                value-format="yyyy-MM-dd HH:mm"
                v-model="form.endTime"
                placeholder="选择结束时间(非必须)">
            </el-time-picker>

          <!--<el-time-picker
              is-range
              value-format="yyyy-MM-dd HH:mm"
              v-model="form.liveTime"
              range-separator="至"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              placeholder="选择时间范围">
            </el-time-picker>-->
        </el-form-item>
        <!--<el-form-item label="结束时间" prop="liveEndTime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.liveEndTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择结束时间">
          </el-date-picker>
        </el-form-item>-->
        <el-form-item label="营养师" prop="liveNutritionistId">
            <el-select
              v-model="form.liveNutritionistId"
              placeholder="请选择营养师"
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
        <el-form-item label="运营" prop="liveOperatorId">
          <el-select
              v-model="form.liveOperatorId"
              placeholder="请选择运营"
              clearable
              filterable
              size="small"
            >
              <el-option
                v-for="dict in operatorIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
        </el-form-item>
        <el-form-item label="策划" prop="livePlannerId">
          <el-select
              v-model="form.livePlannerId"
              placeholder="请选择策划"
              clearable
              filterable
              size="small"
            >
              <el-option
                v-for="dict in plannerIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
        </el-form-item>
        <el-form-item label="所属账号" prop="fanChannel">
            <el-select
              v-model="form.fanChannel"
              placeholder="请选择所属账号渠道"
              clearable
              filterable
              size="small"
            >
              <el-option
                v-for="dict in fanChanneloptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
            <span style="margin-left:10px;color:#1890ff">该账号与销售接粉微信号绑定</span>
        </el-form-item>
        <el-form-item label="直播状态" prop="liveStatus">
            <el-select
              v-model="form.liveStatus"
              placeholder="请选择状态"
              clearable
              filterable
              size="small"
            >
              <el-option :key="0" :label="'未开播'" :value="parseInt('0')"/>
              <el-option :key="1" :label="'开播中'" :value="parseInt('1')"/>
              <el-option :key="2" :label="'已下播'" :value="parseInt('2')"/>
            </el-select>
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
import { listLiveSchedul, getLiveSchedul, delLiveSchedul, addLiveSchedul, updateLiveSchedul, exportLiveSchedul,updateLiveStatus } from "@/api/custom/liveSchedul";
import { listWxAccount } from "@/api/custom/wxAccount";
import AutoHideInfo from "@/components/AutoHideInfo";
import dayjs from "dayjs";
const nowDate = dayjs().format("YYYY-MM-DD");
const nowTime = dayjs().format("YYYY-MM-DD HH:mm");
import { mapGetters } from "vuex";
export default {
  name: "LiveSchedul",
  data() {
    const checkLiveTime = (rule, value, callback) => {
      if (!this.form.startTime || this.form.startTime == null) {
        return callback(new Error("请选择开始时间"));
      }
      if (this.form.endTime != undefined && this.form.endTime != null && !dayjs(this.form.startTime).isBefore(dayjs(this.form.endTime))) {
        return callback(new Error("结束时间必须在开始时间之后"));
      }
      callback();
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
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 直播排班表格数据
      liveSchedulList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        liveSchedulDate: nowDate,
        liveRoom: null,
        fanChannel: null,
        wxAccountId: null
      },
      // 表单参数
      form: {
      },
      // 表单校验
      rules: {
            liveSchedulDate: [{ required: true, trigger: "blur", message: "请选择直播日期" }],
            liveRoom: [{ required: true, trigger: "blur", message: "请选择直播间" }],
            liveTime: [
                //{ required: true, trigger: "blur", message: "请选择直播时间" },
                { required: true, trigger: "blur", validator: checkLiveTime }
            ],
            liveNutritionistId: [{ required: true, trigger: "blur", message: "请选择营养师" }],
            fanChannel: [{ required: true, trigger: "blur", message: "请选择所属账号渠道" }],
      },
      //直播间类型
      liveTypeOptions: [],
      //接粉渠道
      fanChanneloptions: [],
      //所有微信号
      wxAccountList:[]
    };
  },
  components:{
    AutoHideInfo
  },
  created() {
    this.getList();
    this.getDicts("sys_live_type").then((response) => {
      this.liveTypeOptions = response.data;
    });
    this.getDicts("fan_channel").then((response) => {
      this.fanChanneloptions = response.data;
    });
    this.getListWxAccount();
  },
  computed: {
    ...mapGetters([
      // 主营养师字典
      "nutritionistIdOptions",
      // 策划字典
      "plannerIdOptions",
      // 运营字典
      "operatorIdOptions"
    ]),
  },
  methods: {
    /** 查询直播排班列表 */
    getList() {
      this.loading = true;
      listLiveSchedul(this.queryParams).then(response => {
        this.liveSchedulList = response.rows;
        if(this.liveSchedulList != null && this.liveSchedulList.length > 0){
            this.liveSchedulList.forEach((item,index) => {
                item.status = (item.liveStatus == 0 || item.liveStatus == 2) ? false : true; 
                item.statusFalseName = item.liveStatus == 0 ? '未开播' : '已下播';
                item.statusTrueName = '已开播';
            });
        }
        this.total = response.total;
        this.loading = false;
      });
    },
    //获取所有微信号
    getListWxAccount() {
      listWxAccount(this.queryParams).then((response) => {
        this.wxAccountList = response.rows;
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
        id: null,
        liveRoom: null,
        liveSchedulDate: nowDate,
        liveStartTime: null,
        liveEndTime: null,
        startTime: dayjs().format("YYYY-MM-DD HH:mm"),
        endTime: null,
        liveNutritionistId: null,
        liveOperatorId: null,
        livePlannerId: null,
        fanChannel: null,
        liveStatus: 1,
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
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加直播记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getLiveSchedul(id).then(response => {
        response.data.startTime = response.data.liveStartTime;
        response.data.endTime = response.data.liveEndTime;
        this.form = response.data;
        this.open = true;
        this.title = "修改直播记录";
      });
    },
    //修改直播状态
    updateLiveStatus(newStatus, row){
        row.statusFalseName = '已下播';
        row.statusTrueName = '已开播';
        let param = {
          id: row.id,
          liveStatus: newStatus ? 1 : 2
        };
        updateLiveStatus(param).then(response => {
            if(response.code == 200){
               this.getList();
            }
        });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
            //处理直播开始时间
            this.form.liveStartTime = this.form.liveSchedulDate + " " + this.form.startTime.substring(this.form.startTime.length - 5);
            if(this.form.endTime == undefined || this.form.endTime == null){
                this.form.liveEndTime = "";
            }else{
                this.form.liveEndTime = this.form.liveSchedulDate + " " + this.form.endTime.substring(this.form.endTime.length -5);
            }
          if (this.form.id != null) {
            updateLiveSchedul(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addLiveSchedul(this.form).then(response => {
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
      this.$confirm('是否确认删除直播排班编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delLiveSchedul(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有直播排班数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportLiveSchedul(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        return row.userSpan;
      } else if (columnIndex === 1) {
        return row.postSpan;
      }
    },
    wxAccountFormat(row){
       return (row.wxAccountList != null && row.wxAccountList.length > 0) ? row.wxAccountList : [];
    }
  }
};
</script>