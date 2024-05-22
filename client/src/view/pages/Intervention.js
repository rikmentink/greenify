import {css, html, LitElement} from "lit";
import {InterventionUsersPanel} from "../components/intervention/InterventionUsersPanel.js";
import {InterventionInformationBox} from "../components/intervention/InterventionInformationBox.js";
import {InterventionSurveyBox} from "../components/intervention/InterventionSurveyBox.js";

export class Intervention extends LitElement {
    static styles = [css`;`];

    constructor() {
        super();
        this.interventionId = 1;
        this.userData = [
            {
                name: "John Doe",
                email: "john.doe@gmail.com",
                progress: true,
                lastOnline: "2024-5-5",
                userId: 1
            },
            {
                name: "Henk Jan",
                email: "henk.jan@gmail.com",
                progress: false,
                lastOnline: "2024-3-5",
                userId: 2
            }
        ];
    }

    connectedCallback() {
        super.connectedCallback();
        this.addEventListener('person-fetched', this.handlePersonFetched);
    }

    handlePersonFetched(event) {
        const person = event.detail.person;
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
