<template>
  <div class="top-right-btn" :style="style">
    <el-row>
      <el-tooltip class="item" effect="dark" :content="showSearch ? '隐藏搜索' : '显示搜索'" placement="top" v-if="search">
        <el-button size="mini" circle icon="el-icon-search" @click="toggleSearch()" />
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="刷新" placement="top">
        <el-button size="mini" circle icon="el-icon-refresh" @click="refresh()" />
      </el-tooltip>
      <el-tooltip class="item popover" effect="dark" content="显示/隐藏列" placement="top" v-if="columns">
        <el-popover placement="bottom" trigger="click">
          <div class="tree-header">显示/隐藏列</div>
          <el-tree
            ref="columnRef"
            :data="columns"
            show-checkbox
            @check="columnChange"
            node-key="key"
            :props="{ label: 'label', children: 'children' }"
          ></el-tree>
          <template #reference>
            <el-button size="mini" circle icon="el-icon-menu" />
          </template>
        </el-popover>
      </el-tooltip>
    </el-row>
  </div>
</template>
<script>
export default {
  name: "RightToolbar",
  data() {
    return {
      // 显隐数据
      value: [],
      // 弹出层标题
      title: "显示/隐藏",
      // 是否显示弹出层
      open: false,
    };
  },
  props: {
    showSearch: {
      type: Boolean,
      default: true,
    },
    columns: {
      type: Array,
    },
    search: {
      type: Boolean,
      default: true,
    },
    gutter: {
      type: Number,
      default: 10,
    },
  },
  computed: {
    style() {
      const ret = {};
      if (this.gutter) {
        ret.marginRight = `${this.gutter / 2}px`;
      }
      return ret;
    }
  },
  mounted() {
    // 显隐列初始默认隐藏列
    this.columns?.forEach((item) => {
      if (item.visible) {
        this.$refs.columnRef.setChecked(item.key, true, false);
      }
    })
  },
  methods: {
    // 搜索
    toggleSearch() {
      this.$emit("update:showSearch", !this.showSearch);
    },
    // 刷新
    refresh() {
      this.$emit("queryTable");
    },
    // 右侧列表元素变化
    dataChange(data) {
      for (let item in this.columns) {
        const key = this.columns[item].key;
        this.columns[item].visible = !data.includes(key);
      }
    },
    // 更改数据列的显示和隐藏
    columnChange(nodeObject,checkedObject) {
      this.columns?.forEach((item) => {
        item.visible = checkedObject.checkedKeys.includes(item.key);
      })
    }
  },
};
</script>
<style lang="scss" scoped>
::v-deep .el-transfer__button {
  border-radius: 50%;
  padding: 12px;
  display: block;
  margin-left: 0px;
}
::v-deep .el-transfer__button:first-child {
  margin-bottom: 10px;
}
.item.popover{
  margin-left: 10px;
}
.tree-header{
  width: 100%;
  line-height: 24px;
  text-align: center;
}
</style>
