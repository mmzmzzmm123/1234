<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="食材名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入食材名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="食材类别" prop="type">
        <el-select
          v-model="queryParams.type"
          placeholder="请选择食材类别"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="地域" prop="area">
        <el-select
          v-model="queryParams.area"
          placeholder="请选择地域"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in areaOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="忌口人群" prop="notRecIds">
        <el-select
          v-model="queryParams.notRecIds"
          multiple
          placeholder="请选择体征"
        >
          <el-option
            v-for="dict in physicalSignsOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="推荐人群" prop="recIds">
        <el-select
          v-model="queryParams.recIds"
          multiple
          placeholder="请选择体征"
        >
          <el-option
            v-for="dict in physicalSignsOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="审核状态" prop="reviewStatus">
        <el-select
          v-model="queryParams.reviewStatus"
          placeholder="请选择审核状态"
          clearable
        >
          <el-option
            v-for="dict in reviewStatusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="cyan"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索
        </el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置
        </el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['custom:ingredient:add']"
          >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['custom:ingredient:export']"
          >导出
        </el-button>
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="ingredientList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="审核状态" align="center" width="80">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.reviewStatus === 'yes' ? 'success' : 'danger'"
            disable-transitions
          >
            {{ scope.row.reviewStatus === "yes" ? "已审核" : "未审核" }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="图片" align="center" width="100">
        <template slot-scope="scope">
          <img
            width="100"
            height="auto"
            :src="scope.row.imgList[0] ? scope.row.imgList[0].previewUrl : ''"
            :alt="scope.row.imgList[0] ? scope.row.imgList[0].name : ''"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="食材名称"
        align="center"
        prop="name"
        width="120"
      />
      <el-table-column
        label="食材类别"
        align="center"
        prop="type"
        width="120"
        :formatter="typeFormat"
      />
      <el-table-column
        label="蛋白质比例(100g)"
        width="132"
        align="center"
        prop="proteinRatio"
      />
      <el-table-column
        label="脂肪比例(100g)"
        width="120"
        align="center"
        prop="fatRatio"
      />
      <el-table-column
        label="碳水比例(100g)"
        width="120"
        align="center"
        prop="carbonRatio"
      />
      <el-table-column label="每百克热量(千卡)" align="center" width="120">
        <template slot-scope="scope">
          {{
            (
              scope.row.proteinRatio * 4 +
              scope.row.fatRatio * 9 +
              scope.row.carbonRatio * 4
            ).toFixed(1)
          }}
        </template>
      </el-table-column>
      <el-table-column
        label="地域"
        align="center"
        prop="area"
        :formatter="areaFormat"
      />
      <el-table-column
        label="忌口人群"
        align="center"
        prop="notRec"
        width="120"
      >
        <template slot-scope="scope">
          <autohideinfo :data="string2Arr(scope.row.notRec)" />
        </template>
      </el-table-column>
      <el-table-column label="推荐人群" align="center" prop="rec" width="120">
        <template slot-scope="scope">
          <autohideinfo :data="string2Arr(scope.row.rec)" />
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['custom:ingredient:edit']"
            >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:ingredient:remove']"
            >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改食材对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="720px" append-to-body>
      <el-row :gutter="8" style="height: 620px; overflow: auto">
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-col :span="8">
            <el-form-item label="食材名称" prop="name" label-width="90px">
              <el-input v-model="form.name" placeholder="请输入名称" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="食材类别" prop="type" label-width="100px">
              <el-select v-model="form.type" placeholder="请选择类别">
                <el-option
                  v-for="dict in typeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="地域" prop="area" label-width="100px">
              <el-select v-model="form.area" placeholder="请选择地域">
                <el-option
                  v-for="dict in areaOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="蛋白质/100g"
              prop="proteinRatio"
              label-width="100px"
            >
              <el-input
                v-model="form.proteinRatio"
                placeholder="蛋白质比例"
                width="80px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="脂肪/100g" prop="fatRatio" label-width="90px">
              <el-input v-model="form.fatRatio" placeholder="脂肪比例" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="碳水/100g"
              prop="carbonRatio"
              label-width="90px"
            >
              <el-input v-model="form.carbonRatio" placeholder="碳水比例" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="忌口人群" label-width="76px">
              <el-select
                v-model="form.notRecIds"
                multiple
                placeholder="请选择体征"
              >
                <el-option
                  v-for="dict in physicalSignsOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="推荐人群" label-width="76px">
              <el-select
                v-model="form.recIds"
                multiple
                placeholder="请选择体征"
              >
                <el-option
                  v-for="dict in physicalSignsOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="食材图片" prop="imgList">
              <el-upload
                drag
                :auto-upload="true"
                :headers="{ Authorization: 'Bearer ' + token }"
                :limit="5"
                :multiple="true"
                :file-list="form.imgList"
                :action="actionUrl"
                accept="image/jpeg,image/png"
                :on-success="handleOnUploadSuccess"
                :on-exceed="handleOnUploadExceed"
                :on-remove="handleOnUploadRemove"
                :on-preview="handleOnUploadPreview"
                list-type="picture"
              >
                <em class="el-icon-upload" />
                <div class="el-upload__text">
                  将文件拖到此处，或<em>点击上传</em>
                  <div style="font-size: 12px; color: #8c8c8c">
                    最多可上传5个文件，且每个文件不超过10M
                  </div>
                </div>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="审核状态" prop="reviewStatus">
              <el-select
                style="position: absolute"
                v-model="form.reviewStatus"
                placeholder="请选择审核状态"
                clearable
              >
                <el-option
                  v-for="dict in reviewStatusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="介绍" prop="info">
              <el-input
                :rows="5"
                v-model="form.info"
                type="textarea"
                placeholder="请输入内容"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input
                :rows="3"
                v-model="form.remark"
                type="textarea"
                placeholder="请输入内容"
              />
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 预览弹窗 -->
    <el-dialog
      :visible.sync="previewVisible"
      :title="previewTitle"
      class="preview_dialog_wrapper"
    >
      <div class="preview_content">
        <img :src="previewUrl" alt="" class="preview_img" />
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addIngredient,
  delIngredient,
  exportIngredient,
  getIngredient,
  listIngredient,
  updateIngredient,
} from "@/api/custom/ingredient";

