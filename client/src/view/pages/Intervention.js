import {css, html, LitElement} from "lit";
import {InterventionUsersPanel} from "../components/intervention/InterventionUsersPanel.js";
import {InterventionInformationBox} from "../components/intervention/InterventionInformationBox.js";
import {InterventionSurveyBox} from "../components/intervention/InterventionSurveyBox.js";

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
        console.log(this.userData);
        this.requestUpdate();
    }

    handlePersonFetched(event) {
        const person = event.detail.person;

        if(this.userData.some(user => user.userId === person.id)) {
            alert("Gebruiker is al toegevoegd aan de interventie.");
            return;
        }

        alert("Gebruiker is toegevoegd aan de interventie. Er is een email verstuurd naar de gebruiker.");
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
        this.requestUpdate();
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
