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

    firstUpdated() {
        const ctx = this.shadowRoot.getElementById('horizontalBarChart');
        const displayLabels = !!this.chartDatasetLabel; // If there is a label, display it
        const chart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: this.chartLabels,
                datasets: [{
                    label: this.chartDatasetLabel,
                    data: this.chartData,
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