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

      .grid-container {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: .75rem;
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
        margin-top: 1rem;
      }

      .algemeneinfo {
        font-size: 18px;
      }

      .title-desc > h1 {
        margin: 0;
      }

      .title-desc > p {
        margin-top: .25rem;
      }

      hr.divider {
        border-color: #f8f8f8;
        margin-left: 20px;
        height: 100%;
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

      await this._fetchData(this.interventionId, this.phaseId);
      this.authorizeAndRedirect();
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
            complete: (data) => html`
                <a href="/intervention/${this.interventionId}" class="link">&larr; Terug naar interventie</a>
                <div class="content">
                    <div class="title-desc">
                        <h1>Vragenlijst</h1>
                        <p>Hier kunt u alle vragen en categorieën bekijken. Klik op een categorie en vraag om deze in te vullen.</p>
                    </div>
                    <hr class="divider">
                    <div>
                        <h2 class="algemeneinfo">Algemene informatie</h2>
                        <dl class="grid-container">
                            <dt>Fase</dt>
                            <dd>${data.name}</dd>
                            <dt>Voortgang</dt>
                            <dd>${this.getAnsweredQuestions(data.contenders)}/${this.getTotalQuestions(data.categories)} beantwoorden vragen</dd>
                        </div>
                    </div>
                </div>
                <h2>Categorieën</h2>
                <div class="grid-container">
                  ${data.categories ? data.categories.map(category => html`
                    <gi-categorybox .progress=${data.contenders} .category=${category} .surveyId=${data.surveyId}></gi-categorybox>`) : html`
                    <p>Loading categories...</p>`
                  }
                </div>
            `});
    }
}

customElements.define('gi-overview', Overview);
