<template>
  <div class="app-container home">
    <el-row :gutter="20">
       <div class="index_img">
          <p>1.客户管理类</p>
          <img src="../../public/客户管理类.png" />
          <p>2. 电商管理类</p>
          <img src="../../public/电商管理类.png" />
          <p>3. 会员管理类</p>
          <img src="../../public/会员管理类.png" />
          <p>4. 充值系统类</p>
          <img src="../../public/充值系统类.png" />
          <p>5. 商户系统类</p>
          <img src="../../public/商户系统类.png" />
        </div>
    </el-row>
    <el-divider />
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.8.1",
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
  },
};
</script>

<style scoped lang="scss">

  .index_img {
    font-size: xx-large;
    img {
      width: 100%;
      height: 100%;
    }
  }
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

