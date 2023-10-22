function getPrevWeekDataList() {
  var today = new Date();
  var currentDay = today.getDay();
  var diff = today.getDate() - currentDay + (currentDay === 0 ? -6 : 1); // Adjust when today is Sunday
  var lastMonday = new Date(today.setDate(diff - 7));
  var lastSunday = new Date(today.setDate(diff - 1));
  return [formatDate(lastMonday), formatDate(lastSunday)];
}

function getWeekDataList() {
  var today = new Date();
  var currentDay = today.getDay();
  var diff = today.getDate() - currentDay + (currentDay === 0 ? -6 : 1); // Adjust when today is Sunday
  var currentMonday = new Date(today.setDate(diff));
  var currentSunday = new Date(today.setDate(diff + 6));
  return [formatDate(currentMonday), formatDate(currentSunday)];
}

// 格式化日期为字符串
function formatDate(date) {
  var year = date.getFullYear();
  var month = (date.getMonth() + 1).toString().padStart(2, '0');
  var day = date.getDate().toString().padStart(2, '0');
  return year + '-' + month + '-' + day;
}

module.exports = {
  getWeekDataList: getWeekDataList,
  getPrevWeekDataList: getPrevWeekDataList
}