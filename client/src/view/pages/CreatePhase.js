import { LitElement, html, css } from 'lit';
import globalStyles from "../../assets/global-styles.js";
import {createPhase} from "../../services/PhaseService.js";
import {Router} from "@vaadin/router";

export class CreatePhase extends LitElement {
    static styles = [globalStyles, css`
        h1 {
            color: var(--color-primary);
        }

        .outer-container {
            display: flex;
            justify-content: center;
            width: 100%;
        }

        .title-container {
            display: flex;
            justify-content: center;
            width: 100%;
        }

        .desc-container {
            display: flex;
            justify-content: center;
            width: 100%;
        }

        .description {
            font-size: 12px;
            color: slategrey;
            margin-top: -6px;
        }

        .main-block {
            box-shadow: rgba(0, 0, 0, 0.1) 0 4px 8px;
            padding: 15px;
            background-color: white;
            border-radius: 30px;
            display: flex;
            flex-direction: column;
            align-items: center;
            width: 50%;
            border: var(--color-primary) 1px solid;
        }

        .create-btn {
            padding: 10px 50px 10px 50px;
            margin: 8px 0;
            font-size: 16px;
            font-weight: bold;
            border: none;
            border-radius: 40px;
            cursor: pointer;
            background-color: var(--color-primary);
            color: white;
        }

        .phase-select {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            height: 40px;
            font-size: 14px;
            box-sizing: border-box;
            border: lightgray 1px solid;
        }
        
        .phase-select:focus {
            outline: none;
        }
    `]

    static properties = {
        id: {
            type: Number,
            reflect: true
        }
    };



    constructor() {
        super();
        this.interventionId = 0;
    }

    connectedCallback() {
        super.connectedCallback();
        this.extractUrl();
    }

    extractUrl() {
        let url = window.location.href;
        url = url.split('/');
        this.interventionId = url = url[url.length - 2];
        console.log(url);
    }

    extractInput() {
        const PhaseEnum = {
            INITIATION: "Initiation",
            PLANNING: "Planning",
            EXECUTION: "Execution"
        };

        const phaseString = this.shadowRoot.getElementById('phase').value;
        const phaseEnum = PhaseEnum[phaseString.toUpperCase()].toUpperCase();

        this.handleAddPhase(phaseEnum);
    }

    async handleAddPhase(phase) {
        await createPhase(this.interventionId, phase);
        Router.go(`/intervention/${this.interventionId}`);
    }

    render() {
        return html`
            <h1>Fase Creëren</h1>
            <div class="title-container">
                <h1>Naam</h1>
            </div>
            <div class="desc-container">
                <p class="description">🛈 Per fase wordt er een vragenlijst beschikbaar gesteld voor alle toegevoegde gebruikers.</p>
            </div>
            <div class="outer-container">
                <div class="main-block">
                    <select class="phase-select" id="phase" required>
                        <option value="Initiation">Initiation</option>
                        <option value="Planning">Planning</option>
                        <option value="Execution">Execution</option>
                    </select>
                    <button class="create-btn" type="submit" @click="${this.extractInput}">Creëren</button>
                </div>
            </div>
        `;
    }
}

window.customElements.define('gi-createphase', CreatePhase);