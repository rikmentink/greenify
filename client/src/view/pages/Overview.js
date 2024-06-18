import { css, html, LitElement } from 'lit';
import { getOverview } from '../../services/OverviewService.js';

export class Overview extends LitElement {
    /**
     * TODO: Make dynamic
     * 
     * 1. Retrieve phase ID from URL
     * 2. Fetch data from API asynchronously
     * 3. Render data in the template
     */
    
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
        survey: { type: Object },
    };

    constructor() {
        super();
        this.id = 1;
        this.page = 1;
        this.pageSize = 10;
        this.survey = {};
    }

    async connectedCallback() {
        super.connectedCallback();
        await this.fetchSurveyData();
    }

    async fetchSurveyData() {
        try {
            const urlParams = new URLSearchParams(window.location.search);
            this.id = urlParams.get('id');
            this.survey = await getOverview(this.id, this.page, this.pageSize);
        } catch (error) {
            console.error('Error fetching survey data:', error);
        }
    }

    render() {
        return html`
            <div class="content">
                <div class="title-desc">
                    <h1>${this.survey.name || 'Loading...'}</h1>
                    <p class="description">${this.survey.description || ''}</p>
                </div>
                <hr class="divider">
                <div>
                    <h3 class="algemeneinfo">Algemene Informatie</h3>
                    <div class="grid-container">
                        <em>Fase</em>
                        <p class="fase">${this.survey.phase || ''}</p>
                        <em>Voortgang</em>
                        <p class="voortgang">beantwoorden vragen</p>
                    </div>
                </div>
            </div>
            <h1>Categorieën</h1>

            ${this.survey.categories ? this.survey.categories.map(category => html`
                <greenify-categorybox .category=${category}></greenify-categorybox>`) : html`<p>Loading categories...</p>`}
        `;
    }
}

customElements.define('gi-overview', Overview);
