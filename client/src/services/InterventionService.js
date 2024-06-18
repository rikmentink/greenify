const API_URL = import.meta.env.VITE_API_URL;

async function handleErrorMessages(response) {
    if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message);
    }
}

async function getInterventionById(id) {
    const response = await fetch(`${API_URL}/intervention/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });

    await handleErrorMessages(response);

    return response.json();
}

async function addParticipantToIntervention(interventionId, personId) {
const response = await fetch(`${API_URL}/intervention/${interventionId}/person/${personId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    });

    await handleErrorMessages(response);

    return response.json();
}

async function getInterventionByPersonId(id) {
    const response = await fetch(`${API_URL}/intervention/all/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });

    await handleErrorMessages(response);

    return response.json();
}

async function createInterventionWithPhase(adminId, name, description, phaseInformation, phaseName) {
    const response = await fetch(`${API_URL}/intervention`, {
        method: 'POST',
        body: JSON.stringify({adminId, name, description}),
        headers: {
            'Content-Type': 'application/json'
        }
    });

    const data = await response.json();
    await createPhase(data.id, phaseName, phaseInformation);

    await handleErrorMessages(response);
}

async function getPhasesByInterventionId(id) {
    const response = await fetch(`${API_URL}/intervention/${id}/phases`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });

    await handleErrorMessages(response);

    return response.json();
}

export { getInterventionById, getInterventionByPersonId, addParticipantToIntervention, getPhasesByInterventionId, createInterventionWithPhase};