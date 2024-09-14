import axios from "axios";

const REST_API_BASE_URL = "http://localhost:8080/user";

export const findUser = (idUser) => axios.get(`${REST_API_BASE_URL}/find/${idUser}`);

export const searchUserAdd = (formData) => axios.post(`${REST_API_BASE_URL}/search-user-add`, formData);

export const searchUserGroup = (formData) => axios.post(`${REST_API_BASE_URL}/search-member-group`, formData);

export const getProfile = (formData) => axios.post(`${REST_API_BASE_URL}/get-profile`, formData);