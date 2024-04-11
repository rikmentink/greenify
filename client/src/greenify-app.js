import { LitElement, css, html } from 'lit';

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
      <div id="outlet"/>
    `
  }
}

window.customElements.define('greenify-app', GreenifyApp);
