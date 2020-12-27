<!--
 * @Author: pengyu
 * @Date: 2020-12-12 21:00:39
 * @LastEditTime: 2020-12-24 20:46:03
 * @LastEditors: Please set LastEditors
 * @Description: 房型图片
 * @FilePath: \RuoYi-Vue\ruoyi-ui\src\views\priceTag\roomPicture\index.vue
-->
<template>
  <div class='container'>
    <div class="img-wrap">
      <draggable v-model="fileList" class="img-drag-wrap" :forceFallback="true" @end="handleDragEnd">
        <div
          v-for="(item,index) in fileList"
          :key="index"
          :class="['img-item', item.status===1 ? 'disabled' : '']"
        >
          <el-image
            :src="getFileUrl(item.picturePath)"
            fit="fit"
          ></el-image>
          <div class="name">{{item.pictureName}}</div>

          <el-dropdown :hide-on-click="true" @command="(cmd)=>handleCommand(cmd,index)">
            <el-icon class="icon el-icon-s-tools"></el-icon>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="a"><el-icon class="icon el-icon-refresh"></el-icon>替换</el-dropdown-item>
              <el-dropdown-item command="b"><el-icon class="icon el-icon-edit"></el-icon>编辑</el-dropdown-item>
              <el-dropdown-item command="c"><el-icon class="icon el-icon-close"></el-icon>删除</el-dropdown-item>
              <el-dropdown-item command="d"><el-icon class="icon el-icon-switch-button"></el-icon>{{item.status===0 ? "停用" : "启用"}}</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </draggable>

      <el-upload
        class="avatar-uploader"
        action="789"
        :show-file-list="false"
        accept=".png,.jpg,.jpeg"
        :on-success="handleUploadSuccess"
        :before-upload="beforeUpload">
          <i class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <el-upload
        ref="replaceloader"
        class="replace-uploader"
        action="/prod-api/hotel/roomPicture"
        :show-file-list="false"
        accept=".png,.jpg,.jpeg"
        :before-upload="beforeReplace"
      ></el-upload>
    </div>
    <!-- 编辑 -->
    <el-dialog
      :visible="visible"
      title="编辑"
      width="500px"
      class="pic-alert"
      @close="visible=false"
    >
      <el-form
        :model="picForm"
        class="pic-form"
        ref="picForm"
      >
        <el-form-item label="图片名称：" label-width="100px" prop="pictureName" :rules="{required: true,message:'图片名称不能为空'}">
          <el-input v-model="picForm.pictureName"></el-input>
        </el-form-item>
        <el-form-item class="btn-form-item">
          <el-button type="primary" @click="handleSubmit">提交</el-button>
          <el-button @click="visible=false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import draggable from "vuedraggable";
import {getRoomPicture, deleteRoomPicture,enableRoomPicture,replaceRoomPicture,editRoomPicture,orderRoomPicture} from "@/api/priceTag";
import { getToken } from '@/utils/auth'
import axios from "axios"

