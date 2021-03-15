<template>
  <div class="app-container">
    <div v-if="!open">
      <el-row :gutter="20">
        <el-col :span="4">
          <dept-tree @selectedDept="handleNodeClick"
          >
          </dept-tree>
        </el-col>
        <el-col :span="20">
          <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="100px">
            <div v-for="(item, index) in searchFields" :key="index" style="display: inline-block">
              <el-form-item :label="item.tableFieldName" :prop="item.vModel">
                <el-input v-model="queryParams[item.vModel]" size="small" @keyup.enter.native="handleQuery" clearable/>
              </el-form-item>
            </div>
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
                v-hasPermi="['system:metadata:add']"
              >新增
              </el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="success"
                icon="el-icon-edit"
                size="mini"
                :disabled="single"
                @click="handleUpdate"
                v-hasPermi="['system:metadata:edit']"
              >修改
              </el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                :disabled="multiple"
                @click="handleDelete"
                v-hasPermi="['system:metadata:remove']"
              >删除
              </el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="primary"
                icon="el-icon-circle-check"
                size="mini"
                :disabled="multiple"
                @click="handleTransfer"
                v-hasPermi="['system:metadata:transfer']"
              >移交
              </el-button>
            </el-col>
            <el-col :span="1.5">
              <el-dropdown>
                <el-button type="primary" size="mini" @click="handleExport">
                  导入导出
                </el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item @click.native="handleExportItem">
                    导出Excel
                  </el-dropdown-item>
                  <el-dropdown-item @click.native="handleExportAndEle">
                    导出Excel和原文
                  </el-dropdown-item>
                  <el-dropdown-item @click.native="handleImport">
                    数据导入
                  </el-dropdown-item>
                  <el-dropdown-item @click.native="handleExportField">
                    导出字段模板
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </el-col>

            <el-col :span="1.5">
              <el-dropdown>
                <el-button plain size="mini">
                  批量处理<i class="el-icon-arrow-down el-icon--right"></i>
                </el-button>
                <el-dropdown-menu>
                  <el-dropdown-item @click.native="batchHandleIncrease">批量增加</el-dropdown-item>
                  <el-dropdown-item @click.native="handleChooseData">批量修改</el-dropdown-item>
                  <el-dropdown-item @click.native="batchHandleUpdate">批量替换</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>

            </el-col>
            <!--批量处理组件-->
            <replace @updateTableData="getList"  ref="replaceForm"></replace>
            <increase @updateTableData="getList"  ref="increaseForm"></increase>

            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
          </el-row>

          <el-table v-loading="loading" :data="metadataList" @selection-change="handleSelectionChange"
                    @sort-change="handleSort">
            <el-table-column type="selection" width="55" align="center"/>
            <div v-for="(item,index) in tableTitle" :key="index">
              <el-table-column
                :label="item.tableFieldName"
                :width="item.width"
                sortable="custom"
                :prop="item.vModel">
              </el-table-column>
            </div>

            <el-table-column label="操作" align="center" width="150" fixed="right" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['system:metadata:edit']"
                >修改
                </el-button>
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDelete(scope.row)"
                  v-hasPermi="['system:metadata:remove']"
                >删除
                </el-button>
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
        </el-col>
      </el-row>
    </div>
    <el-dialog :visible.sync="upload" v-if="upload" title="数据导入" width="30%">
      <span style="color:red">请选择zip或excel</span>
      <uploads :url="uploadUrl">
      </uploads>
    </el-dialog>
    <InputView v-if="open" :parent-id="queryParamsInner.parentId" :deptid="deptId" :node-id="nodeId"
               :metadata="metadata" :metadataid="id" :type="type" :formconf="formConf" @close="cancel"/>


 <!--   <el-dialog title="批量修改" :visible.sync="BatchEditDialog"  :modal-append-to-body='false'>
     <el-card class="box-card">
        <div slot="header" class="clearfix">
          <el-row style="margin-bottom: 10px">
            <div>
              <el-input placeholder="请输入修改内容"  v-model="field">
                <el-select placeholder="请选择" slot="prepend" v-model="selectField" style="width: 200px">
                  <el-option v-for="(item,index) in tableTitle" :key="index"
                             :label="item.tableFieldName" :value="item.id"></el-option>
                </el-select>
                <el-button slot="append" @click.native="handleAddChangeData()" size="mini">添加</el-button>
              </el-input>
            </div>
          </el-row>
          <el-row style="margin-bottom: 10px">
            <el-checkbox label="替换值是否为空" v-model="checked"></el-checkbox>
          </el-row>
          <el-row style="margin-bottom: 10px">
            <div>
              <el-button size="medium" style="width: 100px" type="primary" @click.native="deleteChooseDate">删除修改</el-button>
              <el-button size="medium" style="width: 100px" type="info" @click.native="clearField">清 除</el-button>
              <el-button size="medium" style="width: 100px" type="warning" @click.native="previewData">预 览</el-button>
            </div>
          </el-row>
        </div>

        <div slot="default">
          <el-table border :data="changeFieldTable"  @selection-change="handleDeleteOne">
            <el-table-column type="selection" ></el-table-column>
            <el-table-column label="字段编码" prop="vModel" align="center"></el-table-column>
            <el-table-column label="字段名称" prop="tableFieldName" align="center"></el-table-column>
            <el-table-column label="字段值"  prop="changeValue" align="center"></el-table-column>
          </el-table>
        </div>
      </el-card>
      <batchModify :selectData="selectData" :tableTitle="tableTitle" :batchEditDialog="BatchEditDialog"></batchModify>
      <span slot="footer" class="dialog-footer">
        <el-button @click="BatchEditDialog= false">取 消</el-button>
        <el-button type="primary" @click="handleBatchUpdate">修改</el-button>
      </span>
    </el-dialog>-->
    <batchModify :selectData="selectData" :tableTitle="tableTitle" :BatchEditDialog="BatchEditDialog"
                 v-on:getList="getList"
                 v-on:closeBatchModify="closeBatchModify"></batchModify>
    <InputView v-if="open" :deptid="deptId" :node-id="nodeId" :metadata="metadata" :metadataid="id" :type="type"
               :formconf="formConf" @close="cancel"/>
    <el-dialog :visible.sync="importS" v-if="importS" title="预览" width="75%">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-table v-loading="leftImportLoading" ref="leftTable" :data="leftTableData" style="height:510px ;overflow-y: scroll">
            <el-table-column
              prop="src"
              label="来源"
              width="140">
            </el-table-column>
            <el-table-column
              label="目的"
              width="165"
            >
              <template slot-scope="scope">
                <el-select v-model="scope.row.dest" clearable  @change="handleDestChange">
                  <el-option
                    v-for="(item,index) in tableFieldsPick"
                    :key="index"
                    :label="item.label"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
        <el-col :span="18">
          <el-table v-loading="rightImportLoading" ref="rightTable" :key="new Date().getTime()" :data="rightTableData" style="height:510px;overflow-y: scroll">
            <div v-for="(item,index) in tableFields" :key="index">
              <el-table-column
                :label="item.tableFieldName"
                :width="item.width"
                sortable="custom"
                :prop="item.vModel">
              </el-table-column>
            </div>
          </el-table>

        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitUploadHandle" >确 定</el-button>
        <el-button @click="cancelUploadHandle">取 消</el-button>
      </div>
    </el-dialog>

    		 <!-- 移交对话框 -->
    <el-dialog :title="title" :visible.sync="open1" width="600px" append-to-body>
      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="移交说明">
              <el-input v-model="form.remark" type="textarea" placeholder="XX部门移交XX档案"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="移交人" prop="transuser">
              <el-input v-model="form.transuser" placeholder="移交人" readonly="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数量" prop="transcount">
               <el-input v-model="form.transcount" placeholder="数量" readonly="true"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="移交部门" prop="transdep">
              <el-input v-model="form.transdep" placeholder="请输入移交部门" readonly="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="移交时间" prop="transTime">
              <el-input v-model="form.transTime" placeholder="请输入移交时间" readonly="true"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitTransferForm">确 定</el-button>
        <el-button @click="cancel1">取 消</el-button>
      </div>
    </el-dialog>

    <InputView v-if="open" :parent-id="queryParamsInner.parentId" :deptid="deptId" :node-id="nodeId"
               :metadata="metadata" :metadataid="id" :type="type" :formconf="formConf" @close="cancel"/>
  </div>
