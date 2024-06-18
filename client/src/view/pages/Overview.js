import { css, html, LitElement } from 'lit';
import { getOverview } from '../../services/OverviewService.js';
import { globalStyles } from '../../assets/global-styles.js';

export class Overview extends LitElement {
    static styles = [
      globalStyles,
      css`
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
        id: { type: Number },
    };

    constructor() {
        super();
        this.id = 0;
        this.data = {};
    }

    async connectedCallback() {
      super.connectedCallback();
      this.id = getRouter().location.params.id || 0;
    }

    async authorizeAndRedirect() {
        if (this.id == 0) {
            window.location.href = '/';
            return false;
        }

        const survey = await this._fetchData(this.id);
        if (survey.hasOwnProperty('error')) {
            window.location.href = '/';
            return false;
        }
        
        return true;
    }

    async _fetchData(id) {
        this.data = new Task(this, {
            task: async ([id]) => getOverview(id),
            args: () => [id]
        });
        return await this.data.run();
    }

    render() {
        if (this.data._value.error) {
            return html`
                <h1>Overview</h1>
                <p>An error occured while loading the questions: ${this.data._value.message}</p>`;
        }
        console.log(this.data);
        return html`
          <h1>Overview</h1>
          <p>done.</p>
        `
        // return html`
        //     <div class="content">
        //         <div class="title-desc">
        //             <h1>${this.survey.name || 'Loading...'}</h1>
        //             <p class="description">${this.survey.description || ''}</p>
        //         </div>
        //         <hr class="divider">
        //         <div>
        //             <h3 class="algemeneinfo">Algemene Informatie</h3>
        //             <div class="grid-container">
        //                 <em>Fase</em>
        //                 <p class="fase">${this.survey.phase || ''}</p>
        //                 <em>Voortgang</em>
        //                 <p class="voortgang">beantwoorden vragen</p>
        //             </div>
        //         </div>
        //     </div>
        //     <h1>Categorieën</h1>

        //     ${this.survey.categories ? this.survey.categories.map(category => html`
        //         <greenify-categorybox .category=${category}></greenify-categorybox>`) : html`<p>Loading categories...</p>`}
        // `;
    }
}

customElements.define('gi-overview', Overview);
