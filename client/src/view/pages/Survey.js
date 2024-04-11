import { html, LitElement } from "lit";
import { Task } from "@lit/task";

export class Survey extends LitElement {
    static properties = {
        id: { type: Number },
        page: { type: Number },
        pageSize: { type: Number },
        questions: { type: Array },
    }

    constructor() {
        super();
        this.id = 0;
        this.page = 1;
        this.pageSize = 10; // TODO: This should be dynamic
        this.questions = [];
        this.data = new Task(this, {
            task: async ([id, page, pageSize]) => this._fetchData(id, page, pageSize),
            args: () => [this.id, this.page, this.pageSize]
        });
    }

    async _fetchData(id, page, pageSize) {
        const response = await fetch(`/api/survey/${id}?page=${page}&pageSize=${pageSize}`, {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' }
        });
        if (!response.ok) {
          throw new Error(`An HTTP error occured. HTTP ${response.status}: ${response.statusText}`);
        }
        return response.json();
    }

    render() {
        return html`
            <h1>Survey</h1>
            <p>Note: this page is static temporarily for development purposes.</p>
            <p>Survey ID: ${this.id}</p>
            ${this.data.render({
                loading: () => html`<p>Loading...</p>`,
                error: (error) => html`<p>An error occured while loading the questions: ${error.message}</p>`,
                data: (questions) => html`
                    <ul>
                        ${questions.map((question) => html`
                            <li>${question}</li>
                        `)}
                    </ul>
                `
            })}
        `
    }
}

window.customElements.define('gi-survey', Survey);