</template>

<script>
  import {
    addMetadata,
    delMetadata,
    exportMetadata,
    exportMetadataField,
    exportMetadataItem,
    exportMetadataItemAndEle,
    listMetadata,
    updateMetadata, uploadHandle,
    getTransInfo,transfer,addTransfer
  } from '@/api/system/metadata'
  import DeptTree from '@/views/components/deptTree'
  import InputView from '@/views/system/metadata/InputView'
  import { listJson} from '@/api/system/json'
  import {listMetadataRule} from '@/api/system/metadataRule'
  import {deepClone} from '@/utils'
  import Uploads from '@/views/components/Uploads'
  import { listTemplates } from '@/api/basic/templates'
  import replace from '@/views/system/metadata/replace'
  import increase from '@/views/system/metadata/increase'
  import batchModify from "./batchModify";

  var _ = require('lodash')
  export default {
    name: "Metadata",
    components: {
      DeptTree,
      InputView,
      Uploads,
      replace,
      increase,
      batchModify
    },
    data() {
      return {
        searchFields:[],
        tableTitle: [],
        upload: false,
        uploadUrl: process.env.VUE_APP_BASE_API + '/system/metadata/fileUpload',
        metadata: {},
        id: 0,
        type: 'create',
        data: {},
        definition: [],
        formConf: {},
        formReserve: {},
        //默认部门ID
        deptId: 100,
        nodeId: 0,
        //部门名称
        deptName: undefined,
        // 部门树选项
        deptOptions: undefined,
        defaultProps: {
          children: "children",
          label: "label"
        },
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: false,
        // 总条数
        total: 0,
        // 文书类基本元数据表格数据
        metadataList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        //移交显示弹出层
        open1:false,
        entryids:[],
        // 聚合层次字典
        aggregationLevelOptions: [],
        // 查询参数
        queryParams: {
          deptId: null,
          parentId:0,
          pageNum: 1,
          pageSize: 10,
          aggregationLevel: '文件',
          archivesName: null,
          archivesIdentifier: null,
          fondsName: null,
          fondsConstitutingUnitName: null,
          electronicRecordCode: null,
          archivalCode: null,
          fondsIdentifier: null,
          catalogueNumber: null,
          year: null,
          retentionPeriod: null,
          organizationalStructureOrFunction: null,
          categoryCode: null,
          agencyFileNumber: null,
          archivesFileNumber: null,
          agencyItemNumber: null,
          archivesItemNumber: null,
          documentSequenceNumber: null,
          pageNumber: null,
          contentDescription: null,
          title: null,
          parallelTitle: null,
          alternativeTitle: null,
          otherTitleInformation: null,
          descriptor: null,
          keyword: null,
          personalName: null,
          abstract: null,
          classCode: null,
          documentNumber: null,
          author: null,
          date: null,
          documentType: null,
          precedence: null,
          principalReceiver: null,
          otherReceivers: null,
          securityClassification: null,
          secrecyPeriod: null,
          formalCharacteristics: null,
          documentAggregationType: null,
          totalNumberOfItems: null,
          totalNumberOfPages: null,
          language: null,
          manuscriptType: null,
          annotation: null
        },
        // 表单参数
        form: {},

        //批量修改弹窗
        BatchEditDialog:false,
        selectData:[],
      };
    },
    created() {
      //this.getList();
      //this.loadForm();
      this.getDicts("ws_al").then(response => {
        this.aggregationLevelOptions = response.data;
      });
    },

    methods: {
      handleExportItem() {
        if (this.ids.length !== 0) {
          exportMetadataItem(this.ids).then(res => {
            this.download(res.msg);
          })
        } else {
          this.$message.error('请选择至少一条数据')
        }
      },
      handleExportAndEle() {
        if (this.ids.length !== 0) {
          exportMetadataItemAndEle(this.ids).then(res => {
            this.download(res.msg);
          })
        } else {
          this.$message.error('请选择至少一条数据')
        }
      },
      handleImport() {
        this.upload = true;
      },
      handleExportField() {
        exportMetadataField().then(res => {
          this.download(res.msg)
        })
      },
      // 节点单击事件
      handleNodeClick(data) {
        this.queryParams.deptId = data;
        this.deptId = data
        this.getList();
        this.loadForm()
      },
      /** 查询文书类基本元数据列表 */
      getList() {
        let fullPath = this.$route.fullPath
        this.queryParams.nodeId = fullPath.substring(fullPath.lastIndexOf('/') + 1)
        this.nodeId = this.queryParams.nodeId
        this.loading = true;
        listMetadata(this.queryParams).then(response => {
          this.metadataList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 聚合层次字典翻译
      aggregationLevelFormat(row, column) {
        return this.selectDictLabel(this.aggregationLevelOptions, row.aggregationLevel);
      },
      cancel() {
        this.open = false;
        this.single = true;
        //this.reset();
      },
      // 表单重置
      reset() {
        this.formConf = this.formReserve
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
      handleSort(data) {
        this.queryParams.sortField = data.prop
        this.queryParams.orderBy = data.order
        this.getList();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.selectData=selection;
        this.ids = selection.map(item => item.id)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      loadForm() {
        let fullPath = this.$route.fullPath
        let id = fullPath.substring(fullPath.lastIndexOf('/') + 1)
        let query = {nodeId: id, deptId: this.deptId}
        listTemplates(query).then(res => {
          this.tableTitle = res.rows.filter(item =>item.tableFieldFlag)
          this.searchFields = res.rows.filter(item=>item.tableSearchFlag)
        })
        listJson(query).then(res => {
          this.formConf = JSON.parse(res.rows[0].formData)
          this.formReserve = deepClone(this.formConf)
        }).catch(err => {
          console.log(err)
        })
        listMetadataRule().then(res => {
          window.sessionStorage.setItem('WS', escape(JSON.stringify(res.rows)))
        })
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.type = 'create'
        this.open = true;
        this.formConf = this.formReserve
        this.title = "添加文书类基本元数据";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        // this.reset();
        const id = row.id || this.ids
        this.id = id[0];
        let data = this.metadataList.filter(item => {
          return item.id === this.id
        })
        this.metadata = data[0]
        this.type = 'modify'
        this.open = true;
      },
       /** 移交按钮操作 */
      handleTransfer(row) {
        const ids = row.id || this.ids;
        this.entryids=ids;
        console.log(this.entryids);
        this.open1=true;
        this.title="移交";
        getTransInfo(ids).then(response => {
          this.form = response.data;
        });
      },
      cancel1() {
      this.open1 = false;
      this.single = true;
      },
      /**提交移交信息 */
      submitTransferForm() {
        this.$confirm('是否移交到档案管理员?', '确认信息', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          delMetadata(this.entryids);
          addTransfer(this.form).then(response => {
              this.msgSuccess("移交成功");
              this.open1 = false;
              this.getList();
            });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消移交'
          });
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateMetadata(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addMetadata(this.form).then(response => {
                this.msgSuccess("新增成功");
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
        this.$confirm('是否确认删除文书类基本元数据编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delMetadata(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有文书类基本元数据数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportMetadata(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
      },
      /**打开批量修改窗口**/
      handleChooseData(){
        if (this.selectData.length==0){
          this.$confirm("请选择记录");
        }else{
          this.BatchEditDialog=true;
        }
      },
      /** 批量替换按钮操作 */
      batchHandleUpdate() {
        if (this.selectData.length==0){
          this.$confirm("请选择记录");
        }else{
          this.$refs.replaceForm.open(this.selectData,this.tableTitle);
        }
      },

      /** 批量增加按钮操作 */
      batchHandleIncrease() {
        if (this.selectData.length==0){
          this.$confirm("请选择记录");
        }else{
          this.$refs.increaseForm.open(this.selectData,this.tableTitle);
        }
      },
      closeBatchModify(){
        this.BatchEditDialog=false;
      }
    },
    watch: {
      formconf: function (nv, ov) {
        console.log(nv, ov)
      }
    }
  };
</script>
