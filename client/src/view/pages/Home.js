import {css, html, LitElement} from "lit";
import {MyInterventionsBox} from "../components/home/MyInterventionsBox.js";
import {CreateInterventionsBox} from "../components/home/CreateInterventionBox.js";
import {getCurrentPerson} from "../../services/PersonService.js";
import {changeRoleToManager, changeRoleToParticipant, getCurrentUser} from "../../services/AccountService.js";
import globalStyles from "../../assets/global-styles.js";

export class Home extends LitElement {
    static styles = [globalStyles, css`
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
            border-bottom: var(--color-primary) 1px solid;
        }

        .home-container {
            display: flex; /* Use flexbox */
        }
        
        my-intervention-box, create-intervention-box {
            flex: 1; /* Make both boxes take equal space */
            margin-right: 10px; /* Add some spacing between the boxes */
        }
        
        .user-info {
            display: flex;
            align-items: flex-end;
            justify-content: space-between;
        }
        
        .new-intervention-btn {
            display: none;
        }


        @media (max-width: 767px) {
            .home-header {
                width: 100%;
            }
            .home-container {
                flex-direction: column;
            }
            
            .btn {
                text-align: center;
                font-size: 13px;
                margin-right: 10px;
            }
            
            .home-header h2 {
                font-size: 1.5em;
            }

            .new-intervention-btn {
                display: block;
            }

            my-intervention-box {
                width: 100%;
            }

            create-intervention-box{
                margin-right: 0;
                width: 100%;
            }
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
            <create-intervention-box personId="${this.userData.person.id}"></create-intervention-box>
        `;
        }
        return html``;
    }

    async handleParticipateButtonClick() {
        await changeRoleToParticipant();
        window.location.href = '/home';
    }

    async handleManageButtonClick() {
        await changeRoleToManager();
        window.location.href = '/home';
    }

    renderButtons() {
        const userRoles = this.userData.authorities.map(role => role.authority);
        if (userRoles.includes("ROLE_MANAGER")) {
            return html`<a class="btn new-intervention-btn" href="/intervention/${this.personData.id}/new-intervention">Interventie aanmaken</a>
            <a class="btn" @click=${this.handleParticipateButtonClick}>Mijn interventie deelnames</a>`;
        }
        return html`<a class="btn" @click=${this.handleManageButtonClick}>Interventie aanmaken? Word beheerder!</a>`;
    }

    render() {
        return html`
            <div class="home-header">
                <h1>Welkom</h1>
                <div class="user-info">
                    <h2>${this.homePageUserInfo()}</h2>
                    ${this.renderButtons()}
                </div>
            </div>
            <div class="home-container">
                <my-intervention-box .userId=${this.userData.person.id}></my-intervention-box>
                ${this.renderCreateInterventionBox()}
            </div>
        `;
    }
}

window.customElements.define('gi-home', Home);
