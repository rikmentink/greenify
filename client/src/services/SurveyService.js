import { fetchData } from "../utils/fetch.js";
const API_URL = import.meta.env.VITE_API_URL;

async function getSurvey(id, categoryId = 0, page = 1, pageSize = 1000) {
    return fetchData(`/survey/${id}/questions?categoryId=${categoryId}&page=${page}&pageSize=${pageSize}`);
}

async function saveResponse(id, subfactorId, response) {
  body = {
    subfactorId,
    facilitatingFactor: response.facilitatingFactor,
    priority: response.priority,
    comment: response.comment
  }
  return fetchData(`/survey/${id}/response`, body, 'POST');
} 

export { getSurvey, saveResponse };