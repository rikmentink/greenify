import {css, html, LitElement} from "lit";

export class BubbleBox extends LitElement {
    static styles = css` 
      .flex-col-container {
        display: flex;
        flex-direction: column;
        align-items: start;
        max-width: 587px;
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
        background-color: green;
        border-radius: 35px;
        } 
      
      .contents {
        justify-content: center;
        align-items: start;
        padding: 9px 8px;
        margin-top: -10px;
        margin-left: 4px;
        width: 100%;
        font-size: 1em;
        color: black;
        border: solid;
        border-color: lightgreen;
        border-radius: 35px;
        border-width: 0.8px;
      }
    `;

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