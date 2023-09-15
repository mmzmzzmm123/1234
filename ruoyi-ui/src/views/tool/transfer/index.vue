<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-refresh"
          size="mini"
          @click="doTransfer('userinfo')"
        >迁移用户数据</el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { execTransfer} from "@/api/tool/transfer";

export default {
  name: "Transfer",
  data() {
    return {
      // 遮罩层
      loading: true,
    };
  },
  methods: {
    /** 开始转换 */
    doTransfer(tabName) {
      this.loading = true;
      execTransfer(tabName).then(response => {
          this.loading = false;
        this.$modal.msgSuccess("转换成功");
        }
      );
    },
  }
};
</script>
