import {css, html, LitElement} from 'lit';
import '../components/overview/CategoryBox.js';

export class Overview extends LitElement {
    static styles = [css`
      p, h1, h3, em {
        color: black;
      }

      .grid-container {
        display: grid;
        grid-template-columns: auto auto;
      }

      .grid-container div {
        padding: 5px;
      }

      .fase, .voortgang {
        margin-top: 0;
        margin-left: 10px;
      }

      .content {
        display: grid;
        grid-template-columns: auto auto 1fr;
        align-items: start;
        gap: 20px;
      }

      .description {
        max-width: 600px;
      }

      .title-desc {
        min-width: 43vw;
      }

      .algemeneinfo {
        margin-top: 30px;
      }

      hr.divider {
        margin-top: 25px;
        border-color: #f8f8f8;
        margin-left: 20px;
        min-height: 150px;
        flex-grow: 1;
        width: 0px;
      }
    `];

    static properties = {
        survey: {type: Object},
    };

    constructor() {
        super();
        this.id = 1;
        this.categoryId = 1;
        this.name = "Category 1";
        this.description =;
        this.questions = [];
        this.data = new Task(this, {
            task: async ([id, page, pageSize]) => getSurvey(id, categoryId, name, description, questions),
            args: () => [this.id, this.categoryId, this.name, this.description, this.questions]
        })

    }

    connectedCallback() {
        super.connectedCallback();
    }

    disconnectedCallback() {
        super.disconnectedCallback();
    }


    render() {
        return html`
            <div class="content">
                <div class="title-desc">
                    <h1>${this.survey.name}</h1>
                    <p class="description">${this.survey.description}</p>
                </div>
                <hr class="divider">
                <div>
                    <h3 class="algemeneinfo">Algemene Informatie</h3>
                    <div class="grid-container">
                        <em>Fase</em>
                        <p class="fase">${this.survey.phase}</p>
                        <em>Voortgang</em>
                        <p class="voortgang">beantwoorden vragen</p>
                    </div>
                </div>
            </div>
            <h1>Categorieën</h1>

            ${this.survey.categories.map(category => html`
                <greenify-categorybox .category=${category}></greenify-categorybox>`)}
        `;
    }
}

customElements.define('gi-overview', Overview);
