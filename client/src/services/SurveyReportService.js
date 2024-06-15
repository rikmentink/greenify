const API_URL = import.meta.env.VITE_API_URL;

async function handleErrorMessages(response) {
    if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message);
    }
}

async function getCategoryScores() {
    const response = await fetch(`${API_URL}/intervention/categoryscores`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });

    await handleErrorMessages(response);

    return response.json();
}

async function getSubfactorScoresOfCategory(categoryId) {
    const response = await fetch(`${API_URL}/intervention/subfactorscores/${categoryId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });

    await handleErrorMessages(response);

    return response.json();
}