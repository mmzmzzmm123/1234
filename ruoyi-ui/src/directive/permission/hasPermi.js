/**
 * v-hasPermi 操作权限处理
 * Copyright (c) 2019 ruoyihandleHasPermi
 */

import store from "@/store";

//判断接口数据判断是否有权限，不包括v-if中的判断。
function hasPermi(el, value) {
  const all_permission = "*:*:*";
  const permissions = store.getters && store.getters.permissions;
  const permissionFlag = value;
  const hasPermissions = permissions.some((permission) => {
    return all_permission === permission || permissionFlag.includes(permission);
  });
  if (!hasPermissions) {
    el.parentNode && (el.style.display = "none");
  }
}

//更新元素显示/隐藏
function updateEle(el, binding, vnode) {
  const { value } = binding;
  el.style.display = el.getAttribute("hasPemiDisplay"); //获取元素首次的display属性

  if (value && Object.prototype.toString.call(value) === "[object Object]") {
    //兼容v-if的判断
    if (value.vIf) {
      hasPermi(el, value.permi);
    } else {
      el.parentNode && (el.style.display = "none");
    }
  } else if (value && value instanceof Array && value.length > 0) {
    //仅针对接口数据权限判断
    hasPermi(el, value);
  } else {
    throw new Error(`请设置操作权限标签值`);
  }
}

export default {
  bind(el, binding, vnode) {
    el.setAttribute("hasPemiDisplay", el.style.display); //将元素首次的display存储起来
  },
  inserted: updateEle,
  update: updateEle,
};
