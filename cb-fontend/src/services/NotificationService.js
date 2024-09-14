import axios from "axios";

const REST_API_BASE_URL = "http://localhost:8080/notification";

export const getNotifiUnread = (formData) => axios.post(`${REST_API_BASE_URL}/get-notification-unread`, formData);

export const countNotifiUnread = (formData) => axios.post(`${REST_API_BASE_URL}/count-notification-unread`, formData);

export const getNotifications = (formData) => axios.post(`${REST_API_BASE_URL}/get-all-notification`, formData);

export const updateNotifiUnread = (formData) => axios.post(`${REST_API_BASE_URL}/update-notification-unread`, formData);

export const saveNotification = (Notification) => axios.post(`${REST_API_BASE_URL}/save-notification`, Notification);


