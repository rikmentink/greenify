import { LitElement, html, css } from 'lit';

export class LoginOption extends LitElement {
    static styles = [css`

        h1 {
            color: #4CBB17;
        }

        *:focus {
            outline: none;
        }

        .option-container {
            display: flex;
            justify-content: center;
            width: 100%;
        }

        .option-block {
            box-shadow: rgba(0, 0, 0, 0.1) 0 4px 8px;
            padding: 20px;
            background-color: white;
            border-radius: 30px;
        }

        a {
            padding: 12px 30px;
            margin: 8px 0;
            font-size: 16px;
            font-weight: bold;
            width: 80%;
            border: none;
            text-align: center;
            border-radius: 40px;
            cursor: pointer;
            text-decoration: none;
        }

        .manage-btn {
            background-color: white;
            color: #4CBB17;
            border: #4CBB17 2px solid;
        }

        .participate-btn {
            border: #4CBB17 2px solid;
            background-color: #4CBB17;
            color: white;
        }

        button:hover, a:hover {
            color: lightgray;
        }

        p {
            font-size: 12px;
            color: #4CBB17;
            margin-top: -6px;
        }

        .btn-container {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        
    `];


    constructor() {
        super();
    }

    // Render the component
    render() {
        return html`
            <h1>Maak een keuze</h1>
            <div class="option-container">
                <div class="option-block">
                    <div class="btn-container">
                        <a class="participate-btn" .href=${import.meta.env.BASE_URL + ''}>Deelnemen aan interventie</a>
                        <a class="manage-btn" .href=${import.meta.env.BASE_URL + ''}>Interventie beheren</a>
                    </div>
                </div>
            </div>
        `;
    }
}

window.customElements.define('gi-loginoption', LoginOption);
