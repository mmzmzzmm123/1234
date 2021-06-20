import request from "@/utils/request";

// 查询家庭教育列表
export function listFamilyedu(query) {
  return request({
    url: "/benyi/familyedu/list",
    method: "get",
    params: query
  });
}

// 查询家庭教育详细
export function getFamilyedu(id) {
  return request({
    url: "/benyi/familyedu/" + id,
    method: "get"
  });
}

// 新增家庭教育
export function addFamilyedu(data) {
  return request({
    url: "/benyi/familyedu",
    method: "post",
    data: data
  });
}

// 修改家庭教育
export function updateFamilyedu(data) {
  return request({
    url: "/benyi/familyedu",
    method: "put",
    data: data
  });
}

// 删除家庭教育
export function delFamilyedu(id) {
  return request({
    url: "/benyi/familyedu/" + id,
    method: "delete"
  });
}

// 导出家庭教育
export function exportFamilyedu(query) {
  return request({
    url: "/benyi/familyedu/export",
    method: "get",
    params: query
  });
}
