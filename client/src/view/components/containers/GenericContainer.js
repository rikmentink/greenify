import { LitElement, html, css } from 'lit';

export class GenericContainer extends LitElement {
    static styles = [css`
      :host {
        display: block;
        width: 100%;
      }

      .main-block { 
        box-shadow: rgba(0, 0, 0, 0.1) 0 4px 8px; 
        padding: 15px; 
        background-color: white; 
        border-radius: 30px; display: flex; 
        flex-direction: column; 
        align-items: center; 
        width: 100%; 
      }

      .section {
        margin-bottom: 20px;
      }

      .title {
        color: green;
      }

      .description {
        color: black;
        margin-top: 5px;
      }
    `];

    static properties = {
        sections: { type: Array }
    };

    render() {
        return html`
            <div class="outer-container">
                <div class="outer-block">
                ${this.sections.map(section => html`
                    <div class="main-block section">
                        <h1 class="title">${section.title}</h1>
                        <p class="description">${section.description}</p>
                    </div>
                `)}
                </div>
            </div>
        `;
    }
}

customElements.define('generic-container', GenericContainer);
