<template>
  <div class="app-container">
    <el-row :gutter="24">
      <el-col :span="24" :xs="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span class="box-card-title">{{ title }}</span>
          </div>
          <div class="text item" v-show="title3">
            <el-button
              type="primary"
              plain
              size="mini"
              icon="el-icon-printer"
              v-show="enable"
              style="float: right"
              @click="prints"
              >打印</el-button
            >
            <h3 class="box-card-title">{{ title3 }}</h3>

            <div class="pad-left" ref="printMe">
              <h2 class="title">{{ title4 }}</h2>
              <table>
                <tr class="align-center">
                  <!-- <td v-for="h in headerData" :key="h.title">
            <b class="table-title">{{h.title}}</b>
            {{h.name}}
          </td>-->
                  <td style="width: 25%">
                    <b class="table-title">班级：{{ bjmc }}</b>
                  </td>
                  <td style="width: 25%">
                    <b class="table-title">制表人：{{ zbr }}</b>
                  </td>
                  <td style="width: 50%">
                    <b class="table-title">日期：{{ time }}</b>
                  </td>
                </tr>
                <tr>
                  <td colspan="3">
                    <div
                      v-for="(item, index) in activityList"
                      :key="index"
                      class="text item"
                    >
                      <h3 class="box-card-case mr">
                        {{ item.name }}
                      </h3>
                      <h3 class="box-card-info">
                        活动形式：{{ fieldFormat(item) }}
                      </h3>
                      <h3 class="box-card-info">
                        重点领域：{{ typeFormat(item) }}
                      </h3>
                      <h3 class="box-card-info">活动目标</h3>
                      <div
                        class="text item pad-left"
                        v-html="item.target"
                      ></div>
                      <h3 class="box-card-info">活动材料</h3>
                      <div class="text item pad-left" v-html="item.data"></div>
                      <h3 class="box-card-info">活动过程</h3>
                      <div
                        class="text item pad-left"
                        v-html="item.process"
                      ></div>
                      <h3 class="box-card-info">活动建议</h3>
                      <div
                        class="text item pad-left"
                        v-html="item.proposal"
                      ></div>
                      <h3 class="box-card-info">活动反思</h3>
                      <div
                        class="text item pad-left"
                        v-html="item.reflect"
                      ></div>
                      <h3 class="box-card-info" v-show="item.appendix">附录</h3>
                      <div
                        class="text item pad-left"
                        v-html="item.appendix"
                      ></div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <td colspan="3" class="table-title">家长支持:{{ jzzc }}</td>
                </tr>
              </table>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getTheme } from "@/api/benyi/theme";
import { listActivity } from "@/api/benyi/activity";
import { getWeekplanitem } from "@/api/benyi/themeweekplanitem";

import { listWeekplan, getWeekplan } from "@/api/benyi/themeweekplan";

export default {
  name: "Theme3",
  data() {
    return {
      //打印是否显示
      enable: true,
      // 主题整合名称
      name: undefined,
      // 主题整合id
      id: undefined,
      wid: "",
      time: null,
      bjmc: null,
      zbr: null,
      title4: null,
      jzzc: null,
      //标题
      title: "",
      title1: "",
      title2: "",
      //活动方案
      title3: "",
      // 主题整合活动表格数据
      activityList: [],
      //家园沟通
      communicate: "",
      //活动形式
      typeOptions: [],
      //活动领域
      fieldOptions: [],
      //目的
      note: "",
      // 树状显示类型
      treeOptions: [],
      // 树结构
      defaultProps: {
        children: "children",
        label: "label",
      },
      // 查询参数
      queryParams: {
        id: undefined,
        themeid: undefined,
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
    const tremThemeId = this.$route.params && this.$route.params.id;
    this.wid = this.$route.params.wid;
    if (tremThemeId != null) {
      this.handleNodeUrl(tremThemeId);
    }
    if (this.wid != "") {
      this.getThemeWeekPlanItem();
    }
    this.getDicts("sys_theme_type").then((response) => {
      this.typeOptions = response.data;
    });
    this.getDicts("sys_theme_field").then((response) => {
      this.fieldOptions = response.data;
    });
  },
  methods: {
    getThemeWeekPlanItem() {
      getWeekplanitem(this.wid).then((response) => {
        this.time = response.data.daytime;
        this.jzzc = response.data.jzzc;

        getWeekplan(response.data.wpid).then((res) => {
          this.title4 = res.data.name;
          this.bjmc = res.classname;
          this.zbr = res.createusername;
        });
      });
    },
    // 活动领域类型--字典状态字典翻译
    fieldFormat(row) {
      //alert(row.scope.split(';').length);
      var ilength = row.field.split(";").length;
      var names = "";
      for (var i = 0; i < ilength; i++) {
        names =
          names +
          this.selectDictLabel(this.fieldOptions, row.field.split(";")[i]) +
          ";";
      }
      //this.selectDictLabel(this.scopeOptions, row.xnxq);
      return names;
    },
    // 活动形式类型--字典状态字典翻译
    typeFormat(row) {
      //alert(row.scope.split(';').length);
      var ilength = row.type.split(";").length;
      var names = "";
      for (var i = 0; i < ilength; i++) {
        names =
          names +
          this.selectDictLabel(this.typeOptions, row.type.split(";")[i]) +
          ";";
      }
      //this.selectDictLabel(this.scopeOptions, row.xnxq);
      return names;
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.id = data.id;
      this.enable = false;
      //console.log(data.id);
      if (data.id >= 9999 && data.id < 99999) {
      } else if (data.id >= 99999) {
      } else {
        this.title = data.label;
        this.getThemeDetail();
        this.enable = true;
        //console.log("最后节点");
      }
    },
    // 节点单击事件
    handleNodeUrl(tid) {
      this.id = tid;
      //console.log(data.id);
      if (tid >= 9999 && tid < 99999) {
      } else if (tid >= 99999) {
      } else {
        //this.title = data.label;
        this.getThemeDetail();
        //console.log("最后节点");
      }
      // console.log(this.dayflowtaskList[date.id])
      // this.getStandardList();
    },
    getThemeDetail() {
      this.title3 = "活动方案";
      this.queryParams.id = this.id;
      this.queryParams.themeid = "";
      //console.log(this.id);
      listActivity(this.queryParams).then((req) => {
        this.title = req.rows[0].name;
        //console.log(req);
        if (req.code == "200") {
          this.activityList = req.rows;
        }
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
table {
  width: 100%;
  border-collapse: collapse;
}
table td {
  border: #ccc solid 1px;
  line-height: 24px;
  padding: 8px 5px;
}
.title {
  margin: 0;
  font-size: 18px;
  text-align: center;
  padding: 15px 0;
}
</style>