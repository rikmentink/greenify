const API_URL = import.meta.env.VITE_API_URL;

async function getSurvey(id, categoryId, page = 1, pageSize = 1000) {
    // TODO: Use back-end service!
    // const url = new URL(`${API_URL}/survey/${id}/questions`);
    // url.searchParams.append('categoryId', categoryId);
    // url.searchParams.append('page', page);
    // url.searchParams.append('pageSize', pageSize);

    const url = './survey-demo.json';

    return fetch(url, {
        method: 'GET',
        mode: 'no-cors',
        headers: { 'Content-Type': 'application/json' },
    }).then(response => response.json())
      .then(data => {
        console.log('Success:', data);
        return data;
      })
      .catch(error => {
        console.error(error);
      })
}

async function saveResponse(id, subfactorId, response) {
  // TODO: Use back-end service!
  console.log(`Saving response for subfactor #${subfactorId} on survey #${id}:`, response);
} 

export { getSurvey, saveResponse };