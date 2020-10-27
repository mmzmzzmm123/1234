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
            <span class="box-card-title">{{ title }}</span>
          </div>
          <div class="text item">
            <h3 class="box-card-title">内容</h3>
            <div class="pad-left" v-html="note"></div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {
  treeselectstudy,
  getAssessmentcontent,
  getAssessmentcontentbyparentid,
} from "@/api/benyi/assessmentcontent";
import { listAssessmentintroduce } from "@/api/benyi/assessmentintroduce";

export default {
  name: "Assessmentstudy",
  data() {
    return {
      // 一日流程名称
      name: undefined,
      //标题
      title: "儿童学习与发展评估介绍",
      //目的
      note: "",
      // 树状显示类型
      treeOptions: [],
      // 树结构
      defaultProps: {
        children: "children",
        label: "label",
      },
      // 查询参数
      queryParams: {
        id: undefined,
      },
    };
  },
  watch: {
    // 根据名称筛选部门树
    name(val) {
      this.$refs.tree.filter(val);
    },
  },
  created() {
    this.getTreeselect();
    this.getNote();
  },
  methods: {
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselectstudy().then((response) => {
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
      console.log("node:" + data.id);
      this.title = data.label;
      if (data.id == "-1") {
        this.getNote();
      } else {
        this.queryParams.id = data.id;

        this.getTaskList();
      }
    },
    /**查询评估介绍 */
    getNote() {
      listAssessmentintroduce(null).then((response) => {
        console.log("评估介绍:" + response.rows[0].content);
        this.note = response.rows[0].content;
      });
    },
    /** 查询一日流程任务列表 */
    getTaskList() {
      getAssessmentcontentbyparentid(this.queryParams.id).then((response) => {
        var tmp = "";
        response.data.forEach((element) => {
          tmp = tmp + element.sort + ". " + element.name + "</br>";
        });
        this.note = tmp;
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
  line-height: 22px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}
.box-card-title {
  display: flex;
  align-items: center;
  font-size: 16px;
  &::before {
    content: "";
    margin-right: 8px;
    width: 4px;
    height: 16px;
    background: #1890ff;
  }
  &.mr {
    margin: 10px 0;
  }
}
.box-card-case {
  margin: 0;
  font-size: 14px;
  font-weight: 700;
  display: flex;
  align-items: center;
  &::before {
    content: "";
    margin-right: 8px;
    width: 4px;
    height: 14px;
    background: #2c3e50;
  }
  &.mr {
    margin: 10px 0;
  }
}
.box-card-info {
  font-size: 14px;
  font-weight: 700;
}
.pad-left {
  padding-left: 15px;
}

// 禁止复制
div {
  -webkit-touch-callout: none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}
</style>