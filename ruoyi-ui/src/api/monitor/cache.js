import request from '@/utils/request'

// 查询缓存详细
export function getCache() {
  return request({
    url: '/monitor/cache',
    method: 'get'
  })
}

// 删除缓存
export function delRedisCache(key) {
  return request({
    url: '/monitor/cache/'+key,
    method: 'delete'
  })
}

// 清空缓存
export function clearRedisCache() {
  return request({
    url: '/monitor/cache/clear',
    method: 'delete'
  })
}
