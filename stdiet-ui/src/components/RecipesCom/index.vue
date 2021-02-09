<template>
  <div class="main">
    <div class="aspect">指标</div>
    <div class="recipes">
      <el-table :data="mData" border :span-method="spanMethod" size="mini">
        <el-table-column :label="`${name}第${num}天`"></el-table-column>
        <el-table-column label="菜品" prop="name"></el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
export default {
  name: "RecipesCom",
  props: {
    data: {
      type: Array,
      default: [],
      required: true,
    },
    name: {
      type: String,
      default: "",
    },
    num: {
      type: Number,
      default: 0,
    },
  },
  components: {},
  mounted() {
    // console.log(this.data);
  },
  data() {
    return {};
  },
  computed: {
    mData() {
      if (!this.data.dishes) {
        return [];
      }

      const mData = this.data.dishes.reduce((arr, cur) => {
        if (cur.id > 0) {
          cur.igdList.forEach((igd) => {
            if (igd.id > 0) {
              const tarDetail = cur.detail.find((det) => det.id == igd.id);
              arr.push({
                id: cur.id,
                name: cur.name,
                type: cur.type,
                isMain: cur.isMain,
                methods: cur.methods,
                igdId: igd.id,
                igdName: igd.name,
                proteinRatio: igd.proteinRatio,
                fatRatio: igd.fatRatio,
                carbonRatio: igd.carbonRatio,
                rec: igd.rec,
                notRec: igd.notRec,
                weight: tarDetail ? tarDetail.weight : igd.weight,
                cusWeight: tarDetail ? tarDetail.cus_weight : igd.cusWeight,
                cusUnit: tarDetail ? tarDetail.cus_unit : igd.cusUnit,
              });
            }
          });
        }
        return arr;
      }, []);
      // console.log(mData);
      return mData;
    },
  },
  methods: {
    spanMethod({ row, column, rowIndex, columnIndex }) {},
  },
};
</script>
<style rel="stylesheet/scss" lang="scss">
.main {
  .aspect {
  }

  .recipies {
  }
}
</style>
