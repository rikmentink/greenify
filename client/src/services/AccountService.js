import { fetchData } from "../utils/fetch";

const API_URL = import.meta.env.VITE_API_URL;

async function login (email, password) {
    return fetch(`${API_URL}/account/login`, {
        method: 'POST',
        body: JSON.stringify({ email, password }),
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Could not fetch data. HTTP Error ${response.status}: ${response.statusText}`);
        }

        let token = response.headers.get('Authorization');
        token.replace('Bearer ', '');
        return token;
    })
    .catch(error => {
        console.error(error);
    });
}

async function register (email, password, firstName, lastName) {
    return fetch(`${API_URL}/account/login`, {
        method: 'POST',
        body: JSON.stringify({ email, password, firstName, lastName }),
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Could not fetch data. HTTP Error ${response.status}: ${response.statusText}`);
        }
        return response.json();
    })
    .catch(error => {
        console.error(error);
    });
}

async function getCurrentUser(){
    return fetchData(`/account/current`);
}

async function changeRoleToManager() {
    return fetchData('account/roletomanager', {}, 'PUT');
}

async function changeRoleToParticipant() {
    return fetchData(`/account/roletoparticipant`, {}, 'PUT');
}

export { login, register, getCurrentUser, changeRoleToManager, changeRoleToParticipant};