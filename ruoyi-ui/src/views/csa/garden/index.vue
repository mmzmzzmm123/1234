<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="农场分区" prop="zoneId">
        <el-select v-model="queryParams.zoneId" placeholder="请选择农场分区" clearable>
          <el-option
            v-for="zone in farmzoneList"
            :key="zone.id"
            :label="zone.name"
            :value="zone.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="菜地编号" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入菜地编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="竣工" prop="isCompleted">
        <el-select v-model="queryParams.isCompleted" placeholder="请选择竣工" clearable>
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['csa:garden:add']"
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
          v-hasPermi="['csa:garden:edit']"
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
          v-hasPermi="['csa:garden:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['csa:garden:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-finished"
          size="mini"
          @click="handleComplete"
          v-hasPermi="['csa:garden:edit']"
        >竣工</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="gardenList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="农场分区" align="center" prop="farmZone.name" />
      <el-table-column label="菜地编号" align="center" prop="code">
        <template slot-scope="scope">
          <el-tag
            :key="scope.row.code"
            :type="scope.row.isCompleted == 'Y' ? 'success' : 'warning'" effect="plain">
            {{ scope.row.code }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="菜地名称" align="center" prop="name" />
      <el-table-column label="平方米" align="center" prop="m2" />
      <el-table-column label="耕作" align="center" prop="isFarming">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isFarming"/>
        </template>
      </el-table-column>
      <el-table-column label="售出" align="center" prop="isSelled">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isSelled"/>
        </template>
      </el-table-column>
      <el-table-column label="竣工" align="center" prop="isCompleted">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isCompleted"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            :disabled="scope.row.isCompleted == 'Y'"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['csa:garden:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            :disabled="scope.row.isCompleted == 'Y'"
            @click="handleDelete(scope.row)"
            v-hasPermi="['csa:garden:remove']"
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

    <!-- 添加或修改菜地划分对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="农场分区" prop="zoneId">
          <el-select v-model="form.zoneId" placeholder="请选择农场分区" clearable>
            <el-option
              v-for="zone in farmzoneList"
              :key="zone.id"
              :label="zone.name"
              :value="zone.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="菜地编号" prop="code">
          <el-input v-model="form.code" placeholder="请输入菜地编号" />
        </el-form-item>
        <el-form-item label="平方米" prop="m2">
          <el-input v-model="form.m2" placeholder="请输入平方米" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
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
  import { listFarmzone } from "@/api/csa/farmzone";
  import { listGarden, getGarden, delGarden, addGarden, updateGarden, updateGardenStatus } from "@/api/csa/garden";

  export default {
    name: "Garden",
    dicts: ['sys_normal_disable', 'sys_yes_no'],
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
        // 农场分区列表
        farmzoneList: [],
        // 菜地划分表格数据
        gardenList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 20,
          zoneId: null,
          code: null,
          isCompleted: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          zoneId: [
            { required: true, message: "农场分区不能为空", trigger: "blur" }
          ],
          code: [
            { required: true, message: "菜地编号不能为空", trigger: "blur" }
          ],
          m2: [
            { required: true, message: "平方米不能为空", trigger: "blur" }
          ]
        }
      };
    },
    created() {
      this.getZoneList();
      this.getList();
    },
    methods: {
      /** 查询农场分区列表 */
      getZoneList() {
        this.loading = true;
        listFarmzone(null).then(response => {
          this.farmzoneList = response.rows;
          this.loading = false;
        });
      },
      /** 查询菜地划分列表 */
      getList() {
        this.loading = true;
        listGarden(this.queryParams).then(response => {
          this.gardenList = response.rows;
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
          zoneId: null,
          code: null,
          m2: null,
          isCompleted: "N",
          remark: null
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
        this.title = "添加菜地划分";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getGarden(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改菜地划分";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateGarden(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addGarden(this.form).then(response => {
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
        this.$modal.confirm('是否确认删除选择的菜地？').then(function() {
          return delGarden(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('csa/garden/export', {
          ...this.queryParams
        }, `garden_${new Date().getTime()}.xlsx`)
      },
      /** 设为竣工 */
      handleComplete(row) {
        const ids = row.id || this.ids;
        this.$modal.confirm('竣工后分区、编号和面积将不可更改！是否要将选择的菜地设为竣工状态？').then(function() {
          return updateGardenStatus({isCompleted: 'Y'}, ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("竣工设置成功");
        }).catch(() => {});
      }
    }
  };
</script>
