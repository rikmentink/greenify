import { Router } from '@vaadin/router';

import { Register } from '../pages/Register';

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
]);

export default router;