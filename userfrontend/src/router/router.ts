import { createRouter, createWebHistory } from 'vue-router'

import Home from '../views/Home.vue'
import Login from "../views/Login.vue";
import ErrorPage from "../views/ErrorPage.vue";
import Product from "../views/Product.vue";


const routes = [
    { path: '/', component: Home },
    { path: '/login', component: Login },
    { path: '/error', component: ErrorPage },
    { path: '/product', component: Product}
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router