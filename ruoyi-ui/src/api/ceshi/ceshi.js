// 查询部门列表
import request from "@/utils/request";

export function listDept(query) {
  return request({
    url: '/system/dept/list',
    method: 'get',
    params: query
  })
}
