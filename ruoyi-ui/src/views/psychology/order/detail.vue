<template>
  <div>
    <el-descriptions title="用户信息">
      <el-descriptions-item label="用户昵称">{{ order.nickName }}</el-descriptions-item>
      <el-descriptions-item label="用户手机号">{{ order.phone }}</el-descriptions-item>
      <el-descriptions-item label="用户ID">{{ order.userId }}</el-descriptions-item>
    </el-descriptions>
    <el-divider/>
    <el-descriptions title="购买信息">
      <el-descriptions-item label="服务名称">{{ order.serveName }}</el-descriptions-item>
      <el-descriptions-item label="服务类型">{{ order.nickName }}</el-descriptions-item>
      <el-descriptions-item label="服务次数">{{ order.num + order.buyNum }}</el-descriptions-item>
      <el-descriptions-item label="已使用次数">{{ order.buyNum }}</el-descriptions-item>
      <el-descriptions-item label="剩余次数">{{ order.num }}</el-descriptions-item>
      <el-descriptions-item label="单次服务时长">{{ order.serve.time }}分钟</el-descriptions-item>
      <el-descriptions-item label="咨询师">{{ order.consultName }}</el-descriptions-item>
      <el-descriptions-item label="咨询方式">{{ order.serve.modeName }}</el-descriptions-item>
    </el-descriptions>
    <el-divider/>
    <template v-if="['1', '2'].includes(this.order.status)">
      <div style="font-size: 16px;font-weight: bold;color: #303133;margin-bottom: 20px">
        咨询明细 <el-button  icon="el-icon-circle-plus-outline" type="text" @click="handleHx">核销</el-button>
      </div>
      <el-table :data="items" size="mini">
        <el-table-column label="咨询次数" align="center" prop="num" />
        <el-table-column label="用户预约时间" align="center" prop="time"/>
        <el-table-column label="实际咨询时间" align="center" prop="realTime"/>
        <el-table-column label="操作人" align="center" prop="updateBy"/>
      </el-table>
      <el-divider/>
    </template>
    <el-descriptions title="订单信息" :column="2">
      <el-descriptions-item label="订单编号">{{ order.orderNo }}</el-descriptions-item>
      <el-descriptions-item label="订单状态">{{ order.statusName }}</el-descriptions-item>
      <el-descriptions-item label="支付状态">{{ order.payStatusName }}</el-descriptions-item>
      <el-descriptions-item label="下单方式">{{ order.createBy === 'system' ? '用户创建' : '后台创建' }}</el-descriptions-item>
      <el-descriptions-item label="下单时间">{{ order.createTime }}</el-descriptions-item>
      <el-descriptions-item label="支付时间">{{ order.payTime }}</el-descriptions-item>
      <el-descriptions-item label="订单备注">{{ order.remark }}</el-descriptions-item>
      <el-descriptions-item label="订单总价">{{ order.amount.toFixed(2) }}</el-descriptions-item>
      <el-descriptions-item label="订单实付">{{ order.pay.toFixed(2) }}</el-descriptions-item>
<!--      <el-descriptions-item label="优惠券金额">{{ order.nickName }}</el-descriptions-item>-->
<!--      <el-descriptions-item label="积分抵扣">{{ order.nickName }}</el-descriptions-item>-->
    </el-descriptions>

    <times ref="formHx" @hxOk="hxOk" />
  </div>
</template>

<script>
import times from "./times";

export default {
  components: { times },
  name: 'OrderDetail',
  props: {
    order: {
      type: Object
    },
    items: {
      type: Array
    }
  },
  data() {
    return {
    }
  },
  methods: {
    handleHx() {
      this.$refs.formHx.setForm(this.order)
    },
    hxOk() {
      this.$emit('handleDetail', this.order.id)
    }
  }
}
</script>

<style scoped>

</style>
