<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="案例名称" prop="caseName">
        <el-input
          v-model="queryParams.caseName"
          placeholder="请输入案例名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item v-show="false" label="案例分类id" prop="caseClassificationId">
        <el-input
          v-model="queryParams.caseClassificationId"
          placeholder="请输入案例分类id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="案例分类名称" label-width="100px" prop="caseClassificationName">
        <el-input 
          v-model="queryParams.caseClassificationName"
          placeholder="请输入案例分类名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="案例编号" prop="caseNumber">
        <el-input
          v-model="queryParams.caseNumber"
          placeholder="请输入案例编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发布人" prop="publisher">
        <el-input
          v-model="queryParams.publisher"
          placeholder="请输入发布人"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:case:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:case:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:case:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:case:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="caseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!--<el-table-column label="主键" align="center" prop="id" />-->
      <el-table-column label="案例名称" align="center" prop="caseName" />
      <!--<el-table-column label="案例分类id" align="center" prop="caseClassificationId" />-->
      <el-table-column label="案例分类名称" align="center" prop="caseClassificationName" />
      <el-table-column label="案例编号" align="center" prop="caseNumber" />
      <el-table-column label="案例简要说明" align="center" prop="briefExplanationCase" />
      <el-table-column label="其它附件上传" align="center" prop="uploadOtherAttachments" />
      <el-table-column label="发布人" align="center" prop="publisher" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:case:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:case:remove']"
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

	
	
	
	
    <!-- 添加或修改典型案例对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="案例名称" prop="caseName">
          <el-input v-model="form.caseName" placeholder="请输入案例名称" />
        </el-form-item>
		
		
		
		
		
		
		<el-form-item label="案例分类" prop="caseClassificationId">
          <treeselect
            v-model="form.caseClassificationId"
            :options="classificationList"
            :normalizer="normalizer"
            @input="handleInput"
			@select="handleSelect"
            placeholder="请选择"
          />
        </el-form-item>
        <el-form-item hidden label="案例分类名称" prop="caseClassificationName">
          <el-input v-model="form.caseClassificationId" placeholder="请输入案例分类名称" />
        </el-form-item>
		
		
		
		
		
		
        <!--<el-form-item label="案例分类" prop="caseClassificationName">
          <el-input v-model="form.caseClassificationName" placeholder="请输入案例分类名称"  >
			<template>
				<i slot="suffix" class="el-icon-search" @click="openCaseClassDialogHandleClick"></i>
			</template>
          </el-input>
		  
		  <el-popover
		  	  title="请选择案例分类"
		  	  v-model="dialogVisible"
		  	  width="760px"
		  	  >
			  <div>
				
				<el-tree ref="classificationTree" show-checkbox :data="classificationList" node-key="id" :default-expanded-keys="[2,3]" 
				:default-checked-keys="[5]" :props="defaultProps">
					<span class="custom-tree-node" slot-scope="{node,data}">
						<span @dblclick="handleNodeClick(node,data)" class="node">
							{{ node.label }}
						</span>
						
					</span>
				</el-tree>
			  </div>
		  	  <span>
		  	    <el-button @click="dialogVisible = false">取 消</el-button>
		  	    <el-button type="primary" @click="handlePopoverClick">确 定</el-button>
		  	  </span>
		  	</el-popover>
          
        </el-form-item>-->
        <el-form-item label="案例编号" prop="caseNumber">
          <el-input v-model="form.caseNumber" placeholder="请输入案例编号" />
        </el-form-item>
        <el-form-item label="案例简要说明">
          <editor v-model="form.briefExplanationCase" :min-height="192"/>
        </el-form-item>
        <el-form-item label="其它附件上传" prop="uploadOtherAttachments">
          <file-upload v-model="form.uploadOtherAttachments"/>
        </el-form-item>
        <el-form-item label="发布人" prop="publisher">
          <el-input v-model="form.publisher" placeholder="请输入发布人" />
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

import {listClassification} from '../../。。/../../api/system/classification';
import { listCase, getCase, delCase, addCase, updateCase } from "@/api/system/case";

import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import IconSelect from "@/components/IconSelect";


