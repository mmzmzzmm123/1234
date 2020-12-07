<template>
  <div class="zm-main">
    <el-button
      type="primary"
      plain
      size="mini"
      icon="el-icon-printer"
      @click="prints"
      >打印</el-button
    >
    <div class="zm-print">
      <div class="print" ref="printMe">
        <div class="text-center">
          <img class="headerbg_pkbg" src="./img/gkkPrintLogo.png" />
        </div>
        <br />
        <div class="vertical-middle">
          <h2 class="title">教育教学公开课证明</h2>
          <div>
            <h3 class="info text-indent">
              <b class="name">幼教</b>
              <span>学科&nbsp;</span>
              <b>吴丹</b>
              <span>老师在区教研活动中执教</span>
              <b>中班艺术活动《好玩的纸巾》</b>。
            </h3>
            <div class="zm-content">
              <h3 class="flex line-text">
                <span class="left-text text-indent">上课学校：</span>
                <div class="right-text">颛桥一幼</div>
              </h3>
              <h3 class="flex line-text">
                <span class="left-text text-indent">上课日期：</span>
                <div class="right-text">2020-12-03</div>
              </h3>
              <h3 class="flex line-text">
                <span class="left-text text-indent">评价：</span>
                <div class="right-text">优秀</div>
              </h3>
              <h3 class="flex line-text">
                <span class="left-text text-indent">证明编号：</span>
                <div class="right-text">【幼教202012024850】</div>
              </h3>
            </div>
          </div>
          <div class="zm-zm">
            <h3 class="text-indent">
              特此证明
            </h3>
          </div>
          <div style="margin:60px 0px 0px 0px;width: 600px;height: 160px;">
            <div style="margin-top: 20px;margin-left: 30px;float: left">
              <img src="./img/ewm.png" width="150px" height="150px" />
            </div>
            <div class="school-info">
              <div>
                <h3 style="line-height: 40px;">闵行区教育学院</h3>
                <h3 style="line-height: 40px;">
                  证明签发部门&nbsp;&nbsp;&nbsp;(公章)：
                </h3>
                <h3 style="line-height: 40px;">证明签发人：</h3>
                <h3 style="line-height: 40px;">
                  日期：<span id="certificateDate">2020年12月06日</span>
                </h3>
              </div>
            </div>
          </div>
          <div class="row" style="margin-top: 180px;margin-bottom:10px;">
            <h5 class="text-center">
              郑重说明：本证明盖有公章且有相关部门及教研员盖章签字方可有效，复印件交学校存档。
            </h5>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "gkkzm", // 公开课证明
  methods: {
    //打印
    prints() {
      //console.log(this.$refs.printMe);
      this.$print(this.$refs.printMe);
    }
  }
};
</script>
<style lang="scss" scoped>
.zm-main {
  width: 880px;
  margin: 0 auto;
  .el-button {
    margin: 10px 0;
  }
}
/*去除页眉页脚*/
@page {
  size: auto; /* auto is the initial value */
  margin: 3mm; /* this affects the margin in the printer settings */
}
.zm-print {
  width: 880px;
  margin: 0 auto 50px;
  border: 1px solid #ccc;
}
.print {
  font-family: "微软雅黑";
  color: #333;
  margin-top: 20px;
  h3 {
    font-size: 22px;
  }
  .vertical-middle {
    width: 700px;
    margin: 0 auto;
    .title {
      font-size: 26px;
      text-align: center;
    }
    .info {
      margin: 20px 0 10px;
      font-size: 22px;
      font-weight: normal;
      line-height: 40px;
    }
  }
  .zm-content {
    padding-top: 25px;
  }
  .flex {
    display: flex;
  }
  .line-text {
    margin: 0;
    margin-top: 20px;
    line-height: 50px;
    .left-text {
      font-weight: normal;
    }
  }
  .text-indent {
    text-indent: 2em;
  }
  .zm-zm {
    margin-top: 20px;
    h3 {
      line-height: 50px;
    }
  }
  .school-info {
    width: 300px;
    float: right;
    h3 {
      font-weight: normal;
    }
  }
  .row {
    h5 {
      font-weight: normal;
    }
  }
}
</style>
