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
      <div>
        <span>流程中所含任务</span>
      </div>
      <el-col :span="20" :xs="24" v-for="(item, index) in dayflowtaskList" :key="index" >
        <el-card :body-style="{ padding: '2px' }">
          <div class="to-detail">
            <el-tooltip effect="dark" :content="item.taskLable" placement="right">
              <div>
                <p class="info-title">{{item.taskLable}}</p>
              </div>
            </el-tooltip>
            <p class="info-title info-title-name">该任务所含标准个数:{{ item.standardCount }}</p>
            <div class="bottom">
              <time class="time">{{ parseTime(item.createtime) }}</time>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listDetail, getDetail } from "@/api/benyi/dayflow/dayflowmanger";
import { listDayflowtask } from "@/api/benyi/dayflow/dayflowtask";
import { listStandard } from "@/api/benyi/dayflow/biaozhun/standard"
import { treeselect } from "@/api/benyi/dayflow/dayflowmanger";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";


export default {
  name: "Detail",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 一日流程名称
      name: undefined,
      // 一日流程id
      id: undefined,
      
      // 根据一日流程id查到的名下任务列表
      dayflowtaskList: [],
      // 根据任务查询到名下标准
      dayflowstandardList: [],
      // 树状显示类型
      treeOptions: [],
      // 树结构
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 查询参数
      queryParams: {
        detailId: undefined,
        taskCode: undefined,
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
      this.getTaskList();
      // console.log(this.dayflowtaskList[date.id])
      // this.getStandardList();
    },
    /** 查询一日流程任务列表 */
    getTaskList() {
      this.loading = true;
      listDayflowtask(this.queryParams).then(response => {
        this.dayflowtaskList = response.rows;
        // console.log(this.dayflowtaskList);
        this.loading = false;
      });
    },
    /** 查询任务标准列表 */
    // getStandardList() {
    //   this.loading = true;
    //   const taskCode = this.dayflowtaskList[this.id-1].code;
    //   listStandard(taskCode).then(response => {
    //     this.dayflowstandardList = response.rows;
    //     console.log(this.dayflowstandardList);
    //     this.loading = false;
    //   });
    // },
  }
};
</script>

<style>
.time {
  line-height: 12px;
  font-size: 12px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.to-detail {
  /*cursor: pointer;*/
  padding: 14px;
}

.info-title {
  width: 100%; /*根据自己项目进行定义宽度*/
  overflow: hidden; /*设置超出的部分进行影藏*/
  text-overflow: ellipsis; /*设置超出部分使用省略号*/
  white-space: nowrap; /*设置为单行*/
}

.info-title-name {
  font-size: 12px;
}
</style>