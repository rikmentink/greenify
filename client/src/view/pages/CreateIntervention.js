import { LitElement, html, css } from 'lit';
import {getRouter} from "../../router.js";
import {createInterventionWithPhase} from "../../services/InterventionService.js";

export class CreateIntervention extends LitElement {
    static styles = [css`
        
        h1{
            color: #4CBB17;
        }

        *:focus {
            outline: none;
        }
        
        .create-intervention-container {
            display: flex;
            justify-content: center;
            width: 100%;
        }
        
        .create-intervention-block {
            box-shadow: rgba(0, 0, 0, 0.1) 0 4px 8px;
            padding: 20px;
            background-color: white;
            border-radius: 30px;
        }
        
        input {
            width: 90%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 0;
            height: 40px;
            box-sizing: border-box;
            border-bottom: #4CBB17 2px solid;
        }
        
        button[type=submit] {
            width: 100%;
            background-color: #4CBB17;
            color: white;
            padding: 18px 80px;
            margin: 8px 0;
            font-size: 16px;
            font-weight: bold;
            border: none;
            border-radius: 40px;
            cursor: pointer;
        }
        
        button[type=submit]:hover {
            color: lightgray;
        }
        
        p {
            font-size: 12px;
            color: #4CBB17;
            margin-top: -6px;
        }
        
        input[type=checkbox] {
            width: 15px;
            height: 15px;
            margin-right: 10px;
        }
        
        .btn-container {
            grid-column: span 2;
            display: grid;
            justify-content: flex-end;
        }
        
        .error-message {
            color: red;
        }

        .phase-select {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            height: 40px;
            font-size: 14px;
            box-sizing: border-box;
            border: lightgray 1px solid;
            color: gray;
        }

        .phase-select:focus {
            outline: none;
        }

        .info-icon {
            display: inline-block;
            width: 20px;
            height: 20px;
            background-color: #4CBB17;
            color: white;
            text-align: center;
            border-radius: 50%;
            font-size: 14px;
            line-height: 20px;
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
        }

        .info-icon:hover .popup {
            display: block;
        }

        @media (min-width: 992px) {
            .intervention-form {
                width: 400px;
                height: 250px;
                padding: 50px;
            }
            
            .intervention-form {
                display: grid;
                align-content: center;
                grid-gap: 10px;
                grid-template-columns: repeat(2, 1fr); 
                grid-template-rows: auto auto auto; 
            }

            .input-group {
                grid-column: span 2; 
            }
        }

    `];

    constructor() {
        super();
        this.personId = 0;
    }

    connectedCallback() {
        super.connectedCallback();
        this.personId = getRouter().location.params.id || 0;
    }

    async submitForm(event) {
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);
        const interventionName = formData.get('interventionName');
        const interventionDescription = formData.get('interventionDescription');
        const informationPhase = formData.get('informationPhase');
        const phase = formData.get('phase');

        await createInterventionWithPhase(this.personId, interventionName, interventionDescription, informationPhase, phase)
            .then(() => {
                window.location.href = import.meta.env.BASE_URL + 'home';
            })
            .catch(error => {
                this.handleErrorMessage(error.message);
            });
    }

    firstUpdated() {
        this.shadowRoot.querySelector('.intervention-form').addEventListener('submit', this.submitForm.bind(this))
    }

    handleErrorMessage(error) {
        const errorMessage = this.shadowRoot.querySelector('.error-message');
        errorMessage.textContent = error;
    }

    // Render the component
    render() {
        return html`
            <h1>Interventie creëren</h1>
            <div class="create-intervention-container">
                <div class="create-intervention-block">
                    <form class="intervention-form">
                        <div class="input-group">
                            <div class="info-group">
                                <input class="interventionNameField" type="text" id="interventionName" name="interventionName" placeholder="Interventie naam" required>
                                <span class="info-icon">i
                                    <div class="popup">Geef een korte, duidelijke naam voor je interventie. Houd het beknopt maar beschrijvend. Bijvoorbeeld: "Tuin zuid".</div>
                                </span>
                                <input class="interventionDescriptionField" type="text" id="interventionDescription" name="interventionDescription" placeholder="Interventie beschrijving" required>
                                <span class="info-icon">i
                                    <div class="popup">Geef een korte beschrijving van de interventie. Vermeld de belangrijkste doelstellingen en acties die worden ondernomen.</div>
                                </span>
                                <input class="informationPhaseField" type="text" id="informationPhase" name="informationPhase" placeholder="Informatie fase" required>
                                <span class="info-icon">i
                                    <div class="popup">Geef hier wat extra informatie aan voor de geselecteerde fase.</div>
                                </span>
                            </div>
                            <select class="phase-select" id="phase" name="phase" required>
                                <option value="">Kies een fase</option>
                                <option value="INITIATION">Initiation</option>
                                <option value="PLANNING">Planning</option>
                                <option value="EXECUTION">Execution</option>
                            </select>
                        </div>
                        <div class="btn-container">
                            <button type="submit">Creëren</button>
                        </div>
                        <p class="error-message"></p>
                    </form>
                </div>
            </div>
        `;
    }
}

window.customElements.define('gi-createintervention', CreateIntervention);