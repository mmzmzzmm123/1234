<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="4">
        <dept-tree @selectedDept="selectedDept">
        </dept-tree>
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
              icon="el-icon-edit"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['system:json:edit']"
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
              v-hasPermi="['system:json:edit']"
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
              v-hasPermi="['system:json:remove']"
            >删除
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" icon="el-icon-download" size="mini" @click="handTableField">
              表格编辑
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" icon="el-icon-download" size="mini" @click="handArchivalCode">
              档号设置
            </el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" row-key="id" :data="jsonList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column label="id" align="center" prop="id"/>
          <el-table-column label="表单名字" align="center" prop="formName"/>
          <el-table-column label="创建时间" align="center" prop="createTime"/>
          <el-table-column label="修改时间" align="center" prop="updateTime"/>
          <!-- <el-table-column label="表单json" align="center" prop="formData" /> -->
          <el-table-column label="备注" align="center" prop="remark"/>
          <el-table-column label="排序" align="center" prop="order"/>
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
        <el-dialog :visible.sync="open" v-if="open" :title="title" width="650px" style="height:600px">
          <el-row>
            <el-col :span="6">
              <el-input
                size="mini"
                placeholder="输入关键字进行过滤"
                v-model="filterText">
              </el-input>
              <el-tree
                :highlight-current=true
                style="overflow-y: scroll;height: 400px"
                class="filter-tree"
                :data="data"
                node-key="id"
                :props="defaultProps"
                default-expand-all
                :filter-node-method="filterNode"
                @node-click="handleClick"
                ref="tree">
              </el-tree>

            </el-col>
            <el-col :span="18">
              <el-form :model="form" ref="form" :rules="rules" style="margin-left: 20px;" class="demo-ruleForm">
                <el-form-item
                  label="是否为表格字段"
                >
                  <el-switch
                    v-model="form.tableFieldFlag"
                    active-color="#13ce66"
                    inactive-color="#ff4949">
                  </el-switch>
                </el-form-item>
                <el-form-item
                  label="是否为检索字段"
                >
                  <el-switch
                    v-model="form.tableSearchFlag"
                    active-color="#13ce66"
                    inactive-color="#ff4949">
                  </el-switch>
                </el-form-item>
                <el-form-item label="排序" prop="sort">
                  <el-input-number v-model="form.sort" :min="1" :max="200" label=""></el-input-number>
                </el-form-item>
                <el-form-item label="检索排序" prop="serachSort">
                  <el-input-number v-model="form.serachSort" :min="1" :max="200" label=""></el-input-number>
                </el-form-item>
                <el-form-item label="占位" prop="width">
                  <el-input
                    placeholder="请输入内容"
                    v-model="form.width"
                    style="width:60%"
                    maxlength="3"
                    clearable>
                  </el-input>
                </el-form-item>
                <el-form-item style="margin-top:20px">
                  <el-button type="primary" @click="saveForm">
                    确定
                  </el-button>
                  <el-button type="warning" @click="cancelEdit">
                    取消
                  </el-button>
                </el-form-item>
              </el-form>
            </el-col>
          </el-row>

        </el-dialog>
        <!-- 添加或修改档号设置对话框 -->
        <el-dialog :title="titleAc" :visible.sync="openAc" width="500px" append-to-body>
          <el-form ref="form" :model="settingForm" :rules="settingRules" label-width="80px">
            <!--        <el-form-item label="部门id" prop="deptId">-->
            <!--          <el-input v-model="form.deptId" placeholder="请输入部门id" />-->
            <!--        </el-form-item>-->
            <el-form-item label="字段1" prop="field1">
              <el-select v-model="settingForm.field1" placeholder="请选择字段1">
                <el-option
                  v-for="dict in fieldOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="字段2" prop="field2">
              <el-select v-model="settingForm.field2" placeholder="请选择字段2">
                <el-option
                  v-for="dict in fieldOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="字段3" prop="field3">
              <el-select v-model="settingForm.field3" placeholder="请选择字段3">
                <el-option
                  v-for="dict in fieldOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="字段4" prop="field4">
              <el-select v-model="settingForm.field4" placeholder="请选择字段4">
                <el-option
                  v-for="dict in fieldOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="字段5" prop="field5">
              <el-select v-model="settingForm.field5" placeholder="请选择字段5">
                <el-option
                  v-for="dict in fieldOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitSettingForm">确 定</el-button>
            <el-button @click="cancelSetting">取 消</el-button>
          </div>
        </el-dialog>
      </el-col>
    </el-row>

    <!--    -->
  </div>
</template>

