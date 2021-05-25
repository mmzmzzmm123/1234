<template>
    <!--  -->
    <!-- 添加或修改导粉管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1100px" :close-on-click-modal="false" append-to-body @closed="cancel">
        <div style="height: 500px; overflow: auto">
            <el-table v-loading="loading" :data="wxAccountList">
                <!--<el-table-column label="微信昵称" align="center" prop="wxNickName" />-->
                <el-table-column label="微信号" align="center" prop="wxAccount" width="160"/>
                <el-table-column label="销售" align="center" prop="saleName" width="120"/>
                <el-table-column label="已导粉数量" align="center" prop="importFanNum" width="120"/>
                <el-table-column label="进粉渠道" align="center" prop="importFanChannel" >
                    <template slot-scope="scope">
                            <el-select
                        v-model="scope.row.importFanChannel"
                        placeholder="请选择账号渠道"
                        clearable
                        filterable
                        size="small"
                        @change="autoSelectLive(scope.row)"
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
                <el-table-column label="直播间" align="center" prop="importFanLive" >
                    <template slot-scope="scope">
                        <el-select
                          v-model="scope.row.importFanLive"
                          placeholder="请选择直播间"
                          clearable
                          filterable
                          size="small"
                        >
                        <el-option
                            v-for="dict in liveSchedukList"
                            :key="dict.id"
                            :label="dict.liveRoomName"
                            :value="parseInt(dict.id)"
                        />
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column label="导粉数量" align="center" prop="fanNum" width="200">
                    <template slot-scope="scope">
                            <el-input-number v-model="scope.row.fanNum" :min="1" :max="10000" label="导粉数量" style="width:160px"></el-input-number>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="100">
                    <template slot-scope="scope">
                    <el-button
                        size="normal"
                        type="text"
                        @click="addImportFanRecord(scope.row)"
                        v-hasPermi="['custom:importFanRecord:add']"
                    >保存</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button
          type="primary"
          @click="addMuchImport"
          v-hasPermi="['custom:importFanRecord:add']"
        >全部保存</el-button>
          <el-button @click="cancel">取消</el-button>
      </div>
    </el-dialog>
