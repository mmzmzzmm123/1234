import request from '@/utils/request'

// 查询周计划(家长和教育部门细化)列表
export function listPlanweekitem(query) {
  return request({
    url: '/benyi/planweekitem/list',
    method: 'get',
    params: query
  })
}

// 查询周计划(家长和教育部门细化)详细
export function getPlanweekitem(id) {
  return request({
    url: '/benyi/planweekitem/' + id,
    method: 'get'
  })
}

// 新增周计划(家长和教育部门细化)
export function addPlanweekitem(data) {
  return request({
    url: '/benyi/planweekitem',
    method: 'post',
    data: data
  })
}

// 修改周计划(家长和教育部门细化)
export function updatePlanweekitem(data) {
  return request({
    url: '/benyi/planweekitem',
    method: 'put',
    data: data
  })
}

// 删除周计划(家长和教育部门细化)
export function delPlanweekitem(id) {
  return request({
    url: '/benyi/planweekitem/' + id,
    method: 'delete'
  })
}

// 导出周计划(家长和教育部门细化)
export function exportPlanweekitem(query) {
  return request({
    url: '/benyi/planweekitem/export',
    method: 'get',
    params: query
  })
}