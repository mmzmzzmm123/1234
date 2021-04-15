import request from '@/utils/request'

// 查询评审方案(主持人评审管理-评审方案)列表
export function listZcrpsfa(query) {
  return request({
    url: '/zcrpsgl/zcrpsfa/list',
    method: 'get',
    params: query
  })
}

// 查询评审方案(主持人评审管理-评审方案)详细
export function getZcrpsfa(id) {
  return request({
    url: '/zcrpsgl/zcrpsfa/' + id,
    method: 'get'
  })
}

// 新增评审方案(主持人评审管理-评审方案)
export function addZcrpsfa(data) {
  return request({
    url: '/zcrpsgl/zcrpsfa',
    method: 'post',
    data: data
  })
}

// 修改评审方案(主持人评审管理-评审方案)
export function updateZcrpsfa(data) {
  return request({
    url: '/zcrpsgl/zcrpsfa',
    method: 'put',
    data: data
  })
}

// 删除评审方案(主持人评审管理-评审方案)
export function delZcrpsfa(id) {
  return request({
    url: '/zcrpsgl/zcrpsfa/' + id,
    method: 'delete'
  })
}

// 启用评审方案(主持人评审管理-评审方案)
export function openStatusZcrpsfa(id) {
  return request({
    url: '/zcrpsgl/zcrpsfa/openstatus',
    method: 'post',
    params: {
      'id':id
    }
  })
}

// 停止评审方案(主持人评审管理-评审方案)
export function stopStatusZcrpsfa(id) {
  return request({
    url: '/zcrpsgl/zcrpsfa/stopstatus',
    method: 'post',
    params: {
      'id':id
    }
  })
}

// 查询基地校列表
export function listJdx() {
  return request({
    url: '/jxjs/jdx/list',
    method: 'get'
  })
}


// 回显基地类型
export function selectJdtype(datas, value) {
  var actions = [];
  Object.keys(datas).some((key) => {
    if (datas[key].id == ('' + value)) {
      actions.push(datas[key].jdxmc);
      return true;
    }
  })
  return actions.join('');
}
