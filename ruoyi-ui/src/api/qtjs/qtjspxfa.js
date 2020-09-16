import request from '@/utils/request'

// 查询群体教师评选方案列表
export function listQtjspxfa(query) {
    return request({
        url: '/qtjs/qtjspxfa/list',
        method: 'get',
        params: query
    })
}

// 查询群体教师评选方案详细
export function getQtjspxfa(id) {
    return request({
        url: '/qtjs/qtjspxfa/' + id,
        method: 'get'
    })
}

// 新增群体教师评选方案
export function addQtjspxfa(data) {
    return request({
        url: '/qtjs/qtjspxfa',
        method: 'post',
        data: data
    })
}

// 修改群体教师评选方案
export function updateQtjspxfa(data) {
    return request({
        url: '/qtjs/qtjspxfa',
        method: 'put',
        data: data
    })
}

// 删除群体教师评选方案
export function delQtjspxfa(id) {
    return request({
        url: '/qtjs/qtjspxfa/' + id,
        method: 'delete'
    })
}

// 导出群体教师评选方案
export function exportQtjspxfa(query) {
    return request({
        url: '/qtjs/qtjspxfa/export',
        method: 'get',
        params: query
    })
}

// 修改方案状态
export function changeStatus(id, fazt) {
    const data = {
        id,
        fazt
    }
    return request({
        url: '/qtjs/qtjspxfa/changeStatus',
        method: 'put',
        data: data
    })
}

// 复制方案
export function copyQtjspxfa(id) {
    return request({
        url: '/qtjs/qtjspxfa/copy/' + id,
        method: 'post'
    })
}