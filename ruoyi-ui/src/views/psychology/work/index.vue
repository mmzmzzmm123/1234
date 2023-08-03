<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="咨询师" prop="ids">
        <el-select v-model="queryParams.ids" multiple clearable :disabled="consultList.length === 1">
          <el-option
            v-for="item in consultList"
            :key="item.id"
            :label="item.nickName"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="时间选择" prop="month">
<!--        <el-radio-group v-model="queryParams.month" type="button" class="mr20" size="small" @input="getList">-->
<!--          <el-radio-button v-for="(item,i) in fromTxt" :key="i" :label="item.val">{{ item.text }}</el-radio-button>-->
<!--        </el-radio-group>-->
        <el-date-picker
          :clearable="false"
          v-model="queryParams.month"
          type="month"
          value-format="yyyy-MM"
          format="yyyy-MM"
          placeholder="选择月份">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:work:add']"
        >新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table ref="myTable"
              :header-cell-style="headerStyle"
              :cell-style="cellStyle"
              border size="mini"
              v-if="headerList.length > 0"
              v-loading="loading"
              :data="workList">
<!--      <el-table-column label="咨询师" align="center" prop="consultName" />-->
      <el-table-column v-for="(th, key) in headerList" :key="key" :fixed="th.prop === 'nickName'" :prop="th.prop" :label="th.label" :align="th.align" :width="th.prop === 'nickName' ? '120px' : '60px'">
        <template slot-scope="scope">
          <div v-if="th.prop === 'nickName'">
            <el-button
              size="mini"
              type="text"
              @click="handleUpdate(scope.row)"
            >{{ scope.row[th.prop] }}</el-button>
            <div>{{ scope.row.userName }}</div>
          </div>
          <div v-else-if="!scope.row[th.prop]" style="color: #BEBEBE">
            未排班
          </div>
          <div v-else>
            <span v-if="noItems(scope.row, th.prop)">{{ scope.row[th.prop] }}</span>
            <el-popover v-else
                        placement="top-start"
                        title="预约咨询用户"
                        width="200"
                        trigger="hover">
              <el-table :data="getItems(scope.row, th.prop)" size="mini" border>
                <el-table-column width="100" property="day" label="时间">
                  <template slot-scope="scope2">
                    {{ scope2.row.timeStart + '-' + scope2.row.timeEnd  }}
                  </template>
                </el-table-column>
                <el-table-column width="100" property="createBy" label="用户"></el-table-column>
              </el-table>
              <el-badge slot="reference" :value="getItems(scope.row, th.prop).length"  class="item">
                <span>{{ scope.row[th.prop] }}</span>
              </el-badge>
            </el-popover>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改咨询服务对话框 -->
    <el-dialog title="排班设置" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="咨询师姓名" prop="ids">
          <el-select v-model="form.ids" :disabled="edit" multiple placeholder="请选择咨询师" clearable>
            <el-option
              v-for="item in consultList"
              :key="item.id"
              :label="item.nickName + '(账号:' + item.userName + ')'"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="日期范围" prop="timeRange">
          <el-date-picker
            clearable
            v-model="form.timeRange"
            :picker-options="pickerOptions"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd HH:mm:ss"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio v-for="item in wList" :label="item.value">{{ item.label }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上班时间" prop="timeStart" v-if="form.status === '0'">
          <el-time-select
            placeholder="上班时间"
            v-model="form.timeStart"
            :picker-options="{
              start: '06:00',
              step: '01:00',
              end: '24:00',
              maxTime: form.timeEnd ? form.timeEnd : null
            }">
          </el-time-select>
        </el-form-item>
        <el-form-item label="下班时间" prop="timeEnd" v-if="form.status === '0'">
          <el-time-select
            placeholder="上班时间"
            v-model="form.timeEnd"
            :picker-options="{
              start: '06:00',
              step: '01:00',
              end: '24:00',
              minTime: form.timeStart
            }">
          </el-time-select>
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
import { mapState } from "vuex";
import { getServeAll } from "@/api/psychology/serve";
import { getConsultAll } from "@/api/psychology/consult";
import { listWork, getWorks, getWork, delWork, addWork, updateWork } from "@/api/psychology/work";

