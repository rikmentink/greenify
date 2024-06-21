const API_URL = import.meta.env.VITE_API_URL;

async function handleErrorMessages(response) {
    if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message);
    }
}

async function removePersonById(id) {
    const response = await fetch(`${API_URL}/person/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });

    await handleErrorMessages(response);
    return response.json();
}

async function getPersonByEmail(email) {
    const response = await fetch(`${API_URL}/person/email/${email}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });

    await handleErrorMessages(response);

    return response.json();
}

async function getPersonById(id) {
    const response = await fetch(`${API_URL}/person/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });

    await handleErrorMessages(response);

    return response.json();
}

async function getCurrentPerson() {
    const response = await fetch(`${API_URL}/person/current`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });

    await handleErrorMessages(response);

    return response.json();
}

export { getPersonByEmail, getPersonById, getCurrentPerson, removePersonById};
