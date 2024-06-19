const API_URL = import.meta.env.VITE_API_URL;

async function getOverview(interventionId, phaseId) {
    return fetch(`${API_URL}/intervention/${interventionId}/phase/${phaseId}`, {
        method: 'GET',
        headers: { 
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
            'Content-Type': 'application/json' 
        },
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
