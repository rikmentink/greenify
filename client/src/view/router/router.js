import { Router } from '@vaadin/router';

import { Home } from '../pages/Home';
import { Register } from '../pages/Register';
import { Overview } from '../pages/Overview';
import { Login } from '../pages/Login';
import { LoginOption } from '../pages/LoginOption';
import { Intervention } from '../pages/Intervention';
import { Survey } from '../pages/Survey';
import { SurveyResultReport } from '../pages/SurveyResultReport';
import { CreatePhase } from '../pages/CreatePhase';
import { InfoPopUp } from "../pages/InfoPopUp.js";

export const router = new Router(document.getElementById('outlet'), {
    baseUrl: import.meta.env.BASE_URL
});

router.setRoutes([
    {
        path: import.meta.env.BASE_URL + '',
        component: 'greenify-app',
        children: [
            {
                path: import.meta.env.BASE_URL + '', // For direct access to the base URL
                component: 'gi-home',
            },
            {
                path: import.meta.env.BASE_URL + 'home',
                component: 'gi-home',
            },
            {
                path: import.meta.env.BASE_URL + 'login',
                component: 'gi-login',
            },
            {
                path: import.meta.env.BASE_URL + 'login/option',
                component: 'gi-loginoption',
            },
            {
                path: import.meta.env.BASE_URL + 'register',
                component: 'gi-register',
            },
            {
                path: import.meta.env.BASE_URL + 'intervention',
                component: 'gi-intervention',
            },
            {
                // TODO: Use form data instead of parameter in the future.
                path: import.meta.env.BASE_URL + 'tool',
                component: 'gi-survey',
            },
            {
                // TODO: Include a way to determine of which phase the survey report is
                path: import.meta.env.BASE_URL + 'toolReport',
                component: 'gi-survey-result-report',
            },
        ]
    },
    {
        path: import.meta.env.BASE_URL + 'register',
        component: 'gi-register',
    },
    {
        path: import.meta.env.BASE_URL + 'overview',
        component: 'gi-overview',
    },
    {
        path: import.meta.env.BASE_URL + 'createphase',
        component: 'gi-createphase',
    },
    {
        path: import.meta.env.BASE_URL + 'category',
        component: 'gi-category',
    }
]);

export default router;
