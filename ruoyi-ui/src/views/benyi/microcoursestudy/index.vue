<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="6" :xs="24">
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
        <div class="head-container tree">
          <el-tree
            :data="treeOptions"
            :props="defaultProps"
            :expand-on-click-node="true"
            :filter-node-method="filterNode"
            ref="tree"
            :default-expand-all="false"
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <el-col :span="18" :xs="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span class="box-card-title">{{ title }}</span>
          </div>
          <div class="text item" v-show="title1">
            <el-button
              type="primary"
              plain
              size="mini"
              icon="el-icon-printer"
              @click="prints"
              >打印</el-button
            >
            <div class="pad-left" v-html="note" ref="printMe"></div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { treeselect, getMicrocourse } from "@/api/benyi/microcourse";

export default {
  name: "Microcoursestudy",
  data() {
    return {
      // 主题整合名称
      name: undefined,
      // 主题整合id
      id: undefined,
      //标题
      title: "微型课程",
      title1: "概论",
      //目的
      note: "微 型 课 程     王东异  主编",
      // 树状显示类型
      treeOptions: [],
      // 树结构
      defaultProps: {
        children: "children",
        label: "label",
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
      treeselect().then((response) => {
        this.treeOptions = response.data;
        //console.log(response.data);
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.id = data.id;
      //console.log(data.id);
      if (data.id >= 9999) {
      } else {
        this.title = data.label;
        this.getMicrocourseDetails();
      }
      // console.log(this.dayflowtaskList[date.id])
      // this.getStandardList();
    },
    getMicrocourseDetails() {
      getMicrocourse(this.id).then((response) => {
        this.title1 = response.data.title;
        this.note = response.data.contents;
      });
    },
    //打印
    prints() {
      //console.log(this.$refs.printMe);
      this.$print(this.$refs.printMe);
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
.pad-left {
  padding-left: 15px;
  text-align: center;
  font-size: 20px;
  font-weight: 700;
  line-height: 40px;
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
.el-tree {
  min-width: 100%;
  display: inline-block;
}
.tree {
  overflow:auto;
  max-height: 600px;
}
</style>