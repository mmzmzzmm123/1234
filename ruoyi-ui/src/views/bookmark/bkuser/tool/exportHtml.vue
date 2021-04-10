<template>
  <div class="app-container ">
    <el-row :gutter="20" >
      <el-col :xs="24" :sm="{span: 16, push: 4}" :md="{span: 14, push: 5}"  :xl="{span: 10, push: 7}"  >
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>备份导出</span>
          </div>
          <div>
            <ul class="list-group list-group-striped">

            </ul>
          </div>
        </el-card>
      </el-col>

    </el-row>
  </div>
</template>
<script>
    export default {
        name: 'areaTree',
        components: {},

        data: function () {
            return {}
        },
        methods: {}
    }
</script>
<style>

</style>
