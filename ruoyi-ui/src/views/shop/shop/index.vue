<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="平台类型" prop="shopTypeId">
        <el-input
          v-model="queryParams.shopTypeId"
          placeholder="请输入平台类型"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="店铺名称" prop="shopName">
        <el-input
          v-model="queryParams.shopName"
          placeholder="请输入店铺名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createDate">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.createDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="修改时间" prop="modifyDate">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.modifyDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择修改时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
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
          v-hasPermi="['shop:shop:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['shop:shop:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['shop:shop:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['shop:shop:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="shopList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="平台类型" align="center" prop="shopType.shortName" />
      <el-table-column label="店铺名称" align="center" prop="shopName" />
      <el-table-column label="创建时间" align="center" prop="createDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="修改时间" align="center" prop="modifyDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.modifyDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-refresh-right"
            @click="handleUpdateAuth(scope.row)"
            v-hasPermi="['shop:shop:auth_url']"
          >重新授权</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['shop:shop:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['shop:shop:remove']"
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

    <!-- 添加或修改电商店铺对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="平台类型" prop="shopTypeId">
          <el-select v-model="form.shopTypeId" placeholder="请选择">
            <el-option
              v-for="item in shopTypes"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="店铺名称" prop="shopName">
          <el-input v-model="form.shopName" placeholder="请输入店铺名称" />
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
import { listShop, getShop, authUrl, delShop, addShop, updateShop, exportShop } from "@/api/shop/shop";

export default {
  name: "Shop",
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
      // 总条数
      total: 0,
      // 电商店铺表格数据
      shopList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        shopTypeId: undefined,
        shopName: undefined,
        createDate: undefined,
        modifyDate: undefined
      },
	  // 平台类型
	  shopTypes: [],
	  // 授权地址
	  auth_url:'',
	  // 授权码
	  auth_code:'',
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        shopTypeId: [
          { required: true, message: "平台类型不能为空", trigger: "blur" }
        ],
        shopName: [
          { required: true, message: "店铺名称不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询电商店铺列表 */
    getList() {
      this.loading = true;
      listShop(this.queryParams).then(response => {
        this.shopList = response.rows;
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
        id: undefined,
        adminId: undefined,
        shopTypeId: undefined,
        shopName: undefined,
        shopData: undefined,
        createDate: undefined,
        modifyDate: undefined
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
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
		this.reset();
		getShop().then(response => {
		  this.shopTypes = response.shopTypes;
		  this.open = true;
			this.title = "添加电商店铺";
		});
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getShop(id).then(response => {
		this.shopTypes = response.shopTypes;
        this.form = response.data;
        this.open = true;
        this.title = "修改电商店铺";
      });
    },
    /** 重新授权按钮操作 */
    handleUpdateAuth(row) {
	  var that = this;
	  that.auth_code = '';
      const id = row.id || that.ids;
      const shopTypeId = row.shopTypeId;
	  const platform = row.shopType.shortName;
      authUrl(shopTypeId).then(response => {
        that.auth_url = response.go_auth_url;
		that.loading = true;
		var wi = '',
		windowWidth = window.screen.width * 0.7,
		windowHeight  = window.screen.height  * 0.6;
		if(platform === 'joom'|| platform === 'shopee'|| platform === 'dh'|| platform === 'wish'|| platform === 'lazada'|| platform === 'ebay' || platform === 'mercado'){
			wi = window.open('about:blank', 'erpAuth', 'width='+windowWidth+',height='+windowHeight+',toolbar=no,location=no,directories=no,status=no,menubar=yes,scrollbars=yes');
		}else {
			wi = window.open('about:blank','_blank');
		}
		wi.location.href = that.auth_url;
		var loop = setInterval(function() {
			const wi_a = wi.location.href;
			console.log(wi_a);
		    if (wi.closed) {
		        clearInterval(loop);
		        that.loading = false;
		    }
			if(platform == 'wish' && wi_a.indexOf("code") > -1){
				clearInterval(loop);
				that.loading = false;
				const code = that.getUrlKey(wi_a,'code');
				that.auth_code = code;
				console.log(code);
				wi.close();
				that.saveAuth(id,shopTypeId,code);
			}
		}, 1000);
		
      });
    },
	saveAuth: function (id,shopTypeId,auth_code){		console.log('---'+auth_code);
		if(auth_code){
			var up_data = {'id':id,'shopTypeId':shopTypeId,'shopData':'{"code":"'+auth_code+'"}'};
			console.log(up_data);
			updateShop(up_data).then(response => {
				if (response.code === 200) {this.msgSuccess("重新授权成功");}
			});
		}
	},
	getUrlKey: function (href,name) {
	    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(href) || [, ""])[1].replace(/\+/g, '%20')) || null
	},
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateShop(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addShop(this.form).then(response => {
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
      this.$confirm('是否确认删除电商店铺编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delShop(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有电商店铺数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportShop(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>