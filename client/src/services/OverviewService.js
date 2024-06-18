const API_URL = import.meta.env.VITE_API_URL;

async function getOverview(id) {
    return fetch(`${API_URL}/phase/${phaseId}`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' },
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Could not fetch phase data. HTTP Error ${response.status}: ${response.statusText}`);
        }
        return response.json();
    })
    .catch(error => {
      console.error(error);
    });
}

export { getOverview };
