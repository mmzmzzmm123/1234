import request from '@/utils/request'

// 查询评估内容列表
export function listAssessmentcontent(query) {
    return request({
        url: '/benyi/assessmentcontent/list',
        method: 'get',
        params: query
    })
}

// 查询部门下拉树结构
export function treeselect() {
    return request({
        url: '/benyi/assessmentcontent/treeselect',
        method: 'get'
    })
}


// 查询评估内容详细
export function getAssessmentcontent(id) {
    return request({
        url: '/benyi/assessmentcontent/' + id,
        method: 'get'
    })
}

// 新增评估内容
export function addAssessmentcontent(data) {
    return request({
        url: '/benyi/assessmentcontent',
        method: 'post',
        data: data
    })
}

// 修改评估内容
export function updateAssessmentcontent(data) {
    return request({
        url: '/benyi/assessmentcontent',
        method: 'put',
        data: data
    })
}

// 删除评估内容
export function delAssessmentcontent(id) {
    return request({
        url: '/benyi/assessmentcontent/' + id,
        method: 'delete'
    })
}

// 导出评估内容
export function exportAssessmentcontent(query) {
    return request({
        url: '/benyi/assessmentcontent/export',
        method: 'get',
        params: query
    })
}