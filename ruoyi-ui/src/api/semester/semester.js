import request from "@/utils/request";

// 查询所有学期
export function semesterList(query) {
  return request({
    url: '/semester/list',
    method: 'get',
    data:query
  })
}
