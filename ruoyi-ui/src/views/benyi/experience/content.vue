<template>
  <div class="content">
    <h3 class="title">{{title}}</h3>
    <div class="artic">
      <p class="part" v-html="content"></p>
    </div>
  </div>
</template>
<script>
import { getHalfdayplan_query } from "@/api/benyi/halfdayplan";

export default {
  name: "content",
  data() {
    return {
      id: "",
      title: "",
      content: "",
    };
  },
  created() {
    this.id = this.$route.params && this.$route.params.id;
    this.getInfo();
  },
  methods: {
    /** 查询体验内容 */
    getInfo() {
      getHalfdayplan_query(this.id).then((response) => {
        if (response.code == "200") {
          this.title = response.data.title;
          this.content = response.data.content;
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.content {
  padding: 0 40px;
  .title {
    text-align: center;
    font-size: 30px;
    margin-bottom: 16px;
  }

  .artic {
    font-size: 14px;
    .part {
      line-height: 22px;
      padding-bottom: 15px;
    }
  }
}
</style>
