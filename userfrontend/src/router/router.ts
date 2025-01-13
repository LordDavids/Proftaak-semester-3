import { createRouter, createWebHistory } from 'vue-router'
import user from "../userStorage.ts";

import Home from '../views/Home.vue'
import Login from "../views/Login.vue";
import ErrorPage from "../views/ErrorPage.vue";
import Product from "../views/ProductPage.vue";
import Register from "../views/Register.vue";
import ProductOverview from "../views/ProductOverview.vue";
import Demo from "../views/Demo.vue";


const routes = [
    { path: '/', component: Home },
    { path: '/login', component: Login },
    { path: '/error', component: ErrorPage },
    { path: '/category/:category/:id', component: ProductOverview },
    { path: '/register', component: Register },
    { path: '/product/:product_id/', component: Product},
    { path: '/demo', component: Demo},
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

router.beforeEach((to, _from, next) => {
    // console.log(`Navigating to: ${to.path}, from: ${from.path}`);

    if ((to.path === '/login' || to.path === '/register') && isAuthenticated()) {
        console.log('Authenticated! Redirecting to home.');
        next('/'); // Redirect to home if already authenticated
    } else if (to.path === '/demo' && (!isAuthenticated() ||  !isAdmin())) {
        next('/'); // Redirect to login if not authenticated
    } else {
        next(); // Proceed to the route
    }
});

function isAuthenticated() {
    // Check if `user.value` exists and is not null
    return !!user.value;
     // User is not authenticated
}
function isAdmin() {
    // Check if `user.value` exists and is not null
    return user.value.role.id === 2;
}


export default router