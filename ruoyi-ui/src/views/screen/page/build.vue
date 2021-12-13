<template>
  <div class="build">
    <imglist ref="imglist"
             @change="handleSetimg"></imglist>
    <top ref="top"></top>
    <div class="app"
         :class="{'app--none':!menuFlag}">
      <div class="menu"
           v-show="menuFlag"
           @click.self="handleMouseDown">
        <p class="title">图层</p>
        <layer ref="layer"
               :nav="nav"></layer>
      </div>
      <container ref="container"></container>
      <div class="menu params"
           v-show="menuFlag">
        <p class="title">操作</p>
        <el-tabs class="tabs"
                 stretch
                 v-model="tabsActive">
          <el-tab-pane name="0">
            <el-tooltip slot="label"
                        effect="dark"
                        content="配置"
                        placement="top">
              <div><i class="el-icon-setting"></i></div>
            </el-tooltip>
            <el-form label-width="120px"
                     label-position="left"
                     size="mini">
              <!-- 组件配置 -->
              <template v-if="!vaildProp('',[undefined])">
                <p class="title">{{activeObj.title}}</p>
                <el-form-item label="图层名称">
                  <avue-input v-model="activeObj.name"></avue-input>
                </el-form-item>
                <el-form-item label="隐藏">
                  <avue-switch v-model="activeObj.display"></avue-switch>
                </el-form-item>
                <template v-if="vaildProp('colorList')">
                  <el-form-item label="系统配色">
                    <avue-switch v-model="activeOption.switchTheme"></avue-switch>
                  </el-form-item>
                  <el-form-item label="配色选择"
                                v-if="activeOption.switchTheme">
                    <avue-select v-model="activeOption.theme"
                                 :dic="dicOption.themeList">
                    </avue-select>
                  </el-form-item>
                </template>
                <component :is="activeComponent.prop+'Option'"></component>
                <common-option></common-option>
              </template>
              <!-- 多选配置选项 -->
              <template v-else-if="isSelectActive">
                <el-form-item label="水平方式">
                  <el-tooltip content="左对齐"
                              placement="top">
                    <i class="el-icon-s-fold icon"
                       @click="$refs.container.handlePostionSelect('left')"></i>
                  </el-tooltip>
                  <el-tooltip content="居中对齐"
                              placement="top">
                    <i class="el-icon-s-operation icon"
                       @click="$refs.container.handlePostionSelect('center')"></i>
                  </el-tooltip>
                  <el-tooltip content="右对齐"
                              placement="top">
                    <i class="el-icon-s-unfold icon"
                       @click="$refs.container.handlePostionSelect('right')"></i>
                  </el-tooltip>
                </el-form-item>
                <el-form-item label="垂直方式">
                  <el-tooltip content="顶对齐"
                              placement="top">
                    <i class="el-icon-s-fold icon"
                       @click="$refs.container.handlePostionSelect('top')"></i>
                  </el-tooltip>
                  <el-tooltip content="中部对齐"
                              placement="top">
                    <i class="el-icon-s-operation icon"
                       @click="$refs.container.handlePostionSelect('middle')"></i>
                  </el-tooltip>
                  <el-tooltip content="底对齐"
                              placement="top">
                    <i class="el-icon-s-unfold icon"
                       @click="$refs.container.handlePostionSelect('bottom')"></i>
                  </el-tooltip>
                </el-form-item>
                <el-form-item label-width="0">
                  <el-button type="primary"
                             size="mini"
                             class="block"
                             @click="handleDeleteSelect">删除</el-button>
                </el-form-item>
                <el-form-item label-width="0">
                  <el-button type="danger"
                             size="mini"
                             class="block"
                             @click="handleFloder">成组</el-button>
                </el-form-item>
              </template>
              <!-- 主屏的配置项 -->
              <template v-else>
                <!-- <el-form-item label="大屏名称">
                  <avue-input v-model="config.name"></avue-input>
                </el-form-item> -->
                <el-form-item label="大屏宽度">
                  <avue-input-number v-model="config.width"></avue-input-number>
                </el-form-item>
                <el-form-item label="大屏高度">
                  <avue-input-number v-model="config.height"></avue-input-number>
                </el-form-item>
                <el-form-item label="大屏简介">
                  <avue-input v-model="config.info"
                              type="textarea"
                              :min-rows="5"></avue-input>
                </el-form-item>
                <el-form-item label="背景颜色">
                  <avue-color v-model="config.backgroundColor"></avue-color>
                </el-form-item>
                <el-form-item label="背景图片">
                  <img :src="config.backgroundImage"
                       @click="handleOpenImg('config.backgroundImage','background')"
                       alt=""
                       width="100%" />
                </el-form-item>
                <el-form-item label="缩放">
                  <el-slider v-model="config.scale"
                             :max="200"
                             :format-tooltip="formatTooltip"></el-slider>
                </el-form-item>
                <el-form-item label="环境地址">
                  <avue-input type="textarea"
                              :min-rows="2"
                              v-model="config.url"></avue-input>
                </el-form-item>
                <el-form-item label="参数">
                  <el-button size="mini"
                             type="primary"
                             @click="openCode('query')">编辑</el-button>
                </el-form-item>
                <el-form-item label="水印(预览有效)">
                  <avue-switch v-model="config.mark.show"></avue-switch>
                </el-form-item>
                <template v-if="config.mark.show">
                  <el-form-item label="内容">
                    <avue-input v-model="config.mark.text"></avue-input>
                  </el-form-item>
                  <el-form-item label="大小">
                    <avue-input-number v-model="config.mark.fontSize"></avue-input-number>
                  </el-form-item>
                  <el-form-item label="颜色">
                    <avue-color v-model="config.mark.textStyle"></avue-color>
                  </el-form-item>
                  <el-form-item label="角度">
                    <avue-input-number v-model="config.mark.degree"></avue-input-number>
                  </el-form-item>
                </template>
              </template>
            </el-form>
          </el-tab-pane>
          <!-- 数据配置 -->
          <el-tab-pane name="1"
                       v-if="vaildProp('dataList')">
            <el-tooltip slot="label"
                        effect="dark"
                        content="数据"
                        placement="top">
              <div><i class="el-icon-document-copy"></i></div>
            </el-tooltip>
            <el-form label-width="120px"
                     label-position="left"
                     size="mini">
              <el-form-item label="数据类型">
                <avue-radio v-model="activeObj.dataType"
                            :dic="dicOption.dataType"></avue-radio>
              </el-form-item>
              <el-form-item label="数据值"
                            label-position="top"
                            v-if="activeObj.dataType===0">
                <el-button size="mini"
                           type="primary"
                           @click="openCode('data')">编辑</el-button>
              </el-form-item>
              <template v-if="activeObj.dataType===1">
                <el-form-item label="接口地址">
                  <avue-input type="textarea"
                              :min-rows="6"
                              v-model="activeObj.url"></avue-input>
                </el-form-item>
                <el-form-item label="接口方式"
                              v-if="activeObj.dataType===1">
                  <avue-radio v-model="activeObj.dataMethod"
                              :dic="dicOption.dataMethod"></avue-radio>
                </el-form-item>
                <el-form-item label="接口参数"
                              v-if="activeObj.dataType===1">
                  <el-button size="mini"
                             type="primary"
                             @click="openCode('dataQuery')">编辑</el-button>
                </el-form-item>
                <el-form-item label="刷新时间">
                  <avue-input-number v-model="activeObj.time"></avue-input-number>
                </el-form-item>
              </template>
              <el-form-item label="数据处理">
                <el-button size="mini"
                           type="primary"
                           @click="openCode('dataFormatter')">编辑</el-button>
              </el-form-item>
              <el-form-item label-width="0">
                <el-button size="mini"
                           type="primary"
                           class="block"
                           @click="handleRefresh">刷新</el-button>
              </el-form-item>
            </el-form>

          </el-tab-pane>
          <!-- 交互事件配置 -->
          <el-tab-pane name="2"
                       v-if="vaildProp('eventList')">
            <el-tooltip slot="label"
                        effect="dark"
                        content="交互"
                        placement="top">
              <div><i class="el-icon-thumb"></i></div>
            </el-tooltip>
            <el-form label-width="120px"
                     label-position="left"
                     size="mini">
              <el-form-item label="子类">
                <avue-select multiple
                             v-model="activeObj.child.index"
                             :dic="childList"
                             :props="childProps">
                </avue-select>
              </el-form-item>
              <el-form-item label="参数名称">
                <avue-input v-model="activeObj.child.paramName"></avue-input>
              </el-form-item>
            </el-form>
          </el-tab-pane>
          <!-- 其他事件配置 -->
          <el-tab-pane name="3"
                       v-if="vaildProp('formatterList')">
            <el-tooltip slot="label"
                        effect="dark"
                        content="事件"
                        placement="top">
              <div><i class="iconfont icon-peizhi"></i></div>
            </el-tooltip>
            <el-form label-width="120px"
                     label-position="left"
                     size="mini">
              <el-form-item label="提示事件">
                <el-button size="mini"
                           type="primary"
                           @click="openCode('formatter')">编辑</el-button>
              </el-form-item>
              <el-form-item label="点击事件"
                            v-if="vaildProp('clickFormatterList')">
                <el-button size="mini"
                           type="primary"
                           @click="openCode('clickFormatter')">编辑</el-button>

              </el-form-item>
              <el-form-item label="标题事件"
                            v-if="vaildProp('labelFormatterList')">
                <el-button size="mini"
                           type="primary"
                           @click="openCode('labelFormatter')">编辑</el-button>
              </el-form-item>
              <el-form-item label-width="0">
                <el-button size="mini"
                           type="primary"
                           class="block"
                           @click="handleRefresh">刷新</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
          <!-- 基本参数配置 -->
          <el-tab-pane name="4"
                       v-if="isActive">
            <el-tooltip slot="label"
                        effect="dark"
                        content="参数"
                        placement="top">
              <div><i class="el-icon-folder"></i></div>
            </el-tooltip>
            <el-form label-width="120px"
                     label-position="left"
                     size="mini">
              <el-form-item label="序号">
                <avue-input v-model="activeObj.index"
                            disabled></avue-input>
              </el-form-item>
              <el-form-item label="X位置">
                <avue-input-number v-model="activeObj.left"></avue-input-number>
              </el-form-item>
              <el-form-item label="Y位置">
                <avue-input-number v-model="activeObj.top"></avue-input-number>
              </el-form-item>
              <el-form-item label="宽度">
                <avue-input-number v-model="activeComponent.width"></avue-input-number>
              </el-form-item>
              <el-form-item label="高度">
                <avue-input-number v-model="activeComponent.height"></avue-input-number>
              </el-form-item>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>

    <el-dialog title="代码编辑"
               :visible.sync="code.box"
               @close="code.obj={}"
               width="60%">
      <codeedit ref="codeedit"
                v-model="code.obj"></codeedit>
      <span slot="footer"
            class="dialog-footer">
        <el-button size="small"
                   @click="code.box=false">取 消</el-button>
        <el-button type="primary"
                   @click="codeClose"
                   size="small">确 定</el-button>
      </span>
    </el-dialog>
    <contentmenu ref="contentmenu"></contentmenu>
  </div>
