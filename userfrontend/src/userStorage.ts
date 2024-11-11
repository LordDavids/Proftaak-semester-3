import {useStorage} from "@vueuse/core";
import { User } from "./enteties/User.ts"

const user = useStorage<User | null>(
    'user',
    null,
    localStorage,
    {
        mergeDefaults: true,
        serializer: {
            read: (v: any): User | null => v ? JSON.parse(atob(v)) : null,
            write: (v: any) => btoa(JSON.stringify(v)),
        },
    }
)

export default user;