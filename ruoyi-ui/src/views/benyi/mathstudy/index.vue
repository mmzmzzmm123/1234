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
          <div class="text item" v-show="title1">
            <h3 class="box-card-title">{{title1}}</h3>
            <div class="pad-left" v-html="note"></div>
          </div>
          <div class="text item" v-show="title2">
            <h3 class="box-card-title">{{title2}}</h3>
            <div class="pad-left" v-html="communicate"></div>
          </div>
          <div class="text item" v-show="title3">
            <h3 class="box-card-title">{{title3}}</h3>
            <div class="pad-left" v-html="sug"></div>
          </div>
          <div class="text item" v-show="gameplan">
            <h3 class="box-card-title">{{gameplan}}</h3>
          </div>
          <div>
            <div class="pad-left">
              <div v-for="(item, index) in planList" :key="index" class="text item">
                <h3 class="box-card-case mr">第{{item.sort}}周 - {{item.name}}</h3>
                <h3 class="box-card-info">游戏形式：{{typeFormat(item)}}</h3>
                <h3 class="box-card-info">游戏目标</h3>
                <div class="text item pad-left" v-html="item.target"></div>
                <h3 class="box-card-info">游戏准备</h3>
                <div class="text item pad-left" v-html="item.prepare"></div>
                <h3 class="box-card-info">游戏过程</h3>
                <div class="text item pad-left" v-html="item.process"></div>
                <h3 class="box-card-info">其他目标</h3>
                <div class="text item pad-left" v-html="item.otherTarget"></div>
                <h3 class="box-card-info">建议</h3>
                <div class="text item pad-left" v-html="item.suggest"></div>
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
import { treeselect, getMath } from "@/api/benyi/math";
import { listPlan } from "@/api/benyi/plan";
import Editor from "@/components/Editor";

export default {
  name: "Detail",
  components: {
    Editor
  },
  data() {
    return {
      // 游戏数学名称
      name: undefined,
      // 游戏数学id
      id: undefined,
      //标题
      title: "游戏数学",
      title1: "前言",
      //目标
      title2: "",
      //活动方案
      title3: "",
      // 游戏数学表格数据
      planList: [],
      //家园沟通
      communicate: "",
      //建议
      sug: "",
      // 数学游戏方案
      gameplan: "数学游戏方案",
      //活动形式
      typeOptions: [],
      //目的
      note: "《幼儿园游戏数学》，就是在《规程》和《纲要》相关教育理念指导下，遵循《指南》关于儿童在科学领域学习与发展的基本目标、原则和策略，所构建和研发完成的。《指南》在科学领域中，对有关儿童数学认知领域的学习与发展提出三个基本目标。即：1.	初步感知生活中数学的有用和有趣。2.	感知和理解数、量及数量关系。3.	感知形状与空间关系.",
        
      // 树状显示类型
      treeOptions: [],
      // 树结构
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 查询参数
      queryParams: {
        mathid: undefined
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
    this.getDicts("sys_math_type").then(response => {
      this.typeOptions = response.data;
    });
  },
  methods: {
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
      this.id = data.id;
      if (data.id >= 9999) {
      } else {
        this.title = data.label;
        this.getMathDetails();
      }
    },
    getMathDetails() {
      getMath(this.id).then(response => {
        if (response.code == "200") {
          this.title1 = "学习目标";
          this.title2 = "表现特征";
          this.title3 = "教学建议";
          this.note = response.data.target;
          this.communicate = response.data.feature;
          this.sug = response.data.suggest;
          this.queryParams.mathid = response.data.id;

          listPlan(this.queryParams).then(req => {
            // console.log(req);
            if (req.code == "200") {
              this.planList = req.rows;
            }
          });
        }
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