import { LitElement, html, css } from 'lit';
import {changeRoleToManager, changeRoleToParticipant, getCurrentUser} from "../../../services/AccountService.js";
import globalStyles from "../../../assets/global-styles.js";

export class ProfileFooter extends LitElement {
    static styles = [globalStyles, css`
        .footer {
            margin: 20px 0 10px 0;
            display: flex;
            flex-direction: column;
        }

        .logout-button, .role-button {
            font-size: 14px;
            color: var(--color-primary);
            font-weight: bold;
            border-radius: 25px;
            cursor: pointer;
            padding: 8px 20px 8px 20px;
            background-color: white;
            text-decoration: none;
            margin: 5px;
        }

        .logout-button:hover, .role-button:hover{
            color: gray;
        }
        
  `];

    constructor() {
        super();
        this.userData = {};
        this.fetchCurrentUser();
    }

    async fetchCurrentUser() {
        this.userData = await getCurrentUser();
        this.requestUpdate()
    }

    async handleParticipateButtonClick() {
        await changeRoleToParticipant();
        window.location.href = '/home';
    }

    async handleManageButtonClick() {
        await changeRoleToManager();
        window.location.href = '/home';
    }

    handleLogoutButtonClick() {
        this.dispatchEvent(new CustomEvent('logout-button-clicked', { bubbles: true, composed: true }));
        localStorage.clear();
    }

    renderButtons() {
        const userRoles = this.userData.authorities.map(role => role.authority);
        if (userRoles.includes("ROLE_MANAGER")) {
            return html`<a class="role-button" @click=${this.handleParticipateButtonClick}>Deelnemen aan interventie</a>`;
        }
        return html`<a class="role-button" @click=${this.handleManageButtonClick}>Interventie beheren</a>`;
    }

    render() {
        return html`
      <div class="footer">
          ${this.renderButtons()}
          <a .href=${import.meta.env.BASE_URL + 'login'} class="logout-button" @click=${this.handleLogoutButtonClick} tabindex="0">Uitloggen</a>
      </div>
    `;
    }
}

customElements.define('profile-footer', ProfileFooter);
