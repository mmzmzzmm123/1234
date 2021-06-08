<template>
    <!--  -->
    <!-- 导粉通过率 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" :close-on-click-modal="true" append-to-body @closed="cancel">
        <div style="height: 500px; overflow: auto">
            <el-table v-loading="loading" :data="passRateList"> 
                <el-table-column label="账号渠道" align="center" prop="channelName" width="160"/>
                <el-table-column label="导粉量" align="center" prop="totalImportFanNum" width="120"/>
                <el-table-column label="进粉量" align="center" prop="totalPassFanNum" width="120"/>
                <el-table-column label="通过率" align="center" prop="passRate" width="120">
                    <template slot-scope="scope">
                        {{scope.row.passRate + '%'}}
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </el-dialog>
</template>
<script>
import {getImportFanPassRate} from "@/api/custom/fanStatistics";
export default {
  name: "ImportFanRate",
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
      data: null,
      passRateList: null
    };
  },
  created(){
    
        
  },
  computed: {
    
  },
  methods: {
    showDialog(data) {
      if(!data || data == undefined || data == null){
          return;
      }
      this.data = data;
      this.title = data.fanStartTime ? ("「"+data.fanStartTime+" 至 "+data.fanEndTime+"」进粉通过率统计") : "进粉通过率统计";
      this.getImportFanPassRate();
      this.open = true;
    },
    cancel(){
        this.open = false; 
    },
    //获取所有可接粉的微信号
    getImportFanPassRate() {
        this.loading = true;
        getImportFanPassRate(this.data).then((response) => {
            if(response.code == 200){
                this.passRateList = response.data;
            }
            this.loading = false;
        });
    },
  },
};
</script>

<style lang="scss" scoped>

</style>
