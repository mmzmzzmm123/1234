<template>
  <div class="app-container ">
    <el-row  >
      <el-col :xs="24" :sm="{span: 18, push: 3}" :md="{span: 18, push: 3}"  :xl="{span: 18, push: 3}"  >
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>关于我们</span>
          </div>
          <p class="text item" v-html = 'hello'>

             </p>
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
      return {
        hello:'     京东致力于为用户打造极致购物体验。京东是中国消费者信赖的家电购买平台，为大中城市和乡镇市场的消费者提供丰富多元、满足各种个性化需求的高品质家电和一站式优质服务。京东手机与品牌商、运营商保持了长期紧密的合作，共同推动了5G生态发展，为消费者打造一站式购机服务体验。\n' +
          '\n' +
          '    京东超市目前已经成为众多知名国际快消品牌的全渠道零售商。京东超市打造的“全城购”项目拓展全渠道业务，已经成功在全国多个城市、区域建立起了完善的全品类即时消费的零售生态。\n' +
          '\n' +
          '    全品类发展的京东生鲜通过七鲜超市、七鲜生活等业态，线上线下相结合，为消费者创造最佳体验。\n' +
          '\n'
      }
    },
    methods: {}
  }
</script>
<style scoped>
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }


</style>
