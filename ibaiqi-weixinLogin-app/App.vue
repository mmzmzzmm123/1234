<script>
  import config from './config'
  import store from '@/store'
  import { getToken } from '@/utils/auth'

  export default {
    onLaunch: function() {
      this.initApp()
    },
    methods: {
      // 初始化应用
      initApp() {
        // 初始化应用配置
        this.initConfig()
        // 检查用户登录状态
        //#ifdef H5
        this.checkLogin()
        //#endif
      },
      initConfig() {
        this.globalData.config = config
      },
	  //想跳过判断不登陆也可访问，在permisson.js的whiteList白名单中设置
      checkLogin() {
        if (!getToken()) {
          this.$tab.reLaunch('/pages/login') 
        }
      }
    }
  }
</script>

<style lang="scss">
  @import '@/static/scss/index.scss'
</style>
