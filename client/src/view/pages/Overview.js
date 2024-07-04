import { css, html, LitElement } from 'lit';
import { Task } from "@lit/task";
import { Router } from "@vaadin/router";
import { getRouter } from "../../router.js";

import { getOverview } from '../../services/OverviewService.js';
import global from '../../assets/global-styles.js';
import { InfoPopUp } from "../components/containers/InfoPopUp.js";

import { CategoryBox } from '../components/overview/CategoryBox.js';
import {getInterventionById} from "../../services/InterventionService.js";

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

          .algemeneinfo h2{
              font-size: 18px;
          }
          
          .algemeneinfo dt {
              font-weight: bold;
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
              width: 0;
          }
          
          .categorie-container {
              margin-bottom: 50px;
          }
          
          @media (max-width: 767px) {
              .grid-container {
                  grid-template-columns: 1fr;
              }
              
              .alg-info-container {
                  gap: 0;
              }

              .popup-btn {
                  font-size: 10px;
              }
          }
    `];

    constructor() {
        super();
        this.interventionId = 0;
        this.phaseId = 0;
        this.data = {};
        this.interventionData = {};
        this.attachShadow({ mode: 'open' });
    }

    async connectedCallback() {
      super.connectedCallback();
      this.interventionId = getRouter().location.params.interventionId || 0;
      this.phaseId = getRouter().location.params.phaseId || 0;
      if (this.interventionId == 0 || this.phaseId == 0) {
        Router.go("/")
        return;
      }


      await this._fetchData(this.interventionId, this.phaseId);
      await this._fetchInterventionData(this.interventionId);
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

    async _fetchInterventionData(interventionId) {
        this.interventionData = new Task(this, {
            task: async ([interventionId]) => getInterventionById(interventionId),
            args: () => [interventionId]
        });
        return await this.interventionData.run();
    }

    getTotalQuestions(categories) {
        return categories.reduce((total, category) => total + category.subfactors.length, 0);
    }

    getAnsweredQuestions(contenders) {
        const currentUser = contenders[0];
        if (!currentUser) return 0;

        return currentUser.responses.length;
    }

    async popUpHandeler(event){
        event.preventDefault(); // Prevent default link behavior
        const infoPopup = this.shadowRoot.getElementById('infoPopup');
        const dialog = infoPopup.shadowRoot.querySelector('dialog');
        dialog.showModal();
    }

    render() {
        const sortedCategories = this.data._value?.categories.sort((a, b) => a.name.localeCompare(b.name));

        return this.data.render({
            loading: () => html`<p>Loading...</p>`,
            error: (data) => html`<p>An error occured while loading the questions: ${data.message}</p>`,
            complete: (data) => html`
                <gi-info-popup id="infoPopup"></gi-info-popup>
                <a href="/intervention/${this.interventionId}" class="link">&larr; Terug naar interventie</a>
                <div class="content">
                    <div class="title-desc">
                        <h1>Tool</h1>
                        <p>Hier kunt u alle factoren en categorieën bekijken. Klik op een categorie en factor om deze in te vullen.</p>
                        <a class="btn-white popup-btn" @click="${this.popUpHandeler.bind(this)}">Toon informatie over de tool</a>
                    </div>
                    <hr class="divider">
                    <div class="algemeneinfo">
                        <dl class="grid-container alg-info-container">
                            <dt>Interventie:</dt>
                            <dd>${this.interventionData._value?.name ?? ''}</dd>
                            <dt>Huidige fase:</dt>
                            <dd>${data.name}</dd>
                            <dt>Voortgang:</dt>
                            <dd>${this.getAnsweredQuestions(data.contenders)}/${this.getTotalQuestions(data.categories)} beantwoorden factoren</dd>
                        </div>
                    </div>
                </div>
                <h2>Categorieën</h2>
                <div class="grid-container categorie-container">
                ${sortedCategories ? sortedCategories.map(category => html`
                    <gi-categorybox .progress=${data.contenders} .category=${category} .surveyId=${data.surveyId}></gi-categorybox>`) : html`
                    <p>Loading categories...</p>`
                  }
                </div>
            `});
    }
}

customElements.define('gi-overview', Overview);
