<template>
  <el-dialog>
  </el-dialog>
</template>
<script>
export default {
  name: 'ReuseDialog',
  data() {
    return {
      visible: false,
      data: undefined
    }
  },
  methods: {
    showDialog() {
      this.visible = true;
    }
  }
}
</script>
