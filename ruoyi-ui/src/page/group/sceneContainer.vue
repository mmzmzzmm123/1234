
<template>
    <!--    style="display:none"-->
    <div id="snece-container">
      <div style="text-align:center">
        <div style="display:inline-block;width: 100px; height: 100px;margin-right: 20px;" v-for="(thumb,index) in thumbs" :key="index">
          <!-- 这里index为什么不报错？ -->
          <el-image
            @click="changePanorama(thumb.index)"
            style="cursor: pointer;"
            :src="thumb.thumb"
            :fit="fits[0]">
          </el-image>
        </div>
      </div>
    </div>
</template>
<script>
import * as THREE from 'three';
import jq from 'jquery';
import { listVisualImage } from "@/api/system/visualImage";
import {getPanorama} from "@/api/system/panorama";
var camera;
var renderer;
var scene;
var imgIndex=[];
export default {
  name: 'showPanoramaInScreen',
  // inject: ["contain"],
  inject: ["contain"],
  components:{
  },
  data() {
    return {
      Img : require("../../../public/icon/blank.png"),
      addIcon: require("../../../public/icon/add.png"),
      helicopter : require("../../../public/icon/helicopter.png"),
      // musicPath : require("../../../public/icon/bgm.mp3"),
      // rotationStatus : false,
      // musicStatus : false,
      // screenStatus: false,
      // sceneChangeStatus:false,
      addSensorStatus:false,
      addChangeStatus:false,
      deleteSensorStatus:false,
      rotation : null,
      music : null,
      rotationY :0,
      ans: 0,//计算当前的场景个数
      panoramaIndex : 0,
      sliceArray:[],
      statuArray:[],
      ansArray :[],
      sphereArray:[],
      sceneArray:[],
      hCut:[],
      vCut:[],
      widthScale:[],
      heightScale:[],
      widthSegments :200,
      heightSegments : 200,
      hotPointObjectsArray:[],
      helicopterObjectsArray:[],
      thumbs:[],
      // {index :0 ,thumb:require("../../../public/thumb/thumb(1).jpg"),v:10,h:10},
      // {index :1,thumb:require("../../../public/thumb/thumb(5).jpg"),v:10,h:10}
      fits: ['fill', 'contain', 'cover', 'none', 'scale-down'],
      maxId:0,
      // input,form没定义会有报错信息
      input:"test",
      form:{},
      thumb:"",
      selectedSceneUrl:{},
      // 查询全景图的参数
      queryParams: {
        pageNum: 1,
        pageSize: 1000,
        originName: null,
        newName: null,
        width: null,
        height: null,
      },
      waitScene:[],
      message:[],
      formDB : [],
      idToIndex:{},
      indexToId:{},
      nowIndex:0
    }
  },
  mounted() {
    // 调用全景图函数
    this.$nextTick(() => {
      this.init();
      this.animate();
    });
  },
  methods: {
    // 全景图配置函数---------------
    test(){
      //   getLink({fileName:"d88ecabf67d64d04aac8543a71878d81.jpg"}).then(res=>{
      //   console.log(res);
      // });
      // const _this=this;
    },
    init() {
      this.showDB();

    },
    async showDB(){
      const id = this.$route.params.panoramaId;
      console.log(id);
      await getPanorama(id).then(response => {
        console.log(response);
        this.formDB=response.data;
      });
      this.message=JSON.parse(this.formDB.component) || [];
      var objects=JSON.parse(this.formDB.component) || [];
      console.log("json文件");
      console.log(objects);
      // 先遍历场景，再遍历传感器和场景变换
      for(var obj of objects) {
        if (obj.type === "scene") {
          this.idToIndex[obj.id] = this.ans;
          this.indexToId[this.ans] = obj.id;
          obj["index"] = this.ans;
          this.ans++;
          console.log(obj);
          this.addSceneByDb(obj);
          this.thumbs.push(obj);

        }
      }
      console.log(this.idToIndex);
    },
    addSceneByDb(obj)
    {
      // 添加场景信息到数组
      // const imagePath=this.addSceneTable.imagePath;
      // const thumbPath=this.addSceneTable.thumbPath;
      const imagePath=obj.originalFileFolder;
      const index= obj.index;
      const imageSub=obj.originalFile.substr(obj.originalFile.length-4,4);
      // console.log(this.addSceneTable.height+"-"+this.addSceneTable.width)
      // console.log(vCut+"-"+hCut);
      // console.log("图片路径"+imageSub);
      this.sliceArray[index]=[];
      this.statuArray[index]=[];
      imgIndex[index]=[];
      this.hCut[index]=obj.h;
      this.vCut[index]=obj.v;
      this.widthScale[index] = this.widthSegments/this.hCut[index];
      this.heightScale[index] = this.heightSegments/this.vCut[index];
      this.ansArray[index]=0;
      this.sceneArray[index]=null;
      this.helicopterObjectsArray[index]=[];
      this.hotPointObjectsArray[index]=[];
      for (let i=0;i<this.vCut[index];i++)
      {
        this.sliceArray[index][i]=[];
        this.statuArray[index][i]=[];
        for(let j=0;j<this.hCut[index];j++)
        {
          this.sliceArray[index][i][j]=null;
          this.statuArray[index][i][j]=0;
        }
      }
      for (let i=0;i<this.vCut[index];i++)
      {
        for(let j=0;j<this.hCut[index];j++)
        {
          // slices[i][j]=this.Imgs[j*vCut+i];
          //因为require它是打包工具所需要的标识，你搞成运行时通过变量去定义的话，它就没办法打包了啊
          //所以不可以写成require("../../public/photo/"+index+".gif"")
          this.sliceArray[index][j][i]=imagePath+i+"_"+j+imageSub;
        }
      }
      //将场景添加到场景切换部分
      // @click="changePanorama('+index+')"
      //初始化数组样式
      this.loadThumb(obj.thumb2,obj.index);
      console.log(obj);
    },
      // controls.update();
      // controls.handleResize();
      // transformControls.update();
    },
    //初始化两个场景的各种数组



    loadSlices(slices,status,ansIndex,sphereIndex) {
      //判断如果全部的碎片图都加载过一次就不再加载
      // vCut=this.thumbs[sphereIndex].v;
      // hCut=this.thumbs[sphereIndex].h;
      // this.widthSegments = widthSegments;
      // this.widthScale = widthSegments/this.hCut[sphereIndex];
      // this.heightSegments = heightSegments;
      // this.heightScale = heightSegments/this.vCut[sphereIndex];
      // console.log(sphereIndex+"loadSlices-"+vCut+hCut);
      if(this.ansArray[ansIndex]===this.hCut[sphereIndex]*this.vCut[sphereIndex]) return;
      // console.log(this.sphere.material);
      const urls = slices;
      const row = urls.length;
      const col = urls[0].length;
      // 渲染
      for(let i = 0; i < row; i++) {
        for(let j = 0; j < col; j++) {
          const index = i * col + j + 1;
          if(status[i][j]===0) {
            const isInSight = this.isInSight(i, j, camera,sphereIndex);
            // console.log(isInSight);
            if(isInSight) {
              // console.log(urls[i][j]);
              this.drawSlice(index, urls[i][j],sphereIndex);
              status[i][j]=1;
              this.ansArray[ansIndex]++;
              //  this.sliceMap[`${i}-${j}`] = 1;
              // this.complate = this.checkComplate();
            }
          }
        }
      }
    },
    loadAllSlices(slices,status,ansIndex,sphereIndex){
      // this.vCut[sphereIndex]=this.thumbs[sphereIndex].v;
      // this.hCut[sphereIndex]=this.thumbs[sphereIndex].h;
      // this.widthSegments = widthSegments;
      // this.widthScale = widthSegments/hCut;
      // this.heightSegments = heightSegments;
      // this.heightScale = heightSegments/vCut;
      // console.log("loadAllSlices-"+this.vCut[sphereIndex]+this.hCut[sphereIndex]);
      if(this.ansArray[ansIndex]===this.hCut[sphereIndex]*this.vCut[sphereIndex]) return;
      // console.log(this.sphere.material);
      const urls = slices;
      const row = urls.length;
      const col = urls[0].length;
      // 渲染
      for(let i = 0; i < row; i++) {
        for(let j = 0; j < col; j++) {
          const index = i * col + j + 1;
          if(status[i][j]===0) {
            // console.log(urls[i][j]);
            this.drawSlice(index, urls[i][j],sphereIndex);
            status[i][j]=1;
            this.ansArray[ansIndex]++;
            //  this.sliceMap[`${i}-${j}`] = 1;
            // this.complate = this.checkComplate();
          }
        }
      }
    },
    changePanorama(index)
    {
      if(this.ansArray[index]==this.hCut[index]*this.vCut[index])
      {
        scene=this.sceneArray[index];
        this.panoramaIndex=index;
        return;
      }
      this.loadSlices(this.sliceArray[index],this.statuArray[index],index,index);
      // this.loadAllSlices(this.sliceArray[index],this.statuArray[index],index,index);
      this.panoramaIndex=index;
      scene=this.sceneArray[index];
      // for (let i=0;i<vCut;i++)
      // {
      //   for(let j=0;j<hCut;j++)
      //   {
      //     this.statuArray[index][i][j]=0;
      //   }
      // }
      // if(index!=0)  this.loadThumb(this.Img,index);
      // // this.creatImgArray(1);
      // this.loadSlices(this.sliceArray[index],this.statuArray[index],this.ansArray[index],index);
      // this.loadAllSlices(this.sliceArray[index],this.statuArray[index],this.ansArray[index],index);
    },
    //添加场景的三个函数
    getPendingScene()
    {
      this.dialogAddSceneVisible = true
      listVisualImage(this.queryParams).then(response => {
        console.log(response.rows);
        // console.log(response.rows[0]);
        this.pending=response.rows;
        console.log(this.pending)
      });
    },
    //计算贴面以及判断是否可视范围的各种计算
    drawSlice(index, url,sphereIndex) {
      const _this=this;
      const sphere=this.sphereArray[sphereIndex];
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
        _this.updateSliceView(index,sphereIndex);
        sphere.geometry.elementsNeedUpdate=true;// 加入这个才能实现实时更新
      });
    },
    // 更新三角面uv映射
    updateSliceView(index,sphereIndex) {
      const sphere=this.sphereArray[sphereIndex];
      let sliceIndex = 0;
      const {widthSegments, heightSegments} = this;
      const widthScale=this.widthScale[sphereIndex];
      const heightScale=this.heightScale[sphereIndex];
      for (let i = 0, l = sphere.geometry.faces.length; i < l; i++) {
        // l是总共的三角面数
        // 每一个三角面对应的图片索引
        if(imgIndex[sphereIndex][i] === index) {
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
    transIndex(i, widthSegments, heightSegments, widthScale, heightScale,sphereIndex) {
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
      imgIndex = (col-1) * this.hCut[sphereIndex] + row;
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
    /**
     * @description 这个函数用来判断一张切图是不是在当前视线中
     * 球体顶点计算公式 x: r*sinθ*cosφ y: r*cosθ z: r*sinθ*sinφ θ纬度 φ经度
     * 行 => 纬度  列 => 经度
     * 全景图一共4行8列 那么某一张图片对应到球面上的顶点坐标就可以求出来
     * 然后根据这4个顶点创建一个几何图形，判断这个几何图形的包围球是否与相机的视锥体相交
     * @param {any} row 当前切图的行
     * @param {any} col 当前切图的列
     * @param {any} camera 判断相交的相机
     * @returns 是否在当前视线
     */
    isInSight(row, col, camera,sphereIndex) {
      // 球体半径
      const vCut=this.vCut[sphereIndex];
      const hCut=this.hCut[sphereIndex];
      const Radius = 100;
      // 经度 2π 分成8份， 每份是4/π
      // 维度 π 分成4份， 每份也是4/π
      const ltPoint = {
        x: Radius*Math.sin(col * Math.PI / vCut) * Math.cos(row * 2 *  Math.PI / hCut),
        y: Radius*Math.cos(col * Math.PI / vCut),
        z: Radius*Math.sin(col * Math.PI / vCut) * Math.sin(row * 2 *  Math.PI / hCut)
      };
      const rtPoint = {
        x: Radius*Math.sin(col * Math.PI / vCut) * Math.cos((row+1) * 2 *  Math.PI / hCut),
        y: Radius*Math.cos(col * Math.PI / vCut),
        z: Radius*Math.sin(col * Math.PI / vCut) * Math.sin((row+1) * 2 *  Math.PI / hCut)
      };
      const lbPoint = {
        x: Radius*Math.sin((col+1) * Math.PI / vCut) * Math.cos(row * 2 *  Math.PI / hCut),
        y: Radius*Math.cos((col+1) * Math.PI / vCut),
        z: Radius*Math.sin((col+1) * Math.PI / vCut) * Math.sin(row * 2 *  Math.PI / hCut)
      };
      const rbPoint = {
        x: Radius*Math.sin((col+1) * Math.PI / vCut) * Math.cos((row+1) * 2 *  Math.PI / hCut),
        y: Radius*Math.cos((col+1) * Math.PI / vCut),
        z: Radius*Math.sin((col+1) * Math.PI / vCut) * Math.sin((row+1) * 2 *  Math.PI / hCut)
      };

      // 创建一个几何图形，四个顶点分别为贴图的四个顶点坐标、
      const geometry = new THREE.Geometry();
      geometry.vertices.push(
        new THREE.Vector3( ltPoint.x, ltPoint.y, ltPoint.z ),
        new THREE.Vector3( rtPoint.x, rtPoint.y, rtPoint.z ),
        new THREE.Vector3( lbPoint.x, lbPoint.y, lbPoint.z ),
        new THREE.Vector3( rbPoint.x, rbPoint.y, rbPoint.z ),
      );
      geometry.faces.push( new THREE.Face3( 0, 1, 2 ), new THREE.Face3( 1, 2, 3 ) );

      // 然后判断包围球是否与视锥体相交
      const tagMesh = new THREE.Mesh(geometry);
      const off = this.isOffScreen(tagMesh, camera);

      return !off;
    },
    /**
     * @description 判断一个物体是否与相机视锥体相交
     * @param {any} obj 要判断的物体
     * @param {any} camera 相机
     * @returns boolean
     */
    isOffScreen (obj, camera){
      let frustum = new THREE.Frustum(); //Frustum用来确定相机的可视区域
      let cameraViewProjectionMatrix = new THREE.Matrix4();
      cameraViewProjectionMatrix.multiplyMatrices(camera.projectionMatrix, camera.matrixWorldInverse); //获取相机的法线
      frustum.setFromProjectionMatrix(cameraViewProjectionMatrix); //设置frustum沿着相机法线方向
      return !frustum.intersectsObject(obj);
    },

  }
}
</script>

<style scoped>

#snece-container{
  position: absolute;
  bottom: 4rem;
  border-radius: 0px;
  border: 15px solid rgba(39, 39, 39, 0.25);
  background: rgba(39, 39, 39, 0.25);
  box-shadow: 0 3px 14px rgb(128 128 128 / 50%);
  width: 100%;
  height:130px;
  background-repeat: repeat-x;
}
#rightSideMenu>div {
  margin-top: 0.5rem;
  position: relative;
}
img {vertical-align: middle;
  border-style: none;}




</style>
