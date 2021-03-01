<template>
  <div class="fileupload">
    <uploader :options="options" ref="uploader" @com :file-status-text="statusText" class="uploader-example">
      <uploader-unsupport></uploader-unsupport>
      <uploader-drop>
        <p>拖拽文件至此或点击按钮</p>
        <uploader-btn>选择文件</uploader-btn>
      </uploader-drop>
      <uploader-list></uploader-list>
    </uploader>
  </div>
</template>

<script>
  import {getToken} from '@/utils/auth'

  export default {
    name: 'Uploads',
    props: {
      url: {
        type: String
      },

    },
    data() {
      return {
        options: {
          //target: process.env.VUE_APP_BASE_API +'/system/attributes/fileUpload?metadataId='+this.metadataId,
          target: this.url,
          chunkSize: 5 * 1024 * 1024,
          headers: {
            // 在header中添加的验证，请根据实际业务来
            Authorization: "Bearer " + getToken()
          },
        },
        attrs: {
          accept: '*/*'
        },
        statusText: {
          success: '成功了',
          error: '出错了',
          uploading: '上传中',
          paused: '暂停中',
          waiting: '等待中'
        }
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    display: inline-block;
  }

  .uploader-list > ul > li {
    width: 100%;
    color: red;
    margin-bottom: 0;
  }

  a {
    color: #42b983;
  }

  .fileupload {
    /*width:50%;*/
    /*margin-left: 25%;*/
  }
</style>
