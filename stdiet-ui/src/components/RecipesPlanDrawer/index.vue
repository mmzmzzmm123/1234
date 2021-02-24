<template>
  <el-drawer
    :title="title"
    :close-on-press-escape="false"
    :visible.sync="visible"
    @closed="handleOnClosed"
    size="40%"
  >
    <div class="app-container">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleInnerOpen"
            >查看暂停记录
          </el-button>
        </el-col>
      </el-row>

      <el-drawer
        :title="innerTitle"
        :append-to-body="true"
        :close-on-press-escape="false"
        :visible.sync="innerVisible"
        @closed="handleOnInnerClosed"
      >
        <div class="app-container">
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button
                type="primary"
                icon="el-icon-plus"
                size="mini"
                @click="handleInnerOpen"
                >查看暂停记录
              </el-button>
            </el-col>
          </el-row>
        </div>
      </el-drawer>
    </div>
  </el-drawer>
</template>
<script>
export default {
  name: "RecipesPlanDrawer",
  data() {
    return {
      visible: false,
      innerVisible: false,
      title: "",
      innerTitle: "",
    };
  },
  methods: {
    showDrawer(data) {
      this.data = data;
      if (!this.data) {
        return;
      }
      this.visible = true;
      this.title = `「${this.data.name}」食谱计划`;
    },
    handleOnClosed() {
      this.data = undefined;
    },
    handleInnerOpen() {
      this.innerVisible = true;
      this.innerTitle = `「${this.data.name}」暂停记录`;
    },
    handleOnInnerClosed() {},
  },
};
</script>
<style lang="scss" scoped>
/deep/ :focus {
  outline: 0;
}
</style>
