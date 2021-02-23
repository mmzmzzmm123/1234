import request from "@/utils/request";

export function getOptions() {
  return request({
    url: "/custom/post/options",
    method: "get"
  });
}
