<template>
  <el-drawer
    :visible.sync="visible"
    :with-header="false"
    size="100%"
    class="user_info_drawer_wrapper"
  >
    <div class="header">
      <el-button
        type="text"
        icon="el-icon-back"
        @click="handleOnBackClick"
        class="icon_btn"
      />
      <span>个人信息</span>
      <span class="hold" />
    </div>
    <div class="content">
      <HealthView v-if="healthyData.type === 0" :data="healthyData.data" />
      <BodySignView v-else :data="healthyData.data" />
    </div>
  </el-drawer>
</template>
<script>
import HealthView from "@/components/HealthyView";
import BodySignView from "@/components/BodySignView";
export default {
  name: "UserInfoDrawer",
  props: {
    healthyData: {
      type: Object,
      default: {
        type: 0,
        data: {},
      },
    },
  },
  components: {
    BodySignView,
    HealthView,
  },
  data() {
    return {
      visible: false,
    };
  },
  methods: {
    showDrawer() {
      this.visible = true;
    },
    handleOnBackClick() {
      this.visible = false;
    },
  },
};
</script>
<style lang="scss" scoped>
.user_info_drawer_wrapper {
  .header {
    padding: 2px 6px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 44px;

    .icon_btn {
      font-size: 24px;
      padding: 6px;
      color: #696969;
    }

    .hold {
      display: block;
      width: 39px;
    }
  }

  .content {
    padding: 20px;
    height: calc(100vh - 44px);
    overflow: auto;
  }
}
</style>