import AutoHideInfo from "@/components/AutoHideInfo";
import FileUpload from "@/components/FileUpload";
import { getToken } from "@/utils/auth";

import { listPhysicalSigns } from "@/api/custom/physicalSigns";

export default {
  name: "Ingredient",
  components: {
    autohideinfo: AutoHideInfo,
    FileUpload,
  },
  data() {
    return {
      // 遮罩层
      loading: true,

      //
      actionUrl: process.env.VUE_APP_BASE_API + "/custom/fileUpload/ingredient",
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
      // 食材表格数据
      ingredientList: [],
      // 审核状态
      reviewStatusOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 食材类别字典
      typeOptions: [],
      // 地域字典
      areaOptions: [],
      //
      physicalSignsOptions: [],
      //
      token: getToken(),
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        type: null,
        area: null,
        notRecIds: null,
        recIds: null,
        reviewStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      //
      previewVisible: false,
      previewUrl: "",
      previewTitle: "",
    };
  },
  created() {
    this.getList();
    this.getDicts("cus_ing_type").then((response) => {
      this.typeOptions = response.data;
    });
    this.getDicts("cus_area").then((response) => {
      this.areaOptions = response.data;
    });
    this.getDicts("cus_review_status").then((response) => {
      this.reviewStatusOptions = response.data;
    });
    listPhysicalSigns().then((response) => {
      this.physicalSignsOptions = response.rows.map((obj) => ({
        dictLabel: obj.name,
        dictValue: obj.id,
      }));
    });
  },
  methods: {
    /** 查询食材列表 */
    getList() {
      this.loading = true;
      listIngredient(this.queryParams).then((response) => {
        this.ingredientList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 食材类别字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
    },
    // 地域字典翻译
    areaFormat(row, column) {
      return this.selectDictLabel(this.areaOptions, row.area);
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
        name: null,
        type: null,
        proteinRatio: null,
        fatRatio: null,
        carbonRatio: null,
        area: null,
        notRecIds: [],
        recIds: [],
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        imgList: [],
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加食材";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getIngredient(id).then((response) => {
        this.form = response.data;
        this.form.notRecIds = this.form.notRec
          ? this.form.notRec
              .split(",")
              .map(
                (label) =>
                  this.physicalSignsOptions.find(
                    (pObj) => pObj.dictLabel === label
                  ).dictValue
              )
          : [];
        this.form.recIds = this.form.rec
          ? this.form.rec
              .split(",")
              .map(
                (label) =>
                  this.physicalSignsOptions.find(
                    (pObj) => pObj.dictLabel === label
                  ).dictValue
              )
          : [];
        this.form.imgList = this.form.imgList.map((obj) => ({
          url: obj.previewUrl,
          name: obj.name,
          fileUrl: obj.url,
        }));
        this.open = true;
        this.title = "修改食材";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          const params = JSON.parse(JSON.stringify(this.form));
          params.imgList = params.imgList.reduce((arr, cur) => {
            if (cur.url) {
              arr.push({
                url: cur.fileUrl,
                name: cur.name,
              });
            }
            return arr;
          }, []);
          if (params.id != null) {
            updateIngredient(params).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addIngredient(params).then((response) => {
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
      this.$confirm('是否确认删除食材编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delIngredient(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有食材数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportIngredient(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    string2Arr(str) {
      return str ? str.split(",") : [];
    },
    handleOnUploadSuccess(res, file, fileList) {
      this.form.imgList = fileList.map((data) => {
        const { name, url, fileUrl, response } = data;
        if (response) {
          return {
            url: response.previewUrl,
            fileUrl: response.fileUrl,
            name: response.fileName,
          };
        }
        return { url, name, fileUrl };
      });
      // console.log({
      //   res,
      //   file,
      //   fileList,
      //   form: this.form,
      // });
    },
    handleOnUploadRemove(file, fileList) {
      this.form.imgList = fileList.map(({ url, fileUrl, name }) => ({
        url,
        name,
        fileUrl,
      }));
      // console.log({ file, fileList, form: this.form });
    },
    handleOnUploadExceed(files, fileList) {
      this.$message({
        message: "最多可上传5张图片",
        type: "warning",
      });
    },
    handleOnUploadPreview(file) {
      console.log(file);
      this.previewTitle = file.name;
      this.previewVisible = true;
      this.previewUrl = file.url;
    },
  },
};
</script>
<style lang="scss" scoped>
.preview_dialog_wrapper {
  .preview_content {
    text-align: center;
    .preview_img {
      max-height: 600px;
      width: auto;
    }
  }
}
</style>
