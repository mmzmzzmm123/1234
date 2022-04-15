<template>
  <div v-if="isExternal" :style="styleExternalIcon" class="svg-external-icon svg-icon" v-on="$listeners" />
  <i v-else-if="isFont" :class="fontClass" ></i>
  <svg v-else :class="svgClass" aria-hidden="true" v-on="$listeners">
    <use :xlink:href="iconName" />
  </svg>
</template>

<script>
import { isExternal } from '@/utils/validate'

export default {
  name: 'SvgIcon',
  props: {
    iconClass: {
      type: String,
      required: true
    },
    className: {
      type: String,
      default: ''
    }
  },
  computed: {
    isExternal() {
      return isExternal(this.iconClass)
    },
    isFont() {
      let flag = false;
      // 判断图标名前缀是否是下面情况
      if (this.iconClass.startsWith("el-icon-") || this.iconClass.startsWith("fa fa-")) {
        flag = true;
      }

      return flag;
    },
    iconName() {
      return `#icon-${this.iconClass}`
    },
    fontClass() {
      if (this.className) {
        return 'svg-icon ' + this.className + ' ' + this.iconClass
      } else {
        return 'svg-icon' + ' ' + this.iconClass
      }
    },
    svgClass() {
      if (this.className) {
        return 'svg-icon ' + this.className
      } else {
        return 'svg-icon'
      }
    },
    styleExternalIcon() {
      return {
        mask: `url(${this.iconClass}) no-repeat 50% 50%`,
        '-webkit-mask': `url(${this.iconClass}) no-repeat 50% 50%`
      }
    }
  }
}
</script>

<style scoped>
.svg-icon {
  width: 1em;
  height: 1em;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
}

.svg-external-icon {
  background-color: currentColor;
  mask-size: cover!important;
  display: inline-block;
}
</style>
