import { LitElement, html, css } from "lit";

import { SurveyQuestion } from "./SurveyQuestion.js";

export class SurveySubfactor extends LitElement {
    static properties = {
        subfactor: { type: Object },
        response: { type: Object },
    }

    static styles = css`
        .subfactor__name {
            flex: 1 1 auto;
            margin: 0;
        }

        .subfactor__question {
            display: flex;
            justify-content: space-between;
            align-items: center;
            flex: 0 0 auto;
            border-right: 1px solid #d6d6d6;
            padding-right: 1rem;
        }

        .subfactor__comments {
            flex: 0 0 auto;
            text-align: center;
        }
    `

    constructor() {
        super();
        this.subfactor = {};
        this.response = {};
    }

    async connectedCallback() {
        super.connectedCallback();
        this.addEventListener('answer', event => {
            this.response[event.detail.question] = event.detail.answer;
            this.dispatchEvent(new CustomEvent('updatedResponse', {
                detail: {
                    subfactorId: this.subfactor.id,
                    response: this.response,
                },
                bubbles: true,
                composed: true,
            }));
        });
    }

    async disconnectedCallback() {
        super.disconnectedCallback();
        this.removeEventListener('answer');
    }

    render() {
        return html`
                <div class="subfactor__name">${this.subfactor.title}</div>
                <div class="subfactor__question">
                    <gi-survey-question name="facilitatingFactor"></gi-survey-question>
                </div>
                <div class="subfactor__question">
                    <gi-survey-question name="priority"></gi-survey-question>
                </div>
                <div class="subfactor__comments">FA</div>
        `
    }
}

window.customElements.define('gi-survey-subfactor', SurveySubfactor);