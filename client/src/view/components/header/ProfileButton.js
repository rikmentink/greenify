import { LitElement, html, css } from 'lit';

export class ProfileButton extends LitElement {
    static styles = css`
        img {
            width: 24px;
            height: 24px;
            margin-right: 20px;
        }

        @media (max-width: 767px) {
            button {
                display: none; /* Hide the button on screens smaller than 768px wide */
            }
        }

        button {
            background-color: transparent;
            border: none;
            cursor: pointer;
        }
    `;

    handleButtonClick() {
        this.dispatchEvent(new CustomEvent('profile-button-clicked', { bubbles: true, composed: true }));
    }

    render() {
        return html`
            <button @click=${this.handleButtonClick} tabindex="0">
                <img src="/icons/profile.png" alt="Profile button icon" width="24" height="24" />
            </button>
    `;
    }
}

customElements.define('profile-button', ProfileButton);
