import {css, html, LitElement} from "lit";
import {HorizontalBarChart} from "../surveyReport/charts/HorizontalBarChart.js";

export class CreateInterventionsBox extends LitElement {
    static styles = [css`
        .create-interventions-container {
            width: 60%;
            margin-bottom: 50px;
        }

        .create-interventions-header {
            background-color: #4CBB17;
            color: white;
            margin-top: 25px;
            border-radius: 5px 5px 0 0;
            text-align: center;
        }

        .create-interventions-header h3 {
            padding: 10px;
            margin: 0;
        }

        .create-interventions-content {
            padding: 0 15px 15px 15px;
            text-align: center;
            box-shadow: rgba(0, 0, 0, 0.1) 0 4px 8px;
            border-radius: 10px;
        }
        
        .create-interventions-btn-container {
            display: flex;
            justify-content: flex-end;
        }

        .create-interventions-btn {
            background-color: #4CBB17;
            color: white;
            padding: 10px 50px 10px 50px;
            border-radius: 25px;
            font-size: 18px;
            text-decoration: none;
        }
    ;
    `];

    static properties = {
        personId: {
            type: Number,
            reflect: true
        }
    }

    constructor() {
        super();
    }


    connectedCallback() {
        super.connectedCallback();
    }

    render() {
        return html`
            <div class="create-interventions-container">
                <div class="create-interventions-header">
                    <h3>Nieuwe interventie aanmaken</h3>
                </div>
                <div class="create-interventions-content">
                    <p>Bepaal welke groene interventie u wilt optimaliseren en beheer de gebruikersdie kunnen bijdragen
                        aan het invullen van de vragenlijst om de groene interventie te optimaliseren.</p>
                    <div class="create-interventions-btn-container">
                        <a href="/intervention/${this.personId}/new-intervention" class="create-interventions-btn">Creëer</a>
                    </div>
                </div>
            </div>
        `;
    }
}

window.customElements.define('create-intervention-box', CreateInterventionsBox);
