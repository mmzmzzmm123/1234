<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="楼层名称" prop="floorName">
        <el-input
          v-model="queryParams.floorName"
          placeholder="请输入楼层名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属建筑物" prop="buildingId">
        <el-select v-model="queryParams.buildingId" placeholder="请输入所属建筑物" clearable>
          <el-option
            v-for="dict in BuildingList"
            :key="dict.id"
            :label="dict.buildingName"
            :value="dict.id"
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
          v-hasPermi="['autoee:floor:add']"
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
          v-hasPermi="['autoee:floor:edit']"
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
          v-hasPermi="['autoee:floor:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['autoee:floor:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="floorList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
       <el-table-column label="楼层编号" align="center" prop="id" />
      <el-table-column label="楼层名称" align="center" prop="floorName" />
      <el-table-column label="所属建筑物" align="center" prop="buildingName" />
<!--      <el-table-column label="所属建筑物" align="center" prop="buildingId" />-->
<!--      <el-table-column label="楼层面积" align="center" prop="floorArea" />-->
      <el-table-column label="房间数量" align="center" prop="roomCount" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['autoee:floor:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['autoee:floor:remove']"
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

    <!-- 添加或修改楼层管理对话框 -->
    <el-dialog class="ruoyi_dialog" :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="楼层名称" prop="floorName">
          <el-input v-model="form.floorName" placeholder="请输入楼层名称" />
        </el-form-item>
        <el-form-item label="所属建筑物" prop="buildingId">
          <el-select v-model="form.buildingId" placeholder="请输入所属建筑物" clearable>
            <el-option
              v-for="dict in BuildingList"
              :key="dict.id"
              :label="dict.buildingName"
              :value="dict.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="楼层面积" prop="floorArea">
          <el-input v-model="form.floorArea" placeholder="请输入楼层面积" />
        </el-form-item>
        <el-form-item label="房间数量" prop="roomCount">
          <el-input-number v-model="form.roomCount" placeholder="请输入房间数量" controls-position="right" :min="1" :max="10000"></el-input-number>
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
import { listFloor, getFloor, delFloor, addFloor, updateFloor } from "@/api/autoee/floor";
import { listBuilding, getBuilding, delBuilding, addBuilding, updateBuilding } from "@/api/autoee/building";
export default {
  name: "Floor",
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
      // 楼层管理表格数据
      floorList: [],
      BuildingList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        floorName: null,
        buildingId: null,
        floorArea: null,
        roomCount: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        floorName: [
          { required: true, message: "楼层名称不能为空", trigger: "blur" }
        ],
        buildingId: [
          { required: true, message: "所属建筑物不能为空", trigger: "blur" }
        ],
        floorArea: [
          { required: true, message: "楼层面积不能为空", trigger: "blur" }
        ],
        roomCount: [
          { required: true, message: "房间数量不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getBuildingList();
  },
  methods: {
    /** 查询楼层管理列表 */
    getList() {
      this.loading = true;
      listFloor(this.queryParams).then(response => {
        this.floorList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getBuildingList() {
      listBuilding({pageNum: 1, pageSize: 90000000}).then(response => {
        this.BuildingList = response.rows;
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
        floorName: null,
        buildingId: null,
        floorArea: null,
        roomCount: null,
        delFlag: null,
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.getBuildingList();
      this.reset();
      this.open = true;
      this.title = "添加楼层管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.getBuildingList();
      this.reset();
      const id = row.id || this.ids
      getFloor(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改楼层管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateFloor(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFloor(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除楼层管理编号为"' + ids + '"的数据项？').then(function() {
        return delFloor(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('autoee/floor/export', {
        ...this.queryParams
      }, `floor_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
