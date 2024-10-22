import {css, html, LitElement} from "lit";
import globalStyles from "../../../assets/global-styles.js";

export class BubbleBox extends LitElement {
    static styles = [globalStyles, css`
      .flex-col-container {
        display: flex;
        flex-direction: column;
        align-items: start;
        padding-left: 10px;
        padding-right: 10px;
      }

      .header {
        position: relative;
        z-index: 10;
        justify-content: center;
        padding: 3.5px 5px;
        font-size: 1em;
        font-weight: bold;
        text-align: center;
        color: white;
        background-color: var(--color-primary);
        border-radius: 35px;
        margin-left: -8px;
      }

      .contents {
        justify-content: center;
        align-items: start;
        padding: 9px 0;
        margin-top: -10px;
        //margin-left: 20px;
        width: 100%;
        font-size: 1em;
        color: black;
        border: solid;
        border-color: lightgreen;
        border-radius: 35px;
        border-width: 0.8px;
        background-color: #f8fcff;
      }
    `];

    constructor() {
        super();
    }

    render() {
        return html`
            <div class="flex-col-container">
                <div class="header">
                    <slot name="header">Default header</slot>
                </div>
                <div class="contents">
                    <slot name="contents">Default content</slot>
                </div>
            </div>
        `;
    }
}

window.customElements.define('bubble-box', BubbleBox);