<template>
  <div v-if="order && openPrice">
    <el-dialog title="订单编辑" :visible.sync="openPrice" width="700px" append-to-body>
      <el-card class="box-card">
        <el-row>
          <el-col :span="12">服务名称：{{ order.serveName }}</el-col>
          <el-col :span="12">咨询师：{{ order.consultName }}</el-col>
          <el-col :span="12">服务次数：{{ order.num + order.buyNum }}</el-col>
          <el-col :span="12">剩余次数：{{ order.num  }}</el-col>
        </el-row>
        <el-row style="margin-top: 30px;margin-bottom: 30px">
          <el-form ref="form" :model="formPrice" :rules="rules" label-width="130px">
            <el-form-item label="订单原价" prop="amount">
              <el-input-number size="mini" disabled :precision="2" v-model="formPrice.amount"/>
            </el-form-item>
            <el-form-item label="修改后订单价格" prop="pay">
              <el-input-number size="mini" :min="0.01" :precision="2" :max="9999"  v-model="formPrice.pay"/>
            </el-form-item>
            <el-form-item label="修改原因" prop="memo1">
              <el-input type="textarea" :rows="3" size="mini" maxlength="100" show-word-limit v-model="formPrice.memo1" placeholder="请输入修改价格原因" />
            </el-form-item>
          </el-form>
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
import { modifyPrice } from "@/api/psychology/order";

export default {
  data() {
    return {
      openPrice: false,
      formPrice: {
        amount: 0,
        pay: 0,
        memo1: null
      },
      order: {},
      // 表单校验
      rules: {
        pay: [
          { required: true, message: "修改后订单价格不能为空", trigger: "blur" }
        ],
      }
    }
  },
  methods: {
    reset() {
      this.formPrice = {
        amount: 0,
        pay: 0,
        memo1: null
      }
    },
    open() {
      this.openPrice = true
    },
    setForm(order) {
      this.reset()
      this.order = order
      this.formPrice.amount = order.amount
      this.formPrice.pay = order.pay
      this.formPrice.memo1 = order.memo1

      this.open()
    },
    submit() {
      const that = this
      if (!that.order.id) {
        return that.$modal.msgError("请选择订单");
      }

      if (that.order.status !== '0') {
        return that.$modal.msgError("订单状态异常");
      }

      if (!that.formPrice.pay) {
        return that.$modal.msgError("请输入修改后的订单价格");
      }

      that.$modal.confirm('你确定修改订单价格吗？').then(function() {
        const data = {
          id: that.order.id,
          pay: that.formPrice.pay.toFixed(2),
          memo1: that.formPrice.memo1
        }
        modifyPrice(data).then(response => {
          that.$modal.msgSuccess("修改成功");
          that.$emit('handleOk');
        });
      }).then(() => {
        that.cancel()
      }).catch(() => {});
    },
    cancel() {
      this.openPrice = false
    },
  }
}
</script>

<style scoped>

</style>
