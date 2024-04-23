import { LitElement, html, css } from "lit";

import { SurveyQuestion } from "./SurveyQuestion.js";

export class SurveySubfactor extends LitElement {
    static properties = {
        subfactor: { type: Object },
        response: { type: Object },
    }

    static styles = css`
        .subfactor {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1rem;
        }

        .subfactor > .subfactor__name {
            flex: 1 1 auto;
            margin: 0;
        }

        .subfactor > .subfactor__question {
            display: flex;
            justify-content: space-between;
            align-items: center;
            flex: 0 0 auto;
        }

        .subfactor > .subfactor__comments {
            flex: 0 0 auto;
        }
    `

    constructor() {
        super();
        this.subfactor = {};
        this.response = {};
    }

    render() {
        return html`
                <div class="subfactor__name">${this.subfactor.title}</div>
                <div class="subfactor__question">
                    <gi-survey-question></gi-survey-question>
                </div>
                <div class="subfactor__question">
                    <gi-survey-question></gi-survey-question>
                </div>
                <div class="subfactor__comments">FA</div>
        `
    }

    createRenderRoot() {
        return this;
    }
}

window.customElements.define('gi-survey-subfactor', SurveySubfactor);