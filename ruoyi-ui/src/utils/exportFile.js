


//导出文件流的文件 blob
export function exportFileHTML(response,fileName) {
  const blob = new Blob([res], { type: 'text/html' }) // 构造一个blob对象来处理数据
  const elink = document.createElement('a') // 创建a标签
  elink.download = fileName // a标签添加属性
  elink.style.display = 'none'
  elink.href = URL.createObjectURL(blob)
  document.body.appendChild(elink)
  elink.click() // 执行下载
  URL.revokeObjectURL(elink.href) // 释放URL 对象
  document.body.removeChild(elink) // 释放标签
}
