/*
 * OSS文件打包批量下载
 */
import JSZip from "jszip";
import FileSaver from "file-saver";
import Vue from 'vue'

const imageTypeArray = ['jpg', 'jpeg', 'png'];

/**
 * 打包下载
 * @param fileUrlArray OSS访问URL数组
 * @param fileNameArray 文件名称数组
 * @param zipName 打包文件名
 */
export function downFileByZip(fileUrlArray, fileNameArray, zipName) {
  try {
    let zip = new JSZip();
    for (let i = 0; i < fileUrlArray.length; i++) {
      let fileType = getFileType(fileNameArray[i]);
      if(imageTypeArray.indexOf(fileType) != -1){
        getBase64Image(fileUrlArray[i], fileType, true).then(res => {
          zip.file(fileNameArray[i], res, { base64: true });
          if(i == fileUrlArray.length -1){
            downZip(zip, zipName);
          }
        });
      }else{
        getFile(fileUrlArray[i]).then(arraybuffer => {
          zip.file(fileNameArray[i], arraybuffer, { binary: true });
          if(i == fileUrlArray.length -1){
            downZip(zip, zipName);
          }
        });
      }
    }
  } catch (err) {
    console.log("err", err);
  }
}

//根据访问链接获取文件类型
export function getFileType(fileUrl){
   let fileType = "";
   let index = fileUrl.lastIndexOf(".");
   if(index == -1){
      return fileType;
   }
   fileType = fileUrl.substring(index+1);
   if(fileType.indexOf("?") != -1){
     fileType = fileType.substring(0,fileType.indexOf("?"));
   }
   return fileType;
}

export function downZip(zip, zipName) {
  zip.generateAsync({
      type: "blob"
    }).then(content => {
      FileSaver.saveAs(content, zipName+".zip");
    });
}

//****传入图片链接，返回base64数据
export function  getBase64Image(url, type, flag) {
  return new Promise((resolve, reject) => {
    var base64 = "";
    var img = new Image();
    img.setAttribute("crossOrigin", "Anonymous");
    img.onload = () => {
      base64 = image2Base64(img, type);
      resolve(flag ? (base64.split(",")[1]) : base64);
    };
    img.onerror = () => reject("加载失败");
    // 这里可能会有跨域失败的问题，解决方案同上，url + 随机数
    img.src = url + "&t=" + Math.random();
});
}

export function image2Base64(img, type) {
  var canvas = document.createElement("canvas");
  canvas.width = img.width;
  canvas.height = img.height;
  var ctx = canvas.getContext("2d");
  ctx.drawImage(img, 0, 0, img.width, img.height);
  var dataURL = canvas.toDataURL("image/"+type);
  return dataURL;
}

//****传入文件链接，返回arraybuffer数据
export function getFile(url) {
  return new Promise((resolve, reject) => {
    // 这里的$http是Vue里的axios
    Vue.http({
      method: "get",
      url,
      responseType: "arraybuffer"
    }).then(data => {
      resolve(data.data);
    }).catch(error => {
      reject("文件加载失败：" + error);
    });
  });
}

/**
 * 下载单个文件
 * @param fileUrl
 * @param fileName
 */
export function downSigleFile(fileUrl, fileName){
  let fileType = getFileType(fileName);
  let blobData = null;
  if(imageTypeArray.indexOf(fileType) != -1){
      getBase64Image(fileUrl, fileType, false).then(res => {
        blobData = base64ToBlob(res);
        downFileByBlob(blobData,fileName);
      });
  }else{
    getFile(fileUrl).then(arraybuffer => {
      blobData = new Blob([arraybuffer]);
      downFileByBlob(blobData,fileName);
    });
  }

}

/**
 * blob对象下载
 */
export function downFileByBlob(blobData, fileName){
  let url = window.URL.createObjectURL(blobData);
  let link = document.createElement('a')
  link.style.display = 'none'
  link.href = url;
  link.setAttribute('download', fileName);
  document.body.appendChild(link);
  link.click();
}

/**
 * base64转blob对象
  */
export function base64ToBlob(base64) {
  console.log(base64);
  var parts = base64.split(";base64,");
  var contentType = parts[0].split(":")[1];
  var raw = window.atob(parts[1]);
  var rawLength = raw.length;
  var uInt8Array = new Uint8Array(rawLength);
  for (var i = 0; i < rawLength; i++) {
    uInt8Array[i] = raw.charCodeAt(i);
  }
  return new Blob([uInt8Array], { type: contentType });
}
