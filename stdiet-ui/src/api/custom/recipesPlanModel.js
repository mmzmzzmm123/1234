import request from "@/utils/request";

export function listRecipesModel(query) {
  return request({
    url: "/recipes/model/list",
    method: "get",
    params: query
  });
}

export function addRecipesModel(data){
  return request({
    url: "/recipes/model/add",
    method: "post",
    data: data
  });
}

export function updateRecipesModel(data) {
  return request({
    url: "/recipes/model/update",
    method: "put",
    data: data
  });
}


export function deleteRecipesModel(id) {
  return request({
    url: "/recipes/model/delete/" + id,
    method: "get"
  });
}
