import { LitElement, html, css } from 'lit';

export class CreatePhase extends LitElement {
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
            <h1>CreatePhase</h1>
            <p>CreatePhase description</p>
            <h1>Categorieen</h1>
        `;
    }
}