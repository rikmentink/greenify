const API_URL = import.meta.env.VITE_API_URL;

async function handleErrorMessages(response) {
    if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message);
    }
}

async function createPhase(id, phaseName, description) {
    const url = new URL(`${API_URL}/intervention/${id}/phase`);
    return fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify({phaseName, description}),
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Could not save response. HTTP Error ${response.status}: ${response.statusText}`);
        }
        return response.json();
    })
    .catch(error => {
        console.error(error);
    });
}

async function getPhaseById(id) {
    const response = await fetch(`${API_URL}/intervention/phase/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });

    await handleErrorMessages(response);

    return response.json();
}

export { getPhaseById, createPhase};