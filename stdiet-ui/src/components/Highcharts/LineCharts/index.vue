<template>
    <div>
        <highcharts :options="chartOptions" ></highcharts>
    </div>  
</template>

<script>

//highcharts折线图
export default {
  name: "PunchLogChart",
  components: {
      
  },
  data() {
    return {
      chartOptions:{
            chart: {
                type: 'line'
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                categories: this.chartOption.xAxisData
            },
            yAxis: {
                title: {
                    text: this.chartOption.yAxisTitle
                }
            },
            plotOptions: {
                line: {
                    dataLabels: {
                        // 开启数据标签
                        enabled: true          
                    },
                    // 关闭鼠标跟踪，对应的提示框、点击事件会失效
                    enableMouseTracking: false
                }
            },
            series: [{
                name: this.chartOption.seriesName,
                data: this.chartOption.seriesData
            }],
            credits: {  
                enabled: false     //不显示LOGO 
            },
            exporting: {
                buttons:{	//配置按钮选项
                    contextButton:{
                        menuItems: [{
						    text: '下载图片',
						    onclick: function () {
							    this.exportChart({
								    width: 1000
							    });
						    }   
					    }]
                    },
                    /*exportButton:{	//配置导出按钮
                        width:50,
                        symbolSize:20,
                        borderWidth:2,
                        borderRadius:0,
                        hoverBorderColor:'red',
                        height:30,
                        symbolX:25,
                        symbolY:15,
                        x:-150,
                        y:20
                    },*/
                },
                filename: this.chartOption.imageFileName,//导出的文件名
                type:'image/png',//导出的文件类型
                width:1000	//导出的文件宽度
            },
            navigation: {
                buttonOptions: {
				    enabled: true
			    },
		        menuStyle: {
			        background: '#E0E0E0'
		        }
	        }
      }
    };
  },
  methods: {
    
  },
  props:{
      chartOption: {
          type: Object,
          default: function(){
              return {
                imageFileName: "体重趋势变化折线图",
                yAxisTitle: "",
                seriesName: "",
                xAxisData: [],
                seriesData: []
              };
          }
      }
  }
};
</script>

<style lang="scss" scoped>

</style>
