import { css, html, LitElement } from "lit";
import { InvitationInput } from "../inputfields/InvitationInput.js";
import { getPersonById } from "../../../services/PersonService.js";

export class UserActionMenu extends LitElement {
    static styles = css`
        .dots-btn {
            align-self: center;
            background-color: white;
            border: none;
            cursor: pointer;
        }
        
        .dots-btn:after{
            content: '\\2807';
            font-size: 25px;
        }
        
        .usermenu {
            position: absolute;
            background-color: #f9f9f9;
            width: 130px;
            box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
            z-index: 1;
            display: none; /* Initially hide the menu */
        }

        .show-menu {
            display: block; /* Show the menu */
        }
        
        ul {
            list-style-type: none;
            padding: 0;
            margin: 0;
        }
        
        ul button {
            width: 100%;
            height: 50px;
            text-align: center;
            text-decoration: none;
            display: block;
            background-color: white;
            color: black;
            border: none;
            border-bottom: 1px solid #DEDEDE;
            cursor: pointer;
            font-size: 14px;
        }
        
        ul button:hover {
            background-color: #DEDEDE;
        }
    `;

    static properties = {
        userId: {
            type: Number,
            reflect: true
        }
    }

    constructor() {
        super();
        this.userId = 102;
        this.menuVisible = false;
    }

    connectedCallback() {
        super.connectedCallback();
    }

    async fetchUser() {
        try {
            this.user = await getPersonById(this.userId);
            this.dispatchEvent(new CustomEvent('user-deleted', {
                detail: { user: this.user },
                bubbles: true,
                composed: true
            }));

        } catch (error) {
            alert('Er is iets misgegaan bij het ophalen van de gebruiker.')
        }
    }

    handleMenu() {
        this.closeOtherMenus();
        this.menuVisible = !this.menuVisible;
        const userMenu = this.shadowRoot.querySelector('.usermenu');
        if (this.menuVisible) {
            userMenu.classList.add('show-menu');
        } else {
            userMenu.classList.remove('show-menu');
        }
    }

    handleRemoveUser() {

    }

    closeOtherMenus() {
        const menus = document.querySelectorAll('ul');
        menus.forEach(menu => {
        });
    }

    render() {
        return html`
            <button class="dots-btn" @click="${this.handleMenu}"></button>
            <div class="usermenu">
                <ul class="${this.menuVisible ? 'show-menu' : ''}">
                    <li><button style="color: red;" @click="${this.handleRemoveUser}">Verwijderen</button></li>
                </ul>
            </div>
        `;
    }
}

customElements.define('user-action-menu', UserActionMenu);
