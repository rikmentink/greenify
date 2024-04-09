import {css, html, LitElement} from "lit";
import Chart from "chart.js/auto";

export class AgreementPolarChart extends LitElement {
    static styles = [
        css`
            :host {
                display: grid;
                place-items: center;
                width: 100%;
                aspect-ratio: 1/1;
            }
    `];

    static properties = {
        chartData: {type: Array},
        chartLabels: {type: Array}
    };

    constructor() {
        super();
        this.chartData = [];
        this.chartLabels = [];
    }

    firstUpdated() {
        const ctx = this.shadowRoot.getElementById('polarChart');
        new Chart(ctx, {
            type: 'radar',
            data: {
                labels: this.chartLabels,
                datasets: [{
                    label: 'Instemmingspercentage',
                    data: this.chartData,
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: true,
                scales: {
                    r: {
                        min: 0,
                        max: 100,
                        ticks: {
                            stepSize: 20
                        },
                    }
                },
                layout: {
                    padding: 20,
                },
                plugins: {
                    legend: {
                        display: false
                    },
                    title: {
                        display: true,
                        text: 'Instemmingspercentage',
                        font: {
                            size: 20
                        }
                    }
                }
            }
        });
    }

    render() {
        return html`
            <canvas id="polarChart"></canvas>
        `
    }
}

window.customElements.define('agreement-polar-chart', AgreementPolarChart);