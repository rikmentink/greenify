async function getSurvey(id, page, pageSize) {
    const response = await fetch(`/api/survey/${id}/questions?page=${page}&pageSize=${pageSize}`);
    if (!response.ok) {
        throw new Error(`Failed to fetch survey questions: ${response.statusText}`);
    }
    return response.json();
}

export { getSurvey };