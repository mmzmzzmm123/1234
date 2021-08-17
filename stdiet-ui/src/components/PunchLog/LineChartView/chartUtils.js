import { iconDict } from "./utils";

function getComOption(title, data) {
  return {
    title: {
      text: title,
      left: 5,
      top: 8,
      textStyle: {
        // color: "#fff"
      }
    },
    textStyle: {
      // fontFamily: "monospace"
    },
    tooltip: {
      show: true,
      trigger: "axis",
      axisPointer: {
        lineStyle: {
          type: "dashed"
        }
      }
    },
    grid: {
      top: 40,
      left: 10,
      right: 20,
      bottom: 10,
      containLabel: true
    },
    dataZoom: {
      type: "slider",
      show: false,
      zoomLock: true,
      startValue: 0,
      endValue: 6,
      height: 16,
      bottom: 8,
      textStyle: {
        color: "#fff"
      },
      handleStyle: {
        // borderColor: "#fff"
      }
      // filterMode: 'none',
    },
    graphic: [],
    xAxis: {
      type: "category",
      inverse: true,
      nameTextStyle: {
        // color: "#fff"
      },
      axisLine: {
        show: false
      },
      axisTick: {
        show: false
      },
      axisLabel: {
        textStyle: {
          // color: "#fff"
        },
        formatter: val => val.substr(5).replace("-", "/")
      },
      data: data.map(obj => obj.name)
    },
    yAxis: {
      type: "value",
      axisLine: {
        show: false
      },
      axisLabel: {
        textStyle: {
          // color: "#fff"
        }
      },
      axisTick: {
        show: false
      },
      splitLine: {
        show: false
      }
    },
    series: [
      {
        type: "line",
        lineStyle: {
          color: "#FF6E67"
        },
        itemStyle: {
          color: "#333"
        }
        // data,
      }
    ]
  };
}

function getOption(type, data, title, unit, extra) {
  const option = getComOption(title, data);
  option.tooltip.formatter = params => {
    const [param] = params;
    let value = param.data.oriValue;
    // console.log({ type, value });
    unit && (value += unit);
    return [`日期：${param.data.name}`, `${title}：${value}`].join("</br>");
  };

  if (unit) {
    option.graphic.push({
      type: "text",
      right: 30,
      top: 16,
      style: {
        text: `单位：${unit}`,
        // fill: "#fff"
      }
    });
  }
  if (extra) {
    option.graphic.push({
      type: "text",
      right: 30,
      top: 36,
      style: {
        text: extra,
        // fill: "#fff"
      }
    });
  }
  if (type === "water" || type === "weight") {
    option.yAxis.max = value => Math.floor(value.max * 1.5);
    option.yAxis.min = value => Math.floor(value.min * 0.5);
    option.yAxis.splitNumber = 1;
    option.series[0].data = data.map(obj => ({
      ...obj,
      label: { show: true },
      oriValue: obj.value,
      formatter: ({ value }) => (type === "water" ? `${value / 1000}升` : value)
    }));
  } else if (type === "sleepTime" || type === "wakeupTime") {
    option.yAxis.max = 24;
    option.yAxis.min = 0;
    option.yAxis.minInterval = 6;
    option.series[0].data = data.map(obj => {
      const [hour, minute] = obj.value.split(":");
      return {
        ...obj,
        oriValue: obj.value,
        value: parseInt(hour) + parseFloat(minute) / 60
      };
    });
  } else if (
    type === "emotion" ||
    type === "insomnia" ||
    type === "sport" ||
    type === "diet" ||
    type === "defecation" ||
    type === "menstrualPeriod"
  ) {
    const tarObj = iconDict[type];
    const range = Object.keys(tarObj);
    option.grid.left = -20;

    // if (type !== 'emotion' && type !== 'defecation') {
    option.yAxis.axisLabel.formatter = value => {
      return range[value];
    };
    // } else {
    // option.yAxis.axisLabel.show = false;
    // }

    option.yAxis.max = range.length - 0.5;
    option.yAxis.min = -0.5;
    option.series[0].data = data.map(obj => ({
      ...obj,
      value: range.indexOf(obj.value),
      oriValue: obj.value,
      symbol: tarObj[obj.value] ? tarObj[obj.value].imgData : "",
      symbolSize: 20
    }));
  }

  return option;
}

export { getOption };
