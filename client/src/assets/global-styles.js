import { css } from "lit";

const global = css`
  .container {
    width: 100%;
    padding: 0 1rem;
    margin: 0 auto;
  }

  @media (min-width: 576px) {
    .container {
      max-width: 540px;
    }
  }
  @media (min-width: 768px) {
    .container {
      max-width: 720px;
    }
  }
  @media (min-width: 992px) {
    .container {
      max-width: 960px;
    }
  }
  @media (min-width: 1200px) {
    .container {
      max-width: 1140px;
    }
  }
  @media (min-width: 1400px) {
    .container {
      max-width: 1320px;
    }
  }

  h1, h2 {
    color: var(--color-primary);;
  }

  .link {
    color: var(--color-primary);
    text-decoration: none;
  }

  .link:hover {
    text-decoration: underline;
  }
`;

export default global;
