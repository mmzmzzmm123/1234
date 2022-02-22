<template>
  <el-dialog
    title="图标选择"
    :close-on-click-modal="false"
    v-dialog-drag
    class="custom_dialog"
    :visible.sync="visible" >

      <el-input v-model="name" style="position: relative; margin-bottom: 10px;" clearable placeholder="请输入图标名称" @clear="filterIcons" @input.native="filterIcons">
        <i slot="suffix" class="el-icon-search el-input__icon" />
      </el-input>

      <el-tabs v-model="activeName" type="card" @tab-click="handleTabsClick">
        <el-tab-pane label="Element图标" name="first">
          <ul class="icon-list">
            <li v-for="(item, index) in elementIconList"
              :key="index" :title="item">
            <el-button
              @click="iconActiveHandle(item)"
              :class="{ 'is-active': item === icon }">
              <i :class="item"></i>
            </el-button>
            </li>
          </ul>
        </el-tab-pane>
        <el-tab-pane label="Font Awesome图标" name="second">
              <ul class="icon-list">
                <li v-for="(item, index) in fontAwesomeIconList"
                  :key="index" :title="item">
                <el-button
                  @click="iconActiveHandle(item)"
                  :class="{ 'is-active': item === icon }">
                  <i :class="item"></i>
                </el-button>
                </li>
              </ul>
        </el-tab-pane>

        <el-tab-pane label="自定义图标" name="thirdly">
          <ul class="icon-list">
            <li v-for="(item, index) in customIconList"
                :key="index" :title="item">
              <el-button
                @click="iconActiveHandle(item)"
                :class="{ 'is-active': item == icon }">
                <svg-icon :icon-class="item" />
              </el-button>
            </li>
          </ul>
        </el-tab-pane>
      </el-tabs>

      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="visible = false" icon="el-icon-circle-close">关闭</el-button>
        <el-button size="small"  type="primary" icon="el-icon-circle-check" @click="doSubmit()">确定</el-button>
      </span>
  </el-dialog>
</template>

<script>
  import elementIcons from '@/utils/icon/elementIcons'
  import fontAwesomeIcons from '@/utils/icon/fontAwesomeIcons'
  import customIcons from '@/utils/icon/customIcons'
  export default {
    name: 'Icon',
    data () {
      return {
        visible: false,
        name: '',
        activeName: 'first',
        elementIconList: elementIcons,
        fontAwesomeIconList: fontAwesomeIcons,
        customIconList: customIcons,
        icon: ''
      }
    },
    props: {
      /* 初始值 */
      value: {
        type: [String, Number],
        default: () => { return null }
      }
    },
    watch: {
      value () {
        this.icon = this.value
      }
    },
    methods: {
      // 图标选中
      iconActiveHandle (iconName) {
        // 赋值
        this.icon = iconName
      },
      // 查询过滤
      filterIcons() {
        let activeName = this.activeName;

        if (activeName == 'first') {
          this.elementIconList = elementIcons

          if (this.name) {
            this.elementIconList = this.elementIconList.filter(item => item.includes(this.name))
          }
        } else if (activeName == 'second') {
          this.fontAwesomeIconList = fontAwesomeIcons

          if (this.name) {
            this.fontAwesomeIconList = this.fontAwesomeIconList.filter(item => item.includes(this.name))
          }
        } else if (activeName == 'thirdly') {
          this.customIconList = customIcons

          if (this.name) {
            this.customIconList = this.customIconList.filter(item => item.includes(this.name))
          }
        }
      },
      handleTabsClick() {
        this.filterIcons()
      },
      // 确定提交
      doSubmit () {
        this.$emit('getValue', this.icon)
        this.visible = false
      }
    }
  }
</script>

<style scoped>

ul{
    margin: 10px 0;
    padding: 0 0 0 20px;
    font-size: 14px;
    color: #5e6d82;
    line-height: 2em;
}

ul.icon-list {
    overflow: hidden;
    list-style: none;
    padding: 0!important;
    border: 1px solid #eaeefb;
    border-radius: 4px;
}
.icon-list li {
    float: left;
    width: 10%;
    text-align: center;
    height:50px;
    line-height: 50px;
    color: #666;
    font-size: 13px;
    border-right: 1px solid #eee;
    border-bottom: 1px solid #eee;
    margin-right: -1px;
    margin-bottom: -1px;
}
</style>
