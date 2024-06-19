import {css, html, LitElement} from "lit";
import {UserActionMenu} from "./UserActionMenu.js";

export class InterventionUsersTable extends LitElement {
    static styles = css`
      table {
        border-collapse: collapse;
        width: 100%;
        background-color: #FDFDFD;
      }

      thead, tbody {
        border: 1px solid #DEDEDE;
      }

      thead {
        color: #7A7A7A;
      }

      th, td {
        padding: 10px;
        text-align: left;
      }

      .email {
        text-decoration: underline;
      }

      span {
        padding-left: 10px;
      }

      form {
        display: flex;
        margin-bottom: 10px;
      }

      input {
        flex: 1;
        padding: 5px 5px 5px 30px;
        border: #CACACA 1px solid;
        margin-right: 5px;
        background-color: white;
        background-image: url("/icons/search-icon.png");
        background-size: 13px;
        background-position: 10px 6px;
        background-repeat: no-repeat;
      }

      input:focus {
        outline: none;
      }
    `;

    static properties = {
        interventionId: { type: Number, reflect: true },
        userData: { type: Array }
    };

    constructor() {
        super();
        this.userData = [];
        this.filteredUserData = [];
    }

    updated(changedProperties) {
        if (changedProperties.has('userData')) {
            this.filteredUserData = [...this.userData];
            this.requestUpdate();
        }
    }

    connectedCallback() {
        super.connectedCallback();
    }

    renderUsers() {
        return this.filteredUserData.map(user => {
            return html`
                <tr>
                    <td>${user.firstName}</td>
                    <td><span class="email">${user.email}</span></td>
                    <td><span style="border-left: ${this.statusColor(user.progress)}">${user.progress ? "Volledig ingevuld" : "Niet gestart"}</span></td>
                    <td>
                        <user-action-menu .userId="${user.userId}"></user-action-menu>
                    </td>
                </tr>
            `;
        });
    }

    statusColor(status) {
        return status ? "3px solid green" : "3px solid red";
    }

    searchUser(event) {
        const searchValue = event.target.value.toLowerCase();
        this.filteredUserData = this.userData.filter(user => {
            return user.name.toLowerCase().includes(searchValue) || user.email.toLowerCase().includes(searchValue);
        });
        this.requestUpdate();
    }

    render() {
        return html`
            <form>
                <input class="input-search" placeholder="Zoek op naam of e-mailadres" @input="${this.searchUser}">
            </form>
            <table>
                <thead>
                <tr>
                    <th>NAAM</th>
                    <th>E-MAILADRES</th>
                    <th>VOORUITGANG</th>
                    <th>ACTIES</th>
                </tr>
                </thead>
                <tbody>
                    ${this.renderUsers()}
                </tbody>
            </table>
        `;
    }
}

customElements.define('intervention-users-table', InterventionUsersTable);
