<template>
  <div class="home" v-loading="loading" >

    <div class="nqtitle">
      <div class="mdui-textfield mdui-textfield-floating-label inputtop">
        <input class="mdui-textfield-input edittitle" v-model="queryParamsAndMg.title" type="text" required/>
      </div>
      <div class="common">
        <el-tag type="info" size="small" >{{queryParamsAndMg.updateTime==null?'2020-12-13 21:24:35':queryParamsAndMg.updateTime}}</el-tag>
        <el-link type="info" icon="el-icon-edit" class="editNote" @click="updateEdit">{{updateOpn}}</el-link>
        <el-link type="info"  icon="el-icon-delete" class="editNote delete">删除</el-link>
        <el-link type="info"  icon="el-icon-refresh-right" class="editNote">刷新</el-link>
        <el-link type="info"  icon="el-icon-connection" class="editNote">分享</el-link>
        <el-link type="info"  class="editNote">|</el-link>
        <el-link type="info"  icon="el-icon-lock" class="editNote">加密</el-link>
<!--        <el-link type="info"  icon="el-icon-unlock" class="editNote">未加密</el-link>-->
        <el-link type="info"  icon="el-icon-key" class="editNote">改密</el-link>
      </div>
    </div>
        <div  v-if="!showEditor" class="mian" v-html="queryParamsAndMg.ueditorContent">


        </div>

    <div v-if="showEditor" class="edit">
      <tinymce
        ref="editor"
        v-model="queryParamsAndMg.ueditorContent"
        :disabled="disabled"
        @onClick="onClick"
      />

    <div v-if="showEditor"  STYLE="margin-top: 10px;margin-bottom: 10px">
      <button class="mdui-btn mdui-btn-raised mdui-color-indigo-300" @click="clear">清空内容</button>
      <button class="mdui-btn mdui-btn-raised mdui-color-indigo-300" @click="UpdateNote">发布</button>
      <button class="mdui-btn mdui-ripple mdui-btn-raised " @click="disabled = true">禁用</button>
      <button class="mdui-btn mdui-ripple mdui-btn-raised " @click="disabled = false">启用</button>
    </div>
    </div>

  </div>
</template>
<script>
  import tinymce from '../../../components/TinyMCE'
  import {
    userUpdateNote, userGetNoteInfo
  } from "@/api/note/note";

  const defaultForm = {
    status: 'draft',
    title: '', // 文章题目
    content: '', // 文章内容
    content_short: '', // 文章摘要
    source_uri: '', // 文章外链
    image_uri: '', // 文章图片
    display_time: undefined, // 前台展示时间
    id: undefined,
    platforms: ['a-platform'],
    comment_disabled: false,
    importance: 0
  }

  export default {
    name: 'home',
    components: {
      tinymce
    },
    props: {
      ueditor: null,
      noteid: null,
      updateEditor:null
    },
    mounted() {
      this.showEditor=this.updateEditor;

    },
    updated() {

    },
    data() {
      return {
        postForm: Object.assign({}, defaultForm),
        msg: "<span>请尽情创作吧!</span>",
        disabled: false,
        queryParamsAndMg: {
          noteId: undefined,
          userId: undefined,
          title: undefined,
          description: undefined,
          menuId: undefined,
          background: undefined,
          noteCount: undefined,
          noteSort: undefined,
          isState: undefined,
          readProgress: undefined,
          isStar: undefined,
          isDelete: undefined,
          topFlag: undefined,
          isShare: undefined,
          isEncryption: undefined,
          createUserName: undefined,
          tiymceUeditor: undefined,
          ueditorContent: undefined,
        },
        showEditor:false,
        loading:true,
        updateOpn:'编辑',
      }
    },
    created() {

      this.getNoteById();
    },
    methods: {
      /** 实时更新文章的信息 */
      UpdateNote() {
        userUpdateNote(this.queryParamsAndMg).then(response => {
          if (response.code==200){
            console.log("已保存:" + Date.now())
            this.$message({
              message: '保存成功!',
              type: 'success'
            });
          }
        });
      },
      /** 查询便签管理列表 */
      getNoteById() {
        var that = this;

        var blueditor = that.ueditor != null && that.ueditor != '' && that.ueditor != undefined;
        var blnoteId = that.noteid != null && that.noteid != '' && that.noteid != undefined;
        if (blueditor) {
          this.queryParamsAndMg.tiymceUeditor = that.ueditor;
        }
        if (blnoteId) {
          this.queryParamsAndMg.noteId = that.noteid;
        }
        if (blueditor && blnoteId) {
          userGetNoteInfo(this.queryParamsAndMg.noteId).then(response => {
            that.queryParamsAndMg = response.data;
            that.loading=false;
          });
        }

      },
      //鼠标单击的事件
      onClick(e, editor) {
        console.log('Element clicked')
        console.log(e)
        console.log(editor)
      },
      //清空内容
      clear() {
        this.$refs.editor.clear()
      },
      updateEdit(){
        var that=this;

        that.showEditor=!that.showEditor;
        if (that.showEditor) {
          that.updateOpn='退出编辑';
        }else {
          that.updateOpn='编辑';
        }
      }
    }
  }
</script>
<style scoped>
  .title {
    display: flex;
  }

  .title div:first-child {
    margin-right: 20px;
  }

  .title div {
    width: 50%;

  }
  .edittitle{
    font-weight: 800;
    font-size: 22px;
  }

  .mian {
    height: auto;
    overflow-y: auto;
    width:auto;
    /*text-indent: 10px;*/
    padding: 10px;
    opacity: 0.9;
    margin: 15px;
    border: 1px solid #D4D4D4;
    padding-left: 20px;
    padding-right: 20px;
    background: #fafcff;
  }

  .home {
    height: 800px;
    overflow: scroll;
  }
  .edit{
    margin-left: 13px;

  }
  .editNote{
    margin-left: 10px;
    font-weight: 600;
    font-size: 16px;

  }
  .nqtitle{
    margin-left: 13px;
    margin-bottom: 10px;
    margin-right: 17px;
  }
  .inputtop{
    padding-top: 6px;
  }
  .common{
    display: flex;
    align-items: center;
    height: 22px;
  }
  .delete{
    color: #ff3b2f;
  }

</style>
