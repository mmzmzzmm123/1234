import request from '@/utils/request'

// 查询主持人基地列表
export function listZcrjdcj(query) {
    return request({
        url: '/zcrpsgl/zcrjdcj/list',
        method: 'get',
        params: query
    })
}

// 查询主持人基地详细
export function getZcrjdcj(id) {
    return request({
        url: '/zcrpsgl/zcrjdcj/' + id,
        method: 'get'
    })
}

// 新增主持人基地
export function addZcrjdcj(data) {
    return request({
        url: '/zcrpsgl/zcrjdcj',
        method: 'post',
        data: data
    })
}

// 修改主持人基地
export function updateZcrjdcj(data) {
    return request({
        url: '/zcrpsgl/zcrjdcj',
        method: 'put',
        data: data
    })
}

// 删除主持人基地
export function delZcrjdcj(id) {
    return request({
        url: '/zcrpsgl/zcrjdcj/' + id,
        method: 'delete'
    })
}

// 导出主持人基地
export function exportZcrjdcj(query) {
    return request({
        url: '/zcrpsgl/zcrjdcj/export',
        method: 'get',
        params: query
    })
}