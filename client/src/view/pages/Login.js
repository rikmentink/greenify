import { LitElement, html, css } from 'lit';

import { login } from '../../services/LoginService.js';

export class Login extends LitElement {
    static styles = [css`

        h1 {
            color: #4CBB17;
        }

        *:focus {
            outline: none;
        }

        .login-container {
            display: flex;
            justify-content: center;
            width: 100%;
        }

        .login-block {
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

        button, a {
            padding: 12px 30px;
            margin: 8px 0;
            font-size: 16px;
            font-weight: bold;
            border: none;
            border-radius: 40px;
            cursor: pointer;
            text-decoration: none;
        }

        .register-btn {
            background-color: white;
            color: #4CBB17;
            border: #4CBB17 2px solid;
        }

        .login-btn {
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
            justify-content: space-between;
        }

        .icon-greenify {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }

        .icon-greenify img {
            width: 125px;
            height: 125px;
        }

        @media (min-width: 992px) {
            .login-form {
                width: 300px;
                height: 200px;
                padding: 0 50px 50px;
            }

            .login-form {
                align-content: center;
            }

        }

    `];

    constructor() {
        super();
    }

    // Handle form submission
    async submitForm(event) {
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);
        const email = formData.get('email');
        const password = formData.get('password');
        try {
            await login(email, password)
            // window.location.href = import.meta.env.BASE_URL + 'dashboard';
        } catch (error) {
            console.error('Failed to login:', error);
        }
    }

    // Add event listener to the form
    firstUpdated() {
        this.shadowRoot.querySelector('.login-form').addEventListener('submit', this.submitForm);
    }

    // Render the component
    render() {
        return html`
            <h1>Login</h1>
            <div class="login-container">
                <div class="login-block">
                    <div class="icon-greenify">
                        <img src="icons/greenifylogo.png" alt="Greenify logo">
                    </div>
                    <form class="login-form">
                        <input class="emailField" type="email" id="email" name="email" placeholder="Email" required>
                        <input class="passwordField" type="password" id="password" name="password" placeholder="Wachtwoord" required>
                        <div class="btn-container">
                            <a class="register-btn" .href=${import.meta.env.BASE_URL + 'register'}>Aanmelden</a>
                            <button class="login-btn" type="submit">Inloggen</button>
                        </div>
                    </form>
                </div>
            </div>
        `;
    }
}

window.customElements.define('gi-login', Login);
