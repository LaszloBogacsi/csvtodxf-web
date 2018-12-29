import axios from "axios";

export const baseURL = process.env.REACT_APP_API_BASE_URL;

export const httpModule = {

    get: (url, config) => {
        return axios.get(baseURL + url, config)
    },

    post: (url, data, config) => {
        return axios.post(baseURL + url, data, config);
    }

};

