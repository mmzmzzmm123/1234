<template>


  <div class="dashboard-editor-container">
    <div class="ibox-title">
      <el-row :gutter="32">

        <el-col :xs="24" :sm="24" :lg="24">

          <b>选择时间范围</b>

          <el-date-picker
            v-model="daterange_time1"
            align="right"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="选择日期"
            :picker-options="pickerOptions">
          </el-date-picker>
          <el-select style="width: 90px" v-model="daterange_group_value1" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <b>--</b>
          <el-date-picker
            v-model="daterange_time2"
            align="right"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="选择日期"
            :picker-options="pickerOptions">
          </el-date-picker>
          <el-select style="width: 90px" v-model="daterange_group_value2" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <el-button @click="getDataRange" type="primary">查询</el-button>
          <el-button @click="getData" type="success">所有数据</el-button>

        </el-col>
      </el-row>
    </div>

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">

      <div class="ibox table-responsive">
        <div class="ibox-title">
          <h3>汇总表 </h3>
        </div>
        <div id="tableL01"  style="font-size:10px"  class="table table-striped table-bordered table-hover float-left">
        </div>
      </div>
    </el-row>

  </div>
</template>

<script>

  import $ from 'jquery'
  import 'bootstrap/dist/css/bootstrap.min.css'
  import 'bootstrap/dist/js/bootstrap.min'
  import 'bootstrap-table/dist/bootstrap-table.min.css'
  import 'bootstrap-table/dist/bootstrap-table.min'
  import 'popper.js/dist/popper.min'
  import 'bootstrap-table/dist/extensions/filter-control/bootstrap-table-filter-control.min.css'
  import 'bootstrap-table/dist/extensions/filter-control/bootstrap-table-filter-control.min.js'

  import resize from '../dashboard/mixins/resize'
  import {ALL, AllDataDataRange} from '@/api/dashboard/fx_technologist'

  const lineChartData = {
    xAxisData: [],
    actualData: []
  }

  export default {
    mixins: [resize],
    name: 'fx_sample_all',
    data() {
      return {
        tableData: [],
        options: [{
          value: '08:00:01',
          label: '白班'
        }, {
          value: '20:00:00',
          label: '夜班'
        }],
        daterange_group_value1: [],
        daterange_group_value2: [],
        pickerOptions: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          },
          shortcuts: [{
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date());
            }
          }, {
            text: '昨天',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit('pick', date);
            }
          }, {
            text: '一周前',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', date);
            }
          }]
        },
        daterange_time1: '',
        daterange_time2: '',
      }
    },

    created() {
      this.getData()
      this.daterange_group_value1 = "08:00:01"
      this.daterange_group_value2 = "08:00:01"

    },
    mounted() {


    },
    methods: {

      getData() {

        ALL().then(response => {


          var tableColumns = [
            {field: 'Technologist', title: '工艺员', sortable: true, filterControl: 'select', filterStrictSearch: false},
            {field: '全称', title: '客户', sortable: true, filterControl: 'input', filterStrictSearch: false},
            {
              field: 'MouldingStyleCode',
              title: '框型',
              sortable: true,
              filterControl: 'input',
              filterStrictSearch: false
            },
            {field: 'SalesMan', title: '销售员', sortable: true, filterControl: 'input', filterStrictSearch: false},
            {field: 'TagName', title: '类型', sortable: true, filterControl: 'select', filterStrictSearch: false},
            {field: 'ProductionLine', title: '产品线', sortable: true, filterControl: 'select', filterStrictSearch: false},
            {field: 'NewMould', title: '是否<br>新模</br>', sortable: true, filterControl: 'select', filterStrictSearch: false},
            {field: '地区', title: '区域', sortable: true, filterControl: 'select', filterStrictSearch: false},
            {field: '经理审核时间', title: '下单日期', sortable: true, filterControl: 'input', filterStrictSearch: false},
            {field: '分解天数', title: '分解<br>(天)</br>', sortable: true, filterControl: 'input', filterStrictSearch: false},
            {
              field: 'FactMakingDate',
              title: '完成时间',
              sortable: true,
              filterControl: 'input',
              filterStrictSearch: false
            },
            {field: '交期天数', title: '交期<br>(天)</br>', sortable: true, filterControl: 'input', filterStrictSearch: false},
            {field: '客户是否下单', title: '是否确认', sortable: true, filterControl: 'select', filterStrictSearch: false},
            {field: '折算人民币价税合计', title: '销售金额', sortable: true, filterControl: 'input', filterStrictSearch: false},
            {field: '客户数量', title: '客户<br>数量</br>', sortable: true, filterControl: 'input', filterStrictSearch: false},
            {
              field: 'FinishedDensity',
              title: '打样<br>密度</br>',
              sortable: true,
              filterControl: 'input',
              filterStrictSearch: false
            },
            {field: 'FinishedSpeed', title: '打样产能', sortable: true, filterControl: 'input', filterStrictSearch: false},
            {field: 'FinishedBy', title: '打样人员', sortable: true, filterControl: 'select', filterStrictSearch: false},
          ];

          $('#tableL01').bootstrapTable('destroy');

          $('#tableL01').bootstrapTable({//表格初始化
            columns: tableColumns,  //表头
            data: JSON.parse(response.msg), //通过ajax返回的数据
            width: 30,
            // height:900,
            method: 'get',
            // pageSize: 50,
            pageNumber: 1,
            pageList: [],
            cache: false,
            fixedColumns: true,
            fixedNumber: 1 ,
            searchText: '',
            filterControl: true,
            disableUnusedSelectOptions: true, striped: true,
            pagination: true,
            sidePagination: 'client',
            searchAlign: 'left',
            pageSize: 20,
            iconSize: 'lg',
            // search: true,

            showRefresh: false,
            showExport: false,
            showFooter: true,
            // exportTypes: ['csv', 'txt', 'xml'],
            clickToSelect: true,
          });


        })
      }


      ,

      getDataRange() {
        const fDateStart = this.daterange_time1 + ' ' + this.daterange_group_value1
        const fDateEnd = this.daterange_time2 + ' ' + this.daterange_group_value2
        AllDataDataRange(fDateStart, fDateEnd).then(response => {


          var tableColumns = [
            {field: 'Technologist', title: '工艺员', sortable: true, filterControl: 'select', filterStrictSearch: false},
            {field: '全称', title: '客户', sortable: true, filterControl: 'input', filterStrictSearch: false},
            {
              field: 'MouldingStyleCode',
              title: '框型',
              sortable: true,
              filterControl: 'input',
              filterStrictSearch: false
            },
            {field: 'SalesMan', title: '销售员', sortable: true, filterControl: 'input', filterStrictSearch: false},
            {field: 'TagName', title: '类型', sortable: true, filterControl: 'select', filterStrictSearch: false},
            {field: 'ProductionLine', title: '产品线', sortable: true, filterControl: 'select', filterStrictSearch: false},
            {field: 'SalesPerson', title: '是否新模', sortable: true, filterControl: 'input', filterStrictSearch: false},
            {field: '地区', title: '区域', sortable: true, filterControl: 'select', filterStrictSearch: false},
            {field: '经理审核时间', title: '下单日期', sortable: true, filterControl: 'input', filterStrictSearch: false},
            {field: '分解天数', title: '分解天数', sortable: true, filterControl: 'input', filterStrictSearch: false},
            {
              field: 'ExpectMakingDate',
              title: '完成时间',
              sortable: true,
              filterControl: 'input',
              filterStrictSearch: false
            },
            {field: '交期天数', title: '交期天数', sortable: true, filterControl: 'input', filterStrictSearch: false},
            {field: '客户是否下单', title: '打样客户是否确认', sortable: true, filterControl: 'input', filterStrictSearch: false},
            {field: '折算人民币价税合计', title: '销售金额', sortable: true, filterControl: 'input', filterStrictSearch: false},
            {field: '客户数量', title: '客户数量', sortable: true, filterControl: 'input', filterStrictSearch: false},
            {
              field: 'FinishedDensity',
              title: '打样密度',
              sortable: true,
              filterControl: 'input',
              filterStrictSearch: false
            },
            {field: 'FinishedSpeed', title: '打样产能', sortable: true, filterControl: 'input', filterStrictSearch: false},
            {field: 'FinishedBy', title: '打样人员', sortable: true, filterControl: 'input', filterStrictSearch: false},
          ];

          $('#tableL01').bootstrapTable('destroy');

          $('#tableL01').bootstrapTable({//表格初始化
            columns: tableColumns,  //表头
            data: JSON.parse(response.msg), //通过ajax返回的数据
            width: 30,
            // height:900,
            method: 'get',
            // pageSize: 50,
            pageNumber: 1,
            pageList: [],
            cache: false,
            searchText: '',
            filterControl: true,
            disableUnusedSelectOptions: true, striped: true,
            pagination: true,
            sidePagination: 'client',
            searchAlign: 'left',
            pageSize: 20,
            iconSize: 'lg',
            // search: true,

            showRefresh: false,
            showExport: false,
            showFooter: true,
            // exportTypes: ['csv', 'txt', 'xml'],
            clickToSelect: true,
          });


        })
      }
    }

  }


</script>


<style lang="scss" scoped>
  .dashboard-editor-container {
    padding: 32px;
    background-color: rgb(240, 242, 245);
    position: relative;

    .chart-wrapper {
      background: #fff;
      padding: 16px 16px 0;
      margin-bottom: 32px;
    }
  }

  @media (max-width: 1024px) {
    .chart-wrapper {
      padding: 8px;
    }
  }
</style>

