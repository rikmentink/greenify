import {css, html, LitElement} from "lit";
import {InvitationInput} from "../inputfields/InvitationInput.js";
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
            background-image: url("../../../../public/icons/search-icon.png");
            background-size: 13px;
            background-position: 10px 6px;
            background-repeat: no-repeat;
        }
        
        input:focus {
            outline: none;
        }
    `;

    static properties = {
        interventionId: {
            type: Number,
            reflect: true
        }
    }

    constructor() {
        super();
        // Dummy data, later need to be fetched from the service
        this.userData = [
            {
                name: "John Doe",
                email: "john.doe@gmail.com",
                progress: true,
                lastOnline: "2024-5-5",
                userId: 1
            },
            {
                name: "Henk Jan",
                email: "henk.jan@gmail.com",
                progress: false,
                lastOnline: "2024-3-5",
                userId: 2
            }
        ];
        this.filteredUserData = [...this.userData];
    }

    connectedCallback() {
        super.connectedCallback();
        this.addEventListener('person-fetched', this.handlePersonFetched);
    }

    disconnectedCallback() {
        this.removeEventListener('person-fetched', this.handlePersonFetched);
        super.disconnectedCallback();
    }

    handlePersonFetched(event) {
        const person = event.detail.person;
        console.log('Person data received:', person);
        // Here you can add the person to your userData and filteredUserData
        this.userData = [...this.userData, person];
        this.filteredUserData = [...this.userData];
        this.requestUpdate();
    }

    renderUsers() {
        return this.filteredUserData.map(user => {
            return html`
            <tr>
                <td>${user.name}</td>
                <td><span class="email">${user.email}</span></td>
                <td><span style="border-left: ${this.statusColor(user.progress)}">${user.progress ? "Volledig ingevuld" : "Niet gestart"}</span></td>
                <td>${user.lastOnline}</td>
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
                    <th>LAATST ONLINE</th>
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