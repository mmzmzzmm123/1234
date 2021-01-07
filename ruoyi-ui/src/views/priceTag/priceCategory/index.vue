<!--
 * @Author: your name
 * @Date: 2020-12-07 22:41:46
 * @LastEditTime: 2020-12-27 16:36:04
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \RuoYi-Vue\ruoyi-ui\src\views\priceTag\priceCategory.vue
-->
<template>
  <div class='container'>
    <div class="row-wrap">
      <el-table
        :data="list"
        border
      >
        <el-table-column type="index" label="类别"></el-table-column>
        <el-table-column prop="priceType" label="名称"></el-table-column>
        <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            :type="scope.row.isVisible===1 ? 'info' : 'warning'"
            @click="handleSwitch(scope.$index, scope.row)"
          >{{scope.row.isVisible===1?"隐藏":"显示"}}</el-button>
          <el-button
            size="mini"
            type="primary"
            @click="handleEdit(scope.$index, scope.row)"
          >修改</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
      </el-table>
    </div>
    <div class="submit">
      <el-button @click="add">新增</el-button>
    </div>
    <!-- 新增编辑房价类别 -->
    <el-dialog
      :visible="visible"
      :title="isEdit ? '编辑' : '新增'"
      width="500px"
      class="room-alert"
      v-if="visible"
      @close="visible=false"
    >
      <el-form
        :model="cateForm"
        ref="cateForm"
        :rules="rules"
      >
        <el-form-item label-width="80px" label="名称" prop="priceType">
          <el-input v-model.number="cateForm.priceType" maxLength="20"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary" @click="handleSubmit">提交</el-button>
        <el-button @click="visible=false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {getPriceType, addPriceType, editPriceType, deletePriceType, switchPriceType} from "@/api/priceTag";
export default {
  name: 'PriceCategory',
  data(){
    return {
      visible: false,
      list: [],
      isEdit: false,
      cateForm: {
        priceType : ""
      },
      rules: {
        priceType : [{required: true, message: "名称不能为空", trigger: "blur"}]
      }
    };
  },
  mounted(){
    this.init();
  },
  methods:{
    //查询房价类别
    init(){
      const _this = this;
      getPriceType()
      .then((res)=>{
        const {data} = res;
        _this.list = data;
      });
    },
    //显示隐藏切换
    handleClick(key){
      this.params[key] = this.params[key] === 0 ? 1 : 0;
    },
    //新增房价类别
    add(){
      this.isEdit =  false;
      this.cateForm = {priceType:""}
      this.visible = true;
    },
    //提交
    handleSubmit(){
      const _this = this;
      this.$refs.cateForm.validate((valid)=>{
        if(valid){
          if(_this.isEdit){
            editPriceType(this.cateForm).then(()=>{
              _this.msgSuccess("修改成功");
              _this.init();
              _this.visible = false;
            });
          }else{
            addPriceType(this.cateForm).then(()=>{
              _this.msgSuccess("新增成功");
              _this.init();
              _this.visible = false;
            });
          }
        }
      });
    },
    //修改
    handleEdit(index, row){
      const {priceType, priceTypeId} = row;
      this.isEdit =  true;
      this.cateForm.priceType = priceType;
      this.cateForm.priceTypeId = priceTypeId;
      this.visible = true;
    },
    //删除
    handleDelete(index, row){
      const _this = this;
      this.$confirm('确定要删除吗?', '提示', {
        confirmButtonText: '确定',
        type: 'warning'
      }).then(()=>{
        const {priceTypeId} = row;
        deletePriceType({priceTypeId})
          .then((res)=>{
          _this.msgSuccess("删除成功");
          _this.init();
        })
      })

    },
    //显示隐藏
    handleSwitch(index, row){
      const {isVisible, priceTypeId} = row;
      const _this = this;
      switchPriceType({
        isVisible: isVisible===0?1:0,
        priceTypeId
      })
      .then((res)=>{
        _this.msgSuccess("操作成功");
        _this.$set(_this.list[index], "isVisible", res.data.isVisible);
      })
    }
  }
};
</script>
<style lang='scss' scoped>
.container{
  .row-wrap{
    padding: 20px;
  }
  .row{
    display: flex;
    align-items: center;
    margin-bottom: 20px;
    .label{
      width: 70px;
      font-size: 14px;
      font-weight: 600;
    }
    .el-input{
      width: 70%;
    }
    .btn{
      flex: 1;
      text-align: right;
      .el-button{
        background: #CCCCCC;
        border-color: #CCCCCC;
        color: #ffffff;
        &.show{
          background: #DDA748;
          border-color: #DDA748;
        }
      }
    }
  }
  .submit{
    text-align: center;
    .el-button{
      width: 100px;
      background: #DDA748;
      border-color: #DDA748;
      color: #ffffff;
    }
  }
}
</style>
