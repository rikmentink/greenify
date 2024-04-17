import { html, css, LitElement } from 'lit';

export class Button extends LitElement {
    static styles = css`
        
    `;

    static get properties() {
        return {
            buttonType: { type: String },
            name: { type: String },
            acceptLabel: { type: String },
            declineLabel: { type: String },
            redirectUrl: { type: String },
            buttonStyle: { type: String }
        };
    }

    constructor() {
        super();
        this.buttonType = 'button';
        this.name = '';
        this.previousLabel = 'Previous';
        this.nextLabel = 'Next';
        this.redirectUrl = '';
        this.buttonStyle = 'primary';
    }

    renderButton() {
        if (this.buttonType === 'submit') {
            return html`<button type="submit" aria-label="Submit" tabindex="0" @click=${this.submitClickHandler}>${this.name}</button>`;
        } else if (this.buttonType === 'accept') {
            return html`<button type="button" @click="${this.acceptClickHandler}" aria-label="Accept" tabindex="0">${this.acceptLabel}</button>`;
        } else if (this.buttonType === 'next') {
            return html`<button type="button" @click="${this.nextClickHandler}" aria-label="Next" tabindex="0">${this.nextLabel}</button>`;
        } else {
            return html`<button type="previous" @click="${this.previousClickHandler}" aria-label="Previous" tabindex="0">${this.previousLabel}</button>`;
        }
    }

    submitClickHandler() {
        this.dispatchEvent(new CustomEvent('submit-button-click', { bubbles: true, composed: true }));
    }

    nextClickHandler() {
        this.dispatchEvent(new Event('next-button-click', { bubbles: true, composed: true }));
    }

    previousClickHandler() {
        this.dispatchEvent(new Event('previous-button-click', { bubbles: true, composed: true }))
    }

    render() {
        return html`
            ${this.renderButton()}
        `;
    }
}

customElements.define('greenify-button', Button);
