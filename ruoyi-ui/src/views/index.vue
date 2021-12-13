<template>
  <div class="title">
    <h1>永正检测
    </h1>
  </div>
</template>

<script>
export default {
  name: "Index",
  data () {
    return {
      // 版本号
      version: "3.7.0",
    };
  },
  methods: {
    goTarget (href) {
      window.open(href, "_blank");
    },
  },
};
</script>

<style scoped lang="scss">
.title {
  font-size: 26px;
  text-align: center;
  line-height: 100%;
}
</style>

