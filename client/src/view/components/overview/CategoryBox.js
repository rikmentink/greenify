import { html, css, LitElement } from 'lit';
import global from "../../../assets/global-styles.js";

export class CategoryBox extends LitElement {
    static styles = [global, css`
      .title {
        color: black;
        margin-top: 0;
        margin-bottom: 0;
      }

      .rectangle {
        display: grid;
        grid-template-columns: 1fr 1fr;
        border: #D6D6D6 2px solid;
        padding: 20px;
        border-radius: 8px;
        overflow: hidden;
      }

      .title-description {
        display: flex;
        flex-direction: column;
      }

      .button-container {
        display: flex;
        justify-content: flex-end;
        align-items: flex-start;
      }

      .questions-container {
        display: flex;
        flex-direction: row;
        gap: 10px;
        margin-bottom: 0;
        max-height: 0;
        overflow: hidden;
        transition: all 0.5s;
      }

      .my-question-btn {
        display: flex;
      }

      .my-question-btn a.disabled {
        background-color: rgba(224, 224, 224, .35);
      }

      .my-question-btn a.enabled {
        background-color: rgba(97, 255, 0, .35);
      }

      .my-question-btn a {
        color: var(--color-text);
        padding: 10px 17.6px;
        border-radius: 4px;
        font-size: 16px;
        text-decoration: none;
      }

      .sy-progress-container {
        align-items: center;
        line-height: 0.1;
        display: flex;
        flex-direction: column;
      }

      .sy-progress-container {
        align-items: center;
        line-height: 0.1;
        display: flex;
        flex-direction: column;
      }

      .progress-wrapper {
        width: 100%;
        margin-bottom: .5rem;
      }

      .progress-labels {
        width: 100%;
        display: flex;
        color: #666666;
        font-style: italic;
        justify-content: flex-end;
        margin-top: 10px;
      }

      .progress-bar {
        width: 100%;
        background-color: #e0e0e0;
        height: 4px;
        border-radius: 5px;
        margin-top: 10px;
        display: flex;
      }

      .progress-bar > .progress {
        width: 100%;
        height: 4px;
        background-color: var(--color-primary);
        border-radius: 5px;
      }

      .progress-value {
        font-size: 13px;
        width: 0%;
        text-align: right;
      }

      .progress-label {
        line-height: 1;
        margin: .25rem 0 .75rem 0;
      }

      .show-more {
        color: #6e706e;
        background-color: transparent;
        text-decoration: none;
        cursor: pointer;
      }

      .show-less {
        color: #6e706e;
        background-color: transparent;
        text-decoration: none;
        cursor: pointer;
        display: none;
      }

      .rectangle.expanded .show-more {
        display: none;
      }

      .rectangle.expanded .show-less {
        display: block;
      }

      .rectangle.expanded .questions-container {
        max-height: 10rem;
        margin-bottom: 10px;
      }

        @media (max-width: 767px) {
            .title {
                font-size: 18px;
            }
            
            .progress-wrapper {
                white-space: nowrap;
            }
        }
    `];

    static properties = {
        category: { type: Object },
        progress: { type: Array },
        surveyId: { type: Number },
        expanded: { type: Boolean }
    }

    constructor() {
        super();
        this.category = {};
        this.progress = [];
        this.surveyId = 0;
        this.expanded = false;
    }

    _getUserProgress() {
        if (!this.progress) {
            return 0;
        }
        const currentUser = this.progress.find(participant => participant.currentUser);
        if (!currentUser) {
            return 0;
        }

        const categoryProgress = currentUser.progress.find(p => p.categoryId === this.category.id);
        if (!categoryProgress) {
            return 0;
        }

        return categoryProgress.progress;
    }

    _isSubfactorAnswered(subfactorId) {
        const currentUser = this.progress.find(participant => participant.currentUser);
        if (!currentUser) {
            return false;
        }

        const response = currentUser.responses.find(response => response.subfactorId === subfactorId);
        return response && response.facilitatingFactor && response.priority;
    }

    _toggleExpand() {
        this.expanded = !this.expanded;
    }

    _calculateProgressData() {
        const userProgress = this._getUserProgress();
        const totalQuestions = this.category.subfactors.length;
        const answeredQuestions = Math.round(userProgress * totalQuestions);
        const progressPercentage = Math.round(userProgress * 100);

        return { totalQuestions, answeredQuestions, progressPercentage };
    }

    render() {
        const { totalQuestions, answeredQuestions, progressPercentage } = this._calculateProgressData();

        return html`
            <div class="rectangle ${this.expanded ? 'expanded' : ''}" id="show-hide-text">
                <div class="title-description">
                    <h2 class="title">${this.category.name}</h2>
                    <div class="sy-progress-container">
                        <div class="progress-wrapper">
                          <p>${answeredQuestions} van de ${totalQuestions} factoren beantwoord</p>
                          <div class="progress-bar">
                              <div class="progress" style="width: ${progressPercentage}%;" aria-label="Progression bar"></div>
                          </div>
                            <p class="progress-value" style="width: ${progressPercentage}%;">${progressPercentage}%</p>
                        </div>
                    </div>
                    <div class="questions-container">
                        ${this.category.subfactors.map((subfactor) => html`
                            <div class="my-question-btn">
                                <a class="${this._isSubfactorAnswered(subfactor.id) ? 'enabled' : 'disabled'}" href="/tool/${this.surveyId}?categoryId=${this.category.id}#subfactor${subfactor.number}">${subfactor.number}</a>
                            </div>`)}
                    </div>
                </div>
                <div class="button-container">
                  <a href="/tool/${this.surveyId}?category=${this.category.id}" class="btn">Ga verder</a>
                </div>
                <a class="show-more" @click="${this._toggleExpand}">Bekijk Vooruitgang <span style="font-size: 1.25rem;">&vee;</span></a>
                <a class="show-less" @click="${this._toggleExpand}">Bekijk Vooruitgang <span style="font-size: 1.25rem;">&wedge;</span></a>
            </div>
        `;
    }
}

customElements.define('gi-categorybox', CategoryBox);
