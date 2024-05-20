const API_URL = import.meta.env.VITE_API_URL;

async function getCategoryScores(phaseId) {
    const url = new URL(`${API_URL}/survey-report/${phaseId}/category-scores`);
    return fetch(url, {
        method: 'GET',
        mode: 'no-cors',
        headers: { 'Content-Type': 'application/json' },
    }).then(response => response.json())
        .then(data => {
            return data;
        })
        .catch(error => {
            console.error(error);
        })
}

async function getSubfactorScores(phaseId, categoryName) {
    const url = new URL(`${API_URL}/survey-report/${phaseId}/subfactor-scores/${categoryName}`);
    return fetch(url, {
        method: 'GET',
        mode: 'no-cors',
        headers: { 'Content-Type': 'application/json' },
    }).then(response => response.json())
        .then(data => {
            return data;
        })
        .catch(error => {
            console.error(error);
        })
}

export { getCategoryScores, getSubfactorScores };