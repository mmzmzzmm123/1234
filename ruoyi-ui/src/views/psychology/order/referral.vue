<template>
  <div v-if="order && openRef">
    <el-dialog title="订单转介" :visible.sync="openRef" width="700px" append-to-body>
      <el-card class="box-card">
        <el-row>
          <el-col :span="12">服务名称：{{ order.serveName }}</el-col>
          <el-col :span="12">咨询师：{{ order.consultName }}</el-col>
          <el-col :span="12">服务次数：{{ order.num + order.buyNum }}</el-col>
          <el-col :span="12">剩余次数：{{ order.num  }}</el-col>
        </el-row>
        <el-row style="margin-top: 30px;margin-bottom: 30px">
          <el-form ref="form" :model="formRef" :rules="rules" label-width="130px">
            <el-form-item label="待转介咨询师" prop="consultId">
              <el-select v-model="formRef.consultId">
                <el-option
                  v-for="item in consultList"
                  :key="item.consultId"
                  :label="item.nickName"
                  :value="item.consultId"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="修改原因" prop="reason">
              <el-input type="textarea" :rows="3" size="mini" maxlength="100" show-word-limit v-model="formRef.reason" placeholder="请输入修改价格原因" />
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
import { modifyRef } from "@/api/psychology/order";

export default {
  data() {
    return {
      openRef: false,
      formRef: {
        consultId: null,
        reason: ''
      },
      order: {},
      consultList: [],
      // 表单校验
      rules: {
        consultId: [
          { required: true, message: "请选择需要转介的咨询师", trigger: "change" }
        ],
      }
    }
  },
  methods: {
    reset() {
      this.formRef = {
        consultId: null,
        reason: ''
      }
    },
    open() {
      this.openRef = true
    },
    setForm(order, list) {
      this.reset()
      this.order = order
      this.consultList = list
      this.open()
    },
    submit() {
      const that = this
      if (!that.order.id) {
        return that.$modal.msgError("请选择订单");
      }

      if (that.order.status !== '1') {
        return that.$modal.msgError("订单状态异常");
      }

      if (!that.formRef.consultId) {
        return that.$modal.msgError("请选择需要转介的咨询师");
      }

      that.$modal.confirm('你确定要转介订单吗？').then(function() {
        const data = {
          orderId: that.order.id,
          consultId: that.formRef.consultId,
          reason: that.formRef.reason
        }

        console.log(data)
        return that.$modal.msgWarning("功能开发中")
        // modifyRef(data).then(response => {
        //   that.$modal.msgSuccess("修改成功");
        //   that.$emit('handleOk');
        // });
      }).then(() => {
        that.cancel()
      }).catch(() => {});
    },
    cancel() {
      this.openRef = false
    },
  }
}
</script>

<style scoped>

</style>
