<template>
  <el-dialog
    :title="title"
    :visible.sync="visible"
    width="680px"
    append-to-body
    :close-on-click-modal="false"
  >

  <VideoTemplate v-if="visible" :videoId="video.videoId" :playAuth="video.playAuth" ></VideoTemplate>
    
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>

import VideoTemplate from "@/components/VideoPlay/video";
export default {
  name: "VideoPlay",
  props: {
    
  },
  data() {
    return {
      title: "",
      visible:false,
      player: null,
      video: null
    }
  },
  created() {
  
  },
  computed: {
    
  },
  components:{
      VideoTemplate
  },
  methods: {
    showDialog(video) {
      this.player = null;
      this.video = video;
      this.title = this.video.title;
      this.visible = true;
      /*let self = this;
      let t = setTimeout(function(){
          self.createPlayer();
      },500);*/
    },
    // 取消按钮
    cancel() {
      this.visible = false;
      // this.reset();
    },
  },
  watch: {

  }
};
</script>
