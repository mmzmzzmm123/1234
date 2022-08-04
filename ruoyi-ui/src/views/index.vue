<template>
  <div class="app-container home">
    <template>
      <el-carousel :interval="4000" type="card" height="200px">
        <el-carousel-item v-for="item in imgList" :key="item">
          <img :src="item.src" style="height:100%;width:100%;" alt="图片丢失了" :title="item.title" />
        </el-carousel-item>
      </el-carousel>
    </template>
  </div>
</template>

<script>
export default {
  components: {},
  data() {
    return {
      imgList: [
        {
          src: require("@/assets/index/3.jpg"),
        },
        {
          src: require("@/assets/index/1.jpg"),
        },
        {
          src: require("@/assets/index/2.jpg"),
        }
      ]
    };
  },
  created() {},
  mounted() {},
  computed: {},
  methods: {}
};
</script>

<style>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>
