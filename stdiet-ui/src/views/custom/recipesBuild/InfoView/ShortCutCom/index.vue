<template>
  <div class="short_cut_com_wrapper">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="关键词" prop="key">
        <el-input
          v-model.trim="queryParams.key"
          placeholder="请输入名称/种类"
          clearable
          @clear="getList"
          @keyup.native="getList"
          size="small"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="cyan"
          icon="el-icon-search"
          size="mini"
          @click="getList"
          >搜索</el-button
        >
      </el-form-item>
    </el-form>

    <div class="header">
      <el-button
        icon="el-icon-search"
        size="mini"
        @click="showSearch = !showSearch"
        circle
        :title="showSearch ? '隐藏搜索' : '显示搜索'"
      />
      <el-button
        icon="el-icon-refresh"
        size="mini"
        @click="getList"
        circle
        title="刷新"
      />
      <el-button
        icon="el-icon-delete"
        size="mini"
        circle
        :title="'清空快捷列表'"
        @click="handleOnMuchDelete"
      />
    </div>
    <el-table
      :data="dataList"
      ref="shortCutTable"
      row-key="id"
      highlight-current-row
      row-class-name="draggable"
      @current-change="handleOnCurrentChange"
      height="700"
    >
      <el-table-column
        prop="name"
        label="对象"
        align="center"
        class-name="handle"
      >
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
              <el-table-column
                align="center"
                label="食材"
                prop="name"
                width="100"
              />
              <el-table-column align="center" label="分量估算" width="100">
                <template slot-scope="scope">
                  <span>{{
                    `${cusWeightDict[scope.row.cusWeight]}${
                      cusUnitDict[scope.row.cusUnit]
                    }`
                  }}</span>
                </template>
              </el-table-column>
              <el-table-column
                align="center"
                label="重量(g)"
                prop="weight"
                width="100"
              />
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
import Sortable from "sortablejs";
import {
  getShortCut,
  removeShortCut,
  editShortCut,
  removeMuchShortCut,
  updateSortCut,
} from "@/utils/shortCutUtils";
import AutoHideInfo from "@/components/AutoHideInfo";
import { createNamespacedHelpers } from "vuex";
import RecipesCom from "../../RecipesView/RecipesCom";
const { mapState, mapMutations, mapGetters } =
  createNamespacedHelpers("recipes");
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
      showSearch: false,
      queryParams: {
        key: null,
      },
    };
  },
  created() {
    this.getList();
  },
  mounted() {
    window.addEventListener("storage", this.storageListener);
    window.addEventListener("message", this.messageListener);

    Sortable.create(
      this.$refs.shortCutTable.$el.querySelector(".el-table__body tbody"),
      {
        animation: 150,
        handle: ".handle",
        draggable: ".draggable",
        onEnd: (evt) => {
          const { newIndex, oldIndex } = evt;
          if (newIndex === oldIndex) {
            return;
          }
          const newDataList = JSON.parse(JSON.stringify(this.dataList));
          const [tarObj] = newDataList.splice(oldIndex, 1);
          newDataList.splice(newIndex, 0, tarObj);

          updateSortCut(newDataList);
          this.dataList = newDataList;
        },
      }
    );
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
        this.getList(data.setCurrent);
      }
    },
    getList(setCurrent) {
      getShortCut(this.queryParams.key).then((data) => {
        this.dataList = data;
        //超过10个就显示搜索按钮
        if (this.dataList && this.dataList.length > 5 && !this.showSearch) {
          this.showSearch = true;
        }
        // console.log(this.dataList);
        if (setCurrent) {
          this.$refs.shortCutTable.setCurrentRow(data[0]);
        }
      });
    },
    handleOnDelete(data) {
      removeShortCut(data.id).then((res) => {
        this.getList();
        if (this.curShortCutObj.id === data.id) {
          this.setCurShortCutObj({});
        }
      });
    },
    handleOnMuchDelete() {
      if (this.dataList && this.dataList.length > 0) {
        let ids = [];
        this.dataList.forEach((item, index) => {
          ids.push(item.id);
        });
        this.$confirm(
          "是否确定清除当前 " + ids.length + " 条快捷数据?",
          "警告",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }
        )
          .then(function () {
            return removeMuchShortCut(ids);
          })
          .then((response) => {
            this.getList();
            if (ids.indexOf(this.curShortCutObj.id) != -1) {
              this.setCurShortCutObj({});
            }
          })
          .catch(function () {});
      }
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
