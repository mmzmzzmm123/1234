<template>
  <div class="app-container home">
    <div id="mainPie" class="mainClass"></div>
    <div class="mainClass">
      <div id="inSchool" class="littleClass"></div>
      <div id="outSchool" class="littleClass"></div>
      <div id="leaveStu" class="littleClass"></div>
      <div id="specialStu" class="littleClass"></div>
    </div>
  </div> 
</template>

<script>
import echarts from 'echarts'
export default {
  name: "Index",
  mounted(){
    	// 在通过mounted调用即可
		this.echartsInit()
    this.echartsInitLittle()
	},
  data() {
    return {
      // 版本号
      version: "3.8.4",
      labelStyle:{
                  show: true,
                  // formatter: '{b} : {c} ({d}%)'
                  formatter: '{b} : {c} '
                },
    };
  },
  methods: {
    //初始化echarts

    echartsInit() {
      const list = [
                {value:9235, name:'实际在校学生人数'},
                {value:274, name:'当日请假离校人数（不返校）'},
                {value:310, name:'未返校学生人数'},
                {value:5, name:'重点人员管控'},
                {value:0, name:'其他人员测试'}
            ]
      
			this.$echarts.init(document.getElementById('mainPie')).setOption({
        series : [{
            name: '人员情况',
            type: 'pie',    // 设置图表类型为饼图
            radius: '55%',  // 饼图的半径，外半径为可视区尺寸（容器高宽中较小一项）的 55% 长度。
            data:list,
            itemStyle:{
              normal:{
                label:this.labelStyle,
                labelLine :{show:true}
              }
            }
        }]
			})
		},
    echartsInitLittle(){
      //在校
      const list1 = [
                {value:925, name:'教学区\n宿舍区'},
                {value:74, name:'教学区其他\n地方'},
                {value:10, name:'家属区'},
                {value:50, name:'走读生'},
                
            ]
      this.$echarts.init(document.getElementById('inSchool')).setOption({
        series : [{
            name: '实际在校',
            type: 'pie',    // 设置图表类型为饼图
            radius: '55%',  // 饼图的半径，外半径为可视区尺寸（容器高宽中较小一项）的 55% 长度。
            data:list1,
            minAngle: 15,//最小角度
            startAngle:270, //起始角度
            itemStyle:{
              normal:{
                label:this.labelStyle,
                labelLine :{show:true}
              }
            }
        }]
			})
      //未返校
      const list2 = [          // 数据数组，name 为数据项名称，value 为数据项值
                {value:85, name:'因疫暂缓\n返校人数'},
                {value:274, name:'因就业实习\n暂缓返校人数'},
                {value:20, name:'因其他暂缓\n返校人数'},
            ]
      this.$echarts.init(document.getElementById('outSchool')).setOption({
        series : [{
            name: '未返校',
            type: 'pie',    // 设置图表类型为饼图
            radius: '55%',  // 饼图的半径，外半径为可视区尺寸（容器高宽中较小一项）的 55% 长度。
            data:list2,
            itemStyle:{
              normal:{
                label:this.labelStyle,
                labelLine :{show:true}
              }
            }
        }]
			})
      //请假
      const list3 = [          // 数据数组，name 为数据项名称，value 为数据项值
                {value:235, name:'市内'},
                {value:14, name:'省内市外'},
                {value:0, name:'省外'},
            ]
      this.$echarts.init(document.getElementById('leaveStu')).setOption({
        series : [{
            name: '请假离校',
            type: 'pie',    // 设置图表类型为饼图
            radius: '55%',  // 饼图的半径，外半径为可视区尺寸（容器高宽中较小一项）的 55% 长度。
            data:list3,
            itemStyle:{
              normal:{
                label:this.labelStyle,
                labelLine :{show:true}
              }
            }
        }]
			})
      //重点
      const list4 = [          // 数据数组，name 为数据项名称，value 为数据项值
                {value:44, name:'校外实际集中\n隔离人数'},
                {value:27, name:'校内实际居家\n隔离人数'},
                {value:110, name:'校内实际居家\n健康监测人数'},
            ]
      this.$echarts.init(document.getElementById('specialStu')).setOption({
        series : [{
            name: '重点监测',
            type: 'pie',    // 设置图表类型为饼图
            radius: '55%',  // 饼图的半径，外半径为可视区尺寸（容器高宽中较小一项）的 55% 长度。
            data:list4,
            itemStyle:{
              normal:{
                label:this.labelStyle,
                labelLine :{show:true}
              }
            }
        }]
			})
    }
  },
};
</script>

<style scoped lang="scss">
.mainClass{
  display: flex;
  width: 50%;
  height: 500px;
  flex-wrap: wrap;
}
.littleClass{
  // display: flex;
  width: 50%;
  height: 50%;
}
.home {
  display: flex;
}
</style>

