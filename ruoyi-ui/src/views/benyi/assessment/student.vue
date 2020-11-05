<template>
  <div class="app-container">
    <div class="flex align-center justify-between">
      <p class="title flex align-center">
        <span>姓名：张三 </span>
        <span>出生日期：男 </span>
        <span>班级：圆圆班 </span>
         <span>学期：2020-2021 </span>
         <span>班长：2021 </span>
      </p>
      <el-button
        type="primary"
        icon="el-icon-s-data"
        size="mini"
        @click="submitForm"
        >生成图表</el-button
      >
    </div>
    <!-- <el-table
      v-loading="loading"
      :data="assessmentcontentList"
      row-key="id"
      default-expand-all
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column prop="name" label="评估内容"></el-table-column>
      <el-table-column label="是否达标" align="center">
        <template slot-scope="scope">
          <el-checkbox
            v-if="scope.row.iselement == 'Y'"
            v-model="checkedValues"
            @change="handleSelectionChange"
            >是</el-checkbox
          >
        </template>
      </el-table-column>
    </el-table> -->
    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
      <el-tab-pane label="健康" name="first">
        <div class="block">
          <h2 class="block-item-title flex align-center">身心状况</h2>
          <ul class="block-content">
            <li>
              <p class="block-content-title">
                <span class="num">1. </span>具有健康的体态
              </p>
              <div class="checkbox-content">
                <p class="checkbox-item flex align-center">
                  <el-checkbox v-model="checked">身高和体重适宜</el-checkbox>
                </p>
                <div class="check-info">
                  参考标准：
                  <p>男孩：身高：94.9-111.7厘米，体重：12.7-21.2公斤</p>
                  <p>女孩：身高：94.1-111.3厘米，体重：12.3-21.5公斤</p>
                </div>
              </div>
              <div class="checkbox-content">
                <p class="checkbox-item flex align-center">
                  <el-checkbox v-model="checked"
                    >在提醒下能自然坐直、站直</el-checkbox
                  >
                </p>
                <div class="check-info"></div>
              </div>
            </li>
            <li>
              <p class="block-content-title">
                <span class="num">2. </span>情绪安定愉快
              </p>
              <div class="checkbox-content">
                <p class="checkbox-item flex align-center">
                  <el-checkbox v-model="checked"
                    >情绪比较稳定，很少因一点小事哭闹不止</el-checkbox
                  >
                </p>
                <div class="check-info"></div>
              </div>
              <div class="checkbox-content">
                <p class="checkbox-item flex align-center">
                  <el-checkbox v-model="checked"
                    >有比较强烈的情绪反应时，能在成人的安抚下逐渐平静下来</el-checkbox
                  >
                </p>
                <div class="check-info"></div>
              </div>
            </li>
          </ul>
        </div>
      </el-tab-pane>
      <el-tab-pane label="语言" name="second">配置管理</el-tab-pane>
      <el-tab-pane label="社会" name="third">角色管理</el-tab-pane>
      <el-tab-pane label="科学" name="fourth">定时任务补偿</el-tab-pane>
      <el-tab-pane label="艺术" name="five">定时任务补偿</el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import {
  listAssessmentcontent,
  getAssessmentcontent,
  delAssessmentcontent,
  addAssessmentcontent,
  updateAssessmentcontent,
  exportAssessmentcontent,
} from "@/api/benyi/assessmentcontent";

export default {
  name: "Assessmentstudent",

  data() {
    return {
      checkedValues: [],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 评估内容表格数据
      assessmentcontentList: [],
      // 查询参数
      queryParams: {
        parentid: undefined,
        name: undefined,
        iselement: undefined,
        scope: undefined,
        sort: undefined,
      },
      activeName: "first",
      checked: false,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询评估内容列表 */
    getList() {
      this.loading = true;
      listAssessmentcontent(this.queryParams).then((response) => {
        this.assessmentcontentList = this.handleTree(response.rows, "id");
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(row) {},
    /** 提交按钮 */
    submitForm: function () {
      console.log("checkedValues:" + this.checkedValues);
    },
    handleClick(tab) {
      // this.activeName = tab
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
.title {
  padding: 10px 0;
  font-weight: 600;
  span {
    font-size: 16px;
    padding: 2px;
    // &:nth-of-type(2) {
    //   font-size: 14px;
    //   font-weight: normal;
    // }
  }
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
      padding-left: 24px;
      line-height: 18px;
      font-size: 12px;
      color: #999;
      p {
        padding-left: 10px;
      }
    }
  }
}
</style>