import {css, html, LitElement} from "lit";
import {HorizontalBarChart} from "../surveyReport/charts/HorizontalBarChart.js";
import {getInterventionByPersonId} from "../../../services/InterventionService.js";
import {Router} from "@vaadin/router";
import globalStyles from "../../../assets/global-styles.js";

export class MyInterventionsBox extends LitElement {
    static styles = [globalStyles, css`
        .my-interventions-container {
            width: 650px;
            margin-bottom: 50px;
        }

        .my-interventions-header {
            background-color: var(--color-primary);
            color: white;
            margin-top: 25px;
            border-radius: 5px 5px 0 0;
        }

        .my-interventions-header h3 {
            padding: 10px;
            margin: 0;
        }

        .my-interventions-list {
            border: 1px solid #DEDEDE;
            border-top: none;
            border-radius: 0 0 5px 5px;
            overflow-y: auto;
            max-height: 550px;
        }
      
      .bekijk-button {
            background-color: var(--color-primary);
            color: white;
            padding: 10px 60px 10px 60px;
            border-radius: 25px;
            font-size: 16px;
            text-decoration: none;
            border: none;
            cursor: pointer;
      }

        .my-interventions-item {
            padding: 10px;
            border-bottom: 1px solid #DEDEDE;
        }

        .my-interventions-item-name {
            font-weight: bold;
        }

        .my-interventions-progress-container {
            padding: 10px;
            box-shadow: rgba(0, 0, 0, 0.1) 0 4px 8px;
            border-radius: 10px;
        }

        .my-interventions-item-description {
            border-bottom: 1px solid lightgray;;
            margin-bottom: 10px;
            padding: 10px 10px 10px 0;
            color: gray;
        }

        .my-interventions-item-description p {
            margin: 0;
        }

        .my-interventions-item-progress {
            height: 50px;
        }
        
        .my-interventions-btn {
            width: 100%;
            justify-content: flex-end;
            display: flex;
            margin-bottom: 5px;
            margin-right: 20px;
            margin-top: 10px;
        }
        
        .my-interventions-btn a {
            background-color: var(--color-primary);
            color: white;
            padding: 10px 60px 10px 60px;
            border-radius: 25px;
            font-size: 16px;
            text-decoration: none;
        }
        
        @media (max-width: 768px) {
            .my-interventions-container {
                width: 100%;
            }
        }

    ;
    `];

    constructor() {
        super();
        this.interventieData = [{}];
        this.loading = true;
    }

    static properties = {
        userId: {
            type: Number,
            reflect: true
        }
    }

    async connectedCallback() {
        super.connectedCallback();
    }

    async getInterventionsByPersonId(userId) {
        this.interventieData = await getInterventionByPersonId(userId);
        this.loading = false;
        this.userId = userId;
        this.requestUpdate();
    }

    updated(changedProperties) {
        if (changedProperties.has('userId')) {
            this.getInterventionsByPersonId(this.userId);
        }
    }

   fetchIntervention(intervention) {
        Router.go(`/intervention/${intervention.id}`);
   }

   filterDuplicateInterventions() {
        let filteredInterventions = [];
        this.interventieData.forEach(interventie => {
            if (!filteredInterventions.some(intervention => intervention.id === interventie.id)) {
                filteredInterventions.push(interventie);
            }
        });
        this.interventieData = filteredInterventions;
   }

    renderInterventions(){
        if (this.loading) {  // If loading, don't render the chart
            return html`Loading...`;
        }

        this.filterDuplicateInterventions();
        if (this.interventieData && this.interventieData.length > 0) {
            return this.interventieData.map(interventie => {
                let progress = Array.isArray(interventie.totalSurveyProgress) ? interventie.totalSurveyProgress : [interventie.totalSurveyProgress];
                return html`
                <div class="my-interventions-item">
                    <div class="my-interventions-item-name">
                        ${interventie.name}
                    </div>
                    <div class="my-interventions-progress-container">
                        <div class="my-interventions-item-description">
                            <p>Progressie over huidige fase</p>
                        </div>
                        <div class="my-interventions-item-progress">
                            <horizontal-bar-chart .chartDatasetLabel="Bar" .chartData=${progress}
                                                  .chartLabels=${["Percentage"]}
                                                  .chartColors=${['#63ABFD']}></horizontal-bar-chart>
                        </div>
                    </div>
                    <div class="my-interventions-btn">
                        <button class="bekijk-button" @click="${() => this.fetchIntervention(interventie)}">Bekijk</button>
                    </div>
                </div>
            `;
            });
        }
        return html`
    <div class="my-interventions-item">
        <div class="my-interventions-item-name">
            Geen interventies gevonden.
        </div>
    </div>
`;
    }

    render() {
        return html`
            <div class="my-interventions-container">
                <div class="my-interventions-header">
                    <h3>Mijn interventie deelnames</h3>
                </div>
                <div class="my-interventions-list">
                    ${this.renderInterventions()}
                </div>
            </div>
        `;
    }
}

window.customElements.define('my-intervention-box', MyInterventionsBox);
