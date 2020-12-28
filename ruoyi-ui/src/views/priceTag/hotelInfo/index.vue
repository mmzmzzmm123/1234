<!--
 * @Author: pengyu
 * @Date: 2020-12-07 22:33:08
 * @LastEditTime: 2020-12-28 22:57:58
 * @LastEditors: Please set LastEditors
 * @Description: 酒店信息
 * @FilePath: \RuoYi-Vue\ruoyi-ui\src\views\priceTag\hotelInfo.vue
-->
<template>
  <div class='container'>
    <el-form ref="form" :model="form" label-width="80px" :rules="rules">
      <el-form-item label="酒店名称">
        <el-input v-model="form.hotelName" maxLength="50"></el-input>
        <div class="tips">酒店名称或简称，也可以上传带酒店名称的LOGO</div>
      </el-form-item>
      <el-form-item label="LOGO">
        <el-upload
          class="upload-logo"
          action="123"
          :show-file-list="false"
          accept=".png,.jpg,.jpeg"
          name="logofile"
          :before-upload="handleLogoBefore"
          :file-list="logoFileList">
          <el-button size="small" type="primary" :disabled="!!form.logoPath">选择文件</el-button>
          <div slot="tip" class="el-upload__tip">要求：尺寸800*120，小于2M，背景透明，建议使用PNG格式</div>
        </el-upload>
        <div class="logo-img-wrap">
          <div class="logo-img-item" v-if="form.logoPath">
            <el-image
              fit="fit"
              class="logo-img"
              :src="getFileUrl(form.logoPath)"
              :preview-src-list="[getFileUrl(form.logoPath)]"
            ></el-image>
            <el-icon class="el-icon-error" @click.native.stop="deleteLogo"></el-icon>
          </div>
        </div>
      </el-form-item>
      <!-- <el-form-item label="客户编号">
        <el-input v-model="form.name" maxLength="50"></el-input>
        <div class="tips">酒店PMS客户标识号，用于标识和同步房态</div>
      </el-form-item> -->
      <el-form-item label="所在城市">
        <el-input v-model="form.name" maxLength="50"></el-input>
        <div class="tips">酒店所在城市</div>
      </el-form-item>
      <el-form-item label="二维码">
        <el-upload
          class="upload-code"
          action="456"
          :show-file-list="false"
          accept=".png,.jpg,.jpeg"
          :before-upload="handleCodeBefore"
          :file-list="codeFileList"
        >
          <el-button size="small" type="primary" :disabled="!!form.qrCodePath">选择文件</el-button>
          <div slot="tip" class="el-upload__tip">要求：最大尺寸800*800，小于2M，不要使用透明背景</div>
        </el-upload>
        <div class="logo-img-wrap">
          <div class="logo-img-item" v-if="form.qrCodePath">
            <el-image
              fit="fit"
              class="logo-img"
              :src="getFileUrl(form.qrCodePath)"
              :preview-src-list="[getFileUrl(form.qrCodePath)]"
            ></el-image>
            <el-icon class="el-icon-error" @click.native.stop="deleteCode"></el-icon>
          </div>
        </div>
      </el-form-item>
      <el-form-item label="公告信息">
        <el-input type="textarea" v-model="form.notice" maxLength="50"></el-input>
        <div class="tips">酒店公告信息</div>
      </el-form-item>
      <el-form-item label="订房电话">
        <el-input v-model="form.reservationPhone" maxLength="50"></el-input>
        <div class="tips">酒店订房电话</div>
      </el-form-item>
      <el-form-item label="服务项目">
        <div class="tag-wrap">
          <serveTag
            v-for="(itm,idx) in tagList"
            :key="idx"
            :value="itm"
            @changeState="handleItemClick"
          ></serveTag>
        </div>
      </el-form-item>
      <el-form-item style="text-align:center;">
        <el-button type="" size="medium" class="submit-btn" @click="submit">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import serveTag from "../components/serveTag";
import { getHotelInfo, saveHotelInfo, uploadLogo, downloadFile, deleteLogo, deleteQrCode } from "@/api/priceTag";
import axios from "axios"
import { compile } from 'path-to-regexp';
import { getToken } from '@/utils/auth'

