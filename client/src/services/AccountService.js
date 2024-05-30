const API_URL = import.meta.env.VITE_API_URL;

async function handleErrorMessages(response) {
    if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message);
    }
}


async function login (email, password) {
    const response = await fetch(`${API_URL}/account/login`, {
        method: 'POST',
        body: JSON.stringify({ email, password })
    });

    await handleErrorMessages(response);

    let token = response.headers.get('Authorization')
    token = token.replace('Bearer ', '');

    return token
}

async function register (email, password, firstName, lastName){
    const response = await fetch(`${API_URL}/account/register`, {
        method: 'POST',
        body: JSON.stringify({ email, password, firstName, lastName}),
        headers: {
            'Content-Type': 'application/json'
        }
    });

    await handleErrorMessages(response);
}

async function getCurrentUser(){
    const response = await fetch(`${API_URL}/account/current`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });

    if (!response.ok) {
        return null;
    }

    return response.json();
}

export { login, register, getCurrentUser };