const API_URL = import.meta.env.VITE_API_URL;

async function handleErrorMessages(response) {
    if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message);
    }
}

async function createPhase(id, phaseName, description) {
    const response = await fetch(`${API_URL}/intervention/${id}/phase`, {
        method: 'POST',
        body: JSON.stringify({phaseName, description}),
        headers: {
            'Content-Type': 'application/json'
        }
    });

    await handleErrorMessages(response);
}

export {createPhase};