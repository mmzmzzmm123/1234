<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="name"
            placeholder="请输入名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="treeOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <el-col :span="20" :xs="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>{{title}}</span>
          </div>
          <div class="text item">
            导言
            <br />
            <label v-html="content"></label>
          </div>
          <div class="text item">
            目的
            <br />
            <label v-html="note"></label>
          </div>
          <div v-for="(item, index) in dayflowtaskList" :key="index" class="text item">
            {{item.taskLable}}
            <div
              v-for="(item_standard, index_standard) in (dayflowstandardList.filter(p=>p.taskCode==item.code))"
              :key="index_standard"
              class="text item"
            >{{item_standard.standardTitle}}
            <br/>解读
            <div
              v-for="(item_unscramble, index_unscramble) in (dayflowunscrambleList.filter(p=>p.standardId==item_standard.id))"
              :key="index_unscramble"
              class="text item"
            >{{item_unscramble.sort}}-{{item_unscramble.content}}
            
            </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listDayflowtask } from "@/api/benyi/dayflow/dayflowtask";
import { listStandard } from "@/api/benyi/dayflow/biaozhun/standard";
import { listUnscramble } from "@/api/benyi/dayflow/unscramble";
import { treeselect, getDetail } from "@/api/benyi/dayflow/dayflowmanger";

export default {
  name: "Detail",
  data() {
    return {
      // 一日流程名称
      name: undefined,
      // 一日流程id
      id: undefined,
      //标题
      title: "一日流程",
      //导言
      content: undefined,
      //目的
      note: undefined,
      // 根据一日流程id查到的名下任务列表
      dayflowtaskList: [],
      // 根据任务查询到名下标准
      dayflowstandardList: [],
      //一日流程解读
      dayflowunscrambleList: [],
      // 树状显示类型
      treeOptions: [],
      // 树结构
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 查询参数
      queryParams: {
        detailId: undefined
      },
      queryStandardParams: {
        taskCode: undefined
      }
    };
  },
  watch: {
    // 根据名称筛选部门树
    name(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getTreeselect();
  },
  methods: {
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.treeOptions = response.data;
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.detailId = data.id;
      this.title = data.label;
      this.getTaskList();
      // console.log(this.dayflowtaskList[date.id])
      // this.getStandardList();
    },
    /** 查询一日流程任务列表 */
    getTaskList() {
      listDayflowtask(this.queryParams).then(response => {
        this.dayflowtaskList = response.rows;
      });
      getDetail(this.queryParams.detailId).then(response => {
        this.content = response.data.content;
        this.note = response.data.note;
      });
      listStandard(null).then(response => {
        this.dayflowstandardList = response.rows;
      });
      listUnscramble(null).then(response => {
        this.dayflowunscrambleList = response.rows;
      });
    }
  }
};
</script>
<style>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}
</style>