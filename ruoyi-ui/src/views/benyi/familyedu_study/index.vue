<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="8" :xs="24">
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
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            :default-expand-all="false"
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <el-col :span="16" :xs="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span class="box-card-title">{{ title }}</span>
          </div>
          <div class="text item" v-show="title1">
            <div class="pad-left" v-html="note"></div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { treeselect, getFamilyedu } from "@/api/benyi/familyedu";

export default {
  name: "familyedu_study",
  data() {
    return {
      // 主题整合名称
      name: undefined,
      // 主题整合id
      id: undefined,
      //标题
      title: "本一家庭教育手册",
      title1: "",
      //目的
      note: "",
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
      console.log(data.id);
      if (data.id >= 9999) {
      } else {
        this.title = data.label;
        this.getFamilyeduDetails();
      }
      // console.log(this.dayflowtaskList[date.id])
      // this.getStandardList();
    },
    getFamilyeduDetails() {
      getFamilyedu(this.id).then((response) => {
        console.log(response);
        this.title1 = response.data.title;
        this.note = response.data.content;
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
.el-tree {
  min-width: 100%;
  display: inline-block;
}
.tree {
  overflow: auto;
  max-height: 300px;
}
</style>