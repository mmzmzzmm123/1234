<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="房间名称" prop="roomName">
        <el-input
          v-model="queryParams.roomName"
          placeholder="请输入房间名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属楼层" prop="floorId">
        <el-select v-model="queryParams.floorId" placeholder="请输入所属楼层" clearable>
          <el-option
            v-for="dict in FloorList"
            :key="dict.id"
            :label="dict.floorName"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="房间类型" prop="roomType">
        <el-select v-model="queryParams.roomType" placeholder="请选择房间类型" clearable>
          <el-option
            v-for="dict in dict.type.room_type"
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
          v-hasPermi="['autoee:room:add']"
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
          v-hasPermi="['autoee:room:edit']"
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
          v-hasPermi="['autoee:room:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['autoee:room:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="roomList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
       <el-table-column label="房间编号" align="center" prop="id" />
      <el-table-column label="房间名称" align="center" prop="roomName" />
      <el-table-column label="所属楼层" align="center" prop="floorName" />
<!--      <el-table-column label="所属楼层" align="center" prop="floorId" />-->
      <el-table-column label="房间类型" align="center" prop="roomType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.room_type" :value="scope.row.roomType"/>
        </template>
      </el-table-column>
      <el-table-column label="房间面积" align="center" prop="roomArea" />
      <el-table-column label="所属学科" align="center" prop="subject" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['autoee:room:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['autoee:room:remove']"
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

    <!-- 添加或修改房间管理对话框 -->
    <el-dialog class="ruoyi_dialog" :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="房间名称" prop="roomName">
          <el-input v-model="form.roomName" placeholder="请输入房间名称" />
        </el-form-item>
        <el-form-item label="所属楼层" prop="floorId">
          <el-select v-model="form.floorId" placeholder="请输入所属楼层" clearable>
            <el-option
              v-for="dict in FloorList"
              :key="dict.id"
              :label="dict.floorName"
              :value="dict.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="房间类型" prop="roomType">
          <el-select v-model="form.roomType" placeholder="请选择房间类型">
            <el-option
              v-for="dict in dict.type.room_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="房间面积" prop="roomArea">
          <el-input v-model="form.roomArea" placeholder="请输入房间面积" />
        </el-form-item>
        <el-form-item label="所属学科" prop="subject">
          <el-input v-model="form.subject" placeholder="请输入所属学科" />
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
import { listRoom, getRoom, delRoom, addRoom, updateRoom } from "@/api/autoee/room";
import { listFloor, getFloor, delFloor, addFloor, updateFloor } from "@/api/autoee/floor";
export default {
  name: "Room",
  dicts: ['room_type'],
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
      // 房间管理表格数据
      roomList: [],
      FloorList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roomName: null,
        floorId: null,
        roomType: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        roomName: [
          { required: true, message: "房间名称不能为空", trigger: "blur" }
        ],
        floorId: [
          { required: true, message: "所属楼层不能为空", trigger: "blur" }
        ],
        roomType: [
          { required: true, message: "房间类型不能为空", trigger: "change" }
        ],
        roomArea: [
          { required: true, message: "房间面积不能为空", trigger: "blur" }
        ],
        subject: [
          { required: true, message: "所属学科不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getFloorList();
  },
  methods: {
    /** 查询房间管理列表 */
    getList() {
      this.loading = true;
      listRoom(this.queryParams).then(response => {
        this.roomList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getFloorList() {
      listFloor({pageNum: 1, pageSize: 90000000}).then(response => {
        this.FloorList = response.rows;
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
        roomName: null,
        floorId: null,
        roomType: null,
        roomArea: null,
        subject: null,
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
      this.reset();
      this.open = true;
      this.title = "添加房间管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRoom(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改房间管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRoom(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRoom(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除房间管理编号为"' + ids + '"的数据项？').then(function() {
        return delRoom(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('autoee/room/export', {
        ...this.queryParams
      }, `room_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
