<script setup lang="ts">
import {ref, computed, watch, onMounted} from 'vue';
import {api} from "../services/apiClient.js";
import router from "../router/router.js";
import {Product} from "../enteties/Product.ts";
import {useRoute} from "vue-router";


const route = useRoute(); // Initialize the router

const products = ref<Product[]>([]);
const categoryId = ref<string>(route.params.id);
const pageNumber = 0;
const pageSize = 10;


const GetProducts = async() => {
  api.get(import.meta.env.VITE_API_PRODUCT +`/product?categoryId=${categoryId.value}&page=${pageNumber}&size=${pageSize}`, {}, {
    headers: {
      'Access-Control-Allow-Origin': '*',
      'Content-Type': 'application/json'
    }
  }).then((response) => {
    console.log(response.data.products);
    products.value = response.data.products.map((item: any) => ({
      id: item.id,
      name: item.name,
      category: item.category,
      price: item.price
    }));
    console.log(products.value);
  }).catch((error) => {
    console.log(error);
    router.push("/error");
  })
}

// Filters state
const filters = ref({
  name: '',
  minPrice: '',
  maxPrice: '',
  category: '',
});

// Filtered products based on filters
const filteredProducts = computed(() => {
  return products.value.filter((product) => {
    const matchesName = product.name.toLowerCase().includes(filters.value.name.toLowerCase());
    const matchesCategory = !filters.value.category || product.category.name === filters.value.category;
    const matchesPrice =
        (!filters.value.minPrice || product.price >= filters.value.minPrice) &&
        (!filters.value.maxPrice || product.price <= filters.value.maxPrice);

    return matchesName && matchesCategory && matchesPrice;
  });
});

watch(
    () => route.params.id,
    (newId) => {
      categoryId.value = newId;
      GetProducts(); // Fetch new products when the category changes
    }
);


onMounted(GetProducts);
</script>


<template>
  <div class="container mx-auto p-6">
<!--    <div class="flex justify-between items-center mb-6">-->
<!--      <div class="flex space-x-4">-->
<!--        &lt;!&ndash; Name filter input &ndash;&gt;-->
<!--        <input-->
<!--            v-model="filters.name"-->
<!--            type="text"-->
<!--            class="border rounded px-4 py-2"-->
<!--            placeholder="Search by name"-->
<!--        />-->
<!--        &lt;!&ndash; Price range filter &ndash;&gt;-->
<!--        <div class="flex space-x-2">-->
<!--          <input-->
<!--              v-model.number="filters.minPrice"-->
<!--              type="number"-->
<!--              class="border rounded px-4 py-2"-->
<!--              placeholder="Min price"-->
<!--          />-->
<!--          <input-->
<!--              v-model.number="filters.maxPrice"-->
<!--              type="number"-->
<!--              class="border rounded px-4 py-2"-->
<!--              placeholder="Max price"-->
<!--          />-->
<!--        </div>-->
<!--      </div>-->

<!--      &lt;!&ndash; Category dropdown filter &ndash;&gt;-->
<!--      <select-->
<!--          v-model="filters.category"-->
<!--          class="border rounded px-4 py-2"-->
<!--      >-->
<!--        <option value="">All Categories</option>-->
<!--        <option v-for="category in categories" :key="category.id" :value="category.name">-->
<!--          {{ category.name }}-->
<!--        </option>-->
<!--      </select>-->
<!--    </div>-->

    <div class="grid grid-cols-1 xsm:grid-cols-2 sm:grid-cols-2 md:grid-cols-3 gap-6 CustomProductCardSize ">
      <!-- Product Cards -->
      <div
          v-for="product in filteredProducts"
          :key="product.id"
          class="bg-white shadow-md rounded-lg p-4"
      >
        <!-- Image placeholder -->
        <div class="w-full h-48 bg-gray-300 mb-4 rounded-lg">
          <!-- Optionally, you can add a fallback image or a dynamic image here -->
          <img src="/src/assets/testcpupng.png" alt="Product Image" class="custom-image w-full h-full object-cover rounded-lg" />
        </div>

        <h2 class="text-lg font-semibold">{{ product.name }}</h2>
        <p class="text-gray-600">{{ product.category.name }}</p>
        <p class="text-xl font-bold text-green-600">€{{ product.price.toFixed(2) }}</p>
        <p class="text-gray-500">Stock: {{ product.stock }}</p>
      </div>
    </div>
  </div>
</template>



<style scoped>
/* Add any additional styles for the page */
@media (min-width: 300px) and (max-width: 480px) {
  .CustomProductCardSize{
    //padding-inline: 10vb;
  }
}


.custom-image {
  margin: 0;
  padding: 0;

}
</style>
