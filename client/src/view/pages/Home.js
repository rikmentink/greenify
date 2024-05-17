import {css, html, LitElement} from "lit";
import {MyInterventionsBox} from "../components/home/MyInterventionsBox.js";

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
    ;
    `];

    constructor() {
        super();
        this.userData = {
            id: 1,
            name: "John Doe",
            email: "john@gmail.com",
        }
    }

    connectedCallback() {
        super.connectedCallback();
    }

    render() {
        return html`
            <div class="home-header">
                <h1>Welkom</h1>
                <h2>${this.userData.name}</h2>
            </div>
            <my-intervention-box .userId=${this.userData.id}></my-intervention-box>
        `;
    }
}

window.customElements.define('gi-home', Home);
