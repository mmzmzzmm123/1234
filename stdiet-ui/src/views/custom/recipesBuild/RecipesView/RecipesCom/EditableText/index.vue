<template>
  <div class="editable_text_wrapper">
    <div class="value" v-if="!editing" @click="handleOnClick">{{ value }}</div>
    <input
      v-else
      class="input"
      ref="inputRef"
      type="number"
      :step="5"
      :value="value"
      @blur="handleOnBlur"
    />
  </div>
</template>
<script>
export default {
  name: "EditableText",
  data() {
    return {
      editing: false,
    };
  },
  props: ["value"],
  methods: {
    handleOnClick(e) {
      if (!this.editing) {
        this.editing = true;
        this.$nextTick(() => {
          this.$refs["inputRef"].focus();
        });
      }
    },
    handleOnBlur(e) {
      const { value } = e.target;
      if (value > 0) {
        this.editing = false;
        const mValue = parseFloat(value)
        if (mValue !== parseFloat(this.value)) {
          this.$emit("onChange", mValue);
        }
      } else {
        this.$message.error("数字必须大于0");
      }
    },
  },
};
</script>
<style lang="scss" scoped>
.editable_text_wrapper {
  .value {
    cursor: pointer;
  }

  .input {
    width: 96%;
    text-align: center;
    border-radius: 4px;
    border: 1px solid #dcdfe6;

    &:hover {
      border-color: #409eff;
    }
    &:focus {
      outline: none;
      border-color: #409eff;
    }
  }
}
</style>
