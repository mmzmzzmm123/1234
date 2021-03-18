<template>
  <div :class="classname">
    <span class="info_title">{{ title }}：</span>
    <span v-if="newLine">
      <div v-for="value in mValue" :key="value">{{ value }}</div>
    </span>
    <span v-else class="info_value">{{ mValue }}</span>
  </div>
</template>
<script>
export default {
  name: "TextInfo",
  data() {
    return {
      classname: `text_info_wrapper ${this.extraclass || ""}`,
      newLine: false,
    };
  },
  computed: {
    mValue: function () {
      if (
        this.value &&
        typeof this.value === "string" &&
        (this.value.includes("</br>") || this.value.includes("\n"))
      ) {
        this.newLine = true;
        if (this.value.includes("</br>")) {
          return this.value.split("</br>");
        } else if (this.value.includes("\n")) {
          return this.value.split("\n");
        }
        return this.value;
      }
      return this.value;
    },
  },
  props: ["title", "value", "extraclass"],
};
</script>
<style  lang="scss" >
.text_info_wrapper {
  display: flex;
  margin-right: 24px;
  min-width: 120px;
  font-size: 14px;

  .info_title {
    color: #8c8c8c;
    width: auto;
  }

  .info_value {
    /* color: #696969; */
    flex: 1 1 0;
  }
}
</style>
