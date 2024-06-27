import { Router } from "@vaadin/router";
import { getCurrentUser } from "./services/AccountService.js";

let BASE_URL = import.meta.env.BASE_URL;
let router;
let routes = [];

(async () => {
  routes = [
    { path: "", component: "gi-login", },
    { path: "/login", component: "gi-login" },
    { path: "/register", component: "gi-register" },
    { path: "(.*)", action: () => window.location.href = "/" }
  ];
  await getCurrentUser().then((user) => {
    if (user) {
      routes = [
        { path: "", component: "gi-home" },
        { path: "/intervention/:id", component: "gi-intervention" },
        { path: "/intervention/:id/new-phase", component: "gi-createphase" },
        { path: "/intervention/:id/new-intervention", component: "gi-createintervention" },
        { path: "/intervention/:interventionId/phase/:phaseId", component: "gi-overview" },
        { path: "/phase/:id/report", component: "gi-survey-result-report" },
        { path: "/tool/:id", component: "gi-survey" },
        { path: "(.*)", action: () => window.location.href = "/" }];
    }
  });
})();

export const initRouter = (outlet) => {
  router = new Router(outlet, {
    baseUrl: BASE_URL,
  });
  
  router.setRoutes(routes);
}

export const getRouter = () => router;
