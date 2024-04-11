import { Router } from '@vaadin/router';

import { Register } from '../pages/Register';
import { Login } from '../pages/Login';
import { Survey } from '../pages/Survey';

export const router = new Router(document.querySelector('#outlet'), {
    baseUrl: import.meta.env.BASE_URL
});

router.setRoutes([
    {
        path: import.meta.env.BASE_URL,
        component: 'greenify-app',
        children: [
            {
                path: import.meta.env.BASE_URL + 'login',
                component: 'gi-login',
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
        ]
    },
    
]);

export default router;