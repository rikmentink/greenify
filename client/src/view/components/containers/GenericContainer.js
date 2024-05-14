import { LitElement, html, css } from 'lit';

export class GenericContainer extends LitElement {
    static styles = [css`
      h1 {
        color: Green;
      }

      .outer-container {
        display: flex;
        flex-direction: column;
        align-items: center;
        width: 100%;
      }

      .title-container {
        display: flex;
        justify-content: center;
        width: 100%;
      }

      .desc-container {
        display: flex;
        justify-content: center;
        width: 100%;
      }

      .description {
        font-size: 12px;
        color: slategrey;
        margin-top: -6px;
      }

      .main-block {
        box-shadow: rgba(0, 0, 0, 0.1) 0 4px 8px;
        padding: 15px;
        background-color: white;
        border-radius: 30px;
        display: flex;
        flex-direction: column;
        align-items: center;
        width: 100%;
      }

      .create-btn {
        padding: 12px 30px;
        margin: 8px 0;
        font-size: 16px;
        font-weight: bold;
        border: none;
        border-radius: 40px;
        cursor: pointer;
        background-color: #4CBB17;
        color: white;
      }

      .phase-select {
        width: 100%;
        padding: 12px 20px;
        margin: 8px 0;
        height: 40px;
        box-sizing: border-box;
        border-bottom: #4CBB17 2px solid;
        border-radius: 40px;
      }

    `]

    render() {
        return html`
            <h1>Creëer Fase</h1>
            <div class="title-container">
                <h1>Interventie Naam</h1>
            </div>
            <div class="desc-container">
                <p class="description">🛈 Per fase wordt er een vragenlijst beschikbaar gesteld voor alle toegevoegde gebruikers.</p>
            </div>
            <div class="outer-container">
                <div class="main-block">
                    <select class="phase-select" id="phase" required>
                        <option value="phase-1">Initiation</option>
                        <option value="phase-2">Planning</option>
                        <option value="phase-3">Execution</option>
                    </select>
                    <button class="create-btn" type="submit">Creëer</button>
                </div>
            </div>
        `;
    }

}

customElements.define('generic-container', GenericContainer);