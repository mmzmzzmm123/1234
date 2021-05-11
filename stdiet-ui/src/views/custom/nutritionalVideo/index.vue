<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关键词" prop="key">
        <el-input
          v-model.trim="queryParams.key"
          placeholder="请输入关键词"
          clearable
          size="small"
        />
      </el-form-item>
      <el-form-item label="小程序展示状态" prop="showFlag" label-width="200">
        <el-select
          v-model="queryParams.showFlag"
          placeholder="请选示状态"
          clearable
          size="small"
        >
          <el-option key="0" label="不展示" value="0"/>
          <el-option key="1" label="展示" value="1"/>
        </el-select>
      </el-form-item>
       <el-form-item label="视频类别" prop="cateId">
            <el-select v-model="queryParams.cateId" clearable filterable placeholder="请选择类别">
              <el-option
                v-for="classify in classifyList"
                :key="classify.id"
                :label="classify.cateName"
                :value="classify.id"
              />
            </el-select>
      </el-form-item>
      <el-form-item label="视频权限" prop="payLevel">
            <el-select v-model="queryParams.payLevel" clearable filterable placeholder="请选择权限">
              <el-option
                v-for="dict in payVideoLevelList"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="clickUploadVideo"
          v-hasPermi="['custom:nutritionalVideo:add']"
        >视频上传</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['custom:nutritionalVideo:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['custom:nutritionalVideo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          size="mini"
          icon="el-icon-menu"
          @click="handleVideoClassify"
          v-hasPermi="['custom:videoClassify:list']"
        >视频分类管理</el-button>
      </el-col>
      <!--
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['custom:nutritionalVideo:export']"
        >导出</el-button>
      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="nutritionalVideoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!--<el-table-column label="视频分类ID" align="center" prop="cateId" />-->
      <el-table-column label="封面" align="center" prop="coverUrl" width="300">
        <template slot-scope="scope">
          <el-image
          style="width: 300px; height: 200px"
          :src="scope.row.coverUrl"
          :preview-src-list="coverImageList">
          </el-image>
        </template>
      </el-table-column>
      <el-table-column label="标题" align="center" prop="title" width="200">
        <template slot-scope="scope" >
            <AutoHideMessage :data="scope.row.title == null ? '' : (scope.row.title+'')" :maxLength="20"></AutoHideMessage>
        </template>
      </el-table-column>
      <el-table-column label="描述" align="center" prop="description" >
        <template slot-scope="scope" >
            <AutoHideMessage :data="scope.row.description == null ? '' : (scope.row.description+'')" :maxLength="100"></AutoHideMessage>
        </template>
      </el-table-column>
      <!--<el-table-column label="标签" align="center" prop="tags" width="100"/>-->
       <el-table-column label="分类" align="center" prop="cateName" width="100"/>
       <el-table-column label="权限等级" align="center" prop="payLevelName" width="100"/>
      <el-table-column label="小程序展示状态" align="center" prop="showFlag" width="200">
        <template slot-scope="scope" >
          <el-switch
                v-model="scope.row.wxShow"
                active-text="展示"
                inactive-text="不展示"
                @change="handleWxShow($event, scope.row)"
                >
              </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="getVideoPlayUrl(scope.row.id)"
            v-hasPermi="['custom:nutritionalVideo:query']"
          >播放</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['custom:nutritionalVideo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:nutritionalVideo:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>



    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      :pageSizes="[5,10,15,20]"
      @pagination="getList"
    />

    <!-- 添加或修改营养视频对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="视频标题" prop="title">
            <el-input
                type="textarea"
                placeholder="请输入视频标题"
                v-model="form.title"
                maxlength="50" 
                rows="1"
                show-word-limit
            />
        </el-form-item>
        <el-form-item label="视频描述" prop="description">
          <el-input
                type="textarea"
                placeholder="请输入视频描述"
                v-model="form.description"
                maxlength="1000"
                rows="2"
                show-word-limit
            />
        </el-form-item>
         <el-form-item label="视频封面" prop="coverUrl">
              <UploadFile ref="uploadFile" v-if="open" :prefix="'videoCover'" :coverUrl="form.previewUrl" @callbackMethod="handleCoverUrl" :tips="'视频未传封面图片时，会主动截取封面，但会存在延迟，请勿直接发布到小程序'"></UploadFile>
          </el-form-item>
        <el-form-item label="视频类别" prop="cateId">
            <el-select v-model="form.cateId" clearable filterable placeholder="请选择类别">
              <el-option
                v-for="classify in classifyList"
                :key="classify.id"
                :label="classify.cateName"
                :value="classify.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="视频权限" prop="payLevel">
            <el-select v-model="form.payLevel" clearable filterable placeholder="请选择权限">
              <el-option
                v-for="dict in payVideoLevelList"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
           <el-form-item label="展示状态" prop="wxShow">
              <el-switch
                v-model="form.wxShow"
                active-text="小程序展示"
                inactive-text="小程序不展示">
              </el-switch>
              <div style="color:red">提示：请保证内容正确再展示到小程序</div>
          </el-form-item>    
      </el-form>
     
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <UploadVideo ref="uploadVideoRef"></UploadVideo>

    <!-- 视频分类管理 -->
    <el-dialog title="视频分类列表" :visible.sync="videoClassifyOpen" width="800px" append-to-body @closed="getAllVideoClassify();">
      <VideoClassify ref="videoClassifyRef"></VideoClassify>
    </el-dialog>
  </div>
</template>

<script>
  import { listNutritionalVideo, getNutritionalVideo, delNutritionalVideo, addNutritionalVideo, updateNutritionalVideo, exportNutritionalVideo, updateWxShow,getVideoPlayUrlById } from "@/api/custom/nutritionalVideo";
  import {getAllClassify } from "@/api/custom/videoClassify";
  import UploadVideo from "@/components/UploadVideo";
  import UploadFile from "@/components/FileUpload/UploadFile";
  import VideoClassify from "../videoClassify";
  import AutoHideMessage from "@/components/AutoHideMessage";
  export default {
    name: "NutritionalVideo",
    data() {
      return {
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 营养视频表格数据
        nutritionalVideoList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 5,
          key: null,
          showFlag: null,
          cateId: null,
          payLevel: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          title: [
            { required: true, message: "标题不能为空", trigger: "blur" },
          ],
          cateId:[
            { required: true, message: "视频类别不能为空", trigger: "blur" },
          ],
          payLevel:[
            { required: true, message: "视频权限不能为空", trigger: "blur" },
          ]
        },
        coverImageList:[],
        //分类列表
        classifyList:[],
        //权限等级列表
        payVideoLevelList:[],
        //视频分类弹窗显示标识
        videoClassifyOpen:false

      };
    },
    created() {
      this.getList();
      this.getAllVideoClassify();
      this.getDicts("video_pay_level").then((response) => {
        this.payVideoLevelList = response.data;
      });
    },
    components: {
      UploadVideo,UploadFile,VideoClassify,AutoHideMessage
    },
    methods: {
      /** 查询营养视频列表 */
      getList() {
        this.loading = true;
        listNutritionalVideo(this.queryParams).then(response => {
          response.rows.forEach(element => {
            element.wxShow = element.showFlag == 1 ? true : false;
          });
          this.nutritionalVideoList = response.rows;
          this.total = response.total;
          this.loading = false;
      });
      },
      //获取所有分类
      getAllVideoClassify(){
        getAllClassify().then(response => {
          if(response.code == 200){
            this.classifyList = response.data;
          }
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          cateId: null,
          coverUrl: null,
          title: null,
          description: null,
          tags: null,
          payLevel:null,
          showFlag: null,
          wxShow: false
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length!==1
        this.multiple = !selection.length
      },
      clickUploadVideo(){
        this.$refs.uploadVideoRef.showDialog(this.classifyList, ()=>{
         this.getList();
        });
      },
      handleVideoClassify(){
        this.videoClassifyOpen = true;
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加营养视频";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getNutritionalVideo(id).then(response => {
          response.data.wxShow = response.data.showFlag == 1 ? true : false;
          this.form = response.data;
          this.open = true;
          this.title = "修改营养视频";
        });
      },
      //获取播放地址
      getVideoPlayUrl(id){
        getVideoPlayUrlById(id).then(response => {
           let url = response.data.playUrl;
           if(url != undefined && url != null){
                window.open(url, '_blank');
           }
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            this.form.showFlag = this.form.wxShow ? 1 : 0;
            if (this.form.id != null) {
              updateNutritionalVideo(this.form).then(response => {
                if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
            } else {
              addNutritionalVideo(this.form).then(response => {
                if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
            }
          }
        });
      },
      handleWxShow(newWxshow, row){
        let param = {
          id: row.id,
          showFlag: newWxshow ? 1 : 0
        };
        updateWxShow(param);
      },
      handleCoverUrl(url){
        this.form.coverUrl = url;
        console.log(this.form.coverUrl);
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除营养视频编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delNutritionalVideo(ids);
        }).then(() => {
          this.getList();
        this.msgSuccess("删除成功");
      }).catch(function() {});
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有营养视频数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportNutritionalVideo(queryParams);
        }).then(response => {
          this.download(response.msg);
      }).catch(function() {});
      }
    }
  };
</script>
