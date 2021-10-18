import request from "@/utils/request";

// 查询入学准备课程列表
export function listAdmissioncourse(query) {
  return request({
    url: "/benyi/admissioncourse/list",
    method: "get",
    params: query
  });
}

// 查询流程下拉树结构
export function treeselect() {
  return request({
      url: '/benyi/admissioncourse/treeselect',
      method: 'get'
  })
}

// 查询入学准备课程详细
export function getAdmissioncourse(id) {
  return request({
    url: "/benyi/admissioncourse/" + id,
    method: "get"
  });
}

// 新增入学准备课程
export function addAdmissioncourse(data) {
  return request({
    url: "/benyi/admissioncourse",
    method: "post",
    data: data
  });
}

// 修改入学准备课程
export function updateAdmissioncourse(data) {
  return request({
    url: "/benyi/admissioncourse",
    method: "put",
    data: data
  });
}

// 删除入学准备课程
export function delAdmissioncourse(id) {
  return request({
    url: "/benyi/admissioncourse/" + id,
    method: "delete"
  });
}

// 导出入学准备课程
export function exportAdmissioncourse(query) {
  return request({
    url: "/benyi/admissioncourse/export",
    method: "get",
    params: query
  });
}
