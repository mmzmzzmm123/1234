<template>
  <el-container class="list">
    <el-header>
      <el-menu :default-active="activeName"
               mode="horizontal"
               background-color="#171b22"
               text-color="#fff"
               active-text-color="#00baff"
               @select="handleSelect">
        <el-menu-item :index="item.categoryValue"
                      :key="item.categoryValue"
                      v-for="item in typelist"
                      @click="getList(item.categoryValue)">
          <i class="iconfont icon-daping"></i>
          {{item.categoryKey}}
        </el-menu-item>
      </el-menu>

    </el-header>
    <el-main>
      <div class="page">
        <el-pagination layout="total, sizes, prev, pager, next, jumper"
                       background
                       @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       :page-size="page.size"
                       :current-page.sync="page.page"
                       :total="page.total">
        </el-pagination>
      </div>
      <div class="content">
        <div class="content__item content__item--add"
             @click="handleAdd">
          <div>
            <i class="el-icon-plus"></i>
            <p>新建大屏</p>
          </div>
        </div>
        <div class="content__item"
             v-for="(item,index) in list"
             :key="index"
             @mouseover="item._menu=true"
             @mouseout="item._menu=false">
          <div class="content__info">
            <img v-if="item.backgroundUrl"
                 :src="item.backgroundUrl"
                 alt="" />
            <div class="content__menu"
                 v-show="item._menu">
              <div class="content__btn"
                   @click="handleEdit(item)">
                编辑
              </div>
            </div>
          </div>
          <div class="content__main">
            <span class="content__name">{{item.title}}</span>
            <div class="content__menulist">
              <div class="content__view">
                <el-tooltip content="删除" style="margin-right: 7px;">
                  <i class="el-icon-delete"
                     @click="handleDel(item,index)"></i>
                </el-tooltip>
                <el-tooltip content="编辑" style="margin-right: 7px;">
                  <i class="el-icon-edit"
                     @click="handleUpdate(item,index)"></i>
                </el-tooltip>
                <el-tooltip content="预览" style="margin-right: 7px;">
                  <i class="el-icon-view"
                     @click="handleAddParonomaBefore(item,index)"></i>
                </el-tooltip>
<!--                <el-tooltip content="预览" style="margin-right: 7px;">-->
<!--                  <i class="el-icon-view"-->
<!--                     @click="handleViews(item,index)"></i>-->
<!--                </el-tooltip>-->
                <el-tooltip content="复制" style="margin-right: 7px;">
                  <i class="el-icon-copy-document"
                     @click="handleCopy(item,index)"></i>
                </el-tooltip>

              </div>
              <span class="content__status"
                    :class="{'content__status--active':item.status}">
                {{item.status?'已发布':'未发布'}}
              </span>
            </div>

          </div>
        </div>
      </div>
    </el-main>
    <el-dialog title="新建大屏"
               width="35%"
               :visible.sync="box">
      <avue-form :option="option"
                 v-model="form"
                 @submit="handleSave"></avue-form>
    </el-dialog>

    <el-dialog title="载入全景图" :visible.sync="dialogAddPanoramaVisible">
      <el-form :model="form">
<!--        :label-width="formLabelWidth"-->
        <el-form-item label="选择全景图" >
          <el-select v-model="panoramaId" placeholder="请选择需要载入的全景图" >
            <el-option v-for="panorama in waitPanorama" :key="panorama.id" :label="panorama.name+'[id:'+panorama.id+']'" :value="panorama.id"></el-option>
          </el-select>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogAddChangeVisible = false" style="color:black">取 消</el-button>
        <el-button type="primary" @click="handleAddParonoma" style="color:black">确 定</el-button>
      </div>
    </el-dialog>

  </el-container>
