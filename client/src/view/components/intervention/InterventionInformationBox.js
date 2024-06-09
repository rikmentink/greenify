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

    static properties = {
        id: {
            type: Number,
            reflect: true
        }
    }

    constructor() {
        super();
        this.id = 1;
        this.interventionData = [{}]
    }

    async connectedCallback() {
        super.connectedCallback();
        this.interventionData = await getInterventionById(this.id);
        console.log(this.interventionData)
        this.loading = false;
    }

    renderInterventionInformation() {
        console.log(this.interventionData)
        return html`
            <div class="description-box">
                <p>${this.interventionData.description}</p>
            </div>
            <div class="information-box">
                <h3>Algemene informatie</h3>
                <p><span>Huidige fase: </span> ${this.interventionData.currentPhase}</p>
            </div>
        `;

    }

    render() {
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