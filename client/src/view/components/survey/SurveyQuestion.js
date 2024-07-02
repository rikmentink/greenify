import { LitElement, html, css } from "lit";

import data from "../../../data/questions.js"

export class SurveyQuestion extends LitElement {
    static properties = {
        name: { type: String },
        answer: { type: Number }
    }

    static styles = css`
      :host {
        width: 100%;
      }

      .question {
        display: flex;
        justify-content: space-between;
        align-items: center;
      }

      .question > .answer {
        display: flex;
        flex-direction: column;
        align-items: center;
      }

      .question > .answer > label {
        font-size: 11px;
        font-weight: 350;
        line-height: 1.1;
        transform: rotate(-45deg) translate(50%, -100%);
        position: absolute;
        max-width: 50px;
        color: #000000;
      }

      .question > .answer > input {
        -webkit-appearance: none;
        -moz-appearance: none;
        appearance: none;
        display: inline-block;
        width: 1rem;
        height: 1rem;
        padding: 0.5rem;
        border: 2px solid #929292;
        border-radius: 50%;
      }

      .question > .answer > input:checked {
        border-color: var(--color-primary);
        background-color: rgba(var(--color-primary-rgb), .2);
      }
    `

    constructor() {
        super();
        this.name = '';
        this.answer = -1;
        this.displayInputLabels = false;
    }

    render() {
        return html`
            <div class="question" data-question="${this.name}">
                ${this._findOptions().map(option => html`
                    <div class="answer">
                        ${this.displayInputLabels ? html`<label class="inputLabel">${option.displayName}</label>` : ''}
                        <input type="radio" name="${this.name}" value="${option.name}" ?checked="${this.answer === option.value}" @change=${this._selectAnswer}/>
                    </div>
                `)}
            </div>
        `
    }

    _findOptions() {
        return data.questions.find(question => question.question === this.name).options;
    }

    _selectAnswer(event) {
        this.dispatchEvent(
            new CustomEvent('answer', {
                detail: {
                    question: event.target.name,
                    answer: event.target.value,
                },
                bubbles: true,
                composed: true
            })
        );
        // Dispatch an event to notify the parent component that the answer has changed
        this.dispatchEvent(new Event('answer-changed', { bubbles: true, composed: true }));
    }
}

window.customElements.define('gi-survey-question', SurveyQuestion);