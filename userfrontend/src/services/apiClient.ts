import axios from "axios";
import user from "../userStorage.ts";
import router from "../router/router.ts";

// Create an instance of Axios with the base URL
export const api = axios.create({
    baseURL: 'http://localhost:8080/api',  // Use the .env variable for the base URL
    withCredentials: true,
});

api.interceptors.response.use((response) => {
    return response;
}, async (error) => {
    if ((error.response.status === 401)) {
        api.post("/auth/logout", {}, {
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            user.value = null;
            router.push("/login");
        }).catch((error) => {
            console.log(error);
        })
    }

    return Promise.reject(error);
});

