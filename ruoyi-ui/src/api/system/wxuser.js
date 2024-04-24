import request from '@/utils/request'
// import { parseStrEmpty } from "@/utils/ruoyi";

// 查询用户列表
export function listUser(query) {
  return request({
    url: '/wx/user/list',
    method: 'get',
    params: query
  })
}