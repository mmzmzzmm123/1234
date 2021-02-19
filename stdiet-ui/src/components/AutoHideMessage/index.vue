<template>
  <div class="autohideinfo_wrapper">
    <div>
      {{ data ? data.substring(0,maxLength) : ""}}<span v-if="data && data.length > maxLength">...</span>
    </div>
    <div v-if="data && data.length > maxLength">
      <!--<div>...</div>-->
      <el-popover placement="top-start" width="300" height="400px" popper-class="autohideinfo_detial" trigger="hover">
        <div>{{ data }}</div>
        <el-button type="text" slot="reference">详情</el-button>
      </el-popover>
    </div>
  </div>
</template>

<script>
  export default {
    name: "AutoHideMessage",
    data() {
      return {
      };
    },
    props: {
      data: {
        type: String,
        default: ''
      },
      maxLength: {
        type: Number,
        default: 10
      }
    },
    computed: {

    }
  };
</script>

<style>
  .autohideinfo_wrapper {

  }

  .autohideinfo_detial {
    max-height: 340px;
    //overflow: auto;
  }
</style>
