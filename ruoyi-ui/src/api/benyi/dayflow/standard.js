import request from '@/utils/request'

// 查询一日流程标准列表
export function listStandard(query) {
    return request({
        url: '/benyi/dayflow/standard/list',
        method: 'get',
        params: query
    })
}

// 查询一日流程标准详细
export function getStandard(sid) {
    return request({
        url: '/benyi/dayflow/standard/' + sid,
        method: 'get'
    })
}


// 新增一日流程标准
export function addStandard(data) {
    return request({
        url: '/benyi/dayflow/standard',
        method: 'post',
        data: data
    })
}

// 修改一日流程标准
export function updateStandard(data) {
    return request({
        url: '/benyi/dayflow/standard',
        method: 'put',
        data: data
    })
}

// 删除一日流程标准
export function delStandard(sid) {
    return request({
        url: '/benyi/dayflow/standard/' + sid,
        method: 'delete'
    })
}

// 导出一日流程标准
export function exportStandard(query) {
    return request({
        url: '/benyi/dayflow/standard/export',
        method: 'get',
        params: query
    })
}