import { LitElement, html, css } from "lit";
import { Task } from "@lit/task";
import { getRouter } from "../../router.js";
import global from "../../assets/global-styles.js";

import { getSurvey } from '../../services/SurveyService.js';
import { saveResponse } from '../../services/SurveyService.js';
import { deleteResponse } from '../../services/SurveyService.js';

import { SurveySubfactor } from "../components/survey/SurveySubfactor.js";

export class Survey extends LitElement {
    static properties = {
        id: { type: Number },
        categoryId: { type: Number },
        nextCategoryId: { type: Number },
        page: { type: Number },
        pageSize: { type: Number }
    }

    static styles = [global, css`
        .survey {
            margin-bottom: 2.5rem;
        }
        .survey > .header,
        .survey > .factor gi-survey-subfactor {
            display: grid;
            grid-template-columns: 6fr 2.5fr 2fr 1.4fr 1fr;
            gap: 1rem;
        }
        h1 {
            font-size: 1.5rem;
            font-weight: normal;
            margin-bottom: 0;
            color: var(--color-text);
        }
        .survey > .factor > h2 {
            font-size: 1.25rem;
            font-weight: normal;
            margin-top: 0;
            color: var(--color-text);
        }
        .survey > .header > .column {
            font-weight: bold;
        }
        .survey > .header > .column:not(:first-child) {
            text-align: center;
        }
        .survey > .factor > ol {
            padding-left: 1.25rem;
        }
        .survey > .factor > ol > li {
            padding-left: .5rem;
        }
        .survey > .factor > ol > li > gi-survey-subfactor {
            margin-bottom: .5rem;
        } 
      
        .btn-container {
          display: flex;
          justify-content: flex-end;
          margin-top: 3rem;
        }
      
        .scroll-to-top {
            position: fixed;
            bottom: 1rem;
            right: 1rem;
            background-color: var(--color-primary);
            color: white;
            width: 2rem;
            height: 2rem;
            line-height: 2rem;
            text-align: center;
            border-radius: 25%;
            font-size: 1.5rem;
            z-index: 1000;
            text-decoration: none;
        }

        .info-icon {
          display: inline-block;
          width: 15px;
          height: 15px;
          background-color: #ccc;
          color: white;
          border-color: #ccc;
          text-align: center;
          border-radius: 50%;
          font-size: 10px;
          line-height: 15px;
          margin-left: 10px;
          cursor: pointer;
          position: relative;
        }

        .popup {
          display: none;
          position: absolute;
          top: 30px;
          left: 0;
          background-color: white;
          color: black;
          border: 1px solid #ccc;
          border-radius: 5px;
          padding: 10px;
          box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
          z-index: 10;
          width: 200px;
          font-weight: normal;
        }

        .info-icon:hover .popup {
          display: block;
        }
    `]

    constructor() {
        super();
        this.id = 0;
        this.categoryId = 0;
        this.page = 1;
        this.pageSize = 10;
        this.data = []
        this.displayInputLabels = false;
    }

    async connectedCallback() {
        super.connectedCallback();
        if (!this.extractParams()) {
            window.location.href = '/';
        }

        const urlParams = new URLSearchParams(window.location.search);
        const myParam = urlParams.get('category');
        this.categoryId = myParam ? parseInt(myParam) : 0;

        await this._fetchData(this.id);
        if (this.authorizeAndRedirect()) {
            this.addEventListener('updatedResponse', async (event) => {
                const { subfactorId, response } = event.detail;
                await saveResponse(this.id, subfactorId, response);
            });

            this.addEventListener('delete', async (event) => {
                const subfactorId = event.detail.subfactorId;
                await deleteResponse(this.id, subfactorId);
            });
        }
    }

    async disconnectedCallback() {
        super.disconnectedCallback();
        this.removeEventListener('updatedResponse');
    }

    extractParams() {
        this.id = getRouter().location.params.id || 0;
        if (this.id === 0) return false;

        const params = new URLSearchParams(window.location.search);
        this.categoryId = params.get('category') || 0;
        this.page = params.get('page') || 0;
        this.pageSize = params.get('pageSize') || 0;

        if (this.categoryId < 0 || this.page < 0 || this.pageSize < 0) return false;
        return true;
    }

    async authorizeAndRedirect() {
        if (
          this.data._value &&
          this.data._value.error &&
          this.data._value.error === "unauthorized"
        ) {
          window.location.href = "/";
          return false;
        }
        
        return true;
    }

    async _fetchData(id) {
        this.data = new Task(this, {
            task: async ([id, categoryId, page, pageSize]) => getSurvey(id, categoryId, page, pageSize),
            args: () => [id, this.categoryId, this.page, this.pageSize]
        });
        return await this.data.run();
    }

    render() {
        return html`
                ${this.data.render({
                    loading: () => html`<p>Loading...</p>`,
                    error: (error) => html`<p>An error occured while loading the questions: ${error.message}</p>`,
                    complete: (survey) => html`
                        <a class="scroll-to-top" href="#top">
                            <i>&uarr;</i>
                        </a>
                        <a class="link" href="/intervention/${survey.interventionId}/phase/${survey.phaseId}">&larr; Terug naar overzicht</a>
                        ${survey.categories.map((category) => html`
                            <h1><strong>Domein ${category.number}</strong> - ${category.name}</h1>
                            <div class="survey">
                                <div class="header">
                                    <div class="column"></div>
                                    <div class="column">
                                        Faciliterende factor
                                        <span class="info-icon">i
                                            <div class="popup">Hieronder kunt u per factor aangeven in hoeverre hiermee rekening gehouden is tijdens de implementatie van de NBI.</div>
                                        </span>
                                    </div>
                                    <div class="column">
                                        Prioriteit
                                        <span class="info-icon">i
                                            <div class="popup">In hoeverre heeft deze factor prioriteit om aan te pakken?</div>
                                        </span>
                                    </div>
                                    <div class="column">Opmerkingen</div>
                                    <div></div> <!-- Empty column for the delete button -->
                                </div>
                                ${category.factors.map((factor) => html`    
                                    <div class="factor">
                                        <h2 class="full-width"><strong>${factor.number}</strong> - ${factor.title}</h2>
                                        <ol>
                                            ${factor.subfactors.sort((a, b) => a.number - b.number).map((subfactor, subfactorIndex) => html`
                                                <li value="${subfactor.number}" id="subfactor${subfactor.number}">
                                                    <gi-survey-subfactor .subfactor=${subfactor} .displayInputLabels=${subfactorIndex === 0}></gi-survey-subfactor>
                                                </li>
                                            `)}
                                        </ol>
                                    </div>
                                `)}
                                <div class="btn-container">
                                    <a class="btn" href="/tool/${this.id}?category=${survey.nextCategoryId || 0}">Ga direct naar volgende categorie &rarr;</a>
                                </div>
                            </div>
                        `)}
                    `
                })}
        `
    }
}

window.customElements.define('gi-survey', Survey);
