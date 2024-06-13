const API_URL = import.meta.env.VITE_API_URL;

async function handleErrorMessages(response) {
    if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message);
    }
}

async function getPhaseById(id) {
    const response = await fetch(`${API_URL}/phase/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });

    await handleErrorMessages(response);

    return response.json();
}

export { getPhaseById };