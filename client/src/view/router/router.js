import { Router } from '@vaadin/router';

import { Register } from '../pages/Register';
import { Vragenlijst } from '../pages/Vragenlijst';
import { Overzicht } from '../pages/Overzicht';

export const router = new Router(document.getElementById('outlet'), {
    baseUrl: import.meta.env.BASE_URL
});

router.setRoutes([
    {
        path: import.meta.env.BASE_URL + '',
        component: 'greenify-app',
    },
    {
        path: import.meta.env.BASE_URL + 'register',
        component: 'gi-register',
    },
    {
        path: import.meta.env.BASE_URL + 'vragenlijst',
        component: 'gi-vragenlijst',
    },
    {
        path: import.meta.env.BASE_URL + 'overzicht',
        component: 'gi-overzicht',
    },
]);

export default router;