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

export default {
  name: "Assessmentstudy",
  data() {
    return {
      // 一日流程名称
      name: undefined,
      //标题
      title: "儿童学习与发展评估介绍",
      //目的
      note:
        "儿童学习与发展评估对象是幼儿园3-6岁儿童。本一的“儿童学习与发展评估”（以下称“评估”），是完全基于教育部《3-6岁儿童学习与发展指南》所构建。尽管“评估”具备工具性质，但更重要的事实是，它“通过提出3-6岁各年龄儿童学习与发展目标和相应的教育建议，帮助幼儿园教师和家长了解3-6岁幼儿学习与发展的基本规律和特点，建立对幼儿发展的合理期望，实施科学的保育和教育，让幼儿度过快乐而有意义的童年”（《指南》）。3-6岁三个年龄段的“儿童学习与发展评估”包括5大领域，11个分支领域，32个指标，以及若干个评估元素。评估以班级中每个孩子为基本单位。建议在班长和教师之间分配所评估孩子的数量。评估过程以日常生活对孩子的观察为主，随时在线记录和标记满足于各个指标的元素。其最终评估结果，将会在线自动生成“雷达图”。需要注意的是，评估的基本目的，是为了更有效地对每个孩子实施个性化教育。因此，不要把评估结果当成“测验”或“考核”手段或工具，更不要直接将结果拿给家长看，以免造成误读。",
      // 根据一日流程id查到的名下任务列表
      dayflowtaskList: [],
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
      this.queryParams.id = data.id;
      this.title = data.label;
      this.getTaskList();
      // console.log(this.dayflowtaskList[date.id])
      // this.getStandardList();
    },
    /** 查询一日流程任务列表 */
    getTaskList() {
      getAssessmentcontentbyparentid(this.queryParams.id).then((response) => {
        var tmp = "";
        response.data.forEach((element) => {
          tmp = tmp + element.name + "</br>";
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