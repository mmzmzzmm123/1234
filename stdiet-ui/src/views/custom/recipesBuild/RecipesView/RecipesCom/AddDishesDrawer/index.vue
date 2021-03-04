<template>
  <el-drawer
    :title="title"
    :visible.sync="visible"
    :close-on-press-escape="false"
    :before-close="handleOnClosed"
    :wrapperClosable="false"
    class="add_dishes_drawer_wrapper"
    direction="ltr"
    size="40%"
  >
    <div class="content_wrapper">
      <div class="content_detail">
        <el-steps :active="active" style="margin-bottom: 20px">
          <el-step title="选择菜品" />
          <el-step title="配置菜品" />
        </el-steps>

        <SelectDishes
          ref="dishesRef"
          v-show="active === 0"
          @onChange="handleCurrentChange"
        />
        <ConfigDishes
          v-show="active === 1"
          v-bind="selDishes"
          :typeOptions="dishesTypeOptions"
          @onChange="handleOnConfigChange"
        />
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button
          size="small"
          v-show="active === 1"
          type="info"
          @click="handleOnLastStepClick"
          >上一步</el-button
        >
        <el-button
          size="small"
          v-show="active === 1"
          type="primary"
          @click="handleOnConfirmClick"
          >确 定</el-button
        >
        <el-button size="small" @click="handleOnCancelClick">取 消</el-button>
      </div>
    </div>
  </el-drawer>
</template>
<script>
import { createNamespacedHelpers } from "vuex";
const { mapState, mapGetters } = createNamespacedHelpers("recipes");

import SelectDishes from "./SelectDishes";
import ConfigDishes from "./ConfigDishes";

export default {
  name: "AddDishesDrawer",
  components: {
    SelectDishes,
    ConfigDishes,
  },
  data() {
    return {
      id: "",
      type: "",
      visible: false,
      title: "",
      active: 0,
      dishesTypeOptions: [],
      selDishes: {
        name: "",
        type: "",
        igdList: [],
        recTags: [],
        notRecTags: [],
      },
    };
  },
  computed: {
    ...mapState(["typeOptions", "currentDay"]),
    ...mapGetters(["typeDict"]),
  },
  methods: {
    showDrawer({ data, type, numDay }) {
      this.title = "添加菜品";
      this.id = "";
      this.type = "";
      if (data) {
        this.title = `替换第${numDay}天${this.typeDict[data.type]}的「${
          data.name
        }」`;
        this.id = data.id;
      } else if (type) {
        this.title = `添加第${numDay}天${this.typeDict[type]}菜品`;
        this.type = type;
      }
      this.visible = true;
      this.$nextTick(() => {
        this.$refs.dishesRef.getList({ type });
      });
    },
    handleOnClosed(done) {
      this.$refs.dishesRef.clean();
      done();
    },
    handleCurrentChange(data) {
      if (!data) {
        return;
      }
      // console.log(data);
      this.selDishes = data;
      this.active = 1;
      this.dishesTypeOptions = (this.type || data.type)
        .split(",")
        .reduce((arr, cur, idx) => {
          if (idx === 0) {
            this.selDishes.type = cur;
          }
          const tarOpt = this.typeOptions.find((obj) => obj.dictValue === cur);
          if (tarOpt) {
            arr.push(tarOpt);
          }
          return arr;
        }, []);
    },
    handleOnConfigChange(val) {
      Object.keys(val).forEach((key) => {
        this.selDishes[key] = val[key];
      });
    },
    handleOnCancelClick() {
      this.visible = false;
      this.active = 0;
      this.$refs.dishesRef.clean();
    },
    handleOnLastStepClick() {
      this.active = 0;
    },
    handleOnConfirmClick() {
      this.visible = false;
      this.active = 0;

      const {
        id,
        methods,
        name,
        notRecTags,
        recTags,
        type,
        igdList,
      } = this.selDishes;
      this.$emit("onConfirm", {
        type: this.id ? "replace" : "add",
        data: {
          id: this.id || new Date().getTime(),
          dishesId: id,
          methods,
          name,
          notRecTags,
          recTags,
          type,
          igdList,
        },
      });
      // console.log(this.selDishes);
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/ :focus {
  outline: 0;
}
.add_dishes_drawer_wrapper {
  .content_wrapper {
    padding: 16px 20px;
    height: 100%;
    overflow: auto;
    display: flex;
    flex-direction: column;

    .content_detail {
      flex: 1 1 0;
      padding: 12px;
      overflow: auto;
    }

    .dialog-footer {
      flex: 0 0 45px;
      display: inline-flex;
      align-items: center;
      justify-content: flex-end;
      padding: 0 12px;
    }
  }
}
</style>
<style lang="scss">
.add_dishes_drawer_wrapper {
  #el-drawer__title {
    margin-bottom: 0 !important;
  }
}
</style>
