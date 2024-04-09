import {css, html, LitElement} from "lit";
import "../components/surveyReport/charts/AgreementPolarChart.js";
import "../components/surveyReport/ContentBoxPlain.js";
import "../components/surveyReport/HeaderBox.js";
import "../components/surveyReport/BubbleBox.js";

export class SurveyResultReport extends LitElement {
  static styles = [
    css`
      :host {
          background-color: #f0f0f0;
      }
      h1 {
        color: green;
      }
      h2 {
        font-size: 15px;
        margin: 0px;
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
      }
      
      .bubble-header {
        font-size: 15px;
        margin: 2px;
      }
      
      .bubble-contents {
        font-size: 12px;
      }
    `];

  constructor() {
    super();
    this.chartData = [100, 80, 20, 60, 85, 30];
    this.chartLabels = ['Kenmerken betrokken', 'De groene interventie', 'Ontwerp', 'De externe omgeving', 'De organisatie', 'Proces'];
  }

  render() {
    return html`
      <h1>Vergroenings rapportage</h1>
      <div class="grid-container">
        <div class="grid-left-section">
          <content-box-plain class="content-box-chart">
            <agreement-polar-chart .chartData=${this.chartData} .chartLabels=${this.chartLabels}></agreement-polar-chart>
          </content-box-plain>
        </div>
        
        <div class="grid-right-section">
          <header-box>
            <h2 slot="header">Actiepunten</h2>
            <div class="header-box-contents">
              <bubble-box>
                <p class="bubble-header" slot="header">Actiepunt 1</p>
                <p class="bubble-contents" slot="contents">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam auctor, orci nec lacinia.</p>
              </bubble-box>
              <bubble-box>
                <p class="bubble-header" slot="header">Actiepunt 2</p>
                <p class="bubble-contents" slot="contents">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam auctor, orci nec lacinia.</p>
              </bubble-box>
              <bubble-box>
                <p class="bubble-header" slot="header">Actiepunt 3</p>
                <p class="bubble-contents" slot="contents">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam auctor, orci nec lacinia. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam auctor, orci nec lacinia. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam auctor, orci nec lacinia.</p>
              </bubble-box>
            </div>
          </header-box>
        </div>
      </div>
      
    `
  }
}

window.customElements.define('survey-result-report', SurveyResultReport);