import { Router } from '@vaadin/router';

import { Register } from '../pages/Register';
import { Overview } from '../pages/Overview';
import { Login } from '../pages/Login';
import { LoginOption } from '../pages/LoginOption';
import { Survey } from '../pages/Survey';
import { SurveyResultReport } from '../pages/SurveyResultReport';
import { CreatePhase } from '../pages/CreatePhase';

export const router = new Router(document.getElementById('outlet'), {
    baseUrl: import.meta.env.BASE_URL
});

router.setRoutes([
    {
        path: import.meta.env.BASE_URL + '',
        component: 'greenify-app',
        children: [
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
                path: import.meta.env.BASE_URL + 'survey/:surveyId',
                component: 'gi-survey',
                action: (context) => {
                  let page = document.querySelector('gi-survey');
                  if (!page) {
                    page = document.createElement('gi-survey');
                    document.body.appendChild(page);
                  }
                  page.id = parseInt(context.params.surveyId);
                  return page;
                }
            },
            {
                // TODO: Include a way to determine of which phase the survey report is
                path: import.meta.env.BASE_URL + 'surveyReport',
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
    }
]);

export default router;
