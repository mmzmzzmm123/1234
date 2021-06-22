<template>
  <el-dialog
    title="视频上传"
    v-if="open"
    :visible.sync="open"
    width="700px"
    :close-on-click-modal="false"
    :show-close="false"
    append-to-body
  >
    <el-form
      ref="videoFrom"
      :model="videoFrom"
      :rules="videoRules"
      label-width="80px"
      style="height: 650px; overflow: auto"
    >
      <el-form-item label="视频标题" prop="title">
        <el-input
          type="textarea"
          placeholder="请输入视频标题"
          v-model.trim="videoFrom.title"
          maxlength="50"
          rows="1"
          show-word-limit
        />
      </el-form-item>
      <el-form-item label="视频描述" prop="description">
        <el-input
          type="textarea"
          placeholder="请输入视频描述"
          v-model.trim="videoFrom.description"
          maxlength="1000"
          rows="2"
          show-word-limit
        />
      </el-form-item>
      <div style="display: flex">
        <el-form-item label="视频类别" prop="cateId" style="width: 300px">
          <treeselect
            v-model="videoFrom.cateId"
            :options="classifyList"
            :normalizer="normalizer"
            :show-count="true"
            placeholder="选择分类"
          />
        </el-form-item>
        <el-form-item
          label="视频权限"
          prop="payLevel"
          style="margin-left: 40px"
        >
          <el-select
            v-model="videoFrom.payLevel"
            clearable
            filterable
            placeholder="请选择权限"
          >
            <el-option
              v-for="dict in payVideoLevelList"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            />
          </el-select>
        </el-form-item>
      </div>
      <el-form-item label="视频文件" prop="file">
        <div>
          <input
            type="file"
            accept=".mp4"
            ref="videoFile"
            id="videoFile"
            @change="fileChange($event)"
          />
          <div>
            <span>上传状态：{{ statusText }}</span
            ><span style="margin-left: 100px">进度：{{ authProgress }}%</span>
          </div>
          <div style="color: #1890ff">
            <el-button
              type="primary"
              @click="authUpload"
              :disabled="uploadDisabled"
              size="small"
              icon="el-icon-upload2"
              >上传视频</el-button
            ><span style="margin-left: 20px">1、只能上传mp4格式视频</span>
          </div>
        </div>
      </el-form-item>
      <el-form-item label="视频封面" prop="coverUrl">
        <UploadFile
          ref="uploadFile"
          :prefix="'videoCover'"
          :coverUrl="videoFrom.previewUrl"
          @callbackMethod="handleCoverUrl"
          :tips="''"
        ></UploadFile>
        <el-button
          type="primary"
          size="small"
          icon="el-icon-film"
          @click="selectVideoCover"
          :disabled="!uploadVideoFlag"
          title="上传视频之后选择视频截图作为封面"
          >选择封面</el-button
        >
      </el-form-item>
      <el-form-item label="排序" prop="priorityLevel">
        <el-input-number
          v-model="videoFrom.priorityLevel"
          size="small"
          :step="1"
        ></el-input-number>
      </el-form-item>
      <el-form-item label="展示状态" prop="wxShow">
        <el-switch
          v-model="videoFrom.wxShow"
          active-text="展示"
          inactive-text="不展示"
        >
        </el-switch>
        <div style="color: red">
          提示：开启展示之后客户可看到该视频，请保证内容正确再展示
        </div>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitVideoForm">保存</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
    <!-- 手动选择封面 -->
    <VideoSelectCover ref="videoSelectCoverRef"></VideoSelectCover>
  </el-dialog>
