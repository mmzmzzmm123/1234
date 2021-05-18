<template>
    <!-- 添加或修改导粉管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
        <div style="height: 500px; overflow: auto">
            <el-table v-loading="loading" :data="wxAccountList">
                <!--<el-table-column label="微信昵称" align="center" prop="wxNickName" />-->
                <el-table-column label="微信号" align="center" prop="wxAccount" width="120"/>
                <el-table-column label="销售" align="center" prop="saleName" width="120"/>
                <el-table-column label="已导粉数量" align="center" prop="importFanNum" width="120"/>
                <el-table-column label="进粉渠道" align="center" prop="fanChannel" >
                    <template slot-scope="scope">
                            <el-select
                        v-model="scope.row.fanChannel"
                        placeholder="请选择账号渠道"
                        clearable
                        filterable
                        size="small"
                        >
                        <el-option
                            v-for="dict in fanChanneloptions"
                            :key="dict.dictValue"
                            :label="dict.dictLabel"
                            :value="parseInt(dict.dictValue)"
                        />
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column label="导粉数量" align="center" prop="fanNum" width="200">
                    <template slot-scope="scope">
                            <el-input-number v-model="scope.row.fanNum" :min="1" :max="10000" label="导粉数量" style="width:160px"></el-input-number>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                    <template slot-scope="scope">
                    <el-button
                        size="mini"
                        type="text"
                        icon="el-icon-edit"
                        @click="handleUpdate(scope.row)"
                        v-hasPermi="['custom:importFanRecord:add']"
                    >导粉</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
       
    </el-dialog>
</template>
<script>
import { addImportFanRecord,getWxAccountAndSale } from "@/api/custom/importFanRecord";
export default {
  name: "ImportFan",
  components: {
      
  },
  data() {
    return {
        // 遮罩层
      loading: true,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 表单校验
      rules: {
        title: [
          { required: true, message: "标题不能为空", trigger: "blur" },
        ]
      },
      data: null,
      callback: null,
      //可接粉的微信号
      wxAccountList:[],
      fanChanneloptions:[]
    };
  },
  created(){
        
  },
  methods: {
    showDialog(data, callback, fanChanneloptions) {
      if(!data || data == undefined || data == null){
          return;
      }
      this.fanChanneloptions = fanChanneloptions;
      this.title = "「"+data.importFanDate+"」微信导粉（只显示可接粉微信号）";
      this.reset();
      this.callback = callback;
      this.getListWxAccount();
      this.open = true;
    },
    reset(){
       
    },
    /** 提交按钮 */
    submitForm() {
       
    },
    onClosed() {
      this.reset();
    },
    cancel(){
        this.open = false;
        this.reset();
    },
    //获取所有可接粉的微信号
    getListWxAccount() {
        this.loading = true;
        getWxAccountAndSale().then((response) => {
            this.wxAccountList = response.rows;
            this.loading = false;
        });
    },
  },
};
</script>

<style lang="scss" scoped>

</style>
