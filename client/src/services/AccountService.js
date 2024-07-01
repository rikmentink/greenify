const API_URL = import.meta.env.VITE_API_URL;

async function handleErrorMessages(response) {
    if (response.ok) return;

    const errorData = await response.json();
    let errorMessage = mapErrorMessage(errorData.message);
    throw new Error(errorMessage);
}

function mapErrorMessage(message) {
    const errorMessages = {
        'Email or password is incorrect': 'Email of wachtwoord is onjuist',
        'Email is already in use': 'Email is al in gebruik',
        'Password is too short': 'Wachtwoord is te kort. Gebruik minimaal 5 tekens'
    };

    return errorMessages[message] || message;
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

async function changeRoleToManager() {
    const response = await fetch(`${API_URL}/account/roletomanager`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });
    await handleErrorMessages(response);
}

async function changeRoleToParticipant() {
    const response = await fetch(`${API_URL}/account/roletoparticipant`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });
    await handleErrorMessages(response);
}

export { login, register, getCurrentUser, changeRoleToManager, changeRoleToParticipant};