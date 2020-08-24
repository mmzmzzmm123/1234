import request from '@/utils/request'

// 查询周计划(家长和教育部门)列表
export function listPlanweek(query) {
  return request({
    url: '/benyi/planweek/list',
    method: 'get',
    params: query
  })
}

// 查询周计划(家长和教育部门)详细
export function getPlanweek(id) {
  return request({
    url: '/benyi/planweek/' + id,
    method: 'get'
  })
}

// 新增周计划(家长和教育部门)
export function addPlanweek(data) {
  return request({
    url: '/benyi/planweek',
    method: 'post',
    data: data
  })
}

// 修改周计划(家长和教育部门)
export function updatePlanweek(data) {
  return request({
    url: '/benyi/planweek',
    method: 'put',
    data: data
  })
}

// 删除周计划(家长和教育部门)
export function delPlanweek(id) {
  return request({
    url: '/benyi/planweek/' + id,
    method: 'delete'
  })
}

// 导出周计划(家长和教育部门)
export function exportPlanweek(query) {
  return request({
    url: '/benyi/planweek/export',
    method: 'get',
    params: query
  })
}
