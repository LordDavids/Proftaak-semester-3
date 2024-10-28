import { createRouter, createWebHistory } from 'vue-router'

import Home from '../views/Home.vue'
import Login from "../views/Login.vue";
import ErrorPage from "../views/ErrorPage.vue";


const routes = [
    { path: '/', component: Home },
    { path: '/login', component: Login },
    { path: '/error', component: ErrorPage },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router