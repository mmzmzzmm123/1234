<template>
  <div v-loading="loading" style="height: 100%;width: 100%;">
    正在加载中...
  </div>
</template>

<script>

import Cookies from "js-cookie";

export default {
  name: "loginByGitee",
  data() {
    return {
      loading: true
    }
  },
  mounted() {
    this.loading = true;
    console.log("uuid", Cookies.get("user-uuid"))
    const formBody = {
      uuid: Cookies.get("user-uuid"),
      code: this.$route.query.code
    }
    this.$store.dispatch("LoginByGitee", formBody).then(() => {
      this.$router.push({path: this.redirect || "/"}).catch(() => {
      });
    }).catch(() => {
      this.loading = false;
    });
  }
}
</script>

<style scoped>

</style>