<script>
  import {
    listJson,
    order,
    delJson,
    addJson,
    updateJson,
    exportJson,
    getTableField,
    updateTableField
  } from '@/api/system/json'
  import deptTree from '@/views/components/deptTree'
  import Sortable from 'sortablejs'
  import { addSetting, getSetting, updateSetting } from '@/api/system/setting'
  import { listTemplates, updateTemplates, updateTemplatesBatch } from '@/api/basic/templates'

  export default {
    name: 'Json',
    components: {
      deptTree
    },
    data() {
      return {
        titleAc:'',
        openAc:false,
        forms: new Map(),
        filterText: '',
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        data: [],
        tableFields: [],
        //deptId
        deptId: undefined,
        nodeId: undefined,
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
        // 单json存储表格数据
        jsonList: [],
        // 弹出层标题
        title: '',
        // 是否显示弹出层
        open: false,
        fieldOptions:{},
        settingForm:{},
        settingRules: {
          field1: [
            {required: true, message: "必须要有3个以上字段", trigger: "blur"}
          ],
          field2: [
            {required: true, message: "必须要有3个以上字段", trigger: "blur"}
          ],
          field3: [
            {required: true, message: "必须要有3个以上字段", trigger: "blur"}
          ],
        },
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          formName: null,
          formData: null,
          nodeId: null,
          deptId: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          remark: [
            {required: true, message: '备注不能为空', trigger: 'blur'}
          ],
          formName: [
            {required: true, message: '表单名字不能为空', trigger: 'blur'}
          ],
          width: [
            {required: true, message: '请输入占位', trigger: 'blur'},
            {type: 'number', message: '年龄必须为数字值'},
          ],
          sort: [
            {
              required: true, message: '请输入排序', trigger: 'blur'
            }
          ],
          searchSort: [
            {
              required: true, message: '请输入排序', trigger: 'blur'
            }
          ]
          // delFlag: [
          //   { required: true, message: "删除标志不能为空", trigger: "blur" }
          // ]
        }
      }
    },
    mounted() {
      this.rowDrop()
      this.getDicts("sys_archival_code_setting").then(response => {
        this.fieldOptions = response.data;
      });
    },
    created() {

    },
    watch: {
      filterText(val) {
        this.$refs.tree.filter(val)
        let a = this.data.filter((item) => {
          if (!val) return true
          return item.label.indexOf(val) !== -1
        })
        if (a) {
          this.$nextTick(() => {
            this.$refs.tree.setCurrentKey(a[0])
          })
          this.handleClick(a[0])
        }
      }
    },

    methods: {
      saveForm() {
        this.$refs.form.validate((valid) => {
          if (valid) {
            this.forms.set(this.form.id, this.form)
          } else {
            console.log('error submit!!');
            return false;
          }
        })
        let data = []
        this.forms.forEach(item => {
          data.push(item)
        })
        //提交
        updateTemplatesBatch(data).then(res => {
          this.forms=new Map()
          this.$message.success(res.msg)
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
              console.log('error submit!!');
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
      // 行拖拽
      rowDrop() {
        // 此时找到的元素是要拖拽元素的父容器
        const tbody = document.querySelector('.el-table__body-wrapper tbody')
        const _this = this
        Sortable.create(tbody, {
          //  指定父元素下可被拖拽的子元素
          draggable: '.el-table__row',
          onEnd({newIndex, oldIndex}) {
            const currRow = _this.jsonList.splice(oldIndex, 1)[0]
            _this.jsonList.splice(newIndex, 0, currRow)
            for (let i = 0; i < _this.jsonList.length; i++) {
              _this.jsonList[i].order = i + 1
            }
            order(_this.jsonList).then(res => {
              _this.$message.success(res.msg)
            })
          }
        })
      },
      selectedDept(data) {
        this.deptId = data
        this.getList()
      },
      handArchivalCode(){
        this.openAc = true
        this.titleAc = '档号设置'
      },
      submitSettingForm(){
        this.settingForm['nodeId']=this.nodeId
        this.settingForm['deptId'] = this.deptId
        if (this.settingForm.id){
          updateSetting(this.settingForm).then(res=>{
              this.$message.success(res.msg)
            })
        }else{
          addSetting(this.settingForm).then(res=>{
            this.$message.success(res.msg)
          })
        }
      },
      cancelSetting(){
        this.openAc = false
      },
      handTableField() {
        this.title = '修改表格头'
        this.open = true
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.data[0].id)
        })
        this.handleClick(this.tableFields[0])
      },
      // filterMethod(query, item) {
      //   return item.name.indexOf(query) > -1;
      // },
      /** 查询单json存储列表 */
      getList() {
        this.loading = true
        this.queryParams.deptId = this.deptId
        let i = this.$route.fullPath.lastIndexOf('/')
        this.nodeId = Number(this.$route.fullPath.substring(i + 1))
        this.queryParams.nodeId = this.nodeId
        listJson(this.queryParams).then(response => {
          this.jsonList = response.rows
          this.total = response.total
          this.loading = false
        })
        listTemplates({nodeId:this.nodeId,deptId:this.deptId}).then(res=>{
          let fields = res.rows
          this.tableFields = fields
          this.data=[]
          fields.forEach((item,index)=>{
            this.data.push({ label: item.tableFieldName,id:item.id })
          })
        })
        getSetting(this.nodeId,this.deptId).then(res=>{
          if(res.data){
            this.settingForm = res.data
          }
        })
      },
      // 取消按钮
      cancel() {
        this.open = false
        this.reset()
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          createTime: null,
          updateTime: null,
          createBy: null,
          updateBy: null,
          remark: null,
          formName: null,
          formData: null,
          delFlag: null
        }
        this.resetForm('form')
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
      /** 新增按钮操作 */
      handleAdd() {
        this.$router.push('/tool/build/' + this.deptId + '/' + this.nodeId)
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        //this.reset();
        console.log(row)
        const id = row.id
        this.$router.push('/tool/build/' + id)
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs['form'].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateJson(this.form).then(response => {
                this.msgSuccess('修改成功')
                this.open = false
                this.getList()
              })
            } else {
              addJson(this.form).then(response => {
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
        this.$confirm('是否确认删除表单单json编号为"' + ids + '"的数据项?', '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function () {
          return delJson(ids)
        }).then(() => {
          this.getList()
          this.msgSuccess('删除成功')
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams
        this.$confirm('是否确认导出所有表单json数据项?', '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function () {
          return exportJson(queryParams)
        }).then(response => {
          this.download(response.msg)
        })
      }
    }
  }
</script>

