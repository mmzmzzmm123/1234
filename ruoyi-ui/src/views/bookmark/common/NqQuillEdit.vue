
<template>
  <div class="edit_container">
    <quill-editor
      v-model="content"
      ref="myQuillEditor"
      :options="editorOption"
      @blur="onEditorBlur($event)" @focus="onEditorFocus($event)"
      @change="onEditorChange($event)">
    </quill-editor>



    <svg-icon name="logo" class="menu-icon" color="#fff" width="16" height="16"></svg-icon>
<!--    <IconSelect></IconSelect>-->
  </div>
</template>



<script>


  import quillEditor from "../../../components/Editor";
  import SvgIcon from "../../../components/SvgIcon";
  import IconSelect from "../../../components/IconSelect";


  export default {
    components: {
      quillEditor,SvgIcon,IconSelect
    },
    data() {
      return {
        content: `<p></p><p><br></p><ol><li><strong><em>Or drag/paste an image here.</em></strong></li><li><strong><em>rerew</em></strong></li><li><strong><em>rtrete</em></strong></li><li><strong><em>tytrytr</em></strong></li><li><strong><em>uytu</em></strong></li></ol>`,
        editorOption: {}
      }
    },
    methods: {
      onEditorReady(quillEditor) { // 准备编辑器

      },
      onEditorBlur(){}, // 失去焦点事件
      onEditorFocus(){}, // 获得焦点事件
      onEditorChange(){}, // 内容改变事件
    },
    computed: {
      editor() {
        return this.$refs.myQuillEditor.quill;
      },
    }
  }
</script>
