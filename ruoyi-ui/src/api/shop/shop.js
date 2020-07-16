import request from '@/utils/request'
import { praseStrEmpty } from "@/utils/ruoyi";

// 查询电商店铺列表
export function listShop(query) {
  return request({
    url: '/shop/shop/list',
    method: 'get',
    params: query
  })
}

// 查询电商店铺详细
export function getShop(id) {
  return request({
    url: '/shop/shop/' + praseStrEmpty(id),
    method: 'get'
  })
}

// 新增电商店铺
export function addShop(data) {
  return request({
    url: '/shop/shop',
    method: 'post',
    data: data
  })
}

// 修改电商店铺
export function updateShop(data) {
  return request({
    url: '/shop/shop',
    method: 'put',
    data: data
  })
}

// 删除电商店铺
export function delShop(id) {
  return request({
    url: '/shop/shop/' + id,
    method: 'delete'
  })
}

// 导出电商店铺
export function exportShop(query) {
  return request({
    url: '/shop/shop/export',
    method: 'get',
    params: query
  })
}

// 获取授权地址（根据平台id)
export function authUrl(shopTypeId) {
  return request({
    url: '/shop/shop/auth_url/' + shopTypeId,
    method: 'get'
  })
}