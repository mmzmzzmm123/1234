import request from '@/utils/request'

// 查询mqtt消息发送接收列表
export function listMqttMsg(query) {
  return request({
    url: '/office/mqttMsg/list',
    method: 'get',
    params: query
  })
}

// 查询mqtt消息发送接收详细
export function getMqttMsg(id) {
  return request({
    url: '/office/mqttMsg/' + id,
    method: 'get'
  })
}

// 新增mqtt消息发送接收
export function addMqttMsg(data) {
  return request({
    url: '/office/mqttMsg',
    method: 'post',
    data: data
  })
}

// 修改mqtt消息发送接收
export function updateMqttMsg(data) {
  return request({
    url: '/office/mqttMsg',
    method: 'put',
    data: data
  })
}

// 删除mqtt消息发送接收
export function delMqttMsg(id) {
  return request({
    url: '/office/mqttMsg/' + id,
    method: 'delete'
  })
}
