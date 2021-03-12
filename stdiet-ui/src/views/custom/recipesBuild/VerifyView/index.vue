<template>
  <div class="verify_view_wrapper">
    <div>忌口</div>
    <div class="content">
      <span
        class="item"
        v-for="item in verifyNotRecData"
        :key="item.name"
        @click="handleOnClick(item.data)"
        >{{ item.name }}</span
      >
    </div>
  </div>
</template>
<script>
import { createNamespacedHelpers } from "vuex";
const { mapActions, mapState, mapGetters } = createNamespacedHelpers("recipes");
export default {
  name: "VerifyView",
  data() {
    return {};
  },
  computed: {
    ...mapGetters(["verifyNotRecData"]),
  },
  methods: {
    handleOnClick(data) {
      console.log({ data, verifyNotRecData: this.verifyNotRecData });
    },
  },
};
</script>
<style lang="scss" scoped>
.verify_view_wrapper {
  .content {
    margin-top: 8px;
    .item {
      font-size: 14px;
      margin: 4px;
      border-radius: 8px;
      cursor: pointer;
      display: inline-block;
      color: #262626;
      border: 1px solid #8c8c8c;
      padding: 3px 8px;
      word-break: normal;

      &:hover {
        color: #d96969;
        border-color: #d96969;
      }
    }
  }
}
</style>
