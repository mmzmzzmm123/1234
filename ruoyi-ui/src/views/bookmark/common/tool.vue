<template>
  <div class="main">
    <div >

      <div class="toolAll">

        <!-- 微信丶在线文档等 -->
        <div  class="tool">
          <svg-icon icon-class="tool" class="toolIcon" />
          <div class="toolName">
            <span >书签去重</span>
          </div>
          <div  class="toolDescription">
            <span style="">微信丶在线文档等</span>
          </div>
        </div>

        <!-- 微信丶在线文档等 -->
        <div  class="tool">
          <svg-icon icon-class="100" class="toolIcon" />
          <div class="toolName">
            <span >文件</span>
          </div>
          <div  class="toolDescription">
            <span style="">微信丶在线文档等</span>
          </div>
        </div>

        <!-- 微信丶在线文档等 -->
        <div  class="tool">
          <svg-icon icon-class="102" class="toolIcon" />
          <div class="toolName">
            <span >文件</span>
          </div>
          <div class="toolDescription">
            <span style="">微信丶在线文档等</span>
          </div>
        </div>

        <!-- 微信丶在线文档等 -->
        <div  class="tool">
          <svg-icon icon-class="103" class="toolIcon" />
          <div class="toolName">
            <span >文件</span>
          </div>
          <div  class="toolDescription">
            <span style="">微信丶在线文档等</span>
          </div>
        </div>

        <!-- 微信丶在线文档等 -->
        <div  class="tool">
          <svg-icon icon-class="104" class="toolIcon" />
          <div class="toolName">
            <span >文件</span>
          </div>
          <div  class="toolDescription">
            <span style="">微信丶在线文档等</span>
          </div>
        </div>

        <!-- 微信丶在线文档等 -->
        <div  class="tool">
          <svg-icon icon-class="105" class="toolIcon" />
          <div class="toolName">
            <span >文件</span>
          </div>
          <div  class="toolDescription">
            <span style="">微信丶在线文档等</span>
          </div>
        </div>









      </div>




      </div>
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
<style scoped>
  .main{
    background-color: #F4F5F9;
  }
  .toolAll{
    width: 100%;
    display: flex;
    align-items: center;
    justify-items:center;
    flex-wrap:wrap;
    padding-bottom: 30px;
  }
  .tool{
    background-color: #FFFFFF;
    width: 180px;
    height: 120px;
    border-radius: 10px;
    margin-left: 48px;
    margin-top: 30px;
  }
  .toolIcon{
    width: 40px;
    height: 40px;
    margin-top: 10px;
    margin-left: 10px;
  }
  .toolName{
    margin-top: 10px
  }
  .toolName span{
    margin-left: 10px;
    color: #2B2B2B;
    font-weight: 800;
    font-size: 17px
  }
  .toolDescription{
    margin-top: 10px;
  }
  .toolDescription span{
    margin-left: 10px;
    margin-top: 18px;
    color: #ADB1B9;
  }


</style>
