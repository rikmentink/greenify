import { LitElement, html, css } from "lit";

export class SurveyQuestion extends LitElement {
    static properties = {
    }

    static styles = css`
    `

    constructor() {
        super();
    }

    render() {
        return html`
            <div>Question.</div>
        `
    }
}

window.customElements.define('gi-survey-question', SurveyQuestion);