import {css, html, LitElement} from "lit";
import {InterventionUsersPanel} from "../components/intervention/InterventionUsersPanel.js";
import {InterventionInformationBox} from "../components/intervention/InterventionInformationBox.js";
import {InterventionSurveyBox} from "../components/intervention/InterventionSurveyBox.js";
import {getCurrentUser} from "../../services/AccountService.js";
import {sendMail} from "../../services/MailService.js";
import {addParticipantToIntervention} from "../../services/InterventionService.js";
import {getInterventionById} from "../../services/InterventionService.js";
import {Task} from "@lit/task";
import {getSurvey} from "../../services/SurveyService.js";
import {removeParticipantFromIntervention} from "../../services/InterventionService.js";
import {getRouter} from "../../router.js";

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
        this.addEventListener('remove-user', this.onUserDeleted);

        this.interventionId = getRouter().location.params.id || 0;

        await this._fetchData(this.interventionId);
        await this._fetchCurrentAccount();
    }

    async onUserDeleted(event) {
        await removeParticipantFromIntervention(this.data.value.id, event.detail.userId);
        window.location.reload();
    }

    async _fetchCurrentAccount() {
        this.userData = new Task(this, {
            task: async () => getCurrentUser()
        });
        return await this.userData.run();
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
            await this._addParticipantToIntervention(this.data.value.id, person.id);
            window.location.reload();

            await sendMail({
                to: person.email,
                subject: "U bent uitgenodigd bij een interventie",
                body: `Beste deelnemer, \nU bent toegevoegd aan interventie: "${this.data.value.name}". Indien u geen account heeft, kunt u zich aanmelden via de registreer pagina: [link]\n\nMet vriendelijke groet,\nDe Vrije Universiteit Amsterdam.`
            });
    }

    renderUserPanel(data){
        const userRoles = this.userData.value ? this.userData.value.authorities.map(role => role.authority) : [];
        const user = this.userData.value ? this.userData.value : {};
        if (user.email === data.admin.email && userRoles.includes("ROLE_MANAGER")) {
            return html`
            <intervention-users-panel .userData="${data.participants}" .progress="${data.participantProgress}"></intervention-users-panel>
        `;
        }
    }

    render() {
        return html`
            ${this.data.render({
                complete:  (data) => html`
                <intervention-information-box .interventionData="${data}"></intervention-information-box>
                <intervention-survey-box .id="${this.interventionId}"></intervention-survey-box>
                ${this.renderUserPanel(data)}
            `,
            })}
        `
    }
}

window.customElements.define('gi-intervention', Intervention);
