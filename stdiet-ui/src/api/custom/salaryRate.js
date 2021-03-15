import request from '@/utils/request'

// 下载薪资导入模板
export function importTemplate() {
    return request({
      url: '/custom/salaryRate/importTemplate',
      method: 'get'
    })
}