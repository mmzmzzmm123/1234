<template>
  <view class="cu-steps">
    <view class="cu-item" :class="index>active?'':'text-orange'" v-for="(item,index) in items" :key="index">
      <text class="num" :class="index===2?'err':''" :data-index="index + 1"></text> {{item.name}}
    </view>
  </view>
</template>

<script>
export default {
  name: "psySteps",
  props: {
    active: {
      type: Number,
      default: 0,
    },
    items: {
      type: Array,
      default: [],
    },
  }
}
</script>

<style scoped>

</style>