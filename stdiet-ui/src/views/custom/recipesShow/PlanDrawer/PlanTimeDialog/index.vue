<template>
  <el-dialog
    :visible.sync="visible"
    width="80%"
    center
    title="请选择采购天数"
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
      <el-button @click="handleOnTimeClick(4)">后4天计划</el-button>
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
      label: "",
    };
  },

  methods: {
    showDialog({ recipesId, label }) {
      this.visible = true;
      this.label = label;
      this.recipesId = recipesId;
    },
    handleOnTimeClick(num) {
      this.visible = false;
      this.$emit("onConfirm", {
        num,
        label: this.label,
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

    & > button:nth-child(1) {
      flex: 3;
    }
    & > button:nth-child(2) {
      flex: 4;
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
