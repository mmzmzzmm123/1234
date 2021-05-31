<template>
  <div class="message_browser_wrapper">
    <div class="topic_list">

    </div>
    <div class="topic_detail"></div>
  </div>
</template>
<script>
import { createNamespacedHelpers } from "vuex";
const {
  mapActions,
  mapState,
  mapMutations,
  mapGetters,
} = createNamespacedHelpers("message");
export default {
  data() {
    return {};
  },
  created() {
    this.init();
  },
  computed: {
    ...mapState([]),
  },
  methods: {
    ...mapActions(["init"]),
    ...mapMutations(["clean"]),
  },
};
</script>
<style lang="scss" scoped>
.message_browser_wrapper {
  display: flex;
  .topic_list {
    flex: 2;
  }

  .topic_detail {
    flex: 3;
    background: gray;
  }
}
</style>
