<script setup lang="ts">

import {api} from "../services/apiClient.ts";
import {onMounted, ref} from 'vue';
import router from "../router/router.ts";
import {Category} from "../enteties/Category.ts";

const categories = ref<Category[]>([]);

const GetCategory = async() => {
  api.get(import.meta.env.VITE_API_PRODUCT +"/category", {}, {
    headers: {
      'Access-Control-Allow-Origin': '*',
      'Content-Type': 'application/json'
    }
  }).then((response) => {
    // console.log(response.data);
    categories.value = response.data.map((item: any) => ({
      id: item.category_id,
      name: item.name
    }));
  }).catch((error) => {
    console.log(error);
    router.push("/error");
  })
}
onMounted(GetCategory);

</script>

<template>
  <nav class="ProductCategoryDropDown">
    <div class="group relative cursor-pointer">

      <div class="flex items-center justify-between">
        <a class="menu-hover lg:mx-4" onClick="">
          Products
        </a>

      </div>

      <div
          class=".CategoryDropdown invisible absolute z-2 flex w-full flex-col bg-gray-100 py-1 px-1 text-gray-800 shadow-xl group-hover:visible">

        <router-link :to="`/product/${category.name}/${category.id}`" class="my-2 block border-b border-gray-100 py-1 font-semibold text-gray-500 hover:text-black md:mx-2"
            v-for="category in categories">
          {{category.name}}
        </router-link>
      </div>
    </div>
  </nav>
</template>

<style scoped>
.CategoryDropdown {
  width: fit-content;   /* Ensures the dropdown expands based on content */
  min-width: 150px;     /* Ensures it never goes smaller than 150px */
}
</style>