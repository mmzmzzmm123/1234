<!--
 * @Author: pengyu
 * @Date: 2020-12-12 22:29:27
 * @LastEditTime: 2021-01-10 23:08:36
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
            @click="curId=itm.themeId"
            :class="['theme-item-h', curId===itm.themeId?'cur':'']"
          >
            <el-image
              :src="getFileUrl(itm.themePicturePath)"
              fit="contain"
            ></el-image>
            <!-- :preview-src-list="[getFileUrl(itm.themePicturePath)]" -->
            <div class="name">{{itm.themeName}}</div>
            <el-dropdown v-if="isSuperAdmin" :hide-on-click="true" @command="(cmd)=>handleCommand(cmd,idx,'h')">
              <el-icon class="icon el-icon-s-tools"></el-icon>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="a"><el-icon class="icon el-icon-edit"></el-icon>编辑</el-dropdown-item>
                <el-dropdown-item command="b"><el-icon class="icon el-icon-close"></el-icon>删除</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
          <el-upload
            v-if="isSuperAdmin"
            class="theme-uploader-h"
            action="789"
            :show-file-list="false"
            accept=".png,.jpg,.jpeg"
            :before-upload="beforeUploadh">
              <i class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-tab-pane>
        <el-tab-pane label="竖版" name="vertical">
          <div
            v-for="(itm,idx) in vFileList"
            :key="idx"
            @click="curId=itm.themeId"
            :class="['theme-item-v', curId===itm.themeId?'cur':'']"
          >
            <el-image :src="getFileUrl(itm.themePicturePath)" fit="contain" :preview-src-list="[getFileUrl(itm.themePicturePath)]"></el-image>
            <div class="name">{{itm.themeName}}</div>
            <el-dropdown v-if="isSuperAdmin" :hide-on-click="true" @command="(cmd)=>handleCommand(cmd,idx,'v')">
              <el-icon class="icon el-icon-s-tools"></el-icon>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="a"><el-icon class="icon el-icon-edit"></el-icon>编辑</el-dropdown-item>
                <el-dropdown-item command="b"><el-icon class="icon el-icon-close"></el-icon>删除</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
          <el-upload
            v-if="isSuperAdmin"
            class="theme-uploader-v"
            action="789"
            :show-file-list="false"
            accept=".png,.jpg,.jpeg"
            :before-upload="beforeUploadv">
              <i class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-tab-pane>
      </el-tabs>
      <div class="btn-wrap">
        <el-button type="primary" @click="handleSubmit">提交</el-button>
      </div>
    </div>

    <!-- 编辑主题名称 -->
    <el-dialog
      :visible="visible"
      title="编辑"
      width="500px"
      class="pic-alert"
      @close="visible=false"
    >
      <el-form
        :model="themeForm"
        class="pic-form"
        ref="themeForm"
      >
        <el-form-item label="主题名称：" label-width="100px" prop="themeName" :rules="{required: true,message:'主题名称不能为空'}">
          <el-input v-model="themeForm.themeName"></el-input>
        </el-form-item>
        <el-form-item class="btn-form-item">
          <el-button type="primary" @click="handleRename">提交</el-button>
          <el-button @click="visible=false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import {mapGetters} from "vuex";
import { getToken } from '@/utils/auth';
import axios from "axios";
import {queryThemeSet,queryCurTheme, saveThemeSet, renameThemeSet, deleteThemeSet} from "@/api/priceTag";

