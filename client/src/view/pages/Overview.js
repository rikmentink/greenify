import {css, html, LitElement} from 'lit';
import '../components/overview/CategoryBox.js';

export class Overview extends LitElement {
    static styles = [css`
      p, h1, h3, em {
        color: Black;
      }

      .grid-container {
        display: grid;
        grid-template-columns: auto auto;
      }

      .grid-container div {
        padding: 5px;
      }

      .fase, .voortgang {
        margin-top: 0;
        margin-left: 10px; 
      }

      .content {
        display: grid;
        grid-template-columns: auto auto 1fr;
        align-items: start;
        gap: 20px;
      }

      .description {
        max-width: 600px;
      }

      .title-desc {
        min-width: 43vw;
      }

      .algemeneinfo {
        margin-top: 30px;
      }

      hr.divider {
        margin-top: 25px;
        border-color: #d4d4d4;
        margin-left: 20px;
        min-height: 150px;
        flex-grow: 1;
        width: 0px;
      }
    `];

    static properties = {}

    constructor() {
        super();
    }

    render() {
        return html`
            <div class="content">
                <div class="title-desc">
                    <h1>Vragenlijst Naam</h1>
                    <p class="description">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod
                        tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur
                        adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                </div>
                <hr class="divider">
                <div>
                    <h3 class="algemeneinfo">Algemene Informatie</h3>
                    <div class="grid-container">
                        <em>Fase</em>
                        <p class="fase">fase</p>
                        <em>Voortgang</em>
                        <p class="voortgang">beantwoorden vragen</p>
                    </div>
                </div>
            </div>
            <h1>Categorieën</h1>

            <greenify-categorybox></greenify-categorybox>
            <greenify-categorybox></greenify-categorybox>

        `;
    }
}
window.customElements.define('gi-overview', Overview);