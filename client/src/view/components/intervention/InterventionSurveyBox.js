import {css, html, LitElement} from "lit";
import {getInterventionById, getPhasesByInterventionId} from "../../../services/InterventionService.js";
import {getPhaseById} from "../../../services/PhaseService.js";
import globalStyles from "../../../assets/global-styles.js";
import {Router} from "@vaadin/router";
import {Task} from "@lit/task";
export class InterventionSurveyBox extends LitElement {
    static styles = [globalStyles, css`
        .title-container {
            display: flex;
            align-items: center;
        }

        .title-container h2 {
            margin-right: 20px; 
        }

        .start-fase-btn {
            padding: 15px 25px 15px 15px;
            border-radius: 2px;
            margin-bottom: 20px;
            font-weight: bold;
            border: none;
            cursor: pointer;
            background-color: var(--color-primary);
            color: white;
            text-decoration: none;
            display: inline-block; 
        }
        
        .survey-container {
            display: flex;
            flex-wrap: nowrap; 
            width: 100%;
            overflow-x: auto; 
            -webkit-overflow-scrolling: touch;
            padding-bottom: 16px;
            scrollbar-width: thin;
            scroll-behavior: smooth;
        }

        .survey-box {
            flex: 0 0 auto;
            width: 400px;
            border: 1px solid #DEDEDE;
            padding: 20px;
            margin-right: 10px;
            margin-bottom: 10px;
            background-color: #FDFDFD;
        }
        
        .sy-header {
            margin: 0;
        }
        
        .sy-name {
            font-weight: bold;
            margin-right: 20px;
        }
        
        .sy-phase {
            font-weight: bold;
            font-size: 1.1em;
        }
        
        .sy-description {
            margin-top: 10px;
            margin-bottom: 10px;
        }
        
        .sy-status {
            display: flex;
            flex-direction: column;
        }
        
        .sy-status a {
            cursor: pointer;
            color: var(--color-primary);
            text-decoration: none;
            font-weight: bold;
            margin-bottom: 10px;
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

      .button-container {
        display: flex;
        justify-content: space-between; /* Adjust as needed */
        margin: 10px 0;
      }

      .button-container a {
        margin-right: 10px; /* Adjust as needed */
      }

        .progress-label {
            margin: 0;
        }

        .survey-container::-webkit-scrollbar {
            width: 8px;
        }

        .survey-container::-webkit-scrollbar-track {
            background: rgba(0, 0, 0, 0);
        }

        .survey-container::-webkit-scrollbar-thumb {
            background-color: rgba(0, 0, 0, 0.2);
            border-radius: 4px;
        }

        .survey-container::-webkit-scrollbar-thumb:hover {
            background-color: rgba(0, 0, 0, 0.4);
        }
    `];

    static properties = {
        id: {
            type: Number,
            reflect: true
        },
        intervention: {
            type: Object
        },
        loading: {
            type: Boolean
        }
    }

    constructor() {
        super();
        this.id = 0;
        this.phaseData = [];
        this.loading = true;
        this.data = {};
    }

    async connectedCallback() {
        super.connectedCallback();
        await this.fetchIntervention();
    }


    async fetchIntervention() {
        const selectedIntervention = JSON.parse(sessionStorage.getItem('selectedIntervention'));
        await this._fetchData(selectedIntervention.id);

        if (selectedIntervention) {
            this.interventionData = this.data;
        }

        this.phaseData = await getPhasesByInterventionId(selectedIntervention.id);
        this.loading = false;
    }

    async _fetchData(id) {
        this.data = new Task(this, {
            task: async ([id]) => getInterventionById(id),
            args: () => [id]
        });
        return await this.data.run();
    }

    showCurrentPhase(phase) {
        if (phase === this.interventionData.value.currentPhase.id) {
            return '2px solid var(--color-primary)';
        }

        return 'none'
    }

    renderSurveys() {
        if(this.loading) {
            return html`<p>Loading...</p>`;
        } else {
            const sortedPhases = [...this.phaseData].sort((a, b) => {
                if (a.id === this.interventionData.value.currentPhase.id) return -1;
                if (b.id === this.interventionData.value.currentPhase.id) return 1;
                return 0;
            });

            return sortedPhases.map(phase => {
                let progress = Math.round(phase.progress) + "%"
                return html`
            <div class="survey-box" style="border: ${this.showCurrentPhase(phase.id)}">
                <p class="sy-header"><span class="sy-phase">Naam: ${phase.name}</span></p>
                <div class="sy-progress-container">
                    <div class="progress-labels">
                    </div>
                    <div class="button-container">
                        <a class="start-fase-btn" href="/intervention/${this.interventionData.id}/phase/${phase.id}">Bekijk Tool &#10132;</a>
                        <a class="start-fase-btn" @click=${() => this.navigateToReport(phase.id, phase.name, this.interventionData.id)}>Bekijk Eindrapport &#10132;</a>
                    </div>
                    <div class="progress-bar">
                        <div class="progress" style="width: ${progress}" aria-label="Progression bar"></div>
                    </div>
                    <div class="progress-labels">
                        <p class="progress-label">${progress}</p>
                    </div>
                </div>
            </div>
        `;
            });
        }
    }

    navigateToReport(phaseId, phaseName, interventionId) {
        const interventionNameParam = encodeURIComponent(this.interventionData.name);
        const phaseNameParam = encodeURIComponent(phaseName);
        const interventionIdParam = encodeURIComponent(interventionId);
        Router.go(`/phase/${phaseId}/report?&interventionId=${interventionIdParam}&interventionName=${interventionNameParam}&phaseName=${phaseNameParam}`);
    }


    render() {
        return html`
            <div class="1ntainer">
                <hr>
            <div class="survey-container">
                ${this.renderSurveys()}
            </div>
            <div class="1ntainer">
                <a class="start-fase-btn" href="/intervention/${this.data.value.id}/new-phase">Nieuwe fase starten</a>
                <hr>
            </div>`;
    }
}

customElements.define('intervention-survey-box', InterventionSurveyBox);