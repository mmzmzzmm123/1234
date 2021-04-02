<template>
  <el-dialog
    :visible.sync="visible"
    :title="title"
    width="500px"
    append-to-body
    @closed="onClosed"
  >
    <el-form
      ref="form"
      :model="form"
      :rules="rules"
      label-position="top"
      label-width="100px"
    >
      <el-form-item label="" prop="remark">
        <el-input
          type="textarea"
          :rows="6"
          maxlength="300"
          show-word-limit
          placeholder="请输入备注"
          v-model="form.remark"
        >
        </el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submit">确 定</el-button>
      <el-button @click="onClosed">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { updateHealthy } from "@/api/custom/healthy";
import { editPhysicalSigns } from "@/api/custom/healthy";
export default {
  name: "PhysicalSignsRemark",
  components: {},
  data() {
    return {
      visible: false,
      title: "",
      data: undefined,
      type: 0,
      form: {
        id: null,
        remark: null,
      },
      rules: {},
    };
  },
  methods: {
    showDialog(data, healthy, type) {
      // console.log(healthy)
      this.data = data;
      this.type = type;
      this.title = "修改" + `「${data.name}」` + "备注";
      this.form.id = healthy.id;
      this.form.remark = healthy.remark;
      this.visible = true;
    },
    onClosed() {
      this.visible = false;
      this.data = null;
    },
    submit() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.editCustomerHealthy();
        } else {
          this.$message({ message: "数据未填写完整", type: "warning" });
        }
      });
    },
    editCustomerHealthy() {
      (this.type === 0 ? updateHealthy : editPhysicalSigns)(this.form)
        .then((response) => {
          if (response.code === 200) {
            this.msgSuccess("修改成功");
            this.onClosed();
            this.$emit("refreshHealthyData");
          }
        })
        .catch(function () {
          console.log("error");
        });
    },
  },
};
</script>

<style scoped>
.margin-top-20 {
  margin-top: 20px;
}
.p_title_1 {
  font-size: 18px;
  font-weight: bold;
  margin-top: 20px;
}
</style>
