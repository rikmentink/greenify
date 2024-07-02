import { LitElement, html, css } from 'lit';

export class ProfileButton extends LitElement {
    static styles = css`
        img {
            width: 24px;
            height: 24px;
            margin-right: 20px;
        }

        p {
            display: inline;
            margin-right: 10px;
            font-size: 16px;
            color: white;
            font-weight: bold;
        }

        @media (max-width: 767px) {
            button, p, img {
                display: none; /* Hide the button on screens smaller than 768px wide */
            }
        }

        button {
            background-color: transparent;
            border: none;
            cursor: pointer;
            display: flex;
            align-items: center;
        }
    `;


    constructor() {
        super();
        this.userData = {};
    }

    static get properties() {
        return {
            userData: { type: Object }
        };
    }

    handleButtonClick() {
        this.dispatchEvent(new CustomEvent('profile-button-clicked', { bubbles: true, composed: true }));
    }

    render() {
        return html`
            <button @click=${this.handleButtonClick} tabindex="0">
                <p>${this.userData.person.firstName} ${this.userData.person.lastName}</p>
                <img src="/icons/profile.png" alt="Profile button icon" width="24" height="24" />
            </button>
    `;
    }
}

customElements.define('profile-button', ProfileButton);
