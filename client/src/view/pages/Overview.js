import { LitElement, html, css } from 'lit';

export class Overview extends LitElement {
    static styles = [css`
        h1 {
            color: Green;
        }
    `];

    static properties = {
        // Define properties here
    };

    constructor() {
        super();
    }

    // Render the component
    render() {
        return html`
            <h1>Vragenlijst Naam</h1>
            <p>Vragenlijst description</p>
            <h1>Categorieen</h1>
        `;
    }
}

window.customElements.define('gi-overview', Overview);