<template>
    <div class="main" >
      <div class="aside-titleB"  @mouseenter="eidtTAGText=!eidtTAGText" @mouseleave="eidtTAGText=!eidtTAGText">
        <i @click="tagListShowCk" :class="tagListShow ? 'el-icon-caret-bottom aside-titleB_childi_one':'el-icon-caret-right aside-titleB_childi_one'"   ></i>
        <i class="el-icon-price-tag aside-titleB_childi_two"></i>
        <span >标签管理</span>
        <div style="margin-left: 40%" v-show="eidtTAGText">
          <i class="el-icon-search" style="font-size: 19px;margin-left: 5px;margin-top: 7px" @click="searchBkTagCk"></i>
          <i class="el-icon-folder-add" style="font-size: 19px;margin-left: 5px;margin-top: 7px" @click="addBkTagCk"></i>
        </div>
      </div>



      <div style="display: flex;justify-items: center;align-items: center">
        <el-input
          v-if="!addBkTAG"
          placeholder="输入书签名字"
          v-model="input4"
          size="mini"
          style="width: 80%;margin-left: 5%"
        >
          <i slot="prefix" class="el-input__icon el-icon-circle-plus-outline"></i>
        </el-input>
        <i v-if="!addBkTAG" @click="addBkTagCk" class="el-icon-close" style="font-size: 25px;margin-left: 5px;margin-bottom: 2px"></i>
      </div>

      <div style="display: flex;justify-items: center;align-items: center">
        <el-input
          v-if="!searchBkTAG"
          placeholder="搜索书签"
          v-model="input4"
          size="mini"
          style="width: 80%;margin-left: 5%"
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
      <div v-if=" !(tagList == undefined ||tagList == null || tagList.length <= 0)" class="aside-title name transition-box" >加载更多</div>

      <!-- 无标签 -->
        <div v-if=" tagList == undefined ||tagList == null || tagList.length <= 0" class="aside-title name transition-box" >暂无标签</div>
        </div>
      </el-collapse-transition>







    </div>
</template>
<script>
  import {listByUser,listByUserLike} from "@/api/bookmark/tag";

    export default {
        name: 'areaTree',
        components: {},

        data: function () {
            return {
              msg:'暂无标签',
              tagList:[],
              tagParams: {
                pageNum: 1,
                pageSize: 8
              },

              addBkTAG:true,//添加书TAG
              searchBkTAG:true,//搜索TAG
              tagListShow:false,//TAGlist
              eidtTAGText:false,//我的TAG
            }
        },
      created(){
          this.listByUser();
      },
        methods: {
          /** 查询书签 */
          listByUser: function () {
            listByUser(this.tagParams).then(response => {
                  if (response.code === 200) {
                    this.tagList=response.rows;
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