export default {
  dicts: ['consult_time'],
  name: "ConsultWork",
  data() {
    return {
      today: '',
      fullToday: '',
      fullToday2: '',
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
      scrollLeft: 60,
      wList: this.$constants.wList,
      consultList: [],
      serveList: [],
      // 咨询服务表格数据
      workList: [],
      headerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      edit: false,
      fromTxt: [],
      // 查询参数
      queryParams: {
        ids: null,
        month: ''
      },
      // 表单参数
      form: {
        ids: [],
        timeRange: [],
        status: '0',
        timeStart: null,
        startDay: null,
        timeEnd: null,
        endDay: null
      },
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < new Date().getTime() - 86400000 || time.getTime() > new Date().getTime() + 86400000 * 31
        },
        shortcuts: [{
          text: '7天',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            end.setTime(start.getTime() + 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '15天',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            end.setTime(start.getTime() + 3600 * 1000 * 24 * 15);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '30天',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            end.setTime(start.getTime() + 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      // 表单校验
      rules: {
        ids: [
          { required: true, message: "咨询师不能为空", trigger: "change" }
        ],
        timeRange: [
          { required: true, message: "咨询时段不能为空", trigger: "change" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ],
        timeStart: [
          { required: true, message: "上班时间不能为空", trigger: "change" }
        ],
        timeEnd: [
          { required: true, message: "下班时间不能为空", trigger: "change" }
        ]
      }
    };
  },
  async created() {
    console.log(this.userName)
    this.setTime()
    await this.getConsultList()
    await this.getList();
  },
  computed: {
  ...mapState({
      userName: (state) => state.user.name,
    }),
  },
  methods: {
    // isAdmin() {
    //   return this.userName === 'admin'
    // },
    cellStyle({column}) {
      if (column.property < this.fullToday2) {
        return {
          backgroundColor: '#EFEFEF'
        }
      }
    },
    headerStyle({column}) {
      // console.log(column)
      const s = {}
      if (column && column.label.includes(this.today)) {
        s.color = '#FA8C16'
      }
      if (column && (column.label.includes('周六') || column.label.includes('周日'))) {
        s.backgroundColor = '#E6FFFB'
      }
      return s
    },
    setScroll() {
      this.$nextTick(()=>{
        this.$refs.myTable.bodyWrapper.scrollLeft = 0
        const ym = this.fullToday.substr(0, 7)
        const d = this.fullToday.substr(8)

        if (this.queryParams.month === ym && d > 15) {
          console.log(this.scrollLeft)
          this.$refs.myTable.bodyWrapper.scrollLeft = this.scrollLeft * (d - 1)
        }
        this.$refs.myTable.doLayout()
      })

    },
    setTime() {
      const date = new Date()
      const year = date.getFullYear()
      const month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
      this.today = date.getDate() < 10 ? month + '-0' + date.getDate() : month + '-' + date.getDate()
      this.fullToday = year + '-' + month + '-' + date.getDate()
      this.fullToday2 = year + '-' + this.today
      this.queryParams.month = year + '-' + month
      // this.fromTxt = [
      //   { text: '上个月', val: '1' },
      //   { text: year+month, val: '2' },
      //   { text: '下个月', val: '3' }
      // ]

    },
    /** 查询咨询师列表 */
    async getConsultList() {
      const res = await getConsultAll({ status: '0' })
      this.consultList = res.data
      if (this.consultList.length === 1) {
        this.queryParams.ids.push(this.consultList[0])
      }
    },
    noItems(row, th) {
      if (row.items) {
        if (row.items.length === 0) {
          return true
        }

        return !row.items.find(a => a.day && a.day === th)
      }
    },
    getItems(row, th) {
      return row.items.filter(a => a.day && a.day === th)
    },
    /** 查询咨询服务列表 */
    getList() {
      this.loading = true;
      getWorks(this.queryParams).then(response => {
        const data =  response.data
        if (data && data.items) {
          data.items.forEach(a => {
            a.items = JSON.parse(a.items)
          })
        }

        this.workList = data.items
        this.headerList = data.headers;
        this.loading = false;

        console.log(this.workList)
        this.setScroll()
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.edit = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        ids: [],
        timeRange: [],
        status: '0',
        timeStart: null,
        startDay: null,
        timeEnd: null,
        endDay: null
      };
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.setTime()
      if (this.consultList.length === 1) {
        this.queryParams.ids.push(this.consultList[0])
      } else {
        this.queryParams.ids = []
      }
      // this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.edit = false;
      this.title = "添加咨询服务";
      if (this.consultList.length === 1) {
        this.form.ids.push(this.consultList[0].id)
      }
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.open = true;
      this.edit = true;
      this.title = "添加咨询服务";
      this.form.ids.push(row.id)

      // this.getConsultServeList(row.consultId)
      // getWork(id).then(response => {
      //   const data = response.data
      //   data.timeRange = [data.timeStart, data.timeEnd]
      //   this.form = data;
      //   this.open = true;
      //   this.title = "修改咨询服务";
      // });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.startDay = this.form.timeRange[0]
          this.form.endDay = this.form.timeRange[1]
          if (this.form.id != null) {
            updateWork(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWork(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除咨询服务编号为"' + ids + '"的数据项？').then(function() {
        return delWork(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/work/export', {
        ...this.queryParams
      }, `work_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style lang="scss" scoped>
::v-deep {
  .el-table .cell {
    overflow: visible;
  }
}
</style>
