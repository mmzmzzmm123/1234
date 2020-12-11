//数组对象方法排序:
export default {
    //排序
  sortByKey(array, key) {
    return array.sort(function (a, b) {
      var x = a[key];
      var y = b[key];
      return ((x < y) ? -1 : ((x > y) ? 1 : 0));
    });
  },
  //回显数组 自定义 数据类型
  selectDictLabelCustomKey(datas, value, dictLabel, dictValue) {
    var actions = [];
    Object.keys(datas).some((key) => {
      if (datas[key][dictValue] == ('' + value)) {
        actions.push(datas[key][dictLabel]);
        return true;
      }
    })
    return actions.join('');
  },

  //日期
  date: {
    getCurrentMonthDay() {
      let date = new Date()
      var year = date.getFullYear()
      var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
      var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      var hours = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
      var minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
      var seconds = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()
      return year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds
    },
    //获取月第一天
    getCurrentMonthFirst() {
      var date = new Date()
      date.setDate(1)
      var month = parseInt(date.getMonth() + 1)
      var day = date.getDate()
      if (month < 10) month = '0' + month
      if (day < 10) day = '0' + day

      return date.getFullYear() + '-' + month + '-' + day  + ' ' + '00:00:00'
    },
    //获取月最后一天
    getCurrentMonthLast() {
      var date = new Date()
      var year = date.getFullYear()
      var month = date.getMonth() + 1
      month = month < 10 ? '0' + month : month
      var day = new Date(year, month, 0)
      return year + '-' + month + '-' + day.getDate()
    },
  },


}
