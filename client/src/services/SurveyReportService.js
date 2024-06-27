const API_URL = import.meta.env.VITE_API_URL;
import {DummyCategoryScores, DummySubfactorScores, DummySubfactorScores2} from "../data/SurveyReport.js";

async function handleErrorMessages(response) {
    if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message);
    }
}

export async function getCategoryScores(phaseId) {
    const response = await fetch(`${API_URL}/survey-report/${phaseId}/category-scores`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
    });
    await handleErrorMessages(response);
    return response.json();
}

export async function getSubfactorScoresOfCategory(phaseId, categoryName) {
    const response = await fetch(`${API_URL}/survey-report/${phaseId}/subfactor-scores/${categoryName}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
    });
    await handleErrorMessages(response);
    return response.json();
}