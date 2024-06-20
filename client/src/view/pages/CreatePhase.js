import { LitElement, html, css } from 'lit';
import { Task } from '@lit/task';
import { getRouter } from '../../router.js';
import global from "../../assets/global-styles.js";

import { getInterventionById } from '../../services/InterventionService.js';
import { createPhase } from '../../services/PhaseService.js';

export class CreatePhase extends LitElement {
    static styles = [global, css`
        h1 {
            color: var(--color-primary);
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
            border: var(--color-primary) 1px solid;
        }

        form {
            display: flex;
            flex-direction: column;
            align-items: center;
            width: 300px;
            min-width: 150px;
        }

        #feedback {
            font-size: 12px;
            align-self: start;
        }

        #feedback.error {
            color: red;
        }

        #feedback.success {
            color: green;
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
        this.id = 0;
    }

    async connectedCallback() {
        super.connectedCallback();
        this.id = getRouter().location.params.id || 0;
        if (this.id === 0) {
            window.location.href = '/';
            return;
        }
        
        await this._fetchData(this.id);
        this.shadowRoot.getElementById('createPhaseForm').addEventListener('submit', this._onSubmit.bind(this))
    }

    async _fetchData(id) {
        this.data = new Task(this, {
            task: async ([id]) => getInterventionById(id),
            args: () => [id]
        });
        return await this.data.run();
    }

    async _onSubmit(event) {
        event.preventDefault();
        this.shadowRoot.getElementById('feedback').innerText = '';
        this.shadowRoot.getElementById('feedback').classList.remove('success');
        this.shadowRoot.getElementById('feedback').classList.remove('error');

        const data = this.validateForm(event);
        await this._savePhase(data);
    }

    validateForm(event) {
        const form = event.target;
        const formData = new FormData(form);
        const name = formData.get('name').toUpperCase();
        const description = formData.get('description') || '';

        if (!name) {
            this.shadowRoot.getElementById('feedback').innerText = 'Vul alle velden in.';
            this.shadowRoot.getElementById('feedback').classList.add('error');
            return;
        }

        return { name, description };
    }

    async _savePhase(data) {
        await createPhase(this.id, data.name, data.description)
        .then((data) => {
            console.log('Phase created:', data);
            this.shadowRoot.getElementById('feedback').innerText = 'Fase is succesvol aangemaakt.';
            this.shadowRoot.getElementById('feedback').classList.add('success');

            setTimeout(() => {
                window.location.href = `/intervention/${this.id}/phase/${data.id}`;
            }, 1000);
        })
        .catch((error) => {
            console.error('Error creating phase:', error);
            this.shadowRoot.getElementById('feedback').innerText = 'Er is iets misgegaan. Probeer het opnieuw.';
            this.shadowRoot.getElementById('feedback').classList.add('error');
        });
    }

    render() {
        return html`
            <h1>Fase Creëren</h1>
            <div class="title-container">
                <h1>Naam</h1>
            </div>
            <div class="desc-container">
                <p class="description">🛈 Per fase wordt er een vragenlijst beschikbaar gesteld voor alle toegevoegde gebruikers.</p>
            </div>
            <div class="outer-container">
                <div class="main-block">
                    <form id="createPhaseForm">
                        <select name="name" id="name" class="phase-select" required>
                            <option value="initiation">Initiation</option>
                            <option value="planning">Planning</option>
                            <option value="execution">Execution</option>
                        </select>
                        <textarea rows="2" name="description" id="description" placeholder="Beschrijving"></textarea>
                        <div id="feedback"></div>
                        <button class="btn" type="submit">Creëren</button>
                    </form>
                </div>
            </div>
        `;
    }
}

window.customElements.define('gi-createphase', CreatePhase);