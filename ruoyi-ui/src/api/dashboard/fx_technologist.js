import request from '@/utils/request'

// 查询服务器详细
export function getConfirmation() {
  return request({
    url: '/fx_technologist/getConfirmation',
    method: 'get'
  })
}


export function getBestODM() {
  return request({
    url: '/fx_technologist/getBestODM',
    method: 'get'
  })
}


export function getBestOEM() {
  return request({
    url: '/fx_technologist/getBestOEM',
    method: 'get'
  })
}

export function getFinishedSampleAvgDay() {
  return request({
    url: '/fx_technologist/getFinishedSampleAvgDay',
    method: 'get'
  })
}


export function getUnFinishedSampleAvgDay() {
  return request({
    url: '/fx_technologist/getUnFinishedSampleAvgDay',
    method: 'get'
  })
}

export function DayFinishedSampleOrder() {
  return request({
    url: '/fx_technologist/DayFinishedSampleOrder',
    method: 'get'
  })
}


export function DayNewMould() {
  return request({
    url: '/fx_technologist/DayNewMould',
    method: 'get'
  })
}

export function DayFinishedSample() {
  return request({
    url: '/fx_technologist/DayFinishedSample',
    method: 'get'
  })
}


export function DayFinishedBOM() {
  return request({
    url: '/fx_technologist/DayFinishedBOM',
    method: 'get'
  })
}


export function ALL() {
  return request({
    url: '/fx_technologist/All',
    method: 'get'
  })
}


export function AllDataDataRange(fDateStart,fDateEnd) {
  return request({
    url: '/fx_technologist/AllDataDataRange/'+fDateStart+'/'+fDateEnd,
    method: 'get'
  })
}

export function getBestHunsha() {
  return request({
    url: '/fx_technologist/getBestHunsha',
    method: 'get'
  })
}


export function getBestTijiaoxian() {
  return request({
    url: '/fx_technologist/getBestTijiaoxian',
    method: 'get'
  })
}

export function getBestHuakuang() {
  return request({
    url: '/fx_technologist/getBestHuakuang',
    method: 'get'
  })
}
