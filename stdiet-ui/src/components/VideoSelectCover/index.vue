<template>
<el-dialog title="选择视频封面（每隔两秒一张截图，共十张）" :visible.sync="open" width="650px" style="height: 80%; overflow: auto;" :close-on-click-modal="true" :show-close="true" append-to-body>
    <div v-if="!showFlag"><el-divider>{{showStatusText}}</el-divider></div>
    <div v-else class="demo-image__lazy" style="margin-left:20px;">
        <div v-for="url in videoSnapshotList" :key="url">
            <el-image :src="url" lazy style="width:550px;height:300px;margin-top:10px;cursor:pointer" title="点击选择" @click="selectVideoSnapshot(url)"></el-image>
            <el-divider></el-divider>
        </div>
    </div>
    <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    
</el-dialog>
  
</template>
<script>
  import axios from 'axios'
  import {getVideoSnapshot } from "@/api/custom/nutritionalVideo";
  export default {
    name: "VideoSelectCover",
    data () {
      return {
        open: false,
        videoData: null,
        callback: undefined,
        showFlag: false,
        showStatusText:"加载中...",
        videoSnapshotList:[]
      }
    },
    created(){
        
    },
    components: {
      
    },
    methods: {
        showDialog(data,callback){
            if(data && data.videoId != null){
                this.videoSnapshotList = [];
                this.showFlag = false;
                this.showStatusText = "加载中...";
                this.open = true;
                this.videoData = data;
                this.callback = callback;
                this.getVideoSnapshot();
            } 
        },
        getVideoSnapshot(){
            getVideoSnapshot(this.videoData.videoId).then(response => {
                if(response.code == 200 && response.data.length > 0){
                    this.videoSnapshotList = response.data;
                    this.showFlag = true;
                }else{
                    this.showStatusText = "暂无截图数据！"
                }
            })
        },
        selectVideoSnapshot(url){
            this.callback && this.callback(url);
            this.open = false;
        },
        cancel(){
            this.open = false;
        }
    }
  }
</script>