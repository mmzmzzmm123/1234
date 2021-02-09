import request from "@/utils/request";

export function getRecipes(id) {
  return request({
    url: "/recipes/" + id,
    method: "get"
  });
}
