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
            <span class="box-card-title">{{title}}</span>
          </div>
          <div class="text item">
            <h3 class="box-card-title">导言</h3>
            <div class="pad-left" v-html="content"></div>
          </div>
          <div class="text item">
            <h3 class="box-card-title">目的</h3>
            <div class="pad-left" v-html="note"></div>
          </div>
          <div v-for="(item, index) in dayflowtaskList" :key="index" class="text item">
            <h3 class="box-card-title mr">{{item.taskLable}}</h3>
            <p class="pad-left">{{item.taskContent}}</p>
            <div class="pad-left">
              <div
                v-for="(item_standard, index_standard) in (dayflowstandardList.filter(p=>p.taskCode==item.code))"
                :key="index_standard"
                class="text item"
              >
                <h3 class="box-card-case mr">{{item_standard.standardTitle}}</h3>
                <h3 class="box-card-info">解读：</h3>
                <div
                  v-for="(item_unscramble, index_unscramble) in (dayflowunscrambleList.filter(p=>p.standardId==item_standard.id))"
                  :key="index_unscramble"
                  class="text item pad-left"
                >{{item_unscramble.sort}}){{item_unscramble.content}}</div>
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
      content: "寓教育于生活。以游戏为基本活动。没有爱就没有教育。教师素质决定教育品质。一童一世界，尊重差异，关注个性化发展。孩子喜欢的老师就是好老师，孩子喜欢的幼儿园就是好幼儿园。",
      //目的
      note: "《幼儿园（3-6）标准化一日流程》（下称《一日流程》）的基本目的，就是要将国家教育纲领性文件中所主张的幼儿园教育理念完全落地。也就是能够让这些理念在幼儿园保教人员指尖上操作和实现。其纲领性文件包括《幼儿园工作规程》（简称《规程》），《幼儿园教育指导纲要》（简称《纲要》），《3-6岁儿童学习与发展指南》（简称《指南》）。这三部纲领中的教育理念不仅仅是确定《一日流程》全部内容的基本依据，而且也自始至终地指导了《一日流程》内容在一线进行实验的整个过程。因此，在其实质上，《一日流程》本身就是将国家三大教育理念——“寓教于生活”、“以游戏为基本活动”以及“以儿童为本”所支撑的教育过程进行分解和物化。",
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
    this.getChildNodeList();
  },
  methods: {
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.treeOptions = response.data;
      });
    },
    getChildNodeList() {
      listStandard(null).then(response => {
        this.dayflowstandardList = response.rows;
      });
      listUnscramble(null).then(response => {
        this.dayflowunscrambleList = response.rows;
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
      if (data.id >= 9999) {
      } else {
        this.title = data.label;
        this.getTaskList();
      }
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
    }
  }
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
</style>