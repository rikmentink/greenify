import { LitElement, html, css } from 'lit';

export default class HeaderBox extends LitElement {
    static styles = css`
      .container {
        background-color: #fff;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        padding-bottom: 5px;
        text-align: center;
      }

      .header {
        background-color: green;
        color: #fff;
        padding: 5px;
        border-radius: 8px 8px 0 0;
      }
    `;

    render() {
        return html`
            <div class="container">
                <div class="header"><slot name="header"></slot></div>
                <slot></slot>
            </div>
        `;
    }
}

customElements.define('header-box', HeaderBox);