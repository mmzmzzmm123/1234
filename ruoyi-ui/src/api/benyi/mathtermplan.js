import request from '@/utils/request'

// 查询游戏数学学期计划列表
export function listMathtermplan(query) {
    return request({
        url: '/benyi/mathtermplan/list',
        method: 'get',
        params: query
    })
}

// 查询游戏数学学期计划详细
export function getMathtermplan(id) {
    return request({
        url: '/benyi/mathtermplan/' + id,
        method: 'get'
    })
}

// 新增游戏数学学期计划
export function addMathtermplan(data) {
    return request({
        url: '/benyi/mathtermplan',
        method: 'post',
        data: data
    })
}

// 修改游戏数学学期计划
export function updateMathtermplan(data) {
    return request({
        url: '/benyi/mathtermplan',
        method: 'put',
        data: data
    })
}

// 提交主题整合学期计划
export function checkTermplan(id) {
    return request({
        url: '/benyi/mathtermplan/check/' + id,
        method: 'post'
    })
}

// 删除游戏数学学期计划
export function delMathtermplan(id) {
    return request({
        url: '/benyi/mathtermplan/' + id,
        method: 'delete'
    })
}

// 导出游戏数学学期计划
export function exportMathtermplan(query) {
    return request({
        url: '/benyi/mathtermplan/export',
        method: 'get',
        params: query
    })
}