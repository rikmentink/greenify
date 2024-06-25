import { LitElement, html, css } from 'lit';

import './NavbarItem.js';
import '../profile/ProfileUserInfo.js';
import '../profile/ProfileContainer.js';
import {getCurrentUser} from "../../../services/AccountService.js";
import globalStyles from "../../../assets/global-styles.js";

class Navbar extends LitElement {
    static properties = {
        menuOpen: { type: Boolean, reflect: true },
    };

    static styles = [globalStyles, css`
    :host {
      flex: 1 1 auto;
      z-index: 100;
    }

    .offcanvas {
      width: 90%;
      max-width: 400px;
      position: absolute;
      top: 0;
      bottom: 0;
      left: -100%;
      background-color: var(--color-primary);
      color: #fff;
      transition: left .3s ease;
    }

    .offcanvas.show {
      left: 0;
    }

    .backdrop {
      visibility: hidden;
      position: absolute;
      left: 0;
      top: 0;
      width: 100%;
      height: 100%;
      background-color: rgba(0, 0, 0, .5);
      transition: opacity .5s ease;
    }

    .backdrop.show {
      visibility: visible;
    }

    nav {
      display: flex;
      flex-direction: column;
      justify-content: center;
      padding: 1.5rem;
    }

    .header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding-bottom: 1rem;
      border-bottom: 1px solid #fff;
      margin-bottom: 1rem;
    }

    h2 {
      margin: 0;
    }

    .close {
      padding: .5rem;
      color: #fff;
      background-color: transparent;
      border: none;
      cursor: pointer;
    }
    
    @media (min-width: 768px) {
      .offcanvas {
        width: 100%;
        max-width: none;
        position: relative;
        left: 0;
      }

      nav {
        flex-direction: row;
        padding: 0;
      }

      .header {
        display: none;
      }
    }
  `];

    constructor() {
        super();
        this.menuOpen = false;
        this.roles = [];
        this.fetchCurrentUserRoles();
    }

    async fetchCurrentUserRoles() {
        try {
            const userData = await getCurrentUser();
            this.roles = userData.authorities.map(auth => auth.authority);
            this.requestUpdate();
        } catch (error) {
            console.error('Error fetching user roles:', error);
        }
    }

    renderNavbarItems() {
        if (Array.isArray(this.roles) && (this.roles.includes('ROLE_USER') || this.roles.includes('ROLE_ADMIN'))) {
            return html`
            <gi-navbar-item url="" label="Home"></gi-navbar-item>
        `;
        }
        return '';
    }

    connectedCallback() {
        super.connectedCallback();
        window.addEventListener('offcanvas-toggle', this.toggleMenu.bind(this));
        window.addEventListener('vaadin-router-location-changed', this.closeMenu.bind(this));
    }

    disconnectedCallback() {
        super.connectedCallback();
        window.removeEventListener('offcanvas-toggle', this.toggleMenu.bind(this));
        window.removeEventListener('vaadin-router-location-changed', this.closeMenu.bind(this));
    }

    toggleMenu() {
        this.menuOpen = !this.menuOpen;
        this.render();
    }

    closeMenu() {
        this.menuOpen = false;
        this.render();
    }

    render() {
        return html`
      <div class="backdrop ${this.menuOpen ? 'show' : ''}"></div>
      <div class="offcanvas ${this.menuOpen ? 'show' : ''}">
        <nav>
            <div class="header">
                <h2 class="title">Menu</h2>
                <button class="close" @click=${this.toggleMenu}>
                    <img src="icons/close.png" width="20" height="20"/>
                </button>
            </div>
            ${this.renderNavbarItems()}
            <profile-container></profile-container>
        </nav>
      </div>
    `;
    }
}
customElements.define('gi-navbar', Navbar);
