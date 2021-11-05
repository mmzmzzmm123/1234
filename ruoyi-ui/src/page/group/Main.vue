<template>
<div>
    <div>
      <el-container style="height: calc(100vh);">
        <el-header style="height :30px">全景图设计</el-header>
        <el-container style="height:calc(100% - 33px);">
          <el-aside width="30px" style="font-size:30px">
            <el-row>
            <i class="el-icon-s-comment"></i>
            </el-row>
             <el-row>
            <i class="el-icon-circle-plus-outline"></i>
            </el-row>
             <el-row>
            <i class="el-icon-edit"></i>
            </el-row>

          </el-aside>
          <el-container style="height: 100%;">

              <el-col  style="width:calc(100% - 150px);height: 100%;">
                <el-main id=contain style="height:calc(100% - 100px);">
                    <div id="container"></div>
                </el-main>
                <el-footer style="height: 100px;">
                  <div style="display:inline-block;width: 100px; height: 100px;float: left;margin-right: 20px;" v-for="(thumb,index) in thumbs" :key="index">
                    <!-- 这里index为什么不报错？ -->
                    <el-image
                      @click="changePanorama(thumb.index)"
                      style="cursor: pointer;"
                      :src="thumb.thumb"
                      :fit="fits[0]">
                      </el-image>
                  </div>
                  <div @click="dialogAddSceneVisible = true" style="display:inline-block;width: 96px; height: 96px;float: left;margin-right: 20px;border: 2px dashed gray;cursor: pointer;" key="fit">
                    <el-image
                      style="width: 50px; height: 50px;"
                      :src="addIcon"
                      :fit="fits[0]">
                      </el-image>
                      <div>
                        添加场景
                      </div>

                  </div>
                  </el-footer>
              </el-col>

              <el-col  style="width:150px ;height: 100%;background-color: #a1acbb;">
                <el-button type="primary" @click="dialogAddSensorVisible = true">添加传感器</el-button>
                <el-button type="primary" @click="dialogAddChangeVisible = true">添加场景切换</el-button>
                <div align="left" style="padding: 15px;"> 当前场景热点</div>
                <div>
                  <el-tag type="success" >test</el-tag>
                  </div>
                <!-- <el-input size="mini" v-model="input" placeholder="请输入内容"></el-input> -->
              </el-col>
          </el-container>
        </el-container>
      </el-container>
    </div>

    <el-dialog title="场景信息" :visible.sync="dialogAddSceneVisible">
        <el-form :model="form">
          <el-form-item label="图片路径" :label-width="formLabelWidth">
            <el-input v-model="addSceneTable.imagePath" autocomplete="off" ></el-input>
          </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogAddSceneVisible = false" style="color:black">取 消</el-button>
          <el-button type="primary" @click="addSceneBefore" style="color:black">确 定</el-button>
        </div>
      </el-dialog>

      <!-- <div id="snece-container" style="display:none"> -->
        <!-- <div style="text-align:center"> -->
          <!-- <img @click="changePanorama(0)" src="../../../public/thumb/thumb(1).jpg" class="thum" style="cursor: pointer; width: 100px;height: 100px;margin: 0 50px;">
          <img @click="changePanorama(1)" src="../../../public/thumb/thumb(4).jpg" class="thum" style="cursor: pointer; width: 100px;height: 100px;margin: 0 50px;"> -->
        <!-- </div>         -->
      <!-- </div> -->

      <el-dialog title="传感器信息" :visible.sync="dialogAddSensorVisible">
        <el-form :model="form">
          <el-form-item label="传感器图标路径" :label-width="formLabelWidth">
            <el-input v-model="addSensorTable.iconPath" autocomplete="off" ></el-input>
          </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogAddSensorVisible = false" style="color:black">取 消</el-button>
          <el-button type="primary" @click="addSensorBefore" style="color:black">确 定</el-button>
        </div>
      </el-dialog>

      <el-dialog title="全景切换信息" :visible.sync="dialogAddChangeVisible">
        <el-form :model="form">
          <el-form-item label="选择目标场景 " :label-width="formLabelWidth">
            <el-select v-model="addChangeTable.targetScene" placeholder="请选择目标场景" >
              <el-option v-for="thumb in thumbs" :key="thumb.index" :label="thumb.index+1" :value="thumb.index"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="场景切换图标路径" :label-width="formLabelWidth">
            <el-input v-model="addChangeTable.iconPath" autocomplete="off" ></el-input>
          </el-form-item>
        </el-form>

        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogAddChangeVisible = false" style="color:black">取 消</el-button>
          <el-button type="primary" @click="addChangeBefore" style="color:black">确 定</el-button>
        </div>
      </el-dialog>



