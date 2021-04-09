import request from "@/utils/request";

export function listRecipesTemplate(data) {
  return request({
    url: "/recipes/template/list",
    method: "post",
    // params: query
    data
  });
}

export function getRecipesTemplateDetail(id) {
  return request({
    url: "/recipes/template/" + id,
    method: "get"
  });
}

export function addRecipesTemplate(data) {
  return request({
    url: "/recipes/template/add",
    method: "post",
    data: data
  });
}

export function updateRecipesTemplate(data) {
  return request({
    url: "/recipes/template/update",
    method: "put",
    data: data
  });
}

export function deleteRecipesTemplate(id) {
  return request({
    url: "/recipes/template/delete/" + id,
    method: "delete"
  });
}
