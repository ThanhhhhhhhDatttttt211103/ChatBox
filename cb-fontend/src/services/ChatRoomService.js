import axios from "axios";

const REST_API_BASE_URL = "http://localhost:8080/chatRoom";

export const listChatRooms = (idUser) => axios.get(`${REST_API_BASE_URL}/getAll/${idUser}`);

export const apiListFriend = (formData) => axios.post(`${REST_API_BASE_URL}/getListFriend`, formData);

export const createChatRoom = (formData) => axios.post(`${REST_API_BASE_URL}/createChatRoomUI`, formData);