</div>
</template>
<script>
  import * as THREE from 'three';
  import jq from 'jquery';
  import { OrbitControls } from "three/examples/jsm/controls/OrbitControls";
  import { TransformControls } from "three/examples/jsm/controls/TransformControls";
  import { DragControls } from "three/examples/jsm/controls/DragControls";
  import { TrackballControls } from 'three/examples/jsm/controls/TrackballControls.js'
  import { getLink,getVisualImage } from "@/api/system/visualImage";
  // import { Loader } from 'three';
  // import { test, test1, test2 ,test3 ,test4 } from "@/api/user";
  var camera;
  var renderer;
  var scene;
  const hCut=10;//水平划分次数
  const vCut=10;//垂直划分次数
  const widthSegments = 100;//水平分段数（沿着经线分段）
  const heightSegments = 100;//垂直分段数（沿着纬线分段）
  var imgIndex=[];
  var transformControls,dragControls,controls;


  export default {
    name: 'Main',
    // inject: ["contain"],
    components:{
    },
    data() {
      return {
        Img : require("../../../public/icon/blank.png"),
        addIcon: require("../../../public/icon/add.png"),
        helicopter : require("../../../public/icon/helicopter.png"),
        musicPath : require("../../../public/icon/bgm.mp3"),
        rotationStatus : false,
        musicStatus : false,
        screenStatus: false,
        sceneChangeStatus:false,
        addSensorStatus:false,
        addChangeStatus:false,
        deleteSensorStatus:false,
        rotation : null,
        music : null,
        rotationY :0,
        panoramaIndex : 0,
        sliceArray:[],
        statuArray:[],
        ansArray :[],
        sphereArray:[],
        sceneArray:[],
        hotPointObjectsArray:[],
        helicopterObjectsArray:[],
        dialogAddSceneVisible:false,
        dialogAddSensorVisible:false,
        dialogAddChangeVisible:false,
        form:{},
        addSceneTable:{
          imagePath: '',
          thumbPath: ''
        },
        addSensorTable:{
          iconPath:''
        },
        addChangeTable:{
          targetScene:'',
          iconPath:''
        },
        thumbs:[
          {index :0 ,thumb:require("../../../public/thumb/thumb(1).jpg")},
          {index :1,thumb:require("../../../public/thumb/thumb(5).jpg")}
        ],
        fits: ['fill', 'contain', 'cover', 'none', 'scale-down'],
        formLabelWidth: '130px',
        maxId:0,
        // input没定义会有报错信息
        input:"test",
        thumb:"",
        sceneMes:
        {
          originalFile:"",
          originalFileFolder:"",
          thumb:""
        }
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
      async init() {
        //取消右键点击事件
        document.oncontextmenu = function (event) {
          event.preventDefault();
        }
        var container = document.getElementById('container');
        // 创建渲染器
        renderer = new THREE.WebGLRenderer();
        renderer.setPixelRatio(window.devicePixelRatio);
        // // 设置画布的宽高
        // renderer.setSize(this.contain.config.width, this.contain.config.height);
        let contain = jq('#contain');
        // renderer.setSize(window.innerWidth, window.innerHeight);
        renderer.setSize(contain.width(), contain.height());
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
        // 创建相机
        camera = new THREE.PerspectiveCamera(90, contain.width() / contain.height(), 0.1, 100);
        // camera = new THREE.PerspectiveCamera(90, this.contain.config.width / this.contain.config.height, 0.1, 100);
        // camera = new THREE.PerspectiveCamera(90, 836/470, 0.1, 100);
        // camera.position.set(0, 0, 0);
        // camera.lookAt(100,0,0)
        // camera.updateProjectionMatrix();
        // camera.rotateOnAxis( 1, Math.PI)
        // this.creatImgArray(1);
        this.creatImgArray();
        // const _this=this;
        await getLink({fileName:"1268f2f77a5b4f28bbb42122294d1c61.jpg"}).then(res=>{
          this.thumb=res.data.original;
          console.log(this.thumb);
        });
        this.loadThumb(this.thumb,0);
        scene=this.sceneArray[0];
        // this.loadSlices(this.sliceArray[0],this.statuArray[0],0,0);
        // this.loadAllSlices(this.sliceArray[0],this.statuArray[0],0,0);

        this.loadThumb(this.Img,1);
        // this.loadSlices(this.sliceArray[1],this.statuArray[1],1,1);
        // this.loadAllSlices(this.sliceArray[1],this.statuArray[1],1,1);

        // const texture1 = new THREE.TextureLoader().load(this.Img);
        // const geometry1 = new THREE.SphereGeometry( 10, 100, 100 ),
        //       materials1 = [new THREE.MeshBasicMaterial({map: texture1})];
        // //使贴图反转,正常贴图从物体外面看正常，现在反转后在物体里看贴图真唱
        // geometry1.scale(-1,1,1);
        // const sphere1 = new THREE.Mesh( geometry1, materials1 );
        // scene.add(sphere1);
        this.addSceneChange(0.013116787999754971,-0.18231706437628967,0.1378297619039064,0,1);
        this.addSceneChange(0.17034009935591,-0.10829839635524996,0.05270989913184244,0,1);
        this.addSceneChange(0.21578786899258415,-0.13091528735648722,-0.09050630485317226,0,1);
        this.addSceneChange(-0.1591935714488157,-0.18811305291274494,0.13739973946592704,0,1);
        this.addSceneChange(-0.048636218952825355,-0.15179340246416667,-0.12639079056109787,1,0);
        this.addSceneChange(-0.14400823928836437,-0.13452716136265883,0.04245067127477214,1,0);
        this.addSceneChange(0.2106611107946028,-0.04024874084778395,-0.00949439526434595,1,0);
        // this.setDrawAvailability();
        this.addMouseEvent();
        // this.showAllSensor();
        // getVisualImage({fileName:"3778417c42f6477cba66443ef20cda50.jpg"}).then(res=>{
        //   console.log(res.data);
        // })
        getLink({fileName:"test.png"}).then(res=>{
          console.log(res);
        });

      },
    // stats.update();
    // this.painter.loadSlices();
      onWindowResize() {
        // 窗口缩放的时候,保证场景也跟着一起缩放
        // camera.aspect = window.innerWidth / window.innerHeight;
        // camera.updateProjectionMatrix();
        // renderer.setSize(window.innerWidth, window.innerHeight);
        let contain = jq('#contain');
        console.log(contain.width()+"-"+contain.height());
        camera.aspect = contain.width() / contain.height();
        camera.updateProjectionMatrix();
        renderer.setSize(contain.width(), contain.height());
      },
      animate() {
        renderer.render(scene, camera);
        requestAnimationFrame(this.animate);
        // controls.update();
        // controls.handleResize();
        // transformControls.update();
      },
       //创建图片数组
      creatImgArray(){
        let path="panorama/";
        // var reg = /\w(\.gif|\.jpeg|\.png|\.jpg|\.bmp)/i;
        // let finalPath="";
        // var context=require.context('${path}', false, /\w(\.gif|\.jpeg|\.png|\.jpg|\.bmp)/i);
        // console.log(context);

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
              this.sliceArray[m][i][j]=require("../../../public/"+path+(j*vCut+i+1+m*100)+".gif");
            }
          }
        }

      },
      tcreatImgArray(){

      },
      //添加鼠标事件
      addMouseEvent(){
        var container = document.getElementById('container');
        const _this = this;
        window.addEventListener('resize', this.onWindowResize, false);
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
        _this.loadSlices(_this.sliceArray[0],_this.statuArray[0],0,0);
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
        container.onclick = function (event){
          event.preventDefault();
          var raycaster=new THREE.Raycaster();
          var mouse = new THREE.Vector2();
                    let contain = jq('#contain');
          // console.log((event.clientX-30)+"-"+(event.clientY-33)+"-"+contain.width()+"-"+contain.height());
          mouse.x = ( (event.clientX-30) / contain.width() ) * 2 - 1;
          mouse.y = - ( (event.clientY-30) / contain.height() ) * 2 + 1;
          raycaster.setFromCamera(mouse,camera);
          var intersects1 =raycaster.intersectObjects(_this.hotPointObjectsArray[_this.panoramaIndex]);
          if(intersects1.length>0)
          {
            test2(intersects1[0].object.details.id).then(res => {
              _this.showform.sensorType=res.sensorName;
              _this.showform.sensorMes="描述信息:"+res.sensorDes;
              _this.dialogTableVisible=true;
            });
          }
          var intersects2 =raycaster.intersectObjects(_this.helicopterObjectsArray[_this.panoramaIndex]);
          if(intersects2.length>0)
          {
            console.log(intersects2[0].object.details);
            _this.changePanorama(intersects2[0].object.details.changeIndex);
          }
        }
        container.oncontextmenu = function (event){
          console.log("这里执行了");
          event.preventDefault();
          var mouse = new THREE.Vector2();
          let contain = jq('#contain');
          // console.log((event.clientX-30)+"-"+(event.clientY-33)+"-"+contain.width()+"-"+contain.height());
          mouse.x = ( (event.clientX-30) / contain.width() ) * 2 - 1;
          mouse.y = - ( (event.clientY-30) / contain.height() ) * 2 + 1;
          let vector = new THREE.Vector3(mouse.x, mouse.y,0);
            //从屏幕向量转为3d空间向量
          vector.unproject(camera);
          console.log(vector.x+"|"+vector.y+"|"+vector.z+"|"+_this.panoramaIndex);
          if(_this.addSensorStatus)
          {
            _this.addSensorStatus=false;
            let vector = new THREE.Vector3(mouse.x, mouse.y,0);
            //从屏幕向量转为3d空间向量
            console.log(vector);
            vector.unproject(camera);
            // id,x,y,z,sceneId,sensorDes
            // _this.addSensorOnDb(_this.panoramaIndex,vector.x,vector.y,vector.z,_this.form.sensorType,_this.form.sensorMes);
            // _this.maxId=_this.maxId+1;
            // _this.addSensor(_this.maxId,_this.panoramaIndex,vector.x,vector.y,vector.z,_this.form.sensorType,_this.form.sensorMes);
            _this.addSensor(vector.x,vector.y,vector.z,_this.panoramaIndex);

            // test1({}).then(res => {
            // for(var sensor of res){
            // console.log(sensor);
            // }
            // })
            // _this.form.sensorType=null;
            // _this.form.sensorMes=null;
          }
          else if(_this.addChangeStatus)
          {
            _this.addSensorStatus=false;
            let vector = new THREE.Vector3(mouse.x, mouse.y,0);
            vector.unproject(camera);
            _this.addSceneChange(vector.x,vector.y,vector.z,_this.panoramaIndex,_this.addChangeTable.targetScene);
          }
          else if(_this.deleteSensorStatus)
          {
            _this.deleteSensorStatus=!_this.deleteSensorStatus;
            var raycaster=new THREE.Raycaster();
            raycaster.setFromCamera(mouse,camera);
            var intersects =raycaster.intersectObjects(_this.hotPointObjectsArray[_this.panoramaIndex]);
            if(intersects.length>0)
            {
                test3(intersects[0].object.details.id).then(res => {
              });
            }
            scene.remove(intersects[0].object);
          }
        }
      },
      //加载缩略图
      loadThumb(url,sphereIndex) {
        //创建分场景；
        this.sceneArray[sphereIndex] = new THREE.Scene();
        let loader = new THREE.TextureLoader();
        loader.crossOrigin = '*';
        console.log(this.thumb);
        const texture = loader.load(url);
        texture.minFilter=THREE.LinearFilter;
        texture.magFilter=THREE.LinearFilter;
        const geometry = new THREE.SphereGeometry( 100, widthSegments, heightSegments );
              // materials = [new THREE.MeshBasicMaterial({map: texture})];
        var   materials = [new THREE.MeshBasicMaterial({map: texture})];

        geometry.groupsNeedUpdate = true;
        geometry.uvsNeedUpdate = true;
        //使贴图反转,正常贴图从物体外面看正常，现在反转后在物体里看贴图正常，更改xyz次序可能导致视野判断失效
        geometry.scale(-1,1,1);
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
           jq('#panoramaShow')[0].classList.add("snece-container");
        } else {
           jq('#panoramaShow')[0].classList.remove("snece-container");
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
      async addSceneBefore(){
        console.log(this.addSceneTable.imagePath);
        console.log(this.addSceneTable.iconPath);
        // sceneMes:
        // {
        //   originalFile:"",
        //   originalFileFolder:"",
        //   thumb:""
        // }
        var imagePath=this.addSceneTable.imagePath;
        await getLink({fileName:imagePath}).then(res=>{
          this.sceneMes.originalFile=res.data.original;
          this.sceneMes.originalFileFolder=res.data.subRoot;
          this.sceneMes.thumb=res.data.thumb;
          console.log(this.sceneMes);
        });
        //大图片的缩略图无法显示问题
        // this.thumbs.push({index:2,thumb:this.sceneMes.thumb});
        this.thumbs.push({index:2,thumb:this.thumb});
        this.addScene();
        this.dialogAddSceneVisible=false;
        // thumbs:[
        //   {index :0 ,thumb:require("../../../public/thumb/thumb(1).jpg")},
        //   {index :1,thumb:require("../../../public/thumb/thumb(5).jpg")}
        // ],
      },
      addScene(){
        // 添加场景信息到数组
        // const imagePath=this.addSceneTable.imagePath;
        // const thumbPath=this.addSceneTable.thumbPath;
        const imagePath=this.sceneMes.originalFileFolder;
        const index= this.sliceArray.length;
        this.sliceArray[index]=[];
        this.statuArray[index]=[];
        this.ansArray[index]=0;
        this.sceneArray[index]=null;
        this.helicopterObjectsArray[index]=[];
        this.hotPointObjectsArray[index]=[];
        for (let i=0;i<vCut;i++)
        {
          this.sliceArray[index][i]=[];
          this.statuArray[index][i]=[];
          for(let j=0;j<hCut;j++)
          {
            this.sliceArray[index][i][j]=null;
            this.statuArray[index][i][j]=0;
          }
        }
        for (let i=0;i<vCut;i++)
        {
          for(let j=0;j<hCut;j++)
          {
            // slices[i][j]=this.Imgs[j*vCut+i];
            //因为require它是打包工具所需要的标识，你搞成运行时通过变量去定义的话，它就没办法打包了啊
            //所以不可以写成require("../../public/photo/"+index+".gif"")
            this.sliceArray[index][j][i]=imagePath+i+"_"+j+".jpg";
          }
        }
        //将场景添加到场景切换部分
        // @click="changePanorama('+index+')"
        //初始化数组样式
        this.loadThumb(this.Img,index);
      },
      addSensorBefore(){
        this.dialogAddSensorVisible = false;
        this.addSensorStatus= true;
      },
      addSensor(x,y,z,index)
      {
        let pointTexture = new THREE.TextureLoader().load(require("../../../public/icon/hotpoint.png"));
        let spriteMaterial = new THREE.SpriteMaterial({map:pointTexture});
        let sprite = new THREE.Sprite(spriteMaterial);
        sprite.scale.set(0.015,0.015,1);
        sprite.position.set(x,y,z);
        this.sceneArray[index].add(sprite);
        sprite.ids={id:1};
        this.hotPointObjectsArray[index].push(sprite);
      },
      addChangeBefore(){
        this.dialogAddChangeVisible = false;
        this.addChangeStatus= true;
      },
      deleteSensorBefore(){
        this.deleteSensorStatus=!this.deleteSensorStatus;
      },
      sceneBarShow(){
        if (!this.sceneChangeStatus) {
            jq('#snece-container').css({"display":"block"});
        } else {
            jq('#snece-container').css({"display":"none"});
        }
        this.sceneChangeStatus = !this.sceneChangeStatus;
    },
      // setDrawAvailability(){
      // var objects=[];
      // console.log(scene.children);
      // for (let i = 0; i < scene.children.length; i++) {
      //       if (scene.children[i].isSprite || scene.children[i].isMesh) {
      //           objects.push(scene.children[i]);
      //       }
      //   }
      // console.log(objects);

      // transformControls = new TransformControls(camera, renderer.domElement);
      // scene.add(transformControls);
      // dragControls = new DragControls(objects, camera, renderer.domElement);
      // controls = new TrackballControls(camera, renderer.domElement);
      // controls.noPan = true;

      // dragControls.addEventListener('hoveron', function (event) {
      //         // 让变换控件对象和选中的对象绑定
      //         console.log("绑定");
      //         console.log(event.object);
      //         transformControls.attach(event.object);
      //     });
      //     // 开始拖拽
      //     dragControls.addEventListener('dragstart', function (event) {
      //         controls.enabled = false;
      //     });
      //     // 拖拽结束
      //     dragControls.addEventListener('dragend', function (event) {
      //         controls.enabled = true;
      //     });
      // },


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
            frustum.setFromProjectionMatrix(cameraViewProjectionMatrix); //设置frustum沿着相机法线方向
            return !frustum.intersectsObject(obj);
      },

    }
  }
</script>

<style scoped>
.btn-container {
  position: absolute;
  left: 10px;
  top: 90px;
  padding: 10px 15px;
  border-radius: 4px;
  border: 1px solid rgba(128, 128, 128, 0.5);
  color: #ffffff;
  background: rgba(0, 0, 0, 0.4);
  box-shadow: 0 3px 14px rgb(128 128 128 / 50%);
  max-width: 380px;
}
.btnarea {
  background: transparent;
  border: 1px solid #00d0ffb8;
  color: white;
  padding: 7px 9px;
  border-radius: 2px;
  margin: 3px;
  cursor: pointer;
}

#snece-container{
    position: absolute;
    bottom: 7rem;
    border-radius: 0px;
    border: 15px solid rgba(39, 39, 39, 0.25);
    background: rgba(39, 39, 39, 0.25);
    box-shadow: 0 3px 14px rgb(128 128 128 / 50%);
    width: 100%;
    height:130px;
    background-repeat: repeat-x;
}


.thum{
  display: inline;
  margin: 0 0 50px 50px;
  cursor: pointer;    /*  鼠标放在上面为手指*/
  user-select: none;     /* 文字不可 */
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

.el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    padding:0px;
  }

  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
  }

  .el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    padding:0px;
  }

</style>