export default {
  name: 'HotelInfo',
  components: { serveTag },
  data(){
    return {
      form: {
        hotelName:"", //酒店名称
        logoPath:"", //logo
        cityId: "", //城市
        qrCodePath: "", //二维码
        notice: "", //公告信息
        reservationPhone: "", //预定电话
        serviceItems: "" //服务项目
      },
      logoFileList: [],
      codeFileList: [],
      tagList: [
        {icon: "icon-wifi", name: "WIFI", id: "1", selected: true},
        {icon: "icon-jiesongjifuwu2", name: "接送服务", id: "2", selected: false},
        {icon: "icon-xinyongqia", name: "信用卡", id: "3", selected: false},
        {icon: "icon-jianshenfang2", name: "健身房", id: "4", selected: false},
        {icon: "icon-yule", name: "娱乐设施", id: "5", selected: false},
        {icon: "icon-hanglijicun", name: "行李寄存", id: "6", selected: false},
        {icon: "icon-canyin1", name: "餐饮", id: "7", selected: false},
        {icon: "icon-Spacentre", name: "SPA", id: "8", selected: false},
        {icon: "icon-huiyishi1", name: "会议室", id: "9", selected: false},
        {icon: "icon-tingchechang", name: "停车场", id: "10", selected: false},
        {icon: "icon-24xiaoshiqiantai", name: "24小时前台", id: "11", selected: false},
        {icon: "icon-daoyou", name: "导游", id: "12", selected: false},
        {icon: "icon-xiyan", name: "吸烟", id: "13", selected: false},
        {icon: "icon-baoxiangui", name: "保险柜", id: "14", selected: false},
        {icon: "icon-nuanqi", name: "暖气", id: "15", selected: false},
        {icon: "icon-xishuyongpin", name: "洗漱用品", id: "16", selected: false},
        {icon: "icon-dianshi", name: "电视", id: "17", selected: false},
        {icon: "icon-chongwu", name: "宠物", id: "18", selected: false},
        {icon: "icon-touyingyi", name: "投影仪", id: "19", selected: false},
        {icon: "icon-ic_zhen", name: "针线", id: "20", selected: false},
        {icon: "icon-kuaidi", name: "快递", id: "21", selected: false},
        {icon: "icon-fuzhuang-dianyundou", name: "电熨斗", id: "22", selected: false},
        {icon: "icon-dianyingyuan", name: "电影院", id: "23", selected: false},
        {icon: "icon-bingxiang-xian", name: "冰箱", id: "24", selected: false},
        {icon: "icon-ganxi", name: "干洗", id: "25", selected: false},
        {icon: "icon-jiuba", name: "酒吧", id: "26", selected: false},
        {icon: "icon-chongdianzhuang", name: "充电桩", id: "27", selected: false},
        {icon: "icon-chuifengji", name: "吹风机", id: "28", selected: false},
        {icon: "icon-peitaosheshixiaotubiao_yugang", name: "浴缸", id: "29", selected: false},
        {icon: "icon-peitaosheshixiaotubiao_reshuihu", name: "热水壶", id: "30", selected: false},
        {icon: "icon-airCondition", name: "空调", id: "31", selected: false},
        {icon: "icon-icon_xinyong_xianxing_jijin-140", name: "旅游", id: "32", selected: false},
        {icon: "icon-jiaoxingfuwu", name: "叫醒服务", id: "33", selected: false},
        {icon: "icon-zhuanzhihangliyuan", name: "行李员", id: "34", selected: false}
      ],
      rules: {
        hotelName: [{required: true, message:"酒店名称不能为空", trigger: "blur"}],
      }
    };
  },
  mounted(){
    this.init();
  },
  methods:{
    //查询赋值
    async init(){
      try {
        const rsp = await getHotelInfo();
        const {data} = rsp;
        const _this = this;
        Object.keys(this.form).forEach(itm=>{
          _this.$set(this.form, itm, data[itm]);
        });
        const services = data.serviceItems ? data.serviceItems.split(",") : [];
        this.tagList.forEach(itm=>{
          itm.selected = services.includes(itm.id);
        });

        // downloadFile({delete: false, fileName: data.logoPath})
      } catch (error) {
        this.msgError(error);
      }
    },
    //logo上传前预处理
    handleLogoBefore(file){
      const {size} = file;
      const _this = this;
      if(size / 1024 / 1204 > 2){
        this.msgError("logo图片大于2M");
        return false;
      }
      const fd = new FormData()
      fd.append('logofile', file);

      const headers = {
        "Content-Type": "multipart/form-data",
        "Authorization": `Bearer ${getToken()}`
      };

      axios({
        method: "post",
        url: `${process.env.VUE_APP_BASE_API}/hotel/hotelInfo/logo`,
        data: fd,
        headers
      })
      .then((res)=>{
        const {data} = res.data;
        _this.$set(_this.form, "logoPath", data.logoPath);
      });

      // uploadLogo(fd, {headers: {"Content-Type": "multipart/form-data"}})
      // .then((res)=>{
      //   console.log(res);
      // });
    },
    //二位阿玛上传前预处理
    handleCodeBefore(file){
      const {size} = file;
      const _this = this;
      if(size / 1024 / 1204 > 2){
        this.msgError("二维码图片大于2M");
        return false;
      }

      const fd = new FormData()
      fd.append('qrCode', file);

      const headers = {
        "Content-Type": "multipart/form-data",
        "Authorization": `Bearer ${getToken()}`
      };

      axios({
        method: "post",
        url: `${process.env.VUE_APP_BASE_API}/hotel/hotelInfo/qrCode`,
        data: fd,
        headers
      })
      .then((res)=>{
        const {data} = res.data;
        _this.$set(_this.form, "qrCodePath", data.qrCodePath);
      });
    },
    //tag服务点击
    handleItemClick({id, flg}){
      const index = this.tagList.findIndex((itm)=>itm.id===id);
      this.tagList[index].selected = flg;
    },
    //提交保存酒店信息
    submit(){
      const _this = this;
      this.$refs.form.validate((valid)=>{
        if(valid){
          const tags = [];
          _this.tagList.forEach(itm=>{
            if(itm.selected){
              tags.push(itm.id);
            }
          });
          _this.form.serviceItems = tags.join(",");
          saveHotelInfo(_this.form)
          .then((res)=>{
            _this.msgSuccess("提交成功");
            _this.init();
          })
        }
      });
    },
    //删除logo
    deleteLogo(){
      const _this = this;
      deleteLogo()
      .then((res)=>{
        _this.msgSuccess("删除成功");
        _this.$set(_this.form, "logoPath", "");
      })
    },
    //删除二维码
    deleteCode(){
      const _this = this;
      deleteQrCode()
      .then((res)=>{
         _this.msgSuccess("删除成功");
        _this.$set(_this.form, "qrCodePath", "");
      })
    },
    //获取图片完整路径
    getFileUrl(url){
      return `http://139.196.112.8/prod-api${url}`
    }
  }
};
</script>
<style lang='scss' scoped>
.container{
  padding: 30px 0;
  .el-form{
    width: 50%;
    margin: 0 auto;
    .el-form-item{
      margin-bottom: 10px;
    }
  }
  .tips{
    font-size: 12px;
    color: #606266;
  }
  .el-upload__tip{
    margin-top: 0px;
  }
  .tag-wrap{
    display: flex;
    flex-wrap: wrap;
    .tag-item{
      margin-right: 10px;
      margin-bottom: 10px;
    }
  }
  .submit-btn{
    background: #DDA748;
    color: #fff;
    border-color:#DDA748;
    width: 160px;
  }
  .logo-img-item{
    width: 45px;
    height: 45px;
    border-radius: 3px;
    border: 1px solid #eeeeee;
    position: relative;
    cursor: pointer;
    &:hover{
      .el-icon-error{
        display: block;
      }
    }
    .logo-img{
      width: 100%;
      height: 100%;
    }
    .el-icon-error{
      position: absolute;
      top: -5px;
      right: -5px;
      color: #f00;
      display: none;
    }
  }

}
</style>
