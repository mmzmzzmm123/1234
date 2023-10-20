<template>
  <div>
    <el-divider content-position="left">纬度设置</el-divider>
    <el-table :data="multiList" style="width: 100%" size="mini">
      <el-table-column type="index"></el-table-column>
      <el-table-column label="纬度名称" prop="name"></el-table-column>
      <el-table-column label="操作" align="center" min-width="100px" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!-- <el-button size="mini" type="text" icon="el-icon-link" @click="handleMultiQuestions(scope.row)">绑定问题</el-button> -->
          <el-button size="mini" type="text" @click="handleScoreSetting(scope.row)">选择维度</el-button>
<!--          <el-button style="color: red" size="mini" type="text" @click="handleDelete(scope.row)"-->
<!--                     v-hasPermi="['gauge:setting:remove']">-->
<!--            删除-->
<!--          </el-button>-->
        </template>
      </el-table-column>
    </el-table>
    <el-row style="margin-top: 20px">
      <el-input style="width: 100%" class="input-new-tag" v-if="newMultiVisible" v-model="newMultiValue"
                ref="saveTagInput" size="small" @keyup.enter.native="handleInputConfirm"
                @blur="handleInputConfirm">
      </el-input>
      <el-button v-else class="button-new-tag" style="width: 100%; margin-left: 0" size="small"
                 @click="showMultiInput()">+ 新维度
      </el-button>
    </el-row>

    <el-divider content-position="left">{{ multi.name || '' }}--绑定问题</el-divider>

    <el-table :data="questionList" ref="multipleTable" tooltip-effect="dark" style="width: 100%"
              :header-cell-class-name="cellClass"
              @select="handleSelectionChange">>
      <el-table-column type="selection" :selectable="judgeSelect"></el-table-column>
      <el-table-column width="50px" property="no" label="序号"></el-table-column>
      <el-table-column width="150px" property="id" label="维度">
        <template slot-scope="scope">
          <el-tag> {{ getLat(scope.row) }} </el-tag>
        </template>
      </el-table-column>
      <el-table-column property="title" label="标题"></el-table-column>
    </el-table>
  </div>
</template>
<script>
import {
  listMultiSetting,
  addMultiSetting,
  updateMultiSetting,
  getMultiSetting,
  delMultiSetting,
} from "@/api/gauge/multiSetting";

import {listQuestions} from "@/api/gauge/questions.js";

export default {
  components: {},
  props: {
    gaugeId: {
      type: Number,
      default: null,
    },
  },
  watch: {
    gaugeId: {
      async handler(val) {
        if (val !== this.currentValue) {
          this.initMulti()
          await this.getMultiQuestions()
          await this.getMultiList(val)
        }
      },
      deep: true,
      immediate: true,
    },
  },
  data() {
    return {
      newMultiVisible: false,
      newMultiValue: "",
      multiList: [],
      multi: {},
      disList: [],
      questionList: [],
      questionListSellection: [],
    };
  },
  methods: {
    judgeSelect(row, index){
      return this.multi.id > 0 && !(this.disList.length > 0 && this.disList.includes(row.id+'')) // 返回true该行可选，返回false则不可选
    },
    cellClass(row) {
      if (row.columnIndex === 0) {
        return 'disabledCheck';
      }
    },
    initMulti() {
      this.multi = {
        id: 0,
        gaugeId: this.gaugeId,
        name: '请选择维度',
        scoreSettingIds: [],
        questionIds: []
      }
    },
    async getMultiList() {
      let res = await listMultiSetting({gaugeId: this.gaugeId, pageSize: 999});
      this.multiList = res.rows;
      this.handleQuestionIds()
    },
    getLat(row) {
      const data = this.multiList.find(a => a.questionIds && a.questionIds !== '' && a.questionIds.split(",").includes(row.id + ''))
      return data ? data.name : '-'
    },

    showMultiInput() {
      this.newMultiVisible = true;
      this.$nextTick((_) => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },

    async handleInputConfirm() {
      let inputValue = this.newMultiValue;

      if (inputValue) {
        let data = {
          name: inputValue,
          gaugeId: this.gaugeId,
        };
        await addMultiSetting(data);
        await this.getMultiList();
      }
      this.newMultiVisible = false;
      this.newMultiValue = "";
    },
    async handleScoreSetting(data) {
      let res = await getMultiSetting(data.id);
      this.multi = res.data
      this.handleQuestionIds()
    },
    handleQuestionIds() {
      const errList = this.multiList.filter(a => a.id !== this.multi.id && a.questionIds)
      // console.log(this.multi)
      // console.log(this.multiList)
      // console.log(errList)
      if (errList.length > 0) {
        let arr = []
        errList.forEach(a => {
          arr = arr.concat(a.questionIds.split(","))
        })
        this.disList = [...new Set(arr)]
        // console.log(this.disList)
      } else {
        this.disList = []
      }

      this.$refs.multipleTable.clearSelection();
      //默认选中
      if (this.multi.questionIds != null && this.multi.questionIds.length > 0) {
        let ids = this.multi.questionIds.split(",");
        this.$nextTick(() => {
          ids.forEach((item) => {
            this.questionList.forEach((row) => {
              if (item == row.id) {
                this.$refs.multipleTable.toggleRowSelection(row);
              }
            });
          });
        });
      }
    },
    /** 删除按钮操作 */
    handleDelete(data) {
      this.$modal.confirm('是否确认删除数据项？').then(function () {
        return delMultiSetting(data.id);
      }).then(() => {
        if (data.id  === this.multi.id) {
          this.initMulti()
        }

        this.getMultiList()
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    async getMultiQuestions() {
      let res = await listQuestions({gaugeId: this.gaugeId, pageSize: 999});
      this.questionList = res.rows;
    },
    async handleSelectionChange(selection, row) {
      // let selected = selection.length && selection.indexOf(row) !== -1
      // console.log(selected)  // true就是选中，0或者false是取消选中

      // if (this.multi.id === 0) {
        // this.$refs.multipleTable.toggleRowSelection(row, false);
        // return this.$modal.msgWarning("请选择维度");
      // }

      // 更新已存在的维度问题
      // const errList = this.multiList.filter(a => a.id !== this.multi.id && a.questionIds && a.questionIds.split(",").includes(row.id+''))
      // console.log(row)
      // if (errList && errList.length > 0) {
      //   return this.$modal.msgWarning("该维度已存在");
      //   errList.forEach(err => {
      //     const arr = err.questionIds.split(',')
      //     arr.splice(arr.findIndex(a => a == row.id), 1)
      //     err.questionIds = arr.join(',')
      //     console.log(err)
      //     updateMultiSetting(err)
      //   })
      // }

      let qids = [];
      selection.forEach((row) => {
        qids.push(row.id);
      });
      this.multi.questionIds = qids.join(",");
      await updateMultiSetting(this.multi)
      await this.getMultiList()
    },
  },
};
</script>
<style lang="scss" scoped>
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}

.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}

// 深度选择器 去掉全选按钮
::v-deep .el-table .disabledCheck .cell .el-checkbox__inner {
  display: none;
}

::v-deep .el-table .disabledCheck .cell::before {
  content: '';
  text-align: center;
  line-height: 37px;
}
</style>
