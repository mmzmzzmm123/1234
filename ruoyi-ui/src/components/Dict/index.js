import Vue from 'vue'
import VueDataDict from 'vue-data-dict'
import { getDicts as getDicts } from '@/api/system/dict/data'
import { DictTableColumnFormatter } from './formatter/DictTableColumnFormatter'

function install() {
  Vue.use(VueDataDict, {
    onCreated(dict) {
      dict.owner.tableDictColumnFormatter = DictTableColumnFormatter
    },
    metas: {
      '*': {
        labelField: 'dictLabel',
        valueField: 'dictValue',
        request(dictMeta) {
          return getDicts(dictMeta.type).then(res => res.data)
        },
      },
    },
  })
}

export default {
  install,
}
