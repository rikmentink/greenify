import { Router } from '@vaadin/router';

import { Register } from '../pages/Register';
import { Login } from '../pages/Login';
import { Survey } from '../pages/Survey';

export const router = new Router(document.getElementById('outlet'), {
    baseUrl: import.meta.env.BASE_URL
});

router.setRoutes([
    {
        path: import.meta.env.BASE_URL + 'login',
        component: 'gi-login',
    },
    {
        path: import.meta.env.BASE_URL + 'register',
        component: 'gi-register',
    },
    {
        path: import.meta.env.BASE_URL + 'temp/survey',
        component: 'gi-survey',
    },
]);

export default router;