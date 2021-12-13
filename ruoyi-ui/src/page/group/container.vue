<template>
  <div class="middle">
    <div class="wrapper__grade"
         @mousedown="contain.handleMouseDown"></div>
    <div id="wrapper"
         class="wrapper"
         @mousedown="contain.handleMouseDown">
      <div class="content"
           id="content"
           ref="content">
        <div class="container"
             :style="styleName"
             id="container"
             ref="container">
          <showPanoramaInScreen  v-if="handleShowPanorama()" ref="showThumbs">

          </showPanoramaInScreen>
          <myDialog></myDialog>
          <div id="snece-container" v-if="show()">
            <div style="text-align:center">
              <div style="display:inline-block;width: 100px; height: 100px;margin-right: 20px;" v-for="(thumb,index) in thumbs" :key="index">
                <!-- 这里index为什么不报错？ -->
                <el-image
                  @click="handleChangePanorama(thumb.index)"
                  style="cursor: pointer;"
                  :src="thumb.thumb"
                  :fit="fits[0]">
                </el-image>
              </div>
            </div>
          </div>
          <div class="grade"
               v-if="gradeFlag || contain.config.gradeShow"
               :style="gradeLenStyle"></div>
          <subgroup ref="subgroup"
                    :nav="contain.list"></subgroup>

        </div>
      </div>

    </div>
  </div>
</template>

