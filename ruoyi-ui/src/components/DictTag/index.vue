<template>
  <div>
    <template v-for="(item, index) in options">
      <template v-if="values.includes(item.value)">
        <span
          v-if="item.raw.listClass == 'default' || item.raw.listClass == ''"
          :key="item.value"
          :index="index"
          :class="item.raw.cssClass"
          >{{ item.label }}</span
        >
        <el-tag
          v-else
          :disable-transitions="true"
          :key="item.value"
          :index="index"
          :type="item.raw.listClass == 'primary' ? '' : item.raw.listClass"
          :class="item.raw.cssClass"
        >
          {{ item.label }}
        </el-tag>
      </template>
    </template>
    <template v-if="unMatch && showValue">
      {{ value | handleValue }}
    </template>
  </div>
</template>

<script>
export default {
  name: "DictTag",
  props: {
    options: {
      type: Array,
      default: null,
    },
    value: [Number, String, Array],
    // 当未找到匹配的数据时，显示value
    showValue: {
      type: Boolean,
      default: true,
    }
  },
  computed: {
    values() {
      if (this.value !== null && typeof this.value !== 'undefined') {
        return Array.isArray(this.value) ? this.value : [String(this.value)];
      } else {
        return [];
      }
    },

    unMatch(){
      if (this.value !== null && typeof this.value !== 'undefined') {
        if(!Array.isArray(this.value)){ // 非数组
          for (let i = 0; i < this.options.length; i++) {
            // 只要找到了匹配的值，就返回false，不显示value
            if(this.options[i].value === this.value) return false;
          }
          // 遍历完options也没找到对应的value，显示value
          return true;
        }
      }
      // 没有value不显示
      return false;
    }
  },
  filters: {
    handleValue(value) {
      // 非数组直接返回
      if(!Array.isArray(value)) return value;
      // 数组处理后返回
      return value.reduce((pre, cur) => {
        return pre + '、' + cur;
      })
    },
  }
};
</script>
<style scoped>
.el-tag + .el-tag {
  margin-left: 10px;
}
</style>
