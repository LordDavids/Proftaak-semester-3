<script setup lang="ts">
import {api} from "../services/apiClient.ts";
import {ref, onMounted, onBeforeUnmount} from 'vue';
import router from "../router/router.ts";
import {Product} from "../enteties/Product.ts";
import {useRoute} from "vue-router";

const product = ref<Product | null>(null);
const route = useRoute(); // Initialize the router
const productId = ref<string>(route.params.product_id as string );
const productStock = ref<number | null>(null);
let ClosePage = false;

const loading = ref<boolean>(true);

const GetProduct = async() => {
  api.get(import.meta.env.VITE_API_PRODUCT +`/product/${productId.value}`, {
    headers: {
      'Access-Control-Allow-Origin': '*',
      'Content-Type': 'application/json'
    }
  }).then((response) => {
    product.value = {
      id: response.data.id,
      name: response.data.name,
      category: response.data.category,
      articleNumber: response.data.articleNumber,
      active: response.data.active,
      description: response.data.description,
      price: response.data.price,
      stock: response.data.stock,
    };
    loading.value = false;
    productStock.value = response.data.stock;
    createSocket();
  }).catch((error) => {
    console.log(error);
    router.push("/error");
  })
}
// socket

let retriesCount = 0;
const maxTries = 4;
const reconnectInterval = 3000; // Interval between reconnection attempts in ms

let socket: WebSocket;

function createSocket() {
  socket = new WebSocket('ws://localhost:8282/wbst/product-stock'); // Use `ws://` for WebSocket protocol

  socket.onopen = function () {
    console.log("Connection open");
    retriesCount = 0; // Reset retries on successful connection
    sendMessage(Number(route.params.product_id))
  };

  socket.onmessage = function (event) {
    try {
      productStock.value = event.data;
    } catch (err){
      console.log(err);
    }
  };

  socket.onerror = function () {
    console.log("Connection error:");
    retriesCount++;
  };

  socket.onclose = function () {
    console.log("Connection closed");
    if (retriesCount < maxTries && !ClosePage) {
      console.log(`Reconnecting attempt ${retriesCount}/${maxTries}...`);
      setTimeout(createSocket, reconnectInterval);
    } else {
      console.log("Max reconnection attempts reached. Giving up.");
    }
  };
}

// Initialize WebSocket connection
function sendMessage(message: any) {
  if (socket.readyState === WebSocket.OPEN) {
    socket.send(JSON.stringify(message));
  } else {
    console.log("Socket not open. Unable to send message:", message);
  }
}

// Ensure socket closes on reload or navigation
window.addEventListener('beforeunload', () => {
  if (socket && socket.readyState === WebSocket.OPEN) {
    socket.close(); // Gracefully close the socket
  }
});

onMounted(() => {
  GetProduct();
});

onBeforeUnmount(() => {
  ClosePage = true;
  if (socket && socket.readyState === WebSocket.OPEN) {
    socket.close(); // Gracefully close the socket
    console.log("WebSocket connection closed");
  }
});
</script>


<template>
  <div class="">
    <div class="container mx-auto px-4 py-8">
      <div class="flex flex-wrap -mx-4">
        <!-- Product Images -->
        <div class="w-full md:w-1/2 px-4 mb-8">
          <img src="../assets/testcpupng.png" alt="Product"
               class="w-full h-auto rounded-lg  mb-4" id="mainImage">
        </div>

        <!-- Product Details -->
        <div v-if="loading === false && product != null" class="w-full md:w-1/2 px-4">
          <h2 id="productName" class="text-3xl font-bold mb-2 pb-2 border-b border-gray-300">{{product.name}}</h2>
          <p id="articleNumber" class="text-gray-600 mb-4">EAN: {{ product.articleNumber }}</p>
          <div class="mb-4">
            <span id="price" class="text-2xl font-bold mr-2">€{{product.price}}</span>
          </div>
          <p id="description" class="text-gray-700 mb-6 border-b border-gray-300 mr-7 pb-2">{{product.description}}</p>

          <div class="mb-6 items-center gap-1">
            <label id="productStock" v-if="productStock != null" for="quantity" class="text-sm font-medium text-gray-700">Online: {{ productStock }} in stock</label>
          </div>

          <div class="mb-7">
            <div class="flex space-x-4">
              <!-- Button -->
              <button
                  :disabled="!product.active"
                  class="bg-indigo-600 flex gap-2 items-center text-white px-6 py-2 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                  :class="{'hover:bg-indigo-700': product.active, 'bg-gray-400 cursor-not-allowed': !product.active}"
              >
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                     stroke-width="1.5" stroke="currentColor" class="size-6">
                  <path stroke-linecap="round" stroke-linejoin="round"
                        d="M2.25 3h1.386c.51 0 .955.343 1.087.835l.383 1.437M7.5 14.25a3 3 0 0 0-3 3h15.75m-12.75-3h11.218c1.121-2.3 2.1-4.684 2.924-7.138a60.114 60.114 0 0 0-16.536-1.84M7.5 14.25 5.106 5.272M6 20.25a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0Zm12.75 0a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0Z" />
                </svg>
                Add to Cart
              </button>
              <!-- Availability Text -->
            </div>
            <p v-if="!product.active" class="text-gray-500 text-sm mt-2">
              This product is currently not available
            </p>
          </div>

          <div>
            <h3 class="text-lg font-semibold mb-2">Key Features:</h3>
            <ul class="list-disc list-inside text-gray-700">
              <li>Lorem ipsum odor amet</li>
              <li>consectetuer adipiscing elit.</li>
              <li>Lorem ipsum odor amet.</li>
              <li>Lorem ipsum odor amet, consectetuer adipiscing elit.</li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>