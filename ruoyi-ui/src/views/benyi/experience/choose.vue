<template>
  <section class="container flex">
		<aside class="aside navs">
      <h3 class="title">体验申请</h3>
      <router-link  class="btn" :class="{'active': active === 0}" :to="href_apply">入班半日体验申请</router-link>
      <router-link class="btn" :class="{'active': active === 1}" :to="href_result">申请结果查询</router-link>
    </aside>
		<section class="flex flex-direction right">
			<div class="content">
        <router-view />
      </div>
		</section>
    <span class="el-icon-menu menu-icon" @click="showMenu"></span>
    <el-drawer
      :direction="`ltr`"
      :modal="false"
      title="我是标题"
      :visible.sync="drawer"
      class="navs"
      :size="`180px`"
      :with-header="false">
      <h3 class="title">体验申请</h3>
      <router-link  class="btn" :class="{'active': active === 0}" :to="href_apply">入班半日体验申请</router-link>
      <router-link class="btn" :class="{'active': active === 1}" :to="href_result">申请结果查询</router-link>
    </el-drawer>
	</section>
</template>
<script>
export default {
  name: "Choose",
  data() {
    return {
      href_apply: "",
      href_result: "",
      active: 0,
      drawer: true
    };
  },
  created() {
    const sid = this.$route.params && this.$route.params.id;
    this.href_apply = "/experience/apply/" + sid;
    this.href_result = "/experience/result/" + sid;
  },
  mounted () {
    this.getNav();
  },
  watch: {
    $route: 'getNav'
  },
  methods: {
    getNav () {
      const path = this.$route.path;
      if (path.indexOf('apply') > -1) {
        this.active = 0;
      } else {
        this.active = 1;
      }
    },
    showMenu () {
      this.drawer = true;
    }
  }
};
</script>

<style lang="scss" scoped>
.container {
  height: 100vh;
  position: relative;
  .menu-icon {
    display: none;
  }
  .el-drawer__wrapper {
    display: none;
  }
}
.flex {
  display: flex;
}
.flex-direction {
  flex-direction: column;
}
.right {
  flex: 1;
}
.content {
  flex: 1;
}
.content > * {
  box-sizing: border-box;
}
.aside {
  padding: 0;
  margin: 0;
  flex: 0 0 200px;
  background:#011627;
}
.navs {
  .title {
    font-size: 18px;
    font-weight: 600;
    color: #fff;
    text-align: center;
    margin: 0;
    padding: 15px 0;
    background: #0a56ca;
  }
  .btn {
    position: relative;
    display: block;
    width: 100%;
    height: 44px;
    line-height: 44px;
    text-align: center;
    font-size: 14px;
    text-decoration: none;
    color: #fff;
    &::after {
      display: none;
    }
    transition: all .5s;
    &:hover {
      background: #77a4ff;
    }
    &.active {
      &::before {
        content: '';
        position: absolute;
        right: 0;
        top: 18px;
        border: 5px solid #fff;
        border-top-color: transparent;
        border-left-color: transparent;
        border-bottom-color: transparent;
      }
      background: #000;
    }
  }
}
@media screen and (max-width: 768.89px) {
  .container {
    .aside {
      display: none;
    }
    .menu-icon {
      position: absolute;
      display: block;
      left: 15px;
      top: 20px;
      font-size: 34px;
      color: #0a56ca;
      cursor: pointer;
    }
    .el-drawer__wrapper {
      display: block;
    }
    ::v-deep .el-drawer {
      background:#011627;
    }
  }
}
</style>