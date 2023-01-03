import request from "@/utils/request";

// 查询【请填写功能名称】列表
export function listCityRation(query) {
  return request({
    url: "/system/cityRation/list",
    method: "get",
    params: query,
  });
}

// 查询【请填写功能名称】详细
export function getCityRation(mId) {
  return request({
    url: "/system/cityRation/" + mId,
    method: "get",
  });
}

// 新增【请填写功能名称】
export function addCityRation(data) {
  return request({
    url: "/system/cityRation",
    method: "post",
    data: data,
  });
}

// 修改【请填写功能名称】
export function updateCityRation(data) {
  return request({
    url: "/system/cityRation",
    method: "put",
    data: data,
  });
}

// 删除【请填写功能名称】
export function delCityRation(mId) {
  return request({
    url: "/system/cityRation/" + mId,
    method: "delete",
  });
}
