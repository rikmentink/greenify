import {css, html, LitElement} from "lit";
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
        this.id = 0;
        this.interventionData = [{
            id: 1,
            name: "Interventie 1",
            description: "Dit is de beschrijving van de interventie. Kan de gebruiker uiteraard ook zelf instellen. Hoort beknopt te zijn en heeft als doel om duidelijk te maken waar de interventie om draait.",
            phase: "Implementatie"
        }]
    }

    renderInterventionInformation() {
        return html`
            <div class="description-box">
                <p>${this.interventionData[0].description}</p>
            </div>
            <div class="information-box">
                <h3>Algemene informatie</h3>
                <p><span>Huidige fase: </span> ${this.interventionData[0].phase}</p>
            </div>
        `;

    }

    render() {
        return html`
            <h1>${this.interventionData[0].name}</h1>
            <div class="information-container">
                ${this.renderInterventionInformation()}
            </div>
        `;
    }
}

customElements.define('intervention-information-box', InterventionInformationBox);