</template>
<script>
import { addImportFanRecord,getWxAccountAndSale } from "@/api/custom/importFanRecord";
import { getAllLiveSchedulByDate } from "@/api/custom/liveSchedul";
import dayjs from "dayjs";
const nowTime = dayjs().format("YYYY-MM-DD HH:mm");
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
      fanChanneloptions:[],
      //当日直播间
      liveSchedukList:[],
      //总导粉统计
      fanNumList:[],
      //是否进行了导粉操作
      importFanFlag: false
    };
  },
  created(){
    
        
  },
  methods: {
    showDialog(data, callback, fanChanneloptions) {
      if(!data || data == undefined || data == null){
          return;
      }
      this.importFanFlag = false;
      this.data = data;
      this.fanChanneloptions = fanChanneloptions;
      this.title = "「"+data.importFanDate+"」微信导粉（只显示可接粉微信号）";
      this.reset();
      this.callback = callback;
      this.getListWxAccount();
      this.getAllLiveSchedulByDate();
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
        if(this.importFanFlag){
          this.callback && this.callback();
        }
        this.reset();
    },
    //获取所有可接粉的微信号
    getListWxAccount() {
        this.loading = true;
        getWxAccountAndSale({'importFanDate': this.data.importFanDate}).then((response) => {
            response.data.wxSaleAccountList.forEach((item,index) => {
                item.fanNum = 1;
                item.importFanNum =  this.getTotalFanNum(response.data.fanNumList, item.id);
                item.importFanChannel = null;
                item.importFanLive = null;
            });
            this.wxAccountList = response.data.wxSaleAccountList;
            this.fanNumList = response.data.fanNumList;
            this.loading = false;
        });
    },
    getAllLiveSchedulByDate(){
      getAllLiveSchedulByDate({'liveSchedulDate':this.data.importFanDate}).then((response) => {
          response.data.forEach((item,index) => {
            item.liveRoomName = item.liveNutritionistName + " " + item.liveRoomName;
          });
          this.liveSchedukList = response.data;  
      });
    },
    getTotalFanNum(fanNumList, wxAccountId){
        if(wxAccountId == null || fanNumList == null){
          return 0;
        }
        let num = 0;
        fanNumList.forEach((item,index) => {
            if(item.wxAccountId == wxAccountId){
                num = item.fanNum;
            }
        });
        return num;
    },
    addImportFanRecord(row){
        //账号渠道、进粉数量不能为空
        if(row.importFanChannel == undefined || row.importFanChannel == null || row.importFanChannel == ""){
          this.$message({
                type: 'warning',
                message: '进粉渠道不能为空',
                center: true
          });
          return;
        }
        if(row.fanNum == undefined || row.fanNum == null || row.fanNum == ""){
          this.$message({
                type: 'warning',
                message: '进粉数量不能为空',
                center: true
          });
          return;
        }
        let param = {
          'importFanDate': this.data.importFanDate,
          'importFanChannel': row.importFanChannel,
          'importFanLive': row.importFanLive,
          'wxAccountId': row.id,
          'fanNum': row.fanNum
        }
        addImportFanRecord(param).then((response) => {
            if(response.code == 200){
                this.msgSuccess("保存成功");
                row.importFanChannel = null;
                row.importFanLive = null;
                row.importFanNum += row.fanNum;
                row.fanNum = 1;
                this.importFanFlag = true;
            }
        });
    },
    addMuchImport(){
        if(this.wxAccountList == null || this.wxAccountList.length == 0){
            this.$message({
                type: 'warning',
                message: '微信号不存在',
                center: true
            });
            return;
        }
        let param = {
          'importFanDate': this.data.importFanDate,
          'importFanChannels': null,
          'importFanLives': null,
          'wxAccountIds': null,
          'fanNums': null
        }
        this.wxAccountList.forEach((item,index) => {
            if(item.importFanChannel != undefined && item.importFanChannel != null && item.importFanChannel != ""){
              param.importFanChannels += param.importFanChannels != null ? (","+this.nullToString(item.importFanChannel)) : item.importFanChannel;
              param.importFanLives += param.importFanLives != null ? (","+this.nullToString(item.importFanLive)) : item.importFanLive;
              param.wxAccountIds += param.wxAccountIds != null ? (","+this.nullToString(item.id)) : item.id;
              param.fanNums += param.fanNums != null ? (","+this.nullToString(item.fanNum)) : item.fanNum;
            }
            if(index == this.wxAccountList.length -1){
                if(param.importFanChannels == null){
                    this.$message({
                      type: 'warning',
                      message: '请至少存在一条数据',
                      center: true
                    });
                }else{
                    addImportFanRecord(param).then((response) => {
                        if(response.code == 200){
                            this.msgSuccess("全部保存成功");
                            this.importFanFlag = true;
                            for(let i=0; i < this.wxAccountList.length; i++){
                                  this.wxAccountList[i].importFanLive = null;
                                  this.wxAccountList[i].importFanChannel = null;
                                  this.wxAccountList[i].importFanNum += this.wxAccountList[i].fanNum;
                                  this.wxAccountList[i].fanNum = 1;
                                  //this.$set('wxAccountList', index, item);
                            }
                        }
                    });
                }
            }
        });
    },
    nullToString(value){
      return (value == undefined || value == null || value == "") ? "" : value;
    },
    autoSelectLive(row){
        if(row.importFanLive == undefined || row.importFanLive == null || row.importFanLive == ""){
            if(row.importFanChannel == undefined || row.importFanChannel == null || row.importFanChannel == ""){
              row.importFanLive = null;
            }else{
              let now = dayjs();//"2021-05-19 16:42"
              //console.log(now.unix());
              if(this.liveSchedukList != null){
                  this.liveSchedukList.forEach((item,index) => {
                    if(item.fanChannel == row.importFanChannel && now.unix() >= dayjs(item.liveStartTime).unix() && dayjs(item.liveEndTime).unix() >= now.unix()){
                        row.importFanLive = item.id;
                        return;
                    }
                    if(index == this.liveSchedukList.length - 1){
                        row.importFanLive = null;
                    }
                  });
              }
            }
        } 
    }
  },
};
</script>

<style lang="scss" scoped>

</style>
