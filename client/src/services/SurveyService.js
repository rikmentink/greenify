const API_URL = import.meta.env.VITE_API_URL;

async function getSurvey(id, categoryId, page = 1, pageSize = 10) {
    const url = new URL(`${API_URL}/survey/${id}/questions`);
    url.searchParams.append('categoryId', categoryId);
    url.searchParams.append('page', page);
    url.searchParams.append('pageSize', pageSize);

    // TODO: Remove!
    const tempUrl = './survey.json';

    return fetch(tempUrl, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' },
    }).then(response => {
        if (!response.ok) {
            throw new Error(`Failed to fetch survey questions: ${response.statusText}`);
        }
        return response.json();
    }).catch(error => {
        console.error(error);
        throw error;
    });
}

export { getSurvey };