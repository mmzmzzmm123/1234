<template>

  <el-select
    v-model="currentValue"
    :multiple="multi"
    :remote-method="listRepoAll"
    filterable
    remote
    reserve-keyword
    clearable
    automatic-dropdown
    placeholder="选择或搜索题库"
    class="filter-item"
    @change="handlerChange"
  >
    <el-option
      v-for="item in dataList"
      :key="item.id"
      :label="item.title"
      :value="item.id"
    />
  </el-select>

</template>

<script>

import { listRepoAll } from "@/api/exammanage/repo";

export default {
  name: 'RepoSelect',
  props: {
    multi: {
      type: Boolean,
      default: false
    },
    value: String | Array,
    excludes: Array
  },
  data() {
    return {
      // 下拉选项值
      dataList: [],
      currentValue: []
    }
  },

  watch: {
    // 检测查询变化
    value: {
      handler() {
        this.currentValue = this.value
      }
    }
  },
  created() {
    this.currentValue = this.value
    this.listRepoAll()
  },
  methods: {

    listRepoAll(q) {
      listRepoAll({ current: 1, size: 1000, params: { title: q, excludes: this.excludes }}).then(res => {
        this.dataList = res.data
      })
    },
    handlerChange(e) {
      const obj = this.dataList.find((item) => {
        return item.id === e
      })

      this.$emit('change', obj)
      this.$emit('input', e)
    }
  }
}
</script>
