<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="姓名" prop="userId">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入咨询师姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类" prop="catId">
        <el-select v-model="queryParams.catId" placeholder="请选择分类" clearable>
          <el-option
            v-for="item in catList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="标签" prop="tabs">
        <el-input
          v-model="queryParams.tabs"
          placeholder="请输入标签"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="咨询方向" prop="way">
        <el-input
          v-model="queryParams.way"
          placeholder="请输入咨询方向"
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
          v-hasPermi="['psychology:consult:add']"
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
          v-hasPermi="['psychology:consult:edit']"
        >修改</el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="danger"-->
<!--          plain-->
<!--          icon="el-icon-delete"-->
<!--          size="mini"-->
<!--          :disabled="multiple"-->
<!--          @click="handleDelete"-->
<!--          v-hasPermi="['psychology:consult:remove']"-->
<!--        >删除</el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['psychology:consult:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="consultList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column v-if="columns[0].visible" label="分类" align="center" prop="catId">
        <template slot-scope="scope">
          {{ catList.find(i => scope.row.catId === i.id).name }}
        </template>
      </el-table-column>
      <el-table-column v-if="columns[1].visible" label="登录名" align="center" prop="userName" />
      <el-table-column v-if="columns[2].visible" label="头像" align="center" prop="avatar">
        <template slot-scope="scope">
          <image-preview :src="scope.row.avatar" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column v-if="columns[3].visible" label="用户邮箱" align="center" prop="email" />
      <el-table-column v-if="columns[4].visible" label="手机号码" align="center" prop="phonenumber" />
      <el-table-column v-if="columns[5].visible" label="用户性别" align="center" prop="sex" />
<!--      <el-table-column v-if="columns[6].visible" label="标签" align="center" prop="tabs" />-->
<!--      <el-table-column v-if="columns[7].visible" label="咨询方向" align="center" prop="way" />-->
      <el-table-column v-if="columns[8].visible" label="介绍文案" align="center" prop="info" />
      <el-table-column v-if="columns[10].visible" label="咨询人数" align="center" prop="workNum" />
      <el-table-column v-if="columns[11].visible" label="服务时长(小时)" align="center" prop="workTime" />
      <el-table-column v-if="columns[12].visible" label="从业时间(年)" align="center" prop="workHours" />
      <el-table-column v-if="columns[13].visible" label="状态" align="center" prop="status" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleServeUpdate(scope.row)"
            v-hasPermi="['system:consult:edit']"
          >编辑服务</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:consult:edit']"
          >修改</el-button>
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['system:consult:remove']"-->
<!--          >删除</el-button>-->
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

    <!-- 添加或修改心理咨询师对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">

        <el-row>
          <el-col :span="12">
            <el-form-item label="登录名" prop="userName">
              <el-input v-model="form.userName" :disabled="form.id" placeholder="请输入登录名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号码" prop="phonenumber">
              <el-input v-model="form.phonenumber" placeholder="请输入手机号码" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入邮箱" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="email">
              <el-select v-model="form.sex" placeholder="请选择性别" clearable>
                <el-option
                  v-for="item in dict.type.consult_sex"
                  :key="item.label"
                  :label="item.label"
                  :value="item.label"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="分类" prop="catId">
              <el-select v-model="form.catId" placeholder="请选择分类" clearable>
                <el-option
                  v-for="item in catList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
<!--          <el-col :span="12">-->
<!--            <el-form-item label="咨询服务" prop="serve">-->
<!--              <el-select v-model="form.serveIds" multiple placeholder="请选择咨询服务" clearable>-->
<!--                <el-option-->
<!--                  v-for="item in dict.type.consult_type"-->
<!--                  :key="item.label"-->
<!--                  :label="item.label"-->
<!--                  :value="item.label"-->
<!--                />-->
<!--              </el-select>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="咨询人数" prop="workNum">
              <el-input-number v-model="form.workNum" :min="0" placeholder="请输入咨询人数" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="服务时长(小时)" prop="workTime">
              <el-input-number v-model="form.workTime" :min="0" placeholder="请输入服务时长" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="从业时间(年)" prop="workHours">
              <el-input-number v-model="form.workHours" :min="0" placeholder="请输入咨询人数" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="启用" prop="status">
              <el-switch v-model="form.status" active-value="0" inactive-value="1"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="头像" prop="avatar">
          <image-upload v-model="form.avatar" :extraData="extraData"/>
        </el-form-item>

        <el-form-item label="咨询方向" prop="way">
          <el-cascader
            v-model="form.way"
            :options="wayList"
            :props="{ multiple: true }"
            clearable>
          </el-cascader>
        </el-form-item>
        <el-form-item label="介绍文案" prop="info">
          <el-input v-model="form.info" placeholder="请输入介绍文案" />
        </el-form-item>
        <el-form-item label="图文详情" prop="detail">
          <editor v-model="form.detail" :min-height="192" :extraData="extraData"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 修改心理咨询师服务对话框 -->
    <el-dialog :title="titleServe" :visible.sync="openServe" width="900px" append-to-body>
      <el-table v-loading="loading" :data="serveList">
        <el-table-column label="服务名称" align="center" prop="name" width="150px"/>
        <el-table-column label="服务介绍" align="center" prop="info">
          <template slot-scope="scope">
            <el-input v-model="scope.row.info"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="服务价格" align="center" prop="price" width="150px">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.price" :precision="2" :step="1" :max="99999" size="mini"></el-input-number>
          </template>
        </el-table-column>
        <el-table-column label="服务时长" align="center" prop="price" width="150px">
          <template slot-scope="scope">
            50
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center" prop="status" width="100px">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.status" active-value="0" inactive-value="1"/>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateServeForm">确 定</el-button>
        <el-button @click="cancelServe">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { getAttrs, listConsult, getConsult, delConsult, addConsult, updateConsult } from "@/api/psychology/consult";
import { getServeAll, updateConsultServe } from "@/api/psychology/serve";
import { getAll } from "@/api/psychology/psyClass";

export default {
  name: "Consult",
  dicts: ['consult_sex','consult_type'],
  data() {
    return {
      extraData: {
        module: 'consult'
      },
      wayList: [],
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
      // 心理咨询师表格数据
      consultList: [],
      catList: [],
      serveList: [],
      // 弹出层标题
      title: "",
      titleServe: "",
      // 是否显示弹出层
      open: false,
      // 服务编辑弹出层
      openServe: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        userId: null,
        catId: null,
        tabs: null,
        way: null
      },
      // 表单参数
      form: {},
      // 列信息
      columns: [
        { key: 0, label: `分类`, visible: true },
        { key: 1, label: `登录名`, visible: true },
        { key: 2, label: `头像`, visible: true },
        { key: 3, label: `用户邮箱`, visible: true },
        { key: 4, label: `手机号码`, visible: true },
        { key: 5, label: `用户性别`, visible: true },
        { key: 6, label: `标签`, visible: true },
        { key: 7, label: `咨询方向`, visible: true },
        { key: 8, label: `介绍文案`, visible: true },
        { key: 9, label: `图文详情`, visible: true },
        { key: 10, label: `咨询人数`, visible: true },
        { key: 11, label: `服务时长`, visible: true },
        { key: 12, label: `从业时间`, visible: true },
        { key: 13, label: `状态`, visible: true },
      ],
      // 表单校验
      rules: {
        catId: [
          { required: true, message: "分类不能为空", trigger: "blur" }
        ],
        userName: [
          { required: true, message: "用户名称不能为空", trigger: "blur" },
          { min: 2, max: 20, message: '用户名称长度必须介于 2 和 20 之间', trigger: 'blur' }
        ],
        email: [
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
        phonenumber: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ]
      }
    };
  },
  created() {
    // 获取分类
    // 获取标签
    // 获取性别
    // 获取咨询方向
    this.getCats()
    this.getAttr()
    this.getList();
  },
  methods: {
    getAttr() {
      getAttrs(['consult_direction_one','consult_direction_two','consult_direction_three']).then(response => {
        console.log(response)
        if (response && response.code === 200 && response.data.length > 0) {
          const list = []
          response.data.forEach(p => {
            const item = {
              value: p.title,
              label: p.title,
              children: []
            }
            if (p.child.length > 0) {
              p.child.forEach(i => {
                item.children.push({
                  value: i.name,
                  label: i.name,
                })
              })
            }
            list.push(item)
          })
          console.log(list)
          this.wayList = list
        }
      });
    },
    /** 获取分类 */
    getCats() {
      getAll().then(response => {
        this.catList =  response.data
      });
    },
    /** 查询心理咨询师列表 */
    getList() {
      this.loading = true;
      listConsult(this.queryParams).then(response => {
        this.consultList = response.rows;
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
        userId: null,
        catId: null,
        tabs: null,
        way: null,
        info: null,
        detail: null,
        workNum: null,
        workTime: null,
        workHours: null,
        delFlag: null,
        status: "0",
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
    // 取消按钮
    cancelServe() {
      this.openServe = false;
      this.reset();
    },
    resetServe() {
      this.titleServe = ''
      this.serveList = []
    },
    /** 修改服务按钮操作 */
    handleServeUpdate(row) {
      this.resetServe()
      const data = {
        consultId: row.id
      }
      getServeAll(data).then(response => {
        this.serveList = response.data;
        this.openServe = true;
        this.titleServe = "修改咨询师" + row.userName + "提供的服务";
      });
    },
    updateServeForm() {
      updateConsultServe(this.serveList).then(response => {
        this.$modal.msgSuccess("修改成功")
        this.cancelServe()
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加心理咨询师";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getConsult(id).then(response => {
        response.data.way = response.data.way && JSON.parse(response.data.way)
        this.form = response.data;
        this.open = true;
        this.title = "修改心理咨询师";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        const form = JSON.parse(JSON.stringify(this.form))
        form.way = JSON.stringify(form.way)
        if (valid) {
          if (this.form.id != null) {
            updateConsult(form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addConsult(form).then(response => {
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
      this.$modal.confirm('是否确认删除心理咨询师编号为"' + ids + '"的数据项？').then(function() {
        return delConsult(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/consult/export', {
        ...this.queryParams
      }, `consult_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
