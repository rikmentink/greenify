import { LitElement, html, css } from 'lit';
import '../components/buttons/radiobutton.js';

export class Vragenlijst extends LitElement {
    static styles = css`
      h1, p {
        color: black;
      }

      .back-button {
        color: #4CBB17;
      }

      .question-container {
        display: flex;
        align-items: center;
        position: relative;
      }

      .radio-container {
        display: flex;
        flex-direction: row;
        align-items: center;
        margin-left: 10px;
      }

      .priority-container {
        display: flex;
        flex-direction: row;
        align-items: center;
        margin-left: 10px;
      }

      .radio-button {
        display: flex;
        flex-direction: column;
        align-items: center;
        margin-left: 10px;
      }

      hr.divider {
        border-top: 3px solid #000000;
        margin-left: 20px;
        min-height: 50px;
        flex-grow: 1;
        width: 0px;
      }
    `;

    static properties = {};

    constructor() {
        super();
    }

    handleRadioChange(event) {
        const radioButton = event.target;
        const allRadios = this.shadowRoot.querySelectorAll(`radio-button[name="${radioButton.name}"]`);

        allRadios.forEach(radio => {
            if (radio !== radioButton) {
                radio.checked = false;
            }
        });
    }

    render() {
        return html`
            <p><a class="back-button" href="/overzicht">← Terug naar overzicht</a></p>
            <h1 class="domain-name">Domein 1 - De groene interventie</h1>
            <div class="question-container">
                <p class="question">1. Deze vragenlijst gaat over de groene interventie.</p>
                <div class="radio-container">
                    <radio-button label="1" name="question1" value="1" @change="${this.handleRadioChange}"></radio-button>
                    <radio-button label="2" name="question1" value="2" @change="${this.handleRadioChange}"></radio-button>
                    <radio-button label="3" name="question1" value="3" @change="${this.handleRadioChange}"></radio-button>
                    <radio-button label="4" name="question1" value="4" @change="${this.handleRadioChange}"></radio-button>
                    <radio-button label="5" name="question1" value="5" @change="${this.handleRadioChange}"></radio-button>
                </div>

                <hr class="divider">

                <div class="priority-container">
                    <radio-button label="1" name="priority1" value="1" @change="${this.handleRadioChange}"></radio-button>
                    <radio-button label="2" name="priority1" value="2" @change="${this.handleRadioChange}"></radio-button>
                    <radio-button label="3" name="priority1" value="3" @change="${this.handleRadioChange}"></radio-button>
                    <radio-button label="4" name="priority1" value="4" @change="${this.handleRadioChange}"></radio-button>
                </div>
            </div>

            <div class="question-container">
                <p class="question">2. Dit is een tweede test vraag.</p>
                <div class="radio-container">
                    <radio-button label="1" name="question2" value="1" @change="${this.handleRadioChange}"></radio-button>
                    <radio-button label="2" name="question2" value="2" @change="${this.handleRadioChange}"></radio-button>
                    <radio-button label="3" name="question2" value="3" @change="${this.handleRadioChange}"></radio-button>
                    <radio-button label="4" name="question2" value="4" @change="${this.handleRadioChange}"></radio-button>
                    <radio-button label="5" name="question2" value="5" @change="${this.handleRadioChange}"></radio-button>
                </div>

                <hr class="divider">

                <div class="priority-container">
                    <radio-button label="1" name="priority2" value="1" @change="${this.handleRadioChange}"></radio-button>
                    <radio-button label="2" name="priority2" value="2" @change="${this.handleRadioChange}"></radio-button>
                    <radio-button label="3" name="priority2" value="3" @change="${this.handleRadioChange}"></radio-button>
                    <radio-button label="4" name="priority2" value="4" @change="${this.handleRadioChange}"></radio-button>
                </div>
            </div>
        `;
    }
}

window.customElements.define('gi-vragenlijst', Vragenlijst);
