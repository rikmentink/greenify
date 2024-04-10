import { LitElement, html, css } from 'lit';

export class Vragenlijst extends LitElement {
    static styles = [css`
      h1 {
        color: black;
      }

      .back-button {
        color: greenyellow;
      }

      .question-container {
        display: flex;
        align-items: center;
        position: relative;
      }

      .radio-container {
        display: flex;
        flex-direction: row;
        align-items: center;
        margin-left: 10px;
      }

      .priority-container {
        display: flex;
        flex-direction: row;
        align-items: center;
        margin-left: 10px;
      }

      .radio-button {
        display: flex;
        flex-direction: column;
        align-items: center;
        margin-left: 10px;
      }

      hr.divider {
        border-top: 3px solid #bbb;
        margin-left: 20px;
        min-height: 50px;
        flew-grow: 1;
        width: 0px;
      }
    `];

    static properties = {
    };

    constructor() {
        super();
    }

    render() {
        return html`
            <p><a class="back-button" href="/overzicht">← Terug naar overzicht</a></p>
            <h1 class="domain-name">Domein 1 - De groene interventie</h1>
            <div class="question-container">
                <p class="question">1. Deze vragenlijst gaat over de groene interventie.</p>
                <div class="radio-container">
                    <div class="radio-button">
                        <label>1</label>
                        <input type="radio" name="question1">
                        <span class="checkmark"></span>
                    </div>
                    <div class="radio-button">
                        <label>2</label>
                        <input type="radio" name="question1">
                        <span class="checkmark"></span>
                    </div>
                    <div class="radio-button">
                        <label>3</label>
                        <input type="radio" name="question1">
                        <span class="checkmark"></span>
                    </div>
                    <div class="radio-button">
                        <label>4</label>
                        <input type="radio" name="question1">
                        <span class="checkmark"></span>
                    </div>
                    <div class="radio-button">
                        <label>5</label>
                        <input type="radio" name="question1">
                        <span class="checkmark"></span>
                    </div>

                    <hr class="divider">

                    <div class="priority-container">
                        <div class="radio-button">
                            <label>1</label>
                            <input type="radio" name="priority">
                            <span class="checkmark"></span>
                        </div>
                        <div class="radio-button">
                            <label>2</label>
                            <input type="radio" name="priority">
                            <span class="checkmark"></span>
                        </div>
                        <div class="radio-button">
                            <label>3</label>
                            <input type="radio" name="priority">
                            <span class="checkmark"></span>
                        </div>
                        <div class="radio-button">
                            <label>4</label>
                            <input type="radio" name="priority">
                            <span class="checkmark"></span>
                        </div>
                    </div>
                </div>
            </div>

            <div class="question-container">
                <p class="question">2. Dit is een tweede test vraag.</p>
                <div class="radio-container">
                    <div class="radio-button">
                        <label>1</label>
                        <input type="radio" name="question2">
                        <span class="checkmark"></span>
                    </div>
                    <div class="radio-button">
                        <label>2</label>
                        <input type="radio" name="question2">
                        <span class="checkmark"></span>
                    </div>
                    <div class="radio-button">
                        <label>3</label>
                        <input type="radio" name="question2">
                        <span class="checkmark"></span>
                    </div>
                    <div class="radio-button">
                        <label>4</label>
                        <input type="radio" name="question2">
                        <span class="checkmark"></span>
                    </div>
                    <div class="radio-button">
                        <label>5</label>
                        <input type="radio" name="question2">
                        <span class="checkmark"></span>
                    </div>

                    <hr class="divider">

                    <div class="priority-container">
                        <div class="radio-button">
                            <label>1</label>
                            <input type="radio" name="priority2">
                            <span class="checkmark"></span>
                        </div>
                        <div class="radio-button">
                            <label>2</label>
                            <input type="radio" name="priority2">
                            <span class="checkmark"></span>
                        </div>
                        <div class="radio-button">
                            <label>3</label>
                            <input type="radio" name="priority2">
                            <span class="checkmark"></span>
                        </div>
                        <div class="radio-button">
                            <label>4</label>
                            <input type="radio" name="priority2">
                            <span class="checkmark"></span>
                        </div>
                    </div>
                </div>
        `;
    }
}

window.customElements.define('gi-vragenlijst', Vragenlijst);