</template>
<script>

import layer from './group/layer';
import top from './group/top';
import imglist from './group/imglist'
import contentmenu from './group/contentmenu'
import codeedit from './group/code';
import { dicOption } from '@/option/config'
import init from '@/mixins/'
import { uuid } from '@/utils/utils'
import components from '@/option/components'
export default {
  mixins: [init, components],
  data () {
    return {
      keys: {
        ctrl: false,
      },
      loading: '',
      childProps: {
        label: 'name',
        value: 'index'
      },
      key: '',
      menuFlag: true,
      code: {
        box: false,
        type: '',
        obj: '',
      },
      form: {},
      dicOption: dicOption,
      tabsActive: 0,
    }
  },
  components: {
    imglist,
    layer,
    codeedit,
    top,
    contentmenu
  },
  computed: {
    isFolder () {
      return this.activeObj.children
    },
    isActive () {
      return this.active.length !== 0
    },
    isSelectActive () {
      return this.active.length > 1;
    },
    childList () {
      return this.list.filter(ele => {
        if (['tabs'].includes(ele.component.prop)) {
          return false;
        }
        return true;
      })
    },
    activeComponent () {
      return this.activeObj.component || {}
    },
    activeOption () {
      return this.activeObj.option || {}
    },
    activeObj () {
      let result
      if (this.validatenull(this.active)) {
        return {}
      }
      this.active.forEach(ele => {
        const item = this.findnav(ele, true);
        if (this.active.length > 1) {
          if (!result) result = [];
          result.push(item.obj);
        } else {
          result = item.obj
        }
      })
      return result
    }
  },
  watch: {
    menuFlag () {
      this.setResize();
    },
    overactive (n, o) {
      [o, n].forEach((ele, index) => {
        if (!ele) return
        this.setActive(ele, index === 1, 'setOverActive');
      })
    },
    active (n, o) {
      [o, n].forEach((ele, index) => {
        ele.forEach(item => {
          this.setActive(item, index === 1, 'setActive');
        })
      })
      //隐藏右键菜单
      this.$refs.contentmenu.hide();
      // 初始化选项卡
      this.tabsActive = '0';
    },
  },
  created () {
    this.listen();
  },
  mounted () {
    this.initFun()
  },
  methods: {
    codeClose () {
      let value = this.$refs.codeedit.getValue();
      if (this.validatenull(value)) {
        value = '{}'
      }
      try {
        if (['query', 'data'].includes(this.code.type)) {
          value = JSON.parse(value, null, 4)
        }
        if (this.code.type === 'query') {
          this.config.query = value;
        } else {
          this.activeObj[this.code.type] = value;
        }
        this.code.box = false;
      } catch (error) {
        this.$message.error('数据格式有误')
      }

    },
    openCode (type) {
      this.code.type = type;
      if (type === 'query') {
        this.code.obj = this.config.query;
      } else {
        this.code.obj = this.activeObj[type];
      }
      this.code.box = true;
    },
    initFun () {
      ['setScale', 'setResize'].forEach(ele => {
        this[ele] = this.$refs.container[ele]
      });
      ['handleAdd'].forEach(ele => {
        this[ele] = this.$refs.top[ele]
      })
    },
    // 右键菜单
    handleContextMenu (e, item = {}) {
      if (!item.index) {
        return
      }
      if (!this.isSelectActive) {
        this.active = [item.index];
      }
      setTimeout(() => {
        this.$refs.contentmenu.show(e.clientX, e.clientY);
      }, 0)
    },
    //监听键盘的按键
    listen () {
      document.onkeydown = (e) => {
        this.keys.ctrl = e.keyCode === 17;
      }
      document.onkeyup = () => {
        this.keys.ctrl = false;
      }
    },
    setActive (val, result, fun) {
      const obj = this.$refs.container.handleGetObj(val);
      if (!this.validatenull(obj)) obj[0][fun](result)
    },
    //批量成组
    handleFloder () {
      let floder = {
        "title": "文件夹",
        "name": "文件夹",
        "index": uuid(),
        "children": []
      }
      this.active.forEach(index => {
        const params = this.findnav(index);
        floder.children.push(params.obj);
        delete params.parent.splice(params.count, 1);
      })
      this.nav.push(floder);
      this.handleInitActive();
    },
    //批量删除
    handleDeleteSelect () {
      this.$confirm(`是否批量删除所选图层?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.active.forEach(index => {
          const params = this.findnav(index);
          delete params.parent.splice(params.count, 1);
        })
        this.handleInitActive()
      })
    },
    vaildProp (name, list) {
      if (list) {
        return list.includes(this.activeComponent.prop)
      }
      return this.dicOption[name].includes(this.activeComponent.prop)
    },
    handleRefresh (tip) {
      this.$refs.container.handleRefresh(tip);
    },
    formatTooltip (val) {
      return parseInt(val);
    },
    //打开图库
    handleOpenImg (item, type) {
      this.$refs.imglist.openImg(item, type);
    },
    //图库框回调赋值
    handleSetimg (val, type) {
      if (type === 'activeObj.data') {
        this.activeObj.data = val;
      } if (type === 'activeObj.data.value') {
        this.activeObj.data.value = val;
      } else if (type === 'activeOption.backgroundImage') {
        this.activeOption.backgroundImage = val;
      } else if (type === 'activeOption.backgroundBorder') {
        this.activeOption.backgroundBorder = val;
      } else if (type === 'activeOption.empBackgroundBorder') {
        this.activeOption.empBackgroundBorder = val;
      } else if (type === 'config.backgroundImage') {
        this.config.backgroundImage = val;
      } else if (type === 'activeOption.symbol') {
        this.activeOption.symbol = val;
      }
    }
  }
}
</script>
<style lang="scss">
.build {
  position: relative;
  width: 100%;
  height: 100%;
  .app {
    width: 100%;
    height: calc(100% - 45px);
    display: flex;
    box-sizing: border-box;
  }
  .selectall {
    position: absolute;
    border: 1px dotted #409eff;
    z-index: 10000;
  }
  .icon {
    font-size: 20px;
    margin-right: 10px;
  }
  .selectbg {
    width: 100%;
    height: 100%;
    position: absolute;
    z-index: 9999;
  }
  .selectflag {
    width: 100%;
    height: 100%;
    position: absolute;
    z-index: 9999;
    cursor: move;
  }
  .el-menu--horizontal .el-menu .el-menu-item,
  .el-menu--horizontal .el-menu .el-submenu__title {
    font-size: 12px;
  }
  .menu {
    width: 180px;
    height: 100%;
    overflow: hidden;
    overflow-y: auto;
    color: #bcc8d4;
    background: #1d1f26;
  }
  .menu__list {
    padding-left: 10px;
    box-sizing: border-box;
  }
  .menu__item--folder {
    display: flex;
    align-items: center;
    padding: 6px 6px;
    color: #bcc9d4;
    font-size: 12px;
    input {
      border: none;
      outline: none;
    }
    .icon-folder {
      font-size: 16px;
      margin-right: 5px;
    }
    .icon-fold {
      font-size: 12px;
      margin-right: 10px;
      transform: rotate(90deg);
      display: inline-block;
      font-weight: bold;
      &.is-active {
        transform: rotate(180deg);
      }
    }
    &.is-active {
      color: #fff;
      background: rgba(143, 225, 255, 0.1);
    }
    &:hover {
      color: #fff;
      background: rgba(143, 225, 255, 0.1);
      cursor: pointer;
    }
  }
  .menu__folder {
    &.ghost {
      opacity: 0.6;
      color: #fff;
      background: #409eff !important;
      cursor: move;
    }
  }
  .menu__item {
    margin-bottom: 1px;
    width: 100%;
    box-sizing: border-box;
    display: flex;
    align-items: center;
    height: 48px;
    padding: 0 6px;
    position: relative;
    background: #1b1f25;
    cursor: pointer;
    flex: none;
    font-size: 12px;
    &.is-over,
    &:hover {
      color: #fff;
      background: rgba(143, 225, 255, 0.1);
      cursor: pointer;
    }
    &.ghost {
      opacity: 0.6;
      color: #fff;
      background: #409eff !important;
      cursor: move;
    }
    &.is-active {
      background: #409eff !important;
      color: #373d41 !important;
    }
  }
  .menu__icon {
    color: #409eff;
    margin-right: 10px;
    width: 53px;
    height: 30px;
    line-height: 30px;
    text-align: center;
    display: block;
    border: 1px solid #3a4659;
    background: #282a30;
  }
  .top {
    padding: 10px 20px;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    z-index: 9999;
    background-color: rgba(255, 255, 255, 0.4);
  }
  .middle {
    background-color: #2a2d32;
    flex: 1;
    position: relative;
    height: 100%;
    overflow: auto;
  }
  .wrapper {
    position: relative;
    padding: 60px 0 0 0;
    box-sizing: border-box;
    width: 100%;
    position: relative;
    box-sizing: border-box;
  }
  .content {
    transform-origin: 0 0;
    background-color: #333;
    position: relative;
    box-sizing: border-box;
  }
  .footer__menu {
    padding-top: 8px;
    margin-right: 370px;
    float: right;
    width: 300px;
  }
  .app--none {
    padding: 0;
    height: 100%;
  }
  .app--none .wrapper {
    position: relative;
    padding: 0;
    width: 100%;
  }
  .app--none .content {
    width: 100%;
    height: 100%;
    border: none;
  }
  .container {
    user-select: none;
    transform-origin: 0 0;
    position: relative;
  }

  .grade {
    width: 100%;
    height: 100%;
    background-size: 30px 30px, 30px 30px;
    background-image: linear-gradient(
        rgba(255, 255, 255, 0.1) 1px,
        transparent 0px
      ),
      linear-gradient(90deg, rgba(255, 255, 255, 0.1) 1px, transparent 0px);
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
  }
  .wrapper__grade {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    height: 100%;
    background: url(https://img.alicdn.com/tfs/TB184VLcPfguuRjSspkXXXchpXa-14-14.png)
      repeat;
  }

  .title {
    padding: 0 20px;
    box-sizing: border-box;
    margin-bottom: 10px;
    line-height: 35px;
    height: 35px;
    text-align: center;
    font-size: 13px;
    letter-spacing: 2px;
    text-indent: 2px;
    background-color: #2d343c;
    color: #fff;
    &--left {
      text-align: left;
    }
  }

  .params {
    width: 350px;
  }

  .tabs {
    margin-top: -10px;
    box-sizing: border-box;
  }
  .tabs .el-input-number {
    width: 100%;
  }
  .tabs .el-tabs__header {
    background: #27343e;
    margin: 0 0 2px;
  }
  .tabs i {
    color: #bcc9d4;
    font-size: 14px;
  }
  .tabs .is-active i {
    color: #409eff;
  }
  .tabs .el-tabs__nav-wrap::after {
    background: #27343e;
  }
  .tabs .el-tabs__item {
    padding: 0;
    box-sizing: border-box;
  }
  .tabs .el-tabs__active-bar {
    top: 0;
  }

  .el-collapse-item__header {
    padding-left: 30px;
  }
  .el-switch__core {
    width: 35px;
    height: 16px;
  }
  .el-switch {
    height: 10px;
    line-height: 10px;
  }
  .el-switch__core:after {
    top: -1px;
    width: 16px;
    height: 16px;
  }
  .el-slider__button-wrapper {
    top: -17px;
  }
  .el-slider__button {
    border-radius: 0;
    width: 8px;
    height: 8px;
    border-width: 1px;
  }
  .el-slider__bar,
  .el-slider__runway {
    height: 2px;
  }
  .imgList {
    height: 350px;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
  }
  .imgList img {
    width: 100px;
    height: 100px;
    margin: 20px 10px;
  }
  .el-input-number__decrease,
  .el-input-number__increase {
    background: transparent;
  }
}
.views {
  .wrapper {
    padding: 0;
  }
  .middle {
    overflow: inherit;
  }
}

// .el-table__cell {
//   background-color: #1d1f26 !important;
//   color: #bcc8d4 !important;
// }
// .el-dialog__body {
//   background-color: #1d1f26;
//   color: #bcc8d4;
// }
// .el-dialog__header {
//   background-color: #1d1f26;
//   color: #bcc8d4;
// }
// .avue-dialog .el-dialog__title,
// .avue-dialog .el-drawer__header {
//   color: #bcc8d4;
// }
// .avue-form__group--flex {
//   background-color: #1d1f26;
//   color: #bcc8d4;
// }
// .avue-dialog__footer {
//   margin-top: 10px;
// }
// .test {
//   background-color: #1d1f26;
//   color: #bcc8d4;
// }
</style>