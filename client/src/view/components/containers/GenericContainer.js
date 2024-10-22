import { LitElement, html, css } from 'lit';
import globalStyles from "../../../assets/global-styles.js";

export class GenericContainer extends LitElement {
    static styles = [globalStyles, css`
        :host {
            display: flex;
            width: 750px;
            height: 500px;
        }

        .outer-container {
            display: flex;
            justify-content: center;
            overflow-y: auto;
        }

        .main-block {
            padding: 10px;
            background-color: white;
            border-radius: 30px; 
            display: flex;
            flex-direction: column;
            align-items: center;
            text-align: center;
        }

        .title {
            color: var(--color-primary);
            margin: 0;
        }

        .description {
            color: black;
            margin-top: 5px;
        }


        .outer-container::-webkit-scrollbar {
            width: 10px;
        }

        .outer-container::-webkit-scrollbar-thumb {
            background: #888;
            border-radius: 10px;
            height: 50px;
        }

        .outer-container::-webkit-scrollbar-thumb:hover {
            background: #555;
        }

        @media (max-width: 600px) {
            :host {
                width: 100%;
                height: 500px;
            }
        }
    `];

    static properties = {
        sections: { type: Array }
    };

    render() {
        return html`
            <div class="outer-container">
                <div class="outer-block">
                ${this.sections.map(section => html`
                    <div class="main-block section">
                        <h1 class="title">${section.title}</h1>
                        <p class="description">${section.description}</p>
                    </div>
                `)}
                </div>
            </div>
        `;
    }
}

customElements.define('generic-container', GenericContainer);
