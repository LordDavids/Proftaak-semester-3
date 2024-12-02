import { createRouter, createWebHistory } from 'vue-router'
import user from "../userStorage.ts";

import Home from '../views/Home.vue'
import Login from "../views/Login.vue";
import ErrorPage from "../views/ErrorPage.vue";
import Product from "../views/ProductPage.vue";
import Register from "../views/Register.vue";
import ProductOverview from "../views/ProductOverview.vue";


const routes = [
    { path: '/', component: Home },
    { path: '/login', component: Login },
    { path: '/error', component: ErrorPage },
    { path: '/category/:category/:id', component: ProductOverview },
    { path: '/register', component: Register },
    { path: '/product/:product_id/', component: Product},
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

router.beforeEach((to, _from, next) => {
    // console.log(`Navigating to: ${to.path}, from: ${from.path}`);

    // Example: Check if the route requires authentication
    if ((to.path === '/login' || to.path === '/register') && isAuthenticated()) {
        console.log('authenticated! Redirecting to home');
        next('/'); // Redirect to login if not authenticated
    } else {
        next(); // Proceed to the route
    }
});

function isAuthenticated() {
    // Check if `user.value` exists and is not null
    if (user.value) {
        return true; // User is authenticated
    }
    return false; // User is not authenticated
}


export default router