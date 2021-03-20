<template>
  <div class="verify_view_wrapper">
    <el-tabs
      v-model="activeName"
      @tab-click="handleOnTabClick"
      :style="{ 'margin-right': '16px' }"
    >
      <el-tab-pane label="病理忌口" name="0">
        <PhysicalSignCom
          :selectedIgd.sync="selectedIgd"
          @onDayClick="handleOnDayClick"
          @onClick="handleOnClick"
        />
      </el-tab-pane>
      <el-tab-pane label="食材忌口" name="1">
        <IngredientSearchCom
          :selectedIgd.sync="selectedIgd"
          @onDayClick="handleOnDayClick"
          @onClick="handleOnClick"
        />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import VueScrollTo from "vue-scrollto";
import { createNamespacedHelpers } from "vuex";
const {
  mapActions,
  mapState,
  mapGetters,
  mapMutations,
} = createNamespacedHelpers("recipes");
import IngredientSearchCom from "./IngredientSearchCom";
import PhysicalSignCom from "./PhysicalSignCom";
export default {
  name: "VerifyView",
  data() {
    return {
      activeName: "0",
      selectedIgd: 0,
    };
  },
  components: { IngredientSearchCom, PhysicalSignCom },
  computed: {
    ...mapGetters(["verifyNotRecData"]),
  },
  methods: {
    handleOnTabClick(tab) {
      this.activeName = tab.name;
    },
    handleOnDayClick(data) {
      console.log(data);
      const { id, numDay } = data;
      if (this.selectedIgd !== id) {
        this.selectedIgd = id;
        this.setNotRecIgds({ data: [this.selectedIgd] });
      }

      this.setCurrentDay({ currentDay: numDay });
      VueScrollTo.scrollTo(`#recipes${numDay}`, 500, {
        container: "#recipes_content",
      });
    },
    handleOnClick(data) {
      this.selectedIgd = data.id === this.selectedIgd ? 0 : data.id;
      this.setNotRecIgds({ data: [this.selectedIgd] });
    },

    ...mapMutations(["setNotRecIgds", "setCurrentDay"]),
  },
};
</script>
<style lang="scss" scoped>
.verify_view_wrapper {
  height: calc(100vh - 32px);
  overflow: auto;
}
</style>
