import axios from 'axios'
import { getToken } from '@/utils/auth'

const mimeMap = {
    xlsx: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
    zip: 'application/zip'
}

const baseUrl = process.env.VUE_APP_BASE_API
export function downLoadZip(str, filename) {
    var url = baseUrl + str
    axios({
        method: 'get',
        url: url,
        responseType: 'blob',
        headers: { 'Authorization': 'Bearer ' + getToken() }
    }).then(res => {
        resolveBlob(res, mimeMap.zip)
    })
}
export function downLoadVideoUrl(str) {
  var url = baseUrl + str
  axios({
      method: 'get',
      url: url,
      responseType: 'arraybuffer',
      headers: { 'Authorization': 'Bearer ' + getToken() },
  }).then(res => {
      // console.log(res);
      res.send();
  })
}

export function downLoadUrl(str, item) {
    var url = baseUrl + str
    axios({
        method: 'get',
        url: url,
        responseType: 'blob',
        headers: { 'Authorization': 'Bearer ' + getToken() },
    }).then(res => {
        // console.log(res);
        // downloadFileFun(res.data, item)
        res.send();
    })
}

export function downloadFileFun(data, item) {
    if (!data) {
        return
    }
    let url = window.URL.createObjectURL(new Blob([data]))
    let link = document.createElement('a')
    link.style.display = 'none'
    link.href = url
    link.setAttribute('download', item.name)
    document.body.appendChild(link)
    link.click()
}

/**
 * 解析blob响应内容并下载
 * @param {*} res blob响应内容
 * @param {String} mimeType MIME类型
 */
export function resolveBlob(res, mimeType) {
    const aLink = document.createElement('a')
    var blob = new Blob([res.data], { type: mimeType })
        // //从response的headers中获取filename, 后端response.setHeader("Content-disposition", "attachment; filename=xxxx.docx") 设置的文件名;
    var patt = new RegExp('filename=([^;]+\\.[^\\.;]+);*')
    var contentDisposition = decodeURI(res.headers['content-disposition'])
    var result = patt.exec(contentDisposition)
    var fileName = result[1]
    fileName = fileName.replace(/\"/g, '')
    aLink.href = URL.createObjectURL(blob)
    aLink.setAttribute('download', fileName) // 设置下载文件名称
    document.body.appendChild(aLink)
    aLink.click()
    document.body.appendChild(aLink)
}