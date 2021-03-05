import request from "@/utils/request";

// 查询食谱计划列表
export function listRecipesPlan(query) {
  return request({
    url: "/recipes/recipesPlan/list",
    method: "get",
    params: query
  });
}

// 查询食谱计划详细
export function getRecipesPlan(id) {
  return request({
    url: "/recipes/recipesPlan/" + id,
    method: "get"
  });
}

// 修改食谱计划
export function updateRecipesPlan(data) {
  return request({
    url: "/recipes/recipesPlan",
    method: "put",
    data: data
  });
}

// 导出食谱计划
export function exportRecipesPlan(query) {
  return request({
    url: "/recipes/recipesPlan/export",
    method: "get",
    params: query
  });
}

export function listRecipesPlanByCusId(id) {
  return request({
    url: "/recipes/recipesPlan/list/" + id,
    method: "get"
  });
}
