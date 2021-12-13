<template>
  <div class="build views" style="overflow: hidden">
    <container ref="container"></container>
  </div>
</template>
<script>
import init from '@/mixins/'
export default {
  mixins: [init],
}
</script>
<style  scoped>

@import "../styles/style.scss";
::-webkit-scrollbar {
  width: 5px;
  height: 5px;
  background-color: transparent;
  border-radius: 5px;
  background-color: rgba(144, 146, 152, 0.3);
  background-color: transparent;
}
</style>


