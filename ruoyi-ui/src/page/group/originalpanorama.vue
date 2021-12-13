<template>
<div id="container"></div>
</template>
<script>
  import * as THREE from 'three';
  import $ from 'jquery';
  // import { test, test1, test2 ,test3 ,test4 } from "@/api/user";
  var camera;
  var renderer;
  var scene;
  const hCut=10;//水平划分次数
  const vCut=10;//垂直划分次数
  const widthSegments = 100;//水平分段数（沿着经线分段）
  const heightSegments = 100;//垂直分段数（沿着纬线分段）
  var imgIndex=[];


  export default {
    name: 'showPanorama',
    inject: ["contain"],
    components:{
    },
    data() {
      return {
        Img : require("../../../public/icon/test.jpg"),
        hotPoint : require("../../../public/icon/hotpoint.png"),
        helicopter : require("../../../public/icon/helicopter.png"),
        rotationStatus : false,
        musicStatus : false,
        screenStatus: false,
        sceneChangeStatus:false,
        addSensorStatus:false,
        deleteSensorStatus:false,
        rotation : null,
        music : null,
        rotationY :0,
        musicPath : require("../../../public/icon/bgm.mp3"),
        panoramaIndex : 0,
        sliceArray:[],
        statuArray:[],
        ansArray :[],
        sphereArray:[],
        sceneArray:[],
        hotPointObjectsArray:[],
        helicopterObjectsArray:[],
        dialogTableVisible: false,
        dialogFormVisible: false,
        percentage: 10,
        colors: [
          {color: '#f56c6c', percentage: 20},
          {color: '#e6a23c', percentage: 40},
          {color: '#5cb87a', percentage: 60},
          {color: '#1989fa', percentage: 80},
          {color: '#6f7ad3', percentage: 100}
        ],
        form: {
          sensorType: '',
          sensorMes: ''
        },
        showform:{
          sensorType: '测试',
          sensorMes: '测试'
        },
        formLabelWidth: '120px',
        maxId:0,
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
      init() {
        //取消右键点击事件
        document.oncontextmenu = function (event) {
          event.preventDefault();
        }
        var container = document.getElementById('container');
        // 创建渲染器
        renderer = new THREE.WebGLRenderer();
        // renderer.setPixelRatio(window.devicePixelRatio);
        // // 设置画布的宽高
        renderer.setSize(this.contain.config.width, this.contain.config.height);
        // // 判断容器中子元素的长度
        let childs = container.childNodes;
        if (container.childNodes.length > 0) {
          container.removeChild(childs[0]);
          container.appendChild(renderer.domElement);
        } else {
          container.appendChild(renderer.domElement);
        }
        //   container.appendChild(renderer.domElement);
        // 创建主场景
        scene = new THREE.Scene();
        console.log(window.innerWidth);
        // 创建相机
        // camera = new THREE.PerspectiveCamera(90, window.innerWidth / window.innerHeight, 0.1, 100);
        camera = new THREE.PerspectiveCamera(90, this.contain.config.width / this.contain.config.height, 0.1, 100);
        // camera = new THREE.PerspectiveCamera(90, 836/470, 0.1, 100);
        // camera.position.set(0, 0, 0);
        // camera.lookAt(100,0,0)
        // camera.updateProjectionMatrix();
        // camera.rotateOnAxis( 1, Math.PI)
        // this.creatImgArray(1);
        this.creatImgArray();
        this.loadThumb(this.Img,0);
        scene=this.sceneArray[0];
        this.loadSlices(this.sliceArray[0],this.statuArray[0],0,0);
        this.loadAllSlices(this.sliceArray[0],this.statuArray[0],0,0);
        scene=this.sceneArray[0];
        this.loadThumb(this.Img,1);
        this.loadSlices(this.sliceArray[1],this.statuArray[1],1,1);
        this.loadAllSlices(this.sliceArray[1],this.statuArray[1],1,1);
        // const texture1 = new THREE.TextureLoader().load(this.Img);
        // const geometry1 = new THREE.SphereGeometry( 10, 100, 100 ),
        //       materials1 = [new THREE.MeshBasicMaterial({map: texture1})];
        // //使贴图反转,正常贴图从物体外面看正常，现在反转后在物体里看贴图真唱
        // geometry1.scale(-1,1,1);
        // const sphere1 = new THREE.Mesh( geometry1, materials1 );
        // scene.add(sphere1);
        // this.addSceneChange(0.013116787999754971,-0.18231706437628967,0.1378297619039064,0,1);
        // this.addSceneChange(0.17034009935591,-0.10829839635524996,0.05270989913184244,0,1);
        // this.addSceneChange(0.21578786899258415,-0.13091528735648722,-0.09050630485317226,0,1);
        // this.addSceneChange(-0.1591935714488157,-0.18811305291274494,0.13739973946592704,0,1);
        // this.addSceneChange(-0.048636218952825355,-0.15179340246416667,-0.12639079056109787,1,0);
        // this.addSceneChange(-0.14400823928836437,-0.13452716136265883,0.04245067127477214,1,0);
        // this.addSceneChange(0.2106611107946028,-0.04024874084778395,-0.00949439526434595,1,0);
        this.addMouseEvent();
        // this.showAllSensor();
        // this.hidePanorama();
      },
      onWindowResize() {
        // 窗口缩放的时候,保证场景也跟着一起缩放
        camera.aspect = window.innerWidth / window.innerHeight;
        camera.updateProjectionMatrix();
        renderer.setSize(window.innerWidth, window.innerHeight);
      },
      animate() {
        renderer.render(scene, camera);
        requestAnimationFrame(this.animate);
      },
       //创建图片数组
      creatImgArray(){
        for(let m=0;m<=1;m++)
        {
          this.sliceArray[m]=[];
          this.statuArray[m]=[];
          this.ansArray[m]=0;
          this.sceneArray[m]=null;
          this.helicopterObjectsArray[m]=[];
          this.hotPointObjectsArray[m]=[];
          for (let i=0;i<vCut;i++)
          {
            this.sliceArray[m][i]=[];
            this.statuArray[m][i]=[];
            for(let j=0;j<hCut;j++)
            {
              this.sliceArray[m][i][j]=null;
              this.statuArray[m][i][j]=0;
            }
          }
        }
        for(let m=0;m<=1;m++)
        {
          for (let i=0;i<vCut;i++)
          {
            for(let j=0;j<hCut;j++)
            {
              // slices[i][j]=this.Imgs[j*vCut+i];
              //因为require它是打包工具所需要的标识，你搞成运行时通过变量去定义的话，它就没办法打包了啊
              //所以不可以写成require("../../public/photo/"+index+".gif"")
              this.sliceArray[m][i][j]=require("../../../public/panorama/"+(j*vCut+i+1+m*100)+".gif");
            }
          }
        }

      },
      //添加鼠标事件
      addMouseEvent(){
        var container = document.getElementById('container');
        const _this = this;
        // window.addEventListener('resize', this.onWindowResize, false);
        var bMouseDown = false;
        var x = -1;
        var y = -1;
        // 添加鼠标事件
        container.onmousedown = function (event) {
          event.preventDefault();//取消默认事件
          x = event.clientX;
          y = event.clientY;
          bMouseDown = true;
        }
        container.onmouseup = function (event) {
          event.preventDefault();
          bMouseDown = false;
        }
        container.onmousemove = function (event) {
        event.preventDefault();//取消默认事件
          x = event.clientX;
          y = event.clientY;
          bMouseDown = true;
        }
        container.onmouseup = function (event) {
          event.preventDefault();
          bMouseDown = false;
        }
        container.onmousemove = function (event) {
        event.preventDefault();
        if(!bMouseDown) return;
        camera.rotateOnWorldAxis(new THREE.Vector3(0,1,0),0.005 *(event.clientX - x));
        _this.rotationY=(_this.rotationY+0.005 *(event.clientX - x));
        if(_this.rotationY>0) _this.rotationY=_this.rotationY%(Math.PI*2);
        else _this.rotationY=-((-_this.rotationY)%(Math.PI*2));
        // camera.rotateOnWorldAxis(new THREE.Vector3(1,0,0).localToWorld(),0.005 *(event.clientY - y));
        //vector3不是一个3D对象
        camera.rotateX(0.005 *(event.clientY - y));
        // camera.rotateOnWorldAxis(new THREE.Vector3(0,1,0),0.005 *(event.clientX - x));
        // camera.rotateOnWorldAxis(new THREE.Vector3(1,0,0),0.005 *(event.clientX - x));
        // camera.rotateOnWorldAxis(new THREE.Vector3(1,0,0),0.005 *(event.clientY - y));
        if((_this.rotationY>=0&&(_this.rotationY < Math.PI / 2 || _this.rotationY > Math.PI * 3/ 2 ))||(_this.rotationY<0&&(_this.rotationY > -Math.PI / 2 || _this.rotationY < -Math.PI * 3/ 2 )))
        {
          if(camera.rotation.x > Math.PI / 2 || camera.rotation.x < -Math.PI / 2 )
          {
            camera.rotateX(-0.005 *(event.clientY - y));
          }
        }
        else
        {
         if(!((camera.rotation.x > Math.PI / 2 && camera.rotation.x < Math.PI)||(camera.rotation.x < -Math.PI / 2 && camera.rotation.x > -Math.PI)))
         {
            camera.rotateX(-0.005 *(event.clientY - y));
         }
        }
        x = event.clientX;
        y = event.clientY;
        }
        container.onmousewheel = function (event) {
          event.preventDefault();
          if (event.wheelDelta != 0) {
            camera.fov += event.wheelDelta > 0 ? -1 : 1;
            if (camera.fov > 90) {
              camera.fov = 90;
            }
            else if (camera.fov < 10) {
              camera.fov = 10;
            }
            camera.updateProjectionMatrix();
          }}
        // container.onclick = function (event){
        //   event.preventDefault();
        //   var raycaster=new THREE.Raycaster();
        //   var mouse = new THREE.Vector2();
        //   mouse.x = ( event.clientX / window.innerWidth ) * 2 - 1;
        //   mouse.y = - ( event.clientY / window.innerHeight ) * 2 + 1;
        //   raycaster.setFromCamera(mouse,camera);
        //   console.log(_this.panoramaIndex);
        //   var intersects1 =raycaster.intersectObjects(_this.hotPointObjectsArray[_this.panoramaIndex]);
        //   if(intersects1.length>0)
        //   {
        //     test2(intersects1[0].object.details.id).then(res => {
        //       console.log(res);
        //       _this.showform.sensorType=res.sensorName;
        //       _this.showform.sensorMes="描述信息:"+res.sensorDes;
        //       _this.dialogTableVisible=true;
        //     });
        //   }
        //   var intersects2 =raycaster.intersectObjects(_this.helicopterObjectsArray[_this.panoramaIndex]);
        //   if(intersects2.length>0)
        //   {
        //     _this.changePanorama(intersects2[0].object.details.changeIndex);
        //   }
        // }
        // container.oncontextmenu = function (event){
        //   event.preventDefault();
        //   var mouse = new THREE.Vector2();
        //   mouse.x = ( event.clientX / window.innerWidth ) * 2 - 1;
        //   mouse.y = - ( event.clientY / window.innerHeight ) * 2 + 1;
        //   let vector = new THREE.Vector3(mouse.x, mouse.y,0);
        //     //从屏幕向量转为3d空间向量
        //   vector.unproject(camera);
        //   console.log(vector.x+"|"+vector.y+"|"+vector.z+"|"+_this.panoramaIndex);
        //   if(_this.addSensorStatus)
        //   {
        //     _this.addSensorStatus=false;
        //     let vector = new THREE.Vector3(mouse.x, mouse.y,0);
        //     //从屏幕向量转为3d空间向量
        //     console.log(vector);
        //     vector.unproject(camera);
        //     // id,x,y,z,sceneId,sensorDes
        //     _this.addSensorOnDb(_this.panoramaIndex,vector.x,vector.y,vector.z,_this.form.sensorType,_this.form.sensorMes);
        //     _this.maxId=_this.maxId+1;
        //     _this.addSensor(_this.maxId,_this.panoramaIndex,vector.x,vector.y,vector.z,_this.form.sensorType,_this.form.sensorMes);
        //     test1({}).then(res => {
        //     for(var sensor of res){
        //     console.log(sensor);
        //     }
        //     })
        //     _this.form.sensorType=null;
        //     _this.form.sensorMes=null;
        //   }
        //   else if(_this.deleteSensorStatus)
        //   {
        //     _this.deleteSensorStatus=!_this.deleteSensorStatus;
        //     var raycaster=new THREE.Raycaster();
        //     raycaster.setFromCamera(mouse,camera);
        //     var intersects =raycaster.intersectObjects(_this.hotPointObjectsArray[_this.panoramaIndex]);
        //     if(intersects.length>0)
        //     {
        //         test3(intersects[0].object.details.id).then(res => {
        //       });
        //     }
        //     scene.remove(intersects[0].object);
        //   }
        // }
      },
      //加载缩略图
      loadThumb(url,sphereIndex) {
        //创建分场景；
        this.sceneArray[sphereIndex] = new THREE.Scene();
        const texture = new THREE.TextureLoader().load(url);
        const geometry = new THREE.SphereGeometry( 100, widthSegments, heightSegments ),
              materials = [new THREE.MeshBasicMaterial({map: texture})];
        //使贴图反转,正常贴图从物体外面看正常，现在反转后在物体里看贴图真唱
        geometry.scale(1,1,-1);
        const sphere = new THREE.Mesh( geometry, materials );
        this.widthSegments = widthSegments;
        this.widthScale = widthSegments/vCut;
        this.heightSegments = heightSegments;
        this.heightScale = heightSegments/hCut ;
        this.sphereArray[sphereIndex]=sphere;
        for (let i = 0, l = sphere.geometry.faces.length; i < l; i++) {
          // l是总共的三角面数
          // 每一个三角面对应的图片索引
          imgIndex[i] = this.transIndex(i, widthSegments, heightSegments, this.widthScale, this.heightScale);
        }
        this.sceneArray[sphereIndex].add(sphere);

      },
      //加载清晰图
      loadSlices(slices,status,ansIndex,sphereIndex) {
        //判断如果全部的碎片图都加载过一次就不再加载
        if(this.ansArray[ansIndex]===hCut*vCut) return;
          // console.log(this.sphere.material);
        const urls = slices;
        const row = urls.length;
        const col = urls[0].length;
        // 渲染
        for(let i = 0; i < row; i++) {
          for(let j = 0; j < col; j++) {
            const index = i * col + j + 1;
                if(status[i][j]===0) {
                const isInSight = this.isInSight(i, j, camera);
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
        if(this.ansArray[ansIndex]===hCut*vCut) return;
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
      // glpainter.js
      // 设置材料数组
      drawSlice(index, url,sphereIndex) {
        const sphere=this.sphereArray[sphereIndex];
        let loader = new THREE.TextureLoader();
        // loader.format = RGBFormat;
        loader.crossOrigin = '*';
        // 使用全景图片生成纹理
        const texture = new THREE.TextureLoader().load(url);
        // 这里可以让纹理之间的过渡更加自然，不会出现明显的棱角
        texture.minFilter=THREE.LinearFilter;
        texture.magFilter=THREE.LinearFilter;
        sphere.material[index] = new THREE.MeshBasicMaterial({
            map: texture
          });
        sphere.geometry.elementsNeedUpdate=true;//加入这个才能实现实时更新
        this.updateSliceView(index,sphereIndex);
      },
      // 更新三角面uv映射
      updateSliceView(index,sphereIndex) {
        const sphere=this.sphereArray[sphereIndex];
        let sliceIndex = 0;
        const {widthSegments, heightSegments, widthScale, heightScale} = this;
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
      transIndex(i, widthSegments, heightSegments, widthScale, heightScale) {
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
      isInSight(row, col, camera) {
        // 球体半径
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
            frustum.setFromMatrix(cameraViewProjectionMatrix); //设置frustum沿着相机法线方向
            return !frustum.intersectsObject(obj);
      },
      addSensor(id,sceneId,x,y,z,sensorName,sensorDes)
      {
        let pointTexture = new THREE.TextureLoader().load(this.hotPoint);
        let spriteMaterial = new THREE.SpriteMaterial({map:pointTexture});
        let sprite = new THREE.Sprite(spriteMaterial);
        sprite.scale.set(0.015,0.015,1);
        sprite.position.set(x,y,z);
        this.sceneArray[sceneId].add(sprite);
        sprite.details={id:id,description:sensorDes,sensorType:sensorName};
        this.hotPointObjectsArray[sceneId].push(sprite);
      },
      addSceneChange(x,y,z,currentIndex,changeIndex)
      {
        let pointTexture = new THREE.TextureLoader().load(this.helicopter);
        let spriteMaterial = new THREE.SpriteMaterial({map:pointTexture});
        let sprite = new THREE.Sprite(spriteMaterial);
        sprite.scale.set(0.025,0.025,1);
        sprite.position.set(x,y,z);
        this.sceneArray[currentIndex].add(sprite);
        sprite.details={changeIndex:changeIndex};
        this.helicopterObjectsArray[currentIndex].push(sprite);
      },
      // 重置场景的时候将原来的cssObject移除
      // resetScene() {
      //     if(this.sphere) {
      //       scene.remove( this.sphere );
      //     }
      // },
      /**
       * @description 加载图片
       * @param {any} src 要加载的图片地址
       * @returns promise
       */
      // loadImg(src) {
      //   return new Promise((resolve, reject) => {
      //     let img = document.createElement('img');
      //     // img.setAttribute('crossorigin', 'anonymous');
      //     img.src = src;
      //     img.onload = () => {
      //       resolve();
      //     };
      //     img.onerror = () => {
      //       reject();
      //     };
      //   });
      // },
      sceneRotation(){
        if (!this.rotationStatus) {
          this.rotation=window.setInterval(function(){ camera.rotateOnWorldAxis(new THREE.Vector3(0,1,0),0.001);},10);
        } else {
          clearTimeout(this.rotation);
        }
        this.rotationStatus = !this.rotationStatus;
      },
      musicPlay(){

        if (!this.musicStatus) {
          this.music = new Audio(this.musicPath);
          this.music.play();
        } else {
          this.music.pause();
        }
        this.musicStatus = !this.musicStatus;
      },
      fullScreen(){
        console.log(document.body.clientWidth+"|"+document.body.clientHeight);
        console.log(window.screen.width+"|"+window.screen.height);
        let main = $('.app-main');
        if (!this.screenStatus) {
          if (main.requestFullscreen) {
            main.requestFullscreen()
          } else if (main.mozRequestFullScreen) {
            main.mozRequestFullScreen()
          } else if (main.webkitRequestFullScreen) {
            main.webkitRequestFullScreen()
          } else if (main.msRequestFullscreen) {
            main.msRequestFullscreen()
          }
        } else {
          if (document.exitFullscreen) {
            document.exitFullscreen()
          } else if (document.mozCancelFullScreen) {
            document.mozCancelFullScreen()
          } else if (document.webkitCancelFullScreen) {
            document.webkitCancelFullScreen()
          } else if (document.msExitFullscreen) {
            document.msExitFullscreen()
          }
        }
        this.screenStatus = !this.screenStatus;
      },
      sneceChange(){
        console.log($('#panoramaShow'));
        if (!this.sceneChangeStatus) {
           $('#panoramaShow')[0].classList.add("snece-container");
        } else {
           $('#panoramaShow')[0].classList.remove("snece-container");
        }
        this.sceneChangeStatus = !this.sceneChangeStatus;
      },
      changePanorama(index)
        {
          if(this.ansArray[index]===100)
          {
            scene=this.sceneArray[index];
            this.panoramaIndex=index;
            return;
          }
          this.loadSlices(this.sliceArray[index],this.statuArray[index],index,index);
          this.loadAllSlices(this.sliceArray[index],this.statuArray[index],index,index);
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
      increase() {
        this.percentage += 10;
        if (this.percentage > 100) {
          this.percentage = 100;
        }
      },
      decrease() {
        this.percentage -= 10;
        if (this.percentage < 0) {
          this.percentage = 0;
        }
      },
      // addSensorOnDb(scid,x,y,z,sensorName,sensorDes)
      // {
      //   const sensor = {
      //   sceneId: scid,
      //   sensorId: -1,
      //   positionX: x,
      //   positionY: y,
      //   positionZ: z,
      //   sensorName: sensorName,
      //   sensorDes: sensorDes
      //   };
      //   test(sensor).then(res=>{
      //     console.log("这里添加了");
      //     console.log(res);
      //   })
      // },
      // showAllSensor()
      // {
      //   const _this=this;
      //   test1({}).then(res => {
      //     for(var sensor of res){
      //       console.log(sensor);
      //       _this.addSensor(sensor.id,sensor.sceneId,sensor.positionX,sensor.positionY,sensor.positionZ,sensor.sensorName,sensor.sensorDes);
      //       if(sensor.id>_this.maxId)
      //       {
      //         _this.maxId=sensor.id;
      //         console.log(_this.maxId);
      //       }
      //     }
      //   });
      // },
      addSensorBefore(){
        if(this.form.sensorType===""||this.form.sensorMes==="")
        {
          this.$message.error('传感器信息不得为空');
          return;
        }
        this.dialogFormVisible = false;
        this.addSensorStatus= true;
        console.log(this.addSensorStatus);
      },
      deleteSensorBefore(){
        this.deleteSensorStatus=!this.deleteSensorStatus;
      },
      hidePanorama(){
        // 原来是block
        let canvas = $('canvas');
        console.log(canvas);
        canvas.css({"display":"none"});
      },




    }
  }
</script>

<style scoped>
.snece-container{
    position: absolute;
    bottom: 7rem;
    border-radius: 0px;
    border: 15px solid rgba(39, 39, 39, 0.25);
    /* color: #0a0101; */
    background: rgba(39, 39, 39, 0.25);
    box-shadow: 0 3px 14px rgb(128 128 128 / 50%);
    width: 100%;
    background-repeat: repeat-x;
}
.thum{
  display: inline;
  margin: 0 0 50px 50px;
  cursor: pointer;    /*  鼠标放在上面为手指*/
  user-select: none;     /* 文字不可 */
}
button {
  background: transparent;
  border: 1px solid #00d0ffb8;
  color: white;
  padding: 7px 9px;
  border-radius: 5px;
  margin: 3px;
  cursor: pointer;
}
#leftbottom_menu {
    left: .5rem;
    bottom: 0;
}
#rightSideMenu{
  right: 1rem;
  top: 2rem;
}
#rightSideMenu>div {
    margin-top: 0.5rem;
    position: relative;
}
.h5Dom {
    position: absolute;
    z-index: 10;
    cursor: pointer;    /*  鼠标放在上面为手指*/
    user-select: none;     /* 文字不可 */
}
.skinBtn {
    font-size: 0.9rem;
    color: #fff;
    /* text-align: center; */
    text-shadow: 3px 3px 3px #000;
}
img {
    vertical-align: middle;
    border-style: none;
}
/* .mr-2, .mx-2 {
    margin-right: .5rem!important;
} */
</style>
