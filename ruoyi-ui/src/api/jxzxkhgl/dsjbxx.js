import request from '@/utils/request'

// 查询导师基本信息列表
export function listDsjbxx(query) {
    return request({
        url: '/jxzxkhgl/dsjbxx/list',
        method: 'get',
        params: query
    })
}

// 查询导师基本信息详细
export function getDsjbxx(id) {
    return request({
        url: '/jxzxkhgl/dsjbxx/' + id,
        method: 'get'
    })
}

// 新增导师基本信息
export function addDsjbxx(data) {
    return request({
        url: '/jxzxkhgl/dsjbxx',
        method: 'post',
        data: data
    })
}

// 修改导师基本信息
export function updateDsjbxx(data) {
    return request({
        url: '/jxzxkhgl/dsjbxx',
        method: 'put',
        data: data
    })
}

// 删除导师基本信息
export function delDsjbxx(id) {
    return request({
        url: '/jxzxkhgl/dsjbxx/' + id,
        method: 'delete'
    })
}

// 导出导师基本信息
export function exportDsjbxx(query) {
    return request({
        url: '/jxzxkhgl/dsjbxx/export',
        method: 'get',
        params: query
    })
}

// 下载用户导入模板
export function importTemplate() {
    return request({
        url: '/jxzxkhgl/dsjbxx/importTemplate',
        method: 'get'
    })
}