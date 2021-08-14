<template>
  <el-table
    :show-header="false"
    :data="data"
    border
    :cell-style="columnStyle"
    style="width: 100%"
  >
    <el-table-column width="100" prop="attr_name_one"> </el-table-column>
    <el-table-column prop="value_one">
      <template slot-scope="scope">
        <AutoHideMessage
          :data="scope.row.value_one == null ? '' : scope.row.value_one + ''"
          :maxLength="10"
        />
      </template>
    </el-table-column>
    <el-table-column width="100" prop="attr_name_two"></el-table-column>
    <el-table-column prop="value_two">
      <template slot-scope="scope">
        <AutoHideMessage
          :data="scope.row.value_two == null ? '' : scope.row.value_two + ''"
          :maxLength="10"
        />
      </template>
    </el-table-column>
    <el-table-column width="100" prop="attr_name_three"></el-table-column>
    <el-table-column prop="value_three">
      <template slot-scope="scope">
        <AutoHideMessage
          :data="
            scope.row.value_three == null ? '' : scope.row.value_three + ''
          "
          :maxLength="10"
        />
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import AutoHideMessage from "@/components/AutoHideMessage";
export default {
  name: "TableDetailMessage",
  data() {
    return {};
  },
  props: {
    data: {},
    maxLength: {
      type: Number,
      default: 20,
    },
  },
  computed: {},
  components: {
    AutoHideMessage,
  },
  methods: {
    // 自定义列背景色
    columnStyle({ row, column, rowIndex, columnIndex }) {
      if (
        columnIndex == 0 ||
        columnIndex == 2 ||
        columnIndex == 4 ||
        columnIndex == 6
      ) {
        //第三第四列的背景色就改变了2和3都是列数的下标
        return "background:#f3f6fc;font-weight:bold";
      } else {
        return "background:#ffffff;";
      }
    },
  },
};
</script>

<style>
</style>
