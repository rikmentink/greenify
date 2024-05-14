const API_URL = import.meta.env.VITE_API_URL;

async function getSurvey(id, categoryId, page = 1, pageSize = 1000) {
    // const url = new URL(`${API_URL}/survey/${id}/questions`);
    // url.searchParams.append('categoryId', categoryId);
    // url.searchParams.append('page', page);
    // url.searchParams.append('pageSize', pageSize);

    const url = './survey-demo.json'

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

async function saveResponse(id, subfactorId, response) {
  console.log(`Saving response for subfactor #${subfactorId} on survey #${id}:`, response);
  
  const url = new URL(`${API_URL}/survey/${id}/response`);
  return fetch(url, {
    method: 'POST',
    mode: 'no-cors',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ subfactorId, response }),
  }).then(response => response.json())
    .then(data => {
      return data;
    })
    .catch(error => {
      console.error(error);
    });
} 

export { getSurvey, saveResponse };