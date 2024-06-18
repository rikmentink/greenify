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
                    display: grid;
                    grid-template-columns: repeat(12, 1fr);
                    gap: 1rem;
                    margin-bottom: 1rem;
                }

                .col {
                    display: block;
                    grid-column: span 6;
                }

                .col.full {
                    grid-column: span 12;
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
            `,
        ];
    }

    /**
     * Renders the formatted date.
     *
     * @private
     * @param {Date} date - The date to be formatted.
     * @returns {string} The formatted date in the format "DD-MM-YYYY".
     */
    _renderDate(date) {
        return `${date.getDate()}-${date.getMonth() + 1}-${date.getFullYear()}`;
    }

    /**
     * Formats a monetary amount.
     *
     * @private
     * @param {number} amount - The amount to be formatted.
     * @returns {string} The formatted monetary amount.
     */
    _formatMonetary(amount) {
        return `€ ${amount.toFixed(2).replace('.', ',').replace(',00', ',-')}`;
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
            <h1>Management report</h1>
            <p>Period ${this._renderDate(this.period.startDate)} - ${this._renderDate(this.period.endDate)}</p>
            <div class="section">
                <div class="col">
                    <h2>Commuting</h2>
                    <h3>CO2 Emissions</h3>
                    <div class="datalist">
                        <dl>
                            <dt>Total CO2 emissions</dt>
                            <dd>${this.data.emissions.commute.total} kg</dd>
                        </dl>
                        <dl>
                            <dt>Average per kilometer</dt>
                            <dd>${this.data.emissions.commute.average_per_km} kg</dd>
                        </dl>
                    </div>
                    <h3>Travel Costs</h3>
                    <div class="datalist">
                        <dl>
                            <dt>Total travel costs</dt>
                            <dd>${this._formatMonetary(this.data.costs.commute.total)}</dd>
                        </dl>
                        <dl>
                            <dt>Average per trip</dt>
                            <dd>${this._formatMonetary(this.data.costs.commute.average_per_trip)}</dd>
                        </dl>
                    </div>
                </div>

                <div class="col">
                    <h2>Business Trips</h2>
                    <h3>CO2 Emissions</h3>
                    <div class="datalist">
                        <dl>
                            <dt>Total CO2 emissions</dt>
                            <dd>${this.data.emissions.trips.total} kg</dd>
                        </dl>
                        <dl>
                            <dt>Average per kilometer</dt>
                            <dd>${this.data.emissions.trips.average_per_km} kg</dd>
                        </dl>
                    </div>
                    <h3>Travel Costs</h3>
                    <div class="datalist">
                        <dl class="datalist">
                            <dt>Total travel costs</dt>
                            <dd>${this._formatMonetary(this.data.costs.trips.total)}</dd>
                        </dl>
                        <dl>
                            <dt>Average per trip</dt>
                            <dd>${this._formatMonetary(this.data.costs.trips.average_per_trip)}</dd>
                        </dl>
                    </div>
                </div>
            </div>
            <div class="section">    
                <div class="col full">
                    <h2>Travel Options</h2>
                    <table>
                        <thead>
                            <tr>
                                <th>Vehicle</th>
                                <th>Average emissions</th>
                                <th>Average costs</th>
                            </tr>
                        </thead>
                        <tbody>
                            ${this.data.travel_options.map(vehicle => html`
                                <tr>
                                    <td>${vehicle.name}</td>
                                    <td>${vehicle.average_emissions_per_km} g/km</td>
                                    <td>${vehicle.average_cost_per_km}</td>
                                </tr>
                            `)}
                        </tbody>
                    </table>
                </div>
            </div>
            <style>
                ${this._getStyles()}
            </style>
        `;
    }
}

window.customElements.define("gi-pdf-report-template", PdfReportTemplate)

