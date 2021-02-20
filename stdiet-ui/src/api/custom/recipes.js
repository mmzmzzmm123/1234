import request from "@/utils/request";

export function getRecipesApi(id) {
  return request({
    url: "/custom/recipes/" + id,
    method: "get"
  });
}

export function updateDishesDetailApi(data) {
  return request({
    url: "/custom/recipes",
    method: "put",
    data
  });
}

export function addDishesApi(data) {
  return request({
    url: "/custom/recipes",
    method: "post",
    data
  });
}

export function deleteDishesApi(cid) {
  return request({
    url: "/custom/recipes/dishes/" + cid,
    method: "delete"
  });
}
