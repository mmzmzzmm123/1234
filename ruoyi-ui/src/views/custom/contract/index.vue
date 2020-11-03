<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="合同编号" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入合同编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入客户姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入电话"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="合同状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择合同状态">
          <el-option
            v-for="dict in signStatusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
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
          v-hasPermi="['custom:contract:add']"
        >新增
        </el-button>
      </el-col>
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="success"-->
      <!--          icon="el-icon-edit"-->
      <!--          size="mini"-->
      <!--          :disabled="single"-->
      <!--          @click="handleUpdate"-->
      <!--          v-hasPermi="['custom:contract:edit']"-->
      <!--        >修改</el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="danger"-->
      <!--          icon="el-icon-delete"-->
      <!--          size="mini"-->
      <!--          :disabled="multiple"-->
      <!--          @click="handleDelete"-->
      <!--          v-hasPermi="['custom:contract:remove']"-->
      <!--        >删除</el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="warning"-->
      <!--          icon="el-icon-download"-->
      <!--          size="mini"-->
      <!--          @click="handleExport"-->
      <!--          v-hasPermi="['custom:contract:export']"-->
      <!--        >导出</el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="contractList" @selection-change="handleSelectionChange">
      <!--      <el-table-column type="selection" width="55" align="center" />-->
      <el-table-column label="合同编号" align="center" prop="id" fixed="left"/>
      <el-table-column label="合同状态" align="center" prop="status" width="80" fixed="left">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.status === 'yes' ? 'success' : 'danger'"
            disable-transitions>
            {{scope.row.status === 'yes' ? '已签订':'未签订'}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="客户姓名" align="center" prop="name" fixed="left"/>
      <el-table-column label="证件号" align="center" prop="cusId" width="190"/>
      <el-table-column label="电话" align="center" prop="phone" width="120"/>
      <el-table-column label="服务时间" align="center" prop="serveTime" :formatter="serveTimeFormat" width="100"/>
      <el-table-column label="服务承诺" align="center" prop="servePromise" width="100">
        <template slot-scope="scope">
          {{scope.row.servePromise ? `${scope.row.servePromise}斤`: ''}}
        </template>
      </el-table-column>
      <el-table-column label="金额" align="center" prop="amount"/>
      <el-table-column label="签订时间" align="center" prop="updateTime" width="180"/>
      <el-table-column label="合同地址" align="center" prop="path">
        <template slot-scope="scope">
          <el-button type="text" icon="el-icon-copy-document" @click="handleCopy(scope.row.path)" class="copyBtn"
                     :data-clipboard-text="copyValue">复制
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status==='yes'"
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleLook(scope.row.path)"
          >查看
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:contract:remove']"
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

    <!-- 添加或修改合同对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="550px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="客户姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入客户姓名"/>
        </el-form-item>
        <!--        <el-form-item label="电话" prop="phone">-->
        <!--          <el-input v-model="form.phone" placeholder="请输入电话"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="金额" prop="amount">
          <el-input v-model="form.amount" placeholder="请输入金额"/>
        </el-form-item>
        <el-form-item label="服务承诺" prop="servePromise">
          <el-input v-model="form.servePromise" placeholder="请输入服务承诺"/>
        </el-form-item>
        <el-form-item label="服务时间" prop="serveTime">
          <el-select v-model="form.serveTime" placeholder="请选择服务时间">
            <el-option
              v-for="dict in serveTimeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
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
  import {
    addContract,
    delContract,
    exportContract,
    getContract,
    listContract,
    updateContract
  } from "@/api/custom/contract";

  import Clipboard from 'clipboard';

  export default {
    name: "Contract",
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
        showSearch: false,
        // 总条数
        total: 0,
        // 合同表格数据
        contractList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 服务时间字典
        serveTimeOptions: [],
        // 签约状态字典
        signStatusOptions: [],
        //
        copyValue: '',
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          id: null,
          name: null,
          phone: null,
          status: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          name: [
            {required: true, message: "请输入客户姓名", trigger: "blur"}
          ],
          amount: [
            {required: true, message: "请输入签订金额", trigger: "blur"}
          ],
          servePromise: [
            {required: true, message: "请输入承诺效果", trigger: "blur"}
          ],
          serveTime: [
            {required: true, message: "请选择服务时间", trigger: "blur"}
          ]
        }
      };
    },
    created() {
      this.getList();
      this.getDicts("cus_serve_time").then(response => {
        this.serveTimeOptions = response.data;
      });
      this.getDicts("cus_sign_status").then(response => {
        this.signStatusOptions = response.data;
      });
    },
    methods: {
      /** 查询合同列表 */
      getList() {
        this.loading = true;
        listContract(this.queryParams).then(response => {
          this.contractList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 服务时间字典翻译
      serveTimeFormat(row, column) {
        return this.selectDictLabel(this.serveTimeOptions, row.serveTime);
      },
      signStatusFormat(row, column) {
        return this.selectDictLabel(this.signStatusOptions, row.serveTime);
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
          name: null,
          phone: null,
          serveTime: null,
          amount: null,
          path: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,
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
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "创建合同";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getContract(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改合同";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateContract(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addContract(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("新增成功");
                  this.open = false;
                  this.getList();
                }
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除合同编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delContract(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function () {
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有合同数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportContract(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function () {
        });
      },
      handleCopy(path) {
        this.copyValue = window.location.origin.replace('manage', 'sign') + path;
        const btnCopy = new Clipboard('.copyBtn');
        this.$message({
          message: '拷贝成功',
          type: 'success'
        });
        // btnCopy.destroy();
      },
      handleLook(path) {
        const url = window.location.origin.replace('manage', 'sign') + path;
        // const url = "http://stsign.busyinn.com" + path;
        window.open(url, '_blank');
      }
    }
  };
</script>
