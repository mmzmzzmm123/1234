<template>
  <div class="physical_sign_com_wrapper">
    <section class="content">
      <span
        v-for="item in verifyNotRecData"
        :key="item.name"
        :class="`item ${
          selectedNotRecName === item.name ? 'selected_item' : ''
        } `"
        @click="handleOnNameClick(item)"
        >{{ item.name }}
      </span>

      <div class="divider" v-if="!!igdDatas.length" />

      <el-popover
        v-for="item in igdDatas"
        :key="item.name"
        :title="item.name"
        placement="top"
        trigger="hover"
      >
        <el-button
          type="text"
          v-for="dData in item.data"
          :key="dData.day"
          @click="handleOnDayClick(item, dData.num)"
          >{{ `第${dData.day}天` }}</el-button
        >
        <span
          slot="reference"
          :class="`item ${selectedIgd === item.id ? 'selected_item' : ''} `"
          @click="handleOnIgdClick(item)"
          >{{ item.name }}
        </span>
      </el-popover>
    </section>
  </div>
</template>
<script>
import { createNamespacedHelpers } from "vuex";
const { mapGetters } = createNamespacedHelpers("recipes");
export default {
  name: "PhysicalSignCom",
  data() {
    return {
      selectedNotRecName: "",
      igdDatas: [],
    };
  },
  props: ["selectedIgd"],
  computed: {
    ...mapGetters(["verifyNotRecData"]),
  },
  methods: {
    handleOnNameClick(obj) {
      const { name, data } = obj;
      if (this.selectedNotRecName === name) {
        this.selectedNotRecName = "";
        this.igdDatas = [];
      } else {
        this.selectedNotRecName = name;
        this.igdDatas = data;
      }
    },
    handleOnDayClick(data, numDay) {
      const { name, id } = data;
      this.$emit("onDayClick", { name, id, numDay });
    },
    handleOnIgdClick(data) {
      const { name, id } = data;
      this.$emit("onClick", { name, id });
    },
    reset() {
      this.selectedNotRecName = "";
      this.igdDatas = [];
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/ :focus {
  outline: 0;
}
.physical_sign_com_wrapper {
  .header {
    display: flex;
    align-items: center;
    padding: 4px;
  }
  .content {
    margin: 8px 0;

    .title {
      font-size: 14px;
      color: #8c8c8c;
      padding: 4px 0;
    }

    .divider {
      border-bottom: 1px solid #e6ebf5;
      margin: 12px 0;
    }

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
