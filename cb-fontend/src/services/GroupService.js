import axios from "axios";

const REST_API_BASE_URL = "http://localhost:8080/group";

export const saveGroupDetail = (groupDetail) => axios.post(`${REST_API_BASE_URL}/save-group-detail`, groupDetail);
