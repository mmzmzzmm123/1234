<template>
<el-dialog title="视频上传" v-if="open" :visible.sync="open" width="700px" :close-on-click-modal="false" :show-close="false" append-to-body>
<el-form ref="videoFrom" :model="videoFrom" :rules="videoRules" label-width="80px">
  <el-row :gutter="15">
        <el-form-item label="视频标题" prop="title">
            <el-input
                type="textarea"
                placeholder="请输入视频标题"
                v-model="videoFrom.title"
                maxlength="50" 
                rows="1"
                show-word-limit
            />
        </el-form-item>
        <el-form-item label="视频描述" prop="description">
          <el-input
                type="textarea"
                placeholder="请输入视频描述"
                v-model="videoFrom.description"
                maxlength="1000"
                rows="3"
                show-word-limit
            />
        </el-form-item>
         <el-form-item label="视频封面" prop="coverUrl">
              <UploadFile ref="uploadFile" :prefix="'videoCover'" @callbackMethod="handleCoverUrl"></UploadFile>
          </el-form-item>
<el-col :span="12">
          <el-form-item label="视频类别" prop="cateId">
            <el-select v-model="videoFrom.cateId" clearable filterable placeholder="请选择类别">
              <el-option
                v-for="classify in classifyList"
                :key="classify.id"
                :label="classify.cateName"
                :value="classify.id"
              />
            </el-select>
          </el-form-item>
</el-col>
      <el-col :span="12">    
          <el-form-item label="视频权限" prop="payLevel">
            <el-select v-model="videoFrom.payLevel" clearable filterable placeholder="请选择权限">
              <el-option
                v-for="dict in payVideoLevelList"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
</el-col>
          
        <el-form-item label="视频文件" prop="file">
            <div>
            <input type="file" accept=".mp4" ref="videoFile" id="videoFile" @change="fileChange($event)">
            <div > <span>上传状态：{{statusText}}</span><span style="margin-left:100px">进度：{{authProgress}}%</span></div>
            <div > 1、只能上传mp4文件，上传大文件时请使用客户端上传，防止上传超时</div>
            </div>
        </el-form-item>

        <el-form-item label="展示状态" prop="wxShow">
              <el-switch
                v-model="videoFrom.wxShow"
                active-text="小程序展示"
                inactive-text="小程序不展示">
              </el-switch>
              <div>提示：请保证内容正确再展示到小程序</div>
          </el-form-item>    
  </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="authUpload" :disabled="uploadDisabled">开始上传</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
</el-dialog>
  
