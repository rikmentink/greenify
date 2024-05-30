import { LitElement, css, html } from 'lit';
import { initRouter } from './router';
import global from './assets/global-styles';

// Components
import { Header } from './view/components/header/Header';
import { ProfilePanel } from "./view/components/profile/ProfilePanel.js";

// Views
import { Home } from './view/pages/Home';
import { Survey } from './view/pages/Survey';
import { Login } from "./view/pages/Login.js";
import { LoginOption } from "./view/pages/LoginOption.js";
import { Register } from "./view/pages/Register.js";
import { Intervention } from "./view/pages/Intervention.js";

/**
 * The complete app, don't remove this!
 */
export class GreenifyApp extends LitElement {
  static styles = [
    global,
    css`
      #outlet {
          flex: 1 1 auto;
      }
    `];

  constructor() {
    super()
  }

  render() {
    return html`
      <gi-header></gi-header>

      <div id="main-content" class="container">
        <main id="outlet"/>
      </div>
    `
  }

  firstUpdated() {
    initRouter(this.shadowRoot.getElementById('outlet'));
  }
}

window.customElements.define('greenify-app', GreenifyApp);
