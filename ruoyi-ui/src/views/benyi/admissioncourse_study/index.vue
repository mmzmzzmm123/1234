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
            :expand-on-click-node="true"
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
            <div class="pad-left" v-html="content"></div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { treeselect, getAdmissioncourse } from "@/api/benyi/admissioncourse";

export default {
  name: "Admissioncoursestudy",
  data() {
    return {
      // 一日流程名称
      name: undefined,
      // 一日流程id
      id: undefined,
      //标题
      title: "入学准备课程",
      //导言
      content: "",
      // 树状显示类型
      treeOptions: [],
      // 树结构
      defaultProps: {
        children: "children",
        label: "label",
      },
      // 查询参数
      queryParams: {
        detailId: undefined,
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
      getAdmissioncourse(this.queryParams.detailId).then((response) => {
        if (response.data.content != null) {
          this.content = response.data.content;
          this.title = response.data.titlename;
        }
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