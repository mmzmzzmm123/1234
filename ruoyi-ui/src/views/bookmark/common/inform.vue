<template>
  <div>

    <el-badge :value="5" :max="99" class="item"><i class="el-icon-bell badgeiocn" @click="dialogVisible = true"></i>
    </el-badge>


    <el-dialog
      title="通知信息"
      :visible.sync="dialogVisible"
      width="35%"
      :before-close="handleClose">

      <!--  tabs-->
      <el-tabs :stretch="true" v-model="activeNameTabs" @tab-click="handleClick" accordion class="tabsList">

        <el-tab-pane name="first">
          <span slot="label"><i class="el-icon-message tabFont"></i> </span>


          <!--  collapse 消息列表-->
          <el-collapse v-model="activeName" accordion @change="handleChange"
                       v-if="informList !==undefined && informList.length > 0">
            <el-collapse-item name="1111">
              <template slot="title"> 一致性 Consistency 20201-10-10 20:22:22 ....</template>
              <div>20201-10-10 20:22:22</div>
              <div>与现实生活一致：与现实生活的流程、逻辑保持一致，遵循用户习惯的语言和概念；</div>
              <div>在界面中一致：所有的元素和结构需保持一致，比如：设计样式、图标和文本、元素的位置等。</div>
            </el-collapse-item>
          </el-collapse>


          <!-- 空 -->
          <div v-if="informList == undefined || informList.length <= 0">
            <div class="emptyList">
              <i class="el-icon-bell"></i>
            </div>
            <div class="emptytext">
              <span>你还没有消息</span>
            </div>
          </div>


        </el-tab-pane>
        <el-tab-pane name="second">
          <span slot="label"><i class="el-icon-chat-round tabFont"></i> </span>

          <!--  collapse 消息列表-->
          <el-collapse v-model="activeName" accordion @change="handleChange"
                       v-if="systemInformList !==undefined && systemInformList.length > 0">
            <el-collapse-item name="1111">
              <template slot="title"> 一致性 Consistency 20201-10-10 20:22:22 ....</template>
              <div>20201-10-10 20:22:22</div>
              <div>与现实生活一致：与现实生活的流程、逻辑保持一致，遵循用户习惯的语言和概念；</div>
              <div>在界面中一致：所有的元素和结构需保持一致，比如：设计样式、图标和文本、元素的位置等。</div>
            </el-collapse-item>
          </el-collapse>


          <!-- 空 -->
          <div v-if="systemInformList == undefined || systemInformList.length <= 0">
            <div class="emptyList">
              <i class="el-icon-bell"></i>
            </div>
            <div class="emptytext">
              <span>你还没有消息</span>
            </div>
          </div>


        </el-tab-pane>

        <el-tab-pane name="second2">
          <span slot="label"><i class="el-icon-document tabFont"></i> </span>

          <!--  collapse 消息列表-->
          <el-collapse v-model="activeName" accordion @change="handleChange"
                       v-if="significanceInformList !==undefined && significanceInformList.length > 0">
            <el-collapse-item name="1111">
              <template slot="title"> 一致性 Consistency 20201-10-10 20:22:22 ....</template>
              <div>20201-10-10 20:22:22</div>
              <div>与现实生活一致：与现实生活的流程、逻辑保持一致，遵循用户习惯的语言和概念；</div>
              <div>在界面中一致：所有的元素和结构需保持一致，比如：设计样式、图标和文本、元素的位置等。</div>
            </el-collapse-item>
          </el-collapse>


          <!-- 空 -->
          <div v-if="significanceInformList == undefined || significanceInformList.length <= 0">
            <div class="emptyList">
              <i class="el-icon-bell"></i>
            </div>
            <div class="emptytext">
              <span>你还没有消息</span>
            </div>
          </div>


        </el-tab-pane>
      </el-tabs>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>



    </el-dialog>
  </div>
</template>
<script>
  export default {
    name: 'areaTree',
    components: {},
    props: {},
    data: function () {
      return {
        activeName: '1',
        dialogVisible: false,
        activeNameTabs: 'first',
        userInformList: [],
        systemInformList: [],
        significanceInformList: [],
        informList:[]
      }
    },
    methods: {

      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {
          });
      },
      handleClick(tab, event) {
        console.log(tab, event);
      },
      handleChange(val) {
        console.log(val);
      }
    }
  }
</script>
<style scoped>

  .emptyList {
    width: 100%;
    height: 180px;
    display: flex;
    justify-items: center;
    align-items: center;
  }

  .emptyList i {
    font-size: 120px;
    margin: 0 auto;
    color: #D4D4D4;
  }

  .emptytext {
    width: 100%;
    display: flex;
    justify-items: center;
    align-items: center;
    font-size: 17px;
    color: #D4D4D4;
  }

  .emptytext span {
    margin: 0 auto;
  }

  .badgeiocn {
    font-size: 17px;
  }

  .tabsList {
    margin-top: -30px;
    min-height: 100px
  }

  .tabFont {
    font-size: 22px;
  }


</style>
