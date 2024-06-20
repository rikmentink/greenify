import { css, html, LitElement } from 'lit';
import { Task } from "@lit/task";
import { getRouter } from "../../router.js";

import { getOverview } from '../../services/OverviewService.js';
import global from '../../assets/global-styles.js';

import { CategoryBox } from '../components/overview/CategoryBox.js';

export class Overview extends LitElement {
    static styles = [
      global,
      css`
      p, h1, h3, em {
        color: black;
      }

      .grid-container {
        display: grid;
        grid-template-columns: auto auto;
      }

      .grid-container div {
        padding: 5px;
      }

      .fase, .voortgang {
        margin-top: 0;
        margin-left: 10px;
      }

      .content {
        display: grid;
        grid-template-columns: auto auto 1fr;
        align-items: start;
        gap: 20px;
      }

      .description {
        max-width: 600px;
      }

      .title-desc {
        min-width: 43vw;
      }

      .algemeneinfo {
        margin-top: 30px;
      }

      hr.divider {
        margin-top: 25px;
        border-color: #f8f8f8;
        margin-left: 20px;
        min-height: 150px;
        flex-grow: 1;
        width: 0px;
      }
    `];

    constructor() {
        super();
        this.interventionId = 0;
        this.phaseId = 0;
        this.data = {};
    }

    async connectedCallback() {
      super.connectedCallback();
      this.interventionId = getRouter().location.params.interventionId || 0;
      this.phaseId = getRouter().location.params.phaseId || 0;
      this.authorizeAndRedirect();
    }

    async authorizeAndRedirect() {
        if (this.interventionId == 0 || this.phaseId == 0) {
            window.location.href = '/';
            return false;
        }

        const phase = await this._fetchData(this.interventionId, this.phaseId);
        if (phase.hasOwnProperty('error')) {
            window.location.href = '/';
            return false;
        }
        
        return true;
    }

    async _fetchData(interventionId, phaseId) {
        this.data = new Task(this, {
            task: async ([interventionId, phaseId]) => getOverview(interventionId, phaseId),
            args: () => [interventionId, phaseId]
        });
        return await this.data.run();
    }

    getTotalQuestions(categories) {
        return categories.reduce((total, category) => total + category.subfactors.length, 0);
    }

    getAnsweredQuestions(contenders) {
        const currentUser = contenders[0];
        if (!currentUser) return 0;

        return currentUser.responses.length;
    }

    render() {
        return this.data.render({
            loading: () => html`<p>Loading...</p>`,
            error: (data) => html`<p>An error occured while loading the questions: ${data.message}</p>`,
            complete: (data) => {
                const totalQuestions = this.getTotalQuestions(data.categories);
                const answeredQuestions = this.getAnsweredQuestions(data.contenders);

                return html`
                    <div class="content">
                        <div class="title-desc">
                            <h1>Vragenlijst</h1>
                            <p>Hier kunt u alle vragen en categorieën bekijken. Klik op een categorie en vraag om deze in te vullen.</p>
                        </div>
                        <hr class="divider">
                        <div>
                            <h3 class="algemeneinfo">Algemene Informatie</h3>
                            <div class="grid-container">
                                <em>Fase</em>
                                <p class="fase">${data.name}</p>
                                <em>Voortgang</em>
                                <p class="voortgang">${answeredQuestions}/${totalQuestions} beantwoorden vragen</p>
                            </div>
                        </div>
                    </div>
                    <h1>Categorieën</h1>

                    ${data.categories ? data.categories.map(category => html`
                        <gi-categorybox .progress=${data.contenders} .category=${category}></gi-categorybox>`) : html`
                        <p>Loading categories...</p>`}
                `
            }
        });
    }
}

customElements.define('gi-overview', Overview);
