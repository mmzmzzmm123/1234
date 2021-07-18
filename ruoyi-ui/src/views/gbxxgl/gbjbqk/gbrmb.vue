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
        <h2 class="title">干部任免审批表</h2>
        <table border="1" cellspacing="0" class="info-table">
          <tr>
            <td><b>姓 名</b></td>
            <td>小明</td>
            <td><b>性 别</b></td>
            <td>男</td>
            <td><b>出生日期</b></td>
            <td>2021-02-02</td>
            <td colspan="2" rowspan="4">照片</td>
          </tr>
          <tr>
            <td><b>民 族</b></td>
            <td>汉</td>
            <td><b>籍 贯</b></td>
            <td>河北</td>
            <td><b>出 生 地</b></td>
            <td>河北唐山XXXX</td>
          </tr>
          <tr>
            <td><b>入党时间</b></td>
            <td>2021-09-09</td>
            <td><b>参加工作时间</b></td>
            <td>2021-09-09</td>
            <td><b>健康状况</b></td>
            <td>健康</td>
          </tr>
          <tr>
            <td><b>专业技术职务</b></td>
            <td colspan="2" class="text-left">汉水电费水电费手动蝶阀</td>
            <td style="width: 150px"><b>熟悉专业专长</b></td>
            <td colspan="2" class="text-left">v汉水电费水电费手动蝶阀</td>
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
            <td><b>现任职务</b></td>
            <td colspan="7" class="text-left">学生</td>
          </tr>
          <tr>
            <td><b>拟任职务</b></td>
            <td colspan="7" class="text-left">学生</td>
          </tr>
          <tr>
            <td><b>拟免职务</b></td>
            <td colspan="7" class="text-left">学生</td>
          </tr>
          <!-- 简历： rowspan= '3' => 简历条数 + 1 -->
          <!-- 简历title -->
          <tr>
            <td rowspan="3"><b>简历</b></td>
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
          <tr>
            <td colspan="2">2020-09~2021-09</td>
            <td colspan="2" class="text-left">工作经历及学历进修情况</td>
            <td>学生</td>
            <td colspan="2">学历进修</td>
          </tr>
          <!-- 简历 END -->
          <tr>
            <td><b>奖惩情况</b></td>
            <td colspan="7" class="text-left">学生</td>
          </tr>
          <tr>
            <td><b>年度考核结果</b></td>
            <td colspan="7" class="text-left">学生</td>
          </tr>
          <tr>
            <td><b>任免理由</b></td>
            <td colspan="7" class="text-left">学生</td>
          </tr>
          <!-- 家庭情况 -->
          <!-- 家庭情况条数和简历一样需循环 -->
          <tr>
            <td rowspan="2"><b>家庭主要成员及重要社会关系</b></td>
            <td><b>关系</b></td>
            <td><b>姓名</b></td>
            <td><b>出生日期</b></td>
            <td><b>政治面貌</b></td>
            <td colspan="3"><b>工作单位及职务</b></td>
          </tr>
          <tr>
            <td>夫妻</td>
            <td>小明</td>
            <td>2021-02-02</td>
            <td>党员</td>
            <td colspan="3" class="text-left">党员</td>
          </tr>
          <!-- 家庭情况 END -->
          <tr>
            <td><b>呈报单位</b></td>
            <td colspan="7">
              <div class="unit">
                <div class="time">2021年7月18日</div>
              </div>
            </td>
          </tr>
          <tr>
            <td><b>审批机关意见</b></td>
            <td colspan="3">
              <div class="unit">
                <div class="time">
                  <p>（盖章）</p>
                  <p>2021年7月18日</p>
                </div>
              </div>
            </td>
            <td><b>行政机关任免意见</b></td>
            <td colspan="3">
              <div class="unit">
                <div class="time">
                  <p>（盖章）</p>
                  <p>2021年7月18日</p>
                </div>
              </div>
            </td>
          </tr>
          <tr>
            <td><b>填表人：</b></td>
            <td colspan="7" class="text-right">中共上海市委组织部制</td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "member-site",
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
}
</style>
