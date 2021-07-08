<template>
<div>
  <div class="prism-player" id="J_prismPlayer" :style="'height:'+height+'px;width:'+width+'px'"></div>
</div>
  
</template>
<script>

export default {
  name: "video",
  props: {
    videoId: {
        type: String,
        default: ''
    },
    playAuth: {
        type: String,
        default: ''
    },
    width:{
        type: Number,
        default: 630
    },
    height:{
        type: Number,
        default: 350
    }
  },
  data() {
    return{
        player: null
    }
  },
  created() {
      
     
  },
  mounted(){
      this.$nextTick(function(){
         this.createPlayer();
      });
  },
  computed: {
     
  },
  methods: {
    createPlayer(){
        this.player = new Aliplayer({
            /*id: 'J_prismPlayer',
            width: '100%',
            autoplay: false,
            //支持播放地址播放,此播放优先级最高
            source : this.video.playUrl*/
            "id": "J_prismPlayer",
            "vid": this.videoId,
            "playauth": this.playAuth,
            "qualitySort": "asc",
            "format": "mp4",
            "mediaType": "video",
            "width": "100%",
            "height": "500px",
            "autoplay": false,
            "isLive": false,
            "rePlay": false,
            "playsinline": true,
            "preload": true,
            "controlBarVisibility": "hover",
            "useH5Prism": true
        },function(){
            
        })
    }
  },
  watch: {

  }
};
</script>
