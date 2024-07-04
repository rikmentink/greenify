import { LitElement, html, css } from 'lit';
import {getRouter} from "../../router.js";
import {createInterventionWithPhase} from "../../services/InterventionService.js";
import globalStyles from "../../assets/global-styles.js";

export class CreateIntervention extends LitElement {
    static styles = [globalStyles, css`
        
        h1{
            color: var(--color-primary);
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
            width: 90% !important;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 0;
            height: 40px;
            box-sizing: border-box;
            border-bottom: var(--color-primary) 2px solid;
        }
        
        button[type=submit] {
            width: 100%;
            background-color: var(--color-primary);
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
            color: var(--color-primary);
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
            background-color: var(--color-primary);
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
        
        .info-icon.phasedesc {
            top: -28px;
        }
        
        .phase-description-field {
            width: 80%;
        }
        
        .phase-select {
            height: 45px;
        }
        
        dialog {
            width: 20%;
            height: 200px;
            background-color: white;
            border-radius: 20px;
            position: fixed;
            border: none;
        }
        
        dialog p {
            text-align: center;
            padding-top: 30px;
            font-size: 20px;
            font-weight: bold;;
        }
        
        .check-icon {
            display: flex;
            justify-content: center;
            font-size: 50px;
            font-weight: bolder;
            color: var(--color-primary);
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

        @media (max-width: 767px) {
            dialog {
                width: 90%;
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
        const phaseDescription = formData.get('phaseDescription');
        const phase = formData.get('phase');

        await createInterventionWithPhase(this.personId, interventionName, interventionDescription, phase, phaseDescription)
            .then(() => {
                this.showDialog();
                setTimeout(() => {
                    window.location.href = import.meta.env.BASE_URL + 'home';
                }, 1000);
            })
            .catch(error => {
                this.handleErrorMessage(error.message);
            });
    }

    showDialog() {
        this.shadowRoot.querySelector('dialog').showModal();
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
                                <input class="intervention-name-field" type="text" id="interventionName" name="interventionName" placeholder="Interventie naam" required>
                                <span class="info-icon">i
                                    <div class="popup">Geef een korte, duidelijke naam voor je interventie. Houd het beknopt maar beschrijvend. Bijvoorbeeld: "Tuin zuid".</div>
                                </span>
                                <input class="intervention-description-field" type="text" id="interventionDescription" name="interventionDescription" placeholder="Interventie beschrijving" required>
                                <span class="info-icon">i
                                    <div class="popup">Geef een korte beschrijving van de interventie. Vermeld de belangrijkste doelstellingen en acties die worden ondernomen.</div>
                                </span>
                                <select class="phase-select" id="phase" name="phase" required>
                                    <option value="">Kies een fase</option>
                                    <option value="DEVELOPMENT">Ontwikkeling</option>
                                    <option value="EXECUTION">Uitvoering</option>
                                    <option value="EVALUATION">Evaluatie</option>
                                </select>
                                <textarea class="phase-description-field" id="phaseDescription" name="phaseDescription" placeholder="Beschrijving fase" required></textarea>
                                <span class="info-icon phasedesc">i
                                    <div class="popup">Geef hier wat extra informatie aan voor de geselecteerde fase.</div>
                                </span>
                            </div>
                        </div>
                        <div class="btn-container">
                            <button type="submit">Creëren</button>
                        </div>
                        <p class="error-message"></p>
                    </form>
                </div>
            </div>
            <dialog>
                <p>Uw interventie is succesvol aangemaakt!</p>
                <div class="check-icon">
                    <span>&#10003;</span>
                </div>
            </dialog>
        `;
    }
}

window.customElements.define('gi-createintervention', CreateIntervention);
