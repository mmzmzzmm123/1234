import request from '@/utils/request'

// 调用es查询案例
// 1. 模糊搜索地址
// 2. 模糊搜索小区



// 查询最终住宅租赁基价列表
export function list(query) {
  return request({
    url: '/basic/cases/closing/list',
    method: 'get',
    params: query
  })
}

// // 查询最终住宅租赁基价详细
export function findByText(text) {
  return request({
    url: '/basic/cases/closing/query?text=' + text,
    method: 'get'
  })
}

// // 修改最终住宅租赁基价
// export function update(data) {
//   return request({
//     url: '/data/rent-price/residence/ultimate',
//     method: 'put',
//     data: data
//   })
// }

// // 导出最终住宅租赁基价
// export function export2File(query) {
//   return request({
//     url: '/data/rent-price/residence/ultimate/export',
//     method: 'get',
//     params: query
//   })
// }

// // 查询人工住宅租赁 年月 列表
// export function getYearMonthList() {
//   return request({
//     url: '/data/rent-price/residence/ultimate/yearmonth',
//     method: 'get'
//   })
// }
