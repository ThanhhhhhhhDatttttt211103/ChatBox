import axios from "axios";

const REST_API_BASE_URL = "http://localhost:8080/api/account";

export const login = (account) => axios.post(REST_API_BASE_URL, account);