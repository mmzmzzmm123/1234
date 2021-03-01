<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="4">
        <dept-tree @selectedDept="handleNodeClick"></dept-tree>
      </el-col>
      <el-col :span="20">

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['system:setting:add']"
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
              v-hasPermi="['system:setting:edit']"
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
              v-hasPermi="['system:setting:remove']"
            >删除
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['system:setting:export']"
            >导出
            </el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="settingList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column label="id" align="center" prop="id"/>
          <!--          <el-table-column label="部门id" align="center" prop="deptId" />-->
          <el-table-column label="字段1" align="center" prop="field1" :formatter="field1Format"/>
          <el-table-column label="字段2" align="center" prop="field2" :formatter="field2Format"/>
          <el-table-column label="字段3" align="center" prop="field3" :formatter="field3Format"/>
          <el-table-column label="字段4" align="center" prop="field4" :formatter="field4Format"/>
          <el-table-column label="字段5" align="center" prop="field5" :formatter="field5Format"/>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:setting:edit']"
              >修改
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:setting:remove']"
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

    <!-- 添加或修改档号设置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!--        <el-form-item label="部门id" prop="deptId">-->
        <!--          <el-input v-model="form.deptId" placeholder="请输入部门id" />-->
        <!--        </el-form-item>-->
        <el-form-item label="字段1" prop="field1">
          <el-select v-model="form.field1" placeholder="请选择字段1">
            <el-option
              v-for="dict in field1Options"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="字段2" prop="field2">
          <el-select v-model="form.field2" placeholder="请选择字段2">
            <el-option
              v-for="dict in field2Options"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="字段3" prop="field3">
          <el-select v-model="form.field3" placeholder="请选择字段3">
            <el-option
              v-for="dict in field3Options"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="字段4" prop="field4">
          <el-select v-model="form.field4" placeholder="请选择字段4">
            <el-option
              v-for="dict in field4Options"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="字段5" prop="field5">
          <el-select v-model="form.field5" placeholder="请选择字段5">
            <el-option
              v-for="dict in field5Options"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
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
  import {listSetting, getSetting, delSetting, addSetting, updateSetting, exportSetting} from "@/api/system/setting";
  import deptTree from '@/views/components/deptTree'

  export default {
    name: "Setting",
    components: {
      deptTree,
    },
    data() {
      return {
        deptId: 0,
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
        // 档号设置表格数据
        settingList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 字段1字典
        field1Options: [],
        // 字段2字典
        field2Options: [],
        // 字段3字典
        field3Options: [],
        // 字段4字典
        field4Options: [],
        // 字段5字典
        field5Options: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          deptId: null,
          field1: null,
          field2: null,
          field3: null,
          field4: null,
          field5: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          field1: [
            {required: true, message: "必须要有3个以上字段", trigger: "blur"}
          ],
          field2: [
            {required: true, message: "必须要有3个以上字段", trigger: "blur"}
          ],
          field3: [
            {required: true, message: "必须要有3个以上字段", trigger: "blur"}
          ],
        }
      };
    },
    created() {
      this.getList();
      this.getDicts("sys_archival_code_setting").then(response => {
        this.field1Options = response.data;
      });
      this.getDicts("sys_archival_code_setting").then(response => {
        this.field2Options = response.data;
      });
      this.getDicts("sys_archival_code_setting").then(response => {
        this.field3Options = response.data;
      });
      this.getDicts("sys_archival_code_setting").then(response => {
        this.field4Options = response.data;
      });
      this.getDicts("sys_archival_code_setting").then(response => {
        this.field5Options = response.data;
      });
    },
    methods: {
      /** 查询档号设置列表 */
      getList() {
        this.loading = true;
        listSetting(this.queryParams).then(response => {
          this.settingList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 字段1字典翻译
      field1Format(row, column) {
        return this.selectDictLabel(this.field1Options, row.field1);
      },
      // 字段2字典翻译
      field2Format(row, column) {
        return this.selectDictLabel(this.field2Options, row.field2);
      },
      // 字段3字典翻译
      field3Format(row, column) {
        return this.selectDictLabel(this.field3Options, row.field3);
      },
      // 字段4字典翻译
      field4Format(row, column) {
        return this.selectDictLabel(this.field4Options, row.field4);
      },
      // 字段5字典翻译
      field5Format(row, column) {
        return this.selectDictLabel(this.field5Options, row.field5);
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
          deptId: this.deptId,
          field1: null,
          field2: null,
          field3: null,
          field4: null,
          field5: null
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
        this.title = "添加档号设置";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getSetting(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改档号设置";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateSetting(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addSetting(this.form).then(response => {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 部门树点击*/
      handleNodeClick(data) {
        this.queryParams.deptId = data;
        this.deptId = data
        this.getList();
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除档号设置编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delSetting(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有档号设置数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportSetting(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
      }
    }
  };
</script>