</template>
<script>
import {
  getUploadVideoAuth,
  addNutritionalVideo,
  getVideoSnapshot,
  submitVideoSnapshot,
} from "@/api/custom/nutritionalVideo";
import { getAllClassify } from "@/api/custom/videoClassify";
import UploadFile from "@/components/FileUpload/UploadFile";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import IconSelect from "@/components/IconSelect";
import VideoSelectCover from "@/components/VideoSelectCover";
export default {
  name: "UploadVideo",
  data() {
    return {
      open: false,
      videoFrom: {},
      videoRules: {
        title: [{ required: true, message: "标题不能为空", trigger: "blur" }],
        cateId: [
          { required: true, message: "视频类别不能为空", trigger: "blur" },
        ],
        payLevel: [
          { required: true, message: "视频权限不能为空", trigger: "blur" },
        ],
      },
      callback: null,
      classifyList: [],
      defaultClassify: null,
      payVideoLevelList: [],
      defaultPayLevel: null,
      uploadAuth: {},
      videoFileList: [],
      timeout: "",
      partSize: "",
      parallel: "",
      retryCount: "",
      retryDuration: "",
      region: "cn-shenzhen",
      userId: "1808303706021362",
      file: null,
      authProgress: 0,
      uploadDisabled: true,
      resumeDisabled: true,
      pauseDisabled: true,
      uploader: null,
      statusText: "",
      fileType: ["mp4", "MP4"],
      uploading: false,
      //视频是否上传成功标识
      uploadVideoFlag: false,
    };
  },
  created() {
    /*getAllClassify().then(response => {
          if(response.code == 200){
              this.classifyList = response.data;
              if(response.data != null && response.data.length > 0){
                 this.defaultClassify = response.data[0].id;
              }
          }
      });*/
    this.getDicts("video_pay_level").then((response) => {
      this.payVideoLevelList = response.data;
      if (response.data != null && response.data.length > 0) {
        this.defaultPayLevel = response.data[0].dictValue;
      }
    });
  },
  components: {
    UploadFile,
    Treeselect,
    IconSelect,
    VideoSelectCover,
  },
  methods: {
    showDialog(classifyList, callback) {
      if (classifyList != null && classifyList.length > 0) {
        this.defaultClassify = classifyList[0].id;
      }
      this.resetVideoFrom();
      this.open = true;
      this.callback = callback;
      this.dealClassify(classifyList);
      //this.classifyList = classifyList;
    },
    handleCoverUrl(url) {
      this.videoFrom.coverUrl = url;
    },
    resetVideoFrom() {
      this.videoFrom = {
        cateId: this.defaultClassify ? this.defaultClassify : null,
        coverUrl: null,
        title: null,
        description: null,
        priorityLevel: 0,
        tags: null,
        payLevel: this.defaultPayLevel ? parseInt(this.defaultPayLevel) : null,
        videoId: null,
        wxShow: false,
        previewUrl: null,
      };
      if (this.$refs.uploadFile) {
        this.$refs.uploadFile.resetUpload();
      }
      this.authProgress = 0;
      this.file = null;
      this.videoFileList = [];
      this.uploadAuth = {};
      this.uploadDisabled = true;
      this.uploader = null;
      this.statusText = "";
      this.uploading = false;
      this.uploadVideoFlag = false;
      this.resetForm("videoFrom");
    },
    submitVideoForm() {
      this.$refs["videoFrom"].validate((valid) => {
        if (valid) {
          //视频分类不能选择主分类
          if (this.videoFrom.cateId == 0) {
            this.$message({
              message: "视频分类不能选择主分类",
              type: "warning",
            });
            return;
          }
          if (this.uploading) {
            this.$message({
              message: "视频正在上传，请勿取消",
              type: "warning",
            });
            return;
          }
          if (!this.uploadVideoFlag) {
            this.$message({
              message: "请先上传视频再保存",
              type: "warning",
            });
            return;
          }
          this.videoFrom.showFlag = this.videoFrom.wxShow ? 1 : 0;
          addNutritionalVideo(this.videoFrom).then((response) => {
            if (response.code === 200) {
              this.msgSuccess("视频保存成功");
              this.open = false;
              this.callback && this.callback();
            }
          });
        }
      });
    },
    selectVideoCover() {
      this.$refs.videoSelectCoverRef.showDialog(this.videoFrom, (url) => {
        //console.log(url);
        this.videoFrom.previewUrl = url;
        this.videoFrom.coverUrl = url;
      });
    },
    /** 转换菜单数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.id,
        label: node.cateName,
        children: node.children,
      };
    },
    dealClassify(data) {
      this.classifyList = [];
      const classify = { id: 0, cateName: "主分类", children: [] };
      classify.children = this.handleTree(data, "id");
      this.classifyList.push(classify);
    },
    cancel() {
      if (this.uploading) {
        this.$message({
          message: "视频正在上传，请勿取消",
          type: "warning",
        });
        return;
      }
      this.open = false;
      this.resetVideoFrom();
    },

    fileChange(e) {
      this.$refs["videoFrom"].validate((valid) => {
        if (valid) {
          if (this.uploading) {
            this.$message({
              message: "视频正在上传，请勿取消",
              type: "warning",
            });
            return;
          }
          this.file = e.target.files[0];
          var userData = '{"Vod":{}}';
          /**if (this.uploader) {
                      this.uploader.stopUpload()
                      this.authProgress = 0
                      this.statusText = ""
                  }**/
          //视频分类不能选择主分类
          if (this.videoFrom.cateId == 0) {
            this.$message({
              message: "视频分类不能选择主分类",
              type: "warning",
            });
            return;
          }
          this.videoFrom.fileName = this.file.name;
          if (
            this.videoFrom.fileName == null ||
            this.videoFrom.fileName.length == 0 ||
            this.videoFrom.fileName.lastIndexOf(".") == -1
          ) {
            this.$message({
              message: "当前视频名称错误",
              type: "warning",
            });
            return;
          }
          let fileType = this.videoFrom.fileName.substring(
            this.videoFrom.fileName.lastIndexOf(".") + 1
          );
          if (this.fileType.indexOf(fileType) == -1) {
            this.$message({
              message: "当前视频格式错误",
              type: "warning",
            });
            return;
          }

          getUploadVideoAuth(this.videoFrom).then((response) => {
            if (response.code == 200) {
              this.uploadAuth = response.uploadAuth;
              console.log(this.uploadAuth);
              this.videoFrom.videoId = this.uploadAuth.videoId;
              this.uploader = this.createUploader();
              this.uploader.addFile(this.file, null, null, null, userData);
              this.uploadDisabled = false;
              this.pauseDisabled = true;
              this.resumeDisabled = true;
            }
          });
        }
      });
    },
    authUpload() {
      // 然后调用 startUpload 方法, 开始上传
      if (this.uploader !== null) {
        this.uploader.startUpload();
        this.uploadDisabled = true;
        this.pauseDisabled = false;
      }
    },
    // 暂停上传
    pauseUpload() {
      if (this.uploader !== null) {
        this.uploader.stopUpload();
        this.resumeDisabled = false;
        this.pauseDisabled = true;
      }
    },
    // 恢复上传
    resumeUpload() {
      if (this.uploader !== null) {
        this.uploader.startUpload();
        this.resumeDisabled = true;
        this.pauseDisabled = false;
      }
    },
    createUploader(type) {
      let self = this;
      let uploader = new AliyunUpload.Vod({
        timeout: self.timeout || 60000,
        partSize: self.partSize || 1048576,
        parallel: self.parallel || 5,
        retryCount: self.retryCount || 1,
        retryDuration: self.retryDuration || 2,
        region: self.region,
        userId: self.userId,
        // 添加文件成功
        addFileSuccess: function (uploadInfo) {
          self.uploadDisabled = false;
          self.resumeDisabled = false;
          self.statusText = "添加视频成功, 等待上传...";
          console.log("addFileSuccess: " + uploadInfo.file.name);
        },
        // 开始上传
        onUploadstarted: function (uploadInfo) {
          self.uploading = true;
          // 如果是 UploadAuth 上传方式, 需要调用 uploader.setUploadAuthAndAddress 方法
          // 如果是 UploadAuth 上传方式, 需要根据 uploadInfo.videoId是否有值，调用点播的不同接口获取uploadauth和uploadAddress
          // 如果 uploadInfo.videoId 有值，调用刷新视频上传凭证接口，否则调用创建视频上传凭证接口
          // 注意: 这里是测试 demo 所以直接调用了获取 UploadAuth 的测试接口, 用户在使用时需要判断 uploadInfo.videoId 存在与否从而调用 openApi
          // 如果 uploadInfo.videoId 存在, 调用 刷新视频上传凭证接口(https://help.aliyun.com/document_detail/55408.html)
          // 如果 uploadInfo.videoId 不存在,调用 获取视频上传地址和凭证接口(https://help.aliyun.com/document_detail/55407.html)
          uploader.setUploadAuthAndAddress(
            uploadInfo,
            self.uploadAuth.uploadAuth,
            self.uploadAuth.uploadAddress,
            self.uploadAuth.videoId
          );
          self.statusText = "视频开始上传...";
          console.log(
            "onUploadStarted:" +
              uploadInfo.file.name +
              ", endpoint:" +
              uploadInfo.endpoint +
              ", bucket:" +
              uploadInfo.bucket +
              ", object:" +
              uploadInfo.object
          );
        },
        // 文件上传成功
        onUploadSucceed: function (uploadInfo) {
          console.log(
            "onUploadSucceed: " +
              uploadInfo.file.name +
              ", endpoint:" +
              uploadInfo.endpoint +
              ", bucket:" +
              uploadInfo.bucket +
              ", object:" +
              uploadInfo.object
          );
          self.statusText = "视频上传成功!";
        },
        // 文件上传失败
        onUploadFailed: function (uploadInfo, code, message) {
          console.log(
            "onUploadFailed: file:" +
              uploadInfo.file.name +
              ",code:" +
              code +
              ", message:" +
              message
          );
          self.uploading = false;
          self.statusText = "视频上传失败!";
        },
        // 取消文件上传
        onUploadCanceled: function (uploadInfo, code, message) {
          console.log(
            "Canceled file: " +
              uploadInfo.file.name +
              ", code: " +
              code +
              ", message:" +
              message
          );
          self.statusText = "视频已暂停上传";
        },
        // 文件上传进度，单位：字节, 可以在这个函数中拿到上传进度并显示在页面上
        onUploadProgress: function (uploadInfo, totalSize, progress) {
          console.log(
            "onUploadProgress:file:" +
              uploadInfo.file.name +
              ", fileSize:" +
              totalSize +
              ", percent:" +
              Math.ceil(progress * 100) +
              "%"
          );
          let progressPercent = Math.ceil(progress * 100);
          self.authProgress = progressPercent;
          self.statusText = "视频上传中...";
        },
        // 上传凭证超时
        onUploadTokenExpired: function (uploadInfo) {
          // 上传大文件超时, 如果是上传方式一即根据 UploadAuth 上传时
          // 需要根据 uploadInfo.videoId 调用刷新视频上传凭证接口(https://help.aliyun.com/document_detail/55408.html)重新获取 UploadAuth
          // 然后调用 resumeUploadWithAuth 方法, 这里是测试接口, 所以我直接获取了 UploadAuth
          /*let refreshUrl = 'https://demo-vod.cn-shanghai.aliyuncs.com/voddemo/RefreshUploadVideo?BusinessType=vodai&TerminalType=pc&DeviceModel=iPhone9,2&UUID=59ECA-4193-4695-94DD-7E1247288&AppVersion=1.0.0&Title=haha1&FileName=xxx.mp4&VideoId=' + uploadInfo.videoId
            axios.get(refreshUrl).then(({data}) => {
              let uploadAuth = data.UploadAuth
              uploader.resumeUploadWithAuth(uploadAuth)
              console.log('upload expired and resume upload with uploadauth ' + uploadAuth)
            })*/
          self.statusText = "视频上传超时...";
          self.uploading = false;
        },
        // 全部文件上传结束
        onUploadEnd: function (uploadInfo) {
          self.statusText = "视频上传完毕";
          self.uploading = false;
          self.uploadVideoFlag = true;
          submitVideoSnapshot(self.videoFrom.videoId).then((response) => {
            if (response.code == 200) {
              console.log("-- 截图成功 --");
            }
          });
        },
      });
      return uploader;
    },
  },
};
</script>
