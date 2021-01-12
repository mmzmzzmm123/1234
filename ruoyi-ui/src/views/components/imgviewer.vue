<template>
  <div v-loading="loading">
    <div class="box">
      <img id="result" :src="src" class="obj">
    </div>
  </div>
</template>

<script>
const { Image } = require('image-js');
export default {
  name: "imgviewer",
  props: {
    src: String,
  },
  data() {
    return {
      loading:true,
    }
  },
  methods: {
    load(){
      let url = this.src
      async function process() {
        let image = await Image.load(url);
        let grey = image.grey().resize({width:500});
        document.getElementById('result').src = grey.toDataURL();
      }
      process()
    }
  }
}
</script>

<style scoped>
  .box{
    border: 1px solid #000;
    vertical-align:middle;
    background-color: #5a5e66;
    text-align:center;
    min-height:700px
  }
  .obj{
    object-fit:scale-down;
    vertical-align:middle;
    margin-top: 30px;
  }
</style>
