import request from "@/utils/request";
import { praseStrEmpty } from "@/utils/ruoyi";

// 查询用户列表
export function listUser(query) {
  return request({
    url: "/system/user/list",
    method: "get",
    params: query
  });
}

// 查询用户列表
export function listlrrUser(query) {
  return request({
    url: "/system/user/lrrlist",
    method: "get",
    params: query
  });
}

// 查询用户详细
export function getUser(userId) {
  return request({
    url: "/system/user/" + praseStrEmpty(userId),
    method: "get"
  });
}

// 查询用户列表根据roleId
export function getUsersByRoleId() {
  return request({
    url: "/system/user/listbyroleid",
    method: "get"
  });
}

// 根据roleid查询用户列表
export function getUserOnlyByRoleId(roleId) {
  return request({
    url: "/system/user/onlybyroleid/" + roleId,
    method: "get"
  });
}

// 新增用户
export function addUser(data) {
  return request({
    url: "/system/user",
    method: "post",
    data: data
  });
}

// 修改用户
export function updateUser(data) {
  return request({
    url: "/system/user",
    method: "put",
    data: data
  });
}

// 删除用户
export function delUser(userId) {
  return request({
    url: "/system/user/" + userId,
    method: "delete"
  });
}

// 绑定微信
export function bindwx(code) {
  return request({
    url: "/system/user/bindwx/" + code,
    method: "post"
  });
}

// 是否已经绑定微信
export function isbindwx() {
  return request({
    url: "/system/user/isbindwx",
    method: "get"
  });
}

// 导出用户
export function exportUser(query) {
  return request({
    url: "/system/user/export",
    method: "get",
    params: query
  });
}

// 用户密码重置
export function resetUserPwd(userId, password) {
  const data = {
    userId,
    password
  };
  return request({
    url: "/system/user/resetPwd",
    method: "put",
    data: data
  });
}

// 用户状态修改
export function changeUserStatus(userId, status) {
  const data = {
    userId,
    status
  };
  return request({
    url: "/system/user/changeStatus",
    method: "put",
    data: data
  });
}

// 查询用户个人信息
export function getUserProfile() {
  return request({
    url: "/system/user/profile",
    method: "get"
  });
}

// 修改用户个人信息
export function updateUserProfile(data) {
  return request({
    url: "/system/user/profile",
    method: "put",
    data: data
  });
}

// 用户密码重置
export function updateUserPwd(oldPassword, newPassword) {
  const data = {
    oldPassword,
    newPassword
  };
  return request({
    url: "/system/user/profile/updatePwd",
    method: "put",
    params: data
  });
}

// 用户头像上传
export function uploadAvatar(data) {
  return request({
    url: "/system/user/profile/avatar",
    method: "post",
    data: data
  });
}

// 下载用户导入模板
export function importTemplate() {
  return request({
    url: "/system/user/importTemplate",
    method: "get"
  });
}

// 登录方法
export function wxlogin(code) {
  return request({
    url: "/wxlogin/" + code,
    method: "post"
  });
}
