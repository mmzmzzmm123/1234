import request from '@/utils/request'

// 查询机器人列表
export function listRobot(query) {
  return request({
    url: '/udef/robot/list',
    method: 'get',
    params: query
  })
}

// 查询机器人详细
export function getRobot(id) {
  return request({
    url: '/udef/robot/' + id,
    method: 'get'
  })
}

// 新增机器人
export function addRobot(data) {
  return request({
    url: '/udef/robot',
    method: 'post',
    data: data
  })
}

// 修改机器人
export function updateRobot(data) {
  return request({
    url: '/udef/robot',
    method: 'put',
    data: data
  })
}

// 删除机器人
export function delRobot(id) {
  return request({
    url: '/udef/robot/' + id,
    method: 'delete'
  })
}
//操作机器人
export function actionOnRobot(id,action) {
  return request({
    url: '/udef/robot/action/'+id+'?opt='+action,
    method: 'put'
  })
}
