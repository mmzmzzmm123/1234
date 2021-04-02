<template>
  <div class="short_cut_com_wrapper">
    <div class="header">
      <el-button icon="el-icon-refresh" size="mini" @click="getList" circle />
    </div>
    <el-table
      :data="dataList"
      ref="shortCutTable"
      highlight-current-row
      @current-change="handleOnCurrentChange"
      height="800"
    >
      <el-table-column prop="name" label="对象" align="center">
        <template slot-scope="scope">
          <el-popover
            placement="left"
            width="200"
            trigger="click"
            title="修改对象名称"
            @hide="handleOnHide"
          >
            <el-input
              v-model="scope.row.name"
              @change="(val) => handleOnNameChange(val, scope.row)"
            />
            <span slot="reference" style="cursor: pointer">
              {{ scope.row.name }}
            </span>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column prop="type" label="可用类型" align="center">
        <template slot-scope="scope">
          <AutoHideInfo :data="translateType(scope.row.type)" :line="10" />
        </template>
      </el-table-column>
      <el-table-column prop="priview" label="预览" align="center">
        <template slot-scope="scope">
          <el-popover
            placement="left"
            trigger="click"
            :title="`「${scope.row.name}」预览`"
          >
            <!-- 菜谱预览 -->
            <RecipesCom
              v-if="scope.row.type.includes('0')"
              :data="scope.row.data"
              :numDay="scope.row.data.numDay"
              preview
            />
            <!-- 菜品预览 -->
            <el-table border :data="scope.row.data.igdList" v-else size="mini">
              <el-table-column align="center" label="食材" prop="name" />
              <el-table-column align="center" label="分量估算">
                <template slot-scope="scope">
                  <span>{{
                    `${cusWeightDict[scope.row.cusWeight]}${
                      cusUnitDict[scope.row.cusUnit]
                    }`
                  }}</span>
                </template>
              </el-table-column>
              <el-table-column align="center" label="重量(g)" prop="weight" />
            </el-table>
            <el-button type="text" size="mini" slot="reference">预览</el-button>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="操作" :width="60" align="center">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="handleOnDelete(scope.row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
import {
  getShortCut,
  removeShortCut,
  editShortCut,
} from "@/utils/shortCutUtils";
import AutoHideInfo from "@/components/AutoHideInfo";
import { createNamespacedHelpers } from "vuex";
import RecipesCom from "../../RecipesView/RecipesCom";
const { mapState, mapMutations, mapGetters } = createNamespacedHelpers(
  "recipes"
);
import { messageTypes } from "@/utils";
export default {
  name: "ShortCutCom",
  components: {
    AutoHideInfo,
    RecipesCom,
  },
  data() {
    return {
      dataList: [],
      modifingId: 0,
    };
  },
  created() {
    this.getList();
  },
  mounted() {
    window.addEventListener("storage", this.storageListener);
    window.addEventListener("message", this.messageListener);
  },
  beforeDestroy() {
    window.removeEventListener("storage", this.storageListener);
    window.removeEventListener("message", this.messageListener);
  },
  computed: {
    names() {
      return this.dataList.map((obj) => obj.name);
    },
    ...mapState(["curShortCutObj"]),
    ...mapGetters(["cusUnitDict", "cusWeightDict"]),
  },
  methods: {
    storageListener(e) {
      const { key } = e;
      if (key === "shortCut") {
        this.getList();
      }
    },
    messageListener(e) {
      const { data } = e;
      if (data.type === messageTypes.UPDATE_SHORTCUT) {
        this.getList();
      }
    },
    getList() {
      getShortCut().then((data) => {
        this.dataList = data;
        // console.log(this.dataList);
      });
    },
    handleOnDelete(data) {
      removeShortCut(data.id).then((res) => {
        this.getList();
      });
    },
    handleOnCurrentChange(data) {
      this.setCurShortCutObj({ data });
    },
    handleOnNameChange(val, data) {
      this.modifingId = data.id;
    },
    handleOnHide() {
      if (this.modifingId) {
        const tarObj = this.dataList.find((obj) => obj.id === this.modifingId);
        editShortCut({
          id: this.modifingId,
          name: tarObj.name,
        });
      }
    },
    translateType(type) {
      return type.map((t) => {
        if (t === "0") {
          return "整天";
        } else if (t === "1") {
          return "早餐";
        } else if (t === "2") {
          return "早加餐";
        } else if (t === "3") {
          return "午餐";
        } else if (t === "4") {
          return "午加餐";
        } else if (t === "5") {
          return "晚餐";
        } else if (t === "6") {
          return "晚加餐";
        }
      });
    },
    ...mapMutations(["setCurShortCutObj"]),
  },
};
</script>
<style lang="scss" scoped>
.short_cut_com_wrapper {
  .header {
    text-align: right;
    padding-bottom: 8px;
  }
}
</style>
