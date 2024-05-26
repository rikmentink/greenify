import { Router } from "@vaadin/router";

const BASE_URL = import.meta.env.BASE_URL;
let router;

export const initRouter = (outlet) => {
  router = new Router(outlet, {
    baseUrl: BASE_URL,
  });
  router.setRoutes([
    { path: "", component: "gi-home" },
    { path: "/login", component: "gi-login" },
    { path: "/login/option", component: "gi-loginoption" },
    { path: "/register", component: "gi-register" },
    { path: "/intervention/:id", component: "gi-intervention" },
    { path: "/intervention/:id/new-phase", component: "gi-createphase" },
    { path: "/phase/:id", component: "gi-overview" },
    { path: "/phase/:id/report", component: "gi-survey-result-report" },
    { path: "/tool/:id", component: "gi-survey" },
  ]);
};

export const getRouter = () => router;
