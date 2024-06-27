import {css, html, LitElement} from "lit";
import Chart from "chart.js/auto";

export class HorizontalBarChart extends LitElement {
    static styles = [
        css`
            :host {
                display: grid;
                place-items: center;
                width: 100%;
                height: 100%;
            }
    `];

    static properties = {
        chartDescription: {type: String},
        chartColors: {type: Array},
        chartData: {type: Array},
        chartLabel: {type: Array},
        chartDatasetLabel: {type: String}
    };

    constructor() {
        super();
        this.chartData = [];
        this.chartLabels = [];
        this.chartDatasetLabel = '';
        this.chartColors = [];
    }

    firstUpdated(_changedProperties) {
        this.updateChart();
    }

    updated(changedProperties) {
        if (changedProperties.has('chartData') || changedProperties.has('chartLabels') || changedProperties.has('chartDatasetLabel')) {
            this.updateChart();
        }
    }

    updateChart() {
        const ctx = this.shadowRoot.getElementById('horizontalBarChart');

        if (this.chart) {
            this.chart.destroy(); // Destroy chart if it already exists, required for updating the chart
        }

        const displayLabels = !!this.chartDatasetLabel; // If there is a label, display it
        const roundedChartData = this.chartData.map(value => Math.round(value)); // Round the data to whole numbers

        this.chart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: this.chartLabels,
                datasets: [{
                    label: this.chartDatasetLabel,
                    data: roundedChartData,
                    backgroundColor: this.chartColors,
                }]
            },
            options: {
                plugins: {
                    legend: {
                        display: false
                    }
                },
                responsive: true,
                maintainAspectRatio: false,
                indexAxis: 'y',
                scales: {
                    x: {
                        min: 0,
                        max: 100,
                        ticks: {
                            stepSize: 10
                        }
                    },
                    y: {
                        display: displayLabels
                    }
                }
            }
        });
    }

    render() {
        return html`
            <canvas id="horizontalBarChart"></canvas>
        `
    }
}

window.customElements.define('horizontal-bar-chart', HorizontalBarChart);