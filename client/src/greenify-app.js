import { LitElement, css, html } from 'lit';
import { Router } from '@vaadin/router';

// Components
import { Header } from './view/components/header/Header';
import { ProfilePanel } from './view/components/profile/ProfilePanel';

// Views
import { Home } from './view/pages/Home';

/**
 * The complete app, don't remove this!
 */
export class GreenifyApp extends LitElement {
  static styles = [
    css`
      h1 {
        color: green;
      }
    `];

  constructor() {
    super()
  }

  render() {
    return html`
      <gi-header></gi-header>
      <profile-panel></profile-panel>

      <div id="main-content" class="container">
        <main id="outlet"/>
      </div>
    `
  }

  firstUpdated() {
    const BASE_URL = import.meta.env.BASE_URL;
    const router = new Router(this.shadowRoot.getElementById('outlet'), {
      baseUrl: BASE_URL
    });
    router.setRoutes([
      { path: `${BASE_URL}`, component: 'gi-home' },
      { path: `${BASE_URL}/login`, component: 'gi-login' },
      // TODO: Include all URLs
    ])
  }
}

window.customElements.define('greenify-app', GreenifyApp);
