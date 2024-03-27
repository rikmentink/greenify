import { LitElement, html } from 'lit';

/**
 * The complete app, don't remove this!
 */
export class GreenifyApp extends LitElement {
  static get properties() {
  }

  constructor() {
    super()
  }

  render() {
    return html`
      <h1>Greenify</h1>
    `
  }
}

window.customElements.define('greenify-app', GreenifyApp);
