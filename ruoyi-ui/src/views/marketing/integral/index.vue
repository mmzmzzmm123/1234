<template>
  <div class="app-container">
    <div slot="header" class="clearfix">
      <div class="container">
        <el-form size="small" label-width="120px">
          <el-form-item label="时间选择：" class="width100">
            <el-radio-group v-model="queryParams.dateLimit" type="button" class="mr20" size="small"
                            @change="selectChange(queryParams.dateLimit)">
              <el-radio-button v-for="(item,i) in fromList.fromTxt" :key="i" :label="item.val">{{ item.text }}
              </el-radio-button>
            </el-radio-group>
            <el-date-picker v-model="timeVal" value-format="yyyy-MM-dd" format="yyyy-MM-dd" size="small"
                            type="daterange" placement="bottom-end" placeholder="自定义时间" style="width: 250px;"
                            @change="onchangeTime"/>
          </el-form-item>
          <el-form-item label="用户微信昵称：">
            <el-input v-model="queryParams.keywords" placeholder="请输入用户昵称" class="selWidth" size="small">
              <el-button slot="append" icon="el-icon-search" size="small" @click="getList(1)" />
            </el-input>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <el-table
      v-loading="listLoading"
      :data="orderList"
      size="small"
      class="table"
      highlight-current-row
      :header-cell-style=" {fontWeight:'bold'}"
    >
      <el-table-column
        prop="title"
        label="标题"
        min-width="130"
      />
      <el-table-column
        sortable
        prop="balance"
        label="积分余量"
        min-width="120"
        :sort-method="(a,b)=>{return a.balance - b.balance}"
      />
      <el-table-column
        sortable
        label="明细数字"
        min-width="120"
        prop="integral"
        :sort-method="(a,b)=>{return a.integral - b.integral}"
      />
      <el-table-column
        label="备注"
        min-width="120"
        prop="mark"
      />
      <el-table-column
        label="用户昵称"
        min-width="120"
        prop="userName"
      />
      <el-table-column
        prop="updateTime"
        label="	添加时间"
        min-width="150"
      />
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { getList } from '@/api/marketing/integral'
export default {
  data() {
    return {
      loading: false,
      options: [],
      fromList: this.$constants.fromList,
      listLoading: false,
      // 总条数
      total: 0,
      // 心理咨询师表格数据
      orderList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dateLimit: '',
        keywords: ''
      },
      userIdList: [],
      userList: [],
      timeVal: [],
      values: []
    }
  },
  mounted() {
    this.getList()
    // this.getUserList()
  },
  methods: {
    seachList() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    // 选择时间
    selectChange (tab) {
      this.queryParams.dateLimit = tab
      this.queryParams.pageNum = 1
      this.timeVal = [];
      this.getList();
    },
    // 具体日期
    onchangeTime (e) {
      this.timeVal = e;
      this.queryParams.dateLimit = e ? this.timeVal.join(',') : ''
      this.queryParams.pageNum = 1
      this.getList();
    },
    // 列表
    getList() {
      this.listLoading = true
      getList(this.queryParams).then(response => {
        this.orderList = response.rows;
        this.total = response.total;
        this.listLoading = false;
      }).catch(res => {
        this.listLoading = false
      })
    }
  }
}
</script>

<style lang="sass" scoped>

</style>
