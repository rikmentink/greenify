import { LitElement, html, css } from 'lit';

import './ProfileHeader.js';
import './ProfileUserInfo.js';
import './ProfileFooter.js';

export class ProfileContainer extends LitElement {
    static styles = css`
    .profile-container {
      background-color: #4CBB17;
      color: #fff;
      padding: 10px;
    }
      
    @media (min-width: 767px) {
        .profile-container {
          display: none; 
        }
    }
  `;


    render() {
        return html`
          <div class="profile-container">
              <hr/>
            <profile-user-info></profile-user-info>
            <profile-footer></profile-footer>
          </div>
        `;
    }
}

customElements.define('profile-container', ProfileContainer);
