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
            <div v-for="(item, index) in tableTitle" :key="index" style="display: inline-block">
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
              <el-button
                type="danger"
                icon="el-icon-plus"
                size="mini"
                :disabled="single"
                @click="handleInsert"
                v-hasPermi="['system:metadata:remove']"
              >插件
              </el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
          </el-row>

          <el-table v-loading="loading" ref="mainTable" :data="metadataList" @row-click="rowClick"
                    @selection-change="handleSelectionChange" @sort-change="handleSort">
            <el-table-column
              type="selection"
              width="55"
              align="center"
              fixed="left"/>
            <div v-for="(item,index) in tableTitle" :key="archives">
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
            ref="pager"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            :page-sizes="[5, 10, 20, 50,100,200]"
            @pagination="getList"
          />
          <br>
          <div v-show="archiveInnerShow">
            <el-form :model="queryParamsInner" ref="queryFormInner" :inline="true" v-show="showSearchInner"
                     label-width="100px">
              <div v-for="(item, index) in tableTitle" :key="archives" style="display: inline-block">
                <el-form-item :label="item.tableFieldName" :prop="item.vModel">
                  <el-input v-model="queryParamsInner[item.vModel]" size="small" @keyup.enter.native="handleQueryInner"
                            clearable/>
                </el-form-item>
              </div>

              <el-form-item>
                <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQueryInner">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQueryInner">重置</el-button>
              </el-form-item>
            </el-form>

            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  size="mini"
                  @click="handleAddInner"
                  v-hasPermi="['system:metadata:add']"
                >新增
                </el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button
                  type="success"
                  icon="el-icon-edit"
                  size="mini"
                  :disabled="singleInner"
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
                  :disabled="multipleInner"
                  @click="handleDelete"
                  v-hasPermi="['system:metadata:remove']"
                >删除
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
              <right-toolbar :showSearch.sync="showSearchInner" @queryTable="getListInner"></right-toolbar>
            </el-row>
            <el-table v-loading="loadingInner" ref="innerTable" :data="metadataInnerList" @row-click="rowClick"
                      @selection-change="handleSelectionChangeInner" @sort-change="handleSortInner">
              <el-table-column type="selection" width="55" align="center"/>
              <div v-for="(item,index) in tableTitle" :key="archives">
                <el-table-column
                  :label="item.tableFieldName"
                  :width="item.width"
                  sortable="custom"
                  :prop="item.vModel">
                </el-table-column>
              </div>

              <el-table-column label="操作" align="center" width="150" fixed="right"
                               class-name="small-padding fixed-width">
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
              v-show="totalInner>0"
              :total="totalInner"
              :page.sync="queryParamsInner.pageNum"
              :limit.sync="queryParamsInner.pageSize"
              :page-sizes="[5, 10, 20, 50,100,200]"
              @pagination="getListInner"
            />
          </div>
        </el-col>
      </el-row>
    </div>
    <el-dialog :visible.sync="upload" v-if="upload" title="数据导入" width="30%">
      <span style="color:red">请选择zip或excel</span>
      <uploads :url="uploadUrl" :single="true" @success="uploadSuccess">
      </uploads>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitUpload" v-if="uploadDisable" disabled>确 定</el-button>
        <el-button type="primary" @click="submitUpload" v-else>确 定</el-button>
        <el-button @click="cancelUpload">取 消</el-button>
      </div>
    </el-dialog>
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
    <InputView v-if="open" :parent-id="queryParamsInner.parentId" :deptid="deptId" :node-id="nodeId"
               :metadata="metadata" :metadataid="id" :type="type" :formconf="formConf" @close="cancel"/>
  </div>
</template>

