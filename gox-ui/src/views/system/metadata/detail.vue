<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col span="24" class="card-box">
        <el-card>
          <div slot="header" class="clearfix">
            <span>聚合层次</span>
            <el-button style="float: right; padding: 3px 0" type="text">修改</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col span="12" class="card-box">
        <el-card>
          <table cellspacing="0" style="width: 100%;">
            <thead>
            <tr>
              <th class="is-leaf">
                <div class="cell">属性</div>
              </th>
              <th class="is-leaf">
                <div class="cell">值</div>
              </th>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td>
                <div class="cell">核心数</div>
              </td>
              <td>
                <div class="cell" v-if="server.cpu">{{ server.cpu.cpuNum }}</div>
              </td>
            </tr>
            <tr>
              <td>
                <div class="cell">用户使用率</div>
              </td>
              <td>
                <div class="cell" v-if="server.cpu">{{ server.cpu.used }}%</div>
              </td>
            </tr>
            <tr>
              <td>
                <div class="cell">系统使用率</div>
              </td>
              <td>
                <div class="cell" v-if="server.cpu">{{ server.cpu.sys }}%</div>
              </td>
            </tr>
            <tr>
              <td>
                <div class="cell">当前空闲率</div>
              </td>
              <td>
                <div class="cell" v-if="server.cpu">{{ server.cpu.free }}%</div>
              </td>
            </tr>
            </tbody>
          </table>
        </el-card>
      </el-col>
      <el-col span="12" class="card-box"></el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    name: "detail",
    props: {
      detail: Object,
    }
  }
</script>

<style scoped>

</style>
