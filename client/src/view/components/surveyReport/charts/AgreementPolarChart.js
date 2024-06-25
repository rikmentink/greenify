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
        this.chartDescription = '';
        this.showDescription = false;
    }

    updated(changedProperties) {
        if (changedProperties.has('chartData') || changedProperties.has('chartLabels')) {
            this.updateChart();
        }
    }

    firstUpdated(_changedProperties) {
        this.updateChart();
    }

    updateChart() {
        const ctx = this.shadowRoot.getElementById('polarChart');

        if (this.chart) {
            this.chart.destroy(); // Destroy chart if it already exists, required for updating the chart
        }

        if (this.chartDescription !== '') {
            this.showDescription = true; // Show description if it is not empty
        }

        this.chart = new Chart(ctx, {
            type: 'radar',
            data: {
                labels: this.chartLabels,
                datasets: [{
                    label: 'Instemmingspercentage',
                    data: this.chartData,
                    borderWidth: 1,
                    pointStyle: 'circle',
                    pointRadius: 5,
                    hitRadius: 50
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
                    subtitle: {
                        display: this.showDescription,
                        text: this.chartDescription,
                        font: {
                            size: 15
                        }
                    },
                    title: {
                        display: true,
                        text: 'Instemmingspercentage',
                        font: {
                            size: 20
                        }
                    }
                },
                onClick: (event, elements) => {
                    if (elements.length) {
                        const label = this.chart.data.labels[elements[0].index];
                        this.dispatchEvent(new CustomEvent('chart-click', { detail: label }));
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