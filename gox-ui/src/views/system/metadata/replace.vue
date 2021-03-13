<template>
  <div>
    <el-dialog
      title="批量修改"
      :visible.sync="dialogVisible"
      v-if="dialogVisible"
      width="60%"
      @close="closeDialog"
    >
      <el-row :gutter="15">
        <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
          <el-col :span="16">
            <el-form-item label="修改字段" prop="modifyField">
              <el-select v-model="formData.modifyField" placeholder="请选择修改字段" clearable
                :style="{width: '100%'}" @change="modifyFieldChange">
              <el-option
                v-for="item in parentTableTitle"
                :key="item.vModel"
                :label="item.tableFieldName"
                :value="item.vModel" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="查找内容" prop="find">
              <el-input v-show="findInput" v-model="formData.find" placeholder="请输入查找内容" clearable :style="{width: '100%'}">
              </el-input>
              <el-select v-show="findSelect" v-model="formData.find" placeholder="请选择查找内容" clearable :style="{width: '100%'}">
                <el-option v-for="(item, index) in findOptions" :key="index" :label="item.label"
                           :value="item.value" :disabled="item.disabled"></el-option>
              </el-select>
              <el-cascader v-show="findCascader" v-model="formData.find" :options="findOptions" :props="{ checkStrictly: true }"
                           :style="{width: '100%'}" :show-all-levels="false" placeholder="请选择查找内容" clearable>
              </el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="替换内容" prop="replacement">
              <el-input v-show="findInput" v-model="formData.replacement" placeholder="请输入替换内容" clearable
                :style="{width: '100%'}"></el-input>
              <el-select v-show="findSelect" v-model="formData.replacement" placeholder="请选择替换内容" clearable :style="{width: '100%'}">
                <el-option v-for="(item, index) in findOptions" :key="index" :label="item.label"
                           :value="item.value" :disabled="item.disabled"></el-option>
              </el-select>
              <el-cascader v-show="findCascader" v-model="formData.replacement" :options="findOptions" :props="{ checkStrictly: true }"
                           :style="{width: '100%'}" :show-all-levels="false" placeholder="请选择查找内容" clearable>
              </el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="18">
            <el-form-item label-width="121px" label="包含前后空格" prop="isblank">
              <el-switch v-model="formData.isblank"></el-switch>
            </el-form-item>
          </el-col>
          <el-col :span="18">
            <el-form-item label-width="121px" label="替换值允许为空" prop="isempty" >
              <el-switch v-model="formData.isempty" @change="changeSwitch"></el-switch>
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <div slot="footer">
        <el-button @click="closeDialog">关闭</el-button>
        <el-button @click="preview">预览</el-button>
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
      formData: {
        modifyField: "",
        find: "",
        replacement: "",
        isblank: false,
        isempty: false,

      },
      //下拉框选项值
      findOptions: [],
      fondsNameOptions: [{
        "label": "全宗1",
        "value": "全宗1"
      }, {
        "label": "全宗2",
        "value": "全宗2"
      }],
      fondsIdentifierOptions: [{
        "label": "选项一",
        "value": "选项一"
      }, {
        "label": "选项二",
        "value": "选项二"
      }],
      retentionPeriodOptions: [{
        "label": "永久",
        "value": "永久"
      }, {
        "label": "长期",
        "value": "长期"
      },
        {
        "label": "短期",
        "value": "短期"
      },
        {
          "label": "30年",
          "value": "30年"
        },
        {
          "label": "10年",
          "value": "10年"
        },
        {
          "label": "[其他]",
          "value": "[其他]"
        }],
      categoryCodeOptions: [
        {
          "children": [
            {
              "label": "党群工作类",
              "value": "DQ"
            },
            {
              "label": "行政管理类",
              "value": "XZ"
            },
            {
              "label": "教育管理类",
              "value": "JY"
            },
            {
              "label": "经验管理类",
              "value": "JG"
            }
          ],
          "label": "文书档案类",
          "value": "WS"
        },
        {
          "children": [
            {
              "label": "科学研究类",
              "value": "KY"
            },
            {
              "label": "建设项目类",
              "value": "JS"
            },
            {
              "label": "设备仪器类",
              "value": "SY"
            }
          ],
          "label": "科学技术类",
          "value": "KJ"
        },
        {
          "children": [
            {
              "label": "会计账簿类",
              "value": "02"
            },
            {
              "label": "会计凭证类",
              "value": "01"
            },
            {
              "label": "会计报表类",
              "value": "03"
            },
            {
              "label": "其他",
              "value": "04"
            }
          ],
          "label": "财务档案类",
          "value": "CW"
        },
        {
          "children": [
            {
              "label": "履历材料类",
              "value": "01"
            },
            {
              "label": "自传材料类",
              "value": "02"
            },
            {
              "label": "鉴定,考核,考察材料类",
              "value": "03"
            },
            {
              "label": "学历,培训和评定岗位技术材料类",
              "value": "04"
            },
            {
              "label": "政审材料类",
              "value": "05"
            },
            {
              "label": "参加中国共产党,共青团及民主党派的材料类",
              "value": "06"
            },
            {
              "label": "奖励材料类",
              "value": "07"
            },
            {
              "label": "处分材料类",
              "value": "08"
            },
            {
              "label": "劳动关系和工资,待遇审批情况材料类",
              "value": "09"
            },
            {
              "label": "其他可供组织参考的材料类",
              "value": "10"
            }
          ],
          "label": "人事档案类",
          "value": "RS"
        },
        {
          "children": [
            {
              "label": "声像档案类",
              "value": "01"
            },
            {
              "label": "电子档案类",
              "value": "02"
            },
            {
              "label": "实物档案类",
              "value": "03"
            }
          ],
          "label": "特殊载体档案类",
          "value": "TZ"
        },
        {
          "children": [
            {
              "label": "书籍类",
              "value": "SJ"
            },
            {
              "label": "报刊类",
              "value": "BK"
            },
            {
              "label": "宣传资料类",
              "value": "XZ"
            }
          ],
          "label": "资料",
          "value": "ZL"
        }
        ],
      //文本框显示
      findInput:true,
      //下拉框显示
      findSelect:false,
      //级联框显示
      findCascader:false,
      rules: {
        modifyField: [{
          required: true,
          message: '请选择修改字段',
          trigger: 'change'
        }],
        find: [{
          required: true,
          message: '请输入查找内容',
          trigger: 'blur'
        }],
        replacement: [{
          required: true,
          message: '请输入替换内容',
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
    //下拉框值改变
    modifyFieldChange(val){
      //情况缓存值
      this.formData.replacement = "";
      this.formData.find = "";

      var v =val+"Options";
       if(this[v]){
         this.findOptions = JSON.parse(JSON.stringify(this[v]));
         if (val == "categoryCode"){
           this.findSelect = false;
           this.findInput = false;
           this.findCascader = true;
           return;
         }
         this.findSelect = true;
         this.findInput = false;
         this.findCascader = false;
         return;
       }
      this.findSelect = false;
      this.findInput = true;
      this.findCascader = false;
    },
    //开关状态改变
    changeSwitch() {
      this.rules.replacement[0].required=!this.formData.isempty;
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
       this.$refs['elForm'].validate(valid => {
         if (!valid) {  return;  }
      for(var i=0;i<this.parentData.length;i++) {
        var varDate = this.parentData[i][this.formData.modifyField];
        //转换为String类型
        if (!(typeof(varDate) == "string" && varDate.constructor === String)){
          varDate=String(varDate);
        }
        //去除空格
        if(this.formData.isblank){
          this.parentData[i][this.formData.modifyField]=varDate.replace(/\s*/g,"")
        }
        //替换
        this.parentData[i][this.formData.modifyField]=varDate.split(this.formData.find).join(this.formData.replacement);
      }
      this.previewDialog=true;
      this.getList();
       })
    },
    //关闭
    onClose() {
      this.$refs['elForm'].resetFields()
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

