<template>

  <div>

        <el-divider content-position="left">纬度设置</el-divider>
        <el-table :data="multiList" style="width: 100%" size="mini">
          <el-table-column type="index"></el-table-column>
          <el-table-column label="纬度名称" prop="name"></el-table-column>
          <el-table-column label="操作" align="center" min-width="100px" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <!-- <el-button size="mini" type="text" icon="el-icon-link" @click="handleMultiQuestions(scope.row)">绑定问题</el-button> -->
              <el-button size="mini" type="text" icon="el-icon-setting" @click="handleScoreSetting(scope.row)">设置维度</el-button>
              <el-button size="mini" type="text" icon="el-icon-delete" @click="handleMultiDelete(scope.row)" v-hasPermi="['gauge:setting:remove']">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-row style="margin-top: 20px">
          <el-input style="width: 100%" class="input-new-tag" v-if="newMultiVisible" v-model="newMultiValue" ref="saveTagInput" size="small" @keyup.enter.native="handleInputConfirm"
            @blur="handleInputConfirm">
          </el-input>
          <el-button v-else class="button-new-tag" style="width: 100%; margin-left: 0" size="small" @click="showMultiInput()">+ 新维度</el-button>
        </el-row>

        <el-divider content-position="left">{{multi.name}}--绑定问题</el-divider>

        <el-table :data="questionList" ref="multipleTable" tooltip-effect="dark" style="width: 100%" @select="handleSelectionChange">>
          <el-table-column type="selection" ></el-table-column>
          <el-table-column property="no" label="序号"></el-table-column>
          <el-table-column property="title" label="标题"></el-table-column>
        </el-table>
        <el-divider content-position="left">{{multi.name}}--得分设置</el-divider>
        <el-table :data="scoreList" style="width: 100%" size="mini">

          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form ref="form" label-width="80px">
                <el-form-item label="得分范围">
                  <el-col :span="7">
                    <el-input-number style="width: 100%;" controls-position="right" size="small" v-model="props.row.start" @change="modifyScoreSetting(props.row)"></el-input-number>
                  </el-col>
                  <el-col class="line" :span="1">-</el-col>
                  <el-col :span="7">
                    <el-input-number style="width: 100%;" controls-position="right" size="small" v-model="props.row.end" @change="modifyScoreSetting(props.row)"></el-input-number>
                  </el-col>
                </el-form-item>
                <el-form-item label="建议">
                  <image-upload v-model="props.row.proposal" @input="modifyScoreSetting(props.row)" :extraData="extraData" />
                </el-form-item>
                <el-form-item label="结论">
                  <el-input
                    type="textarea"
                    :rows="2"
                    placeholder="请输入内容"
                    v-model="props.row.proposal">
                  </el-input>
                </el-form-item>

              </el-form>
            </template>
          </el-table-column>
          <el-table-column label="得分范围">
            <template slot-scope="props">
              {{props.row.start}}-{{props.row.end}}
            </template>
          </el-table-column>
          <el-table-column label="建议" prop="proposal">
            <template slot-scope="props" v-if="props.row.proposal!=null">
              <image-preview v-for="url in props.row.proposal.split(',')" :key="url" :src="url" :width="50" :height="50" />
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" min-width="100px" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-delete" @click="handleScoreSettingDelete(scope.row)" v-hasPermi="['gauge:setting:remove']">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-row style="margin-top: 20px">
          <el-button class="button-new-tag" style="width: 100%; margin-left: 0" size="small" @click="insertScoreSetting()">+ 得分范围</el-button>
        </el-row>


  </div>
</template>
<script>
import {
  listMultiSetting,
  addMultiSetting,
  updateMultiSetting,
  getMultiSetting,
} from "@/api/gauge/multiSetting";
import {
  listSettingByIds,
  listSetting,
  addSetting,
  updateSetting,
  delSetting,
} from "@/api/gauge/setting";

import { listQuestions } from "@/api/gauge/questions.js";
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
      handler(val) {
        if (val !== this.currentValue) {
          this.getMultiList(val);
        }
      },
      deep: true,
      immediate: true,
    },
  },
  data() {
    return {
      extraData: {
        module: 'gauge'
      },
      dialogQuestionVisible: false,
      newMultiVisible: false,
      newMultiValue: "",
      multiList: [],
      multi: { scoreSettingIds: null, questionIds: null },
      scoreList: [],
      questionList: [],
      questionListSellection: [],
    };
  },
  methods: {
    async getMultiList() {
      let res = await listMultiSetting({ gaugeId: this.gaugeId });
      this.multiList = res.rows;
      if (this.multiList.length) {
        this.multi = this.multiList[0];

        setTimeout(() => {
          this.getScoreSettingList();
          this.getMultiQuestions();
        }, 1002);
      }
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
        this.getMultiList();
      }
      this.newMultiVisible = false;
      this.newMultiValue = "";
    },
    async insertScoreSetting() {
      //获取
      let scoreData = {
        gaugeId: this.gaugeId,
        start: 1,
        end: 20,
        proposal: "",
      };
      if (this.scoreList.length) {
        let score = this.scoreList[this.scoreList.length - 1];
        scoreData.start = score.end;
        scoreData.end = score.end + 20;
      }

      let res = await addSetting(scoreData);
      scoreData = res.data;
      if (this.multi.scoreSettingIds == null) {
        this.multi.scoreSettingIds = scoreData.id;
      } else {
        this.multi.scoreSettingIds += "," + scoreData.id;
      }
      await updateMultiSetting(this.multi);
      await this.getScoreSettingList();
    },
    async getScoreSettingList() {
      if (!this.multi.scoreSettingIds == null) {
        this.scoreList = [];
        return;
      }
      let ids = this.multi.scoreSettingIds.split(",");
      let res = await listSettingByIds(ids);
      this.scoreList = res.rows;
    },
    async modifyScoreSetting(data) {
      await updateSetting(data);
    },

    async handleScoreSetting(data) {
      let res = await getMultiSetting(data.id);
      this.multi = res.data;
      await this.getScoreSettingList();
      await this.getMultiQuestions();
    },

    async getMultiQuestions(data) {
      let res = await listQuestions({ gaugeId: this.gaugeId, pageSize: 100 });
      this.questionList = res.rows;

      this.$refs.multipleTable.clearSelection();
      //默认选中
      if (this.multi.questionIds != null) {
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
    async handleSelectionChange(selection, row) {
      let qids = [];
      selection.forEach((row) => {
        qids.push(row.id);
      });
      this.multi.questionIds = qids.join(",");
      await updateMultiSetting(this.multi);
    },

    handleScoreSettingDelete(data) {
      this.$modal
        .confirm(
          "是否确认删除得分设置（" + data.start + "-" + data.end + "）？"
        )
        .then(function () {
          return delSetting(data.id);
        })
        .then(() => {
          let ids = this.multi.scoreSettingIds.split(",");
          var index = ids.indexOf(data.id);
          ids = ids.splice(index, 1);
          this.multi.scoreSettingIds = ids.toString();
          return updateMultiSetting(this.multi);
        })
        .then(() => {
          this.getScoreSettingList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
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
</style>
