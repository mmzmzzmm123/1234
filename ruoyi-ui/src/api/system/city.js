import request from '@/utils/request'

// 查询 城市列表
export function listCity(query) {
    return request({
        url: '/system/city/list',
        method: 'get',
        params: query
    })
}

// 查询 城市详细
export function getCity(code) {
    return request({
        url: '/system/city/' + code,
        method: 'get'
    })
}

// 新增 城市
export function addCity(data) {
    return request({
        url: '/system/city',
        method: 'post',
        data: data
    })
}

// 修改 城市
export function updateCity(data) {
    return request({
        url: '/system/city',
        method: 'put',
        data: data
    })
}

// 删除 城市
export function delCity(code) {
    return request({
        url: '/system/city/' + code,
        method: 'delete'
    })
}
