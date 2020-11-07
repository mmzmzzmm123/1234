<template>
  <div class="app-container">
    <div class="flex align-center justify-between">
      <p class="title flex align-center">
        <span>{{ this.childName }}同学，评估结果图表 </span>
      </p>
    </div>
    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
      <el-tab-pane label="儿童学习与发展评估(36-48)" name="first">
        <div>
          <radar-chart />
        </div>
        <div class="block">
          <h2 class="block-item-title flex align-center">评估建议</h2>
          <ul class="block-content">
            <li>
              <p class="block-content-title">
                <span class="num">1. </span>具有健康的体态
              </p>
              <div class="checkbox-content">
                <p class="checkbox-item flex align-center">
                  身高和体重适宜
                </p>
                <div class="check-info">
                  参考标准：
                  <p>男孩：身高：94.9-111.7厘米，体重：12.7-21.2公斤</p>
                  <p>女孩：身高：94.1-111.3厘米，体重：12.3-21.5公斤</p>
                </div>
              </div>
              <div class="checkbox-content">
                <p class="checkbox-item flex align-center">在提醒下能自然坐直、站直
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
                 情绪比较稳定，很少因一点小事哭闹不止
                </p>
                <div class="check-info"></div>
              </div>
              <div class="checkbox-content">
                <p class="checkbox-item flex align-center">
                 有比较强烈的情绪反应时，能在成人的安抚下逐渐平静下来
                </p>
                <div class="check-info"></div>
              </div>
            </li>
          </ul>
        </div>
      </el-tab-pane>
      <el-tab-pane label="儿童学习与发展评估(48-60)" name="second">配置管理</el-tab-pane>
      <el-tab-pane label="儿童学习与发展评估(60-72)" name="third">角色管理</el-tab-pane>
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

import { getChildByAssessment } from "@/api/benyi/child";
import {
  addAssessmentchild,
  updateAssessmentchild,
} from "@/api/benyi/assessmentchild";
import RadarChart from "@/views/dashboard/RadarChart";

export default {
  name: "Assessmentstudent",
  components: {
    RadarChart,
  },
  data() {
    return {
      childId: "",
      childName: "",
      childCsrq: "",
      bjmc: "",
      classid: "",
      trem: "",
      zbjsxm: "",
      assessmentscope: "",
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 评估内容表格数据
      assessmentcontentList: [],
      // 表单参数
      form: {},
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
      checkList: [],
    };
  },
  created() {
    // this.getList();
    const childId = this.$route.params && this.$route.params.id;
    this.childId = childId;
    // console.log("childId:" + childId);
    this.getChild(childId);
  },
  methods: {
    getChild(childId) {
      getChildByAssessment(childId).then((response) => {
        // console.log(response);
        if (response.code == "200") {
          this.childName = response.data.name;
          this.childCsrq = response.data.csrq;
          this.trem = response.trem;
          this.bjmc = response.data.bjmc;
          this.classid = response.data.classid;
          this.zbjsxm = response.data.zbjsmc;
          response.ByAssessmentchild.forEach((item) =>
            this.checkList.push(item.contentid)
          );
          if (response.isAssessment == "0") {
            this.msgError(
              "当前幼儿出生日期不符合评估范围，幼儿评估范围为36-72个月"
            );
          } else {
            this.assessmentscope = response.isAssessment;

            this.getList();
          }
        }
      });
    },
    /** 查询评估内容列表 */
    getList() {
      this.loading = true;
      listAssessmentcontent(this.queryParams).then((response) => {
        // console.log("rows:" + response.rows);
        this.assessmentcontentList = response.rows;
        this.loading = false;
      });
    },
    handleClick(tab) {
      // this.activeName = tab
    },
    handlecheckedItemsChange(value) {
      // var items = "";
      // this.checkList.forEach((item) => {
      //   //当全选被选中的时候，循环遍历源数据，把数据的每一项加入到默认选中的数组去
      //   items = items + item + ",";
      // });
      // console.log(items);
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