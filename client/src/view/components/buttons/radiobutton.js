import { LitElement, html, css } from 'lit';

export class RadioButton extends LitElement {
    static styles = css`
      .radio-button {
        display: flex;
        flex-direction: column;
        align-items: center;
        margin-left: 10px;
        color: #000000;
      }
    `;

    static properties = {
        label: { type: String },
        name: { type: String },
        value: { type: String },
        checked: { type: Boolean }
    };

    constructor() {
        super();
        this.label = '';
        this.name = '';
        this.value = '';
        this.checked = false;
    }

    toggleChecked() {
        this.checked = !this.checked;
        this.dispatchEvent(new CustomEvent('change', { detail: { checked: this.checked } }));
    }

    render() {
        return html`
            <div class="radio-button">
                <label>${this.label}</label>
                <input
                        type="radio"
                        name="${this.name}"
                        value="${this.value}"
                        .checked="${this.checked}"
                        @click="${this.toggleChecked}"
                >
                <span class="checkmark"></span>
            </div>
        `;
    }
}

customElements.define('radio-button', RadioButton);
