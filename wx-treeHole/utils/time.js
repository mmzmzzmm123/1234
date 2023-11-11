function getPrevWeekDataList() {
  let weekData = getWeekDataList();
  let monday = new Date(weekData[0]);
  let tempArr = get_date(7,monday);
  console.log(tempArr);
  return [tempArr[0].date, tempArr[tempArr.length-1].date];
}
/**
 * 获取本周日期
 */
function getWeekDataList() {
  let weekList = [];
  let date = new Date();
  //判断本日期是否为周日，获取本周一日期
  if (date.getDay() == "0") {
    date.setDate(date.getDate() - 6);
  } else {
    date.setDate(date.getDate() - date.getDay() + 1);
  }
  let myDate = date.getDate();
  let myMonth = date.getMonth() + 1;
  if (date.getDate() < 10) {
    myDate = '0' + myDate;
  }
  if (date.getMonth() + 1 < 10) {
    myMonth = '0' + myMonth;
  }
  weekList.push(date.getFullYear() + "-" + myMonth + "-" + myDate);
  // 获取周二以后日期
  for (let i = 0; i < 6; i++) {
    date.setDate(date.getDate() + 1);
    myDate = date.getDate();
    myMonth = date.getMonth() + 1;
    if (date.getDate() < 10) {
      myDate = '0' + myDate;
    }
    if (date.getMonth() + 1 < 10) {
      myMonth = '0' + myMonth;
    }
    if(i == 5){
      weekList.push(date.getFullYear() + "-" + myMonth + "-" + myDate);
    }
  }
  return weekList;
}

// 格式化日期为字符串
function formatDate(date) {
  let year = date.getFullYear();
  let month = (date.getMonth() + 1).toString().padStart(2, '0');
  let day = date.getDate().toString().padStart(2, '0');
  return year + '-' + month + '-' + day;
}

function get_date(num, dateStr) {
  let dateArray = []
  //获取今天日期
  let myDate = new Date(dateStr)
  myDate.setDate(myDate.getDate() - num)
  let dateTemp;  // 临时日期数据
  let flag = 1;
  for (let i = 0; i < num; i++) {
      dateTemp = myDate.getFullYear() + '-' + (myDate.getMonth() + 1) + "-" + myDate.getDate()
      dateArray.push({
          date: dateTemp
      })
      myDate.setDate(myDate.getDate() + flag);
  }
  return dateArray;
}

module.exports = {
  getWeekDataList: getWeekDataList,
  getPrevWeekDataList: getPrevWeekDataList
}