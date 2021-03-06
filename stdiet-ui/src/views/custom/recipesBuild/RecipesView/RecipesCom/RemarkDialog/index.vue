<template>
  <el-dialog title="修改备注" :visible.sync="open" width="480px">
    <el-input
      type="textarea"
      v-model="remark"
      rows="6"
      placeholder="请输入备注信息"
      maxlength="300"
      show-word-limit
    />
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="onSubmit">确 定</el-button>
      <el-button @click="onClosed">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
export default {
  name: "RemarkDialog",
  data() {
    return {
      value: "",
      open: false,
      remark: "",
      data: undefined,
    };
  },
  methods: {
    showDialog(data) {
      this.open = true;
      this.data = data;
      this.remark = data.remark || "";
    },
    onClosed() {
      this.open = false;
      this.data = undefined;
      this.remark = "";
    },
    onSubmit() {
      this.$emit("onConfirm", {
        ...this.data,
        remark: this.remark,
      });
      this.data = undefined;
      this.open = false;
    },
  },
};
</script>
