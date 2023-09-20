<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="适用门店" prop="storeId">
        <el-select v-model="queryParams.storeId" clearable filterable>
          <el-option v-for="option in storeOptions" :key="option.id" :label="option.name" :value="option.id" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['office:storepromotion:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['office:storepromotion:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['office:storepromotion:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['office:storepromotion:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="storepromotionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="优惠券类型" align="center" prop="couponType" :formatter="couponTypeFormatter" />
      <!-- <el-table-column label="适用商户" align="center" prop="merchantId" /> -->
      <el-table-column label="适用门店" align="center" prop="storeId" :formatter="storeFormatter" />
      <el-table-column label="标准金额" align="center" prop="standardPrice" />
      <el-table-column label="优惠金额" align="center" prop="discountPrice" />
      <el-table-column label="抵扣时长" align="center" prop="maxMinute" />
      <el-table-column label="可领取次数" align="center" prop="maxHold" />
      <el-table-column label="有效期类型" align="center" prop="validType" :formatter="validTypeFormatter" />
      <el-table-column label="自购买日期有效天数" align="center" prop="validDays" />
      <el-table-column label="开始日期" align="center" prop="startDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束日期" align="center" prop="endDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="周内可用日" align="center" prop="weekDays" />
      <!--  <el-table-column label="状态" align="center" prop="status" />-->
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['office:storepromotion:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['office:storepromotion:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改优惠券 t_store_promotion对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-row>
          <el-col :span=12>
            <el-form-item label="名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入名称" />
            </el-form-item>
          </el-col>
          <el-col :span=12>
            <!-- <el-form-item label="适用商户" prop="merchantId">
              <el-input v-model="form.merchantId" placeholder="请输入适用商户 0 所有商户适用" />
            </el-form-item> -->
            <el-form-item label="适用门店" prop="storeId">
              <el-select v-model="form.storeId" filterable>
                <el-option v-for="option in storeOptions" :key="option.id" :label="option.name" :value="option.id" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span=12>
            <el-form-item label="优惠券类型" prop="couponType">
              <el-select v-model="form.couponType">
                <el-option v-for="option in dict.type.promotion_type" :label="option.label" :key="option.value"
                  :value="option.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span=12>
            <el-form-item label="抵扣时长(小时)" prop="maxMinute">
              <!-- <el-input v-model="form.maxMinute" placeholder="请输入抵扣时长" :disabled="form.couponType==1" /> -->
              <el-input-number v-model="form.maxMinute" :step="1" step-strictly :min="1" :disabled='form.cardType==1'>
              </el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span=12>
            <el-form-item label="标准金额" prop="standardPrice">
              <el-input v-model="form.standardPrice" placeholder="请输入标准金额" :disabled="form.couponType==2" />
            </el-form-item>
          </el-col>
          <el-col :span=12>
            <el-form-item label="优惠金额" prop="discountPrice">
              <el-input v-model="form.discountPrice" placeholder="请输入优惠金额" :disabled="form.couponType==2" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span=12>
            <!-- <el-form-item label="可领取次数" prop="maxHold">
              <el-input-number v-model="form.maxHold" :step="1" step-strictly :min="1" :disabled='form.cardType==2'>
              </el-input-number>
            </el-form-item> -->
            <el-form-item label="有效期类型" prop="couponType">
              <el-select v-model="form.validType">
                <el-option v-for="option in dict.type.valid_type" :label="option.label" :key="option.value"
                  :value="option.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span=12>
            <el-form-item label="自购买有效天数" prop="validDays">
              <el-input v-model="form.validDays" placeholder="请输入自购买日期有效天数" :disabled="form.validType==2" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span=12>
            <el-form-item label="开始日期" prop="startDate">
              <el-date-picker clearable v-model="form.startDate" type="date" value-format="yyyy-MM-dd"
                placeholder="请选择开始日期" :disabled="form.validType==1">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span=12>
            <el-form-item label="结束日期" prop="endDate">
              <el-date-picker clearable v-model="form.endDate" type="date" value-format="yyyy-MM-dd"
                placeholder="请选择结束日期" :disabled="form.validType==1">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span=24>
            <el-form-item label="周内可用" prop="weekDays">
              <!-- <el-input v-model="form.weekDays" placeholder="请输入周内可用日期(1,3,4,7)" /> -->
              <el-checkbox-group v-model="weekDays">
                <el-checkbox-button v-for="(week, index) in weekOptions" :label="week.value" :key="index"
                  :value="week.value">{{week.label}}
                </el-checkbox-button>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span=24>
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" placeholder="请输入备注" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="logo" prop="logo">
              <el-upload v-model="form.logo" multiple :action="uploadImgUrl" list-type="picture-card"
                :on-success="handleUploadSuccess" :before-upload="handleBeforeUpload" :limit="limit"
                :on-error="handleUploadError" :on-exceed="handleExceed" ref="imageUpload" :on-remove="handleDeletePic"
                :show-file-list="true" :headers="headers" :file-list="fileList" :on-preview="handlePictureCardPreview"
                :class="{hide: this.fileList.length >= this.limit}">
                <i class="el-icon-plus"></i>
              </el-upload>

              <!-- 上传提示 -->
              <div class="el-upload__tip" slot="tip" v-if="showTip">
                请上传
                <template v-if="fileSize"> 大小不超过 <b style="color: #f56c6c">{{ fileSize }}MB</b> </template>
                <template v-if="fileType"> 格式为 <b style="color: #f56c6c">{{ fileType.join("/") }}</b> </template>
                的文件
              </div>

              <el-dialog :visible.sync="dialogVisible" title="预览" width="800" append-to-body>
                <img :src="dialogImageUrl" style="display: block; max-width: 100%; margin: 0 auto" />
              </el-dialog>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {
    listStorepromotion,
    getStorepromotion,
    delStorepromotion,
    addStorepromotion,
    updateStorepromotion
  } from "@/api/office/storepromotion";

  import {
    listStore
  } from "@/api/office/store";

  import {
    getToken
  } from "@/utils/auth";

  export default {
    name: "Storepromotion",
    dicts: ["promotion_type", "valid_type"],
    props: {
      value: [String, Object, Array],
      // 图片数量限制
      limit: {
        type: Number,
        default: 5,
      },
      // 大小限制(MB)
      fileSize: {
        type: Number,
        default: 8,
      },
      // 文件类型, 例如['png', 'jpg', 'jpeg']
      fileType: {
        type: Array,
        default: () => ["png", "jpg", "jpeg"],
      },
      // 是否显示提示
      isShowTip: {
        type: Boolean,
        default: true
      }
    },
    data() {
      return {
        number: 0,
        baseUrl: process.env.VUE_APP_BASE_API,
        uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload", // 上传的图片服务器地址
        headers: {
          Authorization: "Bearer " + getToken(),
        },
        uploadList: [],
        fileList: [],
        dialogImageUrl: "",
        dialogVisible: false,
        hideUpload: false,
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
        // 优惠券 t_store_promotion表格数据
        storepromotionList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          name: null,
          couponType: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          name: [{
            required: true,
            message: "名称不能为空",
            trigger: "blur"
          }],
          couponType: [{
            required: true,
            message: "优惠券类型不能为空",
            trigger: "change"
          }],
          // storeId: [{
          //   required: true,
          //   message: "适用门店 0 所有门店适用不能为空",
          //   trigger: "blur"
          // }],
          // discountPrice: [{
          //   required: true,
          //   message: "优惠金额不能为空",
          //   trigger: "blur"
          // }],
          maxHold: [{
            required: true,
            message: "可领取次数不能为空",
            trigger: "blur"
          }],
        },
        weekDays: [],
        storeOptions: [],
        weekOptions: [{
          label: '周一',
          value: '1'
        }, {
          label: '周二',
          value: '2'
        }, {
          label: '周三',
          value: '3'
        }, {
          label: '周四',
          value: '4'
        }, {
          label: '周五',
          value: '5'
        }, {
          label: '周六',
          value: '6'
        }, {
          label: '周日',
          value: '7'
        }],
      };
    },
    created() {
      this.getList();
      this.getStoreOptions();
    },
    computed: {
      // 是否显示提示
      showTip() {
        return this.isShowTip && (this.fileType || this.fileSize);
      },
    },
    methods: {
      /** 查询优惠券 t_store_promotion列表 */
      getList() {
        this.loading = true;
        listStorepromotion(this.queryParams).then(response => {
          this.storepromotionList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      getStoreOptions() {
        listStore().then(response => {
          this.storeOptions = response.rows;
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
          name: null,
          couponType: null,
          merchantId: null,
          storeId: null,
          standardPrice: null,
          discountPrice: null,
          maxMinute: null,
          maxHold: null,
          validType: null,
          validDays: null,
          startDate: null,
          endDate: null,
          weekDays: null,
          status: null,
          remark: null,
          logo: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null
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
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加优惠券 t_store_promotion";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getStorepromotion(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改优惠券 t_store_promotion";
          if (response.data.logo != null && response.data.logo.length > 0) {
            var list = response.data.logo.split(',');
            this.fileList = list.map(item => {
              if (typeof item === "string") {
                if (item.indexOf(this.baseUrl) === -1) {
                  item = {
                    name: this.baseUrl + item,
                    url: this.baseUrl + item
                  };
                } else {
                  item = {
                    name: item,
                    url: item
                  };
                }
              }
              return item;
            });
          }
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.form.weekDays = this.weekDays.join(',');
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateStorepromotion(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addStorepromotion(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$modal.confirm('是否确认删除优惠券 t_store_promotion编号为"' + ids + '"的数据项？').then(function() {
          return delStorepromotion(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('office/storepromotion/export', {
          ...this.queryParams
        }, `storepromotion_${new Date().getTime()}.xlsx`)
      },
      storeFormatter(row) {
        var res = "";
        for (var i = 0; i < this.storeOptions.length; i++) {
          if (this.storeOptions[i].id == row.storeId) {
            res = this.storeOptions[i].name;
            break;
          }
        }
        return res;
      },
      validTypeFormatter(row, index) {
        return this.selectDictLabel(this.dict.type.valid_type, row.validType);
      },
      couponTypeFormatter(row, index) {
        return this.selectDictLabel(this.dict.type.promotion_type, row.couponType);
      },
       // 上传成功回调
       handleUploadSuccess(res, file) {
        if (res.code === 200) {
          this.uploadList.push({
            name: res.fileName,
            url: res.fileName
          });
          this.uploadedSuccessfully();
        } else {
          this.number--;
          this.$modal.closeLoading();
          this.$modal.msgError(res.msg);
          this.$refs.imageUpload.handleRemove(file);
          this.uploadedSuccessfully();
        }
      },
      // 上传前loading加载
      handleBeforeUpload(file) {
        let isImg = false;
        if (this.fileType.length) {
          let fileExtension = "";
          if (file.name.lastIndexOf(".") > -1) {
            fileExtension = file.name.slice(file.name.lastIndexOf(".") + 1);
          }
          isImg = this.fileType.some(type => {
            if (file.type.indexOf(type) > -1) return true;
            if (fileExtension && fileExtension.indexOf(type) > -1) return true;
            return false;
          });
        } else {
          isImg = file.type.indexOf("image") > -1;
        }

        if (!isImg) {
          this.$modal.msgError(`文件格式不正确, 请上传${this.fileType.join("/")}图片格式文件!`);
          return false;
        }
        if (this.fileSize) {
          const isLt = file.size / 1024 / 1024 < this.fileSize;
          if (!isLt) {
            this.$modal.msgError(`上传图片大小不能超过 ${this.fileSize} MB!`);
            return false;
          }
        }
        this.$modal.loading("正在上传图片，请稍候...");
        this.number++;
      },
      // 上传失败
      handleUploadError() {
        this.$modal.msgError("上传图片失败，请重试");
        this.$modal.closeLoading();
      },
      // 上传结束处理
      uploadedSuccessfully() {
        if (this.number > 0 && this.uploadList.length === this.number) {
          // debugger
          for (let i in this.uploadList) {
            if (this.uploadList[i].url && this.uploadList[i].url.indexOf(this.baseUrl) < 0) {
              this.uploadList[i].url = this.baseUrl + this.uploadList[i].url;
            }
          }

          this.fileList = this.fileList.concat(this.uploadList);
          this.uploadList = [];
          this.number = 0;
          this.$emit("input", this.listToString(this.fileList));
          this.$modal.closeLoading();
        }
      },
      // 预览
      handlePictureCardPreview(file) {
        var fileUrl = file.url.indexOf(this.baseUrl) > -1 ? file.url : this.baseUrl + file.url;
        this.dialogImageUrl = fileUrl;
        this.dialogVisible = true;
      },
      // 文件个数超出
      handleExceed() {
        this.$modal.msgError(`上传文件数量不能超过 ${this.limit} 个!`);
      },
      // 删除图片
      handleDeletePic(file) {
        const findex = this.fileList.map(f => f.name).indexOf(file.name);
        if (findex > -1) {
          this.fileList.splice(findex, 1);
          this.$emit("input", this.listToString(this.fileList));
        }
      },
      // 对象转成指定字符串分隔
      listToString(list, separator) {
        let strs = "";
        separator = separator || ",";
        for (let i in list) {
          if (list[i].url) {
            strs += list[i].url.replace(this.baseUrl, "") + separator;
          }
        }
        var fileNames = strs != '' ? strs.substr(0, strs.length - 1) : '';
        this.form.logo = fileNames; // 绑定对象属性
        return fileNames;
      },
    }
  };
</script>
