<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import user from "../userStorage.ts";
import { api } from "../services/apiClient.ts";
import { User } from "../enteties/User.ts";

const firstName = ref('');
const lastName = ref('');
const email = ref('');
const password = ref('');
const confirmPassword = ref('');
const phoneNumber = ref('');
const errorMessage = ref('');
const router = useRouter(); // Initialize the router

const handleRegister = async () => {
  errorMessage.value = '';

  // Check if the passwords match
  if (password.value !== confirmPassword.value) {
    errorMessage.value = 'Passwords do not match!';
    return;
  }
  console.log(firstName.value, lastName.value, email.value, password.value, phoneNumber.value);
  // Send the registration details to the server
  api.post<User>('/auth/register', {
    first_name: firstName.value,
    lastname: lastName.value,
    email: email.value,
    password: password.value,
    phone_number: phoneNumber.value
  })
      .then((response: any) => {
        user.value = response.data; // Store user data after registration
        // Redirect the user to the home page after successful registration
        router.push('/');
      })
      .catch((error: any) => {
        if (!error?.response?.data) {
          console.error(error);
          errorMessage.value = 'An unknown error occurred. Please try again later.';
          return;
        }

        if (error.response.status === 500) {
          console.error(error);
          errorMessage.value = 'An error occurred on the server. Please try again later.';
          return;
        }

        errorMessage.value = error.response.data.message;
      });
};
</script>

<template>
  <div class="flex items-center justify-center min-h-screen bg-gray-100">
    <div class="bg-white shadow-md rounded-lg p-8 w-96">
      <h2 class="text-2xl text-purple-800 font-bold mb-6 text-center">Register</h2>
      <span class="text-red-500 text-center" v-if="errorMessage"> {{ errorMessage }} </span>
      <form @submit.prevent="handleRegister">
        <!-- First Name Field -->
        <div class="mb-4">
          <label for="firstName" class="block text-sm font-medium text-gray-700">First Name</label>
          <input
              type="text"
              id="firstName"
              v-model="firstName"
              required
              class="mt-1 block w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Enter your first name"
              minlength="1"
              maxlength="255"
          />
        </div>

        <!-- Last Name Field -->
        <div class="mb-4">
          <label for="lastName" class="block text-sm font-medium text-gray-700">Last Name</label>
          <input
              type="text"
              id="lastName"
              v-model="lastName"
              required
              class="mt-1 block w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Enter your last name"
              minlength="1"
              maxlength="255"
          />
        </div>

        <!-- Email Field -->
        <div class="mb-4">
          <label for="email" class="block text-sm font-medium text-gray-700">Email</label>
          <input
              type="email"
              id="email"
              v-model="email"
              required
              class="mt-1 block w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Enter your email"
              minlength="4"
              maxlength="320"
          />
        </div>

        <!-- Password Field -->
        <div class="mb-4">
          <label for="password" class="block text-sm font-medium text-gray-700">Password</label>
          <input
              type="password"
              id="password"
              v-model="password"
              required
              class="mt-1 block w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Enter your password"
              minlength="6"
          />
        </div>

        <!-- Confirm Password Field -->
        <div class="mb-4">
          <label for="confirmPassword" class="block text-sm font-medium text-gray-700">Confirm Password</label>
          <input
              type="password"
              id="confirmPassword"
              v-model="confirmPassword"
              required
              class="mt-1 block w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Confirm your password"
              minlength="6"
          />
        </div>

        <!-- Phone Number Field -->
        <div class="mb-4">
          <label for="phoneNumber" class="block text-sm font-medium text-gray-700">Phone Number</label>
          <input
              type="text"
              id="phoneNumber"
              v-model="phoneNumber"
              required
              class="mt-1 block w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Enter your phone number"
              minlength="6"
          />
        </div>

        <button
            type="submit"
            class="w-full bg-purple-800 text-white font-semibold py-2 rounded-md hover:bg-purple-900 transition duration-200"
        >
          Register
        </button>
      </form>

      <p class="mt-4 text-sm text-center">
        Already have an account?
        <router-link to="/login" class="text-blue-500 hover:underline">Login</router-link>
      </p>
    </div>
  </div>
</template>

<style scoped>
/* Add any additional styles here if necessary */
</style>
