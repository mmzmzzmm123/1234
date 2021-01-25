<template>
  <div id="deptTree">
    <div class="head-container">
      <el-input
        v-model="deptName"
        placeholder="请输入部门名称"
        clearable
        size="small"
        prefix-icon="el-icon-search"
        style="margin-bottom: 20px"
      />
    </div>
    <div class="head-container">
      <el-tree
        :data="deptOptions"
        :props="defaultProps"
        :filter-node-method="filterNode"
        ref="tree"
        node-key="label"
        :current-node-key="cuid"
        highlight-current
        default-expand-all
        @node-click="handleNodeClick"
      />
    </div>
  </div>
</template>

<script>
import { treeselect } from '@/api/system/dept'
export default {
  name: 'deptTree',
  data () {
    return{
      cuid: '',
      deptName:undefined,
      deptOptions:undefined,
      defaultProps: {
        children: "children",
        label: "label",
      },
      loading:false,
    }
  },
  created () {
    this.getTreeselect()
  },
  methods:{
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
        this.cuid = this.selectMinChild(response.data[0])
        this.$nextTick(()=>{
          this.$refs.tree.setCurrentKey(this.cuid)
        })
      });
    },
    //选中第一节点的最小节点
    selectMinChild(data){
      while(true){
        if(data.children&&data.children.length>0){
          data = data.children[0]
        }
        else {
          this.$emit('selectedDept', data.id)
          return data.label;
        }
      }
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      console.log(1)
      this.$emit('selectedDept',data.id)
      // this.queryParams.deptId = data.id;
      // this.getList();
    },
  }
}
</script>

<style scoped>

</style>
