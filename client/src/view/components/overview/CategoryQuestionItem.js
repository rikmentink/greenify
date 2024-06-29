import { html, css, LitElement } from 'lit';
import global from "../../../assets/global-styles.js";

export class CategoryQuestionItem extends LitElement {
    static styles = [
        global, 
        css`
            .my-question-btn {
                display: flex;
            }

            .my-question-btn a.disabled {
                background-color: grey;
            }

            .my-question-btn a.enabled {
                background-color: var(--color-primary);
            }

            .my-question-btn a {
                background-color: var(--color-primary);
                color: white;
                padding: 10px 17.6px;
                border-radius: 2px;
                font-size: 16px;
                text-decoration: none;
            }
        `
    ];

    static properties = {
        subfactorNumber: { type: Number },
        isAnswered: { type: Boolean },
        categoryId: { type: Number },
        surveyId: { type: Number }
    }

    constructor() {
        super();
        this.subfactorNumber = 0;
        this.isAnswered = false;
        this.categoryId = 0;
        this.surveyId = 0;
    }

    render() {
        return html`
            <div class="my-question-btn">
                <a class="${this.answered ? 'enabled' : 'disabled'}" href="/tool/${this.surveyId}?categoryId=${this.categoryId}#subfactor${this.subfactorNumber}">${this.subfactorNumber}</a>
            </div>
        `;
    }
}

customElements.define('gi-categoryquestion', CategoryQuestionItem);
