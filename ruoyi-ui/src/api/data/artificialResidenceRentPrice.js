import request from '@/utils/request'

// 查询人工住宅租赁基价列表
export function list(query) {
  return request({
    url: '/data/rent-price/residence/artificial/list',
    method: 'get',
    params: query
  })
}

// 查询人工住宅租赁 年月 列表
export function getYearMonthList() {
  return request({
    url: '/data/rent-price/residence/artificial/yearmonth',
    method: 'get'
  })
}

/**
 * 从计算租赁租金同步到人工修正租赁租金
 * @param {年月} yearMonth
 */
export function importSync(yearMonth) {
  return request({
    url: '/data/rent-price/residence/artificial/importSync/' + yearMonth,
    method: 'get'
  })
}
/**
 * 导出
 * @param {查询条件} query
 */
export function export2File(query) {
  return request({
    url: '/data/rent-price/residence/artificial/export',
    method: 'get',
    params: query
  })
}

/**
 * 获取单条数据
 * @param {id} id
 */
export function getById(yearMonth, id) {
  return request({
    url: '/data/rent-price/residence/artificial/' + yearMonth + '/' + id,
    method: 'get'
  })
}

/**
 * 修改人工住宅租赁基价
 * @param {表单数据} data
 */
export function update(data) {
  return request({
    url: '/data/rent-price/residence/artificial/',
    method: 'put',
    data: data
  })
}
