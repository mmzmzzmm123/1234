import request from '@/utils/request'

// 查询学员评审方案列表
export function listXypsfa(query) {
    return request({
        url: '/xypsgl/xypsfa/list',
        method: 'get',
        params: query
    })
}

// 查询学员评审方案详细
export function getXypsfa(id) {
    return request({
        url: '/xypsgl/xypsfa/' + id,
        method: 'get'
    })
}

// 新增学员评审方案
export function addXypsfa(data) {
    return request({
        url: '/xypsgl/xypsfa',
        method: 'post',
        data: data
    })
}

// 修改学员评审方案
export function updateXypsfa(data) {
    return request({
        url: '/xypsgl/xypsfa',
        method: 'put',
        data: data
    })
}

// 删除学员评审方案
export function delXypsfa(id) {
    return request({
        url: '/xypsgl/xypsfa/' + id,
        method: 'delete'
    })
}

// 导出学员评审方案
export function exportXypsfa(query) {
    return request({
        url: '/xypsgl/xypsfa/export',
        method: 'get',
        params: query
    })
}