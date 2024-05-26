import { LitElement, css, html } from 'lit';
import { Router } from '@vaadin/router';

// Components
import { Header } from './view/components/header/Header';
import { ProfilePanel } from './view/components/profile/ProfilePanel';

// Views
import { Home } from './view/pages/Home';
import { Survey } from './view/pages/Survey';

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
      { path: '', component: 'gi-home' },
      { path: '/login', component: 'gi-login' },
      { path: '/login/option', component: 'gi-loginoption' },
      { path: '/register', component: 'gi-register' },
      { path: '/intervention/:id', component: 'gi-intervention' },
      { path: '/intervention/:id/new-phase', component: 'gi-createphase' },
      { path: '/phase/:id', component: 'gi-overview' },
      { path: '/phase/:id/report', component: 'gi-survey-result-report' },
      { path: '/survey/:id', component: 'gi-survey' },
    ])
  }
}

window.customElements.define('greenify-app', GreenifyApp);
