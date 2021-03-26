<template>
  <div class="science">
    <ProjectNotice />
  </div>
</template>

<script>
import ProjectNotice from '@/views/system/notice/projectNotice';
export default {
  name: 'ScienceIndex',
  components: {
    ProjectNotice
  },
  data() {
    return {
      projectList:[]
    }
  },
  methods: {
    
  },
  mounted(){
    // console.log('mouted ...')
    for(let i = 1;i<=10;i++){
      this.projectList.push({
        id:i,
        name:`科研项目${i}`,
        type:(i%2 == 0) ? 0 : 1,
        status:(i%2 == 0) ? 0 : 1,
        budget:'100万',
        startTime:'2021/3/26',
        endTime:'2022/1/1'
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.science{
  // padding: 0 20px;
  .el-tabs{
    min-height: 636px;
  }
}
@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
