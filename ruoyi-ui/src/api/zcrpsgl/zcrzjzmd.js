import request from '@/utils/request'

// 查询主持人报名专家组名单列表
export function listZcrzjzmd(query) {
    return request({
        url: '/zcrpsgl/zcrzjzmd/list',
        method: 'get',
        params: query
    })
}

// 查询主持人报名专家组名单详细
export function getZcrzjzmd(id) {
    return request({
        url: '/zcrpsgl/zcrzjzmd/' + id,
        method: 'get'
    })
}

// 新增主持人报名专家组名单
export function addZcrzjzmd(data) {
    return request({
        url: '/zcrpsgl/zcrzjzmd',
        method: 'post',
        data: data
    })
}

// 修改主持人报名专家组名单
export function updateZcrzjzmd(data) {
    return request({
        url: '/zcrpsgl/zcrzjzmd',
        method: 'put',
        data: data
    })
}

// 删除主持人报名专家组名单
export function delZcrzjzmd(id) {
    return request({
        url: '/zcrpsgl/zcrzjzmd/' + id,
        method: 'delete'
    })
}

// 导出主持人报名专家组名单
export function exportZcrzjzmd(query) {
    return request({
        url: '/zcrpsgl/zcrzjzmd/export',
        method: 'get',
        params: query
    })
}