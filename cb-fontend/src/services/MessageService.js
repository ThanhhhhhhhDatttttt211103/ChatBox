import axios from "axios";

const REST_API_BASE_URL = "http://localhost:8080/api/message";

export const getMessage = (messageRequest) => axios.post(`${REST_API_BASE_URL}/getMessage`, messageRequest);
