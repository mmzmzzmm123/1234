<template>
  <div class="app-container">
    <div class="flex align-center justify-between">
      <p class="title flex align-center">
        <span>幼儿：{{ this.childName }} 评估结果图表 </span>
      </p>
    </div>
    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
      <el-tab-pane
        v-for="item in tabsList"
        :key="item.dictValue"
        :label="item.dictLabel"
        :name="item.dictLabel"
      >
        <h2 class="result-title">综合评估结果</h2>
        <div class="comprehensive">
          <radar-chart :psMsg="item.dictValue" />
        </div>
        <h2 class="result-title">各项评估结果</h2>
        <el-tabs
          v-model="childTab"
          @tab-click="handleTabClick"
        >
          <el-tab-pane label="健康" name="one">
            <radar-chart_jk :psMsg="item.dictValue" v-if="childTab === 'one'" />
            <div class="block">
              <h2 class="block-item-title flex align-center">教育建议</h2>
              <div
                class="block"
                v-for="itemLy in assessmentcontentList.filter(
                  p => p.parentId == item.dictValue && p.name == '健康'
                )"
                :key="itemLy.id"
              >
                {{ itemLy.name }}
                <div
                  class="block"
                  v-for="itemFzly in assessmentcontentList.filter(
                    p => p.parentId == itemLy.id
                  )"
                  :key="itemFzly.id"
                >
                  <h2 class="block-item-title flex align-center">
                    {{ itemFzly.name }}
                  </h2>
                  <ul class="block-content">
                    <li
                      v-for="itemMb in assessmentcontentList.filter(
                        p => p.parentId == itemFzly.id
                      )"
                      :key="itemMb.id"
                    >
                      <p class="block-content-title">
                        <span class="num">{{ itemMb.sort }}. </span
                        >{{ itemMb.name }}
                      </p>
                      <div
                        class="checkbox-content"
                        v-for="itemYs in assessmentcontentList.filter(
                          p => p.parentId == itemMb.id
                        )"
                        :key="itemYs.id"
                      >
                        <p class="checkbox-item flex align-center">
                          {{ itemYs.jyjy }}
                        </p>
                      </div>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="语言" name="two">
            <radar-chart_yy :psMsg="item.dictValue" v-if="childTab === 'two'" />
          </el-tab-pane>
          <el-tab-pane label="社会" name="three">
            <radar-chart_sh
              :psMsg="item.dictValue"
              v-if="childTab === 'three'"
            />
          </el-tab-pane>
          <el-tab-pane label="科学" name="four">
            <radar-chart_kx
              :psMsg="item.dictValue"
              v-if="childTab === 'four'"
            />
          </el-tab-pane>
          <el-tab-pane label="艺术" name="five">
            <radar-chart_ys
              :psMsg="item.dictValue"
              v-if="childTab === 'five'"
            />
          </el-tab-pane>
        </el-tabs>
        <!-- <div>sf
          <radar-chart_yy :psMsg="item.dictValue" />
        </div>
        <div>
          <radar-chart_sh :psMsg="item.dictValue" />
        </div>
        <div>
          <radar-chart_kx :psMsg="item.dictValue" />
        </div>
        <div>
          <radar-chart_ys :psMsg="item.dictValue" />
        </div> -->
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import { listNoAssessmentcontentByChild } from "@/api/benyi/assessmentcontent";
import { getChildByAssessment } from "@/api/benyi/child";
import { getAssessmentDictData } from "@/api/benyi/assessmentchild";
import RadarChart from "@/views/dashboard/RadarChart";
import RadarChart_jk from "@/views/dashboard/RadarChart_jk";
import RadarChart_yy from "@/views/dashboard/RadarChart_yy";
import RadarChart_sh from "@/views/dashboard/RadarChart_sh";
import RadarChart_kx from "@/views/dashboard/RadarChart_kx";
import RadarChart_ys from "@/views/dashboard/RadarChart_ys";

export default {
  name: "Assessmentstudent",
  components: {
    RadarChart,
    RadarChart_jk,
    RadarChart_yy,
    RadarChart_sh,
    RadarChart_kx,
    RadarChart_ys
  },
  data() {
    return {
      childId: "",
      childName: "",
      classid: "",
      assessmentscope: "",
      // tabs列表
      tabsList: [],
      activeName: "",
      // 评估内容表格数据
      assessmentcontentList: [],
      childTab: "one"
    };
  },
  created() {
    // this.getList();
    const childId = this.$route.params && this.$route.params.id;
    this.childId = childId;
    // console.log("childId:" + childId);
    this.getChild(childId);
    this.getList(childId);
    this.getNoAssessmentList();
  },
  methods: {
    /** 查询幼儿未评估内容列表 */
    getNoAssessmentList() {
      listNoAssessmentcontentByChild(this.childId).then(response => {
        // console.log("rows:" + response.rows);
        this.assessmentcontentList = response.rows;
      });
    },
    getChild(childId) {
      getChildByAssessment(childId).then(response => {
        // console.log(response);
        if (response.code == "200") {
          this.childName = response.data.name;
          this.classid = response.data.classid;
        }
      });
    },
    /** 查询评估内容列表 */
    getList(childId) {
      getAssessmentDictData(childId).then(response => {
        // console.log("rows:" + response.dictdata.length);
        if (response.dictdata.length > 0) {
          this.activeName = response.dictdata[0].dictLabel;
          this.tabsList = response.dictdata;
        } else {
          this.msgError("该幼儿尚未提交评估数据，无法展示数据");
        }
      });
    },
    handleClick(tab) {
      //   this.activeName = tab.name;
      //   console.log(tab.name);
    },
    handleTabClick(tab) {
      this.childTab = tab.name;
    }
  }
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
.comprehensive {
  margin-bottom: 40px;
}
.result-title {
  font-size: 18px;
  margin-bottom: 15px;
  text-align: center;
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
