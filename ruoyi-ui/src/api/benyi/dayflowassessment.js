import request from "@/utils/request";

// 查询幼儿园一日流程评估列表
export function listDayflowassessment(query) {
  return request({
    url: "/benyi/dayflowassessment/list",
    method: "get",
    params: query
  });
}

// 查询幼儿园一日流程评估详细
export function getDayflowassessment(id) {
  return request({
    url: "/benyi/dayflowassessment/" + id,
    method: "get"
  });
}

// 新增幼儿园一日流程评估
export function addDayflowassessment(data) {
  return request({
    url: "/benyi/dayflowassessment",
    method: "post",
    data: data
  });
}

// 修改幼儿园一日流程评估
export function updateDayflowassessment(data) {
  return request({
    url: "/benyi/dayflowassessment",
    method: "put",
    data: data
  });
}

// 删除幼儿园一日流程评估
export function delDayflowassessment(id) {
  return request({
    url: "/benyi/dayflowassessment/" + id,
    method: "delete"
  });
}

// 导出幼儿园一日流程评估
export function exportDayflowassessment(query) {
  return request({
    url: "/benyi/dayflowassessment/export",
    method: "get",
    params: query
  });
}
