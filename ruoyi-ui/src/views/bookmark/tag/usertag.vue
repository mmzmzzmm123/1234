<template>
    <div class="main" >
      <div class="aside-titleB" @click="tagListShowCk"  @mouseenter="eidtTAGText=!eidtTAGText" @mouseleave="eidtTAGText=!eidtTAGText">
        <i  :class="tagListShow ? 'el-icon-caret-bottom aside-titleB_childi_one':'el-icon-caret-right aside-titleB_childi_one'"   ></i>
        <i class="el-icon-price-tag aside-titleB_childi_two"></i>
        <span >标签管理</span>
        <div style="margin-left: 40%" v-show="eidtTAGText">
          <i class="el-icon-search title-name" @click.stop="searchBkTagCk"></i>
          <i class="el-icon-folder-add title-name"  @click.stop="addBkTagCk"></i>
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
      <div v-if=" !(tagList == undefined ||tagList == null || tagList.length <= 0)" v-for="item in tagList" @mouseover="enter(item.id)" @mouseleave="leave()" >
        <div class="aside-title name transition-box" id="item.id"  >

         <i class="el-icon-collection-tag" style="font-size: 15px"/> {{item.name}}



          <i v-show="seen&&item.id == current"  class="el-icon-delete tag_coomon" @click="deleteTagOpen(item.id)"></i>
          <i v-show="seen&&item.id == current"  class="el-icon-edit tag_coomon_eidt" @click="updateTagOpen(item.id,item.name)"></i>


<!--          <el-tag type="info" size="mini">{{item.name}}</el-tag>-->
        </div>
      </div>
      <div v-if=" tagList != undefined && tagList != null && total > 8 " class="aside-title name transition-box" @click="getListTag()">加载更多(共{{total}}条)</div>

      <!-- 无标签 -->
        <div v-if=" tagList == undefined ||tagList == null || tagList.length <= 0" class="aside-title name transition-box" >

          暂无标签
          <span v-if ="tagNameCopy != undefined && tagNameCopy != null && tagNameCopy!=''"> [{{tagNameCopy}}]</span>

        </div>
        </div>
      </el-collapse-transition>




      <el-dialog
        title="编辑"
        :visible.sync="dialogVisible"
        width="30%"
        >
        <el-input v-model="newName" placeholder="请输入新的标签名称"></el-input>
        <span slot="footer" class="dialog-footer">
    <el-button class="butWidth" size="small" type="primary" @click="editByUser()">确 定</el-button>
    <el-button class="butWidth" size="small" @click="dialogVisible = false">取 消</el-button>
  </span>
      </el-dialog>





    </div>
</template>
<script>
  import {listByUser,listByUserLike,addTagByUser,deleteTag,editByUser} from "@/api/bookmark/tag";

    export default {
        name: 'areaTree',
        components: {},

        data: function () {
            return {
              dialogVisible: false,
              deleteTag:false,
              msg:'暂无标签',
              tagList:[],
              total:0,
              tagParams: {
                pageNum: 1,
                pageSize: 8,
                name:undefined,
                id:undefined
              },
              newName:'',//标签名称
              addBkTAG:true,//添加书TAG
              searchBkTAG:true,//搜索TAG
              tagListShow:false,//TAGlist
              eidtTAGText:false,//我的TAG
              tagName:undefined,//新增书签 - 搜索的输入框
              tagNameCopy:'',//用于提示
              seen:false,//用于展示触碰显示操作按钮
              current:0,//用于展示触碰显示操作按钮
            }
        },
      created(){
        var that = this;
        that.listByUsers();
      },
        methods: {

          updateTagOpen(id,name){
            var that = this;
            that.dialogVisible = true;
            that.newName = name;
            that.tagParams.id = id;
          },
          /** 修改书签*/
          editByUser(){
            var that = this;
            that.tagParams.name = that.newName;
            editByUser(this.tagParams).then(response => {
              if (response.code === 200) {
                that.listByUsers();
                that.msgSuccess("修改成功");
              }else{
                that.msgError("修改失败");
              }
            });
            that.dialogVisible = false
          },

          /** 删除书签*/
          deleteTagOpen(id){
            this.$confirm('是否确认删除此条书签数据项?', "警告", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning"
            }).then(() => {
              deleteTag(id).then(response => {
                if (response.code === 200) {
                  this.listByUsers();
                  this.msgSuccess("删除成功");
                }else{
                  this.msgError("删除失败");
                }
              });


            }).catch(function () {
              // 取消删除
            });

          },
          handleClose(done) {
            this.$confirm('确认关闭？')
              .then(_ => {
                done();
              })
              .catch(_ => {});
          },

          // 统一的表单重置
          reset() {
            this.tagParams = {
              pageNum: 1,
              pageSize: 8,
              name: undefined,
              id: undefined
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
            that.reset();
            listByUserLike(that.tagParams).then(response => {
                  if (response.code === 200) {
                    that.tagList=response.rows;
                    that.total = response.total;
                  }
                });
          },
          /**加载更多*/
          getListTag(){
            var that = this;
            var that = this;
            //判断是否加载了所有数据
            var i = that.tagParams.pageNum + 1;
            var listcount = Math.ceil(that.total / 8);


            if (i > listcount) {
              this.msgSuccess("已加载全部");
              return ;
            }
            that.tagParams.pageNum = i;
            //初始化
            listByUserLike(that.tagParams).then(response => {
              if (response.code === 200) {
                that.tagList=that.tagList.concat(response.rows)
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
          enter(index){
            this.seen = true;
            this.current = index;
          },
          leave(){
            this.seen = false;
            this.current = null;
          },

        }
    }
</script>
<style scoped>
  .title-name{
    font-size: 16px;
    margin-left: 5px;
    margin-top: 9px
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
  .tag_coomon{
    float: right;
    margin-right: 15px;
    margin-top: 9px;
    font-size: 16px;
  }
  .tag_coomon_eidt{
    float: right;
    margin-right: 5px;
    margin-top: 9px;
    font-size: 16px;
  }
  .butWidth{
    width: 80%;
    margin-left: 10% ;
  }

</style>
