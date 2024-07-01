import {css, html, LitElement} from "lit";
import {getInterventionById} from "../../../services/InterventionService.js";
export class InterventionInformationBox extends LitElement {
    static styles = css`
        .information-container {
            display: flex;
            width: 100%;
            flex-direction: column;
            justify-content: space-between;
        }
        
        h3, p {
            margin: 0;
        }
        
        h1 {
            margin-bottom: 0;
        }

        @media (max-width: 767px) {
            .information-container {
                flex-direction: column;
            }
            
            .information-box {
                margin-left: 0;
            }
        }
    `;

    constructor() {
        super();
        this.interventionData = [{}]
    }

    connectedCallback() {
        super.connectedCallback();
        this.loading = false;
    }

    renderInterventionInformation() {
        return html`
            <div class="information-container">
                <div class="description-box">
                    <p><span><b>Beschrijving: </b></span>${this.interventionData.description}</p>
                    <p><span><b>Huidige fase: </b></span> ${this.interventionData.currentPhase ? this.interventionData.currentPhase.name : 'N/A'}</p>
                </div>
            </div>
        `;
    }

    render() {
        if (!this.interventionData.name) {
            return html`<p>Loading...</p>`;
        }

        return html`
            <h1>Naam: ${this.interventionData.name}</h1>
            <div class="information-container">
                ${this.renderInterventionInformation()}
            </div>
        `;
    }
}

customElements.define('intervention-information-box', InterventionInformationBox);