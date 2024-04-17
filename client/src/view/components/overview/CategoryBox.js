import { html, css, LitElement } from 'lit';

export class CategoryBox extends LitElement {
    static styles = css`
      .rectangle {
        min-width: 200px;
        min-height: 100px;
        width: 600px;
        height: 200px;
        border: #868686 2px solid;
        margin-top: 10px;
      }
    `;

    static properties = {
    };

    constructor() {
        super();
    }

    render() {
        return html`
            <div class="rectangle">
                <slot></slot>
            </div>
        `;
    }
}

customElements.define('greenify-categorybox', CategoryBox);
