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
        <el-card>
          <div slot="header" class="clearfix">
            <span>内容介绍</span>
          </div>
            <div 
              v-for="(item, index) in dayflowtaskList" 
              :key="index"
              class="text item">
              {{' 任务名称 ' + item.taskLable + "     "
              + '所含标准个数' + item.standardCount  }}
            </div>
            <!-- <div class="text item">
              <el-form-item label="意见建议" prop="content">
                <el-input
                  type="textarea"
                  v-model="form.content"
                  :disabled="dis"
                  maxlength="500"
                  placeholder="请您对视频内容和讲师作出评价，并告诉我们你喜欢或不喜欢的理由，以便使我们改进对您的服务品质。谢谢您的支持！"
                />
              </el-form-item>
            </div> -->
            <!-- <div class="text item">
              <el-form-item label="标题">{{title}}</el-form-item>
            </div>
            <div class="text item">
              <el-form-item label="讲师">{{lecturername}}</el-form-item>
            </div>
            <div class="text item">
              <el-form-item label="简介">{{information}}</el-form-item>
            </div>
            <div class="text item">
              <el-form-item label="评分" prop="score">
                <el-rate v-model="form.score" :disabled="dis" :show-score="dis"></el-rate>
                <el-input v-model="form.videoid" v-if="false" />
                <el-input v-model="form.lecturerid" v-if="false" />
              </el-form-item>
            </div> -->
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listDetail, getDetail } from "@/api/benyi/dayflow/dayflowmanger";
import { listDayflowtask } from "@/api/benyi/dayflow/dayflowtask";
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
    },
    /** 查询一日流程任务列表 */
    getTaskList() {
      this.loading = true;
      listDayflowtask(this.queryParams).then(response => {
        this.dayflowtaskList = response.rows;
        console.log(this.dayflowtaskList);
        this.loading = false;
      });
    },
  }
};
</script>