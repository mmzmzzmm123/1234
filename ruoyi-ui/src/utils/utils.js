import SparkMD5  from 'spark-md5'
import service from './request'
// 分片上传
/*
* 分片上传函数 支持多个文件
* @param options
* options.file 表示源文件
* options.pieceSize 表示需要分片的大小 默认是5m
* options.chunkUrl 分片上传的后端地址
* options.fileUrl 整个文件的上传地址
* progress 进度回调
* success 成功回调
* error 失败回调
*
*/
export const uploadByPieces = ({metadataId,files, chunkUrl, fileUrl, pieceSize, progress, success, error}) => {
  if (!files || !files.length) return
  // 上传过程中用到的变量
  let fileList = [] // 总文件列表
  let progressNum = 1 // 进度
  let successAllCount = 0 // 上传成功的片数
  // let currentAllChunk = 0 // 当前上传的片数索引
  let AllChunk = 0 // 所有文件的chunk数之和
  let AllFileSize = 0 // 所有文件size
  // 获取md5
  const readFileMD5 = (files) => {
    // 读取每个文件的md5
    files.map((file, index) => {
      let fileRederInstance = new FileReader()
      fileRederInstance.readAsBinaryString(file)
      fileRederInstance.addEventListener('load', e => {
        let fileBlob = e.target.result
        let spark = new SparkMD5()
        spark.appendBinary(fileBlob)
        let fileMD5 = spark.end()
        if (!fileList.some((arr) => arr.md5 === fileMD5)) {
          fileList.push({md5: fileMD5, name: file.name, file})
          AllFileSize = AllFileSize + file.size
        }
        if (index === files.length - 1) readChunkMD5(fileList)
      }, false)
    })
  }
  const getChunkInfo = (file, currentChunk, chunkSize) => {
    let start = currentChunk * chunkSize
    let end = Math.min(file.size, start + chunkSize)
    let chunk = file.slice(start, end)
    return { start, end, chunk }
  }
  // 针对每个文件进行chunk处理
  const readChunkMD5 = (fileList) => {
    fileList.map((currentFile, fileIndex) => {
      const chunkSize = pieceSize * 1024 * 1024 // 5MB一片
      const chunkCount = Math.ceil(currentFile.file.size / chunkSize) // 总片数
      AllChunk = AllChunk + chunkCount // 计算全局chunk数
      // 针对单个文件进行chunk上传
      for (let i = 0; i < chunkCount; i++) {
        const { chunk } = getChunkInfo(currentFile.file, i, chunkSize)
        let chunkFR = new FileReader()
        chunkFR.readAsBinaryString(chunk)
        chunkFR.addEventListener('load', e => {
          let chunkBlob = e.target.result
          let spark = new SparkMD5()
          spark.appendBinary(chunkBlob)
          let chunkMD5 = spark.end()
          uploadChunk(currentFile, {chunkMD5, chunk, currentChunk:i, chunkCount}, fileIndex)
        }, false)
      }
    })
  }

  // 更新进度
  const progressFun = () => {
    progressNum = Math.ceil(successAllCount / AllChunk * 100)
    progress(progressNum)
  }
  // 对分片已经处理完毕的文件进行上传
  const uploadFile = (currentFile) => {
    let makeFileForm = new FormData()
    makeFileForm.append('md5', currentFile.md5)
    makeFileForm.append('metadataId', metadataId)
    makeFileForm.append('file_name', currentFile.name)
    service({ // 合并文件
      method: 'post',
      url: fileUrl,
      data: makeFileForm
    }).then(res => {
      //progressFun()
      //res.file_name = currentFile.name
      success && success(res)
      successAllCount++
    }).catch(e => {
      error && error(e)
    })
  }

  const uploadChunk = (currentFile, chunkInfo, fileIndex) => {
    let fileForm = new FormData()
    fileForm.append('file_name', currentFile.name)
    fileForm.append('md5', currentFile.md5)
    fileForm.append('data', chunkInfo.chunk)
    fileForm.append('chunks', chunkInfo.chunkCount)
    fileForm.append('chunk_index', chunkInfo.currentChunk)
    fileForm.append('chunk_md5', chunkInfo.chunkMD5)
    service({
      method: 'post',
      url: chunkUrl,
      data: fileForm,
      async: false,
      timeout: 150000
    }).then(res => {
      //progressFun()
      if (chunkInfo.currentChunk < chunkInfo.chunkCount - 1) {
        successAllCount++
      } else {
        // 当总数大于等于分片个数的时候
        if (chunkInfo.currentChunk >= chunkInfo.chunkCount-1) {
          uploadFile(currentFile, fileIndex)
        }
      }
    }).catch((e) => {
      error && error(e)
    })
  }
  readFileMD5(files) // 开始执行代码
}
