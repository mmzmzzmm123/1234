<template>
  <el-dialog
    custom-class="export_dialog_wrapper"
    title="导出图片"
    :visible.sync="visible"
    width="480px"
  >
    <el-form :model="form" label-position="top">
      <el-form-item label="食谱开始天数">
        <el-input v-model="form.startNum" />
      </el-form-item>
      <el-form-item label="导出列项">
        <el-checkbox
          :indeterminate="isIndeterminate"
          v-model="checkAll"
          @change="handleCheckAllChange"
          >全选</el-checkbox
        >
        <el-checkbox-group
          v-model="form.exportCols"
          @change="handleOnCheckboxGroupChange"
        >
          <el-checkbox
            class="checkbox_style"
            v-for="col in cols"
            :label="col"
            :key="col"
          />
        </el-checkbox-group>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="onSubmit">确 定</el-button>
      <el-button @click="onClosed">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
export default {
  name: "ExportDialog",
  data() {
    const cols = [
      "菜品",
      "食材",
      "分量估算",
      "重量(g)",
      "蛋白质/100g",
      "脂肪/100g",
      "碳水/100g",
      "蛋白质含量",
      "脂肪含量",
      "碳水含量",
      "做法",
      "备注",
    ];
    return {
      visible: false,
      isIndeterminate: false,
      checkAll: true,
      cols,
      form: {
        startNum: 1,
        exportCols: cols.slice(),
      },
    };
  },
  methods: {
    showDialog({ num }) {
      this.visible = true;
      this.form.startNum = num;
    },
    onSubmit() {
      console.log(this.form);
      this.$emit("onConfirm", this.form);
      this.visible = false;
      setTimeout(() => {
        this.form = {
          startNum: 1,
          exportCols: this.cols.slice(),
        };
      }, 200);
    },
    onClosed() {
      this.visible = false;
      this.form.startNum = 1;
    },
    handleCheckAllChange(val) {
      this.checkAll = val;
      this.form.exportCols = val ? this.cols.slice() : [];
      this.isIndeterminate = false;
    },
    handleOnCheckboxGroupChange(val) {
      this.checkAll = val.length === this.cols.length;
      this.isIndeterminate = val.length > 0 && val.length < this.cols.length;
    },
  },
};
</script>
<style lang="scss" scoped>
.export_dialog_wrapper {
  .checkbox_style {
  }
}
</style>
