import util from './util';
/**
 * 设置缓存
 * key: 键
 * value： 值
 * expire: 过期时间
 */
let set = function (key, expireKey, value, expire){
  wx.setStorageSync(key, value);
  if (expire>0){
    wx.setStorageSync(expireKey, expire);
  }
}
/**
 * 获取缓存
 * key: 键
 * expireKey: 过期时间key
 * 
 * return 缓存结果
 */
let get = function (key, expireKey){
  let that = this;
  let valObj = wx.getStorageSync(key);
  if (!util.isEmpty(expireKey)){
    let nowExpire = new Date().getTime();
    let expire = wx.getStorageSync(expireKey);//获取到过期时间戳
    if (nowExpire > expire){
      that.removeStorageSync(key);
      that.removeStorageSync(expireKey);
      return null;
    }
  }
  return valObj!=null&&valObj!=""?valObj:null;
}

/**
 * 删除对应缓存
 * 缓存key
 */
let removeStorageSync = function(key){
  wx.removeStorageSync(key);
}


module.exports = {
  set: set,
  get: get,
  removeStorageSync: removeStorageSync
}
