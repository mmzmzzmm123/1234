import request from "@/utils/request";

export function fetchTopicList(query) {
  return request({
    url: "/services/topic/list",
    method: "get",
    params: query
  });
}

export function fetchTopicDetail(query) {
  return request({
    url: "/services/topic/detail",
    method: "get",
    params: query
  });
}

export function postTopicReply(data) {
  return request({
    url: "/services/topic/reply",
    method: "post",
    data
  });
}

export function postTopicComment(data) {
  return request({
    url: "/services/topic/comment",
    method: "post",
    data
  });
}
