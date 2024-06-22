import {css, html, LitElement} from "lit";
import "../components/surveyReport/charts/AgreementPolarChart.js";
import "../components/surveyReport/ContentBoxPlain.js";
import "../components/surveyReport/HeaderBox.js";
import "../components/surveyReport/BubbleBox.js";
import "../components/surveyReport/DialogPlain.js";
import "../components/surveyReport/charts/HorizontalBarChart.js";
import { getCategoryScores, getSubfactorScoresOfCategory } from "../../services/SurveyReportService.js";
import globalStyles from "../../assets/global-styles.js";
import "../components/surveyReport/PdfReportGenerator.js";
import {Task} from "@lit/task";
import {getRouter} from "../../router.js";

export class SurveyResultReport extends LitElement {
  static styles = [globalStyles,
    css`
      :host {
          background-color: #f0f0f0;
      }
      h1 {
        color: var(--color-primary);
      }
      h2 {
        font-size: 15px;
        margin: 0;
        color: white;
      }
      .grid-container {
        display: grid;
        gap: 20px;
        margin: 20px;
        grid-template-columns: repeat(2, 1fr);
      }
      
      .grid-left-section {
        display: grid;
      }
      
      .grid-right-section {
        display: grid;
      }
      
      .header-box-contents {
        margin: 4px;
        display: grid;
        gap: 4px;
        overflow: auto;
        max-height: 700px;
      }
      
      .bubble-header {
        font-size: 15px;
        margin: 2px;
      }
      
      .bubble-contents {
        font-size: 12px;
      }
      
      .list-item {
        font-size: 12px;
        padding-top: 10px;
        padding-bottom: 10px;
      }
      
      .list {
        list-style: none;
        padding-left: 0;
        overflow: auto;
        max-height: 500px;
      }
      
      .list-item-chart {
        min-width: 500px;
        height: 50px;
        padding-right: 8px;
        padding-top: 10px;
      }
      
      #dialog-title {
        color: grey;
      }
      
      .dialog-subtitle {
        color: darkgrey;
        font-size: 20px;
      }
      
      hr {
        width: 95%;
        border: 0;
        border-top: 1px solid #f1f1f1;
        margin: 0;
      }

      ::-webkit-scrollbar {
        width: 5px;
      }

      ::-webkit-scrollbar-track {
        background: #f1f1f1;
      }

      ::-webkit-scrollbar-thumb {
        background: #888;
      }

      ::-webkit-scrollbar-thumb:hover {
        background: #555;
      }
    `];

  static get properties() {
    return {
      phaseId : { type: Number },
      polarChartData: { type: Array },
      polarChartLabels: { type: Array },
      barChartData: { type: Array },
      actionPointData: { type: Array }
    };
  }

  constructor() {
    super();
    this.phaseId = 0;
    this.polarChartData = [];
    this.polarChartLabels = [];
    this.barChartData = [];
    this.actionPointData = [
      {title: "Actiepunt 1", description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam auctor, orci nec lacinia."},
      {title: "Actiepunt 2", description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam auctor, orci nec lacinia."},
      {title: "Actiepunt 3", description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam auctor, orci nec lacinia."},
      {title: "Actiepunt 4", description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam auctor, orci nec lacinia."},
      {title: "Actiepunt 5", description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam auctor, orci nec lacinia."},
      {title: "Actiepunt 6", description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam auctor, orci nec lacinia."},
      {title: "Actiepunt 7", description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam auctor, orci nec lacinia."},
      {title: "Actiepunt 8", description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam auctor, orci nec lacinia."},
      {title: "Actiepunt 9", description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam auctor, orci nec lacinia."},
      {title: "Actiepunt 10", description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam auctor, orci nec lacinia."}
    ];
  }

  async connectedCallback() {
    super.connectedCallback();

    // Get the phaseId from the URL and redirect to the home page if the phaseId is not found
    this.phaseId = getRouter().location.params.id || 0;
    if (this.phaseId == 0) {
        window.location.href = '/';
    }

    await this._fetchData.run();
  }

  firstUpdated() {
    const element = this.shadowRoot.querySelector(".header-box-contents");
    element.scrollIntoView();
  }

  // Fetch method to replace the way data is collected for the page
  _fetchData = new Task(this, {
    task: async () => {
      const categoryScores = await getCategoryScores(this.phaseId);
      this.polarChartData = categoryScores.map(score => score.percentage);
      this.polarChartLabels = categoryScores.map(score => score.categoryName);
    },
    args: () => [this.phaseId]
  });

  _fetchSubfactorScores = new Task(this, {
    // Deconstruct the categoryName array to just get the name
    task: async ([categoryName]) => {
      return await getSubfactorScoresOfCategory(this.phaseId, categoryName);
    },
    args: () => [this.currentCategoryName]
  });

  async openDialog(event) {
    const categoryName = event.detail;
    this.currentCategoryName = categoryName;
    this.shadowRoot.querySelector('#dialog-title').textContent = categoryName;

    // Fetch the subfactor scores for the selected category
    let subfactorScores;
    try {
      await this._fetchSubfactorScores.run();
      subfactorScores = this._fetchSubfactorScores.value;
    } catch (error) {
      console.error(`Failed to fetch subfactor scores for category ${categoryName}:`, error);
      return;
    }

    // Sort the subfactor scores based on the percentage from lowest to highest
    subfactorScores.sort((a, b) => a.percentage - b.percentage);

    this.barChartData = subfactorScores.map(score => ({
      description: score.subfactorName,
      chartData: [score.averageScore],
      chartLabels: ["Percentage"],
      chartColors: ["purple"]
    }));

    this.shadowRoot.querySelector('dialog-plain').open();
  }

  render() {
    return html`
      <h1>Vergroenings rapportage</h1>
      <div class="btn-group">
        <gi-pdf-report-generator slot="actions"></gi-pdf-report-generator>
      </div>
      <div class="grid-container">
        <div class="grid-left-section">
          <content-box-plain class="content-box-chart">
            <agreement-polar-chart .chartData=${this.polarChartData} .chartLabels=${this.polarChartLabels} @chart-click="${this.openDialog}"></agreement-polar-chart>
          </content-box-plain>
        </div>
        
        <div class="grid-right-section">
          <header-box>
            <h2 slot="header">Actiepunten</h2>
            <div class="header-box-contents">
              ${this.renderActionPoints(this.actionPointData)}
            </div>
          </header-box>
        </div>
      </div>

      <dialog-plain>
        <h1 id="dialog-title"></h1>
        <h2 class="dialog-subtitle">Groei uitdagingen</h2>
        <ul class="list">
          ${this.renderListItems(this.barChartData)}
        </ul>
      </dialog-plain>
    `
  }

  renderActionPoints(data) {
    return data.map(item => html`
    <bubble-box>
      <p class="bubble-header" slot="header">${item.title}</p>
      <p class="bubble-contents" slot="contents">${item.description}</p>
    </bubble-box>
  `);
  }

  renderListItems(data) {
    return data.map(item => html`
    <hr>
    <li class="list-item">
      <div class="list-item-description">
        ${item.description}
      </div>
      <div class="list-item-chart">
        <horizontal-bar-chart .chartData=${item.chartData} .chartLabels=${item.chartLabels} .chartColors=${item.chartColors}></horizontal-bar-chart>
      </div>
    </li>
  `);
  }
}

window.customElements.define('gi-survey-result-report', SurveyResultReport);