<template>
  <el-dialog
    :visible.sync="open"
    width="900px"
    :show-close="false"
    append-to-body>
    <i class="el-icon-close icon" @click="close"/>
    <contract-detail :name="form.consultName" :start-time="form.startTime"/>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import contractDetail from "@/layout/components/Contract/detail.vue";

export default {
  name: "ContractInfo",
  components: {
    contractDetail,
  },
  data() {
    return {
      open: false,
      form: {},
    }
  },
  methods: {
    init(data) {
      this.form = data
      this.open = true
    },
    close() {
      this.form = {}
      this.open = false
    }
  }
}
</script>

<style lang="scss" scoped>
::v-deep .el-dialog__header {
  display: none;
}

.main {
  position: relative;
  //line-height: 26px;
}

.content {
  border: 1px solid #e6e6e6;
  height: 600px;
  padding: 0 10px;
  overflow: scroll;
}

.text-center {
  text-align: center;
}

.icon {
  font-size: 20px;
  font-weight: bold;
  position: absolute;
  top: 20px;
  right: 20px;
}

.text-bold {
  font-weight: bold;
}

.margin-top {
  margin-top: 10px;
}

.margin-top-xl {
  margin-top: 20px;
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
