import {css, html, LitElement} from "lit";

export class ContentBoxPlain extends LitElement {
    static styles = [
        css`
          .container {
            background-color: #fff;
            border-radius: 25px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
          }
    `];

    constructor() {
        super();
    }

    render() {
        return html`
            <div class="container">
                <slot></slot>
            </div>
    `
    }
}

window.customElements.define('content-box-plain', ContentBoxPlain);