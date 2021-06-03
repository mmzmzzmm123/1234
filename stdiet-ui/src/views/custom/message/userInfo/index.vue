<template>
  <div v-loading="healthDataLoading">
    <HealthyView
      dev
      :data="healthyDataType === 0 ? healthyData : {}"
      v-show="healthyDataType === 0"
    />
    <BodySignView
      dev
      :data="healthyDataType === 1 ? healthyData : {}"
      v-show="healthyDataType === 1"
    />
  </div>
</template>
<script>
import { createNamespacedHelpers } from "vuex";
import HealthyView from "@/components/HealthyView";
import BodySignView from "@/components/BodySignView";
const {
  mapActions,
  mapState,
  mapMutations,
  mapGetters,
} = createNamespacedHelpers("message");
export default {
  name: "SignUserInfo",
  components: {
    HealthyView,
    BodySignView,
  },
  data() {
    return {};
  },
  computed: {
    ...mapState(["healthyData", "healthyDataType", "healthDataLoading"]),
  },
};
</script>
