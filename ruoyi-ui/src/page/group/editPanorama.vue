<script src="../../api/system/panorama.js"></script>
<template>
  <div>
    <div style="overflow:hidden;">
      <el-container style="height: calc(100vh);">
        <el-header style="height :30px">
          全景图设计
          <i @click="saveDB"
             class="el-icon-edit"
             style="float: right;margin-top: 5px;cursor: pointer;">保存</i>
        </el-header>
        <el-container style="height:calc(100% - 33px);">
          <el-aside width="30px"
                    style="font-size:30px">
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

            <el-col style="width:calc(100% - 150px);height: 100%;">
              <el-main id=contain
                       style="height:calc(100% - 100px);">
                <div id="container"></div>
              </el-main>
              <el-footer style="height: 100px;">
                <div style="display:inline-block;width: 80px; height: 80px;float: left;margin-right: 20px;"
                     v-for="thumb in thumbs"
                     :key="thumb.index">
                  <!-- 这里index为什么不报错？ -->
                  <el-image @click="changePanorama(thumb.index)"
                            style="cursor: pointer;"
                            :src="thumb.thumb"
                            :fit="fits[0]">

                  </el-image>
                  <span style="float:left;font-size: 0.5px;">{{ thumb.newName.substring(0,4) }}</span>
                  <!--                    <i @click="operateScene(thumb.index)" class="el-icon-delete" style="float:right;"></i>-->
                  <el-popconfirm confirm-button-text='确认'
                                 cancel-button-text='取消'
                                 icon="el-icon-info"
                                 icon-color="red"
                                 title="是否删除该场景？"
                                 @confirm="deleteScene(thumb.index)">
                    <i class="el-icon-delete"
                       style="float:right;cursor: pointer;"
                       slot="reference"></i>
                  </el-popconfirm>
                </div>
                <div @click="getWaitScene"
                     style="display:inline-block;width: 100px; height: 100px;float: left;margin-right: 20px;border: 2px dashed gray;cursor: pointer;"
                     key="fit">
                  <el-image style="width: 50px; height: 50px;margin-top:6px"
                            :src="addIcon"
                            :fit="fits[0]">
                  </el-image>
                  <div>
                    添加场景
                  </div>

                </div>
              </el-footer>
            </el-col>

            <el-col style="width:150px ;height: 100%;background-color: #a1acbb;">
              <el-button type="primary"
                         @click="dialogAddSensorVisible = true"
                         style="margin-left:0px;margin-top:3px;width:100%;">添加传感器</el-button>
              <el-button type="primary"
                         @click="dialogAddChangeVisible = true"
                         style="margin-left:0px;margin-top:3px;width:100%;">添加场景切换</el-button>

              <div style="padding: 15px;"> 当前场景热点</div>
              <el-card style="margin-bottom: 5px;"
                       v-for="hotPoint in hotPoints"
                       :key="hotPoint.id"
                       :body-style="{ padding: '0px' }"
                       v-if="isCurrentHotpoint(hotPoint)">
                <span style="float: left">{{ hotPoint.title }}</span>
                <span style="float: right; color: #8492a6; font-size: 10px;margin-top: 3.5px;"
                      v-if="hotPoint.type==='sensor'">传感器</span>
                <span style="float: right; color: #8492a6; font-size: 10px;margin-top: 3.5px;"
                      v-else-if="hotPoint.type==='change'">场景切换</span>
                <i @click="deleteObj(hotPoint.id)"
                   style="float: right;margin-top: 3.5px;cursor: pointer;"
                   class="el-icon-delete"></i>
                <i @click="editObjBefore(hotPoint.id)"
                   style="float: right;margin-top: 3.5px;cursor: pointer;"
                   class="el-icon-edit"></i>
              </el-card>
              <!-- <el-input size="mini" v-model="input" placeholder="请输入内容"></el-input> -->
            </el-col>
          </el-container>
        </el-container>
      </el-container>
    </div>

    <!-- <div id="snece-container" style="display:none"> -->
    <!-- <div style="text-align:center"> -->
    <!-- <img @click="changePanorama(0)" src="../../../public/thumb/thumb(1).jpg" class="thum" style="cursor: pointer; width: 100px;height: 100px;margin: 0 50px;">
          <img @click="changePanorama(1)" src="../../../public/thumb/thumb(4).jpg" class="thum" style="cursor: pointer; width: 100px;height: 100px;margin: 0 50px;"> -->
    <!-- </div>         -->
    <!-- </div> -->
    <!-- 添加场景信息窗体-->
    <el-dialog title="场景信息"
               :visible.sync="dialogAddSceneVisible">
      <el-form :model="form">
        <!-- <el-form-item label="图片路径" :label-width="formLabelWidth">
        <el-input v-model="addSceneTable.imagePath" autocomplete="off" ></el-input>
      </el-form-item> -->

        <el-form-item label="选择场景"
                      :label-width="formLabelWidth">
          <el-select v-model="addSceneTable"
                     placeholder="请选择需要添加的场景"
                     value-key="id">
            <el-option v-for="scene in waitScene"
                       :key="scene.id"
                       :label="scene.originName.substring(0,scene.originName.length-4)"
                       :value="scene">
              <span style="float: left">{{ scene.originName.substring(0,scene.originName.length-4)}}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ scene.createTime }}</span>
            </el-option>
          </el-select>
        </el-form-item>

      </el-form>
      <div slot="footer"
           class="dialog-footer">
        <el-button @click="dialogAddSceneVisible = false"
                   style="color:black">取 消</el-button>
        <el-button type="primary"
                   @click="addSceneBefore"
                   style="color:black">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 添加传感器信息窗体-->
    <el-dialog title="传感器信息"
               :visible.sync="dialogAddSensorVisible">
      <el-form :model="form">

        <el-form-item label="标题"
                      :label-width="formLabelWidth">
          <el-input v-model="addSensorTable.title"
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="数据接口地址"
                      :label-width="formLabelWidth">
          <el-input v-model="addSensorTable.address"
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标上传类型"
                      :label-width="formLabelWidth">
          <el-select v-model="iconType"
                     placeholder="请选择">
            <el-option v-for="item in optionsIconType"
                       :key="item.value"
                       :label="item.label"
                       :value="item.value">
            </el-option>
          </el-select>

        </el-form-item>
        <el-form-item v-if="iconType==='系统图标'"
                      label="传感器图标"
                      :label-width="formLabelWidth">
          <el-select style="width: 100%"
                     v-model="addSensorTable.iconPath"
                     value-key="value"
                     placeholder="请选择图标"
                     @change="changeSelection"
                     ref="refSelect">
            <el-option v-for="item in optionsImg"
                       :key="item.value"
                       :value="item.label"
                       :label="item.desc">
              <div class="option_box">
                <el-image class="option_img"
                          :src="item.label"></el-image>
                <!--                  <span style="float: right; color: #8492a6; font-size: 13px">{{ item.desc }}</span>-->
                <span v-text="item.desc"></span>
              </div>
            </el-option>
          </el-select>
        </el-form-item>
        <!--          <el-form-item v-else-if="iconType==='选项二'" label="上传传感器图标" :label-width="formLabelWidth">-->
        <el-form-item v-else-if="iconType==='自定义上传'"
                      label="上传传感器图标"
                      :label-width="formLabelWidth">
          <el-upload class="avatar-uploader"
                     :action="imageApi"
                     :before-upload="beforeUpload"
                     :show-file-list="false"
                     :on-success="handleAvatarSuccess">
            <img v-if="imageUrl"
                 :src="imageUrl"
                 class="avatar">
            <i v-else
               class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
      </el-form>

      <div slot="footer"
           class="dialog-footer">
        <el-button @click="dialogAddSensorVisible = false"
                   style="color:black">取 消</el-button>
        <el-button type="primary"
                   @click="judgeAddEditSensor"
                   style="color:black">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 添加场景切换信息窗体-->
    <el-dialog @open="openChangeBefore"
               @closed="closeChangeAfter"
               title="全景切换信息"
               :visible.sync="dialogAddChangeVisible">
      <el-form :model="form">
        <el-form-item label="标题"
                      :label-width="formLabelWidth">
          <el-input v-model="addChangeTable.title"
                    autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="选择目标场景"
                      :label-width="formLabelWidth">
          <el-select v-model="addChangeTable.targetScene"
                     placeholder="请选择目标场景">
            <el-option v-for="thumb in thumbs"
                       :key="thumb.index"
                       :label="thumb.newName.substring(0,thumb.newName.length-4)"
                       :value="thumb.index"
                       :disabled="thumb.disabled"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="图标上传类型"
                      :label-width="formLabelWidth">
          <el-select v-model="iconType"
                     placeholder="请选择">
            <el-option v-for="item in optionsIconType"
                       :key="item.value"
                       :label="item.label"
                       :value="item.value">
            </el-option>
          </el-select>

        </el-form-item>
        <el-form-item v-if="iconType==='系统图标'"
                      label="场景切换图标"
                      :label-width="formLabelWidth">
          <el-select style="width: 100%"
                     v-model="addChangeTable.iconPath"
                     placeholder="请选择图标"
                     @change="changeSelection"
                     value-key="value"
                     ref="refSelect">
            <el-option v-for="item in optionsImg"
                       :key="item.value"
                       :value="item.label"
                       :label="item.desc">
              <div class="option_box">
                <el-image class="option_img"
                          :src="item.label"></el-image>
                <span v-text="item.desc"></span>
              </div>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-else-if="iconType==='自定义上传'"
                      label="上传切换图标"
                      :label-width="formLabelWidth">
          <el-upload class="avatar-uploader"
                     :action="imageApi"
                     :before-upload="beforeUpload"
                     :show-file-list="false"
                     :on-success="handleAvatarSuccess">
            <img v-if="imageUrl"
                 :src="imageUrl"
                 class="avatar">
            <i v-else
               class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
      </el-form>

      <div slot="footer"
           class="dialog-footer">
        <el-button @click="dialogAddChangeVisible = false"
                   style="color:black">取 消</el-button>
        <el-button type="primary"
                   @click="judgeAddEditChange"
                   style="color:black">确 定</el-button>
      </div>
    </el-dialog>
    <myDialog></myDialog>
  </div>
