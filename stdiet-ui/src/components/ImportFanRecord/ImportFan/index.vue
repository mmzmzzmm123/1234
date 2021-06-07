<template>
    <!--  -->
    <!-- 添加或修改导粉管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1050px" :close-on-click-modal="false" append-to-body @closed="cancel">
      <!--<div style="margin-bottom: 20px;color:red">1、添加导粉记录时会根据进粉渠道、当前时间来自动确定所属直播间，当前时间段没有直播，则取上一次直播，若账号从未直播过，则为空</div>-->
        <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="账号渠道" prop="channel">
          <el-select
              v-model="queryParams.channel"
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
        </el-form-item>
        <el-form-item label="销售" prop="saleId" label-width="50px">
          <el-select
              v-model="queryParams.saleId"
              placeholder="请选择销售"
              clearable
              filterable
              size="small"
            >
              <el-option
                v-for="dict in preSaleIdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
        </el-form-item>
        <el-form-item label="微信账号" prop="wxAccount">
            <el-input v-model.trim="queryParams.wxAccount" placeholder="请输入微信号" />
        </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
        <div style="height: 500px; overflow: auto">
            <el-table v-loading="loading" :data="wxAccountList">
                <!--<el-table-column label="微信昵称" align="center" prop="wxNickName" />-->
                <el-table-column label="微信号" align="center" prop="wxAccount" width="160"/>
                <el-table-column label="销售" align="center" prop="saleName" width="120"/>
                <!--<el-table-column label="已导粉数量" align="center" prop="importFanNum" width="120"/>-->
                <el-table-column label="账号渠道" align="center" prop="importFanChannel" width="180">
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
                <!--<el-table-column label="直播间" align="center" prop="importFanLive" >
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
                </el-table-column>-->
                 <el-table-column label="导粉时间" align="center" prop="fanTime" width="240">
                   <template slot-scope="scope">
                  <el-time-picker
                    value-format="yyyy-MM-dd HH:mm"
                    v-model="scope.row.fanTime"
                    placeholder="选择导粉时间"
                    @change="autoSelectLive(scope.row)"
                  >
                  </el-time-picker>
                   </template>
                </el-table-column>
                <el-table-column label="导粉数量" align="center" prop="fanNum" width="180">
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
import { getAllLiveSchedulByDate,getLiveSchedulByTime } from "@/api/custom/liveSchedul";
import { mapGetters } from "vuex";
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
      importFanFlag: false,
      queryParams:{
          channel: null,
          saleId: null,
          wxAccount: null
      },
      fanPickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now() || time.getTime() < Date.now();
        },
      },
    };
  },
  created(){
    
        
  },
  computed: {
    ...mapGetters([
      // 售前字典
      "preSaleIdOptions"
    ]),
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
      //this.getAllLiveSchedulByDate();
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
        getWxAccountAndSale(this.queryParams).then((response) => {
            response.data.wxSaleAccountList.forEach((item,index) => {
                item.fanNum = 1;
                //item.importFanNum =  this.getTotalFanNum(response.data.fanNumList, item.id);
                item.importFanChannel = item.channel ? item.channel : null;
                item.importFanLive = null;
                item.fanTime = null;           
            });
            this.wxAccountList = response.data.wxSaleAccountList;
            //this.fanNumList = response.data.fanNumList;
            this.loading = false;
        });
    },
    handleQuery(){
        this.getListWxAccount();
    },
    resetQuery(){
        this.queryParams = {
          channel: null,
          saleId: null,
          wxAccount: null
        };
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
                message: '账号渠道不能为空',
                center: true
          });
          return;
        }
        if(row.fanTime == undefined || row.fanTime == null || row.fanTime == ""){
          this.$message({
                type: 'warning',
                message: '导粉时间不能为空',
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
          'fanNum': row.fanNum,
          "importTime": row.fanTime
        }
        addImportFanRecord(param).then((response) => {
            if(response.code == 200){
                this.msgSuccess("保存成功");
                //row.importFanChannel = null;
                row.importFanLive = null;
                row.importFanNum += row.fanNum;
                row.fanTime = null;
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
          'importFanChannels': "",
          'importFanLives': "",
          'wxAccountIds': "",
          'fanNums': "",
          "importTimes": ""
        }
        this.wxAccountList.forEach((item,index) => {
            if(item.fanTime != undefined && item.fanTime != null && item.fanTime != ""){
              param.importFanChannels += index != 0 ? (","+this.nullToString(item.importFanChannel)) : item.importFanChannel;
              param.importFanLives += index != 0 ? (","+this.nullToString(item.importFanLive)) : item.importFanLive;
              param.wxAccountIds += index != 0 ? (","+this.nullToString(item.id)) : item.id;
              param.fanNums += index != 0 ? (","+this.nullToString(item.fanNum)) : item.fanNum;
              param.importTimes += index != 0 ? (","+this.nullToString(item.fanTime)) : item.fanTime;
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
                                  //this.wxAccountList[i].importFanChannel = null;
                                  this.wxAccountList[i].importFanNum += this.wxAccountList[i].fanNum;
                                  this.wxAccountList[i].fanNum = 1;
                                  this.wxAccountList[i].fanTime = null;
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
        if(row.importFanChannel == undefined || row.importFanChannel == null || row.importFanChannel == ""){
           row.importFanLive = null;
           return;
        }
        row.fanTime = this.data.importFanDate + " " + row.fanTime.substring(row.fanTime.length - 5);
        //console.log(row.fanTime);
        getLiveSchedulByTime({'fanChannel':row.importFanChannel,'liveStartTimeString':encodeURIComponent(row.fanTime)}).then((response) => {
              if (response.code === 200) {
                 let live = response.data;
                 if(live != undefined && live != null){
                    row.importFanLive = live.id;
                 }else{
                   row.importFanLive = null;
                 }
              }else{
                row.importFanLive = null;
              }
        });

        /*if(row.importFanLive == undefined || row.importFanLive == null || row.importFanLive == ""){
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
        } */
    }
  },
};
</script>

<style lang="scss" scoped>

</style>
