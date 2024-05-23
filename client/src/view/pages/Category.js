import { css, html, LitElement } from 'lit';

export class Category extends LitElement {
    static styles = [css`
    `];

    static properties = {
        survey: { type: Object },
    };

    constructor() {
        super();
    }

    render() {
        return html`
        `;
    }
}

customElements.define('gi-category', Category);
