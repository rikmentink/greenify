import {css, html, LitElement} from "lit";
import {getPersonByEmail} from "../../../services/PersonService.js";
import {getSurvey} from "../../../services/SurveyService.js";
import {Task} from "@lit/task";

export class InvitationInput extends LitElement {
    static styles = css`
      form {
        display: flex;
        align-items: center;
      }

      input {
        flex: 1;
        padding: 5px 5px 5px 20px;
        border: #CACACA 1px solid;
        margin-right: 5px;
      }

      .invite-btn {
        padding: 5px 35px;
        background-color: #4CBB17;
        color: #fff;
        border: none;
        cursor: pointer;
      }

      .invite-btn:focus, input:focus {
        outline: none;
      }
    `;

    static properties = {
        email: { type: String },
        person: { type: Object }
    }

    constructor() {
        super();
        this.email = '';
        this.person = {};
    }

    handleNewInvite(event) {
        event.preventDefault();

        const emailInput = this.shadowRoot.querySelector('#email-input');
        const email = emailInput.value;
        this.email = email;

        if (email === "" || !email.includes('@')) {
            this.handleException("Please enter a valid email address.");
            return;
        }

        this.data = new Task(this, {
            task: async ([email]) => getPersonByEmail(email),
            args: () => [this.email],
            onComplete: (result) => {
                this.person = result;
                this.dispatchEvent(new CustomEvent('person-fetched', {
                    detail: { person: this.person },
                    bubbles: true,
                    composed: true
                }));
            },
            onError: (error) => {
                this.handleException("Failed to fetch person data.");
                console.error("Error fetching person data:", error);
            }
        });
    }

    handleException(message) {
        alert(message);
    }

    render() {
        return html`
            <form>
                <input id="email-input" type="text" placeholder="Uitnodigen via e-mail">
                <button @click="${this.handleNewInvite}" class="invite-btn">Uitnodigen</button>
            </form>
        `;
    }
}

customElements.define('invitation-input', InvitationInput);
