<template>
    <div class="main" >
      <div class="aside-titleB"  @mouseenter="eidtTAGText=!eidtTAGText" @mouseleave="eidtTAGText=!eidtTAGText">
        <i @click="tagListShowCk" :class="tagListShow ? 'el-icon-caret-bottom aside-titleB_childi_one':'el-icon-caret-right aside-titleB_childi_one'"   ></i>
        <i class="el-icon-price-tag aside-titleB_childi_two"></i>
        <span >标签管理</span>
        <div style="margin-left: 40%" v-show="eidtTAGText">
          <i class="el-icon-search title-name" @click="searchBkTagCk"></i>
          <i class="el-icon-folder-add title-name"  @click="addBkTagCk"></i>
        </div>
      </div>



      <div style="display: flex;justify-items: center;align-items: center">
        <el-input
          v-if="!addBkTAG"
          placeholder="新增书签的名字"
          v-model="tagName"
          size="mini"
          style="width: 80%;margin-left: 5%"
          @keyup.enter.native ="addTag"
        >
          <i slot="prefix" class="el-input__icon el-icon-circle-plus-outline"></i>
        </el-input>
        <i v-if="!addBkTAG" @click="addBkTagCk" class="el-icon-close" style="font-size: 25px;margin-left: 5px;margin-bottom: 2px"></i>
      </div>

      <div style="display: flex;justify-items: center;align-items: center">
        <el-input
          v-if="!searchBkTAG"
          placeholder="搜索书签"
          v-model="tagName"
          size="mini"
          style="width: 80%;margin-left: 5%"
          @keyup.enter.native ="search"
        >
          <i slot="prefix" class="el-icon-search" style="margin-left: 5px"></i>
        </el-input>
        <i v-if="!searchBkTAG" @click="searchBkTagCk" class="el-icon-close" style="font-size: 25px;margin-left: 5px;margin-bottom: 2px"></i>
      </div>


      <el-collapse-transition>
        <div v-if=" tagListShow">
      <!-- TAG标签 -->
      <div v-if=" !(tagList == undefined ||tagList == null || tagList.length <= 0)" v-for="item in tagList"  >
        <div class="aside-title name transition-box" id="item.id">
         <i class="el-icon-collection-tag" style="font-size: 15px"/> {{item.name}}
<!--          <el-tag type="info" size="mini">{{item.name}}</el-tag>-->
        </div>
      </div>
      <div v-if=" tagList != undefined && tagList != null && total > 8 " class="aside-title name transition-box" >加载更多</div>

      <!-- 无标签 -->
        <div v-if=" tagList == undefined ||tagList == null || tagList.length <= 0" class="aside-title name transition-box" >

          暂无标签
          <span v-if ="tagNameCopy != undefined && tagNameCopy != null && tagNameCopy!=''"> [{{tagNameCopy}}]</span>

        </div>
        </div>
      </el-collapse-transition>







    </div>
</template>
<script>
  import {listByUser,listByUserLike,addTagByUser} from "@/api/bookmark/tag";

    export default {
        name: 'areaTree',
        components: {},

        data: function () {
            return {
              msg:'暂无标签',
              tagList:[],
              total:0,
              tagParams: {
                pageNum: 1,
                pageSize: 8,
                name:undefined
              },

              addBkTAG:true,//添加书TAG
              searchBkTAG:true,//搜索TAG
              tagListShow:false,//TAGlist
              eidtTAGText:false,//我的TAG
              tagName:undefined,//新增书签 - 搜索的输入框
              tagNameCopy:''//用于提示
            }
        },
      created(){
        var that = this;
        that.listByUsers();
      },
        methods: {
          // 统一的表单重置
          reset() {
            this.form = {
              pageNum: 1,
              pageSize: 8,
              name: undefined,
            };
          },
          /**回车搜索**/
          search(){
            if (this.tagName == undefined || this.tagName == null ||this.tagName.trim() == ''){
              this.msgInfo("标签名称不能为空")
              return;
            }
            if (this.tagName.trim().length > 10){
              this.msgInfo("标签名称太长了")
              return;
            }
            this.reset();
            this.tagParams.name = this.tagName;
            console.log("回车搜索",this.tagParams);
            //初始化
            this.tagList = null;
            this.total = 0;
            listByUserLike(this.tagParams).then(response => {
              if (response.code === 200) {
                this.tagList=response.rows;
                this.total = response.total;
                if (this.total == 0){
                  this.tagNameCopy = this.tagName;
                }
              }
            });

          },
          /**回车新增标签**/
          addTag(){
            if (this.tagName == undefined || this.tagName == null ||this.tagName.trim() == ''){
              this.msgInfo("标签名称不能为空!")
              return;
            }
            if (this.tagName.trim().length > 10){
              this.msgInfo("标签名称太长了")
              return;
            }
            let tagParams = {
              name:this.tagName
            };

            addTagByUser(tagParams).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增标签【"+ this.tagName +"】成功");
                //新增成功后 重新获取
                this.reset();
                this.tagName = undefined;
                this.listByUsers();
              }else {
                this.msgError("新增标签【"+ this.tagName +"】失败,系统错误!");
              }
            });

          },

          /** 查询书签 */
          listByUsers() {
            var that = this;
            //初始化
            listByUser(that.tagParams).then(response => {
                  if (response.code === 200) {
                    that.tagList=response.rows;
                    that.total = response.total;
                  }
                });
          },

          /**添加书签目录**/
          addBkTagCk(){
            this.addBkTAG = this.addBkTAG?false:true;
            this.searchBkTAG = true;
            this.tagListShow = true;
          },
          /**搜索书签目录**/
          searchBkTagCk(){
            this.searchBkTAG = this.searchBkTAG?false:true;
            this.addBkTAG = true;
            this.tagListShow = true;
            if(this.searchBkTAG){
              console.log("关闭搜索")
              this.reset();
              this.tagName = undefined;
              this.listByUsers();
            }
          },
          /**搜索书签目录**/
          tagListShowCk(){
            this.tagListShow = this.tagListShow?false:true;
            this.addBkTAG = true;
            this.searchBkTAG = true;
          },


        }
    }
</script>
<style scoped>
  .title-name{
    font-size: 19px;margin-left: 5px;margin-top: 7px
  }
  .name{
    padding-left: 50px;
  }
  .aside-title:hover{
    background-color: #e8e8e8;
    color: #1c84c6;
  }
  .aside-titleB{
    display: flex;
    height: 32px;
    align-items: center;
    justify-items: center;
  }
  .aside-titleB:hover {
    background-color: #e8e8e8;
  }
  /**第一个元素**/
  .aside-titleB_childi_one{
    margin-left: 5px;
    font-size: 15px;
    margin-right: 11px;
    vertical-align: middle;
  }
  /**第二个元素**/
  .aside-titleB_childi_two{
    margin-left: -4px;
    font-size: 20px;
    margin-right: 11px;
    vertical-align: middle;
  }
  .aside-titleB span{
    font-size: 14px;
  }

</style>
