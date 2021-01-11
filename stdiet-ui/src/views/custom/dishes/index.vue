<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="菜品名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入菜品名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="菜品类型" prop="type">
        <el-select v-model="queryParams.type" multiple placeholder="请选择菜品类型" clearable size="small">
          <el-option
            v-for="dict in typeOptions"
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
          v-hasPermi="['custom:dishes:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['custom:dishes:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dishesList" @selection-change="handleSelectionChange">
      <!--      <el-table-column type="selection" width="55" align="center" />-->
      <!--      <el-table-column label="id" align="center" prop="id" />-->
      <el-table-column label="菜品名称" align="center" prop="name"/>
      <el-table-column label="菜品类型" align="center" prop="type" :formatter="typeFormat"/>
      <el-table-column label="包含食材" align="center">
        <template slot-scope="scope">
          <div v-for="igd in scope.row.igdList"
               :key="igd.id">
            {{igd.name}}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="推荐人群" align="center">
        <template slot-scope="scope">
          <div v-for="tag in scope.row.recTags"
            :key="tag">
            {{tag}}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="忌口人群" align="center">
        <template slot-scope="scope">
          <div v-for="tag in scope.row.notRecTags"
               :key="tag">
            {{tag}}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="做法" align="center" prop="methods"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['custom:dishes:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:dishes:remove']"
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

    <!-- 添加或修改菜品对话框 -->
    <el-drawer :title="title" :visible.sync="open" size="50%">
      <div class="drawer_content">
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="菜品名称" prop="name">
            <el-input v-model="form.name" placeholder="请输入菜品名称"/>
          </el-form-item>
          <el-form-item label="菜品类型" prop="type">
            <el-select v-model="form.type" placeholder="请选择菜品类型">
              <el-option
                v-for="dict in typeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="食材" prop="ingIds">
            <el-transfer
              style="text-align: left; display: inline-block"
              v-model="selIngIds"
              size="mini"
              filterable
              :titles="['备选', '已选']"
              :button-texts="['', '']"
              :format="{
          noChecked: '${total}',
          hasChecked: '${checked}/${total}',
        }"
              @change="handleChange"
              :data="ingDataList"
            >
              <el-select
                class="transfer-footer"
                slot="left-footer"
                size="small"
                filterable
                v-model="ingType"
                @change="handleOnTypeChange">
                <el-option
                  v-for="dict in ingTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"/>
              </el-select>
              <div class="transfer-footer" slot="right-footer" size="small"/>
            </el-transfer>
          </el-form-item>
          <el-form-item label="分量" prop="weight">
            <el-table
              :data="selTableData"
              border
              show-summary
              size="mini"
              :summary-method="getSummaries"
              style="width: 100%">
              <el-table-column
                prop="name"
                label="食材">
              </el-table-column>
              <el-table-column label="通俗计量">
                <template slot-scope="scope">
                <span class="cus-unit">
                  <el-input-number
                    v-model="scope.row.cusWeight"
                    size="small"
                    controls-position="right"
                    step="0.5"
                    :min="0.5"/>
                  <el-select
                    size="small"
                    v-model="scope.row.cusUnit"
                  >
                    <el-option
                      v-for="dict in cusUnitOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="parseInt(dict.dictValue)"
                    />
                  </el-select>
                </span>
                </template>
              </el-table-column>
              <el-table-column
                prop="weight"
                label="重量(g)">
                <template slot-scope="scope">
                  <el-input-number
                    class="weight"
                    v-model="scope.row.weight"
                    size="mini"
                    controls-position="right"
                    @change="handleInputChange"
                    :min="0"
                    step="50"/>
                </template>
              </el-table-column>
              <el-table-column
                prop="proteinRatio"
                label="P/100g">
              </el-table-column>
              <el-table-column
                prop="fatRatio"
                label="F/100g">
              </el-table-column>
              <el-table-column
                prop="carbonRatio"
                label="C/100g">
              </el-table-column>
            </el-table>
          </el-form-item>
          <el-form-item label="推荐人群">
            <el-tag
              style="margin-right: 4px"
              v-for="rec in selRec"
              :key="rec"
              type="success">
              {{rec}}
            </el-tag>
          </el-form-item>
          <el-form-item label="忌口人群">
            <el-tag
              style="margin-right: 4px"
              v-for="notRec in selNotRec"
              :key="notRec"
              type="danger">
              {{notRec}}
            </el-tag>
          </el-form-item>
          <el-form-item label="做法" prop="methods">
            <el-input v-model="form.methods" type="textarea" placeholder="请输入内容" rows="4"/>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
  import {addDishes, delDishes, exportDishes, getDishes, listDishes, updateDishes} from "@/api/custom/dishes";
  import {listAllIngredient} from "@/api/custom/ingredient";

  export default {
    name: "Dishes",
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
        // 菜品表格数据
        dishesList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        ingType: '1',
        // 食材类别字典
        ingTypeOptions: [],
        // 远程数据缓存，预防新增的食材找不到
        oriDataList: [],
        // 备选食材列表
        ingDataList: [],
        // 选中的食材列表
        selIngList: [],
        //
        selRec: [],
        //
        selNotRec: [],
        // 选中的食材id
        selIngIds: [],
        // 选中的食材分量列表
        selTableData: [],
        // 菜品类别字典
        typeOptions: [],
        // 通俗单位
        cusUnitOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          name: null,
          type: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {}
      };
    },
    created() {
      this.getList();
      this.getDicts("cus_dishes_type").then(response => {
        this.typeOptions = response.data;
      });
      this.getDicts("cus_ing_type").then(response => {
        this.ingTypeOptions = response.data;
      });
      this.getDicts("cus_cus_unit").then(response => {
        this.cusUnitOptions = response.data;
      });
    },
    methods: {
      /** 查询菜品列表 */
      getList() {
        this.loading = true;
        listDishes(this.queryParams).then(response => {
          this.dishesList = response.rows.map(d => {
            const recTags = [], notRecTags = [];
            d.igdList.forEach(igd => {
              if (igd.rec) {
                igd.rec.split(',').forEach(rec => {
                  if (!recTags.includes(rec)) {
                    recTags.push(rec)
                  }
                })
              }
              if (igd.notRec) {
                igd.notRec.split(',').forEach(notRec => {
                  if (!notRecTags.includes(notRec)) {
                    notRecTags.push(notRec)
                  }
                })
              }
            })
            return {
              ...d,
              recTags,
              notRecTags
            }
          });
          console.log(this.dishesList)
          this.total = response.total;
          this.loading = false;
        });
      },
      // 菜品类型字典翻译
      typeFormat(row, column) {
        return this.selectDictLabel(this.typeOptions, row.type);
      },
      cusUnitFormat(row, column) {
        return this.selectDictLabel(this.cusUnitOptions, row.type);
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
          type: null,
          methods: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,
          igdList: []
        };
        this.selIngIds = [];
        this.selIngList = [];
        this.selTableData = [];
        this.oriDataList = [];
        this.selRec = [];
        this.selNotRec = [];
        this.ingType = '1';
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
        listAllIngredient({type: this.ingType}).then(response => {
          this.open = true;
          this.title = "添加菜品";
          this.oriDataList = response.rows;
          this.ingDataList = this.oriDataList.map(obj => ({
            key: obj.id,
            label: obj.name
          }))
        })
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getDishes(id).then(response => {
          this.form = response.data;
          this.form.igdList.forEach(obj => {
            this.selIngIds.push(obj.id);
            this.selIngList.push({
              key: obj.id,
              label: obj.name
            });
            this.selTableData.push(obj)
            if (obj.rec) {
              obj.rec.split(',').forEach(rec => {
                if (!this.selRec.includes(rec)) {
                  this.selRec.push(rec)
                }
              })
            }
            if (obj.notRec) {
              obj.notRec.split(',').forEach(notRec => {
                if (!this.selNotRec.includes(notRec)) {
                  this.selNotRec.push(notRec)
                }
              })
            }
          })
          listAllIngredient({type: this.ingType}).then(res => {
            this.open = true;
            this.title = "修改菜品";
            this.oriDataList = res.rows;
            this.ingDataList = this.oriDataList.reduce((arr, cur) => {
              if (!arr.some(({key}) => key === cur.id)) {
                arr.push({
                  key: cur.id,
                  label: cur.name
                });
              }
              return arr;
            }, this.selIngList.slice());
          })
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            this.form.igdList = this.selTableData;
            if (this.form.id != null) {
              updateDishes(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addDishes(this.form).then(response => {
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
        this.$confirm('是否确认删除菜品编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delDishes(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function () {
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有菜品数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportDishes(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function () {
        });
      },
      handleChange(value, direction, movedKeys) {
        // console.log({oriIgdList: this.oriDataList, selIgdList: this.form.igdList});
        const newTableData = [];
        this.selRec = [];
        this.selNotRec = [];
        this.selIngList = value.map(id => {
          // 搜索table中的数据
          let tmpTableObj = this.selTableData.find(obj => obj.id === id);
          if (tmpTableObj) {
            newTableData.push(tmpTableObj);
          } else {
            // 搜索请求的缓存数据
            tmpTableObj = this.oriDataList.find(obj => obj.id === id);
            if (tmpTableObj) {
              newTableData.push({...tmpTableObj, weight: 100, cusWeight: 1, cusUnit: 1})
            }
          }
          if (tmpTableObj) {
            if (tmpTableObj.rec) {
              tmpTableObj.rec.split(',').forEach(rec => {
                if (!this.selRec.includes(rec)) {
                  this.selRec.push(rec)
                }
              })
            }
            if (tmpTableObj.notRec) {
              tmpTableObj.notRec.split(',').forEach(notRec => {
                if (!this.selNotRec.includes(notRec)) {
                  this.selNotRec.push(notRec)
                }
              })
            }
          }
          const tarObj = this.ingDataList.find(({key}) => key === id);
          return tarObj
        });
        this.selTableData = newTableData;
      },
      handleOnTypeChange(value) {
        listAllIngredient({type: value}).then(res => {
          this.oriDataList = res.rows;
          this.ingDataList = this.oriDataList.reduce((arr, cur) => {
            if (!arr.some(({key}) => key === cur.id)) {
              arr.push({
                key: cur.id,
                label: cur.name
              });
            }
            return arr;
          }, this.selIngList.slice());
        })
      },
      handleInputChange(val) {
        console.log({val, table: this.selTableData})
      },
      getSummaries(param) {
        const {columns, data} = param;
        return columns.reduce((arr, cur, idx) => {
          if (idx > 1) {
            arr[idx] = data.reduce((acc, dAcc) => {
              if (idx === 2) {
                return acc + parseFloat(dAcc.weight);
              }
              return parseFloat((acc + dAcc[cur.property] * dAcc.weight / 100).toFixed(1));
            }, 0);
          }
          return arr;
        }, ['合计'])
      }
    }
  };
</script>

<style>
  .el-transfer-panel__filter {
    margin: 2px;
  }

  .cus-unit {
    display: inline-flex;
  }

  .cus-unit .el-input-number--small {
    width: 38px;
  }

  .cus-unit .el-input-number .el-input-number__decrease {
    display: none;
  }

  .cus-unit .el-input-number .el-input-number__increase {
    display: none;
  }

  .cus-unit .el-input-number .el-input {
    width: 38px;
  }

  .cus-unit .el-input-number .el-input .el-input__inner {
    padding: 0;
    border-radius: 0;
    border: unset;
    border-bottom: 1px solid #DCDFE6;
  }

  .cus-unit .el-select .el-input__suffix {
    display: none;
  }

  .cus-unit .el-select .el-input__inner {
    padding: 0 4px;
    border: unset;
  }

  .weight {
    width: 70px
  }

  .weight .el-input .el-input__inner {
    padding: 0 32px 0 4px;
  }

  .drawer_content {
    height: 100%;
    display: flex;
    flex-direction: column;
  }

  .drawer_content .el-form {
    /*height: calc(100% - 45px);*/
    flex: 1 1 0;
    padding: 12px;
    overflow: auto;
  }

  .drawer_content > div {
    flex: 0 0 45px;
    display: inline-flex;
    align-items: center;
    justify-content: flex-end;
    padding: 0 12px;
  }
</style>
