<template>
  <el-table :data="data">
    <el-table-column label="序号" type="index" align="center"/>
    <el-table-column label="咨询师账号" align="center" prop="userName" />
    <el-table-column label="咨询师名称" align="center" prop="nickName"/>
    <el-table-column label="销量" align="center" prop="num"/>
  </el-table>
</template>

<script>
export default {
  props: {
    data: {
      type: Array
    },
  },
}
</script>

<style scoped>

</style>
