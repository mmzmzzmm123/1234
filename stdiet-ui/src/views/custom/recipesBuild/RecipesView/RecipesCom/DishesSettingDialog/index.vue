<template>
  <el-dialog
    :title="title"
    :visible.sync="visible"
    width="320px"
    :close-on-click-modal="false"
  >
    <div v-loading="loading" @click="handleClick" class="reuse_dialog_wrapper">
      <div style="margin-bottom: 12px">餐类</div>
      <el-radio-group v-model="type">
        <el-radio
          style="padding: 8px 0"
          v-for="item in menuTypeOptions"
          :key="item.dictValue"
          :label="item.dictValue"
        >
          {{ item.dictLabel }}
        </el-radio>
      </el-radio-group>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submit">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { createNamespacedHelpers } from "vuex";
const { mapState } = createNamespacedHelpers("recipes");
import { getDishes } from "@/api/custom/dishes";
export default {
  name: "DishesSettingDialog",
  data() {
    return {
      visible: false,
      loading: false,
      data: undefined,
      type: "",
      menuTypeOptions: [],
      title: "",
    };
  },
  methods: {
    handleClick(e) {
      e.stopPropagation();
    },
    showDialog({ numDay, data }) {
      if (!data) {
        return;
      }
      this.data = data;
      this.visible = true;
      this.loading = true;
      this.type = data.type;
      this.title = `修改第${numDay}天「${data.name}」菜品`;
      getDishes(data.dishesId).then((res) => {
        if (res.code === 200) {
          this.loading = false;
          this.menuTypeOptions = res.data.type
            .split(",")
            .sort((a, b) => a - b)
            .reduce((arr, cur) => {
              const tarOpt = this.typeOptions.find(
                (obj) => obj.dictValue === cur
              );
              if (tarOpt) {
                arr.push(tarOpt);
              }
              return arr;
            }, []);
        }
      });
    },
    cancel() {
      this.visible = false;
      this.data = undefined;
    },
    submit() {
      this.visible = false;
      this.$emit("onConfirm", { type: this.type, id: this.data.id });
    },
  },
  computed: {
    ...mapState(["typeOptions"]),
  },
};
</script>
<style lang="scss" scoped>
.reuse_dialog_wrapper {
  .item {
    margin-bottom: 12px;
  }

  .menu_type {
    padding: 8px 16px;
  }
}
</style>
