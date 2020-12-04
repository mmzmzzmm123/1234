<template>
  <div class="mod-home">
    <el-form>
      <el-row>
        <el-col :offset="18" :span="6" >
          <div id="sc1" class="ybp chart-box"></div>
          <!--<div class="biaoti">企业整体安全风险等级警示</div>-->
        </el-col>
      </el-row>
      <el-row>
        <el-col :offset="18" :span="6" >
          <div class="tab-div">
            <table class="tab1 layui-table">
              <thead>
              <tr>
                <th>车牌号</th>
                <th>风险等级</th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <td>皖A-******</td>
                <td>4级</td>
              </tr>
              <tr>
                <td>鲁A-******</td>
                <td>4级</td>
              </tr>
              <tr>
                <td>冀B-******</td>
                <td>3级</td>
              </tr>
              <tr>
                <td>豫A-******</td>
                <td>3级</td>
              </tr>
              <tr>
                <td>豫C-******</td>
                <td>3级</td>
              </tr>
              <tr>
                <td>豫D-******</td>
                <td>2级</td>
              </tr>
              <tr>
                <td>沪A-******</td>
                <td>2级</td>
              </tr>
              </tbody>
            </table>
          </div>
        </el-col>
      </el-row>
    </el-form>
    <div></div>
  </div>
</template>

<script>
  import echarts from 'echarts'

  export default {
    data () {
      return {
        sc1: null
      }
    },
    activated () {
      this.initSC1()
    },
    methods: {
      initSC1 () {
        let option = {
          // title: {
          //   show: true,
          //   text: '企业整体安全风险等级警示',
          //   textStyle: {
          //     fontSize: 14
          //   },
          //   bottom: 0,
          //   left: 'center'
          // },
          tooltip: {
            formatter: '{a} <br/>{b} : {c}%'
          },
          grid: {
            top: 10,
            containLabel: true
          },
          series: [
            {
              name: '企业整体安全风险等级警示',
              type: 'gauge',
              detail: {formatter: '{value}%'},
              data: [{value: 23.6}],
              center: ['50%', '55%'],
              radius: '90%',
              axisLine: {
                lineStyle: {
                  width: 15
                }
              },
              splitLine: {
                length: 15
              },
              title: {
                fontSize: 14
              },
              // eslint-disable-next-line no-dupe-keys
              detail: {
                formatter: '{value}%',
                fontSize: 16
              }
            }
          ]
        }
        this.sc1 = echarts.init(document.getElementById('sc1'))
        this.sc1.setOption(option)
        window.addEventListener('resize', () => {
          this.sc1.resize()
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  .title-box {
    min-height: 10px;
    text-align: center;
  }
  .el-card__body{
    padding: 5px;
  }
  body .el-table th.gutter{
    display: table-cell!important;
  }

  body .el-table colgroup.gutter {
    display: table-cell !important;
  }
  .mod-home {
    position: relative;
    width: 100%;
    min-height: calc(100vh - 84px);
    line-height: 1.5;
    background: url(~@/assets/image/home.jpg) no-repeat;
    background-size:100% 100%;
    > .el-form{
      height: 100%;
      > .el-row{
        height: 50%;
        > .el-col{
          height: 100%;
        }
      }
    }
    .chart-box {
      height: calc(100% - 36px);
    }
  }
  .ybp{
    background-color: #ffffff;
  }
  .tab-div{
    width: 100%;
    height: 100%;
    overflow-y: auto;
  }
  .tab1{
    background-color: #7cfdff;
    color: #e04b38;
    text-align: center;
    width: 100%;
    height: 100%;
    border-collapse: collapse;
    > thead,tbody {
      width: 100%;
      > tr th,tr td{
        border: 1.5px solid rgba(255, 130, 113, 0.45);
        font-size: 14px;
        padding: 10px 0px;
        width: 50%;
      }
    }
    > tbody{
      overflow: auto;
      height: 80%
    }
  }
  .biaoti{
    text-align: center;
    color: #FFFFFF;
    font-size: 16px;
    font-weight: 500;
    margin: 10px 0px;
  }
</style>
