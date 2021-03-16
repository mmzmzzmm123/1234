<template>
  <div class="verify_view_wrapper">
    <div>病理忌口</div>
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
    <div style="margin: 24px 0 8px 0">涉及食材</div>
    <div v-for="key in Object.keys(igdTypeDetial).reverse()" :key="key">
      <div style="font-size: 14px; color: #8c8c8c">{{ igdTypeDict[key] }}</div>
      <div class="content">
        <span
          :class="`item ${selectedIgd === item.id ? 'selected_item' : ''} `"
          v-for="item in igdTypeDetial[key]"
          :key="item.id"
          @click="handleOnIgdClick(item)"
          >{{ item.name }}
        </span>
      </div>
    </div>
  </div>
</template>
<script>
import { createNamespacedHelpers } from "vuex";
const {
  mapActions,
  mapState,
  mapGetters,
  mapMutations,
} = createNamespacedHelpers("recipes");
export default {
  name: "VerifyView",
  data() {
    return {
      selectedNotRec: [],
      selectedIgd: 0,
    };
  },
  computed: {
    ...mapGetters(["verifyNotRecData", "igdTypeDict", "igdTypeDetial"]),
  },
  methods: {
    handleOnClick(data) {
      if (this.selectedIgd !== 0) {
        this.selectedIgd = 0;
        this.setNotRecIgds({ data: [] });
      }
      if (this.selectedNotRec.some((str) => data.name === str)) {
        this.selectedNotRec = this.selectedNotRec.filter(
          (str) => str !== data.name
        );
      } else {
        this.selectedNotRec.push(data.name);
        this.selectedNotRec = JSON.parse(JSON.stringify(this.selectedNotRec));
      }

      const notRecIgds = this.selectedNotRec.reduce((arr, cur) => {
        const tarData = this.verifyNotRecData.find((obj) => obj.name === cur);
        if (tarData) {
          tarData.data.forEach((obj) => {
            if (!arr.includes(obj.igdId)) {
              arr.push(obj.igdId);
            }
          });
        }
        return arr;
      }, []);

      // console.log({
      //   data,
      //   notRecIgds,
      //   verifyNotRecData: this.verifyNotRecData,
      // });

      this.setNotRecIgds({ data: notRecIgds });
    },
    handleOnIgdClick(data) {
      if (this.selectedNotRec.length > 0) {
        this.selectedNotRec = [];
        this.setNotRecIgds({ data: [] });
      }

      this.selectedIgd = data.id === this.selectedIgd ? 0 : data.id;

      this.setNotRecIgds({ data: [this.selectedIgd] });
    },
    ...mapMutations(["setNotRecIgds"]),
  },
};
</script>
<style lang="scss" scoped>
.verify_view_wrapper {
  height: calc(100vh - 32px);
  overflow: auto;
  .content {
    margin: 8px 0;
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
