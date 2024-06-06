const API_URL = import.meta.env.VITE_API_URL;

async function getOverview(id, page = 1, pageSize = 1000) {
    const url = new URL(`${API_URL}/phase/${id}`);
    url.searchParams.append('page', page);
    url.searchParams.append('pageSize', pageSize);

    return fetch(url, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' },
    }).then(response => response.json())
        .catch(error => {
            console.error(error);
        });
}

export { getOverview };
