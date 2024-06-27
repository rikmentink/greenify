import { fetchData } from "../utils/fetch";

async function createPhase(id, phaseName, description) {
    return fetchData(`/intervention/${id}/phase`, { phaseName, description }, 'POST');
}

async function getPhaseById(id) {
    return fetchData(`/intervention/phase/${id}`);
}

export { getPhaseById, createPhase};