import { html, css, LitElement } from 'lit';
import globalStyles from "../../../assets/global-styles.js";

import { CategoryQuestionItem } from './CategoryQuestionItem.js';

export class CategoryBox extends LitElement {
    static styles = [globalStyles, css`
      p, h1, h2, h3, h4, h5, h6, em {
        color: black;
      }

      .title {
        color: black;
        margin-top: 0;
        margin-bottom: 0;
      }

      .rectangle {
        display: grid;
        grid-template-columns: 1fr 1fr;
        min-width: 200px;
        min-height: 100px;
        width: 600px;
        height: 100px;
        border: #D6D6D6 2px solid;
        margin-top: 10px;
        padding: 20px;
        border-radius: 5px;
        overflow: hidden;
        transition: height 0.3s ease;
      }

      .rectangle.expanded {
        height: 200px;
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

      greenify-button {
        color: black;
      }

      greenify-categoryquestionbutton {
        color: black;
      }

      .my-category-btn {
        width: 100%;
        justify-content: flex-end;
        display: flex;
        margin-bottom: 5px;
        margin-right: 20px;
        margin-top: 10px;
      }

      .my-category-btn a {
        background-color: var(--color-primary);
        color: white;
        padding: 10px 60px 10px 60px;
        border-radius: 2px;
        font-size: 16px;
        text-decoration: none;
      }


      .questions-container {
        display: none;
        flex-direction: row;
        gap: 10px;
        margin-top: 10px;
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

      .progress-bar {
        width: 100%;
        background-color: #e0e0e0;
        height: 10px;
        border-radius: 5px;
        margin-top: 10px;
        display: flex;
      }

      .progress {
        height: 100%;
        background-color: var(--color-primary)
        border-radius: 5px;
      }

      .progress-labels {
        width: 100%;
        display: flex;
        color: #666666;
        font-style: italic;
        justify-content: flex-end;
        margin-top: 10px;
      }

      .progress-label {
        margin: 0;
      }

      .progress-bar {
        width: 100%;
        background-color: #e0e0e0;
        height: 10px;
        border-radius: 5px;
        margin-top: 10px;
        display: flex;
      }

      .progress {
        height: 100%;
        background-color: var(--color-primary);
        border-radius: 5px;
      }

      .progress-labels {
        width: 100%;
        display: flex;
        color: #666666;
        font-style: italic;
        justify-content: flex-end;
        margin-top: 10px;
      }

      .progress-label {
        margin: 0;
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
        display: flex;
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
            throw new Error('Progress is not set');
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

    _getSubfactorAnswers(subfactorNumber) {
      let answers = this.progress
        .filter(participant => participant.responses.some(response => response.subfactorNumber === subfactorNumber))
        .map(participant => participant.responses.find(response => response.subfactorNumber === subfactorNumber));

      console.log(`All answers for subfactor ${subfactorNumber}`)
      console.log(answers);

      return answers;
    }

    _isSubfactorAnswered(subfactorNumber) {
        const currentUser = this.progress.find(participant => participant.currentUser);
        if (!currentUser) {
            return false;
        }

        return currentUser.responses.some(response => response.subfactorNumber === subfactorNumber);
    }

    render() {
        const { totalQuestions, answeredQuestions, progressPercentage } = this._calculateProgressData();

        return html`
            <div class="rectangle ${this.expanded ? 'expanded' : ''}" id="show-hide-text">
                <div class="title-description">
                    <h2 class="title">${this.category.name}</h2>
                    <div class="sy-progress-container">
                        <div class="progress-labels">
                            <p class="progress-label">Nog ${totalQuestions - answeredQuestions} vragen te gaan</p>
                        </div>
                        <div class="progress-bar">
                            <div class="progress" style="width: ${progressPercentage}%;" aria-label="Progression bar"></div>
                        </div>
                        <div class="progress-labels">
                            <p class="progress-label">${progressPercentage}%</p>
                        </div>
                    </div>
                    <div class="questions-container">
                        ${this.category.subfactors.map((subfactor) => html`
                          <gi-categoryquestion 
                            .subfactorNumber=${subfactor.number} 
                            .answered=${this._isSubfactorAnswered(subfactor.number)} 
                            .categoryId=${this.category.id} 
                            .surveyId=${this.surveyId}
                            .answers=${this._getSubfactorAnswers(subfactor.number)}
                          ></gi-categoryquestion>
                        `)}
                    </div>
                </div>
                <div class="button-container">
                    <div class="my-category-btn">
                        <a href="/tool/${this.surveyId}?category=${this.category.id}">Ga verder</a>
                    </div>
                </div>
                <a class="show-more" @click="${this._toggleExpand}">Bekijk Vooruitgang V</a>
                <a class="show-less" @click="${this._toggleExpand}">Bekijk Vooruitgang Ʌ</a>
            </div>
        `;
    }
}

customElements.define('gi-categorybox', CategoryBox);
