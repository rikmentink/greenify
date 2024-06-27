import { fetchData } from "../utils/fetch";

async function sendMail(mail) {
    return fetchData(`/mail`, mail, 'POST');
}

export { sendMail };