import {css, html, LitElement} from "lit";
import {getPersonByEmail} from "../../../services/PersonService.js";
import globalStyles from "../../../assets/global-styles.js";

export class InvitationInput extends LitElement {
    static styles = [globalStyles, css`
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
        background-color: var(--color-primary);
        color: #fff;
        border: none;
        cursor: pointer;
      }

      .invite-btn:focus, input:focus {
        outline: none;
      }
    `];

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
        const email = emailInput.value.trim();
        this.email = email;

        if (email === "" || !email.includes('@')) {
            this.handleException("Please enter a valid email address.");
            return;
        }
        this.fetchPerson(email);
    }

    async fetchPerson(email) {
        try {
            this.person = await getPersonByEmail(email);
            console.log(this.person);

            this.dispatchEvent(new CustomEvent('person-fetched', {
                detail: { person: this.person },
                bubbles: false,
                composed: true
            }
            ));
        } catch (error) {
            this.handleException("Person bestaat niet. Als het ingevulde email correct is, check jouw inbox voor een uitnodiging.");
        }
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