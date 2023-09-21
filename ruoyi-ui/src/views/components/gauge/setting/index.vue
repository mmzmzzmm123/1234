<template>

  <div>
    <el-divider content-position="left">得分设置</el-divider>
    <el-table :data="scoreList" style="width: 100%" size="mini">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form ref="form" label-width="80px">
            <el-form-item :label="gaugeType === 3 ? '性格类型' : '得分范围'">
              <template v-if="gaugeType === 3">
                <el-select v-if="gaugeType === 3" size="small" style="width: 150px" v-model="props.row.lat" placeholder="纬度" clearable>
                  <el-option
                    v-for="item in gaugeMbtiRes"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </template>
              <template v-else>
                <el-col :span="7">
                  <el-input-number style="width: 100%;" controls-position="right" size="small" v-model="props.row.start"/>
                </el-col>
                <el-col class="line" :span="1">-</el-col>
                <el-col :span="7">
                  <el-input-number style="width: 100%;" controls-position="right" size="small" v-model="props.row.end"/>
                </el-col>
              </template>
            </el-form-item>
<!--            <el-form-item label="建议">-->
<!--              <image-upload v-model="props.row.proposal" @input="modifySetting(props.row)" :extraData="extraData"/>-->
<!--            </el-form-item>-->
            <template v-if="gaugeType === 4">
              <el-form-item label="测评结果">
                <el-input size="small" maxlength="255" show-word-limit v-model="props.row.memo1" placeholder="测评结果" />
              </el-form-item>
              <el-form-item label="结果描述">
                <el-input size="small" maxlength="255" show-word-limit v-model="props.row.memo2" placeholder="结果描述" />
              </el-form-item>
              <el-form-item label="得分解读">
                <el-input size="small" maxlength="255" show-word-limit v-model="props.row.memo3" placeholder="得分解读" />
              </el-form-item>
              <el-form-item label="抑郁表现">
                <el-input size="small" maxlength="255" show-word-limit v-model="props.row.memo4" placeholder="抑郁表现" />
              </el-form-item>
              <el-form-item label="得分建议">
                <editor v-model="props.row.result" placeholder="请输入得分建议" :min-height="192"/>
              </el-form-item>
            </template>
            <template v-else>
              <el-form-item label="结论">
                <editor v-model="props.row.result" placeholder="请输入内容" :min-height="192"/>
              </el-form-item>
            </template>

            <el-form-item>
              <el-button type="primary" @click="modifySetting(props.row)">确 定</el-button>
            </el-form-item>

          </el-form>
        </template>
      </el-table-column>
      <el-table-column :label="gaugeType === 3 ? '性格类型' : '得分范围'">
        <template slot-scope="props">
          <span v-if="gaugeType === 3">
            {{ props.row.lat }}
          </span>
          <span v-else>
            {{ props.row.start }}-{{ props.row.end }}
          </span>
        </template>
      </el-table-column>
<!--      <el-table-column label="描述" prop="proposal">-->
<!--        <template slot-scope="props" v-if="props.row.proposal!=null">-->
<!--          <image-preview v-for="url in props.row.proposal.split(',')" :key="url" :src="url" :width="50" :height="50"/>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="操作" align="center" min-width="100px" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['gauge:setting:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-button type="primary" size="small" @click="insertSetting()">增加得分</el-button>


  </div>
</template>
<script>
import {listMultiSetting} from "@/api/gauge/multiSetting";
import {
  listSetting,
  addSetting,
  updateSetting,
  delSetting,
} from "@/api/gauge/setting";

export default {
  components: {},
  props: {
    gaugeId: {
      type: Number,
      default: null,
    },
    gaugeType: {
      type: Number,
      default: null,
    }
  },
  watch: {
    gaugeId: {
      handler(val) {
        if (val !== this.currentValue) {
          this.getSettingList(val);
        }
      },
      deep: true,
      immediate: true,
    },
  },
  data() {
    return {
      gaugeMbtiRes: this.$constants.gaugeMbtiRes,
      extraData: {
        module: 'gauge'
      },
      form: {},
      scoreList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 999,
        gaugeId: null,
      },
    };
  },
  methods: {
    async insertSetting() {
      //获取
      let score = this.scoreList[this.scoreList.length - 1];
      if (!score) {
        score = {
          id: "12987122",
          start: 1,
          end: 10,
          proposal: "",
        }
      }

      await addSetting({
        gaugeId: this.gaugeId,
        start: score.end,
        end: score.end + 20,
        proposal: "",
      });
      this.getSettingList();
    },
    async getSettingList() {
      this.queryParams.gaugeId = this.gaugeId
      let res = await listSetting(this.queryParams);
      this.scoreList = res.rows;
    },
    async modifySetting(data) {
      await updateSetting(data);
      this.$modal.msgSuccess("保存成功");
    },
    handleDelete(data) {
      this.$modal
        .confirm(
          "是否确认删除得分设置（" + data.start + "-" + data.end + "）？"
        )
        .then(function () {
          return delSetting(data.id);
        })
        .then(() => {
          this.getSettingList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {
        });
    },
  },
};
</script>
