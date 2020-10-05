<template>
  <div class="home">



    <el-form-item style="margin-bottom: 40px;" prop="title">
      <MDinput v-model="postForm.title" :maxlength="100" name="name" required>
        Title
      </MDinput>
    </el-form-item>





    <tinymce
      ref="editor"
      v-model="msg"
      :disabled="disabled"
      @onClick="onClick"
    />
    <button @click="clear">清空内容</button>
    <button @click="disabled = true">禁用</button>
  </div>
</template>
<script>
  import tinymce from '../../../components/TinyMCE'

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
        msg: 'Welcome to Use Tinymce Editor',
        disabled: false
      }
    },
    methods: {
      // 鼠标单击的事件
      onClick (e, editor) {
        console.log('Element clicked')
        console.log(e)
        console.log(editor)
      },
      // 清空内容
      clear () {
        this.$refs.editor.clear()
      }
    }
  }
</script>
