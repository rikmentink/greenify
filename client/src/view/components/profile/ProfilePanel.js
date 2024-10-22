import { LitElement, html, css } from 'lit';

import './ProfileHeader.js';
import './ProfileUserInfo.js';
import './ProfileFooter.js';
import globalStyles from "../../../assets/global-styles.js";

export class ProfilePanel extends LitElement {
    static styles = [globalStyles, css`
    .profile-panel {
        position: absolute;
        right: 0;
        width: 210px;
        background-color: var(--color-primary);
        color: #fff;
        padding: 10px;
        box-shadow: 0 10px 10px -10px rgba(0, 0, 0, 0.5), 
        10px 0 10px -10px rgba(0, 0, 0, 0.5), -10px 0 10px -10px rgba(0, 0, 0, 0.5);
        z-index: 10; // Index value to define which element should be on top
    }
      
    @media (max-width: 767px) {
        .profile-panel {
          display: none; /* Hide the panel on screens smaller than 768px wide */
        }
    }
  `];

    static get properties() {
        return {
            isOpen: { type: Boolean }
        };
    }

    constructor() {
        super();
        this.rendered = false;
        this.firstUpdate = true;
    }

    async firstUpdated() {
        if (this.firstUpdate) {
            this.rendered = true;
            this.requestUpdate();
            this.firstUpdate = false;
        }
    }

    render() {
        if (!this.rendered || !this.isOpen) {
            return html``;
        }
        return html`
            <div class="profile-panel">
                <profile-header></profile-header>
                <profile-user-info></profile-user-info>
                <profile-footer></profile-footer>
            </div>
        `;
    }
}

customElements.define('profile-panel', ProfilePanel);
