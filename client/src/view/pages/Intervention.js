import {css, html, LitElement} from "lit";
import {InterventionUsersPanel} from "../components/intervention/InterventionUsersPanel.js";

export class Intervention extends LitElement {
    static styles = [css`
    ;
    `];

    constructor() {
        super();

        // This is a dummy value, later need to be changed to interventionId from the URL or service
        this.interventionId = 1;
    }

    connectedCallback() {
        super.connectedCallback();
    }

    render() {
        return html`
            <h1>(Intervention)</h1>
            <intervention-users-panel .id="${this.interventionId}"></intervention-users-panel>
        `;
    }
}

window.customElements.define('gi-intervention', Intervention);
