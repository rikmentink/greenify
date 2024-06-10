import {css, html, LitElement} from "lit";
import {HorizontalBarChart} from "../surveyReport/charts/HorizontalBarChart.js";

export class MyInterventionsBox extends LitElement {
    static styles = [css`
        .my-interventions-container {
            width: 650px;
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
        this.interventieData = [{
            id: 1,
            name: "Interventie 1",
            totalSurveys: 2,
            progress: [10],
        }, {
            id: 2,
            name: "Interventie 2",
            totalSurveys: 2,
            progress: [20],
        }, {
            id: 3,
            name: "Interventie 3",
            totalSurveys: 3,
            progress: [30],
        }]
    }

    static properties = {
        userId: {
            type: Number,
            reflect: true
        }
    }

    renderInterventions(){
        return this.interventieData.map(interventie => {
            return html`
                <div class="my-interventions-item">
                    <div class="my-interventions-item-name">
                        ${interventie.name}
                    </div>
                    <div class="my-interventions-progress-container">
                        <div class="my-interventions-item-description">
                            <p>Mijn progressie over ${interventie.totalSurveys} vragenlijsten</p>
                        </div>
                        <div class="my-interventions-item-progress">
                            <horizontal-bar-chart .chartDatasetLabel="Bar" .chartData=${interventie.progress} .chartLabels=${["Percentage"]} .chartColors=${['#63ABFD']}></horizontal-bar-chart>
                        </div>
                    </div>
                    <div class="my-interventions-btn">
                        <a href="">Bekijk</a>
                    </div>
                </div>
            `;
        });
    }


    connectedCallback() {
        super.connectedCallback();
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
