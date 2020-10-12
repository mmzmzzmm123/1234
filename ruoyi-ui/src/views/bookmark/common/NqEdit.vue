<template>
  <div class="home">

    <div CLASS="nqtitle">
      <div class="mdui-textfield mdui-textfield-floating-label">
        <label class="mdui-textfield-label">文章标题</label>
        <input class="mdui-textfield-input" type="text" required/>
        <div class="mdui-textfield-error">文章标题不能为空</div>
      </div>
    </div>
<!--    <div class="mian" v-html="msg">-->


<!--    </div>-->

    <div>
    <tinymce
      ref="editor"
      v-model="queryParams.UeditorContent"
      :disabled="disabled"
      @onClick="onClick"
    />
    </div>
    <div STYLE="margin-top: 10px;margin-bottom: 10px">
      <button class="mdui-btn mdui-btn-raised mdui-color-indigo-300" @click="clear">清空内容</button>
      <button class="mdui-btn mdui-btn-raised mdui-color-indigo-300" @click="clear">发布</button>
      <button class="mdui-btn mdui-ripple mdui-btn-raised " @click="disabled = true">禁用</button>
      <button class="mdui-btn mdui-ripple mdui-btn-raised " @click="disabled = false">启用</button>
    </div>


  </div>
</template>
<script>
  import tinymce from '../../../components/TinyMCE'
  import {
    userUpdateNote,userGetNoteInfo
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
    data(){
      return{
        postForm: Object.assign({}, defaultForm),
        msg: "<span>请尽情创作吧!</span>",
        disabled: false,
        queryParams:{
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
          tiymceUeditor:undefined,
          UeditorContent:undefined,
        },
      }
    },
    created() {
      var that = this;
      var Ueditor = that.$route.query.Ueditor;
      var noteId = that.$route.query.noteId;
      this.queryParams.tiymceUeditor=Ueditor;
      this.queryParams.noteId=noteId;

      //查看对应的文章 信息和标题等等
      this.getNoteList();

    },
    methods: {
      /** 实时更新文章的信息 */
      UpdateNote(){
        userUpdateNote(this.queryParams).then(response =>{
            console.log("已保存:"+Date.now())
        });
      },
      /** 查询便签管理列表 */
      getNoteList() {
        userGetNoteInfo(this.queryParams.noteId).then(response => {
          this.queryParams = response.rows;
        });
      },
      //鼠标单击的事件
      onClick (e, editor) {
        console.log('Element clicked')
        console.log(e)
        console.log(editor)
      },
      //清空内容
      clear () {
        this.$refs.editor.clear()
      }
    }
  }
</script>
<style scoped>
  .title{
    display: flex;
  }
  .title div:first-child{
    margin-right: 20px;
  }
  .title div{
    width: 50%;

  }
  .mian{
    height: 300px;
    overflow-y: auto;
    width: 100%;
    border: 1px solid #cacaca;
    background-color: #ebebeb;
    /*text-indent: 10px;*/
    padding: 10px;
    margin-bottom: 10px;
    opacity: 0.9;
  }
  .home{
    height: 800px;
    overflow: scroll;
  }

</style>
