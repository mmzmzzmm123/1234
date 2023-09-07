<template>
  <div v-if="order && openHx">
    <el-dialog title="订单编辑" :visible.sync="openHx" width="700px" append-to-body>
      <el-card class="box-card">
        <el-row>
          <el-col :span="12">服务名称：{{ order.serveName }}</el-col>
          <el-col :span="12">咨询师：{{ order.consultName }}</el-col>
          <el-col :span="12">服务次数：{{ order.num + order.buyNum }}</el-col>
          <el-col :span="12">剩余次数：{{ order.num  }}</el-col>
        </el-row>
        <el-row style="text-align: center;margin-top: 30px;margin-bottom: 30px">
          <el-col :span="24">
            <span>选择核销次数：</span>
            <el-input-number @change="handleNumChange" :min="1" :max="formHx.max"  v-model="formHx.num" step-strictly/>
          </el-col>
          <el-col :span="24" v-for="item in formHx.times" style="margin-top: 20px">
            <span style="width: 120px">{{ item.label }}</span>
            <el-date-picker v-model="item.value"
                            :picker-options="pickerOptions"
                            value-format="yyyy-MM-dd HH:00"
                            format="yyyy-MM-dd HH:mm"
                            type="datetime"
                            placeholder="选择日期时间">
            </el-date-picker>
          </el-col>
        </el-row>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submit">确 定</el-button>
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { hx } from "@/api/psychology/order";

export default {
  name: 'OrderDetail',
  props: {
    // order: {
    //   type: Object
    // },
    items: {
      type: Array
    }
  },
  data() {
    return {
      openHx: false,
      formHx: {
        id: 0,
        num: 0,
        max: 0,
        times: [],
      },
      order: {},
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > new Date().getTime()
        },
      },
    }
  },
  methods: {
    reset() {
      this.formHx = {
        id: 0,
        num: 1,
        max: 1,
        times: [],
      }
    },
    open() {
      this.openHx = true
    },
    setForm(order) {
      this.reset()
      this.order = order
      const total = parseInt(this.order.num) + parseInt(this.order.buyNum)
      console.log(this.order)
      this.formHx.id =  this.order.id
      this.formHx.min = 1
      if (this.order.items && this.order.items.length > 0) {
        const num = this.order.items.filter(a => a.status === '1').length
        this.formHx.max = total - num
      } else {
        this.formHx.max = total
      }

      this.formHx.times = []
      this.formHx.times.push({
        label: '用户实际咨询时间1：',
        value: null
      })
      console.log(this.formHx)
      this.open()
    },
    handleNumChange(v) {
      const list = this.formHx.times
      const now = list.length
      if (v > now) { // add
        list.push({
          label: '用户实际咨询时间'+ v +'：',
          value: null
        })
      } else {
        list.splice(v, 1)
      }
    },
    submit() {
      console.log(this.formHx)
      const that = this
      if (!that.formHx.id) {
        return that.$modal.msgError("请选择订单");
      }
      if (that.formHx.times.length === 0) {
        return that.$modal.msgError("请选择用户实际咨询时间");
      }
      const arr = []
      that.formHx.times.filter(a => !a.value || a.value === '').forEach(b => {
        arr.push(b.label)
      })
      if (arr.length > 0) {
        return that.$modal.msgError(arr.join(',')  + " 不能为空");
      }
      that.$modal.confirm('你确定核销咨询次数吗？').then(function() {
        const data = {
          id: that.formHx.id,
          times: that.formHx.times.map(a => a.value)
        }
        hx(data).then(response => {
          that.$modal.msgSuccess("修改成功");
          that.$emit('handleOk');
        });
      }).then(() => {
        that.cancel()
      }).catch(() => {});
    },
    cancel() {
      this.openHx = false
    },
  }
}
</script>

<style scoped>

</style>
