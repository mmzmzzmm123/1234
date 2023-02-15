import request from '@/utils/request2'

// 查询学生基本信息列表
export function listStu(query) {
  return request({
    url: '/stuManage/stu/list',
    method: 'get',
    params: query
  })
}

