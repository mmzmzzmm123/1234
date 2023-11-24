<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="78px">
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
          v-hasPermi="['psychology:work:add']"
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
      <el-table-column v-for="(th, key) in headerList" :key="key" :fixed="th.prop === 'nickName'" :prop="th.prop" :label="th.label" :align="th.align" :width="th.prop === 'nickName' ? '120px' : '90px'">
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
            <span v-if="noItems(scope.row, th.prop)">
              <el-tooltip placement="top" effect="light" :disabled="getRows(scope.row[th.prop]).length <= 3">
                <div slot="content">
                  <template v-for="i in getRows(scope.row[th.prop])">
                    {{ i }}<br/>
                  </template>
                </div>
                <span>{{ getRow(scope.row[th.prop]) }}</span>
              </el-tooltip>
            </span>
            <el-popover v-else
                        placement="top-start"
                        title="预约咨询用户"
                        width="200"
                        trigger="click">
              <el-table :data="getItems(scope.row, th.prop)" size="mini" border>
                <el-table-column width="100" property="day" label="时间">
                  <template slot-scope="scope2">
                    {{ scope2.row.timeStart + '-' + scope2.row.timeEnd  }}
                  </template>
                </el-table-column>
                <el-table-column width="100" property="createBy" label="用户"></el-table-column>
              </el-table>
              <el-badge slot="reference" :value="getItems(scope.row, th.prop).length"  class="item">
                <span>
                  <el-tooltip placement="top" effect="light" :disabled="getRows(scope.row[th.prop]).length <= 3">
                    <div slot="content">
                      <template v-for="i in getRows(scope.row[th.prop])">
                        {{ i }}<br/>
                      </template>
                    </div>
                    <span>{{ getRow(scope.row[th.prop]) }}</span>
                  </el-tooltip>
                </span>
              </el-badge>
            </el-popover>
          </div>
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

    <!-- 添加或修改咨询服务对话框 -->
    <el-dialog title="排班设置" :visible.sync="open" width="800px" append-to-body>
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
          <el-radio-group v-model="form.status" @input="statusChange">
            <el-radio v-for="item in wList" :label="item.value">{{ item.label }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="时间段" prop="times">
          <el-checkbox-group v-model="form.times">
            <el-checkbox style="width: 130px;margin: 5px" border v-for="t in time1List" :label="t.val" :key="t.val" @change="timeListChange($event, t)">{{ t.val }}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="时间" prop="live">
          <el-checkbox-group v-model="form.live" @change="timesChange">
            <el-checkbox style="width: 130px;margin: 5px" border v-for="t in timeList" :label="t.val" :key="t.val">{{ t.label }}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
<!--        <el-form-item label="上班时间" prop="timeStart" v-if="form.status === '0'">-->
<!--          <el-time-select-->
<!--            placeholder="上班时间"-->
<!--            v-model="form.timeStart"-->
<!--            :picker-options="{-->
<!--              start: '06:00',-->
<!--              step: '01:00',-->
<!--              end: '24:00',-->
<!--              maxTime: form.timeEnd ? form.timeEnd : null-->
<!--            }">-->
<!--          </el-time-select>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="下班时间" prop="timeEnd" v-if="form.status === '0'">-->
<!--          <el-time-select-->
<!--            placeholder="上班时间"-->
<!--            v-model="form.timeEnd"-->
<!--            :picker-options="{-->
<!--              start: '06:00',-->
<!--              step: '01:00',-->
<!--              end: '24:00',-->
<!--              minTime: form.timeStart-->
<!--            }">-->
<!--          </el-time-select>-->
<!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {mapState} from "vuex";
import {getConsultAll} from "@/api/psychology/consult";
import {addWork, delWork, getWorks, getWorkHeader, updateWork} from "@/api/psychology/work";

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
      scrollLeft: 90,
      wList: this.$constants.wList,
      consultList: [],
      serveList: [],
      // 咨询服务表格数据
      workList: [],
      headerList: [],
      timeList: [],
      time1List: [
        {
          val: '凌晨',
          arr: [0,1,2,3,4,5]
        },
        {
          val: '上午',
          arr: [6,7,8,9,10,11]
        },
        {
          val: '下午',
          arr: [12,13,14,15,16,17]
        },
        {
          val: '晚上',
          arr: [18,19,20,21,22,23]
        }
      ],
      // '凌晨','上午','下午','晚上'
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      edit: false,
      fromTxt: [],
      consultId: null,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ids: [],
        month: ''
      },
      // 表单参数
      form: {
        ids: [],
        timeRange: [],
        live: [],
        times: [],
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
    this.handleQuery()
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
      const arr =  []
      for (let i = 0; i <= 23; i++) {
        arr.push({
          val: i,
          label: i < 10 ? '0' + i + ':00~0' + i + ':50' : i + ':00~' + i + ':50'
        })
      }
      this.timeList = arr

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
    // 排班时间,时间段处理
    statusChange(val) {
      console.log(val)
      if (val === '1') {
        this.form.live = []
        this.form.times = []
      }
    },
    timeListChange(val, obj) {
      if (val) {
        obj.arr.forEach(a => {
          this.form.live.push(a)
        })
      } else {
        this.form.live = this.form.live.filter(a => !obj.arr.includes(a))
      }
      this.handleTimes()
    },
    timesChange() {
      this.handleTimes()
      this.form.times = []
      this.time1List.forEach(a => {
        if (this.arrHasOtherArr(this.form.live, a.arr)) {
          this.form.times.push(a.val)
        }
      })
    },
    handleTimes() {
      this.form.live = this.form.live.filter((item, index) => {
        return this.form.live.indexOf(item, 0) === index;
      }).sort((a,b) => parseInt(a) - parseInt(b))
    },
    arrHasOtherArr(arr, arr1) {
      // return arr1.every(item => arr.includes(item))
      return arr1.some(item => arr.includes(item))
    },
    /** 查询咨询师列表 */
    async getConsultList() {
      const res = await getConsultAll()
      this.consultList = res.data
      if (this.consultList.length === 1) {
        this.consultId = this.consultList[0].id
        this.queryParams.ids.push(this.consultId)
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
    getRow(val) {
      if (val === '休息') {
        return val
      }
      const arr = JSON.parse(val)
      const list = []
      const len = arr.length > 3 ? 3 : arr.length
      for (let i = 0; i < len; i++) {
        list.push(arr[i] < 10 ? '0' + arr[i] + ':00~0' + arr[i] + ':50' : arr[i] + ':00~' + arr[i] + ':50')
      }
      if (arr.length > 3) {
        list.push('...')
      }

      return list.join(' ')
    },
    getRows(val) {
      if (val === '休息') {
        return []
      }
      const arr = JSON.parse(val)
      const list = []
      for (let i = 0; i < arr.length; i++) {
        list.push(arr[i] < 10 ? '0' + arr[i] + ':00~0' + arr[i] + ':50' : arr[i] + ':00~' + arr[i] + ':50')
      }

      return list
    },
    /** 查询咨询服务列表 */
    getHeaders() {
      getWorkHeader(this.queryParams.month).then(response => {
        if (response && response.data) {
          this.headerList = response.data
        }
      })
    },
    getList() {
      this.loading = true;

      getWorks(this.queryParams).then(response => {
        const data =  response.rows
        this.total = response.total;
        if (data) {
          data.forEach(a => {
            a.items = JSON.parse(a.items)
          })
        }

        this.workList = data
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
        live: [],
        times: [],
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
      this.queryParams.pageNum = 1
      this.getHeaders();
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.setTime()
      this.queryParams.ids = []
      if (this.consultId) {
        this.queryParams.ids.push(this.consultId)
      }
      this.handleQuery();
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
          const form = JSON.parse(JSON.stringify(this.form))

          if (form.status === '0' && form.live.length === 0) {
            return this.$modal.msgError("请选择时间");
          }
          form.live = JSON.stringify(form.live)
          form.times = form.times.join(',')

          if (form.id != null) {
            updateWork(form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWork(form).then(response => {
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
    line-height: 14px;
  }
  .el-table .el-table__cell {
    position: relative;
  }
}
</style>
