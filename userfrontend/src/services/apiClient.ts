import axios from "axios";
import user from "../userStorage.ts";
import router from "../router/router.ts";

// Create an instance of Axios with the base URL
export const api = axios.create({
    baseURL: import.meta.env.VITE_API_USER,
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
            console.log(error)
            user.value = null;
            router.push("/login");
        }).catch((error) => {
            console.log(error);
        })
    }

    return Promise.reject(error);
});

