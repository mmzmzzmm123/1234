<template>
  <div class="app-container">
    <el-table border :data="tableData" style="width: 100%">
      <el-table-column label="角色名称" width="200" fixed>
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="拥有权限" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <!-- <el-tag size="medium">{{ scope.row.role }}</el-tag> -->
          <div style="color: #409eff" v-html="scope.row.role"></div>
        </template>
      </el-table-column>
      <el-table-column label="文件权限" width="200">
        <template slot-scope="scope">
          <!-- <el-tag size="medium">{{ scope.row.file }}</el-tag> -->
          <div style="color: #409eff" v-html="scope.row.file"></div>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [
        {
          name: "幼儿园管理员",
          role:
            "<pre>系统管理：用户管理、班级管理、教师管理、托费管理；</pre>" +
            "<pre>行政：园历展示、园历管理(园)；</pre>" +
            "<pre>课程：一日流程学习、主题整合学习、游戏数学学习、微型课程学习；</pre>" +
            "<pre>教学计划：主题整合学期计划审批、主题整合月计划审批、主题整合周计划审批、游戏数学学期计划审批；</pre>" +
            "<pre>培训：学习视频、幼儿园大型活动方案、家庭教育手册；</pre>" +
            "<pre>幼儿管理：幼儿基础数据、幼儿紧急情况联系人、幼儿考勤、</pre>" +
            "<pre>        幼儿学习与发展档案(教师)、幼儿学习与发展档案(家长)、幼儿园考勤统计、幼儿收费统计；</pre>" +
            "<pre>招生：入园体验申请、常规招生策略、入园通知书、入园半日体验计划；</pre>" +
            "<pre>幼儿评估：评估内容学习、幼儿评估；</pre>" +
            "<pre>新闻：新闻管理；</pre>" +
            "<pre>教师评估：一日流程评估计划、一日流程评估、班级平均分、教师得分情况、教师平均分；</pre>" +
            "<pre>教师考核：月绩效考核文件解读、月绩效考核；</pre>" +
            "<pre>本一园长：园长职责与角色、园长素质、园所文化；</pre>" +
            "<pre>文件库：文件。</pre>",
          file: "<pre>全部</pre>",
        },
        {
          name: "教学主任",
          role:
            "<pre>系统管理：用户管理(查询)、班级管理、教师管理；</pre>" +
            "<pre>行政：园历展示、园历管理(园)；</pre>" +
            "<pre>课程：一日流程学习、主题整合学习、游戏数学学习、微型课程学习；</pre>" +
            "<pre>教学计划：主题整合学期计划审批、主题整合月计划审批、主题整合周计划审批、游戏数学学期计划审批；</pre>" +
            "<pre>培训：幼儿园大型活动方案、家庭教育手册；</pre>" +
            "<pre>幼儿管理：幼儿基础数据、幼儿紧急情况联系人、幼儿考勤、</pre>" +
            "<pre>        幼儿学习与发展档案(教师)、幼儿学习与发展档案(家长)、幼儿园考勤统计；</pre>" +
            "<pre>招生：入园体验申请、常规招生策略、入园通知书、入园半日体验计划；</pre>" +
            "<pre>幼儿评估：评估内容学习、幼儿评估；</pre>" +
            "<pre>新闻：新闻管理；</pre>" +
            "<pre>教师评估：一日流程评估计划、一日流程评估、班级平均分、教师得分情况、教师平均分；</pre>" +
            "<pre>教师考核：月绩效考核文件解读、月绩效考核；</pre>" +
            "<pre>本一园长：园长职责与角色、园长素质、园所文化；</pre>" +
            "<pre>文件库：文件。</pre>",
          file:
            "<pre>幼儿入园</pre>" +
            "<pre>本一园长岗位职责</pre>" +
            "<pre>培训</pre>" +
            "<pre>招生</pre>" +
            "<pre>家长会</pre>" +
            "<pre>安全</pre>" +
            "<pre>健康</pre>" +
            "<pre>膳食</pre>" +
            "<pre>资产</pre>",
        },
        {
          name: "班主任、配班教师、助理教师",
          role:
            "<pre>行政：园历展示、园历管理(班)；</pre>" +
            "<pre>课程：一日流程学习、主题整合学习、游戏数学学习、微型课程学习；</pre>" +
            "<pre>教学计划：周教学计划、主题整合学期计划、主题整合月计划、主题整合周计划、游戏数学学期计划；</pre>" +
            "<pre>培训：幼儿园大型活动方案、家庭教育手册；</pre>" +
            "<pre>幼儿管理：幼儿基础数据、幼儿紧急情况联系人、幼儿考勤、</pre>" +
            "<pre>        幼儿学习与发展档案(教师)、幼儿学习与发展档案(家长)、班级考勤统计；</pre>" +
            "<pre>招生：入园体验申请、常规招生策略、入园通知书、入园半日体验计划；</pre>" +
            "<pre>幼儿评估：评估内容学习、幼儿评估；</pre>" +
            "<pre>新闻：新闻管理；</pre>" +
            "<pre>教师评估：个人评估结果查询；</pre>" +
            "<pre>教师考核：月绩效考核文件解读、月绩效考核查看；</pre>" +
            "<pre>本一园长：园所文化；</pre>" +
            "<pre>文件库：文件。</pre>",
          file:
            "<pre>幼儿入园</pre>" +
            "<pre>培训</pre>" +
            "<pre>招生</pre>" +
            "<pre>家长会</pre>",
        },
        {
          name: "后勤园长",
          role:
            "<pre>行政：园历展示；</pre>" + "<pre>本一园长：园所文化；</pre>",
          file: "无",
        },
        {
          name: "保健医",
          role:
            "<pre>行政：园历展示；</pre>" +
            "<pre>幼儿管理：幼儿基础数据(查看)、幼儿常规体检记录；</pre>" +
            "<pre>本一园长：园所文化；</pre>" +
            "<pre>文件库：文件；</pre>",
          file: "<pre>安全</pre>" + "<pre>健康</pre>" + "<pre>膳食</pre>",
        },
        {
          name: "财务",
          role:
            "<pre>系统管理：托费管理；</pre>" +
            "<pre>行政：园历展示；</pre>" +
            "<pre>幼儿管理：幼儿基础数据(查看)、幼儿收费统计；</pre>" +
            "<pre>本一园长：园所文化；</pre>" +
            "<pre>文件库：文件；</pre>",
          file: "无",
        },
        {
          name: "行政人事",
          role:
            "<pre>行政：园历展示；</pre>" +
            "<pre>本一园长：园长职责与角色、园长素质、园所文化；</pre>" +
            "<pre>文件库：文件。</pre>",
          file:
            "<pre>安全</pre>" +
            "<pre>健康</pre>" +
            "<pre>膳食</pre>" +
            "<pre>资产</pre>",
        },
      ],
    };
  },
  methods: {},
};
</script>
<style lang="scss" scoped>
.el-table .cell {
  white-space: pre-line;
}
</style>