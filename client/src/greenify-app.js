import { LitElement, css, html } from 'lit';
import { InfoPopUp } from "./view/pages/InfoPopUp.js";

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
      <main id="main-content">
        <slot></slot>
      </main>
    `
  }

  createRenderRoot() {
    return this;
  }
}

window.customElements.define('greenify-app', GreenifyApp);
