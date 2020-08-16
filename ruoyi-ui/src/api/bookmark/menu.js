import request from '@/utils/request'




// 查询用户 书签菜单
export function listMenuByUserId() {
  return request({
    url: '/bookmark/menu/selectMenuByUserID',
    method: 'get'
  })
}

// 查询书签菜单列表
export function listMenu(query) {
  return request({
    url: '/bookmark/menu/list',
    method: 'get',
    params: query
  })
}

// 查询书签菜单详细
export function getMenu(menuId) {
  return request({
    url: '/bookmark/menu/' + menuId,
    method: 'get'
  })
}

// 新增书签菜单
export function addMenu(data) {
  return request({
    url: '/bookmark/menu',
    method: 'post',
    data: data
  })
}

// 修改书签菜单
export function updateMenu(data) {
  return request({
    url: '/bookmark/menu',
    method: 'put',
    data: data
  })
}

// 删除书签菜单
export function delMenu(menuId) {
  return request({
    url: '/bookmark/menu/' + menuId,
    method: 'delete'
  })
}

// 导出书签菜单
export function exportMenu(query) {
  return request({
    url: '/bookmark/menu/export',
    method: 'get',
    params: query
  })
}
