import request from '@/utils/request'

// 查询信息
export function info() {
    return request({
        url: '/system/home/info',
        method: 'get',
    })
}