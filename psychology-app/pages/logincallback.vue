<template><div></div></template>
<script>
import {pagesType} from "@/utils/constant";
export default {
  async created() {
    let redirectUri = uni.getStorageSync("redirectUri");
    window.location.href = redirectUri+location.search.replace("?","&");
  },
};
</script>
