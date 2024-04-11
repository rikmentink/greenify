import { LitElement, html, css } from 'lit';
import { router } from '../../router/router.js';

class NavbarItem extends LitElement {
    static properties = {
        url: { type: String },
        label: { type: String },
    };

    static styles = css`
    :host {
      position: relative;
      margin-bottom: 1rem;
    }

    a {
      padding: .25rem;
      color: #fff;
      font-size: 18px;
      font-weight: bold;
      text-decoration: none;
    }

    a:before {
      content: "";
      width: 0;
      height: 2px;
      position: absolute;
      left: 50%;
      bottom: -6px;
      transform: translateX(-50%);
      background-color: #fff;
      transition: width .2s ease;
    }

    a.current:before,
    a:hover:before {
      width: 100%;
    }

    @media (min-width: 768px) {
      :host {
        margin-left: 1rem;
        margin-bottom: 0;
      }
    }
  `;


    connectedCallback() {
        super.connectedCallback();
        window.addEventListener('vaadin-router-location-changed', this.handleRouteChange.bind(this));
    }

    disconnectedCallback() {
        super.disconnectedCallback();
        window.removeEventListener('vaadin-router-location-changed', this.handleRouteChange.bind(this));
    }

    handleRouteChange() {
        this.requestUpdate();
    }

    isCurrentPath() {
        return this.url === router.location.getUrl();
    }

    render() {
        return html`
      <a .href=${import.meta.env.BASE_URL + this.url} class=${this.isCurrentPath() ? 'current' : ''}>${this.label}</a>
    `;
    }
}

customElements.define('gi-navbar-item', NavbarItem);