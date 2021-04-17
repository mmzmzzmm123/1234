<template>
  <div class="app-container ">
    <el-row :gutter="20" >
      <el-col :xs="24" :sm="{span: 16, push: 4}" :md="{span: 14, push: 5}"  :xl="{span: 10, push: 7}"  >



        <el-card class="box-card">

          <div style="height: 600px">
            <el-tabs tab-position="left" style="height: 200px;" >
              <el-tab-pane style="margin-bottom: 110px">
                <span slot="label"><i class="el-icon-date"></i> 用户管理</span>
                用户管理
              </el-tab-pane>
              <el-tab-pane >
                <span slot="label"><i class="el-icon-coin"></i> 配置管理</span>
                配置管理
              </el-tab-pane>
              <el-tab-pane >
                <span slot="label"><i class="el-icon-set-up"></i> 角色管理</span>
                角色管理
              </el-tab-pane>
              <el-tab-pane >
                <span slot="label"><i class="el-icon-turn-off"></i> 定时任务</span>
                定时任务
              </el-tab-pane>
            </el-tabs>
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
