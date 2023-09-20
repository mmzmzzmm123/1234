<template>

  <div>
    <el-divider content-position="left">得分设置</el-divider>
    <el-table :data="scoreList" style="width: 100%" size="mini">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form ref="form" label-width="80px">
            <el-form-item :label="gaugeType === 3 ? '性格类型' : '得分范围'">
              <template v-if="gaugeType === 3">
                <el-select v-if="gaugeType === 3" @change="modifySetting(props.row)" size="small" style="width: 150px" v-model="props.row.lat" placeholder="纬度" clearable>
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
                  <el-input-number style="width: 100%;" controls-position="right" size="small" v-model="props.row.start" @change="modifySetting(props.row)"></el-input-number>
                </el-col>
                <el-col class="line" :span="1">-</el-col>
                <el-col :span="7">
                  <el-input-number style="width: 100%;" controls-position="right" size="small" v-model="props.row.end" @change="modifySetting(props.row)"></el-input-number>
                </el-col>
              </template>
            </el-form-item>
            <el-form-item label="建议">
              <image-upload v-model="props.row.proposal" @input="modifySetting(props.row)" :extraData="extraData"/>
            </el-form-item>
            <el-form-item label="结论">
              <editor v-model="props.row.result" placeholder="请输入内容" @blur="modifySetting(props.row)"
                      :min-height="192"/>
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
      <el-table-column label="建议" prop="proposal">
        <template slot-scope="props" v-if="props.row.proposal!=null">
          <image-preview v-for="url in props.row.proposal.split(',')" :key="url" :src="url" :width="50" :height="50"/>
        </template>
      </el-table-column>
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
      let res = await listSetting({gaugeId: this.gaugeId});
      this.scoreList = res.rows;
    },
    async modifySetting(data) {
      await updateSetting(data);
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
