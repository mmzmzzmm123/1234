<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="关键词" prop="title">
        <el-input
          v-model.trim="queryParams.title"
          placeholder="请输入关键词"
          clearable
          size="small"
        />
      </el-form-item>
      <el-form-item label="展示状态" prop="showFlag" label-width="200">
        <el-select
          v-model="queryParams.showFlag"
          placeholder="请选示状态"
          clearable
          size="small"
        >
          <el-option key="0" label="不展示" value="0" />
          <el-option key="1" label="展示" value="1" />
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
          @click="handleAdd"
          v-hasPermi="['custom:wxBannerImage:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['custom:wxBannerImage:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['custom:wxBannerImage:remove']"
        >删除</el-button>
      </el-col>
      <!--<el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['custom:wxBannerImage:export']"
        >导出</el-button>
      </el-col>-->
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="wxBannerImageList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="banner图" align="center" prop="bannerUrl" width="200">
           <template slot-scope="scope">
          <el-image
            :src="scope.row.previewBannerUrl"
            :preview-src-list="previewBannerUrlList"
          >
          </el-image>
        </template>
      </el-table-column>
      <el-table-column label="标题" align="center" prop="title" />
      <el-table-column label="内容" align="center" prop="content" />
      <el-table-column label="跳转地址" align="center" prop="jumpUrl" />
      <el-table-column label="展示状态" align="center" prop="showFlag" >
          <template slot-scope="scope">
          <el-switch
            v-model="scope.row.showFlagExtend"
            active-text="展示"
            inactive-text="不展示"
            @change="handleWxShow($event, scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="优先级" align="center" prop="orderNum" >
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['custom:wxBannerImage:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:wxBannerImage:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改小程序banner图对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="内容" prop="content">
           <el-input type="textarea" placeholder="请输入内容" v-model="form.content" maxlength="200" :autosize="{ minRows: 2, maxRows: 4}" show-word-limit/>
        </el-form-item>
        <el-form-item label="图片地址" prop="bannerUrl">
           <UploadFile
                v-if="open"
                ref="uploadFile"
                :prefix="'wxBanner'"
                :coverUrl="form.previewBannerUrl"
                @callbackMethod="handleBannerImageUrl"
                :tips="''"
                :width="300"
        ></UploadFile>
        </el-form-item>
        <el-form-item label="跳转地址" prop="jumpUrl">
            <el-input type="textarea" placeholder="请输入点击跳转地址"   v-model="form.jumpUrl" maxlength="200" :autosize="{ minRows: 2, maxRows: 4}" show-word-limit></el-input>
        </el-form-item>
        <el-form-item label="展示状态" prop="showFlagExtend">
                <el-switch
                v-model="form.showFlagExtend"
                active-text="小程序展示"
                inactive-text="小程序不展示">
              </el-switch>
              <div>提示：请保证内容正确再展示到小程序</div>
        </el-form-item>
        <el-form-item label="优先级" prop="orderNum">
          <el-input-number v-model="form.orderNum" controls-position="right"  :min="0" :max="100"></el-input-number>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listWxBannerImage, getWxBannerImage, delWxBannerImage, addWxBannerImage, updateWxBannerImage, exportWxBannerImage } from "@/api/custom/wxBannerImage";
import Editor from '@/components/Editor';
import UploadFile from "@/components/FileUpload/UploadFile";
export default {
  name: "WxBannerImage",
  components: { Editor },
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
      // 小程序banner图表格数据
      wxBannerImageList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        showFlag: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
           bannerUrl: [
                { required: true, message: "图片不能为空", trigger: "blur" },
           ],
            jumpUrl: [
                { required: true, message: "跳转地址不能为空", trigger: "blur" },
           ],
      },
      previewBannerUrlList: []
    };
  },
  created() {
    this.getList();
  },
  components:{
      UploadFile
  },
  methods: {
    /** 查询小程序banner图列表 */
    getList() {
      this.loading = true;
      listWxBannerImage(this.queryParams).then(response => {
        response.rows.forEach((item) => {
           this.previewBannerUrlList.push(item.previewBannerUrl); 
           item.showFlagExtend = item.showFlag == 1 ? true : false;
        });
        this.wxBannerImageList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        title: null,
        content: null,
        bannerUrl: null,
        jumpUrl: null,
        showFlag: null,
        showFlagExtend: false,
        orderNum: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null,
        delFlag: null
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加小程序banner图";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getWxBannerImage(id).then(response => {
        this.form = response.data;
        this.form.showFlagExtend = this.form.showFlag == 1;
        this.open = true;
        this.title = "修改小程序banner图";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.showFlag = this.form.showFlagExtend ? 1 : 0;
          if (this.form.id != null) {
            updateWxBannerImage(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addWxBannerImage(this.form).then(response => {
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
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除小程序banner图编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delWxBannerImage(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有小程序banner图数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportWxBannerImage(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
    handleBannerImageUrl(url) {
      this.form.bannerUrl = url;
    },
    handleWxShow(newWxshow, row) {
      let param = {
        id: row.id,
        showFlag: newWxshow ? 1 : 0,
      };
      updateWxBannerImage(param).then(response => {
            /*if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
            }*/
      });
    },
  }
};
</script>