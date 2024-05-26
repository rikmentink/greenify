import { LitElement, html, css } from "lit";
import { Task } from "@lit/task";
import { getRouter } from "../../router.js";

import { getSurvey } from '../../services/SurveyService.js';
import { saveResponse } from '../../services/SurveyService.js';
import { InfoPopUp } from "./InfoPopUp.js";

import { SurveySubfactor } from "../components/survey/SurveySubfactor.js";

export class Survey extends LitElement {
    static properties = {
        id: { type: Number },
        page: { type: Number },
        pageSize: { type: Number }
    }

    static styles = css`
        .survey > .header,
        .survey > .factor > gi-survey-subfactor {
            display: grid;
            grid-template-columns: 6fr 3fr 2fr 1fr;
            gap: 1rem;
        }
        h1,
        .survey > .factor > h2 {
            font-weight: normal;
        }
        .survey > .header > .column {
            font-weight: bold;
        }
        .survey > .header > .column:not(:first-child) {
            text-align: center;
        }
        .survey > .factor > gi-survey-subfactor {
            margin-bottom: .5rem;
        }
    `

    constructor() {
        super();
        this.id = 0;
        this.page = 1;
        this.pageSize = 10; // TODO: This should be dynamic
        this.data = []
    }

    async connectedCallback() {
        super.connectedCallback();
        this.id = getRouter().location.params.id;
        if (this.authorizeAndRedirect()) {
            this.addEventListener('updatedResponse', async (event) => {
                const { subfactorId, response } = event.detail;
                await saveResponse(this.id, subfactorId, response);
            });
        }
    }

    async disconnectedCallback() {
        super.disconnectedCallback();
        this.removeEventListener('updatedResponse');
    }

    /**
     * TODO: Why does it not wait for the promise to resolve? 
     * Now the body is empty and doesn't contain an error property.
     */
    async authorizeAndRedirect() {
        const survey = await this._fetchData(this.id);
        if (survey.hasOwnProperty('error')) {
            console.warning('Unauthorized access to survey. Redirecting to home page.')
            router.navigate('/');
            return false;
        }
        return true;
    }

    async _fetchData(id) {
        this.data = new Task(this, {
            task: async ([id, page, pageSize]) => getSurvey(id, page, pageSize),
            args: () => [id, this.page, this.pageSize]
        });
        return await this.data.run();
    }

    render() {
        if (this.data._value.error) {
            return html`
                <h1>Tool</h1>
                <p>An error occured while loading the questions: ${this.data._value.message}</p>`;
        }
        return html`
            <gi-info-popup></gi-info-popup>
                ${this.data.render({
                    loading: () => html`<p>Loading...</p>`,
                    error: (error) => html`<p>An error occured while loading the questions: ${error.message}</p>`,
                    complete: (data) => html`
                        <a href="/intervention/${this.id}">Back to overview</a>
                        <h1><strong>Domein ${data.category.number}</strong> - ${data.category.name}</h1>
                        <div class="survey">
                            <div class="header">
                                <div class="column">Vraag</div>
                                <div class="column">Faciliterende factor</div>
                                <div class="column">Prioriteit</div>
                                <div class="column">Opmerkingen</div>
                            </div>
                            ${data.factors.map((factor) => html`    
                                <div class="factor">
                                    <h2 class="full-width"><strong>${factor.number}</strong> - ${factor.title}</h2>
                                    ${factor.subfactors.map((subfactor) => html`
                                        <gi-survey-subfactor .subfactor=${subfactor}></gi-survey-subfactor>
                                    `)}
                                </div>
                            `)}
                        </div>
                    `
                })}
        `
    }
}

window.customElements.define('gi-survey', Survey);
