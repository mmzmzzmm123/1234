<template>
  <div class="avoid_certain_food_com_wrapper">
    <el-popover
      placement="right"
      trigger="click"
      width="600"
      title="忌口"
      @hide="handleOnHide"
    >
      <SimpleIngredientListView @onSelect="handleOnSelect" />
      <span slot="reference" class="trigger">忌口： </span>
    </el-popover>
    <div class="content">
      <el-tag
        v-for="item in data"
        :key="item.id"
        closable
        size="mini"
        type="danger"
        @close="handleOnClose(item)"
      >
        {{ item.name }}
      </el-tag>
    </div>
  </div>
</template>
<script>
import { createNamespacedHelpers } from "vuex";
const { mapMutations } = createNamespacedHelpers("recipes");
import SimpleIngredientListView from "./SimpleIngredientListView";
import { editPhysicalSigns } from "@/api/custom/healthy";

export default {
  name: "ACFCom2",
  props: ["value", "id"],
  components: {
    SimpleIngredientListView,
  },
  data() {
    return {
      data: [],
    };
  },
  watch: {
    value(val) {
      this.data = val;
    },
  },
  methods: {
    handleOnSelect(data) {
      // console.log(data);
      if (!this.data.some((obj) => obj.id === data.id)) {
        this.data.push({
          id: data.id,
          name: data.name,
        });
        // console.log(this.data);
        this.$emit("update:value", this.data);
        this.updateAvoidFoodIds({
          avoidFoodIds: this.data.map((obj) => obj.id),
        });
      }
    },
    handleOnClose(data) {
      // console.log(data);
      this.data = this.data.filter((obj) => data.id !== obj.id);
      this.$emit("update:value", this.data);
      this.updateAvoidFoodIds({ avoidFoodIds: this.data.map((obj) => obj.id) });
      // 删除后更新
      this.handleOnHide();
    },
    handleOnHide() {
      editPhysicalSigns({ id: this.id, avoidFood: this.data }).then((res) => {
        if (res.code === 200) {
          this.$message.success("忌口修改成功");
        }
      });
    },
    ...mapMutations(["updateAvoidFoodIds"]),
  },
};
</script>
<style lang="scss" scoped>
.avoid_certain_food_com_wrapper {
  display: flex;
  margin-bottom: 8px;

  .trigger {
    font-size: 14px;
    width: 42px;
    color: #8c8c8c;
    cursor: pointer;
  }

  .content {
    flex: 1;

    & > span {
      margin: 0 4px 4px 4px;
    }
  }
}
</style>
