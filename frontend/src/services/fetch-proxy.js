import { getApiUrl } from "../util";

const apiUrl = getApiUrl();

export default () => ({
  // path must not start with / (e.g. it must be posts/<post_id> not /posts/<post_id>)
  executeRequest: async ({ path, method = "GET", data }) =>
    fetch(`${apiUrl}${path}`, {
      method,
      headers: {
        "Content-Type": "application/json",
      },
      ...(method !== "GET" && { body: JSON.stringify(data) }),
    }).then((response) => {
      console.log(response);
      if (response.ok) {
        try {
          return response.json();
        } catch (err) {
          console.log(err);
        }
      }
      throw response;
    }),

  fetchHTML: async ({ path }) =>
    fetch(`${apiUrl}${path}`, {
      headers: {
        "Content-Type": "text/html",
      },
    }).then((response) => {
      if (response.ok) {
        return response.text();
      }
      throw response;
    }),
});
