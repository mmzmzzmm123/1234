import request from '@/utils/request'

// 查询邮件用户列表列表
export function listUser(query) {
  return request({
    url: '/mail/user/list',
    method: 'get',
    params: query
  })
}

// 查询邮件用户列表详细
export function getUser(id) {
  return request({
    url: '/mail/user/' + id,
    method: 'get'
  })
}

// 新增邮件用户列表
export function addUser(data) {
  return request({
    url: '/mail/user',
    method: 'post',
    data: data
  })
}

// 修改邮件用户列表
export function updateUser(data) {
  return request({
    url: '/mail/user',
    method: 'put',
    data: data
  })
}

// 删除邮件用户列表
export function delUser(id) {
  return request({
    url: '/mail/user/' + id,
    method: 'delete'
  })
}


// 发送邮件
export function sendMail(data) {
  return request({
    url: '/sendmail/sendSimpleMail',
    method: 'post',
    data: data
  })
}
