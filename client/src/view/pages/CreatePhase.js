import { LitElement, html, css } from 'lit';

export class CreatePhase extends LitElement {
    static styles = [css`
        h1 {
            color: #4CBB17;
        }

        .outer-container {
            display: flex;
            justify-content: center;
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
            width: 50%;
            border: #4CBB17 1px solid;
        }

        .create-btn {
            padding: 10px 50px 10px 50px;
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
            font-size: 14px;
            box-sizing: border-box;
            border: lightgray 1px solid;
        }
        
        .phase-select:focus {
            outline: none;
        }
    `]

    static properties = {
        id: {
            type: Number,
            reflect: true
        }
    };

    constructor() {
        super();
        this.interventionId = 0;
        this.intervention = JSON.parse(window.sessionStorage.getItem('intervention'));
        this.interventionName = this.intervention.name;
        console.log(this.interventionName);
    }

    render() {
        return html`
            <h1>Fase Creëren</h1>
            <div class="title-container">
                <h1>${this.interventionName}</h1>
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
                    <button class="create-btn" type="submit">Creëren</button>
                </div>
            </div>
        `;
    }
}

window.customElements.define('gi-createphase', CreatePhase);