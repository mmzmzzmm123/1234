<template>
  <el-dialog
    :visible.sync="open"
    width="900px"
    :close-on-click-modal="false"
    :show-close="false"
    center
    append-to-body>
    <detail :name="form.consultName" :start-time="form.startTime"/>

    <div slot="footer" class="dialog-footer">
      <div class="check-box">
        <el-checkbox v-model="check">我已确认并接受《壹加壹心理咨询师入驻平台服务协议》</el-checkbox>
      </div>
      <el-button type="primary" @click="submitForm">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {getListByUserName, editContract} from "@/api/psychology/contract";
import detail from "./detail";

export default {
  name: "ContractInfo",
  components: { detail },
  data() {
    return {
      open: false,
      check: false,
      form: {}
    }
  },
  mounted() {
    getListByUserName().then(response => {
      if (response && response.data && response.data.id) {
        this.form = response.data
        if (this.form && this.form.id) {
          this.edit = true
          this.open = true
        }
      }
    });
  },
  methods: {
    init(data, type) {
      this.edit = type
      this.form = data
      this.open = true
    },
    /** 提交按钮 */
    submitForm() {
      if (!this.check) {
        return this.$message.error('请勾选同意协议')
      }

      const now = new Date().getTime()
      const start = new Date(this.form.startTime).getTime()
      const end = new Date(this.form.endTime).getTime()

      // 1-待签署,2-已撤销,3-逾期未签署,4-待生效,5-生效中,6-已失效,7-终止
      // 定时任务处理待生效,逾期未签署
      if (now >= start && now <= end) {
        this.form.status = '5'
      } else if (now < start) {
        this.form.status = '4'
      } else {
        this.form.status = '3'
      }

      editContract(this.form).then(response => {
        this.$modal.msgSuccess("操作成功");
        this.close()
        this.$emit('handleOk')
      });
    },
    close() {
      this.form = {}
      this.check = false
      this.open = false
    }
  }
}
</script>

<style lang="scss" scoped>
::v-deep .el-dialog__header {
  display: none;
}

.icon {
  font-size: 20px;
  font-weight: bold;
  position: absolute;
  top: 20px;
  right: 20px;
}

.check-box {
  //display: flex;
  //justify-content: center;
  //align-items: center;
  margin: 10px auto 20px;
  //height: 30px;
  //line-height: 30px;
}

</style>
