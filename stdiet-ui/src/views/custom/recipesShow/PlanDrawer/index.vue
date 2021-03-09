<template>
  <el-drawer
    :visible.sync="visible"
    :with-header="false"
    direction="ltr"
    size="60%"
    class="plan_drawer_wrapper"
  >
    <div class="header">
      <span>食谱列表</span>
    </div>
    <div class="content">
      <el-collapse v-model="curPlanId" accordion>
        <el-collapse-item
          v-for="plan in data"
          :key="plan.id"
          :name="plan.id"
          :class="plan.id === hitPlanId ? 'collapse_item_hit' : ''"
        >
          <template slot="title">
            <div class="title_style">
              <span>{{ plan.label }}</span>
              <em
                class="el-icon-shopping-cart-full icon_style"
                @click="(e) => handleOnShoppingPlanClick(e, plan)"
              />
            </div>
          </template>
          <div
            v-for="menu in plan.menus"
            :class="`item ${menu.id === curMenuId ? 'sel_item' : ''}`"
            :key="menu.id"
            @click="handleOnCurrentChange(plan.id, menu)"
          >
            <div class="item_content">
              {{ menu.date }}
              <em class="el-icon-arrow-right" style="font-weight: bold" />
            </div>
          </div>
        </el-collapse-item>
      </el-collapse>
    </div>

    <plan-time-dialog ref="planRef" @onConfirm="handleOnTimeConfirm" />
  </el-drawer>
</template>
<script>
import PlanTimeDialog from "./PlanTimeDialog";
export default {
  name: "planDrawer",
  data() {
    return {
      visible: false,
      curPlanId: 0,
      curMenuId: 0,
      hitPlanId: 0,
    };
  },
  components: {
    PlanTimeDialog,
  },
  props: ["data", "planId", "menuId"],
  methods: {
    showDrawer() {
      this.visible = true;
    },
    handleOnCurrentChange(planId, menu) {
      // console.log({ planId, menuId });
      this.hitPlanId = planId;
      this.visible = false;
      this.curMenuId = menu.id;
      this.curPlanId = planId;

      this.$emit("plan-change", menu);
    },
    handleOnShoppingPlanClick(e, data) {
      e.stopPropagation();
      this.$refs.planRef.showDialog(data.id);
      this.visible = false;
    },
    handleOnTimeConfirm(val) {
      console.log(val);
    },
  },
  computed: {},
  watch: {
    planId(val) {
      this.curPlanId = val;
      this.hitPlanId = val;
    },
    menuId(val) {
      this.curMenuId = val;
    },
  },
};
</script>
<style lang="scss" scoped>
.plan_drawer_wrapper {
  .header {
    padding: 2px 6px;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 44px;
  }

  .content {
    height: calc(100vh - 44px);
    padding: 12px;
    overflow: auto;

    .item {
      font-size: 14px;
      height: 36px;
      cursor: pointer;
    }

    .item_content {
      display: flex;
      padding: 2px 8px 2px 24px;
      align-items: center;
      height: 100%;
      justify-content: space-between;
    }

    .sel_item {
      background: #409eff;
      color: white;
    }

    .title_style {
      display: flex;
      width: 100%;
      align-items: center;
      justify-content: space-between;

      .icon_style {
        margin-right: 16px;
        font-size: 16px;
        padding: 4px 8px;
      }
    }
  }
}
</style>

<style lang="scss">
.collapse_item_hit .el-collapse-item__header {
  color: #409eff;
}
.el-collapse-item__header.focusing {
  color: unset !important;
}
</style>
