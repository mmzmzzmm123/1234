/*
 * @Author: pengyu
 * @Date: 2020-12-08 23:22:30
 * @LastEditTime: 2021-01-03 14:33:44
 * @LastEditors: Please set LastEditors
 * @Description: 房价牌管理-接口
 * @FilePath: \RuoYi-Vue\ruoyi-ui\src\api\priceTag\index.js
 */
import request from '@/utils/request'

/*  酒店信息start   */
export function downloadFile(params){
  return request({
    url: '/common/download',
    method: 'get',
    params
  })
}

// 查询酒店信息
export function getHotelInfo() {
  return request({
    url: '/hotel/hotelInfo/query',
    method: 'get',
  })
}

// 保存酒店信息
export function saveHotelInfo(params) {
  return request({
    url: '/hotel/hotelInfo/save',
    method: 'post',
    params
  })
}

//上传酒店LOGO
export function uploadLogo(data, headers){
  return request({
    url: '/hotel/hotelInfo/logo',
    method: 'post',
    data,
    headers
  })
}

//删除酒店logo
export function deleteLogo(){
  return request({
    url: '/hotel/hotelInfo/deleteLogo',
    method: 'post'
  })
}

//删除酒店二维码
export function deleteQrCode(){
  return request({
    url: '/hotel/hotelInfo/deleteQrCode',
    method: 'post'
  })
}

//获取一级城市
export function getWeatherCity1(){
  return request({
    url: '/hotel/weather/adm1',
    method: 'get'
  })
}
//获取二级城市
export function getWeatherCity2(params){
  return request({
    url: '/hotel/weather/adm2',
    method: 'get',
    params
  })
}
//获取一级城市
export function getWeatherCity3(params){
  return request({
    url: '/hotel/weather/adm3',
    method: 'get',
    params
  })
}
//获取城市信息
export function getCityInfo(params){
  return request({
    url: '/hotel/weather/getCityInfo',
    method: 'get',
    params
  })
}
//获取一级城市
export function getWeatherCity(){
  return request({
    url: '/hotel/weather/adm1',
    method: 'get'
  })
}
/*  酒店信息end   */

/*  房价类别start  */
//查询房价类型
export function getPriceType() {
  return request({
    url: '/hotel/priceType/query',
    method: 'get'
  })
}

//新增房价类别
export function addPriceType(params) {
  return request({
    url: '/hotel/priceType/add',
    method: 'post',
    params
  })
}

//修改房价类别
export function editPriceType(params) {
  return request({
    url: '/hotel/priceType/edit',
    method: 'put',
    params
  })
}

//删除房价类别
export function deletePriceType(params) {
  return request({
    url: '/hotel/priceType/delete',
    method: 'delete',
    params
  })
}

//显示隐藏房价类别
export function switchPriceType(params) {
  return request({
    url: '/hotel/priceType/switched',
    method: 'post',
    params
  })
}
/*  房价类别end  */

/*  房型价格start  */
//查询房间价格列表
export function getRoomPriceList() {
  return request({
    url: '/hotel/roomPrice/query',
    method: 'get'
  })
}

//新增房间价格
export function addRoomPrice(data) {
  return request({
    url: '/hotel/roomPrice/add',
    method: 'post',
    data
  })
}

//修改房间价格
export function editRoomPrice(data) {
  return request({
    url: '/hotel/roomPrice/edit',
    method: 'put',
    data
  })
}

//删除房间价格
export function deleteRoomPrice(params) {
  return request({
    url: '/hotel/roomPrice/delete',
    method: 'delete',
    params
  })
}
/*  房型价格end  */

/*  图片展示start  */
//获取房间图片信息
export function getRoomPicture() {
  return request({
    url: '/hotel/roomPicture/query',
    method: 'get'
  })
}

//删除房间图片信息
export function deleteRoomPicture(params) {
  return request({
    url: '/hotel/roomPicture/delete',
    method: 'delete',
    params
  })
}

//替换房间图片信息
export function replaceRoomPicture(params) {
  return request({
    url: '/hotel/roomPicture/replace',
    method: 'delete',
    params
  })
}

//停用启用图片
export function enableRoomPicture(params) {
  return request({
    url: '/hotel/roomPicture/switched',
    method: 'post',
    params
  })
}

//修改图片名称序号
export function editRoomPicture(params) {
  return request({
    url: '/hotel/roomPicture/rename',
    method: 'post',
    params
  })
}

//房间图片排序
export function orderRoomPicture(params) {
  return request({
    url: '/hotel/roomPicture/order',
    method: 'post',
    params
  })
}
/*  图片展示end  */

/*  主题设置start  */
//获取酒店所有主题
export function queryThemeSet() {
  return request({
    url: '/hotel/theme/list',
    method: 'get'
  })
}

//获取酒店当前主题
export function queryCurTheme() {
  return request({
    url: '/hotel/theme/query',
    method: 'get'
  })
}

//配置酒店主题
export function saveThemeSet(params){
  return request({
    url: '/hotel/theme/config',
    method: 'post',
    params
  })
}

//重命名酒店主题
export function renameThemeSet(params){
  return request({
    url: '/hotel/theme/rename',
    method: 'post',
    params
  })
}

//删除名酒店主题
export function deleteThemeSet(params){
  return request({
    url: '/hotel/theme/delete',
    method: 'delete',
    params
  })
}

/*  主题设置end  */



