<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="4">
          <el-tree :data="data1" node-key="id" :props="defaultProps" ref="tree" highlight-current :current-node-key="currentNodekey" :default-expanded-keys="expandedkeys" @node-click="handleNodeClick"></el-tree>
      </el-col>
      <el-col :span="20">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="表单名字" prop="formName">
            <el-input
              v-model="queryParams.formName"
              placeholder="请输入表单名字"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
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
              v-hasPermi="['system:node:add']"
            >增加节点
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['system:node:edit']"
            >修改节点
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['system:node:remove']"
            >删除节点
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="info"
              icon="el-icon-s-tools"
              size="mini"
              :disabled="multiple"
              @click="handlePermission"
              v-hasPermi="['system:node:remove']"
            >设置节点权限
            </el-button>
          </el-col>
           <el-col :span="1.5">
            <el-button
              type="info"
              icon="el-icon-s-tools"
              size="mini"
              :disabled="multiple"
              @click="handleApprove"
              v-hasPermi="['system:node:remove']"
            >审批范围
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="info"
              icon="el-icon-s-tools"
              size="mini"
              :disabled="multiple"
              @click="handleUserGroup"
              v-hasPermi="['system:node:remove']"
            >设置环节用户组
            </el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" row-key="id" :data="jsonList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"/>
          <!-- <el-table-column label="id" align="center" prop="id"/> -->
          <el-table-column label="节点名称" align="center" prop="text"/>
          <el-table-column label="节点描述" align="center" prop="desci"/>
          <el-table-column label="下一节点" align="center" prop="nexttext"/>
          <el-table-column sortable label="节点顺序" align="center" prop="sort"/>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:json:edit']"
              >修改
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:json:remove']"
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


     <!-- 添加或修改节点对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row style="display:none">
          <el-col :span="24">
            <el-form-item label="工作流主键" prop="workid">
              <el-input v-model="form.workid"/>
            </el-form-item>
          </el-col>
           </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="节点名称" prop="text">
              <el-input v-model="form.text" placeholder="请输入节点名称"/>
            </el-form-item>
          </el-col>
           </el-row>
           <el-row>
          <el-col :span="24">
            <el-form-item label="节点描述" prop="desci">
              <el-input v-model="form.desci" placeholder="请输入节点描述"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  <!-- 审批范围弹框 -->
    <el-dialog :title="title" :visible.sync="approvescope" width="400px" append-to-body>
      <el-form>
        <el-row>
        <el-col :span="24">
              <el-form-item label="审批范围:">
                <el-select v-model="form.approvescope">
                  <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
          <el-col :span="24" style="display:none">
            <el-form-item label="节点主键">
              <el-input v-model="form.id"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="ApproveSubmitForm">确 定</el-button>
        <el-button @click="cancelApprove">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 设置用户组 -->
    <el-dialog :title="title" :visible.sync="useGroupOpen" width="400px" append-to-body>
      <el-form ref="form1" :model="form1" label-width="80px">
        <el-row style="display:none">
          <el-col :span="24">
            <el-form-item prop="id">
              <el-input v-model="form1.id"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="用户组:">
              <el-select v-model="form1.roleIds" multiple placeholder="请选择">
                <el-option
                  v-for="item in roleOptions"
                  :key="item.roleId"
                  :label="item.roleName"
                  :value="item.roleId"
                  :disabled="item.status == 1"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="userGroupSubmitForm">确 定</el-button>
        <el-button @click="cancelUserGroup">取 消</el-button>
      </div>
    </el-dialog>
    
    <!-- 设置节点权限 -->
    <el-dialog :title="title" :visible.sync="permissionOpen" width="400px" append-to-body>
      <el-form ref="form2" :model="form2" label-width="80px">
        <el-row style="display:none">
          <el-col :span="24">
            <el-form-item prop="id">
              <el-input v-model="form2.id"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="权限:">
              <el-select v-model="form2.nexttext" multiple placeholder="请选择">
                <el-option
                  v-for="item in qxOptions"
                  :key="item.id"
                  :label="item.nexttext"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="permissionSubmitForm">确 定</el-button>
        <el-button @click="cancelPermission">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {
    listNode,
    addNode,
    updateNode,
    delNode,
    getNode,
    listWork,
    getNodeQx
  } from '@/api/system/workflow'
  export default {
    name: 'Json',
    data() {
      return {
        options: [{
          value: '仅本单位',
          label: '仅本单位'
        },{
          value: '支持跨单位',
          label: '支持跨单位'
        }],
        form:{
            approvescope: '', 
        },
       form1:{
         id:'',
         roleIds:[]
       },
        form2:{
         id:'',
         nexttext:[]
       },
        data1:[],//树的数据
        defaultProps: {
          children: 'children',
          label: 'name',
          value:"id",
          checkStrictly:true
        },
        expandedkeys:[],
        currentNodekey:"",//默认选中的节点树
        treeid:'',//
        forms: new Map(),
        id1: 0,
      //  data: [],
        tableFields: [],
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        texts:[],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: false,
        // 总条数
        total: 0,
        // 单json存储表格数据
        jsonList: [],
        // 弹出层标题
        title: '',
        //弹出层
        open:false,
        //审批范围弹出层
        approvescope:false,
        //用户组弹出层
        useGroupOpen:false,
        //设置节点权限弹出层
        permissionOpen:false,
         // 角色选项
        roleOptions: [],
        //节点权限
        qxOptions:[],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          workid: null
        },
        text:'',
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          text: [
            {required: true, message: '节点名称不能为空', trigger: 'blur'}
          ],
          desci: [
            {required: true, message: '节点描述不能为空', trigger: 'blur'}
          ],
        }
      }
    },
    mounted() {
    },
    created() {
      this.getWorkTree();
    },
    watch: {
    },
    methods: {
      getWorkTree(){
        listWork().then(res=>{
          this.data1=res.rows
          this.currentNodekey=this.data1[0].id;
          this.expandedkeys.push(this.data1[0].id);
          this.$nextTick(()=>{
            this.$refs.tree.setCurrentKey(this.data1[0]);
          })
          this.treeid=this.currentNodekey
          this.queryParams.workid=this.currentNodekey,
          this.getList();
        })
      },
       handleNodeClick(data) {
        this.queryParams.workid = data.id;
        this.getList();
      },
      saveForm() {
        this.$refs.form.validate((valid) => {
          if (valid) {
            this.forms.set(this.form.id, this.form)
          } else {
            return false;
          }
        })
        let data = []
        this.forms.forEach(item => {
          data.push(item)
        })
      },
      cancelEdit() {
        this.open = false
      },
      handleClick(data) {
        let f = false
        //如果不是空的 则为上一个表单 保存进修改map中
        if (JSON.stringify(this.form) !== "{}") {
          this.$refs.form.validate((valid) => {
            if (valid) {
              this.forms.set(this.form.id, this.form)
            } else {
              return false;
            }
          })
          f = true
        }
        this.form = {}
        let formData = this.tableFields.filter(item => {
          return item.id === data.id
        })
        this.form = {
          id: formData[0].id,
          tableFieldFlag: formData[0].tableFieldFlag,
          tableSearchFlag:formData[0].tableSearchFlag,
          sort:formData[0].sort,
          searchSort:formData[0].searchSort,
          width: formData[0].width
        }
      },
      filterNode(value, data) {
        if (!value) return true
        if (data.label.indexOf(value) !== -1) {
          return true
        }
      },
      getList() {
        this.loading = true
        listNode(this.queryParams).then(response => {
          this.jsonList = response.rows
          this.total = response.total
          this.loading = false
        })
      },
      // 取消按钮
      cancel() {
        this.open = false
      },
        // 表单重置
      reset() {
        this.form = {
          workid: undefined,
          text: undefined,
          desci: undefined,
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1
        this.getList()
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm('queryForm')
        this.handleQuery()
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
       /** 审批范围按钮操作 */
      handleApprove(row) {
        const id = row.id || this.ids
        this.id1 = id[0];
        getNode(id).then(response => {
           this.form = response.data;
           this.title="审批范围设置",
           this.approvescope=true
           this.form.id=this.id1
        });
      },
     // 取消范围审批按钮
      cancelApprove() {
        this.approvescope = false
      },
      /** 提交审批范围按钮 */
      ApproveSubmitForm() {
        updateNode(this.form).then(response => {
          this.msgSuccess('修改成功')
          this.approvescope = false
          this.getList()
        })
      },
       /** 设置用户组按钮操作 */
      handleUserGroup(row) {
        const id = row.id || this.ids
        this.id1 = id[0];
          this.title="设置用户组",
          this.form1.id=this.id1
        getNode(id).then(response => {
            if(response.data.text=='启动'||response.data.text=='结束'){
            this.$alert('此节点不能设置', '提示信息', {
            confirmButtonText: '确定',
        });
          }else{
              this.useGroupOpen=true,
                this.form1 = response.data;
          this.roleOptions = response.roles;
          this.form1.roleIds = response.roleIds;
          }
        });
      },
       /** 提交设置用户组按钮 */
      userGroupSubmitForm() {
        updateNode(this.form1).then(response => {
          this.msgSuccess('设置成功')
          this.useGroupOpen = false
          this.getList()
        })
      },
      /** 设置节点权限操作 */
      handlePermission(row) {
        const id = row.id || this.ids
        this.id1 = id[0];
        this.title="设置环节权限",
        this.form2.id=this.id1
        getNode(this.id1).then(response =>{
          this.permissionOpen=true
          this.form2 = response.data;
          this.form2.nexttext = response.qxSet;
          this.qxOptions=response.qxList
        })
      },
        /** 提交节点权限按钮 */
      permissionSubmitForm() {
        updateNode(this.form1).then(response => {
          this.msgSuccess('设置成功')
          this.useGroupOpen = false
          this.getList()
        })
      },
        // 取消节点权限按钮
      cancelPermission() {
        this.permissionOpen = false
      },
       // 取消用户组弹出层
      cancelUserGroup() {
        this.useGroupOpen = false
      },
        /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.title="添加节点",
      this.open=true
      this.form.workid=this.queryParams.workid
    },
      /** 修改按钮操作 */
      handleUpdate(row) {
        const id = row.id || this.ids
        getNode(id).then(response => {
          this.form = response.data;
          if(this.form.text=='启动'||this.form.text=='结束'){
            this.$alert('此节点不能修改', '提示信息', {
            confirmButtonText: '确定',
        });
          }else{
             this.open = true;
          }
          this.title = "修改节点";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs['form'].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateNode(this.form).then(response => {
                this.msgSuccess('修改成功')
                this.open = false
                this.getList()
              })
            } else {
              addNode(this.form).then(response => {
                this.form.workid=this.queryParams.workid
                this.msgSuccess('新增成功')
                this.open = false
                this.getList()
              })
            }
          }
        })
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids
        //  getNode(ids).then(response => {
        //   if(response.data.text=='启动'||response.data.text=='结束'){
        //     this.$alert('此节点不能删除', '提示信息', {
        //     confirmButtonText: '确定',
        // });
        //   }else{
        //      this.$confirm('是否确认删除编号为"' + ids + '"的数据项?', '警告', {
        //   confirmButtonText: '确定',
        //   cancelButtonText: '取消',
        //   type: 'warning'
        // }).then(function () {
        //   return delNode(ids)
        // }).then(() => {
        //   this.getList()
        //   this.msgSuccess('删除成功')
        // })
        //   }
        // });
        this.$confirm('是否确认删除编号为"' + ids + '"的数据项?', '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function () {
          return delNode(ids)
        }).then(() => {
          this.getList()
          this.msgSuccess('删除成功')
        })
      },
    }
  }
</script>

