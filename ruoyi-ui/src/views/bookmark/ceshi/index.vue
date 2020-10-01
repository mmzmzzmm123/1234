<template>
  <div>

    <div style="width: 500px">
      <tags-input
        v-model="tagList"
        :defaulList="defaulList"
        :listFilter=true
        placeholder="按enter键创建标签"
        @tagClose="tagClose"></tags-input>
    </div>
  </div>
</template>
<script>
  import TagsInput from '../../../components/TagsInput'
  export default {
    components: {
      TagsInput
    },
    data() {
      return {
        // 子组件返回的list
        tagList: null,
        // 付默认值
        defaulList:[],
        // 是否去重
        listFilter:false
      }
    },
    methods: {
      // 标签关闭方法
      tagClose(tag){
        console.log(tag)
      }
    }
  }
</script>
