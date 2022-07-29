<template>
  <!--
    Excel数据导入组件
    使用示例：
    [HTML]
    <excel-importer
      permission="expo:invitem:import"
      uploadUrl="expo/project/invitem/importData"
      importTemplateUrl="/expo/project/invitem/importTemplate"
      :disabled.sync="queryParams.projId === '' || queryParams.type === ''"
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
    >{{ $t('button.import') }}</el-button>

    <!-- 调查选项导入对话框 -->
    <el-dialog :title="uploadParams.title" :visible.sync="uploadParams.open" width="400px" :close-on-click-modal="false" :close-on-press-escape="false">
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
          {{this.$t("upload.dragfile1")}}
          <em>{{this.$t("upload.dragfile2")}}</em>
        </div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip" style="display: none;">
            <el-checkbox v-model="uploadParams.updateSupport" />
            {{this.$t("upload.updatedatas")}}
          </div>
          <span style="padding-top:15px;">{{this.$t("upload.xls")}}</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">
            {{$t("button.downloadTemplate")}}
          </el-link>
        </div>

        <!--<div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-checkbox v-model="uploadParams.updateSupport" />更新已经存在的数据
          <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>-->
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm"> {{$t("button.ok")}}</el-button>
        <el-button @click="uploadParams.open = false">{{$t("button.cancel")}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { getToken } from "@/utils/auth";

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
        default: {}
      }
    },
    data() {
      return {
        // 导入参数
        uploadParams: {
          // 是否显示弹出层
          open: false,
          // 弹出层标题
          title: this.$t("button.import"),
          // 是否禁用上传
          isUploading: false,
          // 是否更新已经存在的数据
          updateSupport: true,
          // 设置上传的请求头部
          headers: { Authorization: "Bearer " + getToken() },
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + this.uploadUrl
        },
      }
    },
    computed: {
      /** 导入接口的完整URL */
      importURL: function() {
        var importParamsString = "";
        for (let param in this.importParams) {
          importParamsString += "&";
          importParamsString += (param + '=' + this.importParams[param])
        }
        return this.uploadParams.url + '?' +
          'updateSupport=' + this.uploadParams.updateSupport + importParamsString
      }
    },
    methods: {
      /** 导入按钮操作 */
      handleImport() {
        this.uploadParams.open = true;
      },
      /** 下载导入的模板 */
      importTemplate() {
        this.download(
          this.importTemplateUrl,
          {},
          `import_template.xlsx`
        );
      },
      /** 文件上传中处理 */
      handleFileUploadProgress(event, file, fileList) {
        this.uploadParams.isUploading = true;
      },
      /** 文件上传成功处理 */
      handleFileSuccess(response, file, fileList) {
        this.uploadParams.open = false;
        this.uploadParams.isUploading = false;
        this.$refs.upload.clearFiles();
        this.$alert(response.msg, this.$t('upload.result'), { dangerouslyUseHTMLString: true });
        this.$emit("getList");
      },
      /** 提交上传文件 */
      submitFileForm() {
        this.$refs.upload.submit();
      },
    }
  }
</script>