</template>
<script>
import { getList, addObj, updateObj, delObj, getCategory, copyObj } from '@/api/visual';
import {listPanorama} from "@/api/system/panorama";
import container from '@/page/group/container';
export default {
  name: "list",
  data () {
    return {
      typelist: [],
      index: 0,
      type: '',
      option: {
        column: [{
          label: '分组',
          prop: 'category',
          span: 24,
          labelWidth: 100,
          type: 'select',
          dicUrl: this.url + '/category/list',
          props: {
            label: 'categoryKey',
            value: 'categoryValue',
          },
          rules: [{
            required: true,
            message: "请选择分组",
            trigger: "blur"
          }]
        }, {
          label: '大屏名称',
          span: 24,
          labelWidth: 100,
          prop: 'title',
          rules: [{
            required: true,
            message: "请输入大屏名称",
            trigger: "blur"
          }]
        }, {
          label: '大屏尺寸',
          span: 14,
          labelWidth: 100,
          prop: 'width',
          placeholder: '请输入宽度',
          rules: [{
            required: true,
            message: "请输入大屏尺寸",
            trigger: "blur"
          }]
        }, {
          label: '',
          span: 10,
          labelWidth: 1,
          prop: 'height',
          placeholder: '请输入高度',
          rules: [{
            required: true,
            message: "请输入大屏尺寸",
            trigger: "blur"
          }]
        }, {
          label: '密码',
          span: 24,
          type: 'password',
          labelWidth: 100,
          prop: 'password',
        }, {
          label: '发布状态',
          prop: 'status',
          span: 24,
          labelWidth: 100,
          type: 'select',
          dicData: [{
            label: '未发布',
            value: 0
          }, {
            label: '已发布',
            value: 1
          }]
        }]
      },
      page: {
        page: 1,
        size: 10,
        total: 0,
      },
      form: {},
      box: false,
      activeName: '',
      list: [],
      dialogAddPanoramaVisible:false,
      queryParams: {
        pageNum: 1,
        pageSize: 1000,
        name: null,
        descri: null,
        detail: null,
        component: null
      },
      waitPanorama:null,
      screenId:null,
      panoramaId:null,
    }
  },
  created () {
    this.getCategory()
    console.log(this.url);
  },
  methods: {
    vaildData (id) {
      // 这个函数检验大屏是否可以修改
      return false
      const list = [];
      for (var i = 0; i < 20; i++) {
        list.push(i)
      }
      return list.includes(id)
    },
    getCategory () {
      getCategory().then(res => {
        console.log(res);
        const data = res.data;
        this.typelist = data;
        this.activeName = (data[0] || {}).categoryValue;
        this.getList();
      })
    },
    handleCopy (item) {
      this.$confirm('确认复制当前大屏', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        copyObj(item.id).then(() => {
          this.$message.success('复制成功');
          this.getList();
        })
      }).catch(() => {

      });
    },
    handleDel (item, index) {
      this.$confirm('是否确认永久删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (this.vaildData(index)) {
          this.$message.error('例子模板不允许修改')
          return false;
        }
        delObj(item.id).then(() => {
          this.list.splice(index, 1)
          this.$message.success('删除成功')
        })
      }).catch(() => {

      });
    },
    handleUpdate (item, index) {
      this.form = item
      this.form.category = this.form.category + '';
      this.type = 'edit';
      this.option.column[2].display = false;
      this.option.column[3].display = false;
      this.box = true;
      this.index = index;
    },
    handleEdit (item) {
      let routeUrl = this.$router.resolve({
        path: '/build/' + item.id+"/"+this.panoramaId
      })
      if(self!=top){
　　  // 页面在iframe中时处理容

        window.location.replace(routeUrl.href);
  　　}else{
        window.open(routeUrl.href, '_blank');
      }
      // this.$router.replace({path: '/build/' + item.id+"/"+this.panoramaId})
    },
    handleViews (item) {
      let routeUrl = this.$router.resolve({
        path: '/view/' + item.id+"/"+this.panoramaId
      })
      window.open(routeUrl.href, '_blank');
      // this.$router.replace({path: '/view/' + item.id+"/"+this.panoramaId})
    },
    handleAdd () {
      this.type = 'add';
      this.option.column[5].display = false;
      this.form.category = this.activeName;
      this.form.width = 1920;
      this.form.height = 1080;
      this.box = true;
    },
    handleSave (form, done) {
      done();
      if (this.type == 'add') {
        addObj(Object.assign({
          category: this.activeName,
        }, this.form)).then(res => {
          this.box = false;
          this.$message.success('新增成功');
          this.getList();
          const id = res.data.id;
          this.handleEdit({ id })
        })
      } else {
        if (this.vaildData(Number(this.index))) {
          this.$message.error('例子模板不允许修改')
          return false;
        }
        updateObj(Object.assign({
          category: this.activeName
        }, {
          id: this.form.id,
          password: this.form.password,
          status: this.form.status,
          title: this.form.title
        })).then(() => {
          this.box = false;
          this.$message.success('修改成功');
          this.getList();
        })
      }
    },
    handleSelect (key) {
      this.activeName = key;
      this.page.page = 1;
      this.getList();
    },
    handleCurrentChange (val) {
      this.page.page = val;
      this.getList();
    },
    handleSizeChange (val) {
      this.page.size = val;
      this.getList();
    },
    async handleAddParonomaBefore(item){
      await listPanorama(this.queryParams).then(response => {
        this.waitPanorama=response.rows;
      });
      this.screenId=item.id;
      this.dialogAddPanoramaVisible=true;
    },
    handleAddParonoma()
    {
      if(this.panoramaId===null) return;
      let routeUrl = this.$router.resolve({
        path: '/view/' + this.screenId+"/"+this.panoramaId
      })
      window.open(routeUrl.href, '_blank');
      // this.$router.replace({path: '/view/' + this.screenId+"/"+this.panoramaId})
      this.panoramaId=null;
      this.dialogAddPanoramaVisible=false;
    },
    getList (category) {
      const loading = this.$loading({
        lock: true,
        text: '正在加载中，请稍后',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      this.list = []
      getList({
        category: category || this.activeName,
        pageNum: this.page.page,
        pageSize: this.page.size,
      }).then(res => {
        loading.close();
        const data = res.data;
        this.page.total = data.total;
        this.list = data.records
        this.initData();
      })
    },
    initData () {
      this.list.forEach((ele, index) => {
        this.$set(this.list[index], '_menu', false)
      })
    }
  }
}
</script>
<style lang="scss" scoped>
@import "@/styles/list.scss";
</style>
