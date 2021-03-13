<template>
  <div class="verify_view_wrapper">
    <div>忌口</div>
    <div class="content">
      <span
        :class="`item ${
          selectedNotRec.includes(item.name) ? 'selected_item' : ''
        } `"
        v-for="item in verifyNotRecData"
        :key="item.name"
        @click="handleOnClick(item)"
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
    return {
      selectedNotRec: [],
    };
  },
  computed: {
    ...mapGetters(["verifyNotRecData"]),
  },
  methods: {
    handleOnClick(data) {
      if (this.selectedNotRec.some((str) => data.name === str)) {
        this.selectedNotRec = this.selectedNotRec.filter(
          (str) => str !== data.name
        );
      } else {
        this.selectedNotRec.push(data.name);
        this.selectedNotRec = JSON.parse(JSON.stringify(this.selectedNotRec));
      }

      const notRecIgds = this.selectedNotRec.reduce((arr, cur) => {
        this.verifyNotRecData[cur].data.forEach((obj) => {
          if (!arr.includes(obj.igdId)) {
            arr.push(obj.igdId);
          }
        });
        return arr;
      }, []);

      console.log({
        data,
        notRecIgds,
        verifyNotRecData: this.verifyNotRecData,
      });
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
      transition: all 0.3s;

      &:hover {
        color: white;
        background: #d96969;
        border-color: #d96969;
      }
    }

    .selected_item {
      color: white;
      background: #d96969;
      border-color: #d96969;
      font-weight: bold;
    }
  }
}
</style>
