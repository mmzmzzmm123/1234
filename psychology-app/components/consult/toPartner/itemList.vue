<template>
  <view v-if="list.length > 0" class="cu-list menu solid-bottom">
    <view v-for="(item,index) in list" :key="index" class="cu-item">
      <view class="content">
        <view class="text-sm">
          {{ [2,3].includes(type) ? item.param1 : item.startTime + ' ~ ' + item.endTime }}
        </view>

        <view class="text-sm" v-if="[1,2,4,5,6].includes(type)">
          <template v-if="type === 1">
            {{ item.param1 + '|' + item.param2 + '|' + item.param3 }}
          </template>
          <template v-else-if="type === 2">
            编号：{{ item.param2 }}
          </template>
          <template v-else-if="type === 4">
            {{ item.param1 + '|' + item.param2 }}
          </template>
          <template v-else-if="type === 5">
            个人咨询 （共{{ item.num }}个小时）
          </template>
          <template v-else>
            {{ item.param2 + '|' + item.param3 }}
          </template>
        </view>

        <view class="text-sm" v-if="type === 6">
          {{ item.param1 }} （共{{ item.num }}个小时）
        </view>
      </view>
      <view class="action">
        <text class="text-lg text-gray cuIcon-edit margin-right-sm" @tap="edit(item)"/>
        <text class="text-lg text-gray cuIcon-delete" @tap="del(item)"/>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  name: "ItemForm",
  props: {
    type: {
      type: Number,
      default: 1,
    },
    list: {
      type: Array,
      default: [],
    },
  },
  methods: {
    edit(item) {
      this.$emit('edit', item)
    },
    del(item) {
      this.$emit('del', item)
    }
  }
}
</script>

<style scoped>

</style>