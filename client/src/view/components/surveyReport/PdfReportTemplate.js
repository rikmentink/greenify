import { LitElement, html, css } from 'lit';
import globalStyles from "../../../assets/global-styles.js";

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

    async render() {
        return html`
            <h1>Survey report</h1>
            <p>Phase: phase_name_1</p>
            ${this.data.categoryScores.map((categoryScore, index) => html`
                <div class="section">
                    <h2>${categoryScore.categoryName}</h2>
                    <div class="col full">
                        <h3>Category Scores</h3>
                        <div class="datalist">
                            <dl>
                                <dt>Max Possible Score</dt>
                                <dd>${categoryScore.maxPossibleScore}</dd>
                                <dt>Total Score</dt>
                                <dd>${categoryScore.totalScore}</dd>
                                <dt>Percentage</dt>
                                <dd>${categoryScore.percentage}%</dd>
                            </dl>
                        </div>
                    </div>

                    <div class="col full">
                        <h3>Subfactor Scores</h3>
                        ${this.data.subfactorScores[index].subfactorScores.map(subfactorScore => html`
                            <h4>${subfactorScore.subfactorName}</h4>
                            <div class="datalist">
                                <dl>
                                    <dt>Agreement percentage</dt>
                                    <dd>${subfactorScore.percentage}%</dd>
                                </dl>
                            </div>
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

