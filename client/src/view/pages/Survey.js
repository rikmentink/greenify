import { LitElement, html, css } from "lit";
import { Task } from "@lit/task";

import { getSurvey } from '../../services/SurveyService.js';

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
            grid-template-columns: 4fr 2fr 2fr 1fr;
            gap: 1rem;
        }
        .survey > h1,
        .survey > .factor > h2 {
            font-weight: normal;
        }
    `

    constructor() {
        super();
        this.id = 1;
        this.page = 1;
        this.pageSize = 10; // TODO: This should be dynamic
        this.data = new Task(this, {
            task: async ([id, page, pageSize]) => getSurvey(id, page, pageSize),
            args: () => [this.id, this.page, this.pageSize]
        })

        // TODO: Should redirect back if the survey id is not found
    }

    render() {
        return html`
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
