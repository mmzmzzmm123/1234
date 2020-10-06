<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="岗位" prop="postId">
        <el-select v-model="queryParams.postId" placeholder="请选择岗位" clearable size="small" @change="searchPostChange">
          <el-option
            v-for="dict in postIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="业务员" prop="userId">
        <el-select v-model="queryParams.userId" placeholder="请选择业务员" clearable size="small">
          <el-option
            v-for="dict in searchUserIdOptions"
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
          v-hasPermi="['commision:config:add']"
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
      <!--          v-hasPermi="['commision:config:edit']"-->
      <!--        >修改-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="danger"-->
      <!--          icon="el-icon-delete"-->
      <!--          size="mini"-->
      <!--          :disabled="multiple"-->
      <!--          @click="handleDelete"-->
      <!--          v-hasPermi="['commision:config:remove']"-->
      <!--        >删除-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="warning"-->
      <!--          icon="el-icon-download"-->
      <!--          size="mini"-->
      <!--          @click="handleExport"-->
      <!--          v-hasPermi="['commision:config:export']"-->
      <!--        >导出-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="commisionList" :span-method="objectSpanMethod"
              @selection-change="handleSelectionChange">
      <el-table-column label="业务员" align="center" prop="userName"/>
      <el-table-column label="岗位" align="center" prop="postName"/>
      <el-table-column label="金额" align="center" prop="amount">
        <template scope="scope">
          {{toThousands(scope.row.amount)}}
        </template>
      </el-table-column>
      <el-table-column label="比例" align="center" prop="rate">
        <template scope="scope">
          {{scope.row.rate + '%'}}
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['commision:config:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['commision:config:remove']"
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

    <!-- 添加或修改业务提成比例对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-row :gutter="15">
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-col :span="12">
            <el-form-item label="岗位" prop="postId">
              <el-select v-model="form.postId" placeholder="请选择岗位" @change="postChange">
                <el-option
                  v-for="dict in postIdOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="业务员" prop="userId">
              <el-select v-model="form.userId" placeholder="请选择业务员">
                <el-option
                  v-for="dict in userIdOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="金额" prop="amount">
              <el-input v-model="form.amount" placeholder="请输入金额"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="分成比例" prop="rate">
              <el-input v-model="form.rate" placeholder="请输入比例"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {
    listCommision,
    getCommision,
    delCommision,
    addCommision,
    updateCommision,
    exportCommision,
  } from "@/api/custom/commision";

  import {getOptions} from "@/api/custom/order";

  export default {
    name: "CommisionConfig",
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
        // 业务提成比例表格数据
        commisionList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 业务员字典
        userIdOptions: [],
        //
        postIdOptions: [],
        //
        totalUserIdOptions: [],
        //
        searchUserIdOptions: [],
        //
        options: {},
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          userId: null,
          postId: null,
          amount: null,
          rate: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          userId: [
            {required: true, message: "业务员不能为空", trigger: "change"}
          ],
          postId: [
            {required: true, message: "岗位不能为空", trigger: "change"}
          ],
          amount: [
            {required: true, message: "金额不能为空", trigger: "blur"}
          ],
          rate: [
            {required: true, message: "分成比例不能为空", trigger: "blur"}
          ],
        }
      };
    },
    created() {
      this.getList();
      getOptions().then(response => {
        this.options = response.data.reduce((opts, cur) => {
          if (cur.postCode.startsWith('planner')) {
            return opts;
          }
          if (!opts[cur.postId]) {
            opts[cur.postId] = [];
          }
          opts[cur.postId].push({dictValue: cur.userId, dictLabel: cur.userName});

          if (!this.postIdOptions.some(opt => opt.dictValue === cur.postId)) {
            this.postIdOptions.push({dictValue: cur.postId, dictLabel: cur.postName});
          }
          if (!this.totalUserIdOptions.some(opt => opt.dictValue === cur.userId)) {
            this.totalUserIdOptions.push({dictValue: cur.userId, dictLabel: cur.userName});
          }
          return opts;
        }, {});
        this.searchUserIdOptions = this.totalUserIdOptions.slice();
      });

    },
    methods: {
      /** 查询业务提成比例列表 */
      getList() {
        this.loading = true;
        listCommision(this.queryParams).then(response => {
          this.commisionList = response.rows.reduce((arr, cur, idx, oriArr) => {
            for (let i = 0; i < arr.length; i++) {
              if (arr[i].some(e => e.userId === cur.userId)) {
                arr[i].push(cur);
                break;
              } else if (i === arr.length - 1) {
                arr.push([cur]);
                break;
              }
            }
            if (idx === 0) {
              arr.push([cur]);
            }
            if (idx === oriArr.length - 1) {
              const finalArr = [];
              arr.forEach(tmpArr => {
                tmpArr.sort((a, b) => a.amount - b.amount)
                  .forEach((elm, elmIdx) => {
                    finalArr.push({
                      ...elm,
                      userSpan: elmIdx ? {
                        rowspan: 0,
                        colspan: 0
                      } : {
                        rowspan: tmpArr.length,
                        colspan: 1
                      },
                      postSpan: elmIdx ? {
                        rowspan: 0,
                        colspan: 0
                      } : {
                        rowspan: tmpArr.length,
                        colspan: 1
                      }
                    })
                  })
              });
              return finalArr;
            }
            return arr;
          }, []);
          this.total = response.total;
          this.loading = false;
        });
      },
      // 业务员字典翻译
      userIdFormat(row, column) {
        return this.selectDictLabel(this.userIdOptions, row.userId);
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.userIdOptions = [];
        this.form = {
          ruleId: null,
          userId: null,
          userName: null,
          amount: null,
          rate: null,
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
        this.searchUserIdOptions = this.totalUserIdOptions.slice();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.ruleId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加业务提成比例";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const ruleId = row.ruleId || this.ids
        getCommision(ruleId).then(response => {
          this.form = response.data;
          this.userIdOptions = this.options[this.form.postId];
          this.open = true;
          this.title = "修改业务提成比例";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.ruleId != null) {
              updateCommision(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addCommision(this.form).then(response => {
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
        const ruleIds = row.ruleId || this.ids;
        this.$confirm('是否确认删除业务提成比例编号为"' + ruleIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delCommision(ruleIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function () {
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有业务提成比例数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportCommision(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function () {
        });
      },
      objectSpanMethod({row, column, rowIndex, columnIndex}) {
        if (columnIndex === 0) {
          return row.userSpan;
        } else if (columnIndex === 1) {
          return row.postSpan;
        }
      },
      postChange(postId) {
        this.userIdOptions = this.options[postId];
      },
      searchPostChange(postId) {
        if (!postId) {
          this.searchUserIdOptions = this.totalUserIdOptions.slice();
          return;
        }
        this.searchUserIdOptions = this.options[postId];
        this.queryParams.userId = null;
      }
    }
  };
</script>
