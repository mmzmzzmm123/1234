<template>
    <div>
        <div style="width: 100%;height:30px;background-color: #fafcff">


          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>活动管理</el-breadcrumb-item>
            <el-breadcrumb-item>活动列表</el-breadcrumb-item>
            <el-breadcrumb-item>活动详情</el-breadcrumb-item>
          </el-breadcrumb>

        </div>
    </div>
</template>

<script>
  export default {
    components: {

    },
    props: {
      highlighted: null,//搜索是否高亮
      sousuo: null,
    },
    data: function () {
      return {
        isdescription:null
      }
    },
    mounted(){

    },
    methods: {

    }
  }
</script>
<style scoped>

</style>
