
/**
 * 校验字符串是否为空
 * return true:为空，反之
 */
let isEmpty = function(str){
  if (str!=null&&str!=undefined&&str!=""&&str!="null") {
    return false;
  }
  return true;
}
/**
 * 校验字符串是否不为空
 * return true:不为空，反之
 */
let isNotEmpty = function(str){
  return !isEmpty(str);
}

module.exports = {
  isEmpty:isEmpty,
  isNotEmpty: isNotEmpty
}
