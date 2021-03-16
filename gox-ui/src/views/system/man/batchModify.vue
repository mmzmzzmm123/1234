<template>
    <div class="batchModify">

      <el-dialog title="批量修改" :visible.sync="open" @close="closeDialog">
           <el-card class="box-card">
              <div slot="header" class="clearfix">
                <el-row style="margin-bottom: 10px">
                  <div>
                    <el-input placeholder="请输入修改内容"  v-model="field">
                      <el-select placeholder="请选择" slot="prepend" v-model="selectField" style="width: 200px">
                        <el-option v-for="(item,index) in tableTitle" :key="index"
                                   :label="item.tableFieldName" :value="item.id"></el-option>
                      </el-select>
                      <el-button slot="append" @click.native="handleAddChangeData()" size="mini">添加</el-button>
                    </el-input>
                  </div>
                </el-row>
                <el-row style="margin-bottom: 10px">
                  <el-checkbox label="替换值是否为空" v-model="checked"></el-checkbox>
                </el-row>
                <el-row style="margin-bottom: 10px">
                  <div>
                    <el-button size="medium" style="width: 100px" type="primary" @click.native="deleteChooseDate">删除修改</el-button>
                    <el-button size="medium" style="width: 100px" type="info" @click.native="clearField">清 除</el-button>
                    <el-button size="medium" style="width: 100px" type="warning" @click.native="previewData">预 览</el-button>
                  </div>
                </el-row>
              </div>

              <div slot="default">
                <el-table border :data="changeFieldTable"  @selection-change="handleDeleteOne">
                  <el-table-column type="selection" ></el-table-column>
                  <el-table-column label="字段编码" prop="vModel" align="center"></el-table-column>
                  <el-table-column label="字段名称" prop="tableFieldName" align="center"></el-table-column>
                  <el-table-column label="字段值"  prop="changeValue" align="center"></el-table-column>
                </el-table>
              </div>
            </el-card>
            <span slot="footer" class="dialog-footer">
              <el-button @click="closeDialog">取 消</el-button>
              <el-button type="primary" @click="handleBatchUpdate">修改</el-button>
            </span>
          </el-dialog>

            <el-dialog title="预览" :visible.sync="preview"  :modal-append-to-body='false'>
              <el-table  :data="previewTable" >
                <div v-for="(item,index) in tableTitle" :key="index">
                  <el-table-column
                    :label="item.tableFieldName"
                    :width="item.width"
                    sortable="custom"
                    :prop="item.vModel">
                  </el-table-column>
                </div>
              </el-table>
            </el-dialog>
          </div>
      </template>

      <script>
        import {BatchUpdateMetadata} from '@/api/system/metadata'
          export default {
            name: "batchModify",
            props:{
              selectData:{
                type:Array
              },
              tableTitle:{
                type:Array
              },
              BatchEditDialog:{
                type: Boolean
              }
            },
            data(){
              return{
                open:false,
                //选择修改字段
                selectField:"",
                //修改字段替换值
                field:"",
                //修改字段列表
                changeFieldTable:[],
                checked:false,
                delFieldTable:null,
                //预览弹窗
                preview:false,
                //预览数据
                previewTable:[],
                checked:false
              }
            },
            methods:{
              closeDialog(){
                this.checked=false;
                this.open=false;
                this.$emit("closeBatchModify")
              },
              /** 添加修改字段 **/
              handleAddChangeData(){
                //判断修改字段和修改值是否为空
                if (this.field!=null&&this.selectField!=null){
                  let exist=this.changeFieldTable.find(item=>item.id===this.selectField);
                  if(exist){
                    this.$message.error("请勿重复添加相同字段");
                  }else{
                    let obj=this.tableTitle.find((item)=>{return item.id===this.selectField});
                    var changeForm=new Object();
                    changeForm.vModel=obj.vModel;
                    changeForm.tableFieldName=obj.tableFieldName;
                    changeForm.changeValue=this.field;
                    changeForm.id=this.selectField;
                    this.changeFieldTable.push(changeForm);
                    this.field=null;
                    this.selectField=null;
                  }
                  //若是修改字段不为空且，修改值允许为空。
                }else if(this.selectField!=null&&this.checked){
                  let exist=this.changeFieldTable.find(item=>item.id===this.selectField);
                  if(exist){
                    this.$message.error("请勿重复添加相同字段");
                  }else{
                    let obj=this.tableTitle.find((item)=>{return item.id===this.selectField});
                    var changeForm=new Object();
                    changeForm.vModel=obj.vModel;
                    changeForm.tableFieldName=obj.tableFieldName;
                    if(this.field==null||this.field==""){
                      changeForm.changeValue="——";
                    }else{
                      changeForm.changeValue=this.field;
                    }
                    changeForm.id=this.selectField;
                    this.changeFieldTable.push(changeForm);
                    this.field=null;
                    this.selectField=null;
                  }
                }else{
                  this.$message.error("请完善修改字段信息");
                }


              },
              /** 修改提交 **/
              handleBatchUpdate(){
                let updateData=JSON.parse(JSON.stringify(this.selectData));
                if(this.changeFieldTable==null||this.changeFieldTable.length<1){
                  this.$message.error("请先添加修改字段");
                }else{
                  for (var i=0;i<updateData.length;i++){
                    for (var s=0;s<this.changeFieldTable.length;s++){
                      updateData[i][this.changeFieldTable[s].vModel]=this.changeFieldTable[s].changeValue;
                    }
                  }
                  BatchUpdateMetadata(updateData).then(response => {
                    this.msgSuccess("修改成功");
                    this.$emit("getList");
                    this.closeDialog();
                  })
                }

              },
              handleDeleteOne(selection){
                this.delFieldTable=selection;
              },
              //删除选择的修改字段
              deleteChooseDate(){
                var a=this.delFieldTable.map(item=>item.id);
                this.changeFieldTable=this.changeFieldTable.filter(item=>!a.includes(item.id));
              },
              //清空修改字段列表
              clearField(){
                this.changeFieldTable=[];
              },
              //预览修改数据
              previewData(){
                this.preview=true;
                this.previewTable= JSON.parse(JSON.stringify(this.selectData));
                for (var i=0;i<this.selectData.length;i++){
                  for (var s=0;s<this.changeFieldTable.length;s++){
                    //修改整个表格的数据，然后将整个表格的数据传到后端。
                    this.previewTable[i][this.changeFieldTable[s].vModel]=this.changeFieldTable[s].changeValue;
                  }
                }
              },
            },
            watch:{
              BatchEditDialog(val){
                this.open=this.BatchEditDialog
              }
            }
          }
      </script>

      <style scoped>

      </style>
