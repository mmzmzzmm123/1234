<template>
  <div class="autohideinfo_wrapper">
    <div v-for="item in mData" :key="item">
      {{ item }}
    </div>
    <div v-if="showMore">
      <div>...</div>
      <el-popover placement="top-start" width="200" popper-class="autohideinfo_detial" trigger="hover">
        <div v-for="item in data" :key="item">{{ item }}</div>
        <el-button type="text" slot="reference">详情</el-button>
      </el-popover>
    </div>
  </div>
</template>

<script>
export default {
  name: "AutoHideInfo",
  data() {
    return {
      mData: [],
      showMore: false,
    };
  },
  props: {
    data: {
      type: Array,
      default: [],
      required: true,
    },
  },
  watch: {
    data: function (val, oldVal) {
      if (val) {
        this.showMore = val.length > 3;
        this.mData = val.slice(0, 3);
      }
    },
  },
};
</script>

<style>
  .autohideinfo_wrapper {

  }

  .autohideinfo_detial {
    max-height: 240px;
    overflow: auto;
  }
</style>