</template>
<script>
  import axios from 'axios'
  import {getUploadVideoAuth,addNutritionalVideo } from "@/api/custom/nutritionalVideo";
  import {getAllClassify } from "@/api/custom/videoClassify";
  import UploadFile from "@/components/FileUpload/UploadFile";
  export default {
      name: "UploadVideo",
    data () {
      return {
        open: false,
        videoFrom:{},
        videoRules:{
          title: [
            { required: true, message: "标题不能为空", trigger: "blur" },
          ],
          coverUrl: [
           { required: true, message: "封面不能为空", trigger: "blur" },
          ],
          cateId:[
            { required: true, message: "视频类别不能为空", trigger: "blur" },
          ],
          payLevel:[
            { required: true, message: "视频权限不能为空", trigger: "blur" },
          ]
        },
        callback: null,
        classifyList: [],
        payVideoLevelList:[],
        uploadAuth:{
            
        },
        videoFileList:[],
        timeout: '',
        partSize: '',
        parallel: '',
        retryCount: '',
        retryDuration: '',
        region: 'cn-shenzhen',
        userId: '1808303706021362',
        file: null,
        authProgress: 0,
        uploadDisabled: true,
        resumeDisabled: true,
        pauseDisabled: true,
        uploader: null,
        statusText: '',
        fileType:['mp4','MP4'],
        uploading: false
      }
    },
    created(){
      getAllClassify().then(response => {
          if(response.code == 200){
              this.classifyList = response.data;
          }
      });
      this.getDicts("video_pay_level").then((response) => {
        this.payVideoLevelList = response.data;
      });
        
    },
    components: {
      UploadFile
    },
    methods: {
        showDialog(callback){
            this.resetVideoFrom();
            this.open = true;
            this.callback = callback;
        },
        handleCoverUrl(url){
          this.videoFrom.coverUrl = url;
        },
        resetVideoFrom(){
            this.videoFrom = {
                cateId: null,
                coverUrl: null,
                title: null,
                description: null,
                tags: null,
                payLevel: 0,
                videoId: null,
                wxShow: false
            };
            if(this.$refs.uploadFile){
                this.$refs.uploadFile.resetUpload();
            }
            this.authProgress = 0;
            this.file = null;
            this.videoFileList = [];
            this.uploadAuth = {};
            this.uploadDisabled = true;
            this.uploader = null;
            this.statusText = '';
            this.uploading = false;
            this.resetForm("videoFrom"); 
        },
        submitVideoForm(){
            
        },
        cancel(){
            if(this.uploading){
                this.$message({
                    message: "文件正在上传，请勿取消",
                    type: "warning",
                });
                return;
            }
            this.open = false;
            this.resetVideoFrom();
        },

        fileChange (e) {
            this.$refs["videoFrom"].validate((valid) => {
              if (valid) {
                 if(this.uploading){
                    this.$message({
                      message: "文件正在上传，请勿取消",
                      type: "warning",
                    });
                    return;
                  }
                  this.file = e.target.files[0];
                  var userData = '{"Vod":{}}'
                  /**if (this.uploader) {
                      this.uploader.stopUpload()
                      this.authProgress = 0
                      this.statusText = ""
                  }**/
                  this.videoFrom.fileName = this.file.name;
                  if(this.videoFrom.fileName == null || this.videoFrom.fileName.length == 0 || this.videoFrom.fileName.lastIndexOf(".") == -1){
                      this.$message({
                          message: "当前文件名称错误",
                          type: "warning",
                      });
                      return;
                  }
                  let fileType = this.videoFrom.fileName.substring(this.videoFrom.fileName.lastIndexOf(".")+1);
                  if(this.fileType.indexOf(fileType) == -1){
                      this.$message({
                          message: "当前文件格式错误",
                          type: "warning",
                      });
                      return;
                  }
                  
                  getUploadVideoAuth(this.videoFrom).then(response => {
                      if(response.code == 200){
                          this.uploadAuth = response.uploadAuth;
                          console.log(this.uploadAuth);
                          this.videoFrom.videoId = this.uploadAuth.videoId;
                          this.uploader = this.createUploader()
                          this.uploader.addFile(this.file, null, null, null, userData)
                          this.uploadDisabled = false
                          this.pauseDisabled = true
                          this.resumeDisabled = true
                      }
                  })
                    }
                  }); 
      },
      authUpload () {
        // 然后调用 startUpload 方法, 开始上传
        if (this.uploader !== null) {
          this.uploader.startUpload()
          this.uploadDisabled = true
          this.pauseDisabled = false
        }
      },
      // 暂停上传
      pauseUpload () {
        if (this.uploader !== null) {
          this.uploader.stopUpload()
          this.resumeDisabled = false
          this.pauseDisabled = true
        }
      },
      // 恢复上传
      resumeUpload () {
        if (this.uploader !== null) {
          this.uploader.startUpload()
          this.resumeDisabled = true
          this.pauseDisabled = false
        }
      },
      createUploader (type) {
        let self = this
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
            self.uploadDisabled = false
            self.resumeDisabled = false
            self.statusText = '添加文件成功, 等待上传...'
            console.log("addFileSuccess: " + uploadInfo.file.name)
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
            uploader.setUploadAuthAndAddress(uploadInfo, self.uploadAuth.uploadAuth, self.uploadAuth.uploadAddress, self.uploadAuth.videoId) 
            self.statusText = '文件开始上传...'
            console.log("onUploadStarted:" + uploadInfo.file.name + ", endpoint:" + uploadInfo.endpoint + ", bucket:" + uploadInfo.bucket + ", object:" + uploadInfo.object)
          },
          // 文件上传成功
          onUploadSucceed: function (uploadInfo) {
            console.log("onUploadSucceed: " + uploadInfo.file.name + ", endpoint:" + uploadInfo.endpoint + ", bucket:" + uploadInfo.bucket + ", object:" + uploadInfo.object)
            self.statusText = '文件上传成功!'
            
          },
          // 文件上传失败
          onUploadFailed: function (uploadInfo, code, message) {
            console.log("onUploadFailed: file:" + uploadInfo.file.name + ",code:" + code + ", message:" + message);
            self.uploading = false;
            self.statusText = '文件上传失败!'
          },
          // 取消文件上传
          onUploadCanceled: function (uploadInfo, code, message) {
            console.log("Canceled file: " + uploadInfo.file.name + ", code: " + code + ", message:" + message)
            self.statusText = '文件已暂停上传'
          },
          // 文件上传进度，单位：字节, 可以在这个函数中拿到上传进度并显示在页面上
          onUploadProgress: function (uploadInfo, totalSize, progress) {
            console.log("onUploadProgress:file:" + uploadInfo.file.name + ", fileSize:" + totalSize + ", percent:" + Math.ceil(progress * 100) + "%")
            let progressPercent = Math.ceil(progress * 100)
            self.authProgress = progressPercent
            self.statusText = '文件上传中...'
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
            self.statusText = '文件上传超时...';
            self.uploading = false;
            
          },
          // 全部文件上传结束
          onUploadEnd: function (uploadInfo) {
            self.statusText = '文件上传完毕'
            self.uploading = false;
            //self.msgSuccess("上传成功");
            self.videoFrom.showFlag = self.videoFrom.wxShow ? 1 : 0;
            addNutritionalVideo(self.videoFrom).then(response => {
                if (response.code === 200) {
                  self.msgSuccess("视频上传成功");
                  self.open = false;
                  self.callback && self.callback();
                }
            })         
          }
        })
        return uploader
      }
    }
  }
</script>