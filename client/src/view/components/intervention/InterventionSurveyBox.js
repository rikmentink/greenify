import {css, html, LitElement} from "lit";
export class InterventionSurveyBox extends LitElement {
    static styles = css`
        .title-container {
            display: flex;
            align-items: center;
        }

        .title-container h2 {
            margin-right: 40px; 
        }

        .start-fase-btn {
            height: 25px;
            padding: 10px 25px 10px 25px;
            border-radius: 2px;
            font-weight: bold;
            border: none;
            cursor: pointer;
            background-color: #4CBB17;
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
            color: #666666;
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
            color: #4CBB17;
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
            background-color: #4CBB17;
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
    `;

    static properties = {
        id: {
            type: Number,
            reflect: true
        }
    }

    constructor() {
        super();
        this.id = 0;
        this.surveyData = [{
            id: 1,
            name: "Vragenlijst 1",
            description: "Dit is de beschrijving van de vragenlijst. Kan de gebruiker uiteraard ook zelf instellen.",
            phase: "Research"
        }, {
            id: 2,
            name: "Vragenlijst 2",
            description: "Dit is de beschrijving van de vragenlijst. Kan de gebruiker uiteraard ook zelf instellen.",
            phase: "Implementatie"
        }, {
            id: 3,
            name: "Vragenlijst 3",
            description: "Dit is de beschrijving van de vragenlijst. Kan de gebruiker uiteraard ook zelf instellen.",
            phase: "Evaluatie"
        }, {
            id: 4,
            name: "Vragenlijst 4",
            description: "Dit is de beschrijving van de vragenlijst. Kan de gebruiker uiteraard ook zelf instellen.",
            phase: "Research"
        }, {
            id: 5,
            name: "Vragenlijst 5",
            description: "Dit is de beschrijving van de vragenlijst. Kan de gebruiker uiteraard ook zelf instellen.",
            phase: "Implementatie"
        }, {
            id: 6,
            name: "Vragenlijst 6",
            description: "Dit is de beschrijving van de vragenlijst. Kan de gebruiker uiteraard ook zelf instellen.",
            phase: "Evaluatie"
        }
        ];
    }

    renderSurveys() {
        return this.surveyData.map(survey => {
            let progress = 60 + "%" // Placeholder for progress, need to be calculated
            return html`
                <div class="survey-box">
                    <p class="sy-header"><span class="sy-name">${survey.name} </span><span class="sy-phase">Fase - ${survey.phase}</span></p>
                    <p class="sy-description">${survey.description}</p>
                    <div class="sy-status">
                        <a href="">Bekijk vragen &#10132;</a>
                        <a href="">Bekijk eindrapport &#10132;</a>
                    </div>
                    <div class="sy-progress-container">
                        <div class="progress-labels">
                            <p class="progress-label">Nog ... vragen te gaan</p>
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

    render() {
        return html`
            <div class="title-container">
                <h2>Vragenlijsten</h2>
                <a class="start-fase-btn" href="/createphase/${this.id}">Nieuwe fase starten</a>
            </div>
            <div class="survey-container">
                ${this.renderSurveys()}
            </div>
        `;
    }
}

customElements.define('intervention-survey-box', InterventionSurveyBox);