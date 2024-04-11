import { LitElement, html, css } from 'lit';

export class Register extends LitElement {
    static styles = [css`
        
        h1{
            color: #4CBB17;
        }

        *:focus {
            outline: none;
        }
        
        .register-container {
            display: flex;
            justify-content: center;
            width: 100%;
        }
        
        .register-block {
            box-shadow: rgba(0, 0, 0, 0.1) 0 4px 8px;
            padding: 20px;
            background-color: white;
            border-radius: 30px;
        }
        
        input {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 0;
            height: 40px;
            box-sizing: border-box;
            border-bottom: #4CBB17 2px solid;
        }
        
        button[type=submit] {
            width: 100%;
            background-color: #4CBB17;
            color: white;
            padding: 18px 80px;
            margin: 8px 0;
            font-size: 16px;
            font-weight: bold;
            border: none;
            border-radius: 40px;
            cursor: pointer;
        }
        
        button[type=submit]:hover {
            color: lightgray;
        }
        
        p {
            font-size: 12px;
            color: #4CBB17;
            margin-top: -6px;
        }
        
        input[type=checkbox] {
            width: 15px;
            height: 15px;
            margin-right: 10px;
        }
        
        .privacy-group {
            display: flex;
            grid-column: span 2;
            width: 100%;
            font-size: 10px;
            color: gray;
            align-items: center;
            margin-top: -10px;
        }
        
        .btn-container {
            grid-column: span 2;
            display: grid;
            justify-content: flex-end;
        }

        @media (min-width: 992px) {
            .register-form {
                width: 400px;
                height: 250px;
                padding: 50px;
            }
            
            .register-form {
                display: grid;
                align-content: center;
                grid-gap: 10px;
                grid-template-columns: repeat(2, 1fr); 
                grid-template-rows: auto auto auto; 
            }

            .input-group {
                grid-column: span 2; 
            }
        }

    `];

    static properties = {
        // Define properties here
    };

    constructor() {
        super();
    }

    // Render the component
    render() {
        return html`
            <h1>Aanmelden</h1>
            <div class="register-container">
                <div class="register-block">
                    <form class="register-form">
                        <input class="firstNameField" type="text" id="firstName" name="firstName" placeholder="Voornaam" required>
                        <input class="lastNameField" type="text" id="lastName" name="lastName" placeholder="Achternaam" required>
                        <div class="input-group">
                            <input class="emailField" type="email" id="email" name="email" placeholder="Email" required>
                            <input class="passwordField" type="password" id="password" name="password" placeholder="Wachtwoord" required>
                            <p>Vul een sterk wachtwoord in!</p>
                        </div>
                        <div class="privacy-group">
                            <input class="privacyField" type="checkbox" id="privacy" name="privacy" required><label for="privacy">Ik ga akkoord met de <a href="/privacy">privacy policy</a></label>
                        </div>
                        <div class="btn-container">
                            <button type="submit">Aanmelden</button>
                        </div>
                    </form>
                </div>
            </div>
        `;
    }
}

window.customElements.define('gi-register', Register);
