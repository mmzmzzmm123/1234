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
          <div   class="block" v-loading="loading">
            <div class="block-item-title" v-html="note" v-show="isshow"></div>
            <div
              v-for="(itemOne, index) in assessmentList.filter(
                (p) => p.parentId == queryParams.id
              )"
              :key="index"
              v-show="!isshow"
            >
              <h2 class="block-item-title flex align-center">
                {{ itemOne.sort }}.{{ itemOne.name }}
              </h2>
              <ul class="block-content">
                <li
                  v-for="(itemTwo, index) in assessmentList.filter(
                    (p) => p.parentId == itemOne.id
                  )"
                  :key="index"
                >
                  <p class="block-content-title">
                    <span class="num">{{ itemTwo.sort }}. </span
                    >{{ itemTwo.name }}
                  </p>
                  <div
                    class="checkbox-content"
                    v-for="(itemThree, index) in assessmentList.filter(
                      (p) => p.parentId == itemTwo.id
                    )"
                    :key="index"
                  >
                    <p class="checkbox-item flex align-center">
                      {{ itemThree.sort }}.{{ itemThree.name }}
                    </p>
                    <div class="check-info" v-if="itemThree.ckbz">
                      {{ itemThree.ckbz }}
                      <!-- <p>男孩：身高：94.9-111.7厘米，体重：12.7-21.2公斤</p>
                  <p>女孩：身高：94.1-111.3厘米，体重：12.3-21.5公斤</p> -->
                    </div>
                    <div
                      class="checkbox-content"
                      v-for="(itemFour, index) in assessmentList.filter(
                        (p) => p.parentId == itemThree.id
                      )"
                      :key="index"
                    >
                      <p class="checkbox-item flex align-center">
                        {{ itemFour.sort }}.{{ itemFour.name }}
                      </p>
                      <div class="check-info" v-if="itemFour.ckbz">
                        {{ itemFour.ckbz }}
                        <!-- <p>男孩：身高：94.9-111.7厘米，体重：12.7-21.2公斤</p>
                  <p>女孩：身高：94.1-111.3厘米，体重：12.3-21.5公斤</p> -->
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {
  treeselectstudy,
  childnote,
  getAssessmentcontent,
  getAssessmentcontentbyparentid,
} from "@/api/benyi/assessmentcontent";
import { listAssessmentintroduce } from "@/api/benyi/assessmentintroduce";

export default {
  name: "Assessmentstudy",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 名称
      name: undefined,
      //标题
      title: "儿童学习与发展评估介绍",
      //目的
      note: "",
      isshow: true,
      // 树状显示类型
      treeOptions: [],
      assessmentList: [],
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
      // console.log("node:" + data.id);
      this.title = data.label;
      if (data.id == "-1") {
        this.getNote();
        this.isshow = true;
      } else {
        this.queryParams.id = data.id;
        this.getTaskList();
        this.isshow = false;
      }
    },
    /**查询评估介绍 */
    getNote() {
      this.loading = true;
      listAssessmentintroduce(null).then((response) => {
        // console.log("评估介绍:" + response.rows[0].content);
        this.note = response.rows[0].content;
        this.loading = false;
      });
    },
    /** 查询任务列表 */
    getTaskList() {
      // console.log("id" + this.queryParams.id);
      // getAssessmentcontentbyparentid(this.queryParams.id).then((response) => {
      //   var tmp = "";
      //   response.data.forEach((element) => {
      //     tmp = tmp + element.sort + ". " + element.name + "</br>";
      //   });
      //   this.note = tmp;
      // });
      this.loading = true;
      childnote(this.queryParams.id).then((res) => {
        // console.log(res.data.length);
        // console.log("id" + this.queryParams.id);
        this.assessmentList = res.data;
        this.loading = false;
      });
    },
  },
};
</script>
<style lang="scss" scoped>
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
  max-height: calc(100vh - 180px);
  overflow-y: auto;
}
.el-card {
  max-height: calc(100vh - 130px);
  overflow-y: auto;
}
.block {
  padding: 10px;
  color: #333;
  .block-item-title {
    padding: 10px 0;
    margin: 0;
    font-size: 16px;
    font-weight: 500;
    &::before {
      content: "";
      margin-right: 8px;
      width: 4px;
      height: 14px;
      background: #1890ff;
    }
  }

  .block-content {
    border-radius: 5px;
    padding: 10px;
    background: #fcfcfc;
    .block-content-title {
      font-size: 14px;
      font-weight: bold;
      line-height: 24px;
      padding-bottom: 5px;
      .num {
        font-size: 16px;
      }
    }
    .checkbox-content {
      padding-left: 20px;
      margin-bottom: 15px;
    }
    .checkbox-item {
      font-size: 14px;
      line-height: 22px;
    }
    .check-info {
      line-height: 18px;
      font-size: 12px;
      color: #999;
      p {
        padding-left: 10px;
      }
    }
    .el-checkbox {
      display: flex;
      white-space: normal;
      .el-checkbox__input {
        margin-top: 2px;
      }
    }
  }
}
@media (max-width: 768.98px) {
  .title span {
    font-size: 12px;
  }
}
</style>
