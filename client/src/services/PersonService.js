const API_URL = import.meta.env.VITE_API_URL;

async function handleErrorMessages(response) {
    if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message);
    }
}

async function getPersonByEmail(email) {
    const response = await fetch(`${API_URL}/person/email/${email}`, {
        method: 'GET',
        mode: 'no-cors',
        headers: {
            'Content-Type': 'application/json'
        }
    });

    await handleErrorMessages(response);

    return response.json();
}