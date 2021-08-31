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
          <div class="text item" v-show="title1">
            <h3 class="box-card-title">{{ title1 }}</h3>
            <div class="pad-left" v-html="note"></div>
          </div>
          <div class="text item" v-show="title2">
            <h3 class="box-card-title">
              {{ title2 }}
            </h3>
            <div ref="printMe">
              <Editor v-model="communicate" />
            </div>
          </div>
          <div class="text item" v-show="title3">
            <h3 class="box-card-title">{{ title3 }}</h3>

            <div class="pad-left">
              <div
                v-for="(item, index) in activityList"
                :key="index"
                class="text item"
              >
                <h3 class="box-card-case mr">
                  活动{{ item.sort }} - {{ item.name }}
                </h3>
                <h3 class="box-card-info">活动形式：{{ fieldFormat(item) }}</h3>
                <h3 class="box-card-info">重点领域：{{ typeFormat(item) }}</h3>
                <h3 class="box-card-info">活动目标</h3>
                <div class="text item pad-left" v-html="item.target"></div>
                <h3 class="box-card-info">活动材料</h3>
                <div class="text item pad-left" v-html="item.data"></div>
                <h3 class="box-card-info">活动过程</h3>
                <div class="text item pad-left" v-html="item.process"></div>
                <h3 class="box-card-info">活动建议</h3>
                <div class="text item pad-left" v-html="item.proposal"></div>
                <h3 class="box-card-info">活动反思</h3>
                <div class="text item pad-left" v-html="item.reflect"></div>
                <h3 class="box-card-info" v-show="item.appendix">附录</h3>
                <div class="text item pad-left" v-html="item.appendix"></div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { treeselect, getTheme } from "@/api/benyi/theme";
import { listActivity, getActivity } from "@/api/benyi/activity";
import Editor from "@/components/Editor";

export default {
  name: "Detail",
  components: {
    Editor,
  },
  data() {
    return {
      // 主题整合名称
      name: undefined,
      // 主题整合id
      id: undefined,
      //标题
      title: "主题整合",
      title1: "概论",
      //家园沟通
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
      note:
        "《幼儿园主题整合课程》，是在《幼儿园工作规程》（以下称《规程》）和《幼儿园教育指导纲要》（以下称《纲要》）中的基本教育理念指导之下，以《3-6岁儿童学习与发展指南》（以下称《指南》）精神为基本指导原则，所编写完成的幼儿园3-6岁活动课程。",
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
    if (tremThemeId != null) {
      this.handleNodeUrl(tremThemeId);
    }
    this.getTreeselect();
    this.getDicts("sys_theme_type").then((response) => {
      this.typeOptions = response.data;
    });
    this.getDicts("sys_theme_field").then((response) => {
      this.fieldOptions = response.data;
    });
  },
  methods: {
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
      this.id = data.id;
      //console.log(data.id);
      if (data.id >= 9999 && data.id < 99999) {
      } else if (data.id >= 99999) {
        //console.log("2jiedian");
        this.id = this.id - 99999;
        this.title = data.label;
        this.getThemeDetails();
      } else {
        this.title = data.label;
        this.getThemeDetail();
        //console.log("最后节点");
      }
      // console.log(this.dayflowtaskList[date.id])
      // this.getStandardList();
    },
    // 节点单击事件
    handleNodeUrl(tid) {
      this.id = tid;
      //console.log(data.id);
      if (tid >= 9999 && tid < 99999) {
      } else if (tid >= 99999) {
        //console.log("2jiedian");
        this.id = this.id - 99999;
        //this.title = data.label;
        this.getThemeDetails();
      } else {
        //this.title = data.label;
        this.getThemeDetail();
        //console.log("最后节点");
      }
      // console.log(this.dayflowtaskList[date.id])
      // this.getStandardList();
    },
    getThemeDetails() {
      getTheme(this.id).then((response) => {
        //console.log(response);
        if (response.code == "200") {
          this.title1 = "主题网络";
          this.title2 = "家园沟通";
          this.title3 = "活动方案";
          this.note = response.data.content;
          this.communicate = response.data.communicate;
          this.queryParams.themeid = response.data.id;
          this.queryParams.id = "";
          //console.log(this.queryParams);
          listActivity(this.queryParams).then((req) => {
            //console.log(req);
            if (req.code == "200") {
              this.activityList = req.rows;
            }
          });
        }
      });
    },
    getThemeDetail() {
      this.title1 = "";
      this.title2 = "";
      this.title3 = "活动方案";
      this.queryParams.id = this.id;
      this.queryParams.themeid = "";
      //console.log(this.id);
      listActivity(this.queryParams).then((req) => {
        //console.log(req);
        if (req.code == "200") {
          this.activityList = req.rows;
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