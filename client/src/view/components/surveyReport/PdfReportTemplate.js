import { LitElement, html, css, render } from 'lit';
import "./charts/AgreementPolarChart.js";
import "./ContentBoxPlain.js";
import globalStyles from "../../../assets/global-styles.js";
import html2canvas from "html2canvas";

export class PdfReportTemplate extends LitElement {
    static properties = {
        data: { type: Object }
    }

    _getStyles() {
        return [
            globalStyles,
            css`
                :host {
                    display: block;
                }

                .section {
                  display: block;
                  margin-bottom: 1rem;
                }

                .col {
                    display: block;
                }

                .col.full {
                    width: 100%;
                }

                .col > h2 {
                    padding-bottom: .5rem;
                    margin-bottom: .5rem;
                    border-bottom: 1px solid #ddd;
                }

                .col > h3 {
                    margin-bottom: .5rem;
                }

                .datalist > dl {
                    width: 100%;
                    display: grid;
                    grid-template-columns: 1fr 1fr;
                    gap: 0;
                    margin: 0;
                }

                .datalist > dl > dt,
                .datalist > dl > dd {
                    margin: 0;
                    padding: .25rem .5rem;
                }

                .datalist > dl:nth-child(odd) > * {
                    background-color: #f6f6f6; 
                }

                table {
                    display: table;
                    width: 100%;
                }

                table > thead {
                    display: table-header-group;
                    vertical-align: middle;
                    border-radius: .75rem .75rem 0 0;
                    background-color: var(--kpn-green);;
                }

                table > tbody {
                    display: table-row-group;
                    vertical-align: middle;
                }

                table tr {
                    display: table-row;
                }

                table > tbody > tr:nth-child(odd) {
                    background-color: #f4f4f4;
                }

                table th {
                    display: table-cell;
                    text-align: left;
                    color: #fff;
                    font-weight: bold;
                    padding: .5rem;
                }

                table td {
                    display: table-cell;
                    padding: .5rem;
                }

                h1 {
                    margin-bottom: .5rem;
                }

                p {
                    margin: 0;
                }
              
              h3 {
                color: var(--color-primary);
              }
              
              img {
                width: 100%;
              } 
              
              .subfactorName {
                font-weight: 500;
                font-size: 0.8rem;
              }
              
              .reportInfo {
                color: dimgrey;
                font-size: 0.9rem;
              }
            `,
        ];
    }

    /**
     * Returns the render root element for the component.
     * This method is overridden to ensure that the component is not rendered in the shadow DOM,
     * as the PDF generator does not support rendering in the shadow DOM.
     *
     * @returns {HTMLElement} The render root element.
     */
    createRenderRoot() {
        return this;
    }

    async convertChartToImage() {
        // Create a new chart element and append it to the body
        const chartElement = document.createElement('div');
        render(this.renderChart(), chartElement);
        document.body.appendChild(chartElement);

        // Timeout to ensure the chart is rendered TODO: Find a better way to do this
        await new Promise(resolve => setTimeout(resolve, 1000));

        const canvas = await html2canvas(chartElement);
        const dataUrl = canvas.toDataURL();
        const img = new Image();
        img.src = dataUrl;

        // Remove element again after conversion
        document.body.removeChild(chartElement);

        return img;
    }

    renderChart() {
        return html`
            <agreement-polar-chart .chartData=${this.data.polarChartData} .chartLabels=${this.data.polarChartLabels}></agreement-polar-chart>
        `
    }

    async render() {
        const chartImg = await this.convertChartToImage();
        return html`
            <h1>Vergroenings rapportage</h1>
            <h3 class="reportInfo">Interventie naam:</h3>
            <p>${this.data.interventionName}</p>
            <h3 class="reportInfo">Fase naam:</h3>
            <p>${this.data.phaseName}</p>
            <h3 class="reportInfo">Rapportage aangemaakt op:</h3>
            <p>${this.data.reportCreationDate}</p>

            <h2>Instemmingspercentage grafiek</h2>
            ${chartImg}
            
            ${this.data.categoryScores.map((categoryScore, index) => html`
                <div class="section">
                    <h2>${categoryScore.categoryName}</h2>
                    <div class="col full">
                        <h3>Instemmingspercentage over gehele categorie</h3>
                        <div class="datalist">
                            <dl>
                                <dt>Maximaal te behalen score</dt>
                                <dd>${categoryScore.maxPossibleScore}</dd>
                                <dt>Totale score</dt>
                                <dd>${categoryScore.totalScore}</dd>
                                <dt>Percentage</dt>
                                <dd>${categoryScore.percentage}%</dd>
                            </dl>
                        </div>
                    </div>

                    <div class="col full">
                        <h3>Instemmingspercentage per vraag</h3>
                        ${this.data.subfactorScores[index].subfactorScores.map(subfactorScore => html`
                            <p class="subfactorName">${subfactorScore.subfactorName}</p>
                            <div class="datalist">
                                <dl>
                                    <dt>Instemmingspercentage</dt>
                                    <dd>${subfactorScore.percentage}%</dd>
                                </dl>
                            </div>
                            <br>
                        `)}
                    </div>
                </div>
            `)}
            <style>
                ${this._getStyles()}
            </style>
        `;
    }
}

window.customElements.define("gi-pdf-report-template", PdfReportTemplate)

