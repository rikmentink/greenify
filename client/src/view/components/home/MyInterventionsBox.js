import {css, html, LitElement} from "lit";
import {HorizontalBarChart} from "../surveyReport/charts/HorizontalBarChart.js";
import {getInterventionByPersonId} from "../../../services/InterventionService.js";
import {getRouter} from "../../../router.js";
import {saveResponse} from "../../../services/SurveyService.js";
import {Router} from "@vaadin/router";

export class MyInterventionsBox extends LitElement {
    static styles = [css`
        .my-interventions-container {
            width: 50%;
            margin-bottom: 50px;
        }

        .my-interventions-header {
            background-color: #4CBB17;
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
            background-color: #4CBB17;
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
      
      .my-interventions-btn button {
            background-color: #4CBB17;
            color: white;
            padding: 10px 60px 10px 60px;
            border-radius: 25px;
            font-size: 16px;
            text-decoration: none;
            border: none;
            cursor: pointer;
      }
        
        .my-interventions-btn a {
            background-color: #4CBB17;
            color: white;
            padding: 10px 60px 10px 60px;
            border-radius: 25px;
            font-size: 16px;
            text-decoration: none;
        }
        
        @media (max-width: 768px) {
            .my-interventions-container {
                width: 90%;
            }
        }

    ;
    `];

    constructor() {
        super();
        this.interventionData = [{}];
        this.loading = true;
    }

    static properties = {
        userId: {
            type: Number,
            reflect: true
        },
    }

    async connectedCallback() {
        super.connectedCallback();
    }

    async getInterventionsByPersonId(userId) {
        this.interventionData = await getInterventionByPersonId(userId);
        this.loading = false;
        this.requestUpdate();
    }

    updated(changedProperties) {
        if (changedProperties.has('userId')) {
            this.getInterventionsByPersonId(this.userId);
        }
    }

    fetchIntervention(intervention) {
        sessionStorage.setItem('selectedIntervention', JSON.stringify(intervention));
        Router.go(`/intervention/${intervention.id}`);
    }

    renderInterventions(){
        if (this.loading) {  // If loading, don't render the chart
            return html`Loading...`;
        }

        if (this.interventionData && this.interventionData.length > 0) {
            return this.interventionData.map(intervention => {
                let progress = Array.isArray(intervention.progress) ? intervention.progress : [intervention.progress];
                return html`
            <div class="my-interventions-item">
                <div class="my-interventions-item-name">
                    ${intervention.name}
                </div>
                <div class="my-interventions-progress-container">
                    <div class="my-interventions-item-description">
                        <p>Mijn progressie over ${intervention.surveyAmount} vragenlijst(en)</p>
                    </div>
                    <div class="my-interventions-item-progress">
                        <horizontal-bar-chart .chartDatasetLabel="Bar" .chartData=${progress}
                                              .chartLabels=${["Percentage"]}
                                              .chartColors=${['#63ABFD']}></horizontal-bar-chart>
                    </div>
                </div>
                <div class="my-interventions-btn">
                    <button @click=${() => this.fetchIntervention(intervention)}>Bekijk</button>
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
