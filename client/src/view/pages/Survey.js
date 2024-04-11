import { html, LitElement } from "lit";

export class Survey extends LitElement {
    render() {
        return html`
            <h1>Survey</h1>
        `
    }
}

window.customElements.define('gi-survey', Survey);