export default {
name: 'ThemeSet',
  data(){
    return {
      activeName:"horizontal",
      hFileList:[],
      vFileList:[],
      curId: "",
      visible: false,
      themeForm: {
        themeId: "",
        themeName: ""
      }
    };
  },
  computed:{
    isSuperAdmin(){ //是否是超管
      return  this.$store.getters.roles.includes("admin")
    }
  },
  mounted(){
    this.queryThemeList();
  },
  methods:{
    //获取酒店关联主题
    async queryThemeList(){
      const rsp = await queryThemeSet();
      const {data} = rsp;
      this.hFileList = data.filter(itm => itm.themeLayout === 0);
      this.vFileList = data.filter(itm => itm.themeLayout === 1);
      const curData = await queryCurTheme();
      this.curId = curData.data ? curData.data.themeId : "";
    },
    //获取图片完整路径
    getFileUrl(url){
      return `http://139.196.112.8/prod-api${url}`
    },
    //提交
    handleSubmit(){
      if(this.curId){
        saveThemeSet({themeId: this.curId})
        .then((res)=>{
          this.msgSuccess(res.msg);
        })
      }else{
        this.msgError("请选择主题");
      }
    },
    //上传主题图片
    beforeUploadh(file){
      const _this = this;
      const fd = new FormData();
      fd.append('themePicture', file);
      fd.append('themeLayout', 0);

      const headers = {
        "Content-Type": "multipart/form-data",
        "Authorization": `Bearer ${getToken()}`
      };

      axios({
        method: "post",
        url: `${process.env.VUE_APP_BASE_API}/hotel/theme/upload`,
        data: fd,
        headers
      })
      .then((res)=>{
        _this.hFileList.push(res.data);
      });
    },
    //上传主题图片
    beforeUploadv(file){
      const _this = this;
      const fd = new FormData();
      fd.append('themePicture', file);
      fd.append('themeLayout', 1);

      const headers = {
        "Content-Type": "multipart/form-data",
        "Authorization": `Bearer ${getToken()}`
      };

      axios({
        method: "post",
        url: `${process.env.VUE_APP_BASE_API}/hotel/theme/upload`,
        data: fd,
        headers
      })
      .then((res)=>{
        _this.vFileList.push(res.data);
      });
    },
    //主题图片操作
    handleCommand(cmd, index,type){
      const _this = this;
      let obj;
      if(type === "h"){
        obj = this.hFileList[index]
      }else{
        obj = this.vFileList[index]
      }
      if(cmd === "a"){ //编辑
        this.$set(this.themeForm, "themeId", obj.themeId);
        this.$set(this.themeForm, "themeName", obj.themeName);
        this.visible = true;
      }else if(cmd === "b"){ //删除
        this.$confirm('确定删除该主题?', '提示', {
          confirmButtonText: '确定',
          type: 'warning'
        })
        .then(()=>{
          deleteThemeSet({themeId: obj.themeId})
          .then((res) => {
            _this.msgSuccess("删除成功");
            _this[`${type}FileList`].splice(index, 1);
          })
        })
      }else{
        return;
      }
    },
    //重命名
    handleRename(){
      const _this = this;
      this.$refs.themeForm.validate((valid)=>{
        if(valid){
          renameThemeSet(_this.themeForm)
          .then((res)=>{
            _this.msgSuccess("编辑成功");
            _this.visible = false;
            _this.queryThemeList();
          })
        }
      });
    }
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
    position: relative;
    .el-image{
      height: 80%;
    }
    .name{
      text-align: center;
      line-height: 40px;
      font-size: 14px;
    }
    .el-dropdown{
      position: absolute;
      top: 10px;
      right: 10px;
    }
    .icon{
      color: #1890ff;
      font-size: 14px;
      display: none;
    }
    &:hover{
      border-color: #dda748;
      .icon{
        display: block;
      }
    }
  }
  .theme-item-v{
    width: 182px;
    height: 312px;
    border: 1px solid #e4e4e4;
    padding: 10px;
    margin: 0 20px 20px 0;
    position: relative;
    .el-image{
      height: 90%;
    }
    .name{
      text-align: center;
      line-height: 35px;
      font-size: 14px;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
    .el-dropdown{
      position: absolute;
      top: 10px;
      right: 10px;
    }
    .icon{
      color: #1890ff;
      font-size: 14px;
      display: none;
    }
    &:hover{
      border-color: #dda748;
      .icon{
        display: block;
      }
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
  .pic-alert{
    .btn-form-item{
      text-align: right;
    }
  }
}
</style>
