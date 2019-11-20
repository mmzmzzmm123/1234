<template>
  <el-card class="box-card">
  <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
    <el-tab-pane label="基本信息" name="basic">
      <basic-info-form ref="basicInfo" :info="info"/>
      </el-tab-pane>
      <el-tab-pane label="字段信息" name="cloum">
        <el-table
        :data="cloumns"
        style="width: 100% ;margin-top: 10px"
      >
        <el-table-column label="序号" type="index" width="50"/>
        <el-table-column label="字段列名" align="center" prop="columnName" />
        <el-table-column label="字段描述" align="center" prop="columnComment" />
        <el-table-column label="物理类型" align="center" prop="columnType" />
        <el-table-column label="查询方式" align="center" >
          <template slot-scope="scope" >
            <el-select v-model="scope.row.javaType" @change="e => handleChange(e, scope.row.columnId, 'javaType')">
              <el-option value="Long">Long</el-option>
              <el-option value="String">String</el-option>
              <el-option value="Ingeter">Ingeter</el-option>
              <el-option value="Double">Double</el-option>
              <el-option value="BigDecimal">BigDecimal</el-option>
              <el-option value="Date">Date</el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="Java属性" align="center" prop="javaField" />
        <el-table-column label="插入" align="center">
          <template slot-scope="scope" width="50">
            <el-checkbox :checked="scope.row.isInsert==='1'" @change="e => handleChange(e, scope.row.columnId, 'isInsert')"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="编辑" align="center">
          <template slot-scope="scope" width="50">
            <el-checkbox :checked="scope.row.isEdit==='1'" @change="e => handleChange(e, scope.row.columnId, 'isEdit')"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="列表" align="center">
          <template slot-scope="scope" width="50">
            <el-checkbox :checked="scope.row.isList==='1'" @change="e => handleChange(e, scope.row.columnId, 'isList')"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="查询" align="center">
          <template slot-scope="scope" width="50">
            <el-checkbox :checked="scope.row.isQuery==='1'" @change="e => handleChange(e, scope.row.columnId, 'isQuery')"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="查询方式" align="center" >
          <template slot-scope="scope" >
            <el-select v-model="scope.row.queryType" @change="e => handleChange(e, scope.row.columnId, 'queryType')">
              <el-option value="EQ">=</el-option>
              <el-option value="NE">!=</el-option>
              <el-option value="GT">&gt;</el-option>
              <el-option value="GTE">&gt;=</el-option>
              <el-option value="LT">&gt;</el-option>
              <el-option value="LTE">&gt;=</el-option>
              <el-option value="LIKE">LIKE</el-option>
              <el-option value="BETWEEN">BETWEEN</el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="必填" align="center">
          <template slot-scope="scope" width="50">
            <el-checkbox :checked="scope.row.isRequired==='1'" @change="e => handleChange(e, scope.row.columnId, 'isRequired')"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="显示类型" align="center" >
          <template slot-scope="scope" >
            <el-select v-model="scope.row.htmlType" @change="e => handleChange(e, scope.row.columnId, 'htmlType')">
              <el-option value="input">文本框</el-option>
              <el-option value="textarea">文本域</el-option>
              <el-option value="select">下拉框</el-option>
              <el-option value="radio">单选框</el-option>
              <el-option value="checkbox">复选框</el-option>
              <el-option value="datetime">日期控件</el-option>
            </el-select>
          </template>
        </el-table-column>
      </el-table>
    </el-tab-pane>
    <el-tab-pane label="生成信息" name="genInfo">
      <gen-info-form ref="genInfo" :info="info"/>
    </el-tab-pane>
  </el-tabs>
  <el-form label-width="100px">
    <el-form-item style="text-align: center;margin-left:-100px;margin-top:10px;">
      <el-button type="primary" @click="submitForm()">提交</el-button>
      <el-button  @click="rollback()">返回</el-button>
    </el-form-item>
  </el-form>
  </el-card>
</template>
<script>
import { editTableInfo,editGenInfo } from '@/api/tool/gen'
import basicInfoForm from './basicInfoForm';
import genInfoForm from './genInfoForm';
export default {
  components:{
    basicInfoForm,
    genInfoForm
  },
  data() {
    return {
      activeName: 'cloum',
      cloumns:[],
      info:{},
      cloumForm:{}
    };
  },
  beforeCreate(){
    const { tableId } = this.$route.query
    if (tableId) {
      editTableInfo({tableId:tableId}).then(res => {
        this.cloumns=res.data.rows
        this.info=res.data.info
      })
    }
  },
  methods: {
    handleChange (value, key, column) {
      const newData = [...this.cloumns]
      const target = newData.filter(item => key === item.columnId)[0]
      if (target) {
        target[column] = value
        this.cloumns = newData
      }
    },
    submitForm() {
      const basicForm = this.$refs.basicInfo.$refs.basicInfoForm;
      const genForm = this.$refs.genInfo.$refs.genInfoForm;
      Promise.all([basicForm, genForm].map(this.getFormPromise)).then(res => {
        const validateResult = res.every(item => !!item);
        if (validateResult) {
          const genTable = Object.assign({}, basicForm.model, genForm.model)
          genTable.columns = this.cloumns
          console.log('genTable:',genTable)
          editGenInfo(genTable).then(res=>{
            console.log('gen edit res:',res)
            this.msgSuccess(res.msg);
            if(res.code===200){
              this.rollback()
            }
          })
        } else {
          this.msgError("表单校验未通过，请重新检查提交内容");
        }
      })
    },
    getFormPromise(form) {
      return new Promise(resolve => {
        form.validate(res => {
          resolve(res);
        })
      })
    },
    handleClick(tab, event) {
      // console.log(tab, event);
    },
    rollback () {
      this.$router.push('/tool/server')
    }
  }
};
</script>
