import { Router } from "@vaadin/router";
import { getCurrentUser } from "./services/AccountService.js";

let BASE_URL = import.meta.env.BASE_URL;
let router;

export const initRouter = (outlet) => {

  router = new Router(outlet, {
    baseUrl: BASE_URL,
  });

  router.setRoutes([
    { path: "", component: "gi-home", },
    { path: "/login/option", component: "gi-loginoption" },
    { path: "/intervention/:id", component: "gi-intervention" },
    { path: "/intervention/:id/new-phase", component: "gi-createphase" },
    { path: "/login", component: "gi-login" },
    { path: "/register", component: "gi-register"},
    { path: "/phase/:id", component: "gi-overview" },
    { path: "/phase/:id/report", component: "gi-survey-result-report" },
    { path: "/tool/:id", component: "gi-survey" },
    { path: "(.*)", action: () => window.location.href = "/" },
  ]);
};

// Fetch current user and redirect to login page if null
getCurrentUser().then(user => {
  if (!user) {
    router.setRoutes([
      { path: "", component: "gi-login", },
      { path: "/login", component: "gi-login" },
      { path: "/register", component: "gi-register"},
      { path: "(.*)", action: () => window.location.href = "/" }
    ]);
  }
});

export const getRouter = () => router;
