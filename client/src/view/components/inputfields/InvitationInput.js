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

        .invite-btn:focus, input:focus{
            outline: none;
        }
    `;

    constructor() {
        super();
    }

    render() {
        return html`
            <form>
                <input type="text" placeholder="Uitnodigen via e-mail">
                <button type="submit" class="invite-btn">Uitnodigen</input>
            </form>
        `;
    }
}

customElements.define('invitation-input', InvitationInput);