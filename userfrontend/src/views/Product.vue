<script setup lang="ts">
import {api} from "../services/apiClient.ts";
import router from "../router/router.ts";
import {ref, onMounted} from "vue";

const message = ref<string | null>(null);
const errorMessage = ref<string | null>(null);

const demoAccess = async() => {
  errorMessage.value = null;
  api.get("/demo", {
    headers: {
      'Access-Control-Allow-Origin': '*',
      'Content-Type': 'application/json'
    }
  }).then((response) => {
    message.value = "hello";
  }).catch((err) => {
    console.log(err);
    errorMessage.value = err.message;
  })
}
onMounted(demoAccess);

</script>

<template>
  <h1>Demo pagina</h1>

  <div v-if="errorMessage" class="text-center text-red-500">{{ errorMessage }}</div>
  <h2 v-else>{{ message }}</h2>
</template>

<style scoped>

</style>