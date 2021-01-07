<!--
 * @Author: your name
 * @Date: 2020-12-08 20:47:19
 * @LastEditTime: 2020-12-08 22:24:40
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \RuoYi-Vue\ruoyi-ui\src\views\priceTag\components\serveTag\index.vue
-->
<template>
  <div
    class='tag-item'
    :class="value.selected ? 'cur' : ''"
    @click="handleItemClick">
    <div class="icon">
      <i class="iconfont" :class="value.icon"></i>
      <div class="angle" v-show="value.selected"></div>
      <div class="gouzi" v-show="value.selected">✓</div>
    </div>
    <div class="">{{value.name}}</div>
  </div>
</template>
<script>
export default {
  name: 'TagItem',
  props:{
    value:{
      type: Object,
      default: ()=>{}
    }
  },
  data(){
    return {};
  },
  methods: {
    //点击tag
    handleItemClick(){
      this.$emit("changeState",{id: this.value.id, flg: !this.value.selected});
    }
  }
};
</script>
<style lang='scss' scoped>
.tag-item{
  display: flex;
  flex-direction: column;
  align-items: center;
  .icon{
    width: 70px;
    height: 70px;
    border: 1px solid #cccccc;
    text-align: center;
    line-height: 70px;
    position: relative;
    // box-sizing: border-box;
    i{
      font-size: 30px;
    }
    .angle{
      width: 0;
      height: 0;
      border: 20px solid;
      border-color: #DDA748 transparent transparent transparent;
      transform: rotate(-45deg);
      position: absolute;
      right: -21px;
      bottom: -21px;
      z-index: 1;
      line-height: 30px;
      color: #fff;
    }
    .gouzi{
      color: #fff;
      position: absolute;
      right: 2px;
      bottom: -28px;
      z-index: 2;
    }
  }
  &.cur .icon{
    border-color: #DDA748;
  }
}
</style>
