import { LitElement, html, css } from 'lit';

export class ProfileFooter extends LitElement {
    static styles = css`
        .footer {
            margin: 20px 0 10px 0;
        }

        .logout-button, .role-button {
            font-size: 14px;
            color: #4CBB17;
            font-weight: bold;
            border-radius: 25px;
            cursor: pointer;
            padding: 8px 20px 8px 20px;
            background-color: white;
            text-decoration: none;
        }

        .logout-button:hover, .role-button:hover{
            color: gray;
        }
        
  `;

    handleLogoutButtonClick() {
        this.dispatchEvent(new CustomEvent('logout-button-clicked', { bubbles: true, composed: true }));
        sessionStorage.clear();
    }

    render() {
        return html`
      <div class="footer">
          <a .href=${import.meta.env.BASE_URL + 'login/option'} class="role-button" tabindex="0">Mijn rol</a>
          <a .href=${import.meta.env.BASE_URL + 'login'} class="logout-button" @click=${this.handleLogoutButtonClick} tabindex="0">Uitloggen</a>
      </div>
    `;
    }
}

customElements.define('profile-footer', ProfileFooter);
