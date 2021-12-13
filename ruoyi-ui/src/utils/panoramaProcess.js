import * as THREE from 'three';

export default {
//计算贴面以及判断是否可视范围的各种计算
  drawSlice(index, url,sphere,imgIndex,widthScale,heightScale) {
    const _this=this;
    let loader = new THREE.TextureLoader();
    loader.format = THREE.RGBFormat;
    loader.crossOrigin = '*';
    // 使用全景图片生成纹理
    // const texture = new THREE.TextureLoader().load(url);
    // // 这里可以让纹理之间的过渡更加自然，不会出现明显的棱角
    // texture.minFilter=THREE.LinearFilter;
    // texture.magFilter=THREE.LinearFilter;
    // sphere.material[index] = new THREE.MeshBasicMaterial({
    //     map: texture
    //   });
    //
    // this.updateSliceView(index,sphereIndex);
    // sphere.geometry.elementsNeedUpdate=true;
    loader.load(url, (texture) => { //texture箭头函数写法才能使贴面替换的时候不会出现黑块
      // 这里可以让纹理之间的过渡更加自然，不会出现明显的棱角
      texture.minFilter=THREE.LinearFilter;
      texture.magFilter=THREE.LinearFilter;
      sphere.material[index] = new THREE.MeshBasicMaterial({
        map: texture
      });
      _this.updateSliceView(index,sphere,imgIndex,widthScale,heightScale);
      sphere.geometry.elementsNeedUpdate=true;// 加入这个才能实现实时更新
    });
  },
// 更新三角面uv映射
  updateSliceView(index,sphere,imgIndex,widthScale,heightScale) {
    let sliceIndex = 0;
    const {widthSegments, heightSegments} = this;
    for (let i = 0, l = sphere.geometry.faces.length; i < l; i++) {
      // l是总共的三角面数
      // 每一个三角面对应的图片索引
      if(imgIndex[i] === index) {
        // console.log(index);
        sliceIndex++;
        const uvs = this.getVertexUvs(sliceIndex, widthScale, heightScale);
        if(i >= widthSegments*2*heightSegments - 3*widthSegments || i < widthSegments) {
          sphere.geometry.faces[i].materialIndex = index;
          sphere.geometry.faceVertexUvs[0][i][0].set(...uvs[0].a);
          sphere.geometry.faceVertexUvs[0][i][1].set(...uvs[0].b);
          sphere.geometry.faceVertexUvs[0][i][2].set(...uvs[0].c);
        }else {
          sphere.geometry.faces[i].materialIndex = index;
          sphere.geometry.faces[i+1].materialIndex = index;
          sphere.geometry.faceVertexUvs[0][i][0].set(...uvs[0].a);
          sphere.geometry.faceVertexUvs[0][i][1].set(...uvs[0].b);
          sphere.geometry.faceVertexUvs[0][i][2].set(...uvs[0].c);
          sphere.geometry.faceVertexUvs[0][i+1][0].set(...uvs[1].a);
          sphere.geometry.faceVertexUvs[0][i+1][1].set(...uvs[1].b);
          sphere.geometry.faceVertexUvs[0][i+1][2].set(...uvs[1].c);
          i++;
        }
      }
    }
  },
  /**
   * @description 这个函数用来计算球体每个三角面对应使用哪一张图片作为纹理
   * 全景图被分成 4*8 张图片 也就是4行8列
   * 球体的三角面数量为 横向分割数*2 + (纵向分割数-2)*横向分割数*2
   * 如果球体的纵向分割和横向分割正好是4和8，那么顶部和底部的每个三角面对应一张图片，中间每两个相邻的三角面共用一张图片
   * 球体的纵向分割和横向分割大于4和8，那么必须是4和8的整数倍，这样每个三角面和他左右的三角面和上下的三角面共用一张图片
   * @param {any} i 三角面的索引（第几个三角面）
   * @param {any} widthSegments 球体横向切割数
   * @param {any} heightSegments 球体纵向切割数
   * @param {any} widthScale 球体横向切割数/全景图的横向切割数
   * @param {any} heightScale 球体纵向切割数/全景图的纵向切割数
   * @returns imgIndex 图片索引
   */
  transIndex(i, widthSegments, heightSegments, widthScale, heightScale,hCut) {
    let row, col, imgIndex;
    // 第一行
    if(i < widthSegments) {
      row = 1;
      col = i+1;
    }else if(i < 3*widthSegments) {
      // 第二行
      row = parseInt((i+widthSegments)/(2*widthSegments)) + 1;
      col = parseInt((i - (row-1)*widthSegments)/2) + 1;
    }else if(i < widthSegments+2*widthSegments*(heightSegments-2)) {
      row = parseInt((i-widthSegments)/(2*widthSegments)) + 2;
      col = parseInt((i - (row-2) * 2 * widthSegments -widthSegments )/2) + 1;
    }else {
      // 最后一行
      row = parseInt((i-widthSegments)/(2*widthSegments)) + 2;
      col = parseInt( i - (row-2) * 2*widthSegments -widthSegments ) + 1;
    }
    row = Math.ceil(row/heightScale);
    col = Math.ceil(col/widthScale);
    imgIndex = (col-1) * hCut + row;
    return imgIndex;
  },
  /**
   * @description 这个函数用来计算当前三角面和他下一个三角面的uv映射坐标（两个相邻的三角面拼成一个矩形）
   * 比如说当前全景图是4*8 4行8列，但是球体被分割成8*16
   * 所以某一张分割图要被当前行4个三角面使用上半部分，被下一行的4个三角面使用下半部分(第一行和最后一行除外)
   * 第一行的话就是2个三角面使用上半部分，下一行的4个三角面使用下半部分
   * 最后一行的话就是上一行的4个三角面使用上半部分，当前行的2个三角面使用下半部分
   * 所以第一行和最后一行会有缺失
   * @param {any} index 第几个使用当前图形作为纹理的三角面
   * @param {any} widthScale 球体横向分割/全景图横向切割
   * @param {any} heightScale 球体纵向切割/全景图纵向切割
   * @returns 两个三角面的uv映射坐标
   */
  getVertexUvs(index, widthScale, heightScale) {
    // 两个三角面组成的矩形的四个顶点坐标
    const vectors = [
      [((index-1)%widthScale + 1)/widthScale, 1- (parseInt((index-1)/widthScale)%heightScale)/heightScale],
      [((index-1)%widthScale)/widthScale, 1- (parseInt((index-1)/widthScale)%heightScale)/heightScale],
      [((index-1)%widthScale)/widthScale, 1- (parseInt((index-1)/widthScale)%heightScale + 1)/heightScale],
      [((index-1)%widthScale + 1)/widthScale, 1- (parseInt((index-1)/widthScale)%heightScale + 1)/heightScale]
    ];
    // console.log(vectors[0]);
    // console.log(vectors[1]);
    // console.log(vectors[2]);
    // console.log(vectors[3]);
    return [
      {
        a: vectors[0],
        b: vectors[1],
        c: vectors[3]
      },
      {
        a: vectors[1],
        b: vectors[2],
        c: vectors[3]
      }
    ];
  },
  // 球体半径
  isInSight(row, col, camera, sphereIndex, vCut, hCut) {
    const Radius = 100;
    // 经度 2π 分成8份， 每份是4/π
    // 维度 π 分成4份， 每份也是4/π
    const ltPoint = {
      x: Radius * Math.sin(col * Math.PI / vCut) * Math.cos(row * 2 * Math.PI / hCut),
      y: Radius * Math.cos(col * Math.PI / vCut),
      z: Radius * Math.sin(col * Math.PI / vCut) * Math.sin(row * 2 * Math.PI / hCut)
    };
    const rtPoint = {
      x: Radius * Math.sin(col * Math.PI / vCut) * Math.cos((row + 1) * 2 * Math.PI / hCut),
      y: Radius * Math.cos(col * Math.PI / vCut),
      z: Radius * Math.sin(col * Math.PI / vCut) * Math.sin((row + 1) * 2 * Math.PI / hCut)
    };
    const lbPoint = {
      x: Radius * Math.sin((col + 1) * Math.PI / vCut) * Math.cos(row * 2 * Math.PI / hCut),
      y: Radius * Math.cos((col + 1) * Math.PI / vCut),
      z: Radius * Math.sin((col + 1) * Math.PI / vCut) * Math.sin(row * 2 * Math.PI / hCut)
    };
    const rbPoint = {
      x: Radius * Math.sin((col + 1) * Math.PI / vCut) * Math.cos((row + 1) * 2 * Math.PI / hCut),
      y: Radius * Math.cos((col + 1) * Math.PI / vCut),
      z: Radius * Math.sin((col + 1) * Math.PI / vCut) * Math.sin((row + 1) * 2 * Math.PI / hCut)
    };

    // 创建一个几何图形，四个顶点分别为贴图的四个顶点坐标、
    const geometry = new THREE.Geometry();
    geometry.vertices.push(
      new THREE.Vector3(ltPoint.x, ltPoint.y, ltPoint.z),
      new THREE.Vector3(rtPoint.x, rtPoint.y, rtPoint.z),
      new THREE.Vector3(lbPoint.x, lbPoint.y, lbPoint.z),
      new THREE.Vector3(rbPoint.x, rbPoint.y, rbPoint.z),
    );
    geometry.faces.push(new THREE.Face3(0, 1, 2), new THREE.Face3(1, 2, 3));

    // 然后判断包围球是否与视锥体相交
    const tagMesh = new THREE.Mesh(geometry);
    const off = this.isOffScreen(tagMesh, camera);

    return !off;
  },
  isOffScreen (obj, camera){
    let frustum = new THREE.Frustum(); //Frustum用来确定相机的可视区域
    let cameraViewProjectionMatrix = new THREE.Matrix4();
    cameraViewProjectionMatrix.multiplyMatrices(camera.projectionMatrix, camera.matrixWorldInverse); //获取相机的法线
    frustum.setFromProjectionMatrix(cameraViewProjectionMatrix); //设置frustum沿着相机法线方向
    return !frustum.intersectsObject(obj);
  }
}

