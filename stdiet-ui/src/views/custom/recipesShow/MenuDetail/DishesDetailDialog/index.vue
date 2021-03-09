<template>
  <el-dialog
    :visible.sync="visible"
    :title="title"
    width="80%"
    top="30%"
    :close-on-click-modal="false"
    class="dishes_detail_dialog_wrapper"
  >
    <section class="section_zone">
      <div class="section_title">
        食材<em class="el-icon-shopping-cart-1" style="margin-left: 8px" />
      </div>
      <div v-for="igd in data.igdList" :key="igd.id" class="igd_item">
        <span>{{ igd.name }}</span>
        <span class="cusstr_style">
          <span>{{ igd.cusStr }}</span>
          <span>{{ igd.weight }}克</span>
        </span>
      </div>
    </section>
    <section class="section_zone" v-if="data.methods">
      <div class="section_title">
        做法<em class="el-icon-dish" style="margin-left: 8px" />
      </div>
      <div class="section_content">{{ data.methods }}</div>
    </section>
    <section class="section_zone" v-if="data.remark">
      <div class="section_title">
        备注<em class="el-icon-warning-outline" style="margin-left: 8px" />
      </div>
      <div class="section_content">{{ data.remark }}</div>
    </section>
  </el-dialog>
</template>
<script>
export default {
  name: "dishesDetailDialog",
  data() {
    return {
      visible: false,
      data: {},
      title: "",
    };
  },
  methods: {
    showDialog(data) {
      if (!data || !data.igdList.length) {
        return;
      }
      this.data = data;
      this.visible = true;
      this.title = `${data.name}`;
    },
  },
};
</script>
<style lang="scss" scoped>
.dishes_detail_dialog_wrapper {
  .section_zone {
    margin-bottom: 16px;
    .section_title {
      color: #262626;
      margin-bottom: 8px;
    }
  }
  .igd_item {
    height: 32px;
    padding: 0 8px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid #dfe6ec;

    .cusstr_style {
      width: 40%;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
  }

  .section_content {
    padding: 0 8px;
    margin-top: 14px;
  }
}
</style>
<style lang="scss">
.el-dialog__body {
  padding: 12px 18px;
}
.dishes_detail_dialog_wrapper {
  .el-dialog:not(.is-fullscreen) {
    margin-top: 20vh !important;
  }
}
</style>
