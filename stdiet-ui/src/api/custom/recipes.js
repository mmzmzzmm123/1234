import request from "@/utils/request";

export function addRecipesApi(data) {
  return request({
    url: "/custom/recipes",
    method: "post",
    data
  });
}

export function getRecipesApi(id) {
  return request({
    url: "/custom/recipes/" + id,
    method: "get"
  });
}

export function updateDishesDetailApi(data) {
  return request({
    url: "/custom/recipes/dishes",
    method: "put",
    data
  });
}

export function addDishesApi(data) {
  return request({
    url: "/custom/recipes/dishes",
    method: "post",
    data
  });
}

export function deleteDishesApi(id) {
  return request({
    url: "/custom/recipes/dishes/" + id,
    method: "delete"
  });
}

export function replaceMenuApi(data) {
  return request({
    url: "/custom/recipes/menu/replace",
    method: "post",
    data
  });
}
