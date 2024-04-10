import { LitElement, html, css } from 'lit';

export class ProfileFooter extends LitElement {
    static styles = css`
    .footer {
      margin-top: 10px;
    }

    .logout-button {
        color: #fff;
        border: none;
        cursor: pointer;
        background-color: transparent;
    }
      
    .logout-button:hover {
        color: blue;
    }
        
  `;

    handleLogoutButtonClick() {
        this.dispatchEvent(new CustomEvent('logout-button-clicked', { bubbles: true, composed: true }));
    }

    render() {
        return html`
      <div class="footer">
          <a .href=${import.meta.env.BASE_URL + 'login'} class="logout-button" @click=${this.handleLogoutButtonClick} tabindex="0"></a>          
      </div>
    `;
    }
}

customElements.define('profile-footer', ProfileFooter);
