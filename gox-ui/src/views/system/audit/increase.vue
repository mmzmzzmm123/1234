<template>
  <div>
    <el-dialog
      title="批量增加"
      :visible.sync="dialogVisible"
      v-if="dialogVisible"
      width="60%"
      @close="closeDialog"
    >
      <el-row :gutter="15">
        <el-form ref="elFormx" :model="formData" :rules="rules" size="medium" label-width="100px">
          <el-col :span="16">
            <el-form-item label="字段名称" prop="modifyField">
              <el-select v-model="formData.modifyField" placeholder="请选择字段名称" clearable
                         :style="{width: '100%'}">
                <el-option
                  v-for="item in parentTableTitle"
                  v-if="isShow(item.tableFieldName)"
                  :key="item.vModel"
                  :label="item.tableFieldName"
                  :value="item.vModel" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="添加内容" prop="addContent">
              <el-input v-model="formData.addContent" placeholder="请输入添加内容" clearable :style="{width: '100%'}">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="18">
            <el-form-item label-width="121px" label="前面添加" prop="addFront">
              <el-switch v-model="formData.addFront" @change="changeSwitch($event,'front')"></el-switch>
            </el-form-item>
          </el-col>
          <el-col :span="18">
            <el-form-item label-width="121px" label="后面添加" prop="addRear" >
              <el-switch v-model="formData.addRear" @change="changeSwitch($event,'rear')"></el-switch>
            </el-form-item>
          </el-col>
          <el-col :span="18">
            <el-form-item label-width="121px" label="插入添加" prop="middle" >
              <el-switch v-model="formData.middle" @change="changeSwitch($event,'middle')"></el-switch>
            </el-form-item>
          </el-col>
          <el-tooltip placement="top">
            <div slot="content">请输入正整数，例如：原字段内容为"ABC3.1"，添加内容为“0”，插入字符位置为“5”，结果变成"ABC3.01"</div>
          <el-col :span="16">
            <el-form-item v-if="replacementSeen" label="插入位置" prop="replacement">
              <el-input v-model="formData.replacement" placeholder="请输入插入位置" clearable
                        :style="{width: '100%'}" @input="changeInput"></el-input>
            </el-form-item>
          </el-col>
          </el-tooltip>
        </el-form>
      </el-row>
      <div slot="footer">
        <el-button @click="closeDialog">关闭</el-button>
        <el-button @click="preview">预览</el-button>
          <!--<el-button type="primary" @click="handelConfirm">确定</el-button>-->
        <!--  <el-button type="primary" @click="batchUpdateMetadata1">提交</el-button>-->
      </div>
    </el-dialog>
    <el-dialog :visible.sync="previewDialog"
               v-if="previewDialog"
               title="预览"
               width="60%"
               @close="closePreview">
      <el-table v-loading="loading" ref="mainTable" :data="tabledata">
        <el-table-column
          type="selection"
          width="55"
          align="center"
          fixed="left"/>
        <div v-for="(item,index) in parentTableTitle" :key="index">
          <el-table-column
            :label="item.tableFieldName"
            :width="item.width"
            sortable="custom"
            :prop="item.vModel">
          </el-table-column>
        </div>
      </el-table>
      <pagination
        v-show="total>0"
        ref="pager"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        :page-sizes="[5, 10, 20, 50,100,200]"
        @pagination="getList"
      />
      <div slot="footer">
        <el-button @click="closePreview">关闭</el-button>
        <el-button @click="batchUpdate">批量更新</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import {
    BatchUpdateMetadata
  } from '@/api/system/metadata'
  export default {
    inheritAttrs: false,
    components: {},
    props: [],
    data() {
      return {
        // 总条数
        total: 0,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10
        },
        //表格分页展示数据
        tabledata:[],
        //弹框
        dialogVisible:false,
        //预览弹框
        previewDialog:false,
        //父组件传递数据
        parentData:[],
        //父组件传递表格数据
        parentTableTitle:[],
        //备份数据
        backupsData:[],
        //加载效果
        loading:true,
        replacementSeen:false,
        formData: {
          modifyField: "",
          addContent: "",
          replacement: "",
          addFront: false,
          addRear : false,
          middle:false
        },
        rules: {
          modifyField: [{
            required: true,
            message: '请选择字段名称',
            trigger: 'change'
          }],
          addContent: [{
            required: true,
            message: '请输入添加内容',
            trigger: 'blur'
          }],
          replacement: [{
            required: true,
            message: '请输入插入位置',
            trigger: 'blur'
          }],
        },
      }
    },
    computed: {},
    watch: {},
    created() {},
    mounted() {},
    methods: {
      //弹框调用，传参
      open(datas,tableTitle) {
        //先转换成字符串，然后再转换成对象（防止两对象联动）
        this.parentData=JSON.parse(JSON.stringify(datas));
        this.parentTableTitle=JSON.parse(JSON.stringify(tableTitle));
        this.backupsData= JSON.parse(JSON.stringify(this.parentData));
        this.dialogVisible=true;
      },
      //下拉框值显示
      isShow(name){
        return !(name==="分类号" || name==="全宗名称" || name==="全宗号" || name==="保管期限" || name==="年度")
      },
      //开关按钮
      changeSwitch(switchValue,switchName) {
        if (switchValue){
          if(switchName !== "front"){
            this.formData.addFront = false;
          }
          if(switchName !== "rear"){
            this.formData.addRear = false;
          }
          if(switchName !== "middle"){
            this.formData.middle = false;
          }
          this.replacementSeen = this.formData.middle;
        }
      },
      changeInput() {
        var pattern = /^[1-9][0-9]*$/ // 正整数的正则表达式
        // 不符合正整数时
        if (!pattern.test(this.formData.replacement)) {
          // input 框绑定的内容为空
          this.formData.replacement ="";
          this.$message.error("请输入正整数！");
        }
      },
      //关闭主弹框
      closeDialog() {
        this.dialogVisible=false;
      },
      //关闭预览弹框
      closePreview() {
        //数据还原
        this.parentData=JSON.parse(JSON.stringify(this.backupsData));
        this.previewDialog=false;
      },
      //数据加载
      getList(){
        this.loading=true;
        this.total=this.parentData.length;
        this.page();
        this.loading=false;
      },
      //数据分页
      page(){
        //清空数据
        this.tabledata=[];
        //页码
        var pagenum = this.queryParams.pageNum;
        //单页数据条数
        var pagesize = this.queryParams.pageSize;
        //总数据条数
        var datatotal = this.total;
        if(pagesize >= datatotal) {
          this.tabledata = this.parentData;
        }else{
          for (var i = 0;i < datatotal; i++){
            if (i < pagesize && (i+(pagenum-1)*pagesize) < datatotal) {
              this.tabledata[i] = this.parentData[i+(pagenum-1)*pagesize];
            }
          }
        }
      },
      //预览
      preview(){
        //验证
        this.$refs['elFormx'].validate(valid => {
          if (!valid) {  return;  }
          if(!(this.formData.addFront || this.formData.addRear || this.formData.middle)) {
            this.$message.error("请选择添加位置！");
            return;
          }
          for(var i=0;i<this.parentData.length;i++) {
            var varData = this.parentData[i][this.formData.modifyField];
            //转换为String类型
            if (!(typeof(varData) == "string" && varData.constructor === String)){
              varData=String(varData);
            }
            if(this.formData.addFront){
              varData = this.formData.addContent + varData;
            }
            if(this.formData.addRear){
              varData += this.formData.addContent;
            }
            if (this.formData.middle) {
              //判断插入位置与字符串长度
              if (this.formData.replacement > varData.length) {
                this.$message.error("第"+(i+1)+"条数据插入位置异常！");
                //数据还原
                this.parentData=JSON.parse(JSON.stringify(this.backupsData));
                return;
              }
              varData = varData.slice(0,this.formData.replacement) + this.formData.addContent + varData.slice(this.formData.replacement);
            }
            this.parentData[i][this.formData.modifyField] = varData;
            }
          this.previewDialog=true;
          this.getList();
        })
      },
      //关闭
      onClose() {
        this.$refs['elFormx'].resetFields()
      },
      //批量更新
      batchUpdate() {
        BatchUpdateMetadata(this.parentData).then(response => {
          this.msgSuccess("更新成功");
          //更新备份数据
          this.backupsData=JSON.parse(JSON.stringify(this.parentData));
          this.closePreview();
          this.$emit("updateTableData");
        });
      },
    }
  }
</script>
<style>
</style>
