import {css, html, LitElement} from "lit";
import {InterventionUsersPanel} from "../components/intervention/InterventionUsersPanel.js";
import {InterventionInformationBox} from "../components/intervention/InterventionInformationBox.js";
import {InterventionSurveyBox} from "../components/intervention/InterventionSurveyBox.js";
import {sendMail} from "../../services/MailService.js";
import {addParticipantToIntervention} from "../../services/InterventionService.js";
import {getInterventionById} from "../../services/InterventionService.js";

export class Intervention extends LitElement {
    static styles = [css`;`];

    constructor() {
        super();
        this.interventionId = 0;
        this.userData = [];
        this.interventionData = [{}];
    }

    connectedCallback() {
        super.connectedCallback();
        this.addEventListener('person-fetched', this.handlePersonFetched);
        this.addEventListener('user-deleted', this.onUserDeleted);

        const selectedIntervention = JSON.parse(sessionStorage.getItem('selectedIntervention'));
        if (selectedIntervention) {
           this.interventionData = selectedIntervention;
        }
    }

    onUserDeleted(event) {
        const user = event.detail.user;
        this.userData = this.userData.filter(userData => userData.userId !== user.userId);
        this.requestUpdate();
    }

    async handlePersonFetched(event) {
        const person = event.detail.person;
        console.log(person);

        addParticipantToIntervention(this.interventionData.id, person.id);
        this.interventionData = await getInterventionById(this.interventionData.id);

        this.userData = this.interventionData.participants;

        console.log(this.interventionData);
        console.log(this.userData);


        alert("Gebruiker is toegevoegd aan de interventie. Er is een email verstuurd naar de gebruiker.");

        sendMail({
            to: person.email,
            subject: "U bent uitgenodigd bij een interventie",
            body: `U bent toegevoegd aan interventie ${this.interventionData.id}. Indien u geen account heeft, kunt u zich aanmelden via de registreer pagina. `
        });

        this.userData = [
            ...this.userData,
            {
                name: `${person.firstName} ${person.lastName}`,
                email: person.email,
                progress: false, // Hardcoded value
                lastOnline: "2024-5-5", // Hardcoded value
                userId: person.id
            }
        ];
    }

    render() {
        return html`
            <intervention-information-box .interventionData="${this.interventionData}"></intervention-information-box>
            <intervention-survey-box .id="${this.interventionData.id}"></intervention-survey-box>
            <intervention-users-panel .userData="${this.userData}"></intervention-users-panel>
        `;
    }
}

window.customElements.define('gi-intervention', Intervention);
