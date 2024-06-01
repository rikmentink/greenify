const API_URL = import.meta.env.VITE_API_URL;

async function handleErrorMessages(response) {
    if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message);
    }
}

async function getInterventionById(id) {
    const response = await fetch(`${API_URL}/intervention/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });

    await handleErrorMessages(response);

    return response.json();
}

async function getInterventionByPersonId(id) {
    const response = await fetch(`${API_URL}/intervention/all/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });

    await handleErrorMessages(response);

    return response.json();
}

export { getInterventionById };