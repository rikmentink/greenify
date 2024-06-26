const API_URL = import.meta.env.VITE_API_URL;

async function getSurvey(id, categoryId = 0, page = 1, pageSize = 1000) {
    const url = new URL(`${API_URL}/survey/${id}/questions`);
    url.searchParams.append('categoryId', categoryId);
    url.searchParams.append('page', page);
    url.searchParams.append('pageSize', pageSize);

    return fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
    })
    .then(response => {
        if (!response.ok) {
            console.error(`Could not load survey data. HTTP Error ${response.status}: ${response.statusText}`);
        }
        return response.json();
    });
}

async function saveResponse(id, subfactorId, response) {
  const url = new URL(`${API_URL}/survey/${id}/response`);
  return fetch(url, {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
    },
    body: JSON.stringify({ 
      subfactorId,
      facilitatingFactor: response.facilitatingFactor,
      priority: response.priority,
      comment: response.comment
    }),
  })
  .then(response => {
      if (!response.ok) {
          throw new Error(`Could not save response. HTTP Error ${response.status}: ${response.statusText}`);
      }
      return response.json();
  })
  .catch(error => {
    console.error(error);
  });
} 

export { getSurvey, saveResponse };