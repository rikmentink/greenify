import { fetchData } from "../utils/fetch";

const API_URL = import.meta.env.VITE_API_URL;

async function getOverview(interventionId, phaseId) {
    return fetchData(`/intervention/${interventionId}/phase/${phaseId}`);
}

export { getOverview };
