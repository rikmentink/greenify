import { LitElement, html, css } from 'lit';
import { GenericContainer } from "../components/containers/GenericContainer.js";

export class InfoPopUp extends LitElement {
    static styles = [css`
        :host {
            display: block;
        }

        .btn {
            color: white;
            background-color: #4CBB17;
            border: 2px solid #4CBB17;
            padding: 10px 20px;
            font-size: 16px;
            font-weight: bolder;
            cursor: pointer;
            border-radius: 5px;
        }
        
        .btn:focus{
            outline: none;
        }

        dialog {
            border: none;
            border-top-left-radius: 20px;
            border-bottom-left-radius: 20px;
            overflow-y: auto;
        }

        dialog::-webkit-scrollbar {
            width: 12px;
        }

        dialog::-webkit-scrollbar-track {
            background: #f1f1f1;
        }

        dialog::-webkit-scrollbar-thumb {
            background: #888;
        }

        dialog::-webkit-scrollbar-thumb:hover {
            background: #555;
        }
        
        @media (max-width: 600px) {
            dialog {
                font-size: 12px;
            }
        }
      
    `];

    constructor() {
        super();
    }

    connectedCallback() {
        super.connectedCallback();
        this.requestUpdate();
        setTimeout(() => {
            this.showDialog();
        }, 0);
    }


    showDialog() {
        const dialog = this.shadowRoot.querySelector('dialog');
        dialog.showModal();
    }

    hideDialog() {
        const dialog = this.shadowRoot.querySelector('dialog');
        dialog.close();
    }

    render() {
        return html`
            <dialog>
                <button class="btn btn-primary" @click="${this.hideDialog}">Sluit</button>
                <generic-container .sections=${this.sections}></generic-container>
            </dialog>
        `;
    }

    get sections() {
        return [
            {
                title: "Het doel van de tool",
                description: "Deze tool is bedoeld om betrokkenen rondom een groene interventie in een zorginstelling te helpen bij het goed implementeren van de groene interventie in de zorginstelling."
            },
            {
                title: "Wat bedoelen we met implementatie:",
                description: "Met implementatie bedoelen we “een procesmatige en planmatige invoering van vernieuwingen en/of verbeteringen met als doel dat deze een structurele plaats krijgen in het (beroepsmatig) handelen, in het functioneren van organisatie(s) of in de structuur van de gezondheidszorg” (Grol and Wensing). In het geval van groene interventies gaat het dus om het ervoor zorgen dat een groene interventie gebruikt en onderhouden wordt zoals dat bedoeld is door de ontwikkelaars van de interventie."
            },
            {
                title: "Inhoud tool",
                description: "De tool is gebaseerd op een bestaand implementatie framework* en bestaat uit verschillende factoren die verdeeld zijn over zes implementatiedomeinen. De factoren zijn gepresenteerd als stellingen en geven aspecten aan die belangrijk kunnen zijn voor het succesvol implementeren van groene interventies in zorginstellingen."
            },
            {
                title: "Werkwijze tool",
                description: "Als u en andere mensen die betrokken zijn bij de groene interventie de tool invullen kan er inzicht verkregen worden in de mate waarin de groene interventie succesvol geïmplementeerd is/ kan worden en waar eventueel verbeterpunten liggen. Ook wordt inzicht gegeven in de top-10 factoren die het belangrijkst zijn om aan te werken voor een succesvolle implementatie. In de tool kunt u per factor aangeven in hoeverre u het ermee eens bent dat er rekening gehouden is met deze factor bij de implementatie van de groene interventie. Daarnaast wordt per factor gevraagd in hoeverre deze prioriteit heeft om aan te pakken. Per factor kunt u het ook aangeven als de factor volgens u ‘niet van toepassing’ is bij de groene interventie waarbij u betrokken bent. Ook kunt u de optie 'weet ik niet' invullen. Veel succes met het invullen!"
            }
        ];
    }
}

customElements.define('gi-info-popup', InfoPopUp);
