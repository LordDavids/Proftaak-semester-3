<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router';
import user from "../userStorage.ts";
import {api} from "../services/apiClient.ts";
import { User } from "../enteties/User.ts"

const email = ref('')
const password = ref('')
const errorMessage = ref("")
const router = useRouter(); // Initialize the router


const handleLogin = async() => {
  errorMessage.value = ""
  // Send the email and password to the server
  api.post<User>(import.meta.env.VITE_API_USER + "/auth/authenticate", {
    email: email.value,
    password: password.value,
  }).then((response: any) => {
    user.value = response.data;
    // Redirect the user to the home page
    router.push("/");
  }).catch((error: any) => {
    if (!error?.response?.data) {
      console.error(error);
      errorMessage.value = "An unknown error occurred. Please try again later.";
      return;
    }

    if (error.response.status === 500) {
      console.error(error)
      errorMessage.value = "An error occurred on the server. Please try again later.";
      return;
    }

    errorMessage.value = error.response.data.message;
  });
};

</script>

<template>
  <div class="flex items-center justify-center min-h-screen bg-gray-100">
    <div class="bg-white shadow-md rounded-lg p-8 w-96">
      <h2 class="text-2xl text-purple-800 font-bold mb-6 text-center">Login</h2>
      <span class="text-red-500 text-center" v-if="errorMessage"> {{errorMessage}} </span>
      <form @submit.prevent="handleLogin">
        <div class="mb-4">
          <label for="email" class="block text-sm font-medium text-gray-700">Email</label>
          <input
              type="text"
              id="email"
              v-model="email"
              required
              class="mt-1 block w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Enter your email"
          />
        </div>

        <div class="mb-4">
          <label for="password" class="block text-sm font-medium text-gray-700">Password</label>
          <input
              type="password"
              id="password"
              v-model="password"
              required
              class="mt-1 block w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Enter your password"
          />
        </div>

        <button
            type="submit"
            class="w-full bg-purple-800 text-white font-semibold py-2 rounded-md hover:bg-purple-900 transition duration-200">
          Login
        </button>
      </form>

      <p class="mt-4 text-sm text-center">
        Don't have an account?
        <router-link to="/register" class="text-blue-500 hover:underline">Sign up</router-link>
      </p>
    </div>
  </div>
</template>

<style scoped>
/* Add any additional styles here if necessary */
</style>
