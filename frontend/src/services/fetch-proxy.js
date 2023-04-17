const { REACT_APP_API_URL: apiUrl } = process.env;

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
      if (response.ok) {
        return response.json();
      }
      throw response;
    }),
});
