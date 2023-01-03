<template>
  <el-dialog :visible="visible" fullscreen append-to-body @close="close">
    <div class="el-dialog__title" slot="title">
      <el-row>
        <el-col :span="12">选择用户</el-col>
        <el-col :span="12" class="textRight"><el-button size="medium" type="primary" @click="chooseOver">确定选择</el-button></el-col>
      </el-row>
    </div>
    <Index ref="userList" :multipleChoose="multipleChoose" readonly></Index>
  </el-dialog>
</template>

<script>
import Index from "@/views/system/user/index.vue";
export default {
  components: { Index },
  props: {
    multipleChoose: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      visible: false,
    };
  },
  watch: {},
  computed: {},
  methods: {
    open() {
      this.visible = true;
    },
    close() {
      this.visible = false;
      this.$refs.userList.cleanChoose();
    },chooseOver(){
      this.$emit("chooseOver",this.$refs.userList.getChooseList());
      this.close();
    }
  },
  created() {},
  mounted() {},
};
</script>
<style lang="scss" scoped>
.textRight{
  text-align: right;
  padding-right: 50px;
}
</style>