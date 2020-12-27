<!--
 * @Author: pengyu
 * @Date: 2020-12-09 22:02:59
 * @LastEditTime: 2020-12-27 17:42:25
 * @LastEditors: Please set LastEditors
 * @Description: 房型价格
 * @FilePath: \RuoYi-Vue\ruoyi-ui\src\views\priceTag\roomPrice\index.vue
-->
<template>
  <div class='container'>
    <el-table
      :data="tableData"
      border
    >
      <el-table-column prop="orderNum" label="序号" width="80"></el-table-column>
      <el-table-column
        v-for="(itm,idx) in columnData"
        :key="idx"
        :prop="itm.field.toString()"
        :label="itm.name"
      >
        <template slot-scope="scope">
          <div class="cell" v-if="itm.field==='roomType'">{{scope.row[itm.field]}}</div>
          <el-input v-else v-model="scope.row[itm.field]"></el-input>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="160">
        <template slot-scope="scope">
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

    <el-row>
      <el-button @click="handleAdd">添加</el-button>
    </el-row>

    <!-- 添加房型价格 -->
    <el-dialog
      :visible="visible"
      :title="isEdit ? '编辑房型价格' : '添加房型价格'"
      width="500px"
      class="room-alert"
      v-if="visible"
      @close="visible=false"
    >
      <el-form
        :model="roomForm"
        ref="roomForm"
        :rules="rules"
      >
        <el-form-item
          label-width="80px"
          label="房型："
          prop="roomType"
        >
          <el-input v-model="roomForm.roomType" maxLength="20"></el-input>
        </el-form-item>
        <el-form-item
          label-width="80px"
          label="排序："
          prop="orderNum"
        >
          <el-input v-model.number="roomForm.orderNum" maxLength="20"></el-input>
        </el-form-item>
        <el-form-item
          label-width="80px"
          v-for="(itm,idx) in priceCate"
          :key="idx"
          :label="`${itm.priceType}：`"
          :prop="`price${itm.priceTypeId}`"
        >
          <el-input v-model.number="roomForm[`price${itm.priceTypeId}`]" maxLength="20"></el-input>
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
import { getRoomPriceList, addRoomPrice,editRoomPrice,deleteRoomPrice,getPriceType } from "@/api/priceTag"

export default {
  name: 'RoomPrice',
  data(){
    return {
      visible: false,
      columnData: [{field:"roomType", name: "房型"}], //列头数据
      priceCate: [],
      tableData: [], //表格数据
      roomForm: {
        orderNum: "",
        roomType: "",
      },
      rules: {
        roomType: [{required: true, message:"房型不能为空", trigger: "blur"}],
        orderNum: [{required: true, message:"排序不能为空", trigger: "blur"},{type:"number",message:"序号必须为数值"}],
      },
      isEdit: false,
      headerList: [] //房型list
    };
  },
  mounted(){
    this.queryRoomPriceHeader();
    this.queryRoomPriceList();
  },
  methods: {
    //查询房价列头
    async queryRoomPriceHeader(){
      const rsp = await getPriceType();
      const {data} = rsp;
      const _this = this;
      data.forEach(itm=>{
        if(itm.isVisible===0){
          _this.columnData.push({
            field: itm.priceTypeId,
            name: itm.priceType
          });
          _this.$set(_this.roomForm, `price${itm.priceTypeId}`, "");
          _this.$set(_this.rules, `price${itm.priceTypeId}`, [{required: false, validator: _this.isNumber, trigger: "blur"}]);
          _this.priceCate.push(itm);
        }
      });
    },
    //查询房型价格
    queryRoomPriceList(){
      getRoomPriceList()
      .then((res)=>{
        this.tableData = res.rows;
      })
    },
    //提交
    handleSubmit(formName){
      const _this = this;
      this.$refs.roomForm.validate((valid)=>{
        if(valid){
          const {orderNum, roomType} = _this.roomForm;
          const params = {
            orderNum,
            roomType,
            priceList: []
          };
          for(let i in _this.roomForm){
            if(i.includes("price")){
              const sp = i.split("e");
              params.priceList.push({
                roomPrice: _this.roomForm[i],
                priceTypeId: Number(sp[1])
              });
            }
          }
          params.priceList = JSON.stringify(params.priceList);
          if(_this.isEdit){
            editRoomPrice({roomTypeEntity: params}).then(()=>{
              _this.msgSuccess("修改成功");
              _this.queryRoomPriceList();
              _this.visible = false;
            });
          }else{
            addRoomPrice({roomTypeEntity: params}).then(()=>{
              _this.msgSuccess("添加成功");
              _this.queryRoomPriceList();
              _this.visible = false;
            });
          }
        }
      });
    },
    //添加房价
    handleAdd(){
      for(let i in this.roomForm){
        this.roomForm[i] = "";
      }
      this.visible = true;
      this.isEdit = false;
    },
    //编辑房价
    handleEdit(index, data){
      this.visible = true;
      this.isEdit = true;
      Object.assign(this.roomForm, data);
    },
    //删除房价
    handleDelete(index, data){
      const _this = this;
      this.$confirm('确定要删除吗?', '提示', {
        confirmButtonText: '确定',
        type: 'warning'
      }).then(() => {
        deleteRoomPrice({roomTypeId : data.roomTypeId })
        .then((res)=>{
          _this.msgSuccess("删除成功");
          _this.queryRoomPriceList();
        })
      })
    },
    //rules 校验价格为 数值非必填
    isNumber(rule, value, callback){
      if(value){
        if( /^[1-9]\d*$/.test(value)){
          return callback();
        }else{
          return callback(new Error("价格必须为数值"));
        }
      }
      return callback();
    }
  }
};
</script>
<style lang='scss' scoped>
.container{
  padding: 20px;
  .el-row{
    margin-top: 20px;
  }
}
.room-alert{
  & /deep/ .el-dialog__body{
    padding: 10px 40px;
  }
  & /deep/ .el-form-item__label{
    padding-right: 20px;
  }
}
</style>
