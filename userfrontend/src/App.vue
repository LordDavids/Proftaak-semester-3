<script setup lang="ts">
import NavBar from "./components/NavBar.vue";
</script>

<template>
  <nav>
    <NavBar />
  </nav>
  <main class="main-content">
    <RouterView />
  </main>
</template>

<style scoped>
/* Ensure the content is not hidden behind the navbar */
.main-content {
  //padding-top: 80px; /* Adjust based on your navbar's height */
}
</style>

