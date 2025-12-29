import axios from 'axios';
import keycloak from './keycloak';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
    baseURL: API_BASE_URL,
});

api.interceptors.request.use(
    (config) => {
        if (keycloak.authenticated) {
            config.headers.Authorization = `Bearer ${keycloak.token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);
