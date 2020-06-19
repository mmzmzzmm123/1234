import request from '@/utils/request'

// 查询一日流程列表
export function listDetail(query) {
    return request({
        url: '/benyi/dayflow/dayflowmanger/list',
        method: 'get',
        params: query
    })
}


// 查询一日流程详细
export function getDetail(id) {
    return request({
        url: '/benyi/dayflow/dayflowmanger/' + id,
        method: 'get'
    })
}

// 查询流程下拉树结构
export function treeselect() {
    return request({
        url: '/benyi/dayflow/dayflowmanger/treeselect',
        method: 'get'
    })
}

// 新增一日流程
export function addDetail(data) {
    return request({
        url: '/benyi/dayflow/dayflowmanger',
        method: 'post',
        data: data
    })
}

// 修改一日流程
export function updateDetail(data) {
    return request({
        url: '/benyi/dayflow/dayflowmanger',
        method: 'put',
        data: data
    })
}

// 删除一日流程
export function delDetail(id) {
    return request({
        url: '/benyi/dayflow/dayflowmanger/' + id,
        method: 'delete'
    })
}

// 导出一日流程
export function exportDetail(query) {
    return request({
        url: '/benyi/dayflow/dayflowmanger/export',
        method: 'get',
        params: query
    })
}