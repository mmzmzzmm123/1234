<!--
 * @Author: pengyu
 * @Date: 2020-12-12 22:29:27
 * @LastEditTime: 2020-12-27 18:03:11
 * @LastEditors: Please set LastEditors
 * @Description: 主题设置
 * @FilePath: \RuoYi-Vue\ruoyi-ui\src\views\priceTag\themeSet\index.vue
-->
<template>
  <div class='container'>
    <div class="theme-wrap">
       <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="横板" name="horizontal">
          <div
            v-for="(itm,idx) in hFileList"
            :key="idx"
            @click="curId=itm.id"
            :class="['theme-item-h', curId===itm.id?'cur':'']"
          >
            <el-image
              :src="itm.url"
              fit="fit"
              :preview-src-list="[itm.url]"
            ></el-image>
            <div class="name">{{itm.name}}</div>
          </div>
          <el-upload
            v-if="isSuperAdmin"
            class="theme-uploader-h"
            action="789"
            :show-file-list="false"
            accept=".png,.jpg,.jpeg"
            :before-upload="beforeUpload">
              <i class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-tab-pane>
        <el-tab-pane label="竖版" name="vertical">
          <div
            v-for="(itm,idx) in vFileList"
            :key="idx"
            @click="curId=itm.id"
            :class="['theme-item-v', curId===itm.id?'cur':'']"
          >
            <el-image :src="itm.url" fit="fit" :preview-src-list="[itm.url]"></el-image>
            <div class="name">{{itm.name}}</div>
          </div>
          <el-upload
            v-if="isSuperAdmin"
            class="theme-uploader-v"
            action="789"
            :show-file-list="false"
            accept=".png,.jpg,.jpeg"
            :before-upload="beforeUpload">
              <i class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-tab-pane>
      </el-tabs>
      <div class="btn-wrap">
        <el-button type="primary" @click="handleSubmit">提交</el-button>
      </div>
    </div>
  </div>
</template>
<script>
import {mapGetters} from "vuex"
export default {
name: 'ThemeSet',
  data(){
    return {
      activeName:"horizontal",
      hFileList:[
        {id: "1", url: require('@/assets/image/priceTag/temp_01.jpg'), name:"默认主题"},
        {id: "2",url: require("@/assets/image/priceTag/temp_02.jpg"), name:"主题2"},
        {id: "3",url: require("@/assets/image/priceTag/temp_03.jpg"), name:"主题3"},
      ],
      vFileList:[
        {id: "4",url: require('@/assets/image/priceTag/templatev1.png'), name:"主题v1"},
      ],
      curId: "1"
    };
  },
  computed:{
    isSuperAdmin(){ //是否是超管
      return  this.$store.getters.roles.includes("admin")
    }
  },
  methods:{
    //获取主题图片路径
    getImgUrl(itm){
      return require(itm.url);
    },
    //提交
    handleSubmit(){

    },
    //上传主题图片
  }
};
</script>
<style lang='scss' scoped>
.container{
  padding: 20px;
  #pane-horizontal,#pane-vertical{
    display: flex;
  }
  .theme-item-h{
    width: 280px;
    height: 208px;
    border: 1px solid #e4e4e4;
    padding: 10px;
    margin: 0 20px 20px 0;
    .name{
      text-align: center;
      line-height: 40px;
      font-size: 14px;
    }
  }
  .theme-item-v{
    width: 182px;
    height: 312px;
    border: 1px solid #e4e4e4;
    padding: 10px;
    margin: 0 20px 20px 0;
    .el-image{
      height: 90%;
    }
    .name{
      text-align: center;
      line-height: 35px;
      font-size: 14px;
    }
  }
  .theme-item-h.cur,.theme-item-v.cur{
    position: relative;
    &:after{
      content: "✓";
      width: 0;
      height: 0;
      border: 20px solid #dda748;
      border-top-color: transparent;
      border-left-color: transparent;
      position: absolute;
      right: 0;
      bottom: 0;
      color: #fff;
      font-size: 14px;
    }
  }
  .theme-uploader-h{
    width: 280px;
    height: 208px;
    border: 1px solid #e4e4e4;
    background: #eeeeee;
    font-size: 100px;
    text-align: center;
    line-height: 208px;
    color: #ffffff;
    & /deep/ .el-upload{
      width: 100%;
      height: 100%;
    }
  }
  .theme-uploader-v{
    width: 182px;
    height: 312px;
    border: 1px solid #e4e4e4;
    background: #eeeeee;
    font-size: 100px;
    text-align: center;
    line-height: 312px;
    color: #ffffff;
    & /deep/ .el-upload{
      width: 100%;
      height: 100%;
    }
  }
  .btn-wrap{
    text-align: center;
    .el-button{
      background: #DDA748;
      border: #DDA748;
      width: 100px;
    }
  }
}
</style>
