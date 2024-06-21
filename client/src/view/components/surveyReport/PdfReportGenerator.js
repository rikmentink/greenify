import { LitElement, html, css } from 'lit';
import { Task } from '@lit/task';
import { render } from 'lit-html';
import html2pdf from 'html2pdf.js';
import globalStyles from "../../../assets/global-styles.js";
import {PdfReportTemplate} from "./PdfReportTemplate.js";
import {getCategoryScores, getSubfactorScoresOfCategory} from "../../../services/SurveyReportService.js";

export class PdfReportGenerator extends LitElement {

    static styles = [
        globalStyles,
        css`
          :host {
            display: block;
          }

          button {
            background-color: #59a3d8;
            color: white;
            padding: 8px 35px 8px 35px;
            border-radius: 25px;
            font-size: 13px;
            text-decoration: none;
            border: none;
            cursor: pointer;
          }
          
          button:hover {
            background-color: #2a7ba6;
          }

          .icon {
            position: relative;
            height: 1em;
            margin-right: 5px;
            top: 0.5px; // Vertical alignement, making it appear more centered
          }
        `,
    ];

    _fetchData = new Task(this, {
        task: async () => {
            const categoryScores = await getCategoryScores(1); // TODO: Replace PhaseID with actual ID
            const subfactorScores = [];

            for (let categoryScore of categoryScores.categoryScores) {
                try {
                    const subfactorScore = await getSubfactorScoresOfCategory(1, categoryScore.categoryName); // TODO: Replace PhaseID with actual ID
                    subfactorScores.push({
                        categoryName: categoryScore.categoryName,
                        subfactorScores: subfactorScore.subfactorScores.sort((a, b) => a.percentage - b.percentage)
                    });
                } catch (error) {
                    console.error(`Failed to fetch subfactor scores for category ${categoryScore.categoryName}:`, error);
                }
            }

            // Add polar chart data
            const polarChartData = categoryScores.categoryScores.map(score => score.percentage);
            const polarChartLabels = categoryScores.categoryScores.map(score => score.categoryName);

            return {
                categoryScores: categoryScores.categoryScores,
                subfactorScores: subfactorScores,
                polarChartData: polarChartData,
                polarChartLabels: polarChartLabels
            };
        },
        args: () => []
    });


    /**
     * Renders the template for generating a PDF report.
     * @returns {Promise<string>} The rendered report template.
     */
    async _renderTemplate() {
        const reportTemplate = new PdfReportTemplate();
        reportTemplate.data = this._fetchData.value;
        return await reportTemplate.render();
    }

    async _downloadReport() {
        const pdfContainer = document.createElement('div');
        const template = await this._renderTemplate();
        render(template, pdfContainer);

        const options = {
            margin: 10,
            filename: 'report.pdf',
            image: { type: 'jpeg', quality: 0.98 },
            html2canvas: { scale: 2 },
            jsPDF: { unit: 'mm', format: 'a4', orientation: 'portrait' },
            pagebreak: {
                avoid: ['tr', 'td', 'h1', 'h2', 'h3', 'h4'],
                before: ['.section', '.col full']
            },
        };

        html2pdf().set(options).from(pdfContainer).save();
    }

    render() {
        return html`
            <button @click=${this._downloadReport}>
                <img src="/icons/download-alt.svg" class="icon">
                Download PDF rapport
            </button>
        `;
    }
}

window.customElements.define("gi-pdf-report-generator", PdfReportGenerator)