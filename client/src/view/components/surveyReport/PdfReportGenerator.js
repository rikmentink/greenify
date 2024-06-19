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
        `,
    ];

    _fetchData = new Task(this, {
        task: async () => {
            const categoryScores = await getCategoryScores(1); // TODO: Replace PhaseID with actual ID
            const subfactorScores = [];

            for (let categoryName in categoryScores.categoryScores) {
                try {
                    subfactorScores[categoryName] = await getSubfactorScoresOfCategory(1, categoryName); // TODO: Replace PhaseID with actual ID
                } catch (error) {
                    console.error(`Failed to fetch subfactor scores for category ${categoryName}:`, error);
                }
            }

            return {
                categoryScores: categoryScores.categoryScores,
                subfactorScores: subfactorScores
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
            pagebreak: { avoid: ['tr', 'td'] },
        };

        html2pdf().set(options).from(pdfContainer).save();
    }

    render() {
        return html`
            <button @click=${this._downloadReport}>Download</button>
        `;
    }
}

window.customElements.define("gi-pdf-report-generator", PdfReportGenerator)