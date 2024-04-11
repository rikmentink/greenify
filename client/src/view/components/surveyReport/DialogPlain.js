import { LitElement, html, css } from 'lit';

export default class DialogPlain extends LitElement {
    static styles = css`
      dialog {
        position: fixed;
        border-radius: 30px;
        padding: 20px;
        z-index: 1000;
        background-color: white;
        color: black;
        border: none;
      }
      
      dialog::backdrop {
        background-color: rgba(0, 0, 0, 0.7);
      }
      
      #close-button {
        position: absolute;
        right: 10px;
        top: -30px;
        background: none;
        border: none;
        font-size: 2em;
        cursor: pointer;
        color: grey;
      }
    `;

    open() {
        this.shadowRoot.querySelector('dialog').showModal();
    }

    close() {
        this.shadowRoot.querySelector('dialog').close();
    }

    render() {
        return html`
            <dialog>
                <p id="close-button" @click="${this.close}">X</p>
                <slot></slot>
            </dialog>
        `;
    }
}

customElements.define('dialog-plain', DialogPlain);