<script>
  import { listTemplates } from '@/api/basic/templates'

  var _ = require('lodash')
  import {
    addMetadata,
    delMetadata,
    exportMetadata,
    exportMetadataField,
    exportMetadataItem,
    exportMetadataItemAndEle, importHandleConfirm,
    listMetadata,
    updateMetadata, uploadHandle
  } from '@/api/system/metadata'
  import DeptTree from '@/views/components/deptTree'
  import InputView from '@/views/system/metadata/InputView'
  import { listJson } from '@/api/system/json'
  import {listMetadataRule} from '@/api/system/metadataRule'
  import {deepClone} from '@/utils'
  import Uploads from '@/views/components/Uploads'

  export default {
    name: "Metadata",
    components: {
      DeptTree,
      InputView,
      Uploads
    },
    data() {
      return {
        leftImportLoading: false,
        leftTableData:[],
        uploadFileName:'',
        tableTitle: [],
        uploadDisable: true,
        importPreView:[],
        importS:false,
        upload: false,
        uploadUrl: process.env.VUE_APP_BASE_API + '/system/metadata/fileUpload/'+this.nodeId+'/'+this.deptId,
        metadata: {},
        id: 0,
        type: 'create',
        data: {},
        definition: [],
        formConf: {},
        rightTableData:[],
        rightImportLoading:false,
        tableFields:[],
        tableFieldsPick:[],
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
        idsInner:[] ,
        singleInner:true,
        multipleInner:true,
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
        metadataInnerList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 聚合层次字典
        aggregationLevelOptions: [],
        // 查询参数
        queryParams: {
          deptId: null,
          parentId: 0,
          pageNum: 1,
          pageSize: 10,
          aggregationLevel: '案卷',
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
          // intellectualPropertyStatement: null,
          // authorizedAgent: null,
          // permissionAssignment: null,
          // controlIdentifier: null,
          annotation: null
        },
        // 表单参数
        form: {},
        queryParamsInner: {
          pageNum: 1,
          pageSize: 5,
        },
        totalInner: 0,
        archiveInnerShow: false,
        loadingInner: false,
        showSearchInner: false,
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
      submitUploadHandle(){
        let data = {
          nodeId:this.nodeId,
          deptId:this.deptId,
          filename:this.uploadFileName,
          list:this.leftTableData,
          parentId:this.queryParams.parentId
        }
        importHandleConfirm(data).then(res=>{
          this.$message(res.msg)
          this.importS=false;
          this.getList();
        })
      },
      cancelUploadHandle(){
        this.importS=false
      },
      submitUpload(){
        uploadHandle(this.nodeId,this.deptId,this.uploadFileName)
        .then(res=>{
          this.importPreView = res.data
          this.leftTableData = []
          let srcField = this.importPreView[0]
          let destField = this.tableFields.map(item=>item.tableFieldName)
          srcField.forEach(item=>{
            let dest = destField.filter(i=>{
              return i===item
            })
            this.leftTableData.push({
              src:item,
              dest:dest?dest[0]:''
            })
          })
          this.upload = false
          this.handleDestChange()
          this.importS = true
        })

      },
      cancelUpload() {
        this.upload=false
      },
      handleDestChange(){
        this.rightImportLoading = true
        this.rightTableData=[]
        for(let i = 1;i<this.importPreView.length;i++){
          let obj={};
          for(let j = 0;j<this.importPreView[0].length;j++){
            //上传字段j
            let label = this.importPreView[0][j];
            //匹配字段
            let value = this.leftTableData.filter(item=>item.src===label)
            if(value.length>0){
              let dest = this.tableFields.filter(item=>item.tableFieldName===value[0].dest)
              if(dest.length>0){
                obj[dest[0].vModel]=this.importPreView[i][j]
              }
            }
          }
          this.rightTableData.push(obj)
        }
        this.rightImportLoading = false
      },
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
      /** 卷内 **/
      getListInner() {
        this.queryParamsInner.deptId = this.deptId;
        this.queryParamsInner.nodeId = this.nodeId;
        this.loadingInner = true
        listMetadata(this.queryParamsInner).then(response => {
          this.metadataInnerList = response.rows;
          this.totalInner = response.total;
          this.$refs.mainTable.toggleRowSelection(this.metadataList.filter(item => {
            return item.id === this.queryParamsInner.parentId
          })[0]);
          this.loadingInner = false;
        });
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
      uploadSuccess(data){
        this.uploadDisable=false;
        this.uploadFileName= data.name
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
      handleQueryInner() {
        this.queryParams.pageNum = 1;
        this.getListInner();
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      resetQueryInner() {
        this.resetForm("queryFormInner");
        this.handleQueryInner();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      handleSortInner(data) {
        this.queryParamsInner.sortField = data.prop
        this.queryParamsInner.orderBy = data.order
        this.getListInner();
      },
      handleSort(data) {
        this.queryParams.sortField = data.prop
        this.queryParams.orderBy = data.order
        this.getList();
      },
      //卷内多选框选中数据
      handleSelectionChangeInner(selection) {
        this.idsInner  = selection.map(item => item.id)
        this.singleInner = selection.length !== 1
        this.multipleInner = !selection.length
      },
      rowClick(row) {
        if (row.aggregationLevel === '案卷') {
          if (!this.archiveInnerShow) {
            this.queryParams.pageSize = 5
            this.$refs.pager.handleSizeChange(this.queryParams.pageSize)
            this.queryParamsInner.parentId = row.id
            this.getListInner()
            //点击若是案卷 则显示案卷部分
            this.archiveInnerShow = true
          }
          else {
            this.archiveInnerShow = false
          }
        }
        else{
          this.archiveInnerShow = false
        }
        this.$refs.mainTable.toggleRowSelection(row)
      },

      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      loadForm() {
        let fullPath = this.$route.fullPath
        let id = fullPath.substring(fullPath.lastIndexOf('/') + 1)
        let query = {nodeId: id, deptId: this.deptId}
        // getTableTitle(id, this.deptId).then(res => {
        //   this.tableTitle = res.data
        // })
        listJson(query).then(res => {
          this.formConf = JSON.parse(res.rows[0].formData)
          this.formReserve = deepClone(this.formConf)
        }).catch(err => {
          console.log(err)
        })
        listMetadataRule().then(res => {
          window.sessionStorage.setItem('WS', escape(JSON.stringify(res.rows)))
        })
        listTemplates({nodeId:this.nodeId,deptId:this.deptId})
          .then(res=>{
            this.tableFields = res.rows
            this.tableTitle = this.tableFields.filter(item=>{
              return item.tableFieldFlag
            })
            this.searchField = this.tableFields.filter(item=>{
              return item.tableSearchFlag
            })
            this.searchField =this.searchField.sort((a,b)=>{
              return a.searchSort-b.searchSort
            })
            this.tableFieldsPick = []
            this.tableFields.forEach(item=>{
              this.tableFieldsPick.push({
                label:item.tableFieldName,
                value:item.tableFieldName,
                vm:item.vModel
              })
            })
          })
      },
      handleAddInner() {
        this.type = 'create'
        this.open = true
        this.inner = true
        this.formConf = this.formReserve
        this.title = "添加文书类基本元数据";
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
      }
    },
    watch: {
      formconf: function (nv, ov) {
        console.log(nv, ov)
      },
      deptId: function(nv,ov){
        this.uploadUrl = process.env.VUE_APP_BASE_API + '/system/metadata/fileUpload/'+this.nodeId+'/'+this.deptId
      }
    }
  };
</script>
