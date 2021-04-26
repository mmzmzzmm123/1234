<template>
  <div class="app-container ">
    <el-row :gutter="20" >
      <el-col :xs="24" :sm="{span: 16, push: 4}" :md="{span: 14, push: 5}"  :xl="{span: 10, push: 7}"  >
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>备份/导出</span>
          </div>
          <div class="exportList">
            <div class="exportList-main">
              <div class="exportList-main-text">
               <span class="listText"> 书签导出</span>
              </div>
              <div class="listText-button">

                <el-button  plain @click="handleExport" size="mini">书签导出</el-button>

              </div>
            </div>

            <div class="exportList-main">
              <div class="exportList-main-text">
                <span class="listText"> 自动备份</span>
              </div>
              <div class="listText-button" >
                <el-switch style="float: right" v-model="bookMarkBackup" ></el-switch>
              </div>
            </div>

            <div class="exportList-main">
              <div class="exportList-main-text">
                <span class="listText"> 备份方式</span>
              </div>
              <div class="listText-button">
                <el-button  plain @click="handleExport" size="mini" style="width: 80px">   添 加   </el-button>

              </div>
            </div>





          </div>
        </el-card>
      </el-col>

    </el-row>
  </div>
</template>
<script>
  import { exportBookmarkHTML } from "@/api/bookmark/bookmark";
    export default {
        name: 'areaTree',
        components: {},

        data: function () {
            return {
              bookMarkBackup:true
            }
        },
        methods: {
          /** 导出按钮操作 */
          handleExport() {
            this.$confirm('是否确认导出所有书签数据项?', "警告", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning"
            }).then(function() {
              return exportBookmarkHTML();
            }).then(res => {
              var oDate = new Date(); //实例一个时间对象；
              oDate.getFullYear();   //获取系统的年；
              oDate.getMonth()+1;   //获取系统月份，由于月份是从0开始计算，所以要加1
              oDate.getDate(); // 获取系统日，
              oDate.getHours(); //获取系统时，
              oDate.getMinutes(); //分
              oDate.getSeconds(); //秒
              var time = oDate.getFullYear()+"年"+oDate.getMonth()+1+"月"+oDate.getDate() +"日 " +oDate.getHours()+"时"+oDate.getMinutes()+"分"+oDate.getSeconds()+"秒";
              var name = "cqy_"+time;
              const blob = new Blob([res], { type: 'text/html' }) // 构造一个blob对象来处理数据
              const fileName = name + '.html' // 导出文件名
              const elink = document.createElement('a') // 创建a标签
              elink.download = fileName // a标签添加属性
              elink.style.display = 'none'
              elink.href = URL.createObjectURL(blob)
              document.body.appendChild(elink)
              elink.click() // 执行下载
              URL.revokeObjectURL(elink.href) // 释放URL 对象
              document.body.removeChild(elink) // 释放标签
            }).then(res =>{
              this.$message({
                showClose: true,
                message: '导出成功',
                type: 'success'
              });
            }).catch(function() {
              this.$message({
                showClose: true,
                message: '导出失败，系统错误!',
                type: 'error'
              });
            });
          }
        }
    }
</script>
<style>

  .exportList{
    display:flex;flex-direction:column;
  }
  .exportList-main{
    display:flex;height: 50px;width: 100%;
  }
  .exportList-main-text{
    width: 90%;display:flex;align-items:center;
  }
  .listText{
    color: black;font-weight: 400
  }
  .listText-button{
    width: 10%;display:flex;align-items:center;
    flex-direction:column-reverse;
  }

</style>
