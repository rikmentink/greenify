import { LitElement, html, css } from 'lit';
import global from '../../../assets/global-styles';

import './Navbar.js';
import './ProfileButton.js';
import '../profile/ProfilePanel.js';
import {getCurrentUser} from "../../../services/AccountService.js";

export class Header extends LitElement {
    static styles = [ 
        global,
        css`
            .nav-head {
                margin-right: 2rem;
                font-size: 1.5rem;
                font-weight: 700;
                max-width: 600px;
                word-wrap: break-word;
            }
          
            .nav-head-sub {
                font-size: 1rem;
                font-weight: 400;
                color: white;
            }

            .nav-head a {
                color: #fff;
                text-decoration: none;
            }

            header {
                flex: 0 0 auto;
                background-color: var(--color-primary);
                z-index: 100;
            }

            .header__content {
                display: flex;
                align-items: center;
            }

            .toggler {
                display: flex;
                padding: .25rem 0;
                color: #fff;
                background-color: transparent;
                border: none;
                cursor: pointer;
            }

            .toggler > img {
                width: 36px;
                height: 36px;
            }

            @media (min-width: 768px) {
                .toggler {
                    display: none;
                }
            }
    `
    ];

    constructor() {
        super();
        this.rendered = false;
        this.roles = [];
        this.userData = [];
        this.fetchCurrentUserRoles();
        this.handleLocationChange = this.handleLocationChange.bind(this);
    }

    async fetchCurrentUserRoles() {
        try {
            this.userData = await getCurrentUser();
            this.roles = this.userData.authorities.map(auth => auth.authority);
            this.requestUpdate();
        } catch (error) {
            console.error('Error fetching user roles:', error);
        }
    }

    async connectedCallback() {
        super.connectedCallback();
        this.rendered = true;
        await this.fetchCurrentUserRoles();
        window.addEventListener('vaadin-router-location-changed', this.handleLocationChange);
        this.requestUpdate();
    }

    disconnectedCallback() {
        super.disconnectedCallback();
        window.removeEventListener('vaadin-router-location-changed', this.handleLocationChange);
    }

    handleLocationChange(){
        // Need to be fixed later
        window.location.reload();
    }

    renderHeader() {
        if (Array.isArray(this.roles) && (this.roles.includes('ROLE_USER') || this.roles.includes('ROLE_ADMIN'))) {
            return html`
                <button class="toggler" @click=${this.handleOffcanvasToggle}>
                    <img src="/icons/menu.png" width="20" height="20"/>
                </button>
                <h1 class="nav-head">
                    <a href="/">GreenIT<br>
                    <h2 class="nav-head-sub">Een praktische tool voor succesvolle implementatie van groene interventies in zorgomgevingen</h2></a>
                </h1>
                <gi-navbar>
                </gi-navbar>
                <profile-button @click=${this.handleProfileButtonClick} .userData="${this.userData}"></profile-button>
        `;
        }
        return html`<h1 class="nav-head">
            <a href="${window.url}">GreenIT<br>
                <h2 class="nav-head-sub">Een praktische tool voor succesvolle implementatie van groene interventies in zorgomgevingen</h2></a>
        </h1>`;
    }


    handleOffcanvasToggle() {
        this.dispatchEvent(
            new CustomEvent('offcanvas-toggle', {
                bubbles: true,
                composed: true,
                detail: {
                    target: this,
                },
            })
        );
    }


    firstUpdated() {
        this.profilePanel = this.shadowRoot.querySelector('profile-panel');
    }

    handleProfileButtonClick() {
        const profilePanel = this.profilePanel || this.shadowRoot.querySelector('profile-panel');
        if (profilePanel) {
            profilePanel.isOpen = !profilePanel.isOpen;
            profilePanel.requestUpdate('isOpen');
        }
    }

    render() {
        if (!this.rendered) {
            return html``;
        }

        return html`
            <header>
                <div class="container">
                    <div class="header__content">
                        ${this.renderHeader()}
                    </div>
                </div>
            </header>
            <profile-panel></profile-panel>
    `;
    }

}

window.customElements.define("gi-header", Header)