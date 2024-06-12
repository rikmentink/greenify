import {css, html, LitElement} from "lit";
import {MyInterventionsBox} from "../components/home/MyInterventionsBox.js";
import {CreateInterventionsBox} from "../components/home/CreateInterventionBox.js";
import {getCurrentPerson} from "../../services/PersonService.js";
import {getCurrentUser} from "../../services/AccountService.js";

export class Home extends LitElement {
    static styles = [css`
        .home-header h1, h2{
            font-weight: normal;
            margin: 0;
        }
        
        .home-header h1{
            font-size: 3em;
        }
        
        .home-header h2{
            font-size: 2em;
        }
        
        .home-header {
            width: 50%;
            margin-top: 20px;
            line-height: 1.1;
            padding-bottom: 10px;
            border-bottom: #4CBB17 1px solid;
        }

        .home-container {
            display: flex; /* Use flexbox */
        }
        
        my-intervention-box, create-intervention-box {
            flex: 1; /* Make both boxes take equal space */
            margin-right: 10px; /* Add some spacing between the boxes */
        }
    ;
    `];

    constructor() {
        super();
        this.personData = {};
        this.userData = {};
        this.fetchCurrentUser();
    }

    async fetchCurrentUser() {
        this.personData = await getCurrentPerson();
        this.userData = await getCurrentUser()
        this.requestUpdate()
    }

    homePageUserInfo() {
        if (this.personData.firstName && this.personData.lastName) {
            return `${this.personData.firstName} ${this.personData.lastName}`;
        } else {
            return "Geen gebruiker gevonden...";
        }
    }

    renderCreateInterventionBox() {
        const userRoles = this.userData.authorities.map(role => role.authority);
        if (userRoles.includes("ROLE_MANAGER")) {
            return html`
            <create-intervention-box></create-intervention-box>
        `;
        }
        return html``;
    }


    connectedCallback() {
        super.connectedCallback();
    }

    render() {
        return html`
            <div class="home-header">
                <h1>Welkom</h1>
                <h2>${this.homePageUserInfo()}</h2>
            </div>
            <div class="home-container">
                <my-intervention-box .userId=${this.userData.person.id}></my-intervention-box>
                ${this.renderCreateInterventionBox()}
            </div>
        `;
    }
}

window.customElements.define('gi-home', Home);
