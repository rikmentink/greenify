import { html, LitElement } from "lit";

export class Survey extends LitElement {
    render() {
        return html`
            <h1>Survey</h1>
            <p>Note: this page is static temporarily for development purposes.</p>
        `
    }
}

window.customElements.define('gi-survey', Survey);