export default {
name: 'RoomPicture',
  data(){
    return {
      fileList: [],
      picForm:{
        pictureName:""
      },
      visible: false,
      replaceIndex: ""
    };
  },
  components: {draggable},
  mounted(){
    this.queryPicture()
  },
  methods:{
    //查询房间图片
    queryPicture(){
      getRoomPicture()
      .then((res)=>{
        this.fileList = [...res.data];
      });
    },
    //图片操作
    handleCommand(cmd, index){
      switch(cmd){
        case "a": //替换
          this.replacePic(index);
          break;
        case "b": //编辑
          this.editPic(index);
          break;
        case "c": //删除
          this.deletePic(index);
          break;
        case "d":
          this.enablePic(index);
          break;
      }
    },
    //上传前预处理
    beforeUpload(file){
      // const {size} = file;
      // if(size / 1024 / 1204 > 2){
      //   this.msgError("二维码图片大于2M");
      //   return false;
      // }

      const fd = new FormData()
      fd.append('roomPicture', file);

      const headers = {
        "Content-Type": "multipart/form-data",
        "Authorization": `Bearer ${getToken()}`
      };

      axios({
        method: "post",
        url: `${process.env.VUE_APP_BASE_API}/hotel/roomPicture/upload`,
        data: fd,
        headers
      })
      .then((res)=>{
        const {data} = res;
        this.fileList.push(data);
      });
    },
    //上传成功
    handleUploadSuccess(response, file, fileList){

    },
    //替换图片
    replacePic(index){
      this.$refs.replaceloader.$children[0].$refs.input.click();
      this.replaceIndex = index;
    },
    //编辑图片
    editPic(index){
      this.visible = true;
      const item = this.fileList[index];
      Object.assign(this.picForm, item);
    },
    //删除图片
    deletePic(index){
      const _this = this;
      this.$confirm('确定删除该图片?', '提示', {
        confirmButtonText: '确定',
        type: 'warning'
      })
      .then(async ()=>{
        const { pictureId } = _this.fileList[index];
        await deleteRoomPicture({pictureId});
        this.msgSuccess(`删除成功`);
        this.queryPicture();
      })
    },
    //停用图片
    async enablePic(index){
      const obj = this.fileList[index];
      const {pictureId, status} = obj;
      await enableRoomPicture({pictureId, status: status===0?1:0});
      this.msgSuccess(`${status===0?"停用":"启用"}成功`);
      this.queryPicture();
    },
    //编辑图片信息
    handleSubmit(){
      const _this = this;
      this.$refs.picForm.validate((valid)=>{
        if(valid){
          const pa = {
            pictureName: _this.picForm.pictureName,
            orderNum: _this.picForm.orderNum,
            pictureId: _this.picForm.pictureId
          }
          editRoomPicture(pa)
          .then((res)=>{
            _this.msgSuccess("编辑成功");
            _this.visible = false;
            _this.queryPicture();
          })
        }
      });
    },
    //替换图片
    beforeReplace(file){
      const obj = this.fileList[this.replaceIndex];
      const fd = new FormData()
      fd.append('roomPicture', file);
      fd.append("pictureId", obj.pictureId);

      const headers = {
        "Content-Type": "multipart/form-data",
        "Authorization": `Bearer ${getToken()}`
      };

      axios({
        method: "post",
        url: `${process.env.VUE_APP_BASE_API}/hotel/roomPicture/replace`,
        data: fd,
        headers
      })
      .then((res)=>{
        const {data} = res;
        this.fileList.splice(this.replaceIndex, 1, data);
        this.queryPicture();
      });
    },
    //获取图片完整路径
    getFileUrl(url){
      return `http://139.196.112.8/prod-api${url}`
    },
    //排序
    handleDragEnd(e){
      const arr = [];
      this.fileList.forEach(itm => {
        arr.push(itm.pictureId);
      });
      orderRoomPicture({pictureIds: arr.join(",")})
      .then((res)=>{
        this.msgSuccess("排序成功");
      });
    }
  }
};
</script>
<style lang='scss' scoped>
.container{
  padding: 20px;
  .img-wrap{
    display: flex;
    flex-wrap: wrap;
  }
  .img-drag-wrap{
    display: flex;
    flex-wrap: wrap;
  }
  .img-item{
    width: 226px;
    height: 190px;
    border: 1px solid #e4e4e4;
    margin-right: 20px;
    margin-bottom: 20px;
    position: relative;
    &.disabled{
      background: #eeeeee;
      opacity: 0.7;
    }
    &:hover{
      border-color: #dda748;
      .icon{
        display: block;
      }
    }
    .el-image{
      width: 100%;
      height: 160px;
      img{
        width: 100%;
        height: 100%;
      }
    }
    .name{
      height: 30px;
      text-align: center;
      line-height: 30px;
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
  }
  .avatar-uploader{
    width: 226px;
    height: 190px;
    background: #eeeeee;
    line-height: 190px;
    text-align: center;
    font-size: 100px;
    color: #ffffff;
    & /deep/ .el-upload{
      width: 100%;
    }
  }
  .replace-uploader{
    display: none;
  }
  .pic-alert{
    .btn-form-item{
      text-align: center;
    }
  }
}
</style>
