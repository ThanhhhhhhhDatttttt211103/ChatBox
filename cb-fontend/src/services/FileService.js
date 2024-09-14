import axios from "axios";

const REST_API_BASE_URL = "http://localhost:8080/file";

export const uploadFile = (formData) => axios.post(`${REST_API_BASE_URL}/upload`, formData, {
    headers: {
        "Content-Type": "multipart/form-data"
    }
});

export const getFile = (formData) => axios.post(`${REST_API_BASE_URL}/getFile`, formData);