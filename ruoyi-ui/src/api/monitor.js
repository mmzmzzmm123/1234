import request from '@/utils/request'
export const getMonitorData = (params) => request({
  url: params,
  method: 'get',
});
