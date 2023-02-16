import request from '@/utils/request2'

// 查询学生基本信息列表
export function listStu(query) {
  return request({
    url: '/stuManage/stu/list?schoolAreaIds%5B0%5D=1&schoolAreaIds%5B1%5D=2',
    // url: '/stuManage/stu/list',
    method: 'get',
    params: query
  })
}

