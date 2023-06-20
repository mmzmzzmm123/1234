import request from '@/utils/request'

// 查询某门课程试卷结构
export function construct(query){
  return request({
    url:'/stu/exam/construct',
    method:'get',
    params: query
  })
}

// 添加某门课程试卷结构
export function addConstruct(query){
  return request({
    url:'/stu/exam/addConstruct',
    method:'post',
    params: query
  })
}

// 修改某门课程试卷结构
export function updateConstruct(query){
  return request({
    url:'/stu/exam/updateConstruct',
    method:'post',
    params: query
  })
}
