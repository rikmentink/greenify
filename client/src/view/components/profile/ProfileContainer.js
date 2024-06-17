import { LitElement, html, css } from 'lit';

import './ProfileHeader.js';
import './ProfileUserInfo.js';
import './ProfileFooter.js';
import globalStyles from "../../../assets/global-styles.js";

export class ProfileContainer extends LitElement {
    static styles = [globalStyles, css`
    .profile-container {
      background-color: var(--color-primary);
      color: #fff;
    }
      
    @media (min-width: 767px) {
        .profile-container {
          display: none; 
        }
    }
  `];


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
