import request from '@/utils/request'

// 查询一日流程任务列表
export function listDayflowtask(query) {
    return request({
        url: '/benyi/dayflow/dayflowtask/list',
        method: 'get',
        params: query
    })
}

// 查询一日流程任务详细
export function getDayflowtask(code) {
    return request({
        url: '/benyi/dayflow/dayflowtask/' + code,
        method: 'get'
    })
}

// 根据流程名字查询任务信息
export function getDayflow(name) {
    return request({
        url: '/dayflow/dayflowtask/name/' + name,
        method: 'get'
    })
}

// 新增一日流程任务
export function addDayflowtask(data) {
    return request({
        url: '/benyi/dayflow/dayflowtask',
        method: 'post',
        data: data
    })
}

// 修改一日流程任务
export function updateDayflowtask(data) {
    return request({
        url: '/benyi/dayflow/dayflowtask',
        method: 'put',
        data: data
    })
}

// 删除一日流程任务
export function delDayflowtask(code) {
    return request({
        url: '/benyi/dayflow/dayflowtask/' + code,
        method: 'delete'
    })
}

// 导出一日流程任务
export function exportDayflowtask(query) {
    return request({
        url: '/benyi/dayflow/dayflowtask/export',
        method: 'get',
        params: query
    })
}