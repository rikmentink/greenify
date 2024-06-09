import {css, html, LitElement} from "lit";
import {getInterventionById} from "../../../services/InterventionService.js";
export class InterventionInformationBox extends LitElement {
    static styles = css`
        .information-container {
            display: flex;
            width: 100%;
            flex-direction: row; 
            justify-content: space-between;
        }

        .description-box {
            padding: 10px;
            flex: 1; 
        }
        
        h3, p {
            margin: 0;
        }
        
        h1 {
            margin-bottom: 0;
        }
        
        span {
            font-style: italic;
        }
        
        .information-box {
            flex: 1; 
            margin-left: 20px; 
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
            <div class="description-box">
                <p>${this.interventionData.description}</p>
            </div>
            <div class="information-box">
                <h3>Algemene informatie</h3>
                <p><span>Huidige fase: </span> ${this.interventionData.currentPhase ? this.interventionData.currentPhase.name : 'N/A'}</p>
            </div>
        `;
    }

    render() {
        console.log(this.interventionData)
        if (!this.interventionData.name) {
            return html`<p>Loading...</p>`;
        }

        return html`
            <h1>${this.interventionData.name}</h1>
            <div class="information-container">
                ${this.renderInterventionInformation()}
            </div>
        `;
    }
}

customElements.define('intervention-information-box', InterventionInformationBox);