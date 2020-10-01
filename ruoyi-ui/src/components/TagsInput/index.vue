<template>
  <div class="tags_input">

    <div style="border: 1px solid red;height: 50px"
      type="text"
      :placeholder="placeholder"
      v-model="value"
      @keyup.enter.native="add" contenteditable="true">
      <el-tag style="float: left;margin-top: 10px;margin-left: 10px"
        v-for="(tag,index) in list"
        :key="index"
        closable
        :disable-transitions="false"
        @close="handleClose(tag)">
        {{tag}}
      </el-tag>



    </div>
  </div>
</template>
<script>
  export default {
    name:'TagsInput',
    props: {
      listFilter: Boolean, // 是否去重
      defaulList: null,    // 默认值
      placeholder: null // 占位文字
    },
    data() {
      return {
        value: '',
        list: [],
      }
    },
    watch:{
      defaulList: {
        handler(newVal) {
          this.list = newVal
        },
        deep: true
      }
    },
    mounted() {
      this.list = this.defaulList
      this.$emit('input', this.list)  // 发送给父级的tagslist
    },
    methods: {
      add() {
        let self = this
        if (self.value != '' || self.value != ' ') {
          self.list.push(self.value)
          if (self.listFilter) {
            let arr = self.list
            let set = new Set(arr)
            self.list = Array.from(set)
          }
        }
        self.value = ''
        self.$emit('input', self.list)
      },
      handleClose(tag) {
        let self = this
        self.$emit('tagClose', tag)
        self.list.splice(self.list.indexOf(tag), 1);
      },

    }
  }
</script>
