const API_URL = import.meta.env.VITE_API_URL;

async function fetchData(url, body={}, method='GET') {
    let options = {
        method: method,
        headers: { 
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
            'Content-Type': 'application/json' 
        },
    }

    if (method === 'POST' || method === 'PUT') {
        if (Object.keys(body).length === 0) {
            throw new Error('Body is required for POST and PUT requests');
        }
        options.body = JSON.stringify(body);
    }

    return fetch(`${API_URL}${url}`, options)
    .then(response => {
        if (!response.ok) {
            throw new Error(`Could not fetch data. HTTP Error ${response.status}: ${response.statusText}`);
        }
        return response.json();
    })
    .catch(error => {
      console.error(error);
    });
}

export { fetchData };