</template>
<script>
import * as THREE from 'three';
import jq from 'jquery';
import { ref } from 'vue'
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls';
import { TransformControls } from "three/examples/jsm/controls/TransformControls";
import { DragControls } from "three/examples/jsm/controls/DragControls";
import { TrackballControls } from 'three/examples/jsm/controls/TrackballControls.js';
import { getLink, listVisualImage } from "@/api/system/visualImage";
import { getPanorama, delPanorama, addPanorama, updatePanorama, exportPanorama } from "@/api/system/panorama";
import myDialog from "@/page/group/myDialog.vue";
import { globalBus } from "@/page/group/globalBus";
var camera;
var renderer;
var scene;
// var hCut=10;//水平划分次数
// var vCut=10;//垂直划分次数
var imgIndex = [];
// var transformControls,dragControls,controls;


export default {
  name: 'editPanorama',
  // inject: ["contain"],
  components: {
    myDialog: myDialog
  },
  data () {
    return {
      Img: require("../../../public/icon/blank.png"),
      addIcon: require("../../../public/icon/add.png"),
      helicopter: require("../../../public/icon/helicopter.png"),
      // musicPath : require("../../../public/icon/bgm.mp3"),
      // rotationStatus : false,
      // musicStatus : false,
      // screenStatus: false,
      // sceneChangeStatus:false,
      addSensorStatus: false,
      addChangeStatus: false,
      deleteSensorStatus: false,
      rotation: null,
      music: null,
      rotationY: 0,
      sceneAns: 0,//计算当前的场景个数
      panoramaIndex: 0, //当前查看的场景index
      sliceArray: [],
      statuArray: [],
      ansArray: [],
      sphereArray: [],
      sceneArray: [],
      hCut: [],
      vCut: [],
      widthScale: [],
      heightScale: [],
      widthSegments: 100,
      heightSegments: 100,
      hotPointObjectsArray: [],
      helicopterObjectsArray: [],
      dialogAddSceneVisible: false,
      dialogAddSensorVisible: false,
      dialogAddChangeVisible: false,
      addSceneTable: null,
      addSensorTable: { iconPath: null, title: null, address: null },
      addChangeTable: { targetScene: null, iconPath: null, title: null },
      //所有下方显示的缩略图场景信息
      thumbs: [],
      // {index :0 ,thumb:require("../../../public/thumb/thumb(1).jpg"),v:10,h:10},
      // {index :1,thumb:require("../../../public/thumb/thumb(5).jpg"),v:10,h:10}
      fits: ['fill', 'contain', 'cover', 'none', 'scale-down'],
      formLabelWidth: '120px',
      maxId: 0,
      // input,form没定义会有报错信息
      input: "test",
      form: {},
      thumb: "",
      selectedSceneUrl: {},
      // 查询全景图的参数
      queryParams: {
        pageNum: 1,
        pageSize: 1000,
        originName: null,
        newName: null,
        width: null,
        height: null,
      },
      waitScene: [],
      message: [
        // {id:1,
        //   type:"scene",
        //   h:10,
        //   v:10,
        //   originalFile:"http://172.17.169.157:8080/profile/images/c4fa930c378a4e4ebd5a7c29c8895c5e.jpg",
        //   originalFileFolder:"http://172.17.169.157:8080/profile/images/c4fa930c378a4e4ebd5a7c29c8895c5e/",
        //   thumb:"http://172.17.169.157:8080/profile/images/c4fa930c378a4e4ebd5a7c29c8895c5e/thumb.jpg",
        //   thumb2:"http://172.17.169.157:8080/profile/images/c4fa930c378a4e4ebd5a7c29c8895c5e/thumb2.jpg",
        //   newName:"全景图189M版本.jpg",
        //   showName:"全景图189M版本",
        //   index:0,
        //   },
        // {id:1,type:"sensor",x:-0.09723279794161858,y:0.042633608612722446,z:-0.22393108896195182,icon:"../../../public/icon/hotpoint.png",sceneId:'7599ybcnpfw00000000'},
        // {id:1,type:"change",x:0.09896295074531478,y:-0.013120088166992493,z:-0.1998001998001998,icon:"../../../public/icon/helipoter.png",currentSceneId:'7599ybcnpfw00000000',changeSceneId:'sppvlg735pc0000000'}
      ],
      formDB: [],
      idToIndex: {},
      indexToId: {},
      sceneIdArray: [],//放入所有的场景id
      optionsImg: [
        {
          label: require('../../../public/icon/helicopter.png'),
          value: '0',
          desc: "图标1"
        },
        {
          label: require('../../../public/icon/hotpoint.png'),
          value: '1',
          desc: "图标2"
        },
        {
          label: require('../../../public/icon/1.png'),
          value: '2',
          desc: "图标3"
        },
        {
          label: require('../../../public/icon/2.png'),
          value: '3',
          desc: "图标4"
        },
        {
          label: require('../../../public/icon/3.png'),
          value: '4',
          desc: "图标5"
        },
        {
          label: require('../../../public/icon/4.png'),
          value: '5',
          desc: "图标6"
        }, {
          label: require('../../../public/icon/5.png'),
          value: '6',
          desc: "图标7"
        }, {
          label: require('../../../public/icon/6.png'),
          value: '7',
          desc: "图标8"
        }, {
          label: require('../../../public/icon/7.png'),
          value: '8',
          desc: "图标9"
        },
      ],
      //所有传感器以及场景切换的热点
      hotPoints: [],
      // 表单参数
      iconform: {},
      imageUrl: null,
      uploadHeader: {},
      imageApi: process.env.VUE_APP_IMAGE_API,
      // 是上传还是系统图标选项配置
      optionsIconType: [{
        value: '系统图标',
        label: '系统图标'
      }, {
        value: '自定义上传',
        label: '自定义上传'
      }],
      iconType: null,
      //图标文件夹路径
      iconFolder: process.env.VUE_APP_IMAGE_PATH,
      editId: null,
    }
  },
  // setup(){
  //   const child=ref(null);
  //   const clickSon=()=>{
  //     child.value.showDialog();
  //   }
  //   return {child,clickSon};
  // },
  mounted () {
    // 调用全景图函数
    this.$nextTick(() => {
      this.init();
      this.animate();
    });
  },
  methods: {

    //自定义上传图标管理
    loadingStart () {
      this.loadObj = this.$loading({
        lock: true,
        text: '正在上传',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
    },
    loadingClose () {
      this.loadObj.close();
    },
    beforeUpload (file) {
      //
      this.iconform.newName = null
      this.loadingStart()
      return true;
    },
    handleAvatarSuccess (res, file) {
      console.log(res, file);
      this.iconform.originName = file.name
      this.iconform.newName = res.data.name
      this.imageUrl = URL.createObjectURL(file.raw);
      this.loadingClose()
    },
    showDialog (url) {
      // this.$refs.child.showDialog();
      globalBus.$emit("show", url);
    },
    changeSelection (val) {
      if (val == null) {
        this.$refs["refSelect"]
          .$el.children[0]
          .children[0]
          .setAttribute(
            "style",
            `
				`
          );
      }

      let optionsImg = this.optionsImg,
        i = optionsImg.findIndex((item) => item.label == val);
      this.$refs["refSelect"]
        .$el.children[0]
        .children[0]
        .setAttribute(
          "style",
          `
				background: url(${optionsImg[i].label}) no-repeat;
				background-position: 10px center;
				background-size: 20px 20px;
				text-indent: 30px;
				`
        );
    },
    // 全景图配置函数---------------
    test () {
      //   getLink({fileName:"d88ecabf67d64d04aac8543a71878d81.jpg"}).then(res=>{
      //   console.log(res);
      // });
      // const _this=this;
      console.log(this.iconType);
      return this.iconType === "选项1";
    },
    reset () {
      this.form = {
        id: null,
        name: null,
        descri: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        detail: null,
        component: null
      };
    },
    async saveDB () {
      this.reset();
      const id = this.$route.params.id;
      // console.log(id);
      await getPanorama(id).then(response => {
        // console.log(response);
        this.formDB = response.data;
      });
      this.formDB.component = JSON.stringify(this.message);
      // console.log(JSON.parse(this.formDB.component) || []);//把字符串变成json
      await updatePanorama(this.formDB).then(response => {
        this.$message({
          message: '保存成功',
          type: 'success'
        });
      });
      // console.log(JSON.parse(this.formDB.component)||[]);
    },
    async showDB () {
      const id = this.$route.params.id;
      // console.log(id);
      await getPanorama(id).then(response => {
        // console.log(response);
        this.formDB = response.data;
      });
      this.message = JSON.parse(this.formDB.component) || [];
      console.log(this.message);
      // console.log("json文件");
      // console.log(this.message);
      // 先遍历场景，再遍历传感器和场景变换
      for (var obj of this.message) {
        if (obj.type === "scene") {
          this.idToIndex[obj.id] = this.sceneAns;
          this.indexToId[this.sceneAns] = obj.id;
          this.sceneIdArray.push(obj.id);
          obj["index"] = this.sceneAns;
          this.sceneAns++;
          // console.log(obj);
          this.addSceneByDb(obj);
          obj['disabled'] = false;
          this.thumbs.push(obj);
          // console.log(obj);
        }
      }
      // console.log(this.idToIndex);
      // console.log("sceneAns"+this.sceneAns)
      if (this.sceneAns > 0) { scene = this.sceneArray[0]; }

      for (var obj of this.message) {
        if (obj.type === "sensor") {
          // this.addSensorByDb(obj.x,obj.y,obj.z,obj.icon,obj.sceneId);
          // console.log(obj.sceneId);
          // console.log("id:"+this.idToIndex[obj.sceneId]);
          this.addSensorByDb(obj);
          this.hotPoints.push(obj);
        }
        else if (obj.type === "change") {
          // this.addChangeByDb(obj.x,obj.y,obj.z,obj.icon,obj.currentSceneId,obj.changeSceneId);
          // console.log(obj);
          // console.log("--------------------")
          // console.log(this.sceneIdArray);
          // console.log(obj.currentSceneId);
          // console.log(obj.changeSceneId);
          // console.log(this.sceneIdArray.indexOf[obj.currentSceneId]);
          // console.log(this.sceneIdArray.indexOf[obj.changeSceneId]);
          this.addChangeByDb(obj);
          this.hotPoints.push(obj);
        }
      }
    },
    addSceneByDb (obj) {
      // 添加场景信息到数组
      // const imagePath=this.addSceneTable.imagePath;
      // const thumbPath=this.addSceneTable.thumbPath;
      const imagePath = obj.originalFileFolder;
      const index = obj.index;
      const imageSub = obj.originalFile.substr(obj.originalFile.length - 4, 4);
      // console.log(this.addSceneTable.height+"-"+this.addSceneTable.width)
      // console.log(vCut+"-"+hCut);
      // console.log("图片路径"+imageSub);
      this.sliceArray[index] = [];
      this.statuArray[index] = [];
      imgIndex[index] = [];
      this.hCut[index] = obj.h;
      this.vCut[index] = obj.v;
      this.widthScale[index] = this.widthSegments / this.hCut[index];
      this.heightScale[index] = this.heightSegments / this.vCut[index];
      this.ansArray[index] = 0;
      this.sceneArray[index] = null;
      this.helicopterObjectsArray[index] = [];
      this.hotPointObjectsArray[index] = [];
      for (let i = 0; i < this.vCut[index]; i++) {
        this.sliceArray[index][i] = [];
        this.statuArray[index][i] = [];
        for (let j = 0; j < this.hCut[index]; j++) {
          this.sliceArray[index][i][j] = null;
          this.statuArray[index][i][j] = 0;
        }
      }
      for (let i = 0; i < this.vCut[index]; i++) {
        for (let j = 0; j < this.hCut[index]; j++) {
          // slices[i][j]=this.Imgs[j*vCut+i];
          //因为require它是打包工具所需要的标识，你搞成运行时通过变量去定义的话，它就没办法打包了啊
          //所以不可以写成require("../../public/photo/"+index+".gif"")
          if (i == 0) continue;//没有这句话最上方的贴面显示会异常 原因不详
          this.sliceArray[index][j][i] = imagePath + i + "_" + j + imageSub;

          // console.log(imagePath+i+"_"+j+imageSub);
        }
      }
      //将场景添加到场景切换部分
      // @click="changePanorama('+index+')"
      //初始化数组样式
      this.loadThumb(obj.thumb2, obj.index);
      // this.loadAllSlices(this.sliceArray[obj.index],this.statuArray[obj.index],obj.index,obj.index);
      this.loadSlices(this.sliceArray[this.panoramaIndex], this.statuArray[this.panoramaIndex], this.panoramaIndex, this.panoramaIndex);
      // console.log(obj);
    },
    addSensorByDb (obj) {
      let index = this.sceneIdArray.indexOf(obj.sceneId);
      let pointTexture = new THREE.TextureLoader().load(obj.icon);
      let spriteMaterial = new THREE.SpriteMaterial({ map: pointTexture });
      let sprite = new THREE.Sprite(spriteMaterial);
      sprite.scale.set(0.025, 0.025, 1);
      sprite.position.set(obj.x, obj.y, obj.z);
      this.sceneArray[index].add(sprite);
      sprite.details = { address: obj.address, id: obj.id };
      this.hotPointObjectsArray[index].push(sprite);
    },
    addChangeByDb (obj) {
      let currentIndex = this.sceneIdArray.indexOf(obj.currentSceneId);
      let changeIndex = this.sceneIdArray.indexOf(obj.changeSceneId);
      let pointTexture = new THREE.TextureLoader().load(obj.icon);
      let spriteMaterial = new THREE.SpriteMaterial({ map: pointTexture });
      let sprite = new THREE.Sprite(spriteMaterial);
      sprite.scale.set(0.025, 0.025, 1);
      sprite.position.set(obj.x, obj.y, obj.z);
      this.sceneArray[currentIndex].add(sprite);
      sprite.details = { changeIndex: changeIndex, id: obj.id };
      this.helicopterObjectsArray[currentIndex].push(sprite);
    },
    genID (length) {
      return Number(Math.random().toString().substr(3, length) + Date.now()).toString(36);
    },
    init () {
      //取消右键点击事件
      document.oncontextmenu = function (event) { event.preventDefault(); }
      var container = document.getElementById('container');
      // 创建渲染器
      renderer = new THREE.WebGLRenderer();
      renderer.setPixelRatio(window.devicePixelRatio);
      // // 设置画布的宽高
      // renderer.setSize(this.contain.config.width, this.contain.config.height);
      let contain = jq('#contain');
      // renderer.setSize(window.innerWidth, window.innerHeight);
      renderer.setSize(contain.width(), contain.height());
      console.log(contain.width() + "-" + contain.height());
      // // 判断容器中子元素的长度
      let childs = container.childNodes;
      if (container.childNodes.length > 0) {
        container.removeChild(childs[0]);
        container.appendChild(renderer.domElement);
      } else {
        container.appendChild(renderer.domElement);
      }
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
      // this.creatImgArray();
      // const _this=this;
      // await getLink({fileName:"278eaf8c9818405586cef89f310af29b.jpg"}).then(res=>{
      //   this.thumb=res.data.thumb;
      //   console.log(res);
      // });
      // this.loadThumb(this.Img,0);
      // scene=this.sceneArray[0];
      // this.loadSlices(this.sliceArray[0],this.statuArray[0],0,0);
      // this.loadAllSlices(this.sliceArray[0],this.statuArray[0],0,0);

      // this.loadThumb(this.thumb,1);
      // this.loadSlices(this.sliceArray[1],this.statuArray[1],1,1);
      // this.loadAllSlices(this.sliceArray[1],this.statuArray[1],1,1);

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
      // this.setDrawAvailability();
      this.addMouseEvent();
      // this.showAllSensor();
      // getVisualImage({fileName:"3778417c42f6477cba66443ef20cda50.jpg"}).then(res=>{
      //   console.log(res.data);
      // })
      this.showDB();
      this.test();

    },
    // stats.update();
    // this.painter.loadSlices();
    //窗口大小变换执行事件
    onWindowResize () {
      // 窗口缩放的时候,保证场景也跟着一起缩放
      // camera.aspect = window.innerWidth / window.innerHeight;
      // camera.updateProjectionMatrix();
      // renderer.setSize(window.innerWidth, window.innerHeight);
      let contain = jq('#contain');
      // console.log(contain.width()+"-"+contain.height());
      camera.aspect = contain.width() / contain.height();
      camera.updateProjectionMatrix();
      renderer.setSize(contain.width(), contain.height());
      console.log(contain.width() + "-" + contain.height());
    },
    animate () {
      renderer.render(scene, camera);
      requestAnimationFrame(this.animate);
      // controls.update();
      // controls.handleResize();
      // transformControls.update();
    },
    //添加鼠标事件
    addMouseEvent () {
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
        if (!bMouseDown) return;
        _this.loadSlices(_this.sliceArray[_this.panoramaIndex], _this.statuArray[_this.panoramaIndex], _this.panoramaIndex, _this.panoramaIndex);
        // console.log(_this.panoramaIndex);
        camera.rotateOnWorldAxis(new THREE.Vector3(0, 1, 0), 0.005 * (event.clientX - x));
        _this.rotationY = (_this.rotationY + 0.005 * (event.clientX - x));
        if (_this.rotationY > 0) _this.rotationY = _this.rotationY % (Math.PI * 2);
        else _this.rotationY = -((-_this.rotationY) % (Math.PI * 2));
        // camera.rotateOnWorldAxis(new THREE.Vector3(1,0,0).localToWorld(),0.005 *(event.clientY - y));
        //vector3不是一个3D对象
        camera.rotateX(0.005 * (event.clientY - y));
        // camera.rotateOnWorldAxis(new THREE.Vector3(0,1,0),0.005 *(event.clientX - x));
        // camera.rotateOnWorldAxis(new THREE.Vector3(1,0,0),0.005 *(event.clientX - x));
        // camera.rotateOnWorldAxis(new THREE.Vector3(1,0,0),0.005 *(event.clientY - y));
        if ((_this.rotationY >= 0 && (_this.rotationY < Math.PI / 2 || _this.rotationY > Math.PI * 3 / 2)) || (_this.rotationY < 0 && (_this.rotationY > -Math.PI / 2 || _this.rotationY < -Math.PI * 3 / 2))) {
          if (camera.rotation.x > Math.PI / 2 || camera.rotation.x < -Math.PI / 2) {
            camera.rotateX(-0.005 * (event.clientY - y));
          }
        }
        else {
          if (!((camera.rotation.x > Math.PI / 2 && camera.rotation.x < Math.PI) || (camera.rotation.x < -Math.PI / 2 && camera.rotation.x > -Math.PI))) {
            camera.rotateX(-0.005 * (event.clientY - y));
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
        }
      }
      container.onclick = function (event) {
        event.preventDefault();
        var raycaster = new THREE.Raycaster();
        var mouse = new THREE.Vector2();
        let contain = jq('#contain');
        // console.log((event.clientX-30)+"-"+(event.clientY-33)+"-"+contain.width()+"-"+contain.height());
        mouse.x = ((event.clientX - 30) / contain.width()) * 2 - 1;
        mouse.y = - ((event.clientY - 30) / contain.height()) * 2 + 1;
        raycaster.setFromCamera(mouse, camera);
        var intersects1 = raycaster.intersectObjects(_this.hotPointObjectsArray[_this.panoramaIndex]);
        if (intersects1.length > 0) {
          _this.showDialog(intersects1[0].object.details.address);
        }
        var intersects2 = raycaster.intersectObjects(_this.helicopterObjectsArray[_this.panoramaIndex]);
        if (intersects2.length > 0) {
          // console.log(intersects2[0].object.details);
          _this.changePanorama(intersects2[0].object.details.changeIndex);
        }
      }
      container.oncontextmenu = function (event) {
        event.preventDefault();
        var mouse = new THREE.Vector2();
        let contain = jq('#contain');
        // console.log((event.clientX-30)+"-"+(event.clientY-33)+"-"+contain.width()+"-"+contain.height());
        mouse.x = ((event.clientX - 30) / contain.width()) * 2 - 1;
        mouse.y = - ((event.clientY - 30) / contain.height()) * 2 + 1;
        let vector = new THREE.Vector3(mouse.x, mouse.y, 0);
        //从屏幕向量转为3d空间向量
        vector.unproject(camera);
        // console.log(vector.x+"|"+vector.y+"|"+vector.z+"|"+_this.panoramaIndex);
        if (_this.addSensorStatus) {
          _this.addSensorStatus = false;
          let vector = new THREE.Vector3(mouse.x, mouse.y, 0);
          //从屏幕向量转为3d空间向量
          // console.log(vector);
          vector.unproject(camera);
          // id,x,y,z,sceneId,sensorDes
          // _this.addSensorOnDb(_this.panoramaIndex,vector.x,vector.y,vector.z,_this.form.sensorType,_this.form.sensorMes);
          // _this.maxId=_this.maxId+1;
          // _this.addSensor(_this.maxId,_this.panoramaIndex,vector.x,vector.y,vector.z,_this.form.sensorType,_this.form.sensorMes);
          _this.addSensor(vector.x, vector.y, vector.z, _this.panoramaIndex);

          // test1({}).then(res => {
          // for(var sensor of res){
          // console.log(sensor);
          // }
          // })
          // _this.form.sensorType=null;
          // _this.form.sensorMes=null;
        }
        else if (_this.addChangeStatus) {
          _this.addSensorStatus = false;
          let vector = new THREE.Vector3(mouse.x, mouse.y, 0);
          vector.unproject(camera);
          _this.addSceneChange(vector.x, vector.y, vector.z, _this.panoramaIndex, _this.addChangeTable.targetScene);
        }
        else if (_this.deleteSensorStatus) {
          _this.deleteSensorStatus = !_this.deleteSensorStatus;
          var raycaster = new THREE.Raycaster();
          raycaster.setFromCamera(mouse, camera);
          var intersects = raycaster.intersectObjects(_this.hotPointObjectsArray[_this.panoramaIndex]);
          if (intersects.length > 0) {
            test3(intersects[0].object.details.id).then(res => {
            });
          }
          scene.remove(intersects[0].object);
        }
      }
    },
    //加载缩略图
    loadThumb (url, sphereIndex) {
      //创建分场景；
      this.sceneArray[sphereIndex] = new THREE.Scene();
      let loader = new THREE.TextureLoader();
      loader.crossOrigin = '*';
      // console.log("缩略图");
      // console.log(this.thumbs);
      // this.vCut[sphereIndex]=this.thumbs[sphereIndex].v;
      // this.hCut[sphereIndex]=this.thumbs[sphereIndex].h;
      const texture = loader.load(url);
      texture.minFilter = THREE.LinearFilter;
      texture.magFilter = THREE.LinearFilter;
      const geometry = new THREE.SphereGeometry(100, this.widthSegments, this.heightSegments);
      // materials = [new THREE.MeshBasicMaterial({map: texture})];
      var materials = [new THREE.MeshBasicMaterial({ map: texture })];

      geometry.groupsNeedUpdate = true;
      geometry.uvsNeedUpdate = true;
      //使贴图反转,正常贴图从物体外面看正常，现在反转后在物体里看贴图正常，更改xyz次序可能导致视野判断失效
      geometry.scale(-1, 1, 1);
      const sphere = new THREE.Mesh(geometry, materials);
      // this.widthSegments = widthSegments;
      // this.widthScale = widthSegments/this.hCut[sphereIndex];
      // this.heightSegments = heightSegments;
      // this.heightScale = heightSegments/this.vCut[sphereIndex];
      this.sphereArray[sphereIndex] = sphere;
      // console.log("sphere.geometry.faces.length"+"-"+sphere.geometry.faces.length)
      // console.log(this.widthSegments+"-"+this.heightSegments+"-"+this.widthScale+"-"+this.heightScale);
      for (let i = 0, l = sphere.geometry.faces.length; i < l; i++) {
        // l是总共的三角面数
        // 每一个三角面对应的图片索引
        imgIndex[sphereIndex][i] = this.panoramaProcess.transIndex(i, this.widthSegments, this.heightSegments, this.widthScale[sphereIndex], this.heightScale[sphereIndex], this.hCut[sphereIndex]);
      }
      this.sceneArray[sphereIndex].add(sphere);
      // console.log(imgIndex[sphereIndex]);
      // imgIndex[3]=[];
      // for (let i = 0, l = sphere.geometry.faces.length; i < l; i++) {
      //   // l是总共的三角面数
      //   // 每一个三角面对应的图片索引
      //   imgIndex[3][i] = this.transIndex(i, 200, 200, 10, 10);
      // }
      // console.log("test"+imgIndex[3]);
    },
    //加载清晰图
    loadSlices (slices, status, ansIndex, sphereIndex) {
      //判断如果全部的碎片图都加载过一次就不再加载
      // vCut=this.thumbs[sphereIndex].v;
      // hCut=this.thumbs[sphereIndex].h;
      // this.widthSegments = widthSegments;
      // this.widthScale = widthSegments/this.hCut[sphereIndex];
      // this.heightSegments = heightSegments;
      // this.heightScale = heightSegments/this.vCut[sphereIndex];
      // console.log(sphereIndex+"loadSlices-"+vCut+hCut);
      if (this.ansArray[ansIndex] === this.hCut[sphereIndex] * this.vCut[sphereIndex]) return;
      // console.log(this.sphere.material);
      const urls = slices;
      const row = urls.length;
      const col = urls[0].length;
      // 渲染
      for (let i = 0; i < row; i++) {
        for (let j = 0; j < col; j++) {
          const index = i * col + j + 1;
          if (status[i][j] === 0) {
            const isInSight = this.panoramaProcess.isInSight(i, j, camera, sphereIndex, this.vCut[sphereIndex], this.hCut[sphereIndex]);
            // console.log(isInSight);
            if (isInSight) {
              // console.log(urls[i][j]);
              this.panoramaProcess.drawSlice(index, urls[i][j], this.sphereArray[sphereIndex], imgIndex[sphereIndex], this.widthScale[sphereIndex], this.heightScale[sphereIndex]);
              status[i][j] = 1;
              this.ansArray[ansIndex]++;
              //  this.sliceMap[`${i}-${j}`] = 1;
              // this.complate = this.checkComplate();
            }
          }
        }
      }
    },
    loadAllSlices (slices, status, ansIndex, sphereIndex) {
      //判断如果全部的碎片图都加载过一次就不再加载
      // vCut=this.thumbs[sphereIndex].v;
      // hCut=this.thumbs[sphereIndex].h;
      // this.widthSegments = widthSegments;
      // this.widthScale = widthSegments/this.hCut[sphereIndex];
      // this.heightSegments = heightSegments;
      // this.heightScale = heightSegments/this.vCut[sphereIndex];
      // console.log(sphereIndex+"loadSlices-"+vCut+hCut);
      if (this.ansArray[ansIndex] === this.hCut[sphereIndex] * this.vCut[sphereIndex]) return;
      // console.log(this.sphere.material);
      const urls = slices;
      const row = urls.length;
      const col = urls[0].length;
      // 渲染
      for (let i = 0; i < row; i++) {
        for (let j = 0; j < col; j++) {
          const index = i * col + j + 1;
          if (status[i][j] === 0) {
            const isInSight = this.panoramaProcess.isInSight(i, j, camera, sphereIndex, this.vCut[sphereIndex], this.hCut[sphereIndex]);
            // console.log(isInSight);
            // console.log(urls[i][j]);
            this.panoramaProcess.drawSlice(index, urls[i][j], this.sphereArray[sphereIndex], imgIndex[sphereIndex], this.widthScale[sphereIndex], this.heightScale[sphereIndex]);
            status[i][j] = 1;
            this.ansArray[ansIndex]++;
            //  this.sliceMap[`${i}-${j}`] = 1;
            // this.complate = this.checkComplate();
          }
        }
      }
    },
    changePanorama (index) {
      if (this.ansArray[index] == this.hCut[index] * this.vCut[index]) {
        scene = this.sceneArray[index];
        this.panoramaIndex = index;
        return;
      }
      this.loadSlices(this.sliceArray[index], this.statuArray[index], index, index);
      this.loadAllSlices(this.sliceArray[index], this.statuArray[index], index, index);
      // this.loadAllSlices(this.sliceArray[index],this.statuArray[index],index,index);
      this.panoramaIndex = index;
      scene = this.sceneArray[index];
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
    //场景相关函数
    getWaitScene () {
      this.dialogAddSceneVisible = true
      listVisualImage(this.queryParams).then(response => {
        // console.log(response.rows);
        // console.log(response.rows[0]);
        this.waitScene = response.rows;
        // console.log(this.waitScene)
        // console.log("在上面");
      });
    },
    async addSceneBefore () {
      // sceneMes:
      // {
      //   originalFile:"",
      //   originalFileFolder:"",
      //   thumb:""
      // }
      // var imagePath=this.addSceneTable.imagePath;
      if (this.addSceneTable == null) {
        this.$message({
          message: '添加场景不能为空！',
          type: 'warning'
        });
        return;
      }
      var imagePath = this.addSceneTable.newName;
      // console.log(this.addSceneTable);
      await getLink({ fileName: imagePath }).then(res => {
        this.selectedSceneUrl = res.data;
        // this.thumbs.push({index:this.sceneIdArray.length,thumb:this.selectedSceneUrl.thumb,h:this.addSceneTable.height,v:this.addSceneTable.width,disabled:false,newName:this.addSceneTable.originName});
      });

      //大图片的缩略图无法显示问题
      // this.thumbs.push({index:2,thumb:this.sceneMes.thumb});
      this.addScene();
      this.dialogAddSceneVisible = false;
      // thumbs:[
      //   {index :0 ,thumb:require("../../../public/thumb/thumb(1).jpg")},
      //   {index :1,thumb:require("../../../public/thumb/thumb(5).jpg")}
      // ],
    },
    addScene () {
      // 添加场景信息到数组
      // const imagePath=this.addSceneTable.imagePath;
      // const thumbPath=this.addSceneTable.thumbPath;
      const imagePath = this.selectedSceneUrl.subRoot;
      const index = this.sceneIdArray.length;
      // console.log(index);
      const imageSub = this.selectedSceneUrl.original.substr(this.selectedSceneUrl.original.length - 4, 4);
      // console.log(this.addSceneTable.height+"-"+this.addSceneTable.width)
      // console.log(vCut+"-"+hCut);
      // console.log("图片路径"+imageSub);
      this.sliceArray[index] = [];
      this.statuArray[index] = [];
      imgIndex[index] = [];
      this.hCut[index] = this.addSceneTable.height;
      this.vCut[index] = this.addSceneTable.width;
      this.widthScale[index] = this.widthSegments / this.hCut[index];
      this.heightScale[index] = this.heightSegments / this.vCut[index];
      this.ansArray[index] = 0;
      this.sceneArray[index] = null;
      this.helicopterObjectsArray[index] = [];
      this.hotPointObjectsArray[index] = [];
      for (let i = 0; i < this.vCut[index]; i++) {
        this.sliceArray[index][i] = [];
        this.statuArray[index][i] = [];
        for (let j = 0; j < this.hCut[index]; j++) {
          this.sliceArray[index][i][j] = null;
          this.statuArray[index][i][j] = 0;
        }
      }
      for (let i = 0; i < this.vCut[index]; i++) {
        for (let j = 0; j < this.hCut[index]; j++) {
          // slices[i][j]=this.Imgs[j*vCut+i];
          //因为require它是打包工具所需要的标识，你搞成运行时通过变量去定义的话，它就没办法打包了啊
          //所以不可以写成require("../../public/photo/"+index+".gif"")
          if (i == 0) continue;
          this.sliceArray[index][j][i] = imagePath + i + "_" + j + imageSub;
        }
      }
      //将场景添加到场景切换部分
      // @click="changePanorama('+index+')"
      //初始化数组样式
      this.loadThumb(this.selectedSceneUrl.thumb2, index);
      this.loadSlices(this.sliceArray[index], this.statuArray[index], index, index);
      // if(index==0){scene = this.sceneArray[0];}
      const id = this.genID(36);
      let obj = {
        id: id,
        type: "scene",
        h: this.hCut[index],
        v: this.vCut[index],
        originalFile: this.selectedSceneUrl.original,
        originalFileFolder: this.selectedSceneUrl.subRoot,
        thumb: this.selectedSceneUrl.thumb,
        thumb2: this.selectedSceneUrl.thumb2,
        newName: this.addSceneTable.originName,
        showName: null,//这里可以改用户看到的场景名称
        index: index,
      }
      this.message.push(obj);
      this.thumbs.push(obj);
      this.idToIndex[id] = index;
      this.indexToId[index] = id;
      this.sceneIdArray.push(id);
      // console.log(this.sceneIdArray);
      this.sceneAns++;
      this.addSceneTable = null;
    },
    deleteScene (index) {
      // console.log(index);
      // console.log(this.thumbs)
      // this.thumbs.splice(index,1);
      // this.sceneIdArray.splice(index,1);
      for (let i = 0; i < this.thumbs.length; i++) {
        if (this.thumbs[i].id == this.sceneIdArray[index]) {
          this.thumbs.splice(i, 1);
          break;
        }

      }

      for (let i = 0; i < this.message.length; i++) {
        // console.log(this.message[i]);
        if (this.message[i].type === "scene" && this.message[i].id === this.sceneIdArray[index]) {
          // console.log(i);
          this.message.splice(i, 1)
          i = i - 1;
        }
        else if (this.message[i].type === "sensor" && this.message[i].sceneId === this.sceneIdArray[index]) {
          this.deleteObj(this.message[i].id);
          i = i - 1;
        }
        else if (this.message[i].type === "change") {
          this.deleteObj(this.message[i].id);
          i = i - 1;
        }
      }
      this.sceneAns--;

      // console.log("this.idToIndex");
      // console.log(this.idToIndex);
      // console.log(this.indexToId);
    },
    judgeAddEditSensor () {
      for (let i = 0; i < this.message.length; i++) {
        // console.log(_this.message[i]);
        if (this.message[i].type == "sensor" && this.message[i].id === this.editId) {
          this.editSensor(i);
          return;
        }
      }
      this.addSensorBefore();
    },
    //添加传感器的两个函数
    addSensorBefore () {
      if (this.iconType == null || (this.iconType === "系统图标" && this.addSensorTable.iconPath == null) || (this.iconType === "自定义上传" && typeof (this.iconform.newName) === "undefined")) {
        this.$message({
          message: '添加传感器图标不能为空！',
          type: 'warning'
        });
        return;
      }
      this.addChangeStatus = false; //防止添加传感器和添加场景切换功能冲突
      this.dialogAddSensorVisible = false;
      this.addSensorStatus = true;
    },
    addSensor (x, y, z, index) {
      let iconPath;
      if (this.iconType === "系统图标") {
        iconPath = this.addSensorTable.iconPath;
      }
      else {
        iconPath = this.iconFolder + this.iconform.newName;
      }
      let pointTexture = new THREE.TextureLoader().load(iconPath)
      let spriteMaterial = new THREE.SpriteMaterial({ map: pointTexture });
      let sprite = new THREE.Sprite(spriteMaterial);
      let id = this.genID();
      sprite.scale.set(0.025, 0.025, 1);
      sprite.position.set(x, y, z);
      this.sceneArray[index].add(sprite);
      let obj = { id: id, type: "sensor", x: x, y: y, z: z, icon: iconPath, sceneId: this.sceneIdArray[index], title: this.addSensorTable.title, address: this.addSensorTable.address };
      sprite.details = { address: this.addSensorTable.address, id: id };
      this.hotPointObjectsArray[index].push(sprite);
      this.message.push(obj);
      this.hotPoints.push(obj);
      this.addSensorTable.title = null;
      this.addSensorTable.iconPath = null;
      if (this.iconType === "系统图标") {
        this.changeSelection(null);
      }
      this.iconType = null;
    },
    editObjBefore (id) {
      for (let i = 0; i < this.message.length; i++) {
        if (this.message[i].type != "scene" && this.message[i].id === id) {
          this.editId = id;
          if (this.message[i].type == "sensor") {
            this.addSensorTable.title = this.message[i].title;
            this.addSensorTable.address = this.message[i].address;
            this.dialogAddSensorVisible = true;
          }
          else {
            this.addChangeTable.title = this.message[i].title;
            // this.addChangeTable.targetScene=this.message[i].;
            /*              for(let j=0;j<this.thumbs.length;j++)
                          {
                            if(this.thumbs[j].id==this.message[i].changeSceneId)
                            {
                              // console.log("找到场景");
                              this.addChangeTable.targetScene=this.thumbs[j].newName.substring(0,this.thumbs[j].newName.length-4);//这里有问题
                              break;
                            }
                          }*/
            this.dialogAddChangeVisible = true;
          }
          return;
        }
      }
    },
    editSensor (sub) {
      //判断信息是否合法
      if (this.iconType == null || (this.iconType === "系统图标" && this.addSensorTable.iconPath == null) || (this.iconType === "自定义上传" && typeof (this.iconform.newName) === "undefined")) {
        this.$message({
          message: '添加传感器图标不能为空！',
          type: 'warning'
        });
        return;
      }
      //删除场景中的sprite贴面
      const id = this.editId;
      for (let obj of scene.children) {
        if (obj.isSprite && obj.details.id === id) {
          scene.remove(obj);
        }
      }
      let iconPath;
      if (this.iconType === "系统图标") { iconPath = this.addSensorTable.iconPath; }
      else { iconPath = this.iconFolder + this.iconform.newName; }
      let pointTexture = new THREE.TextureLoader().load(iconPath)
      let spriteMaterial = new THREE.SpriteMaterial({ map: pointTexture });
      let sprite = new THREE.Sprite(spriteMaterial);
      this.sceneArray[this.panoramaIndex].add(sprite);
      sprite.scale.set(0.025, 0.025, 1);
      sprite.position.set(this.message[sub].x, this.message[sub].y, this.message[sub].z);
      sprite.details = { address: this.addSensorTable.address, id: id };
      //修改信息
      this.message[sub].title = this.addSensorTable.title;
      this.message[sub].icon = this.addSensorTable.iconPath;
      this.message[sub].address = this.addSensorTable.address;
      for (let i = 0; i < this.hotPoints.length; i++) {
        if (this.hotPoints[i].id === id) {
          this.hotPoints[i].title = this.addSensorTable.title;
          this.hotPoints[i].icon = this.addSensorTable.iconPath;
          this.hotPoints[i].address = this.addSensorTable.address;
        }
      }
      this.addSensorTable.title = null;
      this.addSensorTable.iconPath = null;
      this.addSensorTable.address = null;
      this.iconType = null;
      this.dialogAddSensorVisible = false;
    },
    //打开场景切换信息窗前先禁用当前场景的选项
    openChangeBefore () {
      // console.log(this.thumbs);
      this.thumbs[this.panoramaIndex].disabled = true;
    },
    //关闭场景切换信息窗后恢复当前场景的选项，保证下次在其他场景开启不受影响
    closeChangeAfter () {
      this.thumbs[this.panoramaIndex].disabled = false;
    },
    judgeAddEditChange () {
      for (let i = 0; i < this.message.length; i++) {
        // console.log(_this.message[i]);
        if (this.message[i].type == "change" && this.message[i].id === this.editId) {
          this.editSceneChange(i);
          return;
        }
      }
      this.addChangeBefore();
    },
    addChangeBefore () {
      if (this.addChangeTable.targetScene == null) {
        this.$message({
          message: '切换的目的场景不能为空！',
          type: 'warning'
        });
        return;
      }
      else if (this.iconType == null || (this.iconType === "系统图标" && this.addChangeTable.iconPath == null) || (this.iconType === "自定义上传" && typeof (this.iconform.newName) === "undefined")) {
        this.$message({
          message: '切换场景图标不能为空！',
          type: 'warning'
        });
        return;
      }
      this.addSensorStatus = false; //防止添加传感器和添加场景切换功能冲突
      this.dialogAddChangeVisible = false;
      this.addChangeStatus = true;
    },
    //添加场景切换
    addSceneChange (x, y, z, currentIndex, changeIndex) {
      let iconPath;
      if (this.iconType === "系统图标") {
        iconPath = this.addChangeTable.iconPath;
      }
      else {
        iconPath = this.iconFolder + this.iconform.newName;
      }
      let pointTexture = new THREE.TextureLoader().load(iconPath);
      let spriteMaterial = new THREE.SpriteMaterial({ map: pointTexture });
      let sprite = new THREE.Sprite(spriteMaterial);
      let id = this.genID();
      sprite.scale.set(0.025, 0.025, 1);
      sprite.position.set(x, y, z);
      this.sceneArray[currentIndex].add(sprite);
      sprite.details = { changeIndex: changeIndex, id: id };
      this.helicopterObjectsArray[currentIndex].push(sprite);
      let obj = {
        id: id,
        type: "change",
        x: x,
        y: y,
        z: z,
        icon: iconPath,
        currentSceneId: this.sceneIdArray[currentIndex],
        changeSceneId: this.sceneIdArray[changeIndex],
        title: this.addChangeTable.title
      };
      this.message.push(obj);
      // console.log(this.message);
      this.hotPoints.push(obj);
      // console.log(this.hotPoints);
      this.addChangeTable.targetScene = null;
      this.addChangeTable.iconPath = null;
      if (this.iconType === "系统图标") {
        this.changeSelection(null);
      }
      this.iconType = null;
    },
    //修改传感器或场景切换
    editSceneChange (sub) {
      //判断填入场景信息是否合法
      if (this.addChangeTable.targetScene == null) {
        this.$message({
          message: '切换的目的场景不能为空！',
          type: 'warning'
        });
        return;
      }
      else if (this.iconType == null || (this.iconType === "系统图标" && this.addChangeTable.iconPath == null) || (this.iconType === "自定义上传" && typeof (this.iconform.newName) === "undefined")) {
        this.$message({
          message: '切换场景图标不能为空！',
          type: 'warning'
        });
        return;
      }
      //删除场景中的sprite贴面
      const id = this.editId;
      for (let obj of scene.children) {
        if (obj.isSprite && obj.details.id === id) {
          scene.remove(obj);
        }
      }
      //添加修改后的图标
      let iconPath;
      if (this.iconType === "系统图标") { iconPath = this.addChangeTable.iconPath; }
      else { iconPath = this.iconFolder + this.iconform.newName; }
      let pointTexture = new THREE.TextureLoader().load(iconPath);
      let spriteMaterial = new THREE.SpriteMaterial({ map: pointTexture });
      let sprite = new THREE.Sprite(spriteMaterial);
      sprite.scale.set(0.025, 0.025, 1);
      sprite.position.set(this.message[sub].x, this.message[sub].y, this.message[sub].z);
      this.sceneArray[this.panoramaIndex].add(sprite);
      sprite.details = { changeIndex: this.addChangeTable.targetScene, id: id };
      //修改信息
      this.message[sub].title = this.addChangeTable.title;
      this.message[sub].changeSceneId = this.sceneIdArray[this.addChangeTable.targetScene];
      this.message[sub].icon = this.addChangeTable.iconPath;
      for (let i = 0; i < this.hotPoints.length; i++) {
        if (this.hotPoints[i].id === id) {
          this.hotPoints[i].title = this.addChangeTable.title;
          this.hotPoints[i].changeSceneId = this.sceneIdArray[this.addChangeTable.targetScene];
          this.hotPoints[i].icon = this.addChangeTable.iconPath;
        }
      }
      this.addChangeTable.title = null;
      this.addChangeTable.iconPath = null;
      this.iconType = null;
      this.addChangeTable.targetScene = null;
      this.dialogAddChangeVisible = false;
    },
    //删除传感器或场景切换
    deleteObj (id) {
      //遍历 判断类型是否为sprite 通过设置spriteid来删除
      // console.log(scene.children);
      // scene.remove(scene.getObjectById());
      for (let obj of scene.children) {
        if (obj.isSprite && obj.details.id === id) {
          scene.remove(obj);
        }
      }
      for (let i = 0; i < this.hotPoints.length; i++) {
        if (this.hotPoints[i].id === id) {
          this.hotPoints.splice(i, 1);
        }
      }
      for (let i = 0; i < this.message.length; i++) {
        if (this.message[i].type != "scene" && this.message[i].id === id) {
          // console.log(i);
          this.message.splice(i, 1)
        }
      }


      // scene.remove(THREE.Sprite);
    },
    //判断是否是当前场景下的热点
    isCurrentHotpoint (hotPoint) {
      // console.log(hotPoint.id)
      if (hotPoint.type === "sensor" && this.sceneIdArray.indexOf(hotPoint.sceneId) === this.panoramaIndex)
        return true;
      else if (hotPoint.type === "change" && this.sceneIdArray.indexOf(hotPoint.currentSceneId) === this.panoramaIndex)
        return true;
      else return false;
    },

    //   deleteSensorBefore(){
    //     this.deleteSensorStatus=!this.deleteSensorStatus;
    //   },
    //   sceneBarShow(){
    //     if (!this.sceneChangeStatus) {
    //         jq('#snece-container').css({"display":"block"});
    //     } else {
    //         jq('#snece-container').css({"display":"none"});
    //     }
    //     this.sceneChangeStatus = !this.sceneChangeStatus;
    // },
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
    // sceneRotation(){
    //   if (!this.rotationStatus) {
    //     this.rotation=window.setInterval(function(){ camera.rotateOnWorldAxis(new THREE.Vector3(0,1,0),0.001);},10);
    //   } else {
    //     clearTimeout(this.rotation);
    //   }
    //   this.rotationStatus = !this.rotationStatus;
    // },
    // musicPlay(){
    //   if (!this.musicStatus) {
    //     this.music = new Audio(this.musicPath);
    //     this.music.play();
    //   } else {
    //     this.music.pause();
    //   }
    //   this.musicStatus = !this.musicStatus;
    // },
    // fullScreen(){
    //   console.log(document.body.clientWidth+"|"+document.body.clientHeight);
    //   console.log(window.screen.width+"|"+window.screen.height);
    //   let main = $('.app-main');
    //   if (!this.screenStatus) {
    //     if (main.requestFullscreen) {
    //       main.requestFullscreen()
    //     } else if (main.mozRequestFullScreen) {
    //       main.mozRequestFullScreen()
    //     } else if (main.webkitRequestFullScreen) {
    //       main.webkitRequestFullScreen()
    //     } else if (main.msRequestFullscreen) {
    //       main.msRequestFullscreen()
    //     }
    //   } else {
    //     if (document.exitFullscreen) {
    //       document.exitFullscreen()
    //     } else if (document.mozCancelFullScreen) {
    //       document.mozCancelFullScreen()
    //     } else if (document.webkitCancelFullScreen) {
    //       document.webkitCancelFullScreen()
    //     } else if (document.msExitFullscreen) {
    //       document.msExitFullscreen()
    //     }
    //   }
    //   this.screenStatus = !this.screenStatus;
    // },
    // sneceChange(){
    //   console.log($('#panoramaShow'));
    //   if (!this.sceneChangeStatus) {
    //      jq('#panoramaShow')[0].classList.add("snece-container");
    //   } else {
    //      jq('#panoramaShow')[0].classList.remove("snece-container");
    //   }
    //   this.sceneChangeStatus = !this.sceneChangeStatus;
    // },


    //计算贴面以及判断是否可视范围的各种计算
    drawSlice (index, url, sphereIndex) {
      // console.log(index+"-"+sphereIndex)
      const _this = this;
      const sphere = this.sphereArray[sphereIndex];
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
        texture.minFilter = THREE.LinearFilter;
        texture.magFilter = THREE.LinearFilter;
        sphere.material[index] = new THREE.MeshBasicMaterial({
          map: texture
        });
        _this.updateSliceView(index, sphereIndex);
        sphere.geometry.elementsNeedUpdate = true;// 加入这个才能实现实时更新
      });
    },
    // 更新三角面uv映射
    updateSliceView (index, sphereIndex) {
      const sphere = this.sphereArray[sphereIndex];
      let sliceIndex = 0;
      const { widthSegments, heightSegments } = this;
      const widthScale = this.widthScale[sphereIndex];
      const heightScale = this.heightScale[sphereIndex];
      for (let i = 0, l = sphere.geometry.faces.length; i < l; i++) {
        // l是总共的三角面数
        // 每一个三角面对应的图片索引
        if (imgIndex[sphereIndex][i] === index) {
          // console.log(index);
          sliceIndex++;
          const uvs = this.getVertexUvs(sliceIndex, widthScale, heightScale);
          if (i >= widthSegments * 2 * heightSegments - 3 * widthSegments || i < widthSegments) {
            sphere.geometry.faces[i].materialIndex = index;
            sphere.geometry.faceVertexUvs[0][i][0].set(...uvs[0].a);
            sphere.geometry.faceVertexUvs[0][i][1].set(...uvs[0].b);
            sphere.geometry.faceVertexUvs[0][i][2].set(...uvs[0].c);
          } else {
            sphere.geometry.faces[i].materialIndex = index;
            sphere.geometry.faces[i + 1].materialIndex = index;
            sphere.geometry.faceVertexUvs[0][i][0].set(...uvs[0].a);
            sphere.geometry.faceVertexUvs[0][i][1].set(...uvs[0].b);
            sphere.geometry.faceVertexUvs[0][i][2].set(...uvs[0].c);
            sphere.geometry.faceVertexUvs[0][i + 1][0].set(...uvs[1].a);
            sphere.geometry.faceVertexUvs[0][i + 1][1].set(...uvs[1].b);
            sphere.geometry.faceVertexUvs[0][i + 1][2].set(...uvs[1].c);
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
    transIndex (i, widthSegments, heightSegments, widthScale, heightScale, sphereIndex) {
      let row, col, imgIndex;
      // 第一行
      if (i < widthSegments) {
        row = 1;
        col = i + 1;
      } else if (i < 3 * widthSegments) {
        // 第二行
        row = parseInt((i + widthSegments) / (2 * widthSegments)) + 1;
        col = parseInt((i - (row - 1) * widthSegments) / 2) + 1;
      } else if (i < widthSegments + 2 * widthSegments * (heightSegments - 2)) {
        row = parseInt((i - widthSegments) / (2 * widthSegments)) + 2;
        col = parseInt((i - (row - 2) * 2 * widthSegments - widthSegments) / 2) + 1;
      } else {
        // 最后一行
        row = parseInt((i - widthSegments) / (2 * widthSegments)) + 2;
        col = parseInt(i - (row - 2) * 2 * widthSegments - widthSegments) + 1;
      }
      row = Math.ceil(row / heightScale);
      col = Math.ceil(col / widthScale);
      imgIndex = (col - 1) * this.hCut[sphereIndex] + row;
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
    getVertexUvs (index, widthScale, heightScale) {
      // 两个三角面组成的矩形的四个顶点坐标
      const vectors = [
        [((index - 1) % widthScale + 1) / widthScale, 1 - (parseInt((index - 1) / widthScale) % heightScale) / heightScale],
        [((index - 1) % widthScale) / widthScale, 1 - (parseInt((index - 1) / widthScale) % heightScale) / heightScale],
        [((index - 1) % widthScale) / widthScale, 1 - (parseInt((index - 1) / widthScale) % heightScale + 1) / heightScale],
        [((index - 1) % widthScale + 1) / widthScale, 1 - (parseInt((index - 1) / widthScale) % heightScale + 1) / heightScale]
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
    isInSight (row, col, camera, sphereIndex) {
      // 球体半径
      const vCut = this.vCut[sphereIndex];
      const hCut = this.hCut[sphereIndex];
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
    /**
   * @description 判断一个物体是否与相机视锥体相交
   * @param {any} obj 要判断的物体
   * @param {any} camera 相机
   * @returns boolean
   */
    isOffScreen (obj, camera) {
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

#snece-container {
  position: absolute;
  bottom: 7rem;
  border-radius: 0px;
  border: 15px solid rgba(39, 39, 39, 0.25);
  background: rgba(39, 39, 39, 0.25);
  box-shadow: 0 3px 14px rgb(128 128 128 / 50%);
  width: 100%;
  height: 130px;
  background-repeat: repeat-x;
}

.thum {
  display: inline;
  margin: 0 0 50px 50px;
  cursor: pointer; /*  鼠标放在上面为手指*/
  user-select: none; /* 文字不可 */
}

#leftbottom_menu {
  left: 0.5rem;
  bottom: 0;
}
#rightSideMenu {
  right: 1rem;
  top: 2rem;
}
#rightSideMenu > div {
  margin-top: 0.5rem;
  position: relative;
}
.h5Dom {
  position: absolute;
  z-index: 10;
  cursor: pointer; /*  鼠标放在上面为手指*/
  user-select: none; /* 文字不可 */
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

.el-header,
.el-footer {
  background-color: #b3c0d1;
  color: #333;
  text-align: center;
  padding: 0px;
}

.el-aside {
  background-color: #d3dce6;
  color: #333;
  text-align: center;
  padding: 0px;
  margin-bottom: 0px;
}

.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  padding: 0px;
}
.option_box {
  display: flex;
  align-items: center;
}

.option_img {
  width: 25px;
  height: 25px;
  margin-right: 7px;
}
/*图片上传*/
.avatar-uploader .el-upload {
  border: 1px dashed #000000;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #ff0000;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
/*上传图标样式*/
.avatar-uploader .el-upload {
  border: 1px dashed #000000;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #ff0000;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
