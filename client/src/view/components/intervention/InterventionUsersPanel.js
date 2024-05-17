import {css, html, LitElement} from "lit";
import {InvitationInput} from "../inputfields/InvitationInput.js";
import {InterventionUsersTable} from "./InterventionUsersTable.js";
export class InterventionUsersPanel extends LitElement {
    static styles = css`
        .user-container {
            display: grid;
            grid-template-rows: auto 1fr; 
            gap: 20px; 
            margin-bottom: 40px;
        }
        
        .inventation-box {
            width: 40%;
        }
    `;

    static properties = {
        id: {
            type: Number,
            reflect: true
        }
    }

    constructor() {
        super();
        this.id = 0;
    }

    render() {
        return html`
            <h2>Gebruikers</h2>
            <div class="user-container">
                <div class="inventation-box">
                    <invitation-input></invitation-input>
                </div>
                <div class="users-table">
                    <intervention-users-table .interventionId="${this.id}"></intervention-users-table>
                </div>
            </div>
        `;
    }
}

customElements.define('intervention-users-panel', InterventionUsersPanel);