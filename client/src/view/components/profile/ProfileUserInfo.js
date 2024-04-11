import { LitElement, html, css } from 'lit';

export class ProfileUserInfo extends LitElement {
    static styles = css`
    .user-info {
      text-align: center;
      margin-bottom: 10px;
    }

    .user-name {
      font-weight: bold;
    }

    .user-group {
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
      </div>
      <hr/>
    `;
    }
}

customElements.define('profile-user-info', ProfileUserInfo);
