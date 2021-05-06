import request from '@/utils/request'

// 查询学员报名申请列表
export function listXybmsq(query) {
    return request({
        url: '/xypsgl/xybmsq/list',
        method: 'get',
        params: query
    })
}

// 查询学员学校审核列表
export function listXybmsqXxsh(query) {
    return request({
        url: '/xypsgl/xybmsq/listXxsh',
        method: 'get',
        params: query
    })
}

// 查询学员学校审核列表
export function listXybmsqQjsh(query) {
    return request({
        url: '/xypsgl/xybmsq/listQjsh',
        method: 'get',
        params: query
    })
}

// 查询学员报名申请详细
export function getXybmsq(id) {
    return request({
        url: '/xypsgl/xybmsq/' + id,
        method: 'get'
    })
}

// 新增学员报名申请
export function addXybmsq(data) {
    return request({
        url: '/xypsgl/xybmsq',
        method: 'post',
        data: data
    })
}

// 修改学员报名申请
export function updateXybmsq(data) {
    return request({
        url: '/xypsgl/xybmsq',
        method: 'put',
        data: data
    })
}

// 删除学员报名申请
export function delXybmsq(id) {
    return request({
        url: '/xypsgl/xybmsq/' + id,
        method: 'delete'
    })
}

// 导出学员报名申请
export function exportXybmsq(query) {
    return request({
        url: '/xypsgl/xybmsq/export',
        method: 'get',
        params: query
    })
}