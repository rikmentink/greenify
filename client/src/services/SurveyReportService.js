const API_URL = import.meta.env.VITE_API_URL;
import {DummyCategoryScores, DummySubfactorScores, DummySubfactorScores2} from "../data/SurveyReport.js";

async function handleErrorMessages(response) {
    if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message);
    }
}

export async function getCategoryScores(phaseId) {
    return DummyCategoryScores;
}

export async function getSubfactorScoresOfCategory(phaseId, categoryName) {

    // TODO: Perform actual API call. If-statement is to test updating of charts dynamically
    if (categoryName === "Dummy Category 2") {
        return DummySubfactorScores2;
    }
    return DummySubfactorScores;
}