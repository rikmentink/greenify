import { fetchData } from "../utils/fetch";

async function getPersonByEmail(email) {
    return fetchData(`/person/email/${email}`);
}

async function getCurrentPerson() {
    return fetchData(`/person/current`);
}

export { getPersonByEmail, getCurrentPerson};
