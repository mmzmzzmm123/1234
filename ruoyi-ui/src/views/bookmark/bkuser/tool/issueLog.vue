<template>
  <div class="app-container ">
    <el-row :gutter="20" >
      <el-col :xs="24" :sm="{span: 16, push: 4}" :md="{span: 14, push: 5}"  :xl="{span: 10, push: 7}"  >
        <div class="block">
          <el-timeline>
            <el-timeline-item timestamp="2018/4/12" placement="top">
              <el-card>
                <h4>V1.5 更新日志</h4>
                <ul>
                  <li>新增IE浏览器版本过低提示页面</li>
                  <li>新增详细信息tab页签方式</li>
                  <li>新增解锁屏幕打开上次页签</li>
                  <li>数据监控默认账户密码防止越权访问</li>
                  <li>新增表格示例（导出选择列）</li>
                  <li>个人信息添加手机&邮箱重复验证</li>
                </ul>
              </el-card>
            </el-timeline-item>
            <el-timeline-item timestamp="2018/4/3" placement="top">
              <el-card>
                <h4>V1.4 更新日志</h4>
                <ul>
                <li>个人中心刷新后样式问题</li>
                <li>操作日志返回参数添加非空验证</li>
                <li>velocity剔除commons-collections版本，防止3.2.1版本的反序列化漏洞</li>
                <li>子表模板默认日期格式化</li>
                <li>代码生成预览语言根据后缀名高亮显示</li>
                </ul>
              </el-card>
            </el-timeline-item>
            <el-timeline-item timestamp="2018/4/2" placement="top">
              <el-card>
                <h4>V1.3 更新日志</h4>
                <ul>
                  <li>代码生成主子表相同字段导致数据问题</li>
                  <li>升级SpringBoot到最新版本2.2.13</li>
                  <li>升级shiro到最新版1.7.1 阻止身份认证绕过漏洞</li>
                  <li>升级bootstrapTable到最新版本v1.18.2</li>
                  <li>升级bootstrapTable相关组件到最新版本v1.18.2</li>
                </ul>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </div>
      </el-col>

    </el-row>
  </div>



</template>
<script>
    export default {
        name: 'areaTree',
        components: {},

        data: function () {
            return {}
        },
        methods: {}
    }
</script>
<style>

</style>
