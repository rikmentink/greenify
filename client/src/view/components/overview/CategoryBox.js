import { html, css, LitElement } from 'lit';

export class CategoryBox extends LitElement {
    static styles = css`
      p, h1, h2, h3, h4, h5, h6, em {
        color: black;
      }

      .title {
        color: black;
        margin-top: 0;
        margin-bottom: 0;
      }

      .rectangle {
        display: grid;
        grid-template-columns: 1fr 1fr;
        min-width: 200px;
        min-height: 100px;
        width: 600px;
        height: 200px;
        border: #D6D6D6 2px solid;
        margin-top: 10px;
        padding: 20px;
        border-radius: 5px;
      }

      .title-description {
        display: flex;
        flex-direction: column;
      }

      .button-container {
        display: flex;
        justify-content: flex-end;
        align-items: flex-start;
      }

      greenify-button {
        color: black;
      }

      greenify-categoryquestionbutton {
        color: black;
      }

      .my-category-btn {
        width: 100%;
        justify-content: flex-end;
        display: flex;
        margin-bottom: 5px;
        margin-right: 20px;
        margin-top: 10px;
      }

      .my-category-btn a {
        background-color: #4CBB17;
        color: white;
        padding: 10px 60px 10px 60px;
        border-radius: 2px;
        font-size: 16px;
        text-decoration: none;
      }

      .questions-container {
        display: flex;
        flex-direction: row;
        gap: 10px;
        margin-top: 10px;
      }

      .my-question-btn {
        display: flex;
      }

      .my-question-btn a {
        background-color: #4CBB17;
        color: white;
        padding: 10px 17.6px;
        border-radius: 2px;
        font-size: 16px;
        text-decoration: none;
      }

      .sy-progress-container {
        align-items: center;
        line-height: 0.1;
        display: flex;
        flex-direction: column;
      }

      .sy-progress-container {
        align-items: center;
        line-height: 0.1;
        display: flex;
        flex-direction: column;
      }

      .progress-bar {
        width: 100%;
        background-color: #e0e0e0;
        height: 10px;
        border-radius: 5px;
        margin-top: 10px;
        display: flex;
      }

      .progress {
        height: 100%;
        background-color: #4CBB17;
        border-radius: 5px;
      }

      .progress-labels {
        width: 100%;
        display: flex;
        color: #666666;
        font-style: italic;
        justify-content: flex-end;
        margin-top: 10px;
      }

      .progress-label {
        margin: 0;
      }

      .progress-bar {
        width: 100%;
        background-color: #e0e0e0;
        height: 10px;
        border-radius: 5px;
        margin-top: 10px;
        display: flex;
      }

      .progress {
        height: 100%;
        background-color: #4CBB17;
        border-radius: 5px;
      }

      .progress-labels {
        width: 100%;
        display: flex;
        color: #666666;
        font-style: italic;
        justify-content: flex-end;
        margin-top: 10px;
      }

      .progress-label {
        margin: 0;
      }
    `;

    static properties = {
        category: { type: Object },
    };

    constructor() {
        super();
        this.category = {};
    }

    render() {
        return html`
            <div class="rectangle">
                <div class="title-description">
                    <h2 class="title">${this.category.name}</h2>
                    <div class="sy-progress-container">
                        <div class="progress-labels">
                            <p class="progress-label">Nog 0 vragen te gaan</p>
                        </div>
                        <div class="progress-bar">
                            <div class="progress" aria-label="Progression bar"></div>
                        </div>
                        <div class="progress-labels">
                            <p class="progress-label">0%</p>
                        </div>
                    </div>
                    <div class="questions-container">
                        ${this.category.questions.map((category, question, index) =>
                                html`
                                    <div class="my-question-btn">
                                        <a href="/questionid/${category.questionId}/category/${category.id}">${category.id}</a>
                                    </div>`)}
                    </div>
                </div>
                <div class="button-container">
                    <div class="my-category-btn">
                        <a href="/category/">Ga verder</a>
                    </div>
                </div>
            </div>
        `;
    }
    
    
}

customElements.define('greenify-categorybox', CategoryBox);
