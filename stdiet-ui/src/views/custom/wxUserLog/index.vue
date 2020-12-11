<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="微信应用" prop="appid">
        <el-select v-model="queryParams.appid" placeholder="请选择微信应用" clearable size="small">
          <el-option
            v-for="dict in appidOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入电话"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
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
          v-hasPermi="['custom:wxUserLog:add']"
        >新增
        </el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="success"-->
<!--          icon="el-icon-edit"-->
<!--          size="mini"-->
<!--          :disabled="single"-->
<!--          @click="handleUpdate"-->
<!--          v-hasPermi="['custom:wxUserLog:edit']"-->
<!--        >修改-->
<!--        </el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="danger"-->
<!--          icon="el-icon-delete"-->
<!--          size="mini"-->
<!--          :disabled="multiple"-->
<!--          @click="handleDelete"-->
<!--          v-hasPermi="['custom:wxUserLog:remove']"-->
<!--        >删除-->
<!--        </el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['custom:wxUserLog:export']"-->
<!--        >导出-->
<!--        </el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="wxUserLogList" @selection-change="handleSelectionChange">
      <el-table-column label="用户头像" align="center" prop="avatarUrl">
        <template slot-scope="scope">
          <el-image :src="scope.row.avatarUrl" style="width: 32px; height: 32px; border-radius: 50%"/>
        </template>
      </el-table-column>
      <!--      <el-table-column type="selection" width="55" align="center" />-->
      <!--      <el-table-column label="微信openid" align="center" prop="openid" />-->
      <el-table-column label="当天体重" align="center" prop="weight">
        <template slot-scope="scope">
          <span>{{`${scope.row.weight} 斤` }}</span>
        </template>
      </el-table-column>
      <el-table-column label="微信应用" align="center" prop="appid" :formatter="appidFormat"/>
      <el-table-column label="手机号" align="center" prop="phone" width="180"/>
      <el-table-column label="打卡日期" align="center" prop="logTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.logTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="睡觉时间" align="center" prop="sleepTime" width="180">
        <!--                <template slot-scope="scope">-->
        <!--                  <span>{{ parseTime(scope.row.sleepTime, '{y}-{m}-{d}') }}</span>-->
        <!--                </template>-->
      </el-table-column>
      <el-table-column label="起床时间" align="center" prop="wakeupTime" width="180">
        <!--        <template slot-scope="scope">-->
        <!--          <span>{{ parseTime(scope.row.wakeupTime, '{y}-{m}-{d}') }}</span>-->
        <!--        </template>-->
      </el-table-column>
      <el-table-column label="运动锻炼" align="center" prop="sport" :formatter="sportFormat"/>
      <el-table-column label="按食谱" align="center" prop="diet" :formatter="dietFormat"/>
      <el-table-column label="熬夜失眠" align="center" prop="insomnia" :formatter="insomniaFormat"/>
      <el-table-column label="起床排便" align="center" prop="defecation" :formatter="defecationFormat"/>
      <el-table-column label="饮水量" align="center" prop="water">
        <template slot-scope="scope">
          <span>{{`${scope.row.water} ml`}}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['custom:wxUserLog:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:wxUserLog:remove']"
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
      @pagination="getList"
    />

    <!-- 添加或修改微信用户记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-row :gutter="15">
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-col :span="12">
            <el-form-item label="体重" prop="weight">
              <el-input v-model="form.weight" placeholder="请输入体重"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电话" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入电话"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="打卡日期" prop="logTime">
              <el-date-picker clearable size="small" style="width: 200px"
                              v-model="form.logTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              :picker-options="logTimePickerOptions"
                              placeholder="选择睡觉时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="睡觉时间" prop="sleepTime">
              <el-time-select clearable size="small" style="width: 200px"
                              v-model="form.sleepTime"
                              :picker-options="{
                                start: '00:00',
                                step: '00:15',
                                end: '23:45'
                              }"
                              placeholder="选择睡觉时间">
              </el-time-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="起床时间" prop="wakeupTime">
              <el-time-select clearable size="small" style="width: 200px"
                              v-model="form.wakeupTime"
                              :picker-options="{
                                start: '00:00',
                                step: '00:15',
                                end: '23:45'
                              }"
                              placeholder="选择睡觉时间">
              </el-time-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="运动锻炼" prop="sport">
              <el-select v-model="form.sport" placeholder="请选择运动情况">
                <el-option
                  v-for="dict in sportOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="按食谱" prop="diet">
              <el-select v-model="form.diet" placeholder="请选择饮食情况">
                <el-option
                  v-for="dict in dietOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="熬夜失眠" prop="insomnia">
              <el-select v-model="form.insomnia" placeholder="请选择熬夜失眠">
                <el-option
                  v-for="dict in insomniaOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="起床排便" prop="defecation">
              <el-select v-model="form.defecation" placeholder="请选择排便情况">
                <el-option
                  v-for="dict in defecationOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col>
            <el-form-item label="饮水量" prop="water">
              <el-input v-model="form.water" placeholder="请输入饮水量"/>
            </el-form-item>
          </el-col>
          <el-col>
            <el-form-item label="微信应用" prop="appid">
              <el-select v-model="form.appid" placeholder="请选择微信appid">
                <el-option
                  v-for="dict in appidOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
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
  import {
    addWxUserLog,
    delWxUserLog,
    exportWxUserLog,
    getWxUserLog,
    listWxUserLog,
    updateWxUserLog
  } from "@/api/custom/wxUserLog";

  export default {
    name: "WxUserLog",
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
        // 微信用户记录表格数据
        wxUserLogList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 微信appid字典
        appidOptions: [],
        // 运动情况字典
        sportOptions: [],
        // 饮食情况字典
        dietOptions: [],
        // 熬夜失眠字典
        insomniaOptions: [],
        // 排便情况字典
        defecationOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          appid: null,
          phone: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {},
        logTimePickerOptions: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          },
        },
      };
    },
    created() {
      this.getList();
      this.getDicts("cus_wx_app").then(response => {
        this.appidOptions = response.data;
      });
      this.getDicts("sys_yes_no").then(response => {
        this.sportOptions = response.data;
      });
      this.getDicts("sys_yes_no").then(response => {
        this.dietOptions = response.data;
      });
      this.getDicts("sys_yes_no").then(response => {
        this.insomniaOptions = response.data;
      });
      this.getDicts("sys_yes_no").then(response => {
        this.defecationOptions = response.data;
      });
    },
    methods: {
      /** 查询微信用户记录列表 */
      getList() {
        this.loading = true;
        listWxUserLog(this.queryParams).then(response => {
          this.wxUserLogList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 微信appid字典翻译
      appidFormat(row, column) {
        return this.selectDictLabel(this.appidOptions, row.appid);
      },
      // 运动情况字典翻译
      sportFormat(row, column) {
        return this.selectDictLabel(this.sportOptions, row.sport);
      },
      // 饮食情况字典翻译
      dietFormat(row, column) {
        return this.selectDictLabel(this.dietOptions, row.diet);
      },
      // 熬夜失眠字典翻译
      insomniaFormat(row, column) {
        return this.selectDictLabel(this.insomniaOptions, row.insomnia);
      },
      // 排便情况字典翻译
      defecationFormat(row, column) {
        return this.selectDictLabel(this.defecationOptions, row.defecation);
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        const defaultAppidType = this.appidOptions.find(opt => opt.remark === 'default');

        this.form = {
          id: null,
          openid: null,
          weight: null,
          appid: defaultAppidType ? defaultAppidType.dictValue : null,
          phone: null,
          sleepTime: null,
          wakeupTime: null,
          sport: null,
          avatarUrl: null,
          diet: null,
          insomnia: null,
          defecation: null,
          water: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,
          remark: null,
          logTime: null
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
        this.ids = selection.map(item => item.openid)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加微信用户记录";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const openid = row.openid || this.ids
        getWxUserLog(openid).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改微信用户记录";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            console.log(this.form)
            if (this.form.id != null) {
              updateWxUserLog(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addWxUserLog(this.form).then(response => {
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
        const openids = row.openid || this.ids;
        this.$confirm('是否确认删除微信用户记录编号为"' + openids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delWxUserLog(openids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function () {
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有微信用户记录数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportWxUserLog(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function () {
        });
      }
    }
  };
</script>
