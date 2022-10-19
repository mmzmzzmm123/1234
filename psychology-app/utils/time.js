var secondIndex = 0; // 初始值
var callTimesInter = null; // 定时器
/**
 * 时分秒换算函数
 * @param {Object} value 秒
 */
export function formatSeconds(value) {
  var secondTime = parseInt(value); // 秒
  var minuteTime = 0; // 分
  var hourTime = 0; // 小时
  if (secondTime > 60) {
    minuteTime = Math.floor(secondTime / 60);
    secondTime = Math.floor(secondTime % 60);
    if (minuteTime > 60) {
      hourTime = Math.floor(minuteTime / 60);
      minuteTime = Math.floor(minuteTime % 60);
    }
  }
  var secondTimes =
    Math.floor(secondTime) < 10
      ? "0" + Math.floor(secondTime)
      : Math.floor(secondTime);
  var result = "00:" + secondTimes;
  if (minuteTime > 0) {
    var minuteTimes =
      Math.floor(minuteTime) < 10
        ? "0" + Math.floor(minuteTime)
        : Math.floor(minuteTime);
    result = minuteTimes + ":" + secondTimes;
  }
  if (hourTime > 0) {
    var hourTimes =
      Math.floor(hourTimes) < 10
        ? "0" + Math.floor(hourTimes)
        : Math.floor(hourTimes);
    result = hourTimes + ":" + minuteTimes + ":" + secondTimes;
  }
  return result;
}

// 通话时间初始化
export function callTimeLoad(ele, flag) {
  ele.innerHTML = "00:00";
  if (flag) {
    callTimesInter = setInterval(function () {
      secondIndex++;
      let time = formatSeconds(secondIndex);
      ele.innerHTML = time;
    }, 1000);
  } else {
    clearInterval(callTimesInter);
    secondIndex = 1;
  }
}
