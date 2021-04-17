<template lang="html">
  <div class="editor" style="height: 350px; overflow: auto">
    <!--<div ref="toolbar" class="toolbar">
    </div>-->
    <div ref="editor" class="text">
    </div>
  </div>
</template>

<script>
  import E from 'wangeditor'
  export default {
    name: 'editoritem',
    data() {
      return {
        // uploadPath,
        editor: null,
        info_: null
      }
    },
    model: {
      prop: 'value',
      event: 'change'
    },
    props: {
      value: {
        type: String,
        default: ''
      },
      isClear: {
        type: Boolean,
        default: false
      },
      disable: {
          type: Boolean,
          default: false
      }
    },
    watch: {
      isClear(val) {
        // 触发清除文本域内容
        if (val) {
          this.editor.txt.clear()
          this.info_ = null
        }
      },
      value: function(value) {
        if (value !== this.editor.txt.html()) {
          this.editor.txt.html(this.value == null ? "" : this.value)
        }
      }
      //value为编辑框输入的内容，这里我监听了一下值，当父组件调用得时候，如果给value赋值了，子组件将会显示父组件赋给的值
    },
    mounted() {
      this.seteditor()
      this.editor.txt.html(this.value)
    },
    methods: {
      seteditor() {
        // http://192.168.2.125:8080/admin/storage/create
        //this.editor = new E(this.$refs.toolbar, this.$refs.editor);
        this.editor = new E(this.$refs.editor);
        
        // 配置菜单
        this.editor.config.menus = [
          //'head', // 标题
          //'bold', // 粗体
          //'fontSize', // 字号
          //'fontName', // 字体
          //'italic', // 斜体
          //'underline', // 下划线
          //'strikeThrough', // 删除线
          //'foreColor', // 文字颜色
          //'backColor', // 背景颜色
          //'link', // 插入链接
          //'list', // 列表
          'justify', // 对齐方式
          //'quote', // 引用
          //'emoticon', // 表情
          //'image', // 插入图片
          //'table', // 表格
          //'video', // 插入视频
          //'code', // 插入代码
          'undo', // 撤销
          //'redo', // 重复
          //'fullScreen' // 全屏
        ]
        this.editor.config.onchange = (html) => {
          this.info_ = html // 绑定当前逐渐地值
          this.$emit('change', this.info_) // 将内容同步到父组件中
        }
        this.editor.config.showFullScreen = true
        // 创建富文本编辑器
        this.editor.create();
        
        if(this.disable){
            this.editor.disable();
        }
      }
    }
  }
</script>

<style lang="css">
  .editor {
    width: 100%;
    margin: 0 auto;
    position: relative;
    z-index: 0;
  }
  .toolbar {
    border: 1px solid #ccc;
  }
  .text {
    border: 1px solid #ccc;
    min-height: 300px;
  }
</style>