const API_URL = import.meta.env.VITE_API_URL;

async function getSurvey(id, page, pageSize) {
    // return fetch(`${import.meta.env.VITE_API_URL}/api/survey/${id}/questions?page=${page}&pageSize=${pageSize}`)
    return fetch(`${API_URL}/api/survey/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Failed to fetch survey questions: ${response.statusText}`);
            }
            return response.json();
        })
        .catch(error => {
            console.error(error);
            throw error;
        });
}

export { getSurvey };