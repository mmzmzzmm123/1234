<template>
  <el-dialog
    :visible.sync="visible"
    width="80%"
    center
    title="选择时间段"
    append-to-body
    class="plan_time_wrapper"
  >
    <div class="full_day">
      <el-button style="width: 100%" @click="handleOnTimeClick(7)">
        7天计划
      </el-button>
    </div>
    <div class="seperate_day">
      <el-button @click="handleOnTimeClick(3)">前3天计划</el-button>
      <el-button @click="handleOnTimeClick(4)">后四天计划</el-button>
    </div>
  </el-dialog>
</template>
<script>
export default {
  name: "PlanTimeDialog",
  data() {
    return {
      visible: false,
      recipesId: "",
    };
  },
  methods: {
    showDialog(recipesId) {
      this.visible = true;
      this.recipesId = recipesId;
    },
    handleOnTimeClick(num) {
      this.visible = false;
      this.$emit("onConfirm", {
        num,
        recipesId: this.recipesId,
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.plan_time_wrapper {
  & > div {
    margin-top: 30vh;
  }
  .seperate_day {
    margin-top: 10px;
    display: flex;

    & > button {
      flex: 1;
    }
  }
}
</style>

<style lang="scss">
.plan_time_wrapper {
  .el-dialog:not(.is-fullscreen) {
    margin-top: 20vh !important;
  }
}
</style>
