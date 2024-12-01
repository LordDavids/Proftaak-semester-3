<script setup lang="ts">
import {ref, computed, watch, onMounted} from 'vue';
import {api} from "../services/apiClient.js";
import router from "../router/router.js";
import {Product} from "../enteties/Product.ts";
import {useRoute} from "vue-router";


const route = useRoute(); // Initialize the router


const products = ref<Product[]>([]);
const categoryId = ref<string>(route.params.id);
const pageSize = ref<number>(6);
const pageNumber = ref<number>(0);  // Track the current page number
const TotalElements = ref<number>();


const GetProducts = async() => {
  api.get(import.meta.env.VITE_API_PRODUCT +`/product/${categoryId.value}/${pageNumber.value}/${pageSize.value}`, {}, {
    headers: {
      'Access-Control-Allow-Origin': '*',
      'Content-Type': 'application/json'
    }
  }).then((response) => {
      console.log(response.data);
    products.value = response.data.products.map((item: any) => ({
      id: item.id,
      name: item.name,
      category: item.category,
      price: item.price,
      stock: item.stock
    }));
    TotalElements.value = response.data.totalElements;
  }).catch((error) => {
    console.log(error);
    router.push("/error");
  })
}
const moreProducts = () => {
  pageSize.value = pageSize.value + 6;
  GetProducts();
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
    const matchesPrice =
        (!filters.value.minPrice || product.price >= filters.value.minPrice) &&
        (!filters.value.maxPrice || product.price <= filters.value.maxPrice);

    return matchesName && matchesPrice;
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
    <div class="flex justify-center items-center mb-6">
      <div class="flex flex-wrap justify-between items-center mb-6 gap-4">
        <!-- Name filter input -->
        <input
            v-model="filters.name"
            type="text"
            class="border rounded px-4 py-2 w-full sm:w-auto"
            placeholder="Search by name"
        />
        <!-- Price range filter -->
        <div class="flex flex-wrap gap-2 w-full sm:w-auto">
          <input
              v-model.number="filters.minPrice"
              type="number"
              class="border rounded px-4 py-2 w-full sm:w-auto"
              placeholder="Min price"
          />
          <input
              v-model.number="filters.maxPrice"
              type="number"
              class="border rounded px-4 py-2 w-full sm:w-auto"
              placeholder="Max price"
          />
        </div>
      </div>
    </div>

    <div class="grid grid-cols-1 xsm:grid-cols-2 sm:grid-cols-2 md:grid-cols-3 gap-6 CustomProductCardSize ">
      <!-- Product Cards -->
      <router-link
          v-for="product in filteredProducts"
          :key="product.id"
          class="bg-white shadow-md rounded-lg p-4"
          :to="`/product/${product.id}`"
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
      </router-link>
    </div>
    <!-- Pagination -->
    <div v-if="products.values != null" class="flex justify-center items-center my-6">
      <button
          @click="moreProducts"
          v-if="products.length < TotalElements"
          class="relative inline-flex items-center justify-center p-0.5 mb-2 me-2 overflow-hidden text-sm font-medium text-gray-900 rounded-lg group bg-gradient-to-br from-purple-600 to-purple-600 group-hover:from-purple-600 group-hover:to-purple-600 hover:text-white focus:ring-4 focus:outline-none focus:ring-purple-300">
        <span class="relative px-5 py-2.5 transition-all ease-in duration-75 bg-white rounded-md group-hover:bg-opacity-0">
          Show more
        </span>
      </button>


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
