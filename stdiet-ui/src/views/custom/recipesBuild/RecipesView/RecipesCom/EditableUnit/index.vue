<template>
  <div class="editable_unit_wrapper">
    <div class="value" v-if="!editing" @click="handleOnClick">
      <span>{{ unitWeight }}</span>
    </div>
    <div v-else class="selector">
      <select
        :value="mWeight"
        @click="handleOnSelectClick"
        @change="handleOnWeightChange"
      >
        <option
          v-for="item in cusWeightOptions"
          :key="item.dictValue"
          :value="item.dictValue"
        >
          {{ item.dictLabel }}
        </option>
      </select>
      <select
        :value="mUnit"
        @click="handleOnSelectClick"
        @change="handleOnUnitChange"
      >
        <option
          v-for="item in cusUnitOptions"
          :key="item.dictValue"
          :value="item.dictValue"
        >
          {{ item.dictLabel }}
        </option>
      </select>
    </div>
  </div>
</template>
<script>
import { createNamespacedHelpers } from "vuex";
const { mapState, mapGetters } = createNamespacedHelpers("recipes");
export default {
  name: "EditableUnit",
  props: ["weight", "unit"],
  mounted() {
    window.addEventListener("click", this.handleOnWindowClick);
  },
  unmounted() {
    window.removeEventListener("click", this.handleOnWindowClick);
  },
  data() {
    return {
      editing: false,
      mWeight: this.weight,
      mUnit: this.unit,
    };
  },
  methods: {
    handleOnClick(e) {
      if (!this.editing) {
        setTimeout(() => {
          this.editing = true;
        }, 0);
      }
    },
    handleOnWindowClick(e) {
      if (this.editing) {
        // console.log("handleOnWindowClick");
        this.editing = false;
        if (
          String(this.mWeight) !== String(this.weight) ||
          String(this.mUnit) !== String(this.unit)
        ) {
          // console.log({
          //   mWeight: this.mWeight,
          //   mUnit: this.mUnit,
          //   weight: this.weight,
          //   unit: this.unit,
          // });
          this.$emit("onChange", {
            cusWeight: this.mWeight,
            cusUnit: this.mUnit,
          });
        }
      }
    },
    handleOnSelectClick(e) {
      if (this.editing) {
        e.stopPropagation();
      }
    },
    handleOnWeightChange(e) {
      const { value } = e.target;
      this.mWeight = value;
    },
    handleOnUnitChange(e) {
      const { value } = e.target;
      this.mUnit = value;
    },
  },
  watch: {
    weight(val) {
      this.mWeight = val;
    },
    unit(val) {
      this.mUnit = val;
    },
  },
  computed: {
    unitWeight() {
      return (
        `${this.cusWeightDict[this.mWeight] || ""}${
          this.cusUnitDict[this.mUnit] || ""
        }` || "_"
      );
    },
    ...mapState(["cusUnitOptions", "cusWeightOptions"]),
    ...mapGetters(["cusUnitDict", "cusWeightDict"]),
  },
};
</script>
<style lang="scss" scoped>
.editable_unit_wrapper {
  .value {
    cursor: pointer;
  }
  .selector {
    display: flex;

    select:nth-child(1) {
      margin-right: 2px;
    }

    select {
      font-size: 11px;
      border: solid 1px #dcdfe6;
      border-radius: 4px;
      appearance: none;
      -moz-appearance: none;
      -webkit-appearance: none;
      padding: 3px 6px;

      &:hover {
        border-color: #409eff;
      }
      &:focus {
        outline: none;
        border-color: #409eff;
      }
    }

    /*清除ie的默认选择框样式清除，隐藏下拉箭头*/
    select::-ms-expand {
      display: none;
    }
  }
}
</style>
