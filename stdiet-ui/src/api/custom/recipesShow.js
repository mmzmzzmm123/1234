import request from "@/utils/request";

export function getRecipesPlans(id) {
  return request({
    url: "/wap/recipes/plans/" + id,
    method: "get",
    headers: {
      isToken: false
    }
  });
}

export function getHealthyInfo(id) {
  return request({
    url: "/wap/healthyInfo/" + id,
    method: "get",
    headers: {
      isToken: false
    }
  });
}

export function getRecipesMenuInfoApi(id) {
  return request({
    url: "/wap/recipes/menu/" + id,
    method: "get",
    headers: {
      isToken: false
    }
  });
}

export function getDicts(id) {
  return request({
    url: "/wap/dict/" + id,
    method: "get",
    headers: {
      isToken: false
    }
  });
}

export function getRecipesFullInfo(id) {
  return request({
    url: "/wap/recipes/" + id,
    method: "get",
    headers: {
      isToken: false
    }
  });
}

export function getPlanPause(id) {
  return request({
    url: "/wap/recipes/plan/pause/" + id,
    method: "get",
    headers: {
      isToken: false
    }
  });
}

