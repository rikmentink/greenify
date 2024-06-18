import {css, html, LitElement} from "lit";
import {InterventionUsersPanel} from "../components/intervention/InterventionUsersPanel.js";
import {InterventionInformationBox} from "../components/intervention/InterventionInformationBox.js";
import {InterventionSurveyBox} from "../components/intervention/InterventionSurveyBox.js";
import {sendMail} from "../../services/MailService.js";

export class Intervention extends LitElement {
    static styles = [css`;`];

    constructor() {
        super();
        this.interventionId = 1;
        this.userData = [];
    }

    connectedCallback() {
        super.connectedCallback();
        this.addEventListener('person-fetched', this.handlePersonFetched);
        this.addEventListener('user-deleted', this.onUserDeleted);
    }

    onUserDeleted(event) {
        const user = event.detail.user;
        this.userData = this.userData.filter(userData => userData.userId !== user.userId);
        this.requestUpdate();
    }

    handlePersonFetched(event) {
        const person = event.detail.person;

        if(this.userData.some(user => user.userId === person.id)) {
            alert("Gebruiker is al toegevoegd aan de interventie.");
            return;
        }

        alert("Gebruiker is toegevoegd aan de interventie. Er is een email verstuurd naar de gebruiker.");

        sendMail({
            to: person.email,
            subject: "U bent uitgenodigd bij een interventie",
            body: `U bent toegevoegd aan interventie ${this.interventionId}. Indien u geen account heeft, kunt u zich aanmelden via de registreer pagina. `
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
        window.location.reload();
    }

    render() {
        return html`
            <intervention-information-box .id="${this.interventionId}"></intervention-information-box>
            <intervention-survey-box .id="${this.interventionId}"></intervention-survey-box>
            <intervention-users-panel .id="${this.interventionId}" .userData="${this.userData}"></intervention-users-panel>
        `;
    }
}

window.customElements.define('gi-intervention', Intervention);
