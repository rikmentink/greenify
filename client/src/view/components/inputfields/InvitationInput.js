import {css, html, LitElement} from "lit";

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

    constructor() {
        super();
    }

    handleNewInvite(event) {
        event.preventDefault();

        const emailInput = this.shadowRoot.querySelector('#email-input');
        const email = emailInput.value;

        if (email === "" || !email.includes('@')) {
            this.handleException("Please enter a valid email address.");
            return;
        }

        console.log("Email:", email);
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
