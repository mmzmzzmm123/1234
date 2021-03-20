<template>
  <div class="ingredient_search_com_wrapper">
    <section class="header">
      <el-input
        v-model="keyword"
        size="mini"
        class="search"
        placeholder="请输入食材"
        clearable
        @keydown.enter.native="handleOnSearch"
      />
      <el-button
        size="mini"
        type="primary"
        class="search_btn"
        @click="handleOnSearch"
      >
        搜索
      </el-button>
    </section>
    <section class="content">
      <el-popover
        v-for="item in searchResults"
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
  name: "IngredientSearchCom",
  data() {
    return {
      keyword: "",
      searchResults: [],
    };
  },
  props: ["selectedIgd"],
  computed: {
    ...mapGetters(["igdTypeData"]),
  },
  methods: {
    handleOnSearch() {
      if (!this.keyword) {
        return;
      }
      this.searchResults = this.igdTypeData.reduce((arr, igd) => {
        if (igd.name.includes(this.keyword)) {
          arr.push(igd);
        }
        return arr;
      }, []);
      // console.log(this.searchResults);
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
      this.keyword = "";
      this.searchResults = [];
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/ :focus {
  outline: 0;
}

.ingredient_search_com_wrapper {
  .header {
    display: flex;
    align-items: center;
    padding: 4px;

    .label {
      width: 38px;
    }
    .search {
      flex: 1;
    }
    .search_btn {
      margin-left: 4px;
    }
  }

  .content {
    margin: 8px 0;

    .title {
      font-size: 14px;
      color: #8c8c8c;
      padding: 4px 0;
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