export default {
  name: "Case",
  dicts: ['sys_yes_no'],
  components: { Treeselect, IconSelect },
  data() {
    return {
		
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedDiCaseClassification: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 典型案例表格数据
      caseList: [],
      // 案例分类表格数据
      diCaseClassificationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
	  dialogVisible: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        caseName: null,
        caseClassificationId: null,
        caseClassificationName: null,
        caseNumber: null,
        briefExplanationCase: null,
        uploadOtherAttachments: null,
        publisher: null
      },
	  classificationList: [],
	  queryParams1: {
	          classificationName: null,
	          classificationLabels: null,
	          isEnabled: null,
	          parentId: null,
	          imageIcon: null,
	          description: null
      },
	 
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        caseNumber: [
          { required: true, message: "案例编号不能为空", trigger: "blur" }
        ],
        briefExplanationCase: [
          { required: true, message: "案例简要说明不能为空", trigger: "blur" }
        ],
        uploadOtherAttachments: [
          { required: true, message: "其它附件上传不能为空", trigger: "blur" }
        ],
      },
	  
	  defaultProps: {
	     children: 'children',
	     label: 'classificationName'
	   },
	  numTime: 0,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询典型案例列表 */
    getList() {
      this.loading = true;
      listCase(this.queryParams).then(response => {
        this.caseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
	
	handleInput(selectItems){
		this.form.caseClassificationId=selectItems;
	},
	handleSelect(selectItems){
		console.log("selectItems",selectItems);
		this.form.caseClassificationName=selectItems.classificationName;
	},
	/** 转换菜单数据结构 */
    normalizer(node) {
		console.log("node",node)
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.id,
        label: node.classificationName,
        children: node.children
      };
    },
	/** 查询菜单下拉树结构 */
    getTreeselect() {
		
		listClassification(this.queryParams1).then(response => {
			this.classificationList=[];
			this.classificationList=this.handleTree(response.data, "id", "parentId");
      	});	
		
      
    },
	openCaseClassDialogHandleClick(){
		this.dialogVisible = true
		debugger;
		this.getClassification();
	},
	getClassification(){
		debugger;
		
		listClassification(this.queryParams1).then(response => {
	        this.classificationList = this.handleTree(response.data, "id", "parentId");
			console.log("this.classificationList",this.classificationList)
	        this.loading = false;
	      });
		
	},
	handleNodeClick(node,data) {
		
		
		this.dialogVisible = false;
		this.form.caseClassificationId=data.id;
		this.form.caseClassificationName=data.classificationName;
	},
	handlePopoverClick(){
		var data = this.$refs.classificationTree.getCheckedNodes();
		
		console.log("data",data)
		this.form.caseClassificationId=data.id;
		this.form.caseClassificationName=data.classificationName;
		this.dialogVisible = false;
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
        caseName: null,
        caseClassificationId: null,
        caseClassificationName: null,
        caseNumber: null,
        briefExplanationCase: null,
        uploadOtherAttachments: null,
        publisher: null
      };
      this.diCaseClassificationList = [];
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
	  this.getTreeselect();
      this.open = true;
      this.title = "添加典型案例";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCase(id).then(response => {
        this.form = response.data;
        this.diCaseClassificationList = response.data.diCaseClassificationList;
        this.open = true;
        this.title = "修改典型案例";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.diCaseClassificationList = this.diCaseClassificationList;
          if (this.form.id != null) {
            updateCase(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCase(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除典型案例编号为"' + ids + '"的数据项？').then(function() {
        return delCase(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
	/** 案例分类序号 */
    rowDiCaseClassificationIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 案例分类添加按钮操作 */
    handleAddDiCaseClassification() {
      let obj = {};
      obj.classificationName = "";
      obj.isEnabled = "";
      obj.imageIcon = "";
      obj.description = "";
      this.diCaseClassificationList.push(obj);
    },
    /** 案例分类删除按钮操作 */
    handleDeleteDiCaseClassification() {
      if (this.checkedDiCaseClassification.length == 0) {
        this.$modal.msgError("请先选择要删除的案例分类数据");
      } else {
        const diCaseClassificationList = this.diCaseClassificationList;
        const checkedDiCaseClassification = this.checkedDiCaseClassification;
        this.diCaseClassificationList = diCaseClassificationList.filter(function(item) {
          return checkedDiCaseClassification.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleDiCaseClassificationSelectionChange(selection) {
      this.checkedDiCaseClassification = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/case/export', {
        ...this.queryParams
      }, `case_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
