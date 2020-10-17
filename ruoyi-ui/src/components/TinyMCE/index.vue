<template>
  <div class="tinymce-box">
    <editor v-model="myValue"
            :init="init"
            :disabled="disabled"
            @onClick="onClick">
    </editor>
  </div>
</template>

<script>
  import tinymce from 'tinymce/tinymce' //tinymce默认hidden，不引入不显示
  import Editor from '@tinymce/tinymce-vue'
  import 'tinymce/themes/silver'
  // 编辑器插件plugins
  // 更多插件参考：https://www.tiny.cloud/docs/plugins/
  import 'tinymce/plugins/image'// 插入上传图片插件
  import 'tinymce/plugins/media'// 插入视频插件
  import 'tinymce/plugins/table'// 插入表格插件
  import 'tinymce/plugins/lists'// 列表插件
  import 'tinymce/plugins/wordcount'// 字数统计插件


  // import "tinymce/themes/silver";
  // import "tinymce/plugins/paste";
  // import "tinymce/plugins/image";
  // import "tinymce/plugins/link";
  // import "tinymce/plugins/code";
  // import "tinymce/plugins/table";
  // import "tinymce/plugins/lists";
  // import "tinymce/plugins/wordcount";



  import toolbar from './toolbar'
  import plugins from './plugins'




  export default {
    components:{
      Editor
    },
    name:'tinymce',
    props: {
      value: {
        type: String,
        default: ''
      },
      disabled: {
        type: Boolean,
        default: false
      },
      plugins: {
        type: [String, Array],
        default: ' lists image media table wordcount hr '
      },
      toolbar: {
        type: Array,
        required: false,
        default() {
          return []
        }
      },

    },
    data(){
      return{
        init: {
          language_url: '/tinymce/langs/zh_CN.js',
          language: 'zh_CN',
          skin_url: '/tinymce/skins/ui/oxide',
          // skin_url: 'tinymce/skins/ui/oxide-dark',//暗色系
          height: 600,
          menubar: 'file edit insert view format table',//菜单  //
          // 配置每个菜单栏的子菜单项（如下是默认配置）
          menu: {
            file: {title: 'File', items: 'newdocument'},
            edit: {title: 'Edit', items: 'undo redo | cut copy paste pastetext | selectall'},
            insert: {title: 'Insert', items: 'link media | template hr'},
            view: {title: 'View', items: 'visualaid'},
            format: {title: 'Format', items: 'bold italic underline strikethrough superscript subscript | formats | removeformat'},
            table: {title: 'Table', items: 'inserttable tableprops deletetable | cell row column'},
          },
          menubar: false,//编辑器最上面菜单关闭
          toolbar: this.toolbar.length > 0 ? this.toolbar : toolbar,
          plugins: this.plugins,
          plugins: plugins,
          branding: false,

          // 此处为图片上传处理函数，这个直接用了base64的图片形式上传图片，
          // 如需ajax上传可参考https://www.tiny.cloud/docs/configure/file-image-upload/#images_upload_handler
          images_upload_handler: (blobInfo, success, failure) => {
            const img = 'data:image/jpeg;base64,' + blobInfo.base64()
            success(img)
          }
        },
        myValue: this.value
      }
    },
    mounted () {
      tinymce.init({})
    },
    methods: {
      // 添加相关的事件，可用的事件参照文档=> https://github.com/tinymce/tinymce-vue => All available events
      // 需要什么事件可以自己增加
      onClick (e) {
        this.$emit('onClick', e, tinymce)
      },
      // 可以添加一些自己的自定义事件，如清空内容
      clear () {
        this.myValue = ''
      }
    },
    watch: {
      value (newValue) {
        this.myValue = newValue
      },
      myValue (newValue) {
        this.$emit('input', newValue)
      }
    }
  }

</script>
<style scoped>

</style>
