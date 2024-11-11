import { Role } from '../enteties/Role';

export type User = {
    firstname: string;
    lastname: string;
    email: string;
    role: Role;
}