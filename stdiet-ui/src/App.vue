<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
import { createNamespacedHelpers } from "vuex";
const { mapActions } = createNamespacedHelpers("global");
export default {
  name: "App",
  created() {
    // this.init();
  },
  methods: {
    ...mapActions(["init"]),
  },
};
</script>
