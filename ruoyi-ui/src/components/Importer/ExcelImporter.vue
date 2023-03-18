<template>
  <!--
    Excel数据导入组件
    使用示例：
    [HTML]
    <excel-importer
      permission="xxx:xxx:import"
      uploadUrl="xxx/xxx/importData"
      importTemplateUrl="/xxx/xxx/importTemplate"
      :disabled.sync="..."
      :importParams.sync="importParams"
      @getList="getList">
    </excel-importer>
  -->
  <div class="excel-importer-container">
    <el-button
      plain
      icon="el-icon-upload"
      size="mini"
      @click="handleImport"
      :disabled="disabled"
      v-hasPermi="[permission]"
    >导入
    </el-button>

    <el-dialog :title="uploadParams.title" :visible.sync="uploadParams.open" width="400px" :close-on-click-modal="false"
               :close-on-press-escape="false">
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="uploadParams.headers"
        :action="importURL"
        :disabled="uploadParams.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处,或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="uploadParams.updateSupport"/>
            覆盖更新已存在的数据
          </div>
          <span style="padding-top:15px;">仅允许导入xls、xlsx格式文件</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;"
                   @click="importTemplate">
            下载数据导入模板
          </el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确定</el-button>
        <el-button @click="uploadParams.open = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { getToken } from '@/utils/auth'

  export default {
    name: 'ExcelImporter',
    props: {
      /** 权限字符 */
      permission: {
        type: String,
        required: true
      },
      /** 上传地址 */
      uploadUrl: {
        type: String,
        required: true
      },
      /** 导入模板的下载地址 */
      importTemplateUrl: {
        type: String,
        required: true
      },
      /** 是否禁用上传按钮 */
      disabled: {
        type: Boolean,
        default: false
      },
      /** 导入参数 */
      importParams: {
        type: Object,
        default: () => {
          return {}
        }
      }
    },
    data() {
      return {
        // 导入参数
        uploadParams: {
          // 是否显示弹出层
          open: false,
          // 弹出层标题
          title: '导入',
          // 是否禁用上传
          isUploading: false,
          // 是否更新已经存在的数据
          updateSupport: true,
          // 设置上传的请求头部
          headers: { Authorization: 'Bearer ' + getToken() },
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + this.uploadUrl
        }
      }
    },
    computed: {
      /** 导入接口的完整URL */
      importURL: function() {
        var importParamsString = ''
        for (let param in this.importParams) {
          importParamsString += '&'
          importParamsString += (param + '=' + this.importParams[param])
        }
        return this.uploadParams.url + '?' +
          'updateSupport=' + this.uploadParams.updateSupport + importParamsString
      }
    },
    methods: {
      /** 导入按钮操作 */
      handleImport() {
        this.uploadParams.open = true
      },
      /** 下载导入的模板 */
      importTemplate() {
        this.download(
          this.importTemplateUrl,
          {},
          `import_template.xlsx`
        )
      },
      /** 文件上传中处理 */
      handleFileUploadProgress(event, file, fileList) {
        this.uploadParams.isUploading = true
      },
      /** 文件上传成功处理 */
      handleFileSuccess(response, file, fileList) {
        this.uploadParams.open = false
        this.uploadParams.isUploading = false
        this.$refs.upload.clearFiles()
        this.$alert(response.msg, '导入结果', { dangerouslyUseHTMLString: true })
        this.$emit('getList')
      },
      /** 提交上传文件 */
      submitFileForm() {
        this.$refs.upload.submit()
      }
    }
  }
</script>
