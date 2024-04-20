import { html, LitElement } from "lit";
import { Task } from "@lit/task";

import { getSurvey } from '../../services/SurveyService.js';

export class Survey extends LitElement {
    static properties = {
        id: { type: Number },
        page: { type: Number },
        pageSize: { type: Number }
    }

    constructor() {
        super();
        this.id = 1;
        this.page = 1;
        this.pageSize = 10; // TODO: This should be dynamic
        this.data = new Task(this, {
            task: async ([id, page, pageSize]) => getSurvey(id, page, pageSize),
            args: () => [this.id, this.page, this.pageSize]
        });
    }

    render() {
        return html`
            <h1>Survey</h1>
            <p>Note: this page is static temporarily for development purposes.</p>
            <div class="survey">
                ${this.data.render({
                    loading: () => html`<p>Loading...</p>`,
                    error: (error) => html`<p>An error occured while loading the questions: ${error.message}</p>`,
                    data: (factors) => html`
                        <ul>
                            ${factors.map((question) => html`
                                <li>${factors}</li>
                            `)}
                        </ul>
                    `
                })}
            </div>
        `
    }
}

window.customElements.define('gi-survey', Survey);
