<template>
  <div class="autohideinfo_wrapper">
    <div v-for="item in mData" :key="item">
      {{ item }}
    </div>
    <div v-if="data.length > 3">
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
      };
    },
    props: {
      data: {
        type: Array,
        default: '',
        // required: true,
      },
    },
    computed: {
      mData: function () {
        if (this.data instanceof Array) {
          return this.data.slice(0, 3);
        }
        return [];
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
