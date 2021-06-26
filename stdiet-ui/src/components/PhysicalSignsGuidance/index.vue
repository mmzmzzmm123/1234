<template>
  <!--<el-dialog :visible.sync="visible" :title="title" width="500px" append-to-body @closed="onClosed">-->
  <div style="margin-left: 20px">
    <h3 style="width: 200px;">{{title}}</h3>
    <el-form ref="form" :model="form" :rules="rules" label-position="top" label-width="100px">
      <el-form-item label="" prop="guidance" >
        <el-input
          style="width: 230px"
          type="textarea"
          :rows="25"
          maxlength="300"
          show-word-limit
          placeholder="请输入指导内容"
          v-model="form.guidance">
        </el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer" style="float: right">
      <el-button type="primary" @click="submit()">确 定</el-button>
      <el-button @click="onClosed()">取 消</el-button>
    </div>
  </div>
  <!--</el-dialog>-->
</template>
<script>
import { getCustomerPhysicalSignsByCusId } from "@/api/custom/customer";
import { updateHealthy } from "@/api/custom/healthy";
export default {
  name: "PhysicalSignsGuidance",
  components: {

  },
  data() {
    return {
      visible: false,
      title: "",
      data: undefined,
      form: {
        id: null,
        guidance: null
      },
      rules: {

      }
    };
  },
  methods: {
    showDialog(data, healthy,guidanceButtonNmae) {
      this.data = data;
      this.title = "修改"+guidanceButtonNmae;//"修改"+`「${data.name}」`+""+guidanceButtonNmae+"指导";
      this.form.id = healthy.id;
      this.form.guidance = healthy.guidance;
      this.visible = true;
    },
    onClosed() {
      this.visible = false;
      this.data = null;
      this.$emit('close');
    },
    submit(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.editCustomerHealthy();
        } else {
          this.$message({message: "数据未填写完整", type: "warning"});
        }
      });
    },
    editCustomerHealthy(){
      updateHealthy(this.form).then((response) => {
        if (response.code === 200) {
          this.msgSuccess("修改成功");
          this.onClosed();
          this.$emit('refreshHealthyData');
        }
      }).catch(function() {
        console.log("error");
      });
    }
  }
};
</script>

<style scoped>
  .margin-top-20{
    margin-top:20px;
  }
  .p_title_1{
    font-size: 18px;
    font-weight: bold;
    margin-top: 20px;
  }
</style>
