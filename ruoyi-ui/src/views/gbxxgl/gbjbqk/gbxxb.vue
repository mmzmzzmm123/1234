<template>
  <div class="member-site">
    <el-button
      class="print-btn"
      type="primary"
      plain
      size="mini"
      icon="el-icon-printer"
      @click="prints"
      >打印</el-button
    >
    <div class="must-container">
      <div class="print" ref="printMe">
        <h2 class="title">闵行区教育系统干部信息表</h2>
        <p class="unit-name">单位（盖章）：<span>教育部</span></p>
        <table border="1" cellspacing="0" class="info-table">
          <tr>
            <td rowspan="2"><b>姓 名</b></td>
            <td rowspan="2">小明</td>
            <td rowspan="2"><b>性 别</b></td>
            <td rowspan="2">男</td>
            <td>
              <b>出生日期</b>
            </td>
            <td>2021-02-02</td>
            <td rowspan="2"><b>籍贯</b></td>
            <td rowspan="2">河北</td>
          </tr>
          <tr>
            <td><b>身份证号</b></td>
            <td>202102202102202102</td>
          </tr>
          <tr>
            <td rowspan="2"><b>学历学位</b></td>
            <td><b>全日制教育</b></td>
            <td colspan="2" class="text-left">清北</td>
            <td><b>毕业院校及专业</b></td>
            <td colspan="3" class="text-left">清北 - 航天学</td>
          </tr>
          <tr>
            <td><b>在职教育</b></td>
            <td colspan="2" class="text-left">清北</td>
            <td><b>毕业院校及专业</b></td>
            <td colspan="3" class="text-left">清北 - 航天学</td>
          </tr>
          <tr>
            <td><b>职称</b></td>
            <td>教师</td>
            <td><b>参加工作时间</b></td>
            <td>2021-09-09</td>
            <td><b>政治面貌</b></td>
            <td>党员</td>
            <td><b>入党时间</b></td>
            <td>2021-09-09</td>
          </tr>
          <tr>
            <td><b>现任职务</b></td>
            <td colspan="3">教师</td>
            <td><b>任职时间</b></td>
            <td colspan="3">2021-09-09~2021-09-09</td>
          </tr>
          <tr>
            <td><b>办公室电话</b></td>
            <td colspan="3">1332222222</td>
            <td><b>任教学科及周课时</b></td>
            <td colspan="3">2021-09-09~2021-09-09</td>
          </tr>
          <tr>
            <td><b>家庭住址</b></td>
            <td colspan="5" class="text-left">聚少离多咖啡机</td>
            <td><b>邮编</b></td>
            <td>999999</td>
          </tr>
          <tr>
            <td><b>宅电</b></td>
            <td colspan="3">1332222222</td>
            <td><b>手机号</b></td>
            <td colspan="3">1332222222</td>
          </tr>
          <!-- 简历： rowspan= '3' => 简历条数 + 1 -->
          <!-- 简历title -->
          <tr>
            <td rowspan="2"><b>简历</b></td>
            <td colspan="2"><b>时间</b></td>
            <td colspan="2"><b>工作经历及学历进修情况</b></td>
            <td><b>职务</b></td>
            <td colspan="2"><b>备注</b></td>
          </tr>
          <!-- 简历条数 -->
          <tr>
            <td colspan="2">2020-09~2021-09</td>
            <td colspan="2" class="text-left">工作经历及学历进修情况</td>
            <td>学生</td>
            <td colspan="2">学历进修</td>
          </tr>
          <!-- 简历 END -->
          <!-- 家庭情况 -->
          <!-- 家庭情况条数和简历一样需循环 -->
          <tr>
            <td rowspan="2"><b>家庭主要成员及重要社会关系</b></td>
            <td><b>姓名</b></td>
            <td><b>关系</b></td>
            <td><b>出生日期</b></td>
            <td><b>政治面貌</b></td>
            <td colspan="2"><b>工作单位及职务</b></td>
            <td><b>身体状况</b></td>
          </tr>
          <tr>
            <td>小明</td>
            <td>夫妻</td>
            <td>2021-02-02</td>
            <td>党员</td>
            <td colspan="2" class="text-left">党员</td>
            <td>健康</td>
          </tr>
          <!-- 家庭情况 END -->
          <!-- 荣誉 -->
          <tr>
            <td rowspan="2"><b>近三年所获荣誉</b></td>
            <td><b>时间</b></td>
            <td colspan="3"><b>奖励名称</b></td>
            <td><b>奖励等级</b></td>
            <td colspan="2"><b>备注</b></td>
          </tr>
          <tr>
            <td>2021-02-02</td>
            <td colspan="3">党员</td>
            <td>一等奖</td>
            <td colspan="2">一等奖</td>
          </tr>
          <!-- 家庭情况 END -->
        </table>

        <p class="footer">闵行区教育局</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "member-info",
  data() {
    return {};
  },
  methods: {
    //打印
    prints() {
      //console.log(this.$refs.printMe);
      this.$print(this.$refs.printMe);
    }
  }
};
</script>

<style scoped lang="scss">
/*去除页眉页脚*/
@page {
  size: auto; /* auto is the initial value */
  margin: 3mm; /* this affects the margin in the printer settings */
}
@media print {
  .info-table {
    padding: 30px 0;
  }
}
.member-site {
  position: relative;
  max-width: 1200px;
  margin: 20px auto 40px;
  .print-btn {
    position: absolute;
    right: 0;
    top: 10px;
  }
}
.print {
  margin: 0 auto;
  font-family: "微软雅黑";
  color: #333;
  .title {
    margin: 0;
    margin-bottom: 15px;
    font-size: 18px;
    font-weight: 600;
    text-align: center;
    padding: 15px 0;
  }
  .unit-name {
    font-size: 14px;
    font-weight: 600;
    margin: 0;
    padding: 10px 0;
    line-height: 22px;
    span {
      padding: 0 12px 3px;
      border-bottom: 1px solid #ccc;
    }
  }
  .info-table {
    width: 100%;
    border-top: 1px solid #dcdfe6;
    border-left: 1px solid #dcdfe6;
    border-collapse: collapse; /*设置单元格的边框合并为1*/
    font-size: 14px;
    td {
      width: 12.5%;
      color: #333;
      padding: 8px 5px;
      text-align: center;
      border-bottom: 1px solid #dcdfe6;
      border-right: 1px solid #dcdfe6;
      b {
        font-size: 13px;
        color: #252525;
      }
    }
  }
  .text-left {
    text-align: left !important;
  }
  .text-right {
    text-align: right !important;
  }
  .unit {
    height: 100px;
    position: relative;
    .time {
      p {
        margin: 0;
        line-height: 22px;
      }
      position: absolute;
      right: 0;
      bottom: 0;
    }
  }
  .footer {
    padding-right: 15px;
    font-size: 12px;
    text-align: right;
  }
}
</style>