<script>
import subgroup from './subgroup'
import common from '@/config'
import { getObj } from '@/api/visual'
import jq from 'jquery';
import showPanoramaInScreen from "@/page/group/showPanoramaInScreen";
import {getPanorama} from "@/api/system/panorama";
import myDialog from "@/page/group/myDialog";
export default {
  name: 'contents',
  inject: ["contain"],
  provide () {
    return {
      contain: this.contain,
      container: this
    };
  },
  components: {
    subgroup,
    showPanoramaInScreen,
    myDialog
  },
  props: ['panoramaId'],
  data () {
    return {
      selectCount: {},
      scale: 1,
      gradeFlag: false,
      dialog_visible:false,
      panorama_visible:false,
      thumbs:[],
      fits: ['fill', 'contain', 'cover', 'none', 'scale-down'],
      ans:0,
    }
  },
  computed: {
    stepScale () {
      let scale = Number(((1 / this.scale) * 100).toFixed(2));
      return scale
    },
    //计算中央可视化大屏比例
    styleName () {
      const scale = this.contain.config.scale;
      return Object.assign({
        transform: `scale(${scale / 100}, ${scale / 100})`,
        width: this.setPx(this.contain.config.width),
        height: this.setPx(this.contain.config.height),
        backgroundColor: this.contain.config.backgroundColor,
          'transform-origin': '0 0',
        },
      //  (() => {
      //   if (this.contain.config.backgroundImage) {
      //     return {
      //       background: `url(${this.contain.config.backgroundImage}) 0% 0% / 100% 100% rgb(3, 12, 59)`,
      //     }
      //   }
      //   return
      // })()
      )
    },
    gradeLenStyle () {
      return {
        backgroundSize: `${this.setPx(this.contain.config.gradeLen)} ${this.setPx(this.contain.config.gradeLen)},${this.setPx(this.contain.config.gradeLen)} ${this.setPx(this.contain.config.gradeLen)}`
      }
    }
  },
  mounted () {
    this.showThumbs();
    this.initData();
    this.initFun();
    // this.hidePanorama();

  },
  methods: {

    handleChangePanorama(index){
      // console.log(index);
      this.$refs.showThumbs.changePanorama(index);
    },
    show(){
      const id = this.$route.params.panoramaId;
      if(id==="null") return false;
      return true;
    },

    async showThumbs(){
      const id = this.$route.params.panoramaId;
      if(id==="null") return false;
      // console.log(id);
      await getPanorama(id).then(response => {
        // console.log(response);
        this.formDB=response.data;
      });
      this.message=JSON.parse(this.formDB.component) || [];
      var objects=JSON.parse(this.formDB.component) || [];
      // console.log("json文件");
      // console.log(objects);
      // 先遍历场景，再遍历传感器和场景变换
      for(var obj of objects) {
        if (obj.type === "scene") {
          // console.log("这这里");
          obj["index"] = this.ans;
          this.ans++;
          // console.log(obj);
          this.thumbs.push(obj);

        }
      }
    },
    initFun () {
      ['handleRefresh', 'handleGetObj'].forEach(ele => {
        // console.log('66666');
        this[ele] = this.$refs.subgroup[ele]
      });
    },
    hidePanorama(){
        // 原来是block
        let canvas = jq('canvas');
        // console.log(canvas);
        canvas.css({"display":"none"});
      },
    //初始化数据
    initData () {

      const id = this.$route.params.id;
      this.contain.id = id;
      this.contain.contentWidth = this.$refs.content.offsetWidth;
      const isBuild = this.$route.name === 'build';
      const width = isBuild ? this.contain.contentWidth : document.body.clientWidth
      if (id) {
        const loading = this.$loading({
          lock: true,
          text: '正在加载中，请稍后',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        getObj(id).then(res => {
          const callback = () => {
            //赋值属性
            this.contain.config = JSON.parse(config.detail) || {};
            this.contain.nav = JSON.parse(config.component) || [];
            this.calcData();
            this.setScale(width);
          }
          const data = res.data;
          this.contain.obj = data;
          const config = data.config;
          this.contain.visual = data.visual;
          //添加水印。只有查看页面生效
          if (!isBuild) {
            if (this.contain.config.mark.show) {
              this.watermark(this.contain.config.mark);
            }
            const password = this.contain.visual.password
            if (!this.validatenull(password)) {
              this.$prompt('请输入密码', '提示', {
                confirmButtonText: '确定',
                showCancelButton: false,
                showClose: false,
                closeOnClickModal: false,
                inputPattern: new RegExp(password),
                inputErrorMessage: '密码不正确，请重新输入'
              }).then(() => {
                callback();
              })
            } else {
              callback();
            }

          } else {
            callback();
          }
          loading.close();
        }).catch((err) => {
          // console.log(err)
          loading.close();
        })
      } else {
        this.setScale(width);
      }
    },
    //适配尺寸
    setResize () {
      this.$nextTick(() => {
        this.$refs.content.style.width = this.setPx((this.contain.config.scale * this.contain.config.width) / 100)
        this.$refs.content.style.height = this.setPx((this.contain.config.scale * this.contain.config.height) / 100)
      })
    },
    //计算比例
    setScale (width) {
      this.$nextTick(() => {
        this.contain.config.scale = (width / this.contain.config.width) * 100
        this.scale = this.contain.config.scale;
        this.setResize();
      })
    },
    calcData () {
      if (!this.contain.config.mark) this.contain.config.mark = {}
      if (!this.contain.config.query) this.contain.config.query = {}
    },
    handlePostionSelect (postion) {
      this.handleCalcPostionSelect();
      const x1 = this.selectCount.maxx1;
      const x2 = this.selectCount.maxx2;
      const y1 = this.selectCount.maxy1;
      const y2 = this.selectCount.maxy2;
      if (postion === 'left') {
        this.handleMoveSelectList(x1, undefined, true, postion);
      } else if (postion === 'center') {
        this.handleMoveSelectList(x1 + (x2 - x1) / 2, undefined, true, postion);
      } else if (postion === 'right') {
        this.handleMoveSelectList(x2, undefined, true, postion);
      } else if (postion === 'top') {
        this.handleMoveSelectList(undefined, y1, true, postion);
      } else if (postion === 'middle') {
        this.handleMoveSelectList(undefined, y1 + (y2 - y1) / 2, true, postion);
      } else if (postion === 'bottom') {
        this.handleMoveSelectList(undefined, y2, true, postion);
      }
    },
    handleMoveSelectList (left, top, type, postion) {
      this.contain.active.forEach(ele => {
        ele = this.contain.findlist(ele)
        const ele_component = ele.component;
        //水平情况
        if (left) {
          let baseLeft = Number(type ? left : (ele.left + left).toFixed(2));
          if (postion === 'right') {
            baseLeft = baseLeft - ele_component.width
          }
          else if (postion === 'center') {
            const obj_center = ele.left + ele_component.width / 2;
            baseLeft = ele.left + (left - obj_center)
          }
          this.$set(ele, 'left', baseLeft);
          this.$refs.subgroup.$refs[common.DEAFNAME + ele.index][0].setLeft(baseLeft)
        }
        //垂直情况
        if (top) {
          let baseTop = Number(type ? top : (ele.top + top).toFixed(2));
          if (postion === 'bottom') {
            baseTop = baseTop - ele_component.height
          }
          else if (postion === 'middle') {
            const obj_middle = ele.top + ele_component.height / 2;
            baseTop = ele.top + (top - obj_middle)
          }
          this.$set(ele, 'top', baseTop)
          this.$refs.subgroup.$ref[common.DEAFNAME + ele.index][0].setTop(baseTop)
        }
      })
    },
    //计算多选状态下的最大边界值
    handleCalcPostionSelect () {
      this.selectCount.maxx1 = 99999;
      this.selectCount.maxy1 = 99999;
      this.contain.active.forEach(ele => {
        ele = this.contain.findlist(ele)
        const left = ele.left;
        const top = ele.top;
        const width = ele.component.width;
        const height = ele.component.height;
        if (this.selectCount.maxx1 > left) {
          this.selectCount.maxx1 = left;
        }
        if (this.selectCount.maxx2 < left + width) {
          this.selectCount.maxx2 = left + width;
        }
        if (this.selectCount.maxy1 > top) {
          this.selectCount.maxy1 = top;
        }
        if (this.selectCount.maxy2 < top + height) {
          this.selectCount.maxy2 = top + height;
        }
      })
    },
    handleShowPanorama(){
      const id=this.$route.params.panoramaId;
      // console.log(id);
      // console.log("这里执行了");
      if(id==="null") return false;
      else return true;
    }

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

</style>
