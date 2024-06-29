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

  .link {
    color: var(--color-primary);
    text-decoration: none;
  }

  .link:hover {
    text-decoration: underline;
  }

  .btn {
    background-color: var(--color-primary);
    color: white;
    padding: 0.5rem 1.5rem;
    border: none;
    border-radius: 1.25rem;
    cursor: pointer;
    font-size: 15px;
    font-weight: 600;
    text-decoration: none;
    transition: background-color .3s;
  }

  .btn:hover {
    background-color: var(--color-primary-dark);
  }

  form input,
  form textarea,
  form select {
    width: 100%;
    font-family: inherit;
    font-size: 14px;
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    padding: 0.5rem;
    margin: 0.5rem 0;
    border: 1px solid #ccc;
    border-radius: 4px;
    position: relative;
  }

  form input:active, form input:focus, 
  form textarea:active, form textarea:focus,
  form select:active, form select:focus {
    outline: none;
    border-color: var(--color-primary);
  }

  form textarea {
    resize: none;
  }
  
  form select {
    background: transparent;
    background-image: url("data:image/svg+xml;utf8,<svg fill='black' height='24' viewBox='0 0 24 24' width='24' xmlns='http://www.w3.org/2000/svg'><path d='M7 10l5 5 5-5z'/><path d='M0 0h24v24H0z' fill='none'/></svg>");
    background-repeat: no-repeat;
    background-position-x: 100%;
    background-position-y: 50%;
  }
`;

export default global;
