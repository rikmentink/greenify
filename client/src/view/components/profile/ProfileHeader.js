import { LitElement, html, css } from 'lit';

export class ProfileHeader extends LitElement {
    static styles = css`
        .header {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
            font-weight: bold;
            font-size: 16px;
        }
  `;

    render() {
        return html`
      <div class="header">
        <div tabindex="0">Profiel</div>
      </div>
      <hr/>
    `;
    }
}

customElements.define('profile-header', ProfileHeader);
