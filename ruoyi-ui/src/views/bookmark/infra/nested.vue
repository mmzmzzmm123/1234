<template>
  <draggable class="dragArea" tag="ul" :list="tasks" :group="{ name: 'g1' }">
    <li v-for="el in tasks" :key="el.name">
      <p>{{ el.name }}</p>
<!--      <nested-draggable :tasks="el.tasks" />-->

      <draggable :list="el.tasks" tag="ul" group="g2">
    <li v-for="el in el.tasks" :key="el.name">
      <p>{{ el.name }}</p>
    </li>
  </draggable>


    </li>
  </draggable>
</template>
<script>
  import draggable from "vuedraggable";
  export default {
    props: {
      tasks: {
        required: true,
        type: Array
      }
    },
    components: {
      draggable
    },
    name: "nested-draggable"
  };
</script>
<style scoped>
  .dragArea {
    min-height: 50px;
    outline: 1px dashed;
  }
</style>
