import axios from "axios";

const REST_API_BASE_URL = "http://localhost:8080/api/friend";

export const listFriend = (idUser) => axios.get(`${REST_API_BASE_URL}/${idUser}`);

export const addFriendTest = (formData) => axios.post(`${REST_API_BASE_URL}/add-friend`, formData);

