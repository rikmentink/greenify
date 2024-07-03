import { LitElement, html, css } from "lit";

import { SurveyQuestion } from "./SurveyQuestion.js";

export class SurveySubfactor extends LitElement {
    static properties = {
        subfactor: { type: Object },
        response: { type: Object },
        commentDisabled: { type: Boolean }
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
        display: flex;
        align-items: center;
        padding-right: 1rem;
        border-right: 1px solid #d6d6d6;
      }

      .subfactor__comments > input {
        background-color: white;
        color: black;
        border: 1px solid var(--color-primary);
        padding: .5rem;
        border-radius: 20px;
        font-size: 10px;
      }

      .subfactor__comments > input::placeholder {
        color: darkgray;
      }

      .subfactor__comments > input:disabled {
        background-color: #efefef;
        color: darkgray;
        border: 1px solid #d6d6d6;
      }

      .subfactor__delete {
        display: flex;
        align-items: center;
      }

      .subfactor__delete > input {
        background-color: var(--color-primary);
        color: white;
        border: 1px solid var(--color-primary);
        padding: .5rem;
        border-radius: 20px;
        cursor: pointer;
        font-size: 10px;
      }

      .subfactor__delete > input:hover {
        background-color: white;
        color: grey;
      }

      .popup {
        display: none;
        position: absolute;
        background-color: white;
        color: black;
        border: 1px solid #ccc;
        border-radius: 5px;
        padding: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        z-index: 10;
        font-weight: normal;
        overflow-wrap: break-word;
        margin-bottom: 6rem;
        font-size: 10px;
        max-width: 300px;
        max-height: 40px;
        overflow-y: auto;
      }

      .subfactor__comments:focus-within .popup{
        display: block;
      }
    `

    constructor() {
        super();
        this.subfactor = {};
        this.response = {
            facilitatingFactor: -1,
            priority: -1,
            comment: ''
        };
        this.displayInputLabels = false;
        this.commentDisabled = true;
    }
    
    firstUpdated() {
        if (this.subfactor.response) {
            this.response = this.subfactor.response;
        }

        // Enable the comment input if both a response was provided for the facilitating factor and the priority
        if (this.response.facilitatingFactor !== -1 && this.response.priority !== -1) {
            this.commentDisabled = false;
        }
    }

    async connectedCallback() {
        super.connectedCallback();
        this.addEventListener('answer', event => {
            this.response[event.detail.question] = event.detail.answer;
            // Only dispatch the event if both a response has been provided for the facilitating factor and the priority
            if (this.response.facilitatingFactor && this.response.priority) {
                this.dispatchEvent(new CustomEvent('updatedResponse', {
                    detail: {
                        subfactorId: this.subfactor.id,
                        response: this.response,
                    },
                    bubbles: true,
                    composed: true,
                }));
            }
        });

        this.addEventListener('answer-changed', () => {
            const commentInput = this.shadowRoot.querySelector('.subfactor__comments > input');
            commentInput.disabled = this.response.facilitatingFactor === -1 || this.response.priority === -1;
        });
    }

    _commentChanged(e) {
        this.response.comment = e.target.value;
        this.dispatchEvent(new CustomEvent('answer', {
            detail: {
                subfactorId: this.subfactor.id,
                response: this.response,
            },
            bubbles: true,
            composed: true,
        }));
    }

    _deleteRequest() {
        this.dispatchEvent(new CustomEvent('delete', {
            detail: {
                subfactorId: this.subfactor.id
            },
            bubbles: true,
            composed: true
        }));
        this._resetInputs();
    }

    _resetInputs() {
        // Disabled relevant comment field
        const commentInput = this.shadowRoot.querySelector('.subfactor__comments > input');
        commentInput.value = '';
        commentInput.disabled = true;

        // Uncheck all radio buttons
        const surveyQuestions = this.shadowRoot.querySelectorAll('gi-survey-question');
        surveyQuestions.forEach(question => {
            const radioButtons = question.shadowRoot.querySelectorAll('input[type="radio"]');
            radioButtons.forEach(radioButton => {
                radioButton.checked = false;
            });
        });
    }

    async disconnectedCallback() {
        super.disconnectedCallback();
        this.removeEventListener('answer');
        this.removeEventListener('answer-changed')
    }

    render() {
        return html`
            <div class="subfactor__name">${this.subfactor.title}</div>
            <div class="subfactor__question">
                <gi-survey-question name="facilitatingFactor" .answer=${this.response.facilitatingFactor} .displayInputLabels=${this.displayInputLabels}></gi-survey-question>
            </div>
            <div class="subfactor__question">
                <gi-survey-question name="priority" .answer=${this.response.priority} .displayInputLabels=${this.displayInputLabels}></gi-survey-question>
            </div>
            <div class="subfactor__comments">
                <input type="text" .value=${this.response.comment} @input=${e => this.response.comment = e.target.value} @change="${this._commentChanged}" placeholder="Opmerking..." ?disabled=${this.commentDisabled}/>
                <div class="popup">${this.response.comment}</div>
            </div>
            <div class="subfactor__delete">
                <input type="button" value="Verwijder" @click=${this._deleteRequest}>
            </div>
        `
    }
}

window.customElements.define('gi-survey-subfactor', SurveySubfactor);