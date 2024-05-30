import { LitElement, html, css } from 'lit';
import { getCurrentUser } from "../../../services/AccountService.js";

export class ProfileUserInfo extends LitElement {
    static styles = css`
    .user-info {
      text-align: left;
      margin-bottom: 10px;
    }

    .user-name {
      font-weight: bold;
    }

    .user-email {
      font-size: 0.8em;
    }

    hr {
      margin: 10px 0;
    }
  `;

    static properties = {
        userInfo: { type: Object }
    };

    constructor() {
        super();
        this.userInfo = {};
        this.fetchCurrentUser();
    }

    async fetchCurrentUser() {
        this.userInfo = await getCurrentUser();
    }

    async connectedCallback() {
        super.connectedCallback();
        window.addEventListener('logout-button-clicked', this.handleUserLoggedOut.bind(this));
    }

    disconnectedCallback() {
        super.connectedCallback();
        window.removeEventListener('logout-button-clicked', this.handleUserLoggedOut.bind(this));
    }

    handleUserLoggedOut() {
        this.userInfo = {};
    }

    render() {
        return html`
      <div class="user-info">
        <div class="user-name">${this.userInfo.person ? `${this.userInfo.person.firstName} ${this.userInfo.person.lastName}` : ''}</div>
        <div class="user-email">${this.userInfo.email}</div>
      </div>
      <hr/>
    `;
    }
}

customElements.define('profile-user-info', ProfileUserInfo);
