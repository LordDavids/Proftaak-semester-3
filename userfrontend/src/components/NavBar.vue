<script setup lang="ts">
import { ref } from 'vue';
import user from "../userStorage.ts";
import router from "../router/router.ts";
import {api} from "../services/apiClient.ts";
import ProductCategoryDropDown from "../components/ProductCategoryDropDown.vue";


const isMenuOpen = ref(false);

const toggleMenu = () => {
  isMenuOpen.value = !isMenuOpen.value;
};

const logout = async() => {
  api.post(import.meta.env.VITE_API_USER +"/auth/logout", {}, {
    headers: {
      'Access-Control-Allow-Origin': '*',
      'Content-Type': 'application/json'
    }
  }).then(() => {
    user.value = null;
    router.push("/login");
  }).catch((error) => {
    console.log(error);
    router.push("/error");
  })

}
</script>

<template>
  <nav class="bg-white p-4 w-full border-b border-gray-300">
    <div class="grid grid-cols-3 gap-1 ustify-evenly w-full items-center">

      <!-- Links in the center (shown on medium screens and above) -->
      <div class="text-sm hidden md:flex space-x-6 ml-6 " >
        <router-link to="/" class="text-black group ">Home
          <div class="bg-black h-[2px] w-0 group-hover:w-full transition-all duration-500"></div></router-link>
        <ProductCategoryDropDown/>
        <router-link to="/" class="text-black group ">About Us
          <div class="bg-black h-[2px] w-0 group-hover:w-full transition-all duration-500"></div></router-link>
      </div>


      <!-- Hamburger menu for small screens -->
      <div class="md:hidden">
        <button @click="toggleMenu" class="text-GRAY-500 hover:text-black focus:outline-none">
          <!-- Hamburger icon -->
          <svg class="h-6 w-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16m-7 6h7"></path>
          </svg>
        </button>
      </div>

      <!-- Name on the left -->
      <div class="flex justify-center">
        <h1 class=" text-xl font-bold">Fontys Solution</h1>
      </div>


      <!-- Login button on the right (hugging the right edge) -->
      <div class="text-sm hidden md:flex justify-end mr-6">
        <router-link id="login" v-if="!user" to="/login" class="text-black group ">Login
          <div class="bg-black h-[2px] w-0 group-hover:w-full transition-all duration-500"></div></router-link>
        <button id="logout" @click="logout" v-if="user">Sign out</button>
      </div>
    </div>


  </nav>

  <!-- Dropdown menu for small screens -->
  <div v-if="isMenuOpen" class="flex flex-col md:hidden space-y-2 border-b border-gray-300" >
    <div class="px-2 pt-2 pb-3 space-y-1 sm:px-3">
      <router-link to="/" class="hover:underline block px-3 py-2 rounded-md text-base font-medium text-gray-700 hover:text-black">Home</router-link>
      <router-link to="/about" class="hover:underline block px-3 py-2 rounded-md text-base font-medium text-gray-700 hover:text-black">About</router-link>
      <router-link to="/services" class="hover:underline block px-3 py-2 rounded-md text-base font-medium text-gray-700 hover:text-black">Services</router-link>
      <router-link to="/contact" class="hover:underline block px-3 py-2 rounded-md text-base font-medium text-gray-700 hover:text-black">Contact</router-link>

      <div class="flex pt-4 pb-2 border-t border-gray-300">
      <router-link
          to="/login"
          class=" inline-flex justify-center items-center w-[100px] rounded-full border-2 border-[#663366] px-8 pb-[6px] pt-2 text-xs font-medium uppercase leading-normal text-neutral-800 transition duration-150 ease-in-out hover:border-[#663366] hover:bg-[#663366] hover:text-white focus:border-[#663366] focus:bg-[#663366] focus:text-white focus:outline-none focus:ring-0 active:border-[#663366] active:text-white motion-reduce:transition-none dark:text-neutral-600 dark:hover:bg-[#663366] dark:focus:bg-[#663366]">
        Login
      </router-link>
      </div>

    </div>
  </div>

</template>

<style scoped>
/* Custom styles if needed */

</style>
