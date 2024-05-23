import { css, html, LitElement } from 'lit';

export class CategoryBoxCutoff extends LitElement {
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

customElements.define('gi-categoryboxcutoff', CategoryBoxCutoff);
