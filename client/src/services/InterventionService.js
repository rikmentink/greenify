import { fetchData } from '../utils/fetch.js';
import { createPhase } from './PhaseService.js';

async function getInterventionById(id) {
    return fetchData(`/intervention/${id}`);
}

async function addParticipantToIntervention(interventionId, personId) {
    return fetchData(`/intervention/${interventionId}/person/${personId}`, {}, 'POST');
}

async function removeParticipantFromIntervention(interventionId, personId) {
    return fetchData(`/intervention/${interventionId}/person/${personId}/remove`, {}, 'POST');
}

async function getInterventionByPersonId(id) {
    return fetchData(`/intervention/all/${id}`);
}

async function createInterventionWithPhase(adminId, name, description, phaseName, phaseDescription) {
    let intervention = await fetchData(`/intervention`, { adminId, name, description }, 'POST');
    return await createPhase(intervention.id, phaseName, phaseDescription);
}

async function getPhasesByInterventionId(id) {
    return fetchData(`/intervention/${id}/phases`);
}

export { getInterventionById, getInterventionByPersonId, addParticipantToIntervention, getPhasesByInterventionId, createInterventionWithPhase, removeParticipantFromIntervention};