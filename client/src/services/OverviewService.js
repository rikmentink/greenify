const API_URL = import.meta.env.VITE_API_URL;

async function getOverview(id, categoryId, name, description, questions) {
    const url = new URL(`${API_URL}/overview/${id}`);
    url.searchParams.append('categoryId', categoryId);
    url.searchParams.append('name', name);
    url.searchParams.append('description', description);
    url.searchParams.append('questions', questions);

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

export { getOverview };