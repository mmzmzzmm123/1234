<template>
  <div class="simple_remark_com_wrapper">
    <el-popover
      placement="right"
      trigger="click"
      width="400"
      :title="title"
      @hide="handleOnHide"
    >
      <el-input
        type="textarea"
        v-model="nData"
        rows="6"
        placeholder="请输入备注信息"
        maxlength="300"
        show-word-limit
      />
      <span slot="reference" class="trigger">{{ title }}：</span>
    </el-popover>
    <div class="content">
      <span v-if="newLine">
        <div v-for="v in mValue" :key="v">
          {{ v }}
        </div>
      </span>
      <span v-else>{{ mValue }}</span>
    </div>
  </div>
</template>
<script>
export default {
  name: "RemarkCom",
  props: {
    value: {
      type: String,
      default: "",
    },
    title: {
      type: String,
      default: "备注",
    },
  },
  components: {},
  data() {
    return {
      newLine: false,
      nData: "",
    };
  },
  watch: {
    value(val) {
      // console.log(val);
      this.nData = val;
    },
    nData(val) {
      this.$emit("updata:value", val);
    },
  },
  computed: {
    mValue: function () {
      if (
        this.nData &&
        typeof this.nData === "string" &&
        (this.nData.includes("</br>") || this.nData.includes("\n"))
      ) {
        this.newLine = true;
        if (this.nData.includes("</br>")) {
          return this.nData.split("</br>");
        } else if (this.nData.includes("\n")) {
          return this.nData.split("\n");
        }
        this.newLine = false;
        return this.nData;
      }
      this.newLine = false;
      return this.nData;
    },
  },
  methods: {
    handleOnHide() {
      if (this.nData !== null && this.nData !== undefined) {
        this.$emit("onConfirm", { remark: this.nData });
      }
    },
  },
};
</script>
<style lang="scss" scoped>
.simple_remark_com_wrapper {
  display: flex;
  margin-bottom: 8px;
  font-size: 14px;

  .trigger {
    width: 42px;
    color: #8c8c8c;
    cursor: pointer;
  }

  .content {
    flex: 1;
  }
}
</style>
