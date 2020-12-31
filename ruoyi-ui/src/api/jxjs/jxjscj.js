import request from '@/utils/request'

// 查询见习教师成绩列表
export function listJxjscj(query) {
    return request({
        url: '/jxjs/jxjscj/list',
        method: 'get',
        params: query
    })
}

// 查询见习教师成绩详细
export function getJxjscj(id) {
    return request({
        url: '/jxjs/jxjscj/' + id,
        method: 'get'
    })
}

// 新增见习教师成绩
export function addJxjscj(data) {
    return request({
        url: '/jxjs/jxjscj',
        method: 'post',
        data: data
    })
}

// 修改见习教师成绩
export function updateJxjscj(data) {
    return request({
        url: '/jxjs/jxjscj',
        method: 'put',
        data: data
    })
}

// 删除见习教师成绩
export function delJxjscj(id) {
    return request({
        url: '/jxjs/jxjscj/' + id,
        method: 'delete'
    })
}

// 导出见习教师成绩
export function exportJxjscj(query) {
    return request({
        url: '/jxjs/jxjscj/export',
        method: 'get',
        params: query
    })
}

// 下载用户导入模板
export function importTemplate() {
    return request({
        url: '/jxjs/jxjscj/importTemplate',
        method: 'get'
    })
}