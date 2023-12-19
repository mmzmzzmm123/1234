<template>
  <div v-if="order && show">
    <el-dialog title="订单编辑" :visible.sync="show" width="700px" append-to-body>
      <el-card class="box-card">
        <el-row>
          <el-col :span="12">服务名称：{{ order.serveName }}</el-col>
          <el-col :span="12">咨询师：{{ order.consultName }}</el-col>
          <el-col :span="12">服务次数：{{ order.num + order.buyNum }}</el-col>
          <el-col :span="12">剩余次数：{{ order.num  }}</el-col>
        </el-row>
        <el-row style="margin-top: 30px;margin-bottom: 30px">
          <el-col :span="24"><span style="color: #F56C6C">确认要关闭该订单吗？</span></el-col>
          <el-col :span="24">关闭后，用户的剩余次数不能再继续预约。</el-col>
        </el-row>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submit">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { close } from "@/api/psychology/order";

export default {
  data() {
    return {
      show: false,
      order: {},
    }
  },
  methods: {
    setForm(order) {
      this.order = order
      this.show = true
    },
    submit() {
      const that = this
      if (!that.order.id) {
        return that.$modal.msgError("请选择订单");
      }

      that.$modal.confirm('确认要关闭该订单吗？').then(function() {
        close(that.order.id).then(response => {
          that.$modal.msgSuccess("操作成功");
          that.$emit('handleOk');
        });
      }).then(() => {
        that.cancel()
      }).catch(() => {});
    },
    cancel() {
      this.show = false
      this.order = {}
    },
  }
}
</script>

<style scoped>

</style>
