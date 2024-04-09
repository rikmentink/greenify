import { LitElement, html, css } from 'lit';

export class Register extends LitElement {
    static styles = [css`
        h1 {
            color: black;
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
            <h1>Register</h1>
        `;
    }
}

window.customElements.define('gi-register', Register);