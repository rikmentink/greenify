import {css, html, LitElement} from "lit";
import {InterventionUsersPanel} from "../components/intervention/InterventionUsersPanel.js";
import {InterventionInformationBox} from "../components/intervention/InterventionInformationBox.js";
import {InterventionSurveyBox} from "../components/intervention/InterventionSurveyBox.js";
import {sendMail} from "../../services/MailService.js";
import {addParticipantToIntervention} from "../../services/InterventionService.js";
import {getInterventionById} from "../../services/InterventionService.js";
import {Task} from "@lit/task";
import {getSurvey} from "../../services/SurveyService.js";

export class Intervention extends LitElement {
    static styles = [css`;`];

    constructor() {
        super();
        this.interventionId = 0;
        this.userData = [];
        this.interventionData = [{}];
        this.data = {};
    }

    async connectedCallback() {
        super.connectedCallback();
        this.addEventListener('person-fetched', this.handlePersonFetched);
        this.addEventListener('user-deleted', this.onUserDeleted);

        const selectedIntervention = JSON.parse(sessionStorage.getItem('selectedIntervention'));
        if (selectedIntervention) {
           this.interventionId = selectedIntervention.id;
        }

        this._fetchData(this.interventionId);
    }

    onUserDeleted(event) {
        const user = event.detail.user;
        this.userData = this.userData.filter(userData => userData.userId !== user.userId);
        this.requestUpdate();
    }

    async _fetchData(id) {
        this.data = new Task(this, {
            task: async ([id]) => getInterventionById(id),
            args: () => [id]
        });
        return await this.data.run();
    }

    async _addParticipantToIntervention(id, personId) {
        new Task(this, {
            task: async ([id, personId]) => addParticipantToIntervention(id, personId),
            args: () => [id, personId]
        });
        return await this.data.run();
    }

    async handlePersonFetched(event) {
        const person = event.detail.person;

        console.log(person);
        console.log(this.data);

        try {
            await this._addParticipantToIntervention(this.data.value.id, person.id);
            window.location.reload();

            await sendMail({
                to: person.email,
                subject: "U bent uitgenodigd bij een interventie",
                body: `Beste deelnemer, \nU bent toegevoegd aan interventie: "${this.data.value.name}". Indien u geen account heeft, kunt u zich aanmelden via de registreer pagina: [link]\n\nMet vriendelijke groet,\nDe Vrije Universiteit Amsterdam.`
            });
        } catch (error) {
            alert("Er is iets misgegaan. Let op dat u niet dezelfde deelnemers opnieuw of de admin toevoegt.");
        }
    }

    render() {
        return html`
            ${this.data.render({
                complete:  (data) => html`
                <intervention-information-box .interventionData="${data}"></intervention-information-box>
                <intervention-survey-box .id="${data.id}"></intervention-survey-box>
                <intervention-users-panel .userData="${data.participants}"></intervention-users-panel>
            `,
            })}
        `
    }
}

window.customElements.define('gi-intervention', Intervention);
