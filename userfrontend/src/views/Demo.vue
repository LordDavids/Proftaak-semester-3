<script setup lang="ts">

import {api} from "../services/apiClient.ts";
import router from "../router/router.ts";
import {onMounted, ref} from "vue";

const message = ref('')

const GetDemoInfo = async() => {
  api.get(import.meta.env.VITE_API_PRODUCT +'/product/demo', {
    headers: {
      'Access-Control-Allow-Origin': '*',
      'Content-Type': 'application/json'
    }
  }).then((response) => {
    message.value = response.data;

  }).catch((error) => {
    console.log(error);
    router.push("/error");
  })
}
onMounted(() => {
  GetDemoInfo();
})
</script>

<template>
  <h1>for this page you need to be logged in</h1>
  <h2>{{message}}</h2>
</template>

<style scoped>

</style>