<template>
  <el-dropdown
    trigger="click"
    @command="handleSetLanguage"
  >
    <div>
      <svg-icon
        class-name="size-icon"
        icon-class="language"
      />
    </div>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item
        v-for="item of languageOptions"
        :key="item.value"
        :disabled="lang===item.value"
        :command="item.value"
      >
        {{
          item.label }}
      </el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
export default {
  data() {
    return {
      languageOptions: [
        { label: '中文', value: 'zh' },
        { label: 'English', value: 'en' }
      ]
    }
  },
  computed: {
    lang() {
      return this.$store.getters.lang
    }
  },
  methods: {
    handleSetLanguage(lang) {
      this.$store.dispatch('app/setLang', lang)
      this.$i18n.locale = lang
      const msg = lang == 'zh' ? '语言切换成功' : 'Switch Language Success'
      this.$message({
        message: msg,
        type: 'success'
      })
    }
  }
}
</script>
