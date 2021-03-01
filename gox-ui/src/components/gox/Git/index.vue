<template>
  <div>
    <svg-icon icon-class="github" @click="goto"/>
  </div>
</template>

<script>
  export default {
    name: 'goxGit',
    data() {
      return {
        url: 'https://gitee.com/y_project/gox-Vue'
      }
    },
    methods: {
      goto() {
        window.open(this.url)
      }
    }
  }
</script>
