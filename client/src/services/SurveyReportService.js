import { fetchData } from "../utils/fetch.js";

export async function getCategoryScores(phaseId) {
    return fetchData(`/survey-report/${phaseId}/category-scores`);
}

export async function getSubfactorScoresOfCategory(phaseId, categoryName) {
    return fetchData(`/survey-report/${phaseId}/subfactor-scores/${categoryName}`);
}