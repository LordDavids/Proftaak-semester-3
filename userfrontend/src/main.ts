import { createApp } from 'vue'
import App from './App.vue'
import router from './router/router.ts'; // Import the router
import './index.css'


createApp(App)
    .use(router) // Use the router
    .mount('#app');
