
import storage from "../utils/storage";
import storageConstant from "../constans/storageConstant";
import util from "./util";

/**
 * 构建header
 */
let buildHeader = function () {
  let header = {
    "authorization": "yingde",
    "flag": "yingde"
  }
  let userInfo = storage.get(storageConstant.userInfo);
  if (util.isNotEmpty(userInfo)) {
    header.authorization = userInfo.token;
  }
  return header;
}

module.exports = {
  buildHeader: buildHeader
}
