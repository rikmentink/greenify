import { LitElement, html, css } from 'lit';
import './Navbar.js';
import './ProfileButton.js';
import '../profile/ProfilePanel.js';

export class Header extends LitElement {
    static styles = [ css`
        :host {
            background-color: #4CBB17;
            z-index: 100;
        }

        .nav-head {
            margin: 0 40px 0 30px;
            font-size: 1.3rem;
            font-weight: 700;
            max-width: 600px;
            word-wrap: break-word;
        }
        
        .nav-head a {
            color: #fff;
            text-decoration: none;
        }

        header {
            flex: 0 0 auto;
            padding: 2rem 0;
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
    }

    async connectedCallback() {
        super.connectedCallback();
        this.rendered = true;
        this.requestUpdate();
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
    handleProfileButtonClick() {
        const profilePanel = document.querySelector('profile-panel');
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
                        <button class="toggler" @click=${this.handleOffcanvasToggle}>
                            <img src="icons/menu.png" width="20" height="20"/>
                        </button>
                        <h1 class="nav-head"><a href="/">GreenIT: Een praktische tool voor succesvolle implementatie van groene interventies in zorgomgevingen</a></h1>
                        <gi-navbar>
                        </gi-navbar>
                        <profile-button @click=${this.handleProfileButtonClick}></profile-button>
                    </div>
                </div>
            </header>
    `;
    }

}

window.customElements.define("gi-